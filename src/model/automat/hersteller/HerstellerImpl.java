package model.automat.hersteller;

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
