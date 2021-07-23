package control.automat;

import control.automat.events.AutomatEvent;
import control.automat.observers.CreateDeleteHerstellerObserver;
import control.console.output.MessageType;
import control.console.output.OutputEvent;
import control.console.output.OutputEventHandler;
import control.gui.event.UpdateGuiEventHandler;
import model.Automat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@DisplayName("AutomatController Test")
public class AutomatControllerTest {

        @Spy
        AutomatController spyAutomatController;
        Automat spyAutomat;
        Automat spyAutomat2;

        @BeforeEach
        void setUp() throws Exception {
            spyAutomat = Mockito.spy(new Automat(10));
            spyAutomat2 = Mockito.spy(new Automat(10));
            spyAutomatController = Mockito.spy(new AutomatController(spyAutomat));


        }

    @Test
    @DisplayName("HAPPY - rehydrate test automat set")
    void rehydrate4() throws Exception {
        spyAutomatController.rehydrate(spyAutomat2);
        Automat actual = spyAutomatController.getAutomat();
        /*ZUSICHERUNG*/
        Automat excpected = spyAutomat2;
        Assertions.assertEquals(actual, excpected);
    }

    @Test
    @DisplayName("HAPPY - rehydrate test allergene refresh")
    void rehydrate() throws Exception {
            spyAutomatController.rehydrate(spyAutomat2);
        /*ZUSICHERUNG*/
        Mockito.verify(spyAutomatController, times(1)).aktualisiereAllergene();
    }  @Test
    @DisplayName("HAPPY - rehydrate test capacity refresh")
    void rehydrate2() throws Exception {
            spyAutomatController.rehydrate(spyAutomat2);
        /*ZUSICHERUNG*/
        Mockito.verify(spyAutomatController, times(1)).aktualisiereKuchenCapacity();
    }  @Test
    @DisplayName("HAPPY - rehydrate test benachrichtige")
    void rehydrate3() throws Exception {
            spyAutomatController.rehydrate(spyAutomat2);
        /*ZUSICHERUNG*/
        Mockito.verify(spyAutomatController, times(3)).benachrichtige();
    }

    @Test
    @DisplayName("HAPPY - aktualisiereKuchenKapacity")
    void kuchenCapacity() throws Exception {
        spyAutomatController.aktualisiereKuchenCapacity();
        double actual = spyAutomatController.getCapacity();
        /*ZUSICHERUNG*/
        double excpected = 0;
        Assertions.assertEquals(actual, excpected);
    }

    @Test
    @DisplayName("HAPPY - aktualisiereHersteller")
    void herstellerCount() throws Exception {
        spyAutomatController.aktualisiereHersteller();
        /*ZUSICHERUNG*/
        Mockito.verify(spyAutomatController, times(1)).benachrichtige();
    }

    @Test
    @DisplayName("HAPPY - meldeAn")
    void meldeAn() throws Exception {
        spyAutomatController.meldeAn(Mockito.mock(Observer.class));
        int actual = spyAutomatController.getBeobachter().size();
        /*ZUSICHERUNG*/
        double excpected = 1;
        Assertions.assertEquals(actual, excpected);
    }

    @Test
    @DisplayName("HAPPY - meldeAb")
    void meldeAb() throws Exception {
            Observer obServerMock = Mockito.mock(Observer.class);
        spyAutomatController.meldeAn(obServerMock);
        spyAutomatController.meldeAb(obServerMock);
        int actual = spyAutomatController.getBeobachter().size();
        /*ZUSICHERUNG*/
        double excpected = 0;
        Assertions.assertEquals(actual, excpected);
    }

    @Test
    @DisplayName("HAPPY - benachrichtige")
    void benachrichtige() throws Exception {
        Observer obServerMock = Mockito.mock(Observer.class);
        spyAutomatController.meldeAn(obServerMock);
        int actual = spyAutomatController.getBeobachter().size();
        /*ZUSICHERUNG*/
        double excpected = 1;
        Assertions.assertEquals(actual, excpected);
    }

    @Test
    @DisplayName("HAPPY - getCapacity")
    void getCapacity() throws Exception {
        Observer obServerMock = Mockito.mock(Observer.class);
        spyAutomatController.meldeAn(obServerMock);
        double actual = spyAutomatController.getCapacity();
        /*ZUSICHERUNG*/
        double excpected = 0;
        Assertions.assertEquals(actual, excpected);
    }



}
