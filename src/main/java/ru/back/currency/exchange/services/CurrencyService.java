package ru.back.currency.exchange.services;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.back.currency.exchange.dto.CurrencyDto;
import ru.back.currency.exchange.entity.Currency;
import ru.back.currency.exchange.mapper.CurrencyMapper;
import ru.back.currency.exchange.repository.CurrencyRepository;
import ru.back.currency.exchange.responses.CurrencyResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyMapper mapper;
    private final CurrencyRepository repository;

    public CurrencyDto getById(Long id) {
        log.info("CurrencyService method getById executed");
        Currency currency = repository.findById(id).orElseThrow(() -> new RuntimeException("Currency not found with id: " + id));
        return mapper.convertToDto(currency);
    }

    public Double convertValue(Long value, Long numCode) {
        log.info("CurrencyService method convertValue executed");
        Currency currency = repository.findByIsoNumCode(numCode);
        return value * currency.getValue();
    }

    public CurrencyDto create(CurrencyDto dto) {
        log.info("CurrencyService method create executed");
        return mapper.convertToDto(repository.save(mapper.convertToEntity(dto)));
    }

    public Map<String, List<CurrencyResponse>> getAll() {
        log.info("CurrencyService method getAll executed");
        List<Currency> currencies = repository.findAll();
        List<CurrencyDto> currenciesDto = new ArrayList<>();
        for (Currency currency : currencies) {
            currenciesDto.add(mapper.convertToDto(currency));
        }
        List<CurrencyResponse> currencyResponses = new ArrayList<>();
        for (CurrencyDto currencyDto : currenciesDto) {
            currencyResponses.add(new CurrencyResponse(currencyDto.getName(), currencyDto.getValue()));
        }
        Map<String, List<CurrencyResponse>> map = new HashMap<>();
        map.put("currencies", currencyResponses);
        return map;
    }
}