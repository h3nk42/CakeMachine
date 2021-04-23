package control.console;

import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventHandler;
import control.automat.events.DataType;
import control.automat.events.OperationType;
import sun.plugin2.message.Message;
import view.input.Input;
import view.output.MessageType;
import view.output.OutputEvent;
import view.output.OutputEventHandler;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


public class Console {
    private final Input input;
    private Stack<ConsoleState> modeStack;
    private OutputEventHandler outputEventHandler;
    private AutomatEventHandler automatEventHandler;
    /*private final InputEventListenerConstant lConst;
    private final InputEventListenerMode lMode;
    private final InputEventListenerCreate lCreate;
    private final InputEventListenerCreateHersteller lCreateH;
    private final InputEventListenerCreateKuchen lcreateK;
    private InputEventHandler handler;
    private Automat automat;
     */


    public Console(OutputEventHandler outputEventHandler, AutomatEventHandler automatEventHandler) {
        this.outputEventHandler = outputEventHandler;
        this.automatEventHandler = automatEventHandler;
        modeStack = new Stack<>();
        modeStack.push(ConsoleState.none);
       /* this.automat = automat;
        reader = new Reader(this);
        handler = new InputEventHandler(this.automat);
        lConst = new InputEventListenerConstant(this);
        lMode = new InputEventListenerMode(this);
        lCreate = new InputEventListenerCreate(this);
        lCreateH = new InputEventListenerCreateHersteller(this);
        lcreateK = new InputEventListenerCreateKuchen(this);
        handler.add(lConst,true);
        handler.add(lMode,true);*/
        this.input = new Input();
    }

    public String awaitInput() {
        String inText = input.awaitInput();
        return inText;
    }

    public boolean sendOutPutEvent(String textToSend, MessageType messageType) {
        OutputEvent oEventPrintText = new OutputEvent(this, textToSend, messageType);
        outputEventHandler.handle(oEventPrintText);
        return true;
    }

    public void initiateLoop() {
        while (true) {

            /* PRINT CONSOLE STATUS */
            String messageToPrint = "";
            switch (this.getState()) {
                case none:
                    messageToPrint = "\u001B[33m" + "Modus wählen:" + "\u001B[0m" + " \n c - Einfügen \n r - Anzeigen\n u - Ändern\n d - Löschen\n p - Speichern \n conf - Konfiguration \n exit - Programm beenden";
                    break;
                case r:
                    messageToPrint = "\u001B[33m" + "Anzeigen: " + "\u001B[0m" + " \n b - Zurück \n exit - Programm beenden";
                    break;
                case c:
                    messageToPrint = "\u001B[33m" + "Einfügen: " + "\u001B[0m" + " \n h - Hersteller \n k - Kuchen \n b - Zurück \n exit - Programm beenden";
                    break;
                case ch:
                    messageToPrint = "\u001B[33m" + "Hersteller Einfügen: " + "\u001B[0m" + " \n <herstellerName> \n b - Zurück \n exit - Programm beenden";
                    break;
                case ck:
                    messageToPrint = "\u001B[33m" + "Kuchen Einfügen: " + "\u001B[0m" + " \n <kuchenTyp> <herstellername> <preis> <nährwert> <haltbarkeit> <allergen,..> <kremsorte> oder/und <obstsorte> \n b - Zurück \n exit - Programm beenden";
                    break;
                case d:
                    messageToPrint = "\u001B[33m" + "Löschen: " + "\u001B[0m" + " \n b - Zurück \n exit - Programm beenden";
                    break;
                case p:
                    messageToPrint = "\u001B[33m" + "Speichern: " + "\u001B[0m" + " \n b - Zurück \n exit - Programm beenden";
                    break;
                case config:
                    messageToPrint = "\u001B[33m" + "Konfiguration: " + "\u001B[0m" + " \n b - Zurück \n exit - Programm beenden";
                    break;
            }

            this.sendOutPutEvent(messageToPrint, MessageType.normal);

            /* HANDLE NEXT INPUT */
            String inPutText = this.awaitInput();
            handleInput(inPutText, getState());
        }
    }

