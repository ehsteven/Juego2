package principal.entes;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.control.GestorControles;
import principal.herramientas.DibujoDebug;
import principal.inventario.RegistroObjeto;
import principal.inventario.armas.Arma;
import principal.inventario.armas.Desarmado;
import principal.mapas.Mapa;
import principal.sprites.HojaSprites;

import java.awt.*;
import java.awt.image.BufferedImage;

import static principal.ElementosPrincipales.mapa;

public class Jugador {
    private double posicionX;
    private double posicionY;
    private int direccion;
    private double velocidad = 1;
    private boolean enMovimiento;
    private HojaSprites hs;
    private BufferedImage imagenActual;
    private final int anchoJugador = 16;
    private final int altoJugador = 16;
    private final Rectangle LIMITE_ARRIBA = new Rectangle(Constantes.CENTRO_VENTANA_X - anchoJugador,
            Constantes.CENTRO_VENTANA_Y, 30, 1);
    private final Rectangle LIMITE_ABAJO = new Rectangle(Constantes.CENTRO_VENTANA_X - anchoJugador,
            Constantes.CENTRO_VENTANA_Y + altoJugador, 30, 1);
    private final Rectangle LIMITE_IZQUIERDA = new Rectangle(Constantes.CENTRO_VENTANA_X - anchoJugador,
            Constantes.CENTRO_VENTANA_Y, 1, 16);
    private final Rectangle LIMITE_DERECHA = new Rectangle(Constantes.CENTRO_VENTANA_X + anchoJugador-2,
            Constantes.CENTRO_VENTANA_Y, 1, 16);

    private int animacion;
    private int estado;
    public static final int RESISTENCIA_TOTAL = 600;
    private int resistencia = 600;
    private int recuperacion = 0;
    private final int RECUPERACION_MAX = 300;
    private boolean recuperado = true;
    public int limitePeso = 100;
    public int pesoActual = 30;
    private AlmacenEquipo ae;

    public Jugador(final Mapa mapa) {
        posicionX = ElementosPrincipales.mapa.getPosicionInicial().getX();
        posicionY = ElementosPrincipales.mapa.getPosicionInicial().getY();
        this.enMovimiento = false;
        direccion = 0;
        hs = new HojaSprites(Constantes.RUTA_PERSONAJE, Constantes.LADO_SPRITES, false);
        imagenActual = hs.getSprite(2).getImagen();
        animacion = 0;
        ae = new AlmacenEquipo((Arma) RegistroObjeto.getObjeto(3));
    }

    public void actualizar(){
        cambiarHojaSprites();
        gestionarVelocidadResistencia();
        cambiarAnimacionEstado();
        enMovimiento = false;
        determinarDireccion();
        animar();
    }

    private void cambiarHojaSprites(){
        if (ae.getArma() instanceof Arma && !(ae.getArma() instanceof Desarmado)){
            System.out.println("TE FALTA HACER LAS HOJAS DE SPRITES BABOSO!!!!!!!!!!!");
            //hs = new HojaSprites()
        }
    }

    private void gestionarVelocidadResistencia(){
        if (GestorControles.teclado.corriendo && resistencia > 0){
            velocidad = 2;
            recuperado = false;
            recuperacion = 0;
        }else{
            velocidad = 1;
            if (!recuperado && recuperacion < RECUPERACION_MAX){
                recuperacion++;
            }
            if (recuperacion == RECUPERACION_MAX && resistencia < 600)
                resistencia++;
        }
    }

    private void cambiarAnimacionEstado() {
        if(animacion < 30)
            animacion++;
        else
            animacion= 0;
        if (animacion < 15 )
            estado = 0;
        else
            estado = 1;
    }


    private void determinarDireccion() {
        final int velocidadX = evaluarVelocidadX();
        final int velocidadY = evaluarVelocidadY();
        if(velocidadX != 0 && velocidadY != 0)
            return;
        if( (velocidadX != 0 && velocidadY == 0) || (velocidadX == 0 && velocidadY != 0))
            mover(velocidadX, velocidadY);
        else{
            //izquierda y arriba
            if(velocidadX == -1 && velocidadY == -1){
                if(GestorControles.teclado.izquierda.getUltimaPulsacion() > GestorControles.teclado.arriba.getUltimaPulsacion())
                    mover(velocidadX, 0);
                else
                    mover(0, velocidadY);
            }
            //izquierda y abajo
            if(velocidadX == -1 && velocidadY == 1){
                if(GestorControles.teclado.izquierda.getUltimaPulsacion() > GestorControles.teclado.abajo.getUltimaPulsacion())
                    mover(velocidadX, 0);
                else
                    mover(0, velocidadY);
            }
            //derecha y arriba
            if(velocidadX == 1 && velocidadY == -1){
                if(GestorControles.teclado.derecha.getUltimaPulsacion() > GestorControles.teclado.arriba.getUltimaPulsacion())
                    mover(velocidadX, 0);
                else
                    mover(0, velocidadY);
            }
            //derecha y abajo
            if(velocidadX == 1 && velocidadY == 1){
                if(GestorControles.teclado.derecha.getUltimaPulsacion() > GestorControles.teclado.abajo.getUltimaPulsacion())
                    mover(velocidadX, 0);
                else
                    mover(0, velocidadY);
            }
        }
    }

