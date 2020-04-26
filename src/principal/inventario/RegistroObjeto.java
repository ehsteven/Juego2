package principal.inventario;

import principal.inventario.armas.*;
import principal.inventario.consumibles.Consumible;
import principal.sprites.HojaSprites;

public class RegistroObjeto {
    public static Objeto getObjeto(final int i){
        Objeto objeto = null;
        int idXObjeto, idYObjeto;
        switch (i){
            //0-100 objetos consumibles;
            case 0:
                idYObjeto = 45;
                idXObjeto = 0;
                objeto = new Pistola(idXObjeto, idYObjeto, "Espada", "", 1,2 , false,
                        false, 0.4);
                break;
            case 1:
                idYObjeto = 42;
                idXObjeto = 0;
                objeto = new Consumible(idXObjeto, idYObjeto, "Posion Azul", "");
                break;
            case 2:
                idYObjeto = 42;
                idXObjeto = 5;
                objeto = new Consumible(idXObjeto, idYObjeto, "Posion Roja","");
                break;
            case 3:
                idXObjeto = 0;
                idYObjeto = 0;
                objeto = new Desarmado(idXObjeto, idYObjeto, "Desarmado", "", 0,0, false, false, 0.7);
            //100- armas;
        }

        return objeto;
    }
}
