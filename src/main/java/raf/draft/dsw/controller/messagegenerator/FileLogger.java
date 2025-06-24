package raf.draft.dsw.controller.messagegenerator;

import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.model.messages.Message;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileLogger extends Logger {
    @Override
    public void update(Message message) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String timestamp = sdf.format(new Date(message.getTimestamp()));
        try (FileWriter writer = new FileWriter("src/main/resources/log.txt", true)) {
            writer.write("[" + message.getType() + "][" + timestamp + "] " + message.getContent() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