    private boolean handleInput(String input, ConsoleState consoleState) {
        input = input.toLowerCase();
        if (!this.checkArgumentSize(input, 1, 8))
            return this.sendOutPutEvent("zu wenig / zu viele Anweisungen!", MessageType.error);
        switch (input) {
            case "exit":
                System.exit(0);
            case "b":
                return this.handleBack();
        }
        switch (consoleState) {
            case none:
                handleNoneState(input);
                break;
            case c:
                handleCreateState(input);
                break;
            case r:

                break;
            case u:

                break;
            case d:

                break;
            case p:

                break;
            case ch:
                handleCreateHersteller(input);
                break;
            case ck:

                break;
            case config:

                break;
        }
        return false;
    }

    private boolean handleNoneState(String input) {
        switch (input) {
            case "c":
                return this.changeState(ConsoleState.c);
            case "r":
                return this.changeState(ConsoleState.r);
            case "u":
                return this.changeState(ConsoleState.u);
            case "d":
                return this.changeState(ConsoleState.d);
            case "p":
                return this.changeState(ConsoleState.p);
            case "conf":
                return this.changeState(ConsoleState.config);
            default:
                sendOutPutEvent("Anweisung nicht erkannt", MessageType.error);
                return false;
        }
    }

    private boolean handleCreateState(String input) {
        switch (input) {
            case "h":
                return this.changeState(ConsoleState.ch);
            case "k":
                return this.changeState(ConsoleState.ck);
            default:
                sendOutPutEvent("Anweisung nicht erkannt", MessageType.error);
                return false;
        }
    }

    private boolean handleCreateHersteller(String input) {
        String[] splitText = input.split("\\s+");
        if (splitText.length > 1)
            return sendOutPutEvent("Zu viele Anweisungen! / kein Leerzeichen zu beginn erlaubt ", MessageType.error);
        else if (!checkCharSize(input,3,10)) {
            return sendOutPutEvent("3 - 10 Zeichen erlaubt", MessageType.error);
        };
        Map<DataType, Object> tempMap = new HashMap<>();
        tempMap.put(DataType.hersteller, splitText[0]);
        AutomatEvent automatEvent = new AutomatEvent(this,tempMap,this.getState(), OperationType.cHersteller);
        automatEventHandler.handle(automatEvent);
        return true;
    }

    private boolean checkArgumentSize(String input, int minSize, int maxSize) {
        String[] splitText = input.split("\\s+");
        return (splitText.length >= minSize && splitText.length <= maxSize);
    }

    private boolean checkCharSize(String input, int minSize, int maxSize) {
        return (input.length() >= minSize && input.length() <= maxSize);
    }


    private boolean handleBack() {
        if (this.getState() == ConsoleState.none) {
            sendOutPutEvent("es geht nicht weiter zurück!", MessageType.error);
            return false;
        } else {
            return this.changeState();
        }
    }

    public boolean changeState() {
        this.modeStack.pop();
        return true;
        /*setUpStateChange(this.modeStack.pop(), true);
        setUpStateChange(this.getState(), false);*/
    }

    public boolean changeState(ConsoleState newCState) {
        this.modeStack.push(newCState);
        return true;
       /* setUpStateChange(this.getState(), true);
        setUpStateChange(this.modeStack.push(newCState), false);*/
    }

    /* public InputEventHandler getHandler() {
         return this.handler;
     }

     public void initiate() {
         reader.start();
     }





     public Automat getAutomat() {
         return this.automat;
     }

     public void setUpStateChange(ConsoleState cState, Boolean isPre) {
         switch (cState) {
             case none:
                 if (isPre) handler.remove(lMode);
                 else handler.add(lMode,false);
                 break;
             case c:
                 if (isPre) handler.remove(lCreate);
                 else handler.add(lCreate,false);
                 break;
             case r:
                 if (isPre) handler.remove(lMode);
                 else handler.add(lMode, false);
                 break;
             case u:
                 if (isPre) handler.remove(lMode);
                 else handler.add(lMode, false);
                 break;
             case d:
                 if (isPre) handler.remove(lMode);
                 else handler.add(lMode, false);
                 break;
             case p:
                 if (isPre) handler.remove(lMode);
                 else handler.add(lMode, false);
                 break;
             case ch:
                 if (isPre) handler.remove(lCreateH);
                 else handler.add(lCreateH, false);
                 break;
             case ck:
                 if (isPre) handler.remove(lcreateK);
                 else handler.add(lcreateK, false);
                 break;
             case config:
                 if (isPre) handler.remove(lMode);
                 else handler.add(lMode, false);
                 break;
         }
     }
 */
    public ConsoleState getState() {
        return this.modeStack.peek();
    }
}
