package principal.inventario.consumibles;

import principal.inventario.Objeto;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

import static principal.Constantes.*;

public class Consumible extends Objeto {
    public static HojaSprites hojaConsumibles = new HojaSprites(RUTA_OBJETOS, LADO_SPRITES, false);
    public Consumible(int id_x, int id_y, String nombre, String descripcion) {
        super(id_x, id_y, nombre, descripcion);
    }

    public Consumible(int id_x, int id_y, String nombre, String descripcion, int cantida) {
        super(id_x, id_y, nombre, descripcion, cantida);
    }

    @Override
    public Sprite getSprite() {
        return hojaConsumibles.getSprite(id_x, id_y);
    }
}
