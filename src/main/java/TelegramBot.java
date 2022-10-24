import huobi.HuobiAPI;
import huobi.HuobiAPIImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.util.Objects;

public class TelegramBot extends TelegramLongPollingBot {

    private static final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    private final String BOT_USERNAME = "Bro333bot";
    private final String BOT_TOKEN = "5711756410:AAHZF72d3DVjuLP5bl61tlmH88zAtnuttoA";

    public void initBot() {
        // log4j
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            log.info("Registering bot...");
            telegramBotsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            log.error("Failed to register bot(check internet connection / bot token or make sure only one instance of bot is running).", e);
        }
        log.info("Telegram bot is ready to accept updates from user......");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();

            if (update.getMessage().getText().startsWith("/evmos")) {
                HuobiAPI huobiAPI = new HuobiAPIImpl();
                JSONArray json = new JSONObject(huobiAPI.getTokenCandles()).getJSONArray("data");
                float tokenPrice = 0f;
                for (int i = 0; i < json.length(); i++) {
                    JSONObject j = json.getJSONObject(i);
                    tokenPrice = j.getFloat("close");
                }
                SendMessage message = SendMessage.builder()
                        .chatId(chatId.toString())
                        .text("EVMOS price: " + tokenPrice)
                        .build();
                try {
                    sendApiMethod(message);
                } catch (TelegramApiException e) {
                    log.error("Exception when sending message: ", e);
                }
            }
            sendFuckYouMessage(chatId);
        }
    }

    private void sendFuckYouMessage(Long chatId) {
        SendAnimation sendAnimation = new SendAnimation(chatId.toString(),
                new InputFile(new File(Objects
                        .requireNonNull(this.getClass()
                                .getResource("/mp4/hi.mp4")).getFile())));
        try {
            Integer lastId = execute(sendAnimation).getMessageId();
            Thread.sleep(5000);
            execute(new DeleteMessage(chatId.toString(), lastId));
        } catch (TelegramApiException | InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Exception when sending \"FUCK YOU\" message: ", e);
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

}