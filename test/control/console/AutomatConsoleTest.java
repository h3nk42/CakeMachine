package control.console;

import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventHandler;
import control.console.input.InputEvent;
import control.console.input.InputEventType;
import control.console.output.MessageType;
import control.console.output.OutputEvent;
import control.console.output.OutputEventHandler;

import control.lib.ConsoleLib;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@DisplayName("AutomatConsole test")
public class AutomatConsoleTest {

    AutomatConsole automatConsole;

    @Spy
    OutputEventHandler outputEventHandlerSpy;
    AutomatEventHandler automatEventHandlerSpy;

    @Mock
    InputEvent inputEventMock;

    @BeforeEach
    void setUp() {
        outputEventHandlerSpy = Mockito.spy(new OutputEventHandler());
        automatEventHandlerSpy = Mockito.spy(new AutomatEventHandler());
        automatConsole = new AutomatConsole(outputEventHandlerSpy, automatEventHandlerSpy);
        inputEventMock = Mockito.mock(InputEvent.class);
    }

    @Test
    @DisplayName("test initial state")
    void handleEventDKuchen3() throws Exception {
        ConsoleState actual = automatConsole.getConsoleState();
        /*ZUSICHERUNG*/
        ConsoleState expected = ConsoleState.none;
        Assertions.assertEquals(actual, expected);

    }

    @Nested
    class stateChangers {
        @BeforeEach
        void setUp() throws Exception {
            when(inputEventMock.getInputEventType()).thenReturn(InputEventType.read);
        }

        @Test
        @DisplayName("HAPPY - handleEvent :c")
        void handleEventRead() throws Exception {
            when(inputEventMock.getText()).thenReturn(":c");
            automatConsole.onInputEvent(inputEventMock);
            ConsoleState actual = automatConsole.getConsoleState();
            /*ZUSICHERUNG*/
            ConsoleState expected = ConsoleState.c;
            Assertions.assertEquals(actual, expected);
        }

        @Test
        @DisplayName("HAPPY - handleEvent :r")
        void handleEventRead2() throws Exception {
            when(inputEventMock.getText()).thenReturn(":r");
            automatConsole.onInputEvent(inputEventMock);
            ConsoleState actual = automatConsole.getConsoleState();
            /*ZUSICHERUNG*/
            ConsoleState expected = ConsoleState.r;
            Assertions.assertEquals(actual, expected);
        }

        @Test
        @DisplayName("HAPPY - handleEvent :u")
        void handleEventRead3() throws Exception {
            when(inputEventMock.getText()).thenReturn(":u");
            automatConsole.onInputEvent(inputEventMock);
            ConsoleState actual = automatConsole.getConsoleState();
            /*ZUSICHERUNG*/
            ConsoleState expected = ConsoleState.u;
            Assertions.assertEquals(actual, expected);
        }

        @Test
        @DisplayName("HAPPY - handleEvent :d")
        void handleEventRead4() throws Exception {
            when(inputEventMock.getText()).thenReturn(":d");
            automatConsole.onInputEvent(inputEventMock);
            ConsoleState actual = automatConsole.getConsoleState();
            /*ZUSICHERUNG*/
            ConsoleState expected = ConsoleState.d;
            Assertions.assertEquals(actual, expected);
        }

        @Test
        @DisplayName("HAPPY - handleEvent :p")
        void handleEventRead5() throws Exception {
            when(inputEventMock.getText()).thenReturn(":p");
            automatConsole.onInputEvent(inputEventMock);
            ConsoleState actual = automatConsole.getConsoleState();
            /*ZUSICHERUNG*/
            ConsoleState expected = ConsoleState.p;
            Assertions.assertEquals(actual, expected);
        }

        @Test
        @DisplayName("UNHAPPY - handleEvent else")
        void handleEventRead6() throws Exception {
            when(inputEventMock.getText()).thenReturn("else");
            automatConsole.onInputEvent(inputEventMock);
            /*ZUSICHERUNG*/
            Mockito.verify(outputEventHandlerSpy, times(1)).handle(new OutputEvent(automatConsole,"Eingabe nicht erkannt!", MessageType.error));
        }

