package principal.inventario.armas;

import principal.inventario.Objeto;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

import java.util.ArrayList;
import java.util.Random;

import static principal.Constantes.*;

public abstract class Arma extends Objeto {
    public static HojaSprites hojaArmas = new HojaSprites(RUTA_OBJETOS, LADO_SPRITES, false);
    protected int ataqueMin;
    protected int ataqueMax;

    public Arma(int id_x, int id_y, String nombre, String descripcion, int ataqueMin, int ataqueMax) {
        super(id_x, id_y, nombre, descripcion);
        this.ataqueMax = ataqueMax;
        this.ataqueMin = ataqueMin;

    }

    public Arma(int id_x, int id_y, String nombre, String descripcion, int cantida, int ataqueMin, int ataqueMax) {
        super(id_x, id_y, nombre, descripcion, cantida);
        this.ataqueMax = ataqueMax;
        this.ataqueMin = ataqueMin;
    }

    protected abstract ArrayList<Readable> getAlcance();

    @Override
    public Sprite getSprite() {
        return hojaArmas.getSprite(id_x, id_y);
    }

    protected int getAtaqueMedio(final int ataqueMin, final int ataqueMax){
        Random r = new Random();
        return r.nextInt(ataqueMax-ataqueMin) + ataqueMin;
    }
}
