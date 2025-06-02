package ru.back.currency.exchange.process_api;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.xml.bind.annotation.XmlElement;

@ToString
@Setter
@NoArgsConstructor
public class CurrencyJaxb {
    private Long numCode;
    private String charCode;
    private String name;
    private String value;
    private Long nominal;

    @XmlElement(name = "NumCode")
    public Long getNumCode() {
        return numCode;
    }

    @XmlElement(name = "CharCode")
    public String getCharCode() {
        return charCode;
    }

    @XmlElement(name = "Name")
    public String getName() {
        return name;
    }

    @XmlElement(name = "Value")
    public String getValue() {
        return value.replace(",", ".");
    }

    @XmlElement(name = "Nominal")
    public Long getNominal() {
        return nominal;
    }
}