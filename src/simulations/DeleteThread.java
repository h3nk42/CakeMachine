package simulations;

public class DeleteThread extends Thread {

    private LockWrapper wrapper;
    private SimulationType simulationType;

    public DeleteThread(LockWrapper wrapper, SimulationType simulationType) {
        this.wrapper = wrapper;
        this.simulationType = simulationType;
    }


    public void run() {
        while(true){
            try {
                switch (simulationType){
                    case sim1:
                        wrapper.deleteCakeUnsynchronized(simulationType, false);
                        break;
                    case sim2:
                    case sim3:
                        wrapper.deleteCakeSynchronized(simulationType, false);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
