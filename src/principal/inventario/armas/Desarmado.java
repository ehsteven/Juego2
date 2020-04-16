package principal.inventario.armas;

import principal.entes.Jugador;

import java.awt.*;
import java.util.ArrayList;


public class Desarmado extends Arma {
    public Desarmado(int id_x, int id_y, String nombre, String descripcion, int ataqueMin, int ataqueMax) {
        super(id_x, id_y, nombre, descripcion, ataqueMin, ataqueMax);
    }

    public Desarmado(int id_x, int id_y, String nombre, String descripcion, int cantida, int ataqueMin, int ataqueMax) {
        super(id_x, id_y, nombre, descripcion, cantida, ataqueMin, ataqueMax);
    }

    @Override
    public ArrayList<Rectangle> getAlcance(final Jugador jugador) {
        return null;
    }
}
