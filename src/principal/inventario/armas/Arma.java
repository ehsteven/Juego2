package principal.inventario.armas;

import principal.entes.Enemigo;
import principal.entes.Jugador;
import principal.inventario.Objeto;
import principal.sprites.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static principal.Constantes.*;

public abstract class Arma extends Objeto {
    public static HojaSprites hojaArmas = new HojaSprites(RUTA_OBJETOS, LADO_SPRITES, false);
    protected int ataqueMin;
    protected int ataqueMax;
    protected boolean automatica;
    protected boolean penetrante;
    protected double ataquesPorSeg;
    protected int actualizacionesSiguienteAtaque;

    public Arma(int id_x, int id_y, String nombre, String descripcion, int ataqueMin, int ataqueMax,
                final boolean automatica, final boolean penetrante, final double ataquesPorSeg) {
        super(id_x, id_y, nombre, descripcion);
        this.ataqueMax = ataqueMax;
        this.ataqueMin = ataqueMin;
        this.automatica = automatica;
        this.penetrante = penetrante;
        this.ataquesPorSeg = ataquesPorSeg;
        this.actualizacionesSiguienteAtaque = 0;
    }

    public abstract ArrayList<Rectangle> getAlcance(final Jugador jugador);

    public void actualizar(){
        if (actualizacionesSiguienteAtaque > 0)
            actualizacionesSiguienteAtaque--;
    }

    public void atacar(final ArrayList<Enemigo> enemigos){
        if (actualizacionesSiguienteAtaque > 0)
            return;
        actualizacionesSiguienteAtaque = (int) ataquesPorSeg * 60;

        if (enemigos.isEmpty())
            return;
        float ataqueActual = getAtaqueMedio();
        for (Enemigo enemigo : enemigos){
            enemigo.perderVida(ataqueActual);
        }
    }

    @Override
    public Sprite getSprite() {
        return hojaArmas.getSprite(id_x, id_y);
    }

    protected int getAtaqueMedio(){
        Random r = new Random();
        return r.nextInt(ataqueMax-ataqueMin) + ataqueMin;
    }

    public boolean isAutomatica() {
        return automatica;
    }

    public boolean isPenetrante() {
        return penetrante;
    }
}