        @Nested
        @DisplayName("stateSpecific - c")
        class stateSpecific {
            @BeforeEach
            void setUp() throws Exception {
                when(inputEventMock.getText()).thenReturn(":c");
                automatConsole.onInputEvent(inputEventMock);
            }
            @Nested
            @DisplayName("hersteller")
            class hersteller {
                @Test
                @DisplayName("UNHAPPY - string too long")
                void handleEventRead7() throws Exception {
                    when(inputEventMock.getText()).thenReturn("raaaaaaaaaaaaaaewe");
                    automatConsole.onInputEvent(inputEventMock);
                    /*ZUSICHERUNG*/
                    Mockito.verify(outputEventHandlerSpy, times(1)).handle(new OutputEvent(automatConsole, "3 - 10 Zeichen erlaubt", MessageType.error));
                }


                @Test
                @DisplayName("HAPPY - success")
                void handleEventRead8() throws Exception {
                    when(inputEventMock.getText()).thenReturn("rewe");
                    automatConsole.onInputEvent(inputEventMock);
                    /*ZUSICHERUNG*/
                    Mockito.verify(automatEventHandlerSpy, times(1)).handle(any(AutomatEvent.class));
                }
            }

            @Nested
            @DisplayName("hersteller")
            class kuchen {

                @Test
                @DisplayName("UNHAPPY - krem/obst fehlt")
                void handleEventRead7() throws Exception {
                    when(inputEventMock.getText()).thenReturn("Obsttorte rewe 7,50 632 24 Gluten Sahne");
                    automatConsole.onInputEvent(inputEventMock);
                    /*ZUSICHERUNG*/
                    Mockito.verify(outputEventHandlerSpy, times(1)).handle(new OutputEvent(automatConsole, "Kuchenart passt nicht zur ArgumentAnzahl", MessageType.error));
                }

                @Test
                @DisplayName("UNHAPPY - krem/obst zu viel")
                void handleEventRead71() throws Exception {
                    when(inputEventMock.getText()).thenReturn("Kremkuchen rewe 7,50 632 24 Gluten Sahne Apfel");
                    automatConsole.onInputEvent(inputEventMock);
                    /*ZUSICHERUNG*/
                    Mockito.verify(outputEventHandlerSpy, times(1)).handle(new OutputEvent(automatConsole, "Kuchenart passt nicht zur ArgumentAnzahl", MessageType.error));
                }

                @Test
                @DisplayName("UNHAPPY - krem/obst zu viel")
                void handleEventRead721() throws Exception {
                    when(inputEventMock.getText()).thenReturn("obstkuchen rewe 7,50 632 24 Gluten Sahne Apfel");
                    automatConsole.onInputEvent(inputEventMock);
                    /*ZUSICHERUNG*/
                    Mockito.verify(outputEventHandlerSpy, times(1)).handle(new OutputEvent(automatConsole, "Kuchenart passt nicht zur ArgumentAnzahl", MessageType.error));
                }

                @Test
                @DisplayName("UNHAPPY - naehrwert no number")
                void handleEventCreate3() throws Exception {
                    when(inputEventMock.getText()).thenReturn("Obsttorte rewe 7,50 632s 24 Gluten Apfel Sahne");
                    automatConsole.onInputEvent(inputEventMock);
                    /*ZUSICHERUNG*/
                    Mockito.verify(outputEventHandlerSpy, times(1)).handle(new OutputEvent(automatConsole, "Nährwert nicht erkannt - erlaubtes Format: <ZAHL> keine zeichen, maximal 5 Zahlen", MessageType.error));
                }

                @Test
                @DisplayName("UNHAPPY - haltbarkeit no number")
                void handleEventCreate6() throws Exception {
                    when(inputEventMock.getText()).thenReturn("Obsttorte rewe 7,50 632 24s Gluten Apfel Sahne");
                    automatConsole.onInputEvent(inputEventMock);
                    /*ZUSICHERUNG*/
                    Mockito.verify(outputEventHandlerSpy, times(1)).handle(new OutputEvent(automatConsole, "Haltbarkeit nicht erkannt - erlaubtes Format: <ZAHL> keine zeichen, maximal 3 Zahlen", MessageType.error));
                }

