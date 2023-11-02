package ca.jrvs.apps;

import ca.jrvs.apps.util.DatabaseConnectionManager;
import ca.jrvs.apps.util.PropertiesHelper;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Application starting");
        if(args.length != 4) {
            System.out.println("USAGE: java FxCalculator.jar FROM_CODE TO_CODE DATE AMOUNT");
            return;
        }

        String[] properties = PropertiesHelper.getProperties();

        try {
            logger.info("Initializing postgres driver");
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("Could not initialize postgres driver: " + e);
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
            logger.error("Could not process exchange: " + e);
        }

        logger.error("Program has finished");
    }
}