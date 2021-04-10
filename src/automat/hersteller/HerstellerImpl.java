package automat.hersteller;

import automat.verkaufsobjekte.kuchen.*;
import java.util.List;

class HerstellerImpl implements Hersteller {

    private String name;

    protected HerstellerImpl(String _name) {
        this.name = _name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }


}
