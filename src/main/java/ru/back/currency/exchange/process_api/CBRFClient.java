package ru.back.currency.exchange.process_api;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.http.client.HttpClient;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class CBRFClient {
    private final HttpClient httpClient;
    private final XmlMapper xmlMapper;
    private final String apiUrl;

    public CBRFClient(@Value("${cbrf.api.getInfo}") String apiUrl) {
        this.apiUrl = apiUrl;
        this.httpClient = HttpClientBuilder.create().build();
        this.xmlMapper = new XmlMapper();
    }

    public String getXmlData() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(apiUrl, String.class);
    }
}