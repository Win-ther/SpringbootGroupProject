package se.iths.springbootgroupproject.services;

import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class WordcountService {
    private final RestClient restClient;

    public WordcountService(RestClient restClient) {
        this.restClient = restClient;
    }
    @Retryable
    public String[] analyseText(String text){
        String url = String.format("https://wordcount2.p.rapidapi.com/?text=%s", text);
        final String WORDCOUNT_APIKEY_ENV = System.getenv("WORDCOUNT_APIKEY");
        return Objects.requireNonNull(restClient.get()
                        .uri(url)
                        .header("X-RapidAPI-Key", WORDCOUNT_APIKEY_ENV)
                        .header("X-RapidAPI-Host", "wordcount2.p.rapidapi.com")
                        .accept()
                        .retrieve()
                        .body(String.class))
                .split(",");
    }
}
