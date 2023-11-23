package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.AppConfig;
import ca.jrvs.apps.trading.model.IexQuote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class IexQuoteHttpHelper {
    final Logger logger = LoggerFactory.getLogger(IexQuoteHttpHelper.class);
    private final String IEX_TOKEN;
    private final String STABLE_API_BASE = "https://cloud.iexapis.com/stable/stock/";
    private final String BATCH_API_BASE = "https://cloud.iexapis.com/v1/stock/market/";

    public IexQuoteHttpHelper() {
        this.IEX_TOKEN = AppConfig.getProperty("IEX_TOKEN");
    }

    public Optional<IexQuote> getIexQuote(String ticker) {
        try {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(STABLE_API_BASE+ticker+"/quote?token="+IEX_TOKEN))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            logger.error("Error fetching quote: " + e);
        }
        return null;
    }

    public Optional<List<IexQuote>> getBatchQuote(Iterable<String> tickers) {
        try {

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
            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            logger.error("Error fetching quote batch: " + e);
        }

        return null;
    }
}
