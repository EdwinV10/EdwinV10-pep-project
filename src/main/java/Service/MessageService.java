package Service;
import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.*;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public Message newMessage(Message message){
        Account tempAccount = accountDAO.findExistingAccountbyId(message.getPosted_by());
        if(message.getMessage_text().length() > 0 && message.getMessage_text().length() < 255 &&
        tempAccount != null){
            return messageDAO.addMessage(message);
        }
        return null;
    }

    public Message deleteMessage(int message_id){
        Message tempMessage = messageDAO.getMessageByID(message_id);
        messageDAO.deleteMessage(message_id);
        return tempMessage;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageByID(int message_id) {
        return messageDAO.getMessageByID(message_id);
    }

    public Message patchMessage(int message_id, Message message){
        Message tempMessage = messageDAO.getMessageByID(message_id);
        if(tempMessage != null && (message.getMessage_text().length() > 0 && 
        message.getMessage_text().length()<255)){
            messageDAO.editMessage(tempMessage, message);
            tempMessage = messageDAO.getMessageByID(message_id);
            return tempMessage;
        }
        tempMessage = messageDAO.getMessageByID(message_id);
        return null;
    }

    public List<Message> getAllMessagesFromAUser(int account_id){
        return messageDAO.getAllMessagesFromAUser(account_id);
    }
}
