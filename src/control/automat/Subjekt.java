package control.automat;

import control.automat.Observer;

public interface Subjekt {
    void meldeAn(Observer beobachter);
    void meldeAb(Observer beobachter);
    void benachrichtige();
}
