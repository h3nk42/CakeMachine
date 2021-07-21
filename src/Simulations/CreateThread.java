package Simulations;

public class CreateThread extends Thread {

    private LockWrapper wrapper;
    private SimulationType simulationType;

    public CreateThread(LockWrapper wrapper, SimulationType simulationType) {
        this.wrapper = wrapper;
        this.simulationType = simulationType;
    }


    public void run() {
        while(true){
            try {
                switch (simulationType){
                    case sim1:
                        wrapper.createCakeUnsynchronized();
                        break;
                    case sim2:
                    case sim3:
                        wrapper.createCakeSynchronized();
                        break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
