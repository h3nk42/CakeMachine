package control.lib;

import control.automat.AutomatController;
import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventHandler;
import control.automat.events.CakeDataType;
import model.Automat;
import model.verkaufsobjekte.Allergen;
import model.verkaufsobjekte.kuchen.KuchenArt;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.Spy;
import simulations.LockWrapper;
import simulations.SimulationType;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@DisplayName("Persist Tests")
    public class PersistLibTest {

        @Spy
        AutomatController automatControllerSpy;


        Automat automat;

        @BeforeEach
        void setUp() throws Exception {
            automat = new Automat(10);
            automatControllerSpy = Mockito.spy(new AutomatController(automat));
        }

        @DisplayName("Save Tests")
        @Nested
         class saveTests {

            @Test
            @DisplayName("writeAutomatToFile")
            void writeToFileTest() throws Exception {
                Map<CakeDataType, Object> randomMap = SimulationLib.rollCake(new Random());
                automat.createHersteller("rewe");
                automat.createHersteller("lidl");
                automat.createHersteller("frodo");
                randomMap.put(CakeDataType.kuchenart, KuchenArt.Kremkuchen);
                automat.createKuchen((KuchenArt) randomMap.get(CakeDataType.kuchenart),
                        automat.getHersteller( (String) randomMap.get(CakeDataType.hersteller)),
                        (BigDecimal) randomMap.get(CakeDataType.preis),
                        (int) randomMap.get(CakeDataType.naehrwert),
                        (Allergen[]) randomMap.get(CakeDataType.allergene),
                        new String[]{(String) randomMap.get(CakeDataType.kremsorte)},
                        (int) randomMap.get(CakeDataType.haltbarkeit));
                PersistLib.writeAutomatToFileJOS("testFile.ser", automat, false);
                Automat justReadAutomat = PersistLib.readAutomatFromFileJOS("testFile.ser");
                boolean actual = automat.equals(justReadAutomat);
                /* ZUSICHERUNG */
                boolean expected = true;
                Assertions.assertEquals(expected,actual);
            }
        }

}
