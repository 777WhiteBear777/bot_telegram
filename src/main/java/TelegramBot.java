import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.io.File;
import java.util.Objects;

public class TelegramBot extends TelegramLongPollingBot {
    private static final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    @Override
    public String getBotUsername() {
        return "Bro333bot";
    }

    @Override
    public String getBotToken() {
        return "5711756410:AAHZF72d3DVjuLP5bl61tlmH88zAtnuttoA";
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            SendAnimation sendAnimation = new SendAnimation(chatId.toString(),
                    new InputFile(new File(Objects
                            .requireNonNull(this.getClass()
                                    .getResource("/mp4/hi.mp4")).getFile())));

            try {

                JSONArray print = new JSONObject(getUrlContent()).getJSONArray("data");
                float cod = 0;
                for (int i = 0; i < print.length(); i++) {
                    JSONObject j = print.getJSONObject(i);
                    cod = j.getFloat("close");
                }
                SendMessage pricetoken = SendMessage.builder()
                        .chatId(chatId.toString())
                        .text("Hi!" + "\n" + "Price \"Evmos\"  - " + cod)
                        .build();

                sendApiMethod(pricetoken);
                Integer lastId = execute(sendAnimation).getMessageId();
                Thread.sleep(5000);
                execute(new DeleteMessage(update.getMessage().getChatId().toString(), lastId));
            } catch (TelegramApiException e) {
                Thread.currentThread().interrupt();
                log.error("Exception when sending message: ", e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static String getUrlContent() {
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL("https://api.huobi.pro/market/history/kline?period=4hour&size=1&symbol=evmosusdt");
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Такая торговая пара не найдена!");
        }
        return content.toString();
    }

}