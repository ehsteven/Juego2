package principal.entes;

import principal.inventario.armas.Arma;

public class AlmacenEquipo {
    private Arma arma;
    //armadura
    //municion en uso
    //cinturon


    public AlmacenEquipo(final Arma arma1) {
        this.arma = arma1;
    }

    public Arma getArma() {
        return arma;
    }

    public void setArma(Arma arma) {
        this.arma = arma;
    }
}