    private boolean enColisionArriba(int velocidadY){
        for(int r = 0; r < mapa.areasColision.size(); r++) {
            final Rectangle area = mapa.areasColision.get(r);
            int origenX = area.x;
            int origenY = area.y + velocidadY * (int)velocidad + 3 * (int)velocidad;
            final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITES, Constantes.LADO_SPRITES);
            if(LIMITE_ARRIBA.intersects(areaFutura)){
                return true;
            }
        }
        return false;
    }

    private boolean enColisionAbajo(int velocidadY){
        for(int r = 0; r < mapa.areasColision.size(); r++) {
            final Rectangle area = mapa.areasColision.get(r);
            int origenX = area.x;
            int origenY = area.y + velocidadY * (int)velocidad - 3 * (int)velocidad;
            final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITES, Constantes.LADO_SPRITES);
            if(LIMITE_ABAJO.intersects(areaFutura)){
                return true;
            }
        }
        return false;
    }

    private boolean enColisionIzquierda(int velocidadX){
        for(int r = 0; r < mapa.areasColision.size(); r++) {
            final Rectangle area = mapa.areasColision.get(r);
            int origenX = area.x  + velocidadX * (int)velocidad + 3 * (int)velocidad;
            int origenY = area.y;
            final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITES, Constantes.LADO_SPRITES);
            if(LIMITE_IZQUIERDA.intersects(areaFutura)){
                return true;
            }
        }
        return false;
    }

    private boolean enColisionDerecha(int velocidadX){
        for(int r = 0; r < mapa.areasColision.size(); r++) {
            final Rectangle area = mapa.areasColision.get(r);
            int origenX = area.x + velocidadX * (int)velocidad - 3 * (int)velocidad;
            int origenY = area.y;
            final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITES, Constantes.LADO_SPRITES);
            if(LIMITE_DERECHA.intersects(areaFutura)){
                return true;
            }
        }
        return false;
    }

    private boolean fueraMapa(final int velocidadX, final int velocidadY){
        int posicionFuturaX = (int) posicionX + velocidadX * (int)velocidad;
        int posicionFuturaY = (int) posicionY + velocidadY * (int)velocidad;
        final Rectangle bordeMapa = mapa.getBordes(posicionFuturaX, posicionFuturaY);
        final boolean fuera;
        if (LIMITE_ARRIBA.intersects(bordeMapa) || LIMITE_ABAJO.intersects(bordeMapa) ||
                LIMITE_DERECHA.intersects(bordeMapa) || LIMITE_IZQUIERDA.intersects(bordeMapa))
            fuera = false;
        else
            fuera = true;
        return fuera;
    }

    private void mover(final int velocidadX, final int velocidadY) {
        enMovimiento = true;
        cambiarDireccion(velocidadX, velocidadY);
        if (!fueraMapa(velocidadX, velocidadY)) {
            if (velocidadX == -1 && !enColisionIzquierda(velocidadX)) {
                posicionX += velocidadX * velocidad;
                restarResistencia();
            }
            if (velocidadX == 1 && !enColisionDerecha(velocidadX)) {
                posicionX += velocidadX * velocidad;
                restarResistencia();
            }
            if (velocidadY == -1 && !enColisionArriba(velocidadY)) {
                posicionY += velocidadY * velocidad;
                restarResistencia();
            }
            if (velocidadY == 1 && !enColisionAbajo(velocidadY)) {
                posicionY += velocidadY * velocidad;
                restarResistencia();
            }
        }
    }

    private void restarResistencia(){
        if(GestorControles.teclado.corriendo && resistencia > 0)
            resistencia--;
    }

    private void cambiarDireccion(final int velocidadX, final int velocidadY){
        if(velocidadX == -1)
            direccion = 2;
        else if(velocidadX == 1)
            direccion = 3;
        if(velocidadY == -1)
            direccion = 0;
        else if(velocidadY == 1)
            direccion = 1;
    }

    private int evaluarVelocidadX(){
        int velocidadX = 0;
        if(GestorControles.teclado.izquierda.isPulseada() && !GestorControles.teclado.derecha.isPulseada())
            velocidadX = -1;
        else if(GestorControles.teclado.derecha.isPulseada() && !GestorControles.teclado.izquierda.isPulseada())
            velocidadX = 1;
        return velocidadX;
    }

    private int evaluarVelocidadY(){
        int velocidadY = 0;
        if(GestorControles.teclado.arriba.isPulseada() && !GestorControles.teclado.abajo.isPulseada())
            velocidadY = -1;
        else if(GestorControles.teclado.abajo.isPulseada() && !GestorControles.teclado.arriba.isPulseada())
            velocidadY = 1;
        return velocidadY;
    }

    private void animar(){
        if(!enMovimiento){
            estado = 1;
            animacion = 0;
        }
        imagenActual = hs.getSprite(estado, direccion).getImagen();
    }

    public void dib(Graphics g){
        final int centroX = Constantes.CENTRO_VENTANA_X - Constantes.LADO_SPRITES / 2;
        final int centroY = Constantes.CENTRO_VENTANA_Y - Constantes.LADO_SPRITES / 2;
        DibujoDebug.dibujarImagen(g, imagenActual, centroX, centroY);
    }

    public void setPosicionX(final double posicionX) {
        this.posicionX = posicionX;
    }

    public void setPosicionY(final double posicionY) {
        this.posicionY = posicionY;
    }

    public double getPosicionX() {
        return posicionX;
    }

    public double getPosicionY() {
        return posicionY;
    }

    public int getPosicionXInt(){
        return (int)posicionX;
    }

    public int getResistencia() {
        return resistencia;
    }

    public int getPosicionYInt(){
        return (int)posicionY;
    }

    public int getAnchoJugador() {
        return anchoJugador;
    }

    public int getAltoJugador() {
        return altoJugador;
    }

    public Rectangle getLIMITE_ABAJO() {
        return LIMITE_ABAJO;
    }

    public AlmacenEquipo getAlmacenEquipo() {
        return ae;
    }
}