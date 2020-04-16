package principal.inventario.armas;

import principal.Constantes;
import principal.entes.Jugador;

import java.awt.*;
import java.util.ArrayList;

public class Pistola extends Arma{

    public Pistola(int id_x, int id_y, String nombre, String descripcion, int ataqueMin, int ataqueMax) {
        super(id_x, id_y, nombre, descripcion, ataqueMin, ataqueMax);
    }

    public Pistola(int id_x, int id_y, String nombre, String descripcion, int cantida, int ataqueMin, int ataqueMax) {
        super(id_x, id_y, nombre, descripcion, cantida, ataqueMin, ataqueMax);
    }

    @Override
    public ArrayList<Rectangle> getAlcance(final Jugador jugador) {
        final ArrayList<Rectangle> alcance = new ArrayList<>();
        final Rectangle alcance1 = new Rectangle();
        final int spriteAlcance = 4;
        if(jugador.getDireccion() == 0 || jugador.getDireccion() == 1 ){
            alcance1.width = 1;
            alcance1.height = spriteAlcance * Constantes.LADO_SPRITES;
            alcance1.x = Constantes.CENTRO_VENTANA_X;
            if (jugador.getDireccion() == 1){
                alcance1.y = Constantes.CENTRO_VENTANA_Y - 9;
            }else
                alcance1.y = Constantes.CENTRO_VENTANA_Y - 9 - alcance1.height;
        }else{
            alcance1.width = spriteAlcance * Constantes.LADO_SPRITES;
            alcance1.height = 1;
            alcance1.y = Constantes.CENTRO_VENTANA_Y - 3;
            if (jugador.getDireccion() == 2){
                alcance1.x = Constantes.CENTRO_VENTANA_X - alcance1.width;
            }else
                alcance1.x = Constantes.CENTRO_VENTANA_X;
        }

        alcance.add(alcance1);
        return alcance;
    }
}
