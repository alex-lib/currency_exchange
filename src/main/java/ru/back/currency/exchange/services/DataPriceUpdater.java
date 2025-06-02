package ru.back.currency.exchange.services;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.back.currency.exchange.entity.Currency;
import ru.back.currency.exchange.process_api.CBRFClient;
import ru.back.currency.exchange.process_api.CurrenciesLoader;
import ru.back.currency.exchange.process_api.CurrencyJaxb;
import ru.back.currency.exchange.process_api.XmlParser;
import ru.back.currency.exchange.repository.CurrencyRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class DataPriceUpdater {
    private final CBRFClient cbrfClient;
    private final CurrencyRepository currencyRepository;
    private final XmlParser parser;
    private static final int CHECK_INTERVAL_MS = 3_600_000;

    @Scheduled(fixedRate = CHECK_INTERVAL_MS)
    public void updateDataPrices() {
        CurrenciesLoader loader = parser.toObject(cbrfClient.getXmlData());
        List<CurrencyJaxb> uniqueCurrencies = checkUniqueness(loader);
        for (CurrencyJaxb currencyJaxb : uniqueCurrencies) {
            Currency currency = currencyRepository.findByIsoNumCode(currencyJaxb.getNumCode());
            if (currency != null) {
                currency.setValue(Double.valueOf(currencyJaxb.getValue()));
                currency.setName(currencyJaxb.getName());
                currency.setIsoCharCode(currencyJaxb.getCharCode());
                currencyRepository.save(currency);
                log.debug("Currency updated: " + currency.getName());
            } else {
                createNewCurrency(currencyJaxb);
            }
        }
        currencyRepository.flush();
        log.info("Data prices updated successfully");
    }

    private void createNewCurrency(CurrencyJaxb currencyJaxb) {
        Currency currencyEntity = new Currency();
        currencyEntity.setValue(Double.valueOf(currencyJaxb.getValue()));
        currencyEntity.setIsoNumCode(currencyJaxb.getNumCode());
        currencyEntity.setIsoCharCode(currencyJaxb.getCharCode());
        currencyEntity.setName(currencyJaxb.getName());
        currencyEntity.setNominal(currencyJaxb.getNominal());
        currencyRepository.save(currencyEntity);
        log.debug("New currency created: " + currencyEntity.getName());
    }

    private List<CurrencyJaxb> checkUniqueness(CurrenciesLoader loader) {
        Set<String> checkingUniqueness = new HashSet<>();
        log.debug("Checking uniqueness of currencies...");
        return loader.getCurrencies().stream()
                .filter(currency -> checkingUniqueness.add(currency.getName()))
                .collect(Collectors.toList());
    }
}