import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class main {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            log.info("Registering bot...");
            telegramBotsApi.registerBot(new brobot());
        } catch (TelegramApiRequestException e) {
            log.info("Failed to register bot(check internet connection / bot token or make sure only one instance of bot is running).");
        }
        log.info("Telegram bot is ready to accept updates from user......");
    }
}
