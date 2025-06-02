package ru.back.currency.exchange.process_api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

@Slf4j
@Service
public class XmlParser {

    public CurrenciesLoader toObject(String xml) {
        try {
            JAXBContext context = JAXBContext.newInstance(CurrenciesLoader.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (CurrenciesLoader) unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            e.printStackTrace();
            log.error("Error parsing XML", e);
            return null;
        }
    }
}