package control.console;

import control.automat.Automat;
import view.consoleReader.*;
import view.consoleReader.Create.InputEventListenerCreate;
import view.consoleReader.Create.InputEventListenerCreateHersteller;
import view.consoleReader.Create.InputEventListenerCreateKuchen;

import java.util.Stack;


public class Console {
    private final Reader reader;
    private final InputEventListenerPrint lPrint;
    private final InputEventListenerConstant lConst;
    private final InputEventListenerMode lMode;
    private final InputEventListenerCreate lCreate;
    private final InputEventListenerCreateHersteller lCreateH;
    private final InputEventListenerCreateKuchen lcreateK;
    private InputEventHandler handler;
    private Stack<ConsoleState> modeStack;
    private Automat a;


    public Console() {
        reader = new Reader(this);
        handler = new InputEventHandler();
        lPrint = new InputEventListenerPrint();
        lConst = new InputEventListenerConstant(this);
        lMode = new InputEventListenerMode(this);
        lCreate = new InputEventListenerCreate(this);
        lCreateH = new InputEventListenerCreateHersteller(this);
        lcreateK = new InputEventListenerCreateKuchen(this);
        a = new Automat(10);
        modeStack = new Stack<>();
        modeStack.push(ConsoleState.none);
        handler.add(lConst,true);
        handler.add(lMode,true);
    }

    public InputEventHandler getHandler() {
        return this.handler;
    }

    public void initiate() {
        reader.start();
    }

    public void changeState(ConsoleState newCState) {
        setUpStateChange(this.getState(), true);
        setUpStateChange(this.modeStack.push(newCState), false);
    }

    public void changeState() {
        setUpStateChange(this.modeStack.pop(), true);
        setUpStateChange(this.getState(), false);
    }

    public Automat getAutomat() {
        return this.a;
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

    public ConsoleState getState() {
        return this.modeStack.peek();
    }
}
