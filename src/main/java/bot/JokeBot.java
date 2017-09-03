package bot;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.telegram.telegrambots.*;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.lang.reflect.Array;
import java.util.*;

public class JokeBot extends TelegramLongPollingBot{

    public static HashMap<Long, HashMap<NewUser, Integer>> chatMap = new HashMap<>();


    public static void main(String[] args) throws TelegramApiException {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new JokeBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        Timer time = new Timer();
        MapEraser st = new MapEraser();
      //  time.schedule(st, 0, 20000);
    }



    @Override
    public String getBotUsername() {
        return BotStrings.botUserName;
    }

    @Override
    public String getBotToken() {
        return BotStrings.botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        ArrayList<NewUser> minUsers = new ArrayList<>();

        Message message = update.getMessage();

        try {
            if (update.getMessage().isReply() && message.getText().equals("-")) {
                NewUser newUser = new NewUser();
                newUser.setUserName(message.getReplyToMessage().getFrom().getUserName());
                newUser.setFirstName(message.getReplyToMessage().getFrom().getFirstName());
                newUser.setLastName(message.getReplyToMessage().getFrom().getLastName());
                newUser.setId(message.getReplyToMessage().getFrom().getId());
                {
                    if (chatMap.containsKey(update.getMessage().getChatId()))  //check for the chat in chatMap
                    {
                        if (chatMap.get(message.getChatId()).containsKey(newUser)) { //check for user in innerMap
                            Integer i = chatMap.get(message.getChatId()) //get innerMap
                                    .get(newUser); //get number of "Jokes" for currently user
                            chatMap.get(message.getChatId()).put(newUser, i - 1);
                        } else {
                            chatMap.get(message.getChatId()).put(newUser, -1);
                        }
                    } else {
                        chatMap.put(message.getChatId(), new HashMap<NewUser, Integer>());
                        chatMap.get(message.getChatId()).put(newUser, -1);
                    }
                }
            } else if (message.getText().equals("show")) {
                if (!chatMap.containsKey(message.getChatId())) {
                    sendMsg(message, "В данном чате еще никто \"Хуево\" не шутил");
                } else {
                    if (chatMap.isEmpty()) {
                        sendMsg(message, "список пуст");
                    } else {
                        Integer min = 0;
                        HashMap<NewUser, Integer> tmpMap = chatMap.get(message.getChatId());
                        for (NewUser key : tmpMap.keySet()) {
                            if (tmpMap.get(key) <= min) {
                                min = tmpMap.get(key);
                            }
                        }
                        for (NewUser key : tmpMap.keySet()) {
                            if (tmpMap.get(key).equals(min)) {
                                minUsers.add(key);
                            }
                        }
                        {
                            System.out.println(minUsers);
                            sendMsg(message, "Звание \"Хуев шутник\" сегодня получает ");
                            int usr = 0;
                            for (NewUser i : minUsers) {
                                sendMessage(message, minUsers.get(usr).getFirstName() + " " + minUsers.get(usr).getLastName() +
                                        " @" + minUsers.get(usr).getUserName() + " рейтинг " + min);
                                usr++;
                            }
                            minUsers.clear();
                        }
                    }
                }
            }

            if (message.getText().equals("sort")) {
                int n = 0;
                MapOperations mapOperations = new MapOperations();
                ArrayList<NewUser> sortedUsers = mapOperations.sortingKeys(chatMap.get(message.getChatId()));
                sendMsg(message, "Топ хуевых шутников сегодня: ");
                if (sortedUsers.size() < 6)
                    n = sortedUsers.size();
                else
                    n = 5;
                for (int i = 0; i < n; i++) {
                    sendMessage(message, sortedUsers.get(i).getFirstName() + " " + sortedUsers.get(i).getLastName() +
                            " @" + sortedUsers.get(i).getUserName() + " рейтинг " +
                            mapOperations.sortingValues(chatMap.get(message.getChatId())).get(i).toString());
                }
            }
        } catch (NullPointerException e) {
        }
    }
    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
