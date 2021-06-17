package model.automat.hersteller;

import java.io.Serializable;

class HerstellerImpl implements Hersteller, Serializable {

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
