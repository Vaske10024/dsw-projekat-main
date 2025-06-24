package raf.draft.dsw.controller.messagegenerator;

import raf.draft.dsw.controller.observer.IPublisher;
import raf.draft.dsw.controller.observer.ISubscriber;

import raf.draft.dsw.model.messages.Message;
import raf.draft.dsw.model.messages.MessageType;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.security.PublicKey;
import java.util.ArrayList;

public class MessageGenerator implements IPublisher {
    private ArrayList<ISubscriber> subscribers = new ArrayList<>();

    public MessageGenerator(){
        addSubscriber(LoggerFactory.createLogger("console"));
        addSubscriber(LoggerFactory.createLogger("file"));


    }

    @Override
    public void addSubscriber(ISubscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(ISubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers(Message message) {
        for (ISubscriber subscriber : subscribers) {
            subscriber.update(message);
        }
    }
    public void generateMessage(String content, MessageType type) {
        Message message = new Message(content, type, System.currentTimeMillis());


        notifySubscribers(message);
    }



}
