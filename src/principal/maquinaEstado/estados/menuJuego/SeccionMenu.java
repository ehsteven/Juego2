package principal.maquinaEstado.estados.menuJuego;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.DibujoDebug;

import java.awt.*;
import java.util.EmptyStackException;

import static principal.ElementosPrincipales.*;

public abstract class SeccionMenu {
    protected final String nombreSeccion;
    protected final Rectangle etiquetaMenu;
    protected final EstructuraMenu em;
    protected  final int margenGeneral  = 8;
    protected final Rectangle barraPeso;

    public SeccionMenu(String nombreSeccion, Rectangle etiquetaMenu, final EstructuraMenu em) {
        this.nombreSeccion = nombreSeccion;
        this.etiquetaMenu = etiquetaMenu;
        this.em = em;
        int anchoBarra = 100;
        barraPeso = new Rectangle(Constantes.ANCHO_JUEGO - anchoBarra - 12, em.BANER_SUPERIOR.height + margenGeneral,
                jugador.limitePeso, 8);
    }

    public abstract void actualizar();
    public abstract void dibujar(Graphics g, final SuperficieDibujo sd, final EstructuraMenu em);
    public void dibujarEtiquetaInactiva(final Graphics g){
        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaMenu, Color.WHITE);
        DibujoDebug.dibujarString(g, nombreSeccion, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.BLACK);
    }

    public void dibujarEtiquetaActiva(Graphics g){
        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaMenu, Color.WHITE);
        final Rectangle marcaActiva = new Rectangle(etiquetaMenu.x, etiquetaMenu.y, 5, etiquetaMenu.height);
        DibujoDebug.dibujarRectanguloRelleno(g, marcaActiva, new Color(255, 103, 0));
        DibujoDebug.dibujarString(g, nombreSeccion, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.BLACK);
    }

    public void dibujarEtiquetaInactivaResaltada(final Graphics g){
        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaMenu, Color.WHITE);
        DibujoDebug.dibujarRectanguloRelleno(g, new Rectangle(etiquetaMenu.x + etiquetaMenu.width - 10, etiquetaMenu.y + 5,
                5, etiquetaMenu.height - 10),new Color(0x2A2A2A));
        DibujoDebug.dibujarString(g, nombreSeccion, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.BLACK);
    }

    public void dibujarEtiquetaActivaResaltada(final Graphics g){
        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaMenu, Color.WHITE);
        final Rectangle marcaActiva = new Rectangle(etiquetaMenu.x, etiquetaMenu.y, 5, etiquetaMenu.height);
        DibujoDebug.dibujarRectanguloRelleno(g, marcaActiva, new Color(255, 103, 0));
        DibujoDebug.dibujarRectanguloRelleno(g, new Rectangle(etiquetaMenu.x + etiquetaMenu.width - 10, etiquetaMenu.y + 5,
                5, etiquetaMenu.height - 10),new Color(0x2A2A2A));
        DibujoDebug.dibujarString(g, nombreSeccion, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.BLACK);

    }

    protected void dibujarLimitePeso(Graphics g, EstructuraMenu em){
        final Rectangle contenidoBarra = new Rectangle(barraPeso.x + 1, barraPeso.y + 1,
                barraPeso.width / (jugador.limitePeso/jugador.pesoActual), barraPeso.height - 2);

        DibujoDebug.dibujarString(g, "Peso", barraPeso.x - 30, barraPeso.y + 7, Color.BLACK);
        DibujoDebug.dibujarRectanguloRelleno(g, barraPeso, Color.GRAY);
        DibujoDebug.dibujarRectanguloRelleno(g, contenidoBarra, Constantes.COLOR_NARANJA);
    }

    public String getNombreSeccion() {
        return nombreSeccion;
    }

    public Rectangle getEtiquetaMenu() {
        return etiquetaMenu;
    }

    public Rectangle getEtiquetaMenuEscalada(){
        final Rectangle etiquetaEscalada = new Rectangle((int) (etiquetaMenu.x * Constantes.FACTOR_ESCALADO_X), (int) (etiquetaMenu.y * Constantes.FACTOR_ESCALADO_Y),
                (int)(etiquetaMenu.width * Constantes.FACTOR_ESCALADO_X), (int)(etiquetaMenu.height * Constantes.FACTOR_ESCALADO_Y));
        return etiquetaEscalada;
    }
}
//54011