package view.output;

import java.io.Serializable;

public class Output implements Serializable {
    protected void printLine(String textToPrint) {
        System.out.println(textToPrint);
    }

    public static void print(Object source, String text, MessageType messageType, OutputEventHandler outputEventHandler) {
        OutputEvent outputEvent = new OutputEvent(source, text, messageType);
        outputEventHandler.handle(outputEvent);
    }
}
