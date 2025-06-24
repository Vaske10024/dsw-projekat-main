package raf.draft.dsw.controller.observer;

import raf.draft.dsw.model.messages.Message;

public interface IPublisher {
    void addSubscriber(ISubscriber subscriber);
    void removeSubscriber(ISubscriber subscriber);
    void notifySubscribers(Message message);


}
