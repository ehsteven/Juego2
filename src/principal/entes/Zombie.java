package principal.entes;

import principal.Constantes;
import principal.herramientas.DibujoDebug;
import principal.sprites.HojaSprites;

import java.awt.*;

public class Zombie extends Enemigo {
    private static HojaSprites hojaZombie;

    public Zombie(int idEnemigo, String zombie, int vidaMax) {
        super(idEnemigo, zombie, vidaMax);
        if(hojaZombie == null)
            hojaZombie = new HojaSprites(Constantes.RUTA_ENEMIGOS + idEnemigo + ".png", Constantes.LADO_SPRITES, false);
    }

    public void dibujar(final Graphics g, final int puntoX, final int puntoY){
        DibujoDebug.dibujarImagen(g, hojaZombie.getSprite(0).getImagen(), puntoX, puntoY);
        super.dibujar(g, puntoX, puntoY);
    }
}
