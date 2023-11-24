package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.AppConfig;
import ca.jrvs.apps.trading.model.IexQuote;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MarketDataHttpHelper {
    final Logger logger = LoggerFactory.getLogger(MarketDataHttpHelper.class);
    private final String IEX_TOKEN;
    private final String STABLE_API_BASE = "https://cloud.iexapis.com/stable/stock/";
    private final String BATCH_API_BASE = "https://cloud.iexapis.com/v1/stock/market/";

    public MarketDataHttpHelper() {
        this.IEX_TOKEN = AppConfig.getProperty("IEX_TOKEN");
    }

    public Optional<IexQuote> getIexQuote(String ticker) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(STABLE_API_BASE + ticker + "/quote?token=" + IEX_TOKEN))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            Map<?,?> map = mapper.readValue(response.body(), Map.class);
            IexQuote quote = mapper.convertValue(map, IexQuote.class);
            return Optional.of(quote);
        } catch (IOException | InterruptedException e) {
            logger.error("Error fetching quote: " + e);
        }

        return Optional.empty();
    }

    public Optional<List<IexQuote>> getBatchQuote(Iterable<String> tickers) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<IexQuote> quoteList = new ArrayList<>();
            StringBuilder stringBuilder = new StringBuilder();
            for (String ticker : tickers) {
                stringBuilder.append(ticker).append(',');
            }
            String tickersString = stringBuilder.toString();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BATCH_API_BASE + "batch?symbols=" + tickersString + "&types=quote&token=" + IEX_TOKEN))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            Map<?,?> map = mapper.readValue(response.body(), Map.class);
            for(var entry : map.entrySet()) {
                quoteList.add(mapper.convertValue(map, IexQuote.class));
            }
            return Optional.of(quoteList);
        } catch (IOException | InterruptedException e) {
            logger.error("Error fetching quote batch: " + e);
        }

        return Optional.empty();
    }
}
