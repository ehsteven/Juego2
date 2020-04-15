package principal.inventario.armas;

import principal.sprites.HojaSprites;
import java.util.ArrayList;


public class Desarmado extends Arma {
    public Desarmado(int id_x, int id_y, String nombre, String descripcion, int ataqueMin, int ataqueMax) {
        super(id_x, id_y, nombre, descripcion, ataqueMin, ataqueMax);
    }

    public Desarmado(int id_x, int id_y, String nombre, String descripcion, int cantida, int ataqueMin, int ataqueMax) {
        super(id_x, id_y, nombre, descripcion, cantida, ataqueMin, ataqueMax);
    }

    @Override
    protected ArrayList<Readable> getAlcance() {
        return null;
    }
}
