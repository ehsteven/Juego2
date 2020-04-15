package principal.maquinaEstado.estados.menuJuego;

import principal.graficos.SuperficieDibujo;
import principal.maquinaEstado.EstadoJuego;

import java.awt.*;

public class GestorMenu implements EstadoJuego {
    private final SuperficieDibujo sd;
    private final EstructuraMenu estructuraMenu;
    private final SeccionMenu[] secciones;
    private SeccionMenu seccionActual;

    public GestorMenu(final SuperficieDibujo sd) {
        this.sd = sd;
        estructuraMenu = new EstructuraMenu();
        secciones = new SeccionMenu[2];
        final Rectangle etiquetaInventario = new Rectangle(estructuraMenu.BANER_LATERAL.x + estructuraMenu.MARGEN_HORIZONTAL_ETIQUETAS,
                estructuraMenu.BANER_LATERAL.y + estructuraMenu.MARGEN_VERTICAL_ETIQUETAS, estructuraMenu.ANCHO_ETIQUETA,
                estructuraMenu.ALTO_ETIQUETA);
        final Rectangle etiquetaEquipo = new Rectangle(estructuraMenu.BANER_LATERAL.x + estructuraMenu.MARGEN_HORIZONTAL_ETIQUETAS,
                etiquetaInventario.y + etiquetaInventario.height + estructuraMenu.MARGEN_VERTICAL_ETIQUETAS, estructuraMenu.ANCHO_ETIQUETA,
                estructuraMenu.ALTO_ETIQUETA);
        secciones[0] = new MenuInventario("Inventario", etiquetaInventario, estructuraMenu);
        secciones[1] = new MenuEquipo("Equipo", etiquetaEquipo, estructuraMenu);
        seccionActual = secciones[0];
    }

    @Override
    public void actualizar() {
        for (int i = 0; i < secciones.length; i++){
            if (sd.getRaton().isClickIzq() && sd.getRaton().getRectanguloPosicion().intersects(secciones[i].getEtiquetaMenuEscalada()))
                seccionActual = secciones[i];
        }
        seccionActual.actualizar();
    }

    @Override
    public void dibujar(final Graphics g) {
        estructuraMenu.dibujar(g);
        for (int i = 0; i < secciones.length; i++){
            if (seccionActual == secciones[i]) {
                if (sd.getRaton().getRectanguloPosicion().intersects(secciones[i].getEtiquetaMenuEscalada()))
                    secciones[i].dibujarEtiquetaActivaResaltada(g);
                else
                    secciones[i].dibujarEtiquetaActiva(g);
            }else{
                if (sd.getRaton().getRectanguloPosicion().intersects(secciones[i].getEtiquetaMenuEscalada()))
                    secciones[i].dibujarEtiquetaInactivaResaltada(g);
                else
                    secciones[i].dibujarEtiquetaInactiva(g);
            }
        }
        seccionActual.dibujar(g, sd, estructuraMenu);
    }
}
