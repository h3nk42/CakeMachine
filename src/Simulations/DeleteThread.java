package Simulations;

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
                        wrapper.deleteCakeUnsynchronized(simulationType);
                        break;
                    case sim2:
                    case sim3:
                        wrapper.deleteCakeSynchronized(simulationType);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
