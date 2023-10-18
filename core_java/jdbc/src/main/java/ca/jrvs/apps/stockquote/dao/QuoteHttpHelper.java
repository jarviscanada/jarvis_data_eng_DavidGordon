package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.practice.dto.Company;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.Reader;
import java.nio.file.Paths;
import java.util.Map;

public class QuoteHttpHelper {
    private final String apiKey;
    private OkHttpClient client;

    public QuoteHttpHelper(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Fetch latest quote data from Alpha Vantage endpoint
     *
     * @param symbol The stock symbol
     * @return Quote with latest data
     * @throws IllegalArgumentException - if no data was found for the given symbol
     */
    public Quote fetchQuoteInfo(String symbol) throws IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol="+symbol+"&datatype=json")
                    .get()
                    .addHeader("X-RapidAPI-Key", apiKey)
                    .addHeader("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                    .build();

            Response response = client.newCall(request).execute();

            Map<?,?> map = mapper.readValue(response.body().string(), Map.class);

            return mapper.convertValue(map.get("Global Quote"), Quote.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
