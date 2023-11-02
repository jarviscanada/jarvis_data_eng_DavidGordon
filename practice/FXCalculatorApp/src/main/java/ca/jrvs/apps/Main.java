package ca.jrvs.apps;

import ca.jrvs.apps.util.DatabaseConnectionManager;
import ca.jrvs.apps.util.PropertiesHelper;
import okhttp3.OkHttpClient;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        String[] properties = PropertiesHelper.getProperties();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient();
        DatabaseConnectionManager dcm = new DatabaseConnectionManager();
        try (Connection c = dcm.getConnection()) {
            DailyRateRepo rateRepo = new DailyRateRepo(c);
            CurrencyRepo currRepo = new CurrencyRepo(c);
            RateHttpHelper http = new RateHttpHelper(properties[6], "FX_DAILY", client);
            RateService rateService = new RateService(rateRepo, http);
            CurrencyService currService = new CurrencyService(currRepo);
            FxController controller = new FxController(rateService, currService);
            controller.processExchange(args[0], args[1], args[2], args[3]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}