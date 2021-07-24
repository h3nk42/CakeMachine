package control.lib;

import control.automat.AutomatController;
import control.automat.events.AutomatEventHandler;
import model.Automat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import org.mockito.Spy;
import simulations.LockWrapper;
import simulations.SimulationType;

@DisplayName("Simulations Tests")
    public class PersistLibTest {
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

            lockWrapper.createCakeUnsynchronized(SimulationType.sim1, true);
        }
}
