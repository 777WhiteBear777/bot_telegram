import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class brobot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
    }

    @Override
    public String getBotUsername() {
        return "Bro333bot";
    }

    @Override
    public String getBotToken() {
        return "5711756410:AAHZF72d3DVjuLP5bl61tlmH88zAtnuttoA";
    }

}