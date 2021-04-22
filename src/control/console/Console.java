package control.console;

import view.consoleReader.*;

import java.util.Stack;


public class Console {
    private final Reader reader;
    private final InputEventListenerPrint lPrint;
    private final InputEventListenerConstant lConst;
    private final InputEventListenerMode lMode;
    private final InputEventListenerCreate lCreate;
    private InputEventHandler handler;
    private Stack<ConsoleState> modeStack;


    public Console() {
        reader = new Reader(this);
        handler = new InputEventHandler();
        lPrint = new InputEventListenerPrint();
        lConst = new InputEventListenerConstant(this);
        lMode = new InputEventListenerMode(this);
        lCreate = new InputEventListenerCreate();
        modeStack = new Stack<>();
        modeStack.push(ConsoleState.none);
        handler.add(lMode,true);
        handler.add(lConst,true);
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
