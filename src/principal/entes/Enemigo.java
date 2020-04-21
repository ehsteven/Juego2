package principal.entes;

import principal.Constantes;
import principal.dijkstra.Nodo;
import principal.herramientas.*;

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
    private Nodo nodoSiguiente;

    public Enemigo(final int idEnemigo, final String nombre, final int vidaMax) {
        this.idEnemigo = idEnemigo;
        this.nombre = nombre;
        this.vidaMax = vidaMax;
        this.posicionX = 0;
        this.posicionY = 0;
        this.vidaActual = vidaMax;
    }

    public void actualizar() {
        moverHaciaSiguienteNodo();
    }

    private void moverHaciaSiguienteNodo(){
        if(nodoSiguiente == null)
            return ;
        double velocidad = 0.5;
        int xSiguienteNodo = nodoSiguiente.getPosicion().x * LADO_SPRITES;
        int ySiguienteNodo = nodoSiguiente.getPosicion().y * LADO_SPRITES;
        if (posicionX < xSiguienteNodo)
            posicionX += velocidad;

        if (posicionX > xSiguienteNodo)
            posicionX -= velocidad;

        if (posicionY < ySiguienteNodo)
            posicionY += velocidad;

        if (posicionY > ySiguienteNodo)
            posicionY -= velocidad;
    }

    public void dibujar(final Graphics g, final int puntoX, final int puntoY) {
        if (vidaActual <= 0)
            return;

        dibujarBarraVida(g, puntoX, puntoY);
        DibujoDebug.dibujarRectanguloContorno(g, getArea());
        dibujarDistancia(g, puntoX, puntoY);
    }

    private void dibujarBarraVida(final Graphics g, final int puntoX, final int puntoY) {
        g.setColor(Color.RED);
        DibujoDebug.dibujarRectanguloRelleno(g, puntoX, puntoY - 5, LADO_SPRITES * (int) vidaActual / vidaMax, 2);
    }

    private void dibujarDistancia(final Graphics g, final int puntoX, final int puntoY) {
        Point puntoJugador = new Point(jugador.getPosicionXInt(),
                jugador.getPosicionYInt());
        Point puntoEnemigo = new Point((int) posicionX, (int) posicionY);
        Double distancia = CalculadoraDistancia.getDistanciaEntrePunto(puntoJugador, puntoEnemigo);
        DibujoDebug.dibujarString(g, String.format("%.2f", distancia), puntoX, puntoY - 8);
    }

    public void setposiciones(final double posicionX, final double posicionY) {
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

    public void perderVida(float ataqueRecibido){
        //sonidos de recimientos de ataque
        if (vidaActual - ataqueRecibido < 0 )
            vidaActual = 0;
        else
            vidaActual -= ataqueRecibido;
    }

    public Rectangle getArea() {
        final int puntoX = (int) posicionX - jugador.getPosicionXInt() + MARGEN_X;
        final int puntoY = (int) posicionY - jugador.getPosicionYInt() + MARGEN_Y;
        return new Rectangle(puntoX, puntoY, LADO_SPRITES, LADO_SPRITES);

    }

    public Rectangle getAreaPosicional(){
        return new Rectangle((int) posicionX, (int) posicionY, LADO_SPRITES, LADO_SPRITES);
    }

    public void setNodoSiguiente(Nodo nodoSiguiente) {
        this.nodoSiguiente = nodoSiguiente;
    }

    public Nodo getNodoSiguiente() {
        return nodoSiguiente;
    }
}
