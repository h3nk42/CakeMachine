package view.console;

import control.console.output.OutputEvent;
import control.console.output.MessageType;
import control.console.output.OutputEventHandler;

import java.io.Serializable;

public class Printer implements Serializable {
    public void printLine(String textToPrint) {
        System.out.println(textToPrint);
    }

    public static void print(Object source, String text, MessageType messageType, OutputEventHandler outputEventHandler) {
        OutputEvent outputEvent = new OutputEvent(source, text, messageType);
        outputEventHandler.handle(outputEvent);
    }
}
