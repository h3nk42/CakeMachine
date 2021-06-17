package model.automat.hersteller;
import java.util.HashMap;

public interface HerstellerFactory {

    Hersteller produceHersteller(String name) throws Exception;

    HashMap<String, Hersteller> getHerstellerListe();

    void deleteHersteller(String herstellerName);

}
