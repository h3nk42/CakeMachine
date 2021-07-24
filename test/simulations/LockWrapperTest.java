package simulations;

import control.automat.AutomatController;
import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventHandler;
import control.automat.events.CakeDataType;
import model.Automat;
import model.hersteller.Hersteller;
import model.verkaufsobjekte.Allergen;
import model.verkaufsobjekte.kuchen.KuchenArt;
import model.verkaufsobjekte.kuchen.VerkaufsKuchen;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


/*
* doppelte zusicherung um SPY Parameter abzufangen
* */
@DisplayName("Simulations Tests")
public class LockWrapperTest {

    LockWrapper lockWrapper;

    @Spy
    AutomatEventHandler automatEventHandlerSpy;
    AutomatController automatControllerSpy;
    Automat automatSpy;

    @BeforeEach
    void setUp() throws Exception {
        automatSpy = Mockito.spy(new Automat(10));
        automatControllerSpy = Mockito.spy(new AutomatController(automatSpy));
        automatEventHandlerSpy = Mockito.spy(new AutomatEventHandler());
        lockWrapper = new LockWrapper(automatControllerSpy, automatEventHandlerSpy);

        lockWrapper.createCakeUnsynchronized(SimulationType.sim1,true);
    }

    @Nested
    @DisplayName("Create tests")
    class simCreateTests {
        final ArgumentCaptor<AutomatEvent> captor= ArgumentCaptor.forClass(AutomatEvent.class);

        @BeforeEach
        void setUp() throws Exception {
            lockWrapper = new LockWrapper(automatControllerSpy, automatEventHandlerSpy);
        }
        @Test
        @DisplayName("automatEvent is getting called")
        void createDeterministicCake() throws Exception {
            lockWrapper.createCakeUnsynchronized(SimulationType.sim1,true);
            /* ZUSICHERUNG */
            Mockito.verify(automatEventHandlerSpy, times(2)).handle(any(AutomatEvent.class));
        }

        /*
         * doppelte zusicherung um SPY Parameter abzufangen GILT FUER GANZEN FILE
         * */
        @Test
        @DisplayName("cake creation is deterministic but \"random\"")
        void createDeterministicCakeExceptMap() throws Exception {
            lockWrapper.createCakeUnsynchronized(SimulationType.sim1, true);
            Mockito.verify(automatEventHandlerSpy, times(2)).handle(captor.capture());
            List<AutomatEvent> capturedEvents = captor.getAllValues();
            boolean actual = capturedEvents.get(0).getData().equals(capturedEvents.get(1).getData());
            /* ZUSICHERUNG */
            boolean expected = true;
            Assertions.assertEquals(expected, actual);
        }
    }

    /*
     * doppelte zusicherung um SPY Parameter abzufangen GILT FUER GANZEN FILE
     * */
    @Nested
    @DisplayName("Delete tests")
    class simDeleteTests {
        final ArgumentCaptor<AutomatEvent> captor= ArgumentCaptor.forClass(AutomatEvent.class);

        @BeforeEach
        void setUp() throws Exception {
            List<VerkaufsKuchen> tempList = new ArrayList<>();
            VerkaufsKuchen verkaufsKuchenMock = Mockito.mock(VerkaufsKuchen.class);
            when(verkaufsKuchenMock.getFachnummer()).thenReturn(0);
            when(verkaufsKuchenMock.getInspektionsdatum()).thenReturn(new Date());
            tempList.add(verkaufsKuchenMock);
            VerkaufsKuchen verkaufsKuchenMock2 = Mockito.mock(VerkaufsKuchen.class);
            when(verkaufsKuchenMock2.getFachnummer()).thenReturn(1);
            when(verkaufsKuchenMock2.getInspektionsdatum()).thenReturn(new Date(Instant.now().plusSeconds(1).getEpochSecond()*1000));
            tempList.add(verkaufsKuchenMock2);
            VerkaufsKuchen verkaufsKuchenMock3 = Mockito.mock(VerkaufsKuchen.class);
            when(verkaufsKuchenMock3.getFachnummer()).thenReturn(2);
            when(verkaufsKuchenMock3.getInspektionsdatum()).thenReturn(new Date(Instant.now().plusSeconds(2).getEpochSecond()*1000));
            tempList.add(verkaufsKuchenMock3);

            doReturn(tempList).when(automatSpy).getKuchen();
            lockWrapper = new LockWrapper(automatControllerSpy, automatEventHandlerSpy);
            lockWrapper.deleteCakeUnsynchronized(SimulationType.sim1, true);
        }

