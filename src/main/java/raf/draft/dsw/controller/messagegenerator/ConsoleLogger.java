package raf.draft.dsw.controller.messagegenerator;

import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.model.messages.Message;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleLogger extends Logger {


    @Override
    public void update(Message message) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String timestamp = sdf.format(new Date(message.getTimestamp()));
        System.out.println("[" + message.getType() + "][" + timestamp + "] " + message.getContent());

    }
}