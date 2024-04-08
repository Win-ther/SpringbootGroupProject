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
        return Objects.requireNonNull(restClient.get()
                        .uri(url)
                        .header("X-RapidAPI-Key", "87306660eamshda4409a1ab43323p1532cbjsn3e85c9bcd44a")
                        .header("X-RapidAPI-Host", "wordcount2.p.rapidapi.com")
                        .accept()
                        .retrieve()
                        .body(String.class))
                .split(",");
    }
}
