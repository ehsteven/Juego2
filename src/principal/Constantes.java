package principal;

import principal.herramientas.CargadorRecursos;

import java.awt.*;

public class Constantes {
    public static final int LADO_SPRITES = 32;
    public static int LADO_CURSOR = 0;

    public static int ANCHO_JUEGO = 1220;  //640
    public static int ALTO_JUEGO = 920;  //360

    public static int ANCHO_PANTALLA_COMPLETA = 1920;
    public static int ALTO_PANTALLA_COMPLETA = 1080;

    public static double FACTOR_ESCALADO_X = ANCHO_PANTALLA_COMPLETA / (double) ANCHO_JUEGO;
    public static double FACTOR_ESCALADO_Y = ALTO_PANTALLA_COMPLETA / (double) ALTO_JUEGO;

    public static int CENTRO_VENTANA_X = ANCHO_JUEGO / 2;
    public static int CENTRO_VENTANA_Y = ALTO_JUEGO / 2;

    public static String RUTA_MAPA = "/texto/mapa1.ap";
    public static String RUTA_MAPA_2 = "/texto/mapa2.ap";
    public static String RUTA_ICONO_RATON = "/icono/cursor.png";
    public static String RUTA_PERSONAJE = "/personajes/pelonALPHA.PNG";
    public static String RUTA_ICONO_VENTANA = "/icono/poo.png";
    public static String RUTA_OBJETOS = "/texturas/hojaObjetos/full.png";

    public static Font FUENTE_PIXEL = CargadorRecursos.cargarFuente("/fuentes/pixel7.ttf");

    public final static Color COLOR_NARANJA = new Color(0xFF6700);
    public final static Color COLOR_FONDO = new Color(222, 210, 210);
}
