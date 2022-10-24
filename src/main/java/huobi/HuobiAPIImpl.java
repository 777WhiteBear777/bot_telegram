package huobi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class HuobiAPIImpl implements HuobiAPI {

    private static final Logger log = LoggerFactory.getLogger(HuobiAPIImpl.class);
    private final String API_CANDLES_URL = "https://api.huobi.pro/market/history/kline?period=4hour&size=1&symbol=evmosusdt";

    @Override
    public String getTokenCandles() {
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(API_CANDLES_URL);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            log.warn("Такая торговая пара не найдена!");
            System.out.println("Такая торговая пара не найдена!");
        }
        return content.toString();
    }

}
