package simulations;

public class InspectThread extends Thread {

    private LockWrapper wrapper;
    private SimulationType simulationType;

    public InspectThread(LockWrapper wrapper, SimulationType simulationType) {
        this.wrapper = wrapper;
        this.simulationType = simulationType;
    }


    public void run() {
        while(true){
            try {
                switch (simulationType){
                    case sim1:
                        break;
                    case sim2:
                        wrapper.inspectUnsynchronized();
                        break;
                    case sim3:
                        wrapper.inspectUnsynchronized();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