        @Test
        @DisplayName("cake deletion is sending an Event ")
        void deleteDeterministicCake() throws Exception {
            /* ZUSICHERUNG */
            Mockito.verify(automatEventHandlerSpy, times(2)).handle(any(AutomatEvent.class));
        }

        /*
         * doppelte zusicherung um SPY Parameter abzufangen GILT FUER GANZEN FILE
         * */
        @Test
        @DisplayName("cake deletion is deterministic but \"random\" ")
        void deleteDeterministicCakeCapture() throws Exception {
            lockWrapper = new LockWrapper(automatControllerSpy, automatEventHandlerSpy);
            lockWrapper.deleteCakeUnsynchronized(SimulationType.sim1, true);
            Mockito.verify(automatEventHandlerSpy, times(3)).handle(captor.capture());
            List<AutomatEvent> capturedEvents = captor.getAllValues();
            boolean actual = capturedEvents.get(1).getData().equals(capturedEvents.get(2).getData());
            /* ZUSICHERUNG */
            boolean expected = true;
            Assertions.assertEquals(expected, actual);
        }

        /*
         * doppelte zusicherung um SPY Parameter abzufangen GILT FUER GANZEN FILE
         * */
        @Test
        @DisplayName("cake deletion is deleting oldest ")
        void deleteDeterministicCakeOldest() throws Exception {
            lockWrapper = new LockWrapper(automatControllerSpy, automatEventHandlerSpy);
            lockWrapper.deleteCakeUnsynchronized(SimulationType.sim2, true);
            Mockito.verify(automatEventHandlerSpy, times(3)).handle(captor.capture());
            List<AutomatEvent> capturedEvents = captor.getAllValues();
            boolean actual = (int) capturedEvents.get(2).getData().get(CakeDataType.fachnummer) == 2;
             /*ZUSICHERUNG*/
            boolean expected = true;
            Assertions.assertEquals(expected, actual);
        }

        /*
         * doppelte zusicherung um SPY Parameter abzufangen GILT FUER GANZEN FILE
         * */
        @Test
        @DisplayName("cake deletion is deleting oldest multiple deterministic ")
        void deleteDeterministicCakeOldestMultiple() throws Exception {
            lockWrapper = new LockWrapper(automatControllerSpy, automatEventHandlerSpy);
            lockWrapper.deleteCakeSynchronized(SimulationType.sim3, true);
            Mockito.verify(automatEventHandlerSpy, times(4)).handle(captor.capture());
            List<AutomatEvent> capturedEvents = captor.getAllValues();
            boolean actual = (int) capturedEvents.get(2).getData().get(CakeDataType.fachnummer) == 2;
            /*ZUSICHERUNG*/
            boolean expected = true;
            Assertions.assertEquals(expected, actual);
        }

        @Nested
        @DisplayName("Inspect tests")
        class simInspectTests {
            @BeforeEach
            void setUp() throws Exception {
                lockWrapper = new LockWrapper(automatControllerSpy, automatEventHandlerSpy);
                lockWrapper.inspectUnsynchronized();
            }

            @Test
            @DisplayName("cake inspection is sending an Event ")
            void deleteDeterministicCake() throws Exception {
                /* ZUSICHERUNG */
                Mockito.verify(automatEventHandlerSpy, times(3)).handle(any(AutomatEvent.class));
            }

            @Test
            @DisplayName("cake inspection is deterministic but \"random\" ")
            void createDeterministicCakeCapture() throws Exception {
                lockWrapper = new LockWrapper(automatControllerSpy, automatEventHandlerSpy);
                lockWrapper.inspectUnsynchronized();
                Mockito.verify(automatEventHandlerSpy, times(4)).handle(captor.capture());
                List<AutomatEvent> capturedEvents = captor.getAllValues();
                boolean actual = capturedEvents.get(2).getData().equals(capturedEvents.get(3).getData());
                /* ZUSICHERUNG */
                boolean expected = true;
                Assertions.assertEquals(expected, actual);
            }
        }
    }


}
