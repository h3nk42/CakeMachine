package control.console.input;

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
