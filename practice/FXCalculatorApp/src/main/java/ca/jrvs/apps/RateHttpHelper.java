package ca.jrvs.apps;

import okhttp3.OkHttpClient;

import java.util.Optional;

public class RateHttpHelper {

    private String apiKey;
    private String apiEndpoint;
    private OkHttpClient client;

    public RateHttpHelper(String apiKey, String apiEndpoint, OkHttpClient client) {
        this.apiKey = apiKey;
        this.apiEndpoint = apiEndpoint;
        this.client = client;
    }

    public Optional<Rate> fetchRate(String fromCode, String toCode, String date) {
        return null;
    }

}
