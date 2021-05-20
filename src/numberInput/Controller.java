package numberInput;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.text.NumberFormat;
import java.util.Locale;

public class Controller {
    @FXML private Spinner spinner;
    @FXML private Slider slider;
    @FXML private TextField textField;
    @FXML private Label label;

    private SimpleDoubleProperty blurRadius=new SimpleDoubleProperty();
    public DoubleProperty blurRadiusProperty(){return this.blurRadius;}
    public Double getBlurRadius(){return this.blurRadius.get();}

    public void initialize(){
        this.spinner.getValueFactory().valueProperty().bindBidirectional(this.blurRadius);
        this.slider.valueProperty().bindBidirectional(this.blurRadius);
        NumberFormat nf=NumberFormat.getInstance(Locale.GERMANY);
        nf.setMaximumFractionDigits(2);
        this.label.textProperty().bindBidirectional(this.blurRadius,nf);
        //this.textField.textProperty().bindBidirectional(this.blurRadius,nf);
        this.textField.textProperty().bindBidirectional(this.blurRadius,new StringConverter<Number>(){
            @Override
            public String toString(Number object) {
                return object.toString();
            }
            @Override
            public Number fromString(String string) {
                double v=0;
                if(string.isEmpty()){
                    textField.setStyle("");
                    return v;
                }
                try {
                    v = Double.parseDouble(string);
                    if(10<v||0>v) textField.setStyle("-fx-background-color: Red;");
                    else textField.setStyle("");
                }catch (NumberFormatException e){
                    textField.setStyle("-fx-background-color: Red;");
                }
                return v;
            }
        });
    }
}