                @Test
                @DisplayName("HAPPY - success")
                void handleEventRead8() throws Exception {
                    when(inputEventMock.getText()).thenReturn("Obsttorte rewe 7,50 632 24 Gluten Apfel Sahne");
                    automatConsole.onInputEvent(inputEventMock);
                    /*ZUSICHERUNG*/
                    Mockito.verify(automatEventHandlerSpy, times(1)).handle(any(AutomatEvent.class));
                }


            }
            @Test
            @DisplayName("UNHAPPY - wrongArgumentCount")
            void handleEventRead6() throws Exception {
                when(inputEventMock.getText()).thenReturn("eins zwei drei");
                automatConsole.onInputEvent(inputEventMock);
                /*ZUSICHERUNG*/
                Mockito.verify(outputEventHandlerSpy, times(1)).handle(new OutputEvent(automatConsole, ConsoleLib.argumentCountWrong, MessageType.error));
            }
        }

        @Nested
        @DisplayName("stateSpecific - r")
        class stateSpecificR {
            @BeforeEach
            void setUp() throws Exception {
                when(inputEventMock.getText()).thenReturn(":r");
                automatConsole.onInputEvent(inputEventMock);
            }
            @Nested
            @DisplayName("hersteller")
            class hersteller {
                @Test
                @DisplayName("HAPPY - success")
                void handleEventRead6() throws Exception {
                    when(inputEventMock.getText()).thenReturn("hersteller");
                    automatConsole.onInputEvent(inputEventMock);
                    /*ZUSICHERUNG*/
                    Mockito.verify(automatEventHandlerSpy, times(1)).handle(any(AutomatEvent.class));
                }
            }

            @Nested
            @DisplayName("hersteller")
            class kuchen {
                @Test
                @DisplayName("HAPPY - success")
                void handleEventRead6() throws Exception {
                    when(inputEventMock.getText()).thenReturn("kuchen");
                    automatConsole.onInputEvent(inputEventMock);
                    /*ZUSICHERUNG*/
                    Mockito.verify(automatEventHandlerSpy, times(1)).handle(any(AutomatEvent.class));
                }
            }

            @Test
            @DisplayName("UNHAPPY - wrongArgumentCount")
            void handleEventRead6() throws Exception {
                when(inputEventMock.getText()).thenReturn("eins zwei drei");
                automatConsole.onInputEvent(inputEventMock);
                /*ZUSICHERUNG*/
                Mockito.verify(outputEventHandlerSpy, times(1)).handle(new OutputEvent(automatConsole, ConsoleLib.argumentCountWrong, MessageType.error));
            }
        }

        @Nested
        @DisplayName("stateSpecific - d")
        class stateSpecificD {
            @BeforeEach
            void setUp() throws Exception {
                when(inputEventMock.getText()).thenReturn(":d");
                automatConsole.onInputEvent(inputEventMock);
            }

            @Nested
            @DisplayName("kuchen")
            class kuchen {
                @Test
                @DisplayName("HAPPY - success")
                void handleEventDelete() throws Exception {
                    when(inputEventMock.getText()).thenReturn("0");
                    automatConsole.onInputEvent(inputEventMock);
                    /*ZUSICHERUNG*/
                    Mockito.verify(automatEventHandlerSpy, times(1)).handle(any(AutomatEvent.class));
                }

                @Test
                @DisplayName("UNHAPPY - fachNummer too long")
                void handleEventDelete2() throws Exception {
                    when(inputEventMock.getText()).thenReturn("11111");
                    automatConsole.onInputEvent(inputEventMock);
                    /*ZUSICHERUNG*/
                    Mockito.verify(outputEventHandlerSpy, times(1)).handle(new OutputEvent(automatConsole, "1 - 3 Zeichen erlaubt", MessageType.error));
                }

            }

            @Nested
            @DisplayName("hersteller")
            class hersteller {
                @Test
                @DisplayName("HAPPY - success")
                void handleEventRead6() throws Exception {
                    when(inputEventMock.getText()).thenReturn("rewe");
                    automatConsole.onInputEvent(inputEventMock);
                    /*ZUSICHERUNG*/
                    Mockito.verify(automatEventHandlerSpy, times(1)).handle(any(AutomatEvent.class));
                }
            }

            @Test
            @DisplayName("UNHAPPY - wrongArgumentCount")
            void handleEventRead6() throws Exception {
                when(inputEventMock.getText()).thenReturn("eins zwei");
                automatConsole.onInputEvent(inputEventMock);
                /*ZUSICHERUNG*/
                Mockito.verify(outputEventHandlerSpy, times(1)).handle(new OutputEvent(automatConsole, ConsoleLib.argumentCountWrong, MessageType.error));
            }
        }
        @Nested
        @DisplayName("stateSpecific - p")
        class stateSpecificP {
            @BeforeEach
            void setUp() throws Exception {
                when(inputEventMock.getText()).thenReturn(":p");
                automatConsole.onInputEvent(inputEventMock);
            }

