package model.hersteller;
import java.util.HashMap;

public interface HerstellerFactory {

    Hersteller produceHersteller(String name) throws Exception;

    HashMap<String, Hersteller> getHerstellerList();

    void deleteHersteller(String herstellerName) throws Exception;

}
