package principal.inventario.armas;

import java.util.ArrayList;

public class Pistola extends Arma{

    public Pistola(int id_x, int id_y, String nombre, String descripcion, int ataqueMin, int ataqueMax) {
        super(id_x, id_y, nombre, descripcion, ataqueMin, ataqueMax);
    }

    public Pistola(int id_x, int id_y, String nombre, String descripcion, int cantida, int ataqueMin, int ataqueMax) {
        super(id_x, id_y, nombre, descripcion, cantida, ataqueMin, ataqueMax);
    }

    @Override
    protected ArrayList<Readable> getAlcance() {
        return null;
    }
}
