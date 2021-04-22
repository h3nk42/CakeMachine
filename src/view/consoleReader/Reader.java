package view.consoleReader;

import control.console.Console;
import control.console.ConsoleState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Reader {
    private Console c;

    public Reader(Console c) {
        this.c = c;
    }

    public void start() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ) {
            while (true) {
                while (c.getState() == ConsoleState.none) {
                    System.out.println("\u001B[33m"+"Modus wählen:" + "\u001B[0m"+ " \n c - Einfügen \n r - Anzeigen\n u - Ändern\n d - Löschen\n p - Speichern \n conf - Konfiguration");
                    String text = br.readLine();
                    InputEvent e = new InputEvent(this, text);
                    InputEventHandler tempHandler = c.getHandler();
                    if (null != tempHandler) tempHandler.handle(e);
                }
                while (c.getState() == ConsoleState.r) {
                    System.out.println("Enter text:");
                    String text = br.readLine();
                    InputEvent e = new InputEvent(this, text);
                    InputEventHandler tempHandler = c.getHandler();
                    if (null != tempHandler) tempHandler.handle(e);
                }

                while (c.getState() == ConsoleState.c) {
                    System.out.println("\u001B[33m"+"Einfügen: " + "\u001B[0m"+ " \n h - Hersteller \n k - Kuchen \n b - Zurück");
                    String text = br.readLine();
                    InputEvent e = new InputEvent(this, text);
                    InputEventHandler tempHandler = c.getHandler();
                    if (null != tempHandler) tempHandler.handle(e);
                }
                while (c.getState() == ConsoleState.ch) {
                    System.out.println("\u001B[33m"+"Hersteller Einfügen: " + "\u001B[0m"+ " \n <herstellerName> \n b - Zurück");
                    String text = br.readLine();
                    InputEvent e = new InputEvent(this, text);
                    InputEventHandler tempHandler = c.getHandler();
                    if (null != tempHandler) tempHandler.handle(e);
                }
                while (c.getState() == ConsoleState.ck) {
                    System.out.println("\u001B[33m"+"Kuchen Einfügen: " + "\u001B[0m"+ " \n <kuchenTyp> <herstellername> <preis> <nährwert> <haltbarkeit> <allergen,..> <kremsorte> oder/und <obstsorte> \n b - Zurück");
                    String text = br.readLine();
                    InputEvent e = new InputEvent(this, text);
                    InputEventHandler tempHandler = c.getHandler();
                    if (null != tempHandler) tempHandler.handle(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