            @Test
            @DisplayName("HAPPY - savejos ")
            void handleEventPersist1() throws Exception {
                when(inputEventMock.getText()).thenReturn("savejos");
                automatConsole.onInputEvent(inputEventMock);
                /*ZUSICHERUNG*/
                Mockito.verify(automatEventHandlerSpy, times(1)).handle(any(AutomatEvent.class));
            }

            @Test
            @DisplayName("HAPPY - loadjos ")
            void handleEventPersist2() throws Exception {
                when(inputEventMock.getText()).thenReturn("loadjos");
                automatConsole.onInputEvent(inputEventMock);
                /*ZUSICHERUNG*/
                Mockito.verify(automatEventHandlerSpy, times(1)).handle(any(AutomatEvent.class));
            }

            @Test
            @DisplayName("UNHAPPY - wrongArgumentCount")
            void handleEventRead6() throws Exception {
                when(inputEventMock.getText()).thenReturn("eins zwei");
                automatConsole.onInputEvent(inputEventMock);
                /*ZUSICHERUNG*/
                Mockito.verify(outputEventHandlerSpy, times(1)).handle(new OutputEvent(automatConsole, ConsoleLib.argumentCountWrong, MessageType.error));
            }

        }

        @Nested
        @DisplayName("stateSpecific - u")
        class stateSpecificU {
            @BeforeEach
            void setUp() throws Exception {
                when(inputEventMock.getText()).thenReturn(":u");
                automatConsole.onInputEvent(inputEventMock);
            }

            @Test
            @DisplayName("HAPPY - updateKuchen")
            void handleEventUpdate6() throws Exception {
                when(inputEventMock.getText()).thenReturn("0");
                automatConsole.onInputEvent(inputEventMock);
                /*ZUSICHERUNG*/
                Mockito.verify(automatEventHandlerSpy, times(1)).handle(any(AutomatEvent.class));
            }

            @Test
            @DisplayName("UNHAPPY - updateKuchen no number")
            void handleEventUpdate7() throws Exception {
                when(inputEventMock.getText()).thenReturn("tooet");
                automatConsole.onInputEvent(inputEventMock);
                /*ZUSICHERUNG*/
                Mockito.verify(outputEventHandlerSpy, times(1)).handle(new OutputEvent(automatConsole, "keine Nummer eingeben", MessageType.error));
            }

            @Test
            @DisplayName("UNHAPPY - wrongArgumentCount")
            void handleEventRead6() throws Exception {
                when(inputEventMock.getText()).thenReturn("eins zwei");
                automatConsole.onInputEvent(inputEventMock);
                /*ZUSICHERUNG*/
                Mockito.verify(outputEventHandlerSpy, times(1)).handle(new OutputEvent(automatConsole, ConsoleLib.argumentCountWrong, MessageType.error));
            }
        }

    }


    @Nested
    class print {
        @BeforeEach
        void setUp() throws Exception {
            when(inputEventMock.getInputEventType()).thenReturn(InputEventType.print);
        }
        @Test
        @DisplayName("HAPPY - initialPrint")
        void handleEventRead6() throws Exception {
            when(inputEventMock.getText()).thenReturn("eins zwei");
            automatConsole.onInputEvent(inputEventMock);
            /*ZUSICHERUNG*/
            Mockito.verify(outputEventHandlerSpy, times(1)).handle(new OutputEvent(automatConsole, System.lineSeparator()+"\u001B[33m" + "Modus wechseln:" + "\u001B[0m" + " "+System.lineSeparator()+" :c - Einfügen "+System.lineSeparator()+" :r - Anzeigen"+System.lineSeparator()+" :u - Ändern"+System.lineSeparator()+" :d - Löschen"+System.lineSeparator()+" :p - Speichern"+System.lineSeparator()+" exit - Programm beenden", MessageType.normal));
        }

        @Nested
        @DisplayName("stateSpecific - u")
        class stateSpecificU {
            @BeforeEach
            void setUp() throws Exception {
                when(inputEventMock.getText()).thenReturn(":r");
                automatConsole.onInputEvent(inputEventMock);
            }
        }
    }
}
