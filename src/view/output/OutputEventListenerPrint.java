package view.output;

public class OutputEventListenerPrint implements OutputEventListener {
    private final Output out;

    public OutputEventListenerPrint(Output out) {
        this.out = out;
    }

    @Override
    public void onOutputEvent(OutputEvent event) {
        if (null != event.getText()) {
            switch(event.getType()) {
                case normal:
                    out.printLine(event.getText());
                    break;
                case error:
                    out.printLine("\u001B[31m \n --- " + event.getText() + " --- \n \u001B[0m");
                    break;
                case success:
                    out.printLine("\u001B[36m \n --- " + event.getText() + " --- \n \u001B[0m");
                    break;
                case warning:
                    out.printLine("\u001B[33m \n --- " + event.getText() + " --- \n \u001B[0m");
                    break;
            }
        }
    }
}
