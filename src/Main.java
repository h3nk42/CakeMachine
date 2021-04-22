import control.automat.Automat;
import control.console.Console;
import view.consoleReader.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Automat automat = new Automat(5);
        Console c = new Console();
        c.initiate();
    }
}

