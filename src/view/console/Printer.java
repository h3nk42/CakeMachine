package view.console;

import control.console.output.OutputEvent;
import control.console.output.MessageType;
import control.console.output.OutputEventHandler;

import java.io.Serializable;

public class Printer implements Serializable {
    public void printLine(String textToPrint) {
        System.out.println(textToPrint);
    }
}
