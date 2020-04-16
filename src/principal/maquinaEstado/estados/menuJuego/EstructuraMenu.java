package principal.maquinaEstado.estados.menuJuego;

import principal.Constantes;
import principal.herramientas.DibujoDebug;

import java.awt.*;

public class EstructuraMenu {
    public final Color COLOR_BANER_LATERAL;
    public final Color COLOR_BANER_SUPERIOR;
    public final Color COLOR_FONDO;

    public final Rectangle BANER_SUPERIOR;
    public final Rectangle BANER_LATERAL;
    public final Rectangle FONDO;

    public final int MARGEN_HORIZONTAL_ETIQUETAS;
    public final int MARGEN_VERTICAL_ETIQUETAS;
    public final int ANCHO_ETIQUETA;
    public final int ALTO_ETIQUETA;

    public EstructuraMenu() {
        COLOR_BANER_SUPERIOR = new Color(0xff6700);
        COLOR_BANER_LATERAL = Color.BLACK;
        COLOR_FONDO = new Color(222, 210, 210);

        BANER_SUPERIOR = new Rectangle(0,0, Constantes.ANCHO_JUEGO, 20);
        BANER_LATERAL = new Rectangle(0, BANER_SUPERIOR.height, 140, Constantes.ALTO_JUEGO - BANER_SUPERIOR.height);
        FONDO =  new Rectangle(BANER_LATERAL.x + BANER_LATERAL.width, BANER_LATERAL.y,
                Constantes.ANCHO_JUEGO - BANER_LATERAL.width, Constantes.ALTO_JUEGO - BANER_SUPERIOR.height);

        MARGEN_HORIZONTAL_ETIQUETAS = 20;
        MARGEN_VERTICAL_ETIQUETAS = 20;
        ANCHO_ETIQUETA = 100;
        ALTO_ETIQUETA = 20;
    }

    public void actualizar(){

    }

    public void dibujar(final Graphics g){
        DibujoDebug.dibujarRectanguloRelleno(g, BANER_SUPERIOR, COLOR_BANER_SUPERIOR);
        DibujoDebug.dibujarRectanguloRelleno(g, BANER_LATERAL, COLOR_BANER_LATERAL);
        DibujoDebug.dibujarRectanguloRelleno(g, FONDO, COLOR_FONDO);
    }
}
