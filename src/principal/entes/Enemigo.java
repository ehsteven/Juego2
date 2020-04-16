package principal.entes;

import principal.Constantes;
import principal.herramientas.CalculadoraDistancia;
import principal.herramientas.DibujoDebug;

import java.awt.*;

import static principal.Constantes.*;
import static principal.ElementosPrincipales.*;

public class Enemigo {
    private int idEnemigo;
    private double posicionX;
    private double posicionY;
    private String nombre;
    private int vidaMax;
    private float vidaActual;

    public Enemigo(final int idEnemigo,final  String nombre,final int vidaMax) {
        this.idEnemigo = idEnemigo;
        this.nombre = nombre;
        this.vidaMax = vidaMax;
        this.posicionX = 0;
        this.posicionY = 0;
        this.vidaActual = vidaMax;
    }

    public void actualizar(){

    }

    public void dibujar(final Graphics g, final int puntoX, final int puntoY){
        if(vidaActual <= 0)
            return;

        dibujarBarraVida(g, puntoX, puntoY);
        DibujoDebug.dibujarRectanguloContorno(g, getArea());
        dibujarDistancia(g, puntoX, puntoY);
    }

    private void dibujarBarraVida(final Graphics g, final int puntoX, final int puntoY){
        g.setColor(Color.RED);
        DibujoDebug.dibujarRectanguloRelleno(g, puntoX, puntoY - 5, LADO_SPRITES *(int) vidaActual / vidaMax, 2);
    }

    private void dibujarDistancia(final Graphics g, final int puntoX, final int puntoY){
        Point puntoJugador = new Point(jugador.getPosicionXInt() / LADO_SPRITES,
                jugador.getPosicionYInt() / LADO_SPRITES);
        Point puntoEnemigo = new Point((int) posicionX, (int) posicionY);
        Double distancia = CalculadoraDistancia.getDistanciaEntrePunto(puntoJugador, puntoEnemigo);

        DibujoDebug.dibujarString(g, String.format("%.2f",distancia), puntoX, puntoY - 8);
    }

    public void setposiciones(final double posicionX, final double posicionY){
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    public double getPosicionX() {
        return posicionX;
    }

    public double getPosicionY() {
        return posicionY;
    }

    public int getIdEnemigo() {
        return idEnemigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getVidaMax() {
        return vidaMax;
    }

    public float getVidaActual() {
        return vidaActual;
    }

    public Rectangle getArea(){
        final int puntoX = (int) posicionX * LADO_SPRITES - jugador.getPosicionXInt() + MARGEN_X;
        final int puntoY = (int) posicionY * LADO_SPRITES - jugador.getPosicionYInt() + MARGEN_Y;
        return  new Rectangle(puntoX, puntoY, LADO_SPRITES, LADO_SPRITES);

    }
}
