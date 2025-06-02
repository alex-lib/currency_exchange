package ru.back.currency.exchange.process_api;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Setter
@NoArgsConstructor
@XmlRootElement(name = "ValCurs")
public class CurrenciesLoader {

    private String date;
    private List<CurrencyJaxb> currencies;

    @XmlAttribute(name = "Date")
    public String getDate() {
        return date;
    }

    @XmlElement(name = "Valute")
    public List<CurrencyJaxb> getCurrencies() {
        return currencies;
    }
}