package automat.hersteller;

import automat.verkaufsobjekte.kuchen.*;

import java.util.Date;

class HerstellerImpl implements Hersteller {

    private String name;
   /* private int kuchenAnzahl;*/

    protected HerstellerImpl(String _name) {
        this.name = _name;
      /*  this.kuchenAnzahl = 0;*/
    }

    @Override
    public String getName() {
        return this.name;
    }

    public boolean equals(Hersteller a) {
        return (a.getName().equalsIgnoreCase(this.name));
    }

    @Override
    public String toString() {
        return this.name;
    }


   /* public Kremkuchen createKremkuchen(String kremsorte) {
        this.kuchenAnzahl++;
        return new KremkuchenImpl( this, new Date(), kremsorte);
    }
    public Obstkuchen createObstkuchen(String obstsorte) {
        this.kuchenAnzahl++;
        return new ObstkuchenImpl( this, new Date(), obstsorte);
    }
    public Obsttorte createObsttorte(String kremsorte, String obstsorte) {
        this.kuchenAnzahl++;
        return new ObsttorteImpl( this, new Date(), obstsorte, kremsorte);
    }
*/

}
