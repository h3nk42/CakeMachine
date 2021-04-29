package control.automat.observers;

public interface Subjekt {
    void meldeAn(Observer beobachter);
    void meldeAb(Observer beobachter);
    void benachrichtige();
}
