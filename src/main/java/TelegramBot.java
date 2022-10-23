import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
            SendAnimation sendAnimation = new SendAnimation(chatId.toString(), new InputFile(new File(Objects.requireNonNull(this.getClass().getResource("/mp4/hi.mp4")).getFile())));
            try {
                Integer lastId = execute(sendAnimation).getMessageId();
                Thread.sleep(4000);
                execute(new DeleteMessage(update.getMessage().getChatId().toString(), lastId));
                execute(new DeleteMessage(update.getMessage().getChatId().toString(), lastId - 1));
            } catch (TelegramApiException | InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Exception when sending message: ", e);
            }
        }
    }

}