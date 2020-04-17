package principal;

import principal.control.GestorControles;
import principal.graficos.*;
import principal.mapas.MapaTiled;
import principal.maquinaEstado.GestorEstados;

public class GestorPrincipal {
    private boolean enfuncionamiento = false;
    private String title = "";
    private int ancho;
    private int alto;
    private static SuperficieDibujo sd;
    private Ventana ventana;
    private GestorEstados ge;
    private static int fps = 0;
    private static int aps = 0;

    private GestorPrincipal(final String title, final int ancho, final int alto){
        this.title = title;
        this.ancho = ancho;
        this.alto = alto;
    }

    public static void main(String[] args) {
        System.setProperty("sun.java2d.openg", "true");

        MapaTiled mt = new MapaTiled(Constantes.RUTA_MAPA_TILED);
        GestorPrincipal gp  = new GestorPrincipal("Juego", Constantes.ANCHO_PANTALLA_COMPLETA, Constantes.ALTO_PANTALLA_COMPLETA);
        gp.iniciarJuego();
        gp.iniciarBuclePrincipal();
    }


    private void inicializar() {
        sd = new SuperficieDibujo(ancho, alto);
        ventana = new Ventana(title, sd);
        ge = new GestorEstados(sd);
    }

    private void iniciarJuego() {
        enfuncionamiento = true;
        inicializar();
    }

    private void iniciarBuclePrincipal() {
        int actualizacionesAcumuladas = 0;
        int framesAcumulados = 0;
        final int NS_POR_SEGUNDO = 1000000000;
        final int  APS_OJETIVO = 60;
        final double NS_POR_ACTUALIZACION = NS_POR_SEGUNDO/APS_OJETIVO;
        long referenciaActualizacion = System.nanoTime();
        long referenciaContador = System.nanoTime();
        double tiempoTranscurrido;
        double delta = 0;

        while(enfuncionamiento){
            final long inicioBucle = System.nanoTime();
            tiempoTranscurrido = inicioBucle - referenciaActualizacion;
            referenciaActualizacion = inicioBucle;
            delta += tiempoTranscurrido/NS_POR_ACTUALIZACION;

            while(delta >= 1){
                actualizar();
                actualizacionesAcumuladas++;
                delta--;
            }
            dibujar();
            framesAcumulados++;
            if (System.nanoTime() - referenciaContador > NS_POR_SEGUNDO){
                fps = framesAcumulados;
                aps = actualizacionesAcumuladas;
                actualizacionesAcumuladas = 0;
                framesAcumulados = 0;
                referenciaContador = System.nanoTime();
            }
        }
    }

    private void actualizar(){
        if(GestorControles.teclado.inventarioActivo)
            ge.cambiarEstadoActual(1);
        else
            ge.cambiarEstadoActual(0);

        ge.actualizar();
        sd.actualizar();
    }

    private void dibujar(){
        sd.dibujar(ge);
    }

    public static int getFps() {
        return fps;
    }

    public static int getAps() {
        return aps;
    }

    public static SuperficieDibujo getSd() {
        return sd;
    }
}
