package ru.DreadBot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

import static java.lang.StrictMath.toIntExact;
import static java.util.Arrays.asList;
import static ru.DreadBot.Constant.VarConstant.*;

public class SendMessageOperationService {

    private final String GREETING_MASSEGE = "Привет";
    private final String PLANING_MASSEGE = "Вводите дела";
    private final String END_PLANNING_MASSEGE = "Планирование закончено";
    private final String INSTRUCTIONS  = "Хотите прочесть инструкцию?";
    private final ButtonsService buttonsService = new ButtonsService();

    //public SendMessageOperationService() {
      //  this.buttonsService = buttonsService;
   // }



    public SendMessage createGreetingInformation(Update update) {
        SendMessage massage = createSimpleMessage(update, GREETING_MASSEGE);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(asList(START_PLANNING, END_PLANNING, SHOW_DEALS)));
        massage.setReplyMarkup(keyboardMarkup);
        return massage;

    }
        public SendMessage createPlanningMassege(Update update) {
            return  createSimpleMessage(update, PLANING_MASSEGE);
        }

        public SendMessage createEndPlanningMassege(Update update) {
            return  createSimpleMessage(update, END_PLANNING_MASSEGE);
        }


    public static SendMessage createSimpleMessage(Update update, String massege) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText(massege);
        return sendMessage;
    }
    public   SendMessage createSimpleMessage(Update update, List<String> masseges) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        StringBuilder massege = new StringBuilder();
        for (String s: masseges) {
            massege.append(s).append("\n");
        }
        sendMessage.setText(massege.toString());
        return sendMessage;
    }
    public  SendMessage createInstructionMessege(Update update) {
        SendMessage sendMessage = createSimpleMessage(update, INSTRUCTIONS);
        InlineKeyboardMarkup replyKeyboardMarkup =
                buttonsService.setInlineKeyboard(buttonsService.createInlineButton("Yes"));
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }


    public EditMessageText createEditMassege(Update update, String instruction) {
        EditMessageText editMessageText = new EditMessageText();
        long mesId = update.getCallbackQuery().getMessage().getMessageId();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        editMessageText.setChatId(String.valueOf(chatId));
        editMessageText.setMessageId(toIntExact(mesId));
        editMessageText.setText(instruction);
        return editMessageText;

    }
}
