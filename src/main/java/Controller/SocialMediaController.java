package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;
import Model.*;
import Service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    MessageService messageService;
    AccountService accountService;
    public SocialMediaController(){
        this.messageService = new MessageService();
        this.accountService = new AccountService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getMessagesHandler);
        app.get("/messages/{message_id}", this::getMessagebyIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::patchMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesFromAUserHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void postRegisterHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedaccount = accountService.addAccount(account);
        if(addedaccount!=null){
            context.json(mapper.writeValueAsString(addedaccount));
        }else{
            context.status(400);
        }
    }

    private void postLoginHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedaccount = accountService.login(account);
        if(addedaccount!=null){
            context.json(mapper.writeValueAsString(addedaccount));
        }else{
            context.status(401);
        }
    }

    private void postMessagesHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message addedmessage = messageService.newMessage(message);
        if(addedmessage!=null){
            context.json(mapper.writeValueAsString(addedmessage));
        }else{
            context.status(400);
        }
    }

    private void getMessagesHandler(Context context) throws JsonProcessingException{
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    private void getMessagebyIdHandler(Context context) throws JsonProcessingException{
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message retrivedMessage = messageService.getMessageByID(messageId);
        if(retrivedMessage!=null){
            context.json(retrivedMessage);
        }else{
            context.status(200);
        }
    }

    private void deleteMessageByIdHandler(Context context) throws JsonProcessingException{
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessage(messageId);
        if(deletedMessage!=null){
            context.json(deletedMessage);
        }else{
            context.status(200);
        }
    }

    private void patchMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = mapper.readValue(context.body(), Message.class);
        Message addedmessage = messageService.patchMessage(messageId, message);
        if(addedmessage!=null){
            context.json(mapper.writeValueAsString(addedmessage));
        }else{
            context.status(400);
        }
    }
    
    private void getMessagesFromAUserHandler(Context context){
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesFromAUser(accountId);
        context.json(messages);
    }

}