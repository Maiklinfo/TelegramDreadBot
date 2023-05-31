package ru.DreadBot.Core;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.DreadBot.Store.BaseStore;
import ru.DreadBot.Store.Sqlite.SqLiteStore;
import ru.DreadBot.service.SendMessageOperationService;
import java.time.LocalDate;

import static ru.DreadBot.Constant.VarConstant.*;

public class CoreBot extends TelegramLongPollingBot {
    SendMessageOperationService sendMessageOperationService = new SendMessageOperationService();
    private BaseStore store = new SqLiteStore();
    private  boolean startPlanning;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            switch (update.getMessage().getText()) {
                case START:
                    executeMessage(sendMessageOperationService.createGreetingInformation(update));
                    executeMessage(sendMessageOperationService.createInstructionMessege(update));
                    break;
                case START_PLANNING:
                    startPlanning = true;
                    executeMessage(sendMessageOperationService.createPlanningMassege(update));
                    break;
                case END_PLANNING:
                    startPlanning = false;
                    executeMessage(sendMessageOperationService.createEndPlanningMassege(update));
                    break;
                case SHOW_DEALS:
                    if (startPlanning == false) {
                        executeMessage(sendMessageOperationService.createSimpleMessage(update, store.selectAll(LocalDate.now())));
                    }
                default:
                    if (startPlanning == true) {
                        store.save(LocalDate.now(), update.getMessage().getText());
                    }
            }
        }
        if (update.hasCallbackQuery()){
            String instruction = "Бот для формирования дел на день";
            String callDate = update.getCallbackQuery().getData();
            switch (callDate.toLowerCase()){
                case YES:
                    EditMessageText text = sendMessageOperationService.createEditMassege(update,instruction);
                    executeMessage(text);

            }


        }
    }

    @Override
    public String getBotUsername() {
        return "DreadBot";
    }

    @Override
    public String getBotToken() {
        return "6299185775:AAEenY4lnPPBPXYJwB_qjn25reJXyFhYIC0";

    }
    private <T extends BotApiMethod> void executeMessage(T sendMessage) {
        try {
            execute(sendMessage);
            } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}