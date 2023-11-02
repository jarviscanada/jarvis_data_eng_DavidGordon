package ca.jrvs.apps;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.sql.Date;
import java.util.Map;
import java.util.Optional;

public class RateHttpHelper {
    private static final Logger logger = LoggerFactory.getLogger(RateHttpHelper.class);
    private String apiKey;
    private String apiEndpoint;
    private OkHttpClient client;

    public RateHttpHelper(String apiKey, String apiEndpoint, OkHttpClient client) {
        logger.info("Initializing RateHttpHelper");
        this.apiKey = apiKey;
        this.apiEndpoint = apiEndpoint;
        this.client = client;
    }

    public Optional<Rate> fetchRate(String fromCode, String toCode, String date) {
        logger.info("Fetching rate from endpoint");
        ObjectMapper mapper = new ObjectMapper();
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://alpha-vantage.p.rapidapi.com/query?function="+apiEndpoint+"&from_symbol="+fromCode+"&datatype=json&outputsize=compact&to_symbol="+toCode)
                    .get()
                    .addHeader("X-RapidAPI-Key", "92f1efc4f2msh7f3d1b2d831b3c6p16dd4djsn6d12127ac48c")
                    .addHeader("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                    .build();

            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();

            Map<?,?> map = mapper.readValue(responseBody.string(), Map.class);
            Map<?,?> dates = mapper.convertValue(map.get("Time Series FX (Daily)"), Map.class);
            Rate rate = mapper.convertValue(dates.get(date), Rate.class);
            rate.setDate(Date.valueOf(date));
            rate.setFromSymbol(fromCode);
            rate.setToSymbol(toCode);
            return Optional.of(rate);
        } catch (IOException e) {
            logger.error("Couldn't reach AlphaVantage: " + e);
        }

        return Optional.empty();
    }

}
