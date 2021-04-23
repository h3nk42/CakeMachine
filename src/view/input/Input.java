package view.input;

import view.consoleReader.InputEvent;
import view.consoleReader.InputEventHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Input {
    public String awaitInput() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String text = null;
        try {
            text = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
