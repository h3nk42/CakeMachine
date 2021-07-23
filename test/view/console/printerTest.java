package view.console;

import model.Automat;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@DisplayName("Printer Test")
class printerTest {
    Printer printer;
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();


    @BeforeEach
            void setUp() throws Exception {
                printer = new Printer();
                System.setOut(new PrintStream(outContent));
            }

            @Test
            @DisplayName("printLine test")
            void printLineTest() {
                printer.printLine("test");
                String actual = outContent.toString();
                /* ZUSICHERUNG */
                String expected = "test" + System.lineSeparator();
                Assertions.assertEquals(expected, actual);
            }
}
