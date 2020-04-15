package principal.interfaz_usuario;

import principal.Constantes;
import principal.herramientas.DibujoDebug;

import java.awt.*;

import static principal.ElementosPrincipales.jugador;

public class MenuInferior {
    private Rectangle areaInventario;
    private Rectangle bordeAreaInventario;

    private final Color negroGrisoso, rojoClaro, rojoOscuro, verdeClaro, verdeOscuro, celesteClaro, celesteOscuro, colorExp_O, colorExp_C;

    public MenuInferior() {
        int distanciaY = 400;
        int altoMenu = 120;
        areaInventario = new Rectangle(0, Constantes.ALTO_JUEGO - distanciaY, Constantes.ANCHO_JUEGO, altoMenu);
        bordeAreaInventario = new Rectangle(areaInventario.x, areaInventario.y - 1, areaInventario.width, 1);
        negroGrisoso = new Color(33, 38, 39);
        rojoOscuro = new Color(255, 0, 0);
        rojoClaro = new Color(214, 86, 90);
        verdeOscuro = new Color(0, 255, 0);
        verdeClaro = new Color(115, 255, 125);
        celesteClaro = new Color(12, 183, 242);
        celesteOscuro = new Color(0, 135, 255);
        colorExp_O = new Color(54, 190, 175);
        colorExp_C = new Color(50, 222, 191);
    }

    public void dibujar(final Graphics g){
        dibujarAreaInventario(g);
        dibujarBarraVida(g);
        dibujarBarraResistencia(g);
        dibujarBarraAP(g);
        dibujarBarraExp(g, 50);
        dibujarRanurasObjetos(g);
    }

    private void dibujarAreaInventario(final Graphics g){
        DibujoDebug.dibujarRectanguloRelleno(g, areaInventario, negroGrisoso);
        DibujoDebug.dibujarRectanguloRelleno(g, bordeAreaInventario, Color.WHITE);
    }

    private void dibujarBarraVida(final Graphics g){
        final int medidaVertical = 5;
        final int anchoTotal = 100;
        DibujoDebug.dibujarRectanguloRelleno(g, areaInventario.x + 35, areaInventario.y + medidaVertical,
                anchoTotal, medidaVertical, rojoClaro);
        DibujoDebug.dibujarRectanguloRelleno(g, areaInventario.x + 35, areaInventario.y + medidaVertical * 2,
                anchoTotal, medidaVertical, rojoOscuro);
        g.setColor(Color.WHITE);
        DibujoDebug.dibujarString(g, "HP", areaInventario.x + 15, areaInventario.y + medidaVertical * 3);
        DibujoDebug.dibujarString(g, "1000", 75, areaInventario.y + medidaVertical * 3);
    }

    private void dibujarBarraResistencia(final Graphics g){
        final int medidaVertical = 5;
        final int anchoTotal = 100 * jugador.getResistencia() / jugador.RESISTENCIA_TOTAL;
        DibujoDebug.dibujarRectanguloRelleno(g, areaInventario.x + 35, areaInventario.y + medidaVertical * 4,
                anchoTotal, medidaVertical, verdeClaro);
        DibujoDebug.dibujarRectanguloRelleno(g, areaInventario.x + 35, areaInventario.y + medidaVertical * 5,
                anchoTotal, medidaVertical, verdeOscuro);
        g.setColor(Color.WHITE);
        DibujoDebug.dibujarString(g, "RES", areaInventario.x + 15, areaInventario.y + medidaVertical * 6);
        DibujoDebug.dibujarString(g, "" + jugador.getResistencia(), 75, areaInventario.y + medidaVertical * 6);
    }

    private void dibujarBarraAP(final Graphics g){
        final int medidaVertical = 5;
        final int anchoTotal = 100;
        DibujoDebug.dibujarRectanguloRelleno(g, areaInventario.x + 35, areaInventario.y + medidaVertical * 7,
                anchoTotal, medidaVertical, celesteClaro);
        DibujoDebug.dibujarRectanguloRelleno(g, areaInventario.x + 35, areaInventario.y + medidaVertical * 8,
                anchoTotal, medidaVertical, celesteOscuro);
        g.setColor(Color.WHITE);
        DibujoDebug.dibujarString(g, "AP", areaInventario.x + 15, areaInventario.y + medidaVertical * 9);
        DibujoDebug.dibujarString(g, "500", 75, areaInventario.y + medidaVertical * 9);
    }

    private void dibujarBarraExp(final Graphics g, final int exp){
        final int medidaVertical = 5;
        final int anchoTotal = 100;
        final int ancho = anchoTotal * exp / anchoTotal;
        DibujoDebug.dibujarRectanguloRelleno(g, areaInventario.x + 35, areaInventario.y + medidaVertical * 10,
                ancho, medidaVertical, colorExp_C);
        DibujoDebug.dibujarRectanguloRelleno(g, areaInventario.x + 35, areaInventario.y + medidaVertical * 11,
                ancho, medidaVertical, colorExp_O);
        g.setColor(Color.WHITE);
        DibujoDebug.dibujarString(g, "EXP", areaInventario.x + 15, areaInventario.y + medidaVertical * 12);
        DibujoDebug.dibujarString(g, exp + "%", 75, areaInventario.y + medidaVertical * 12);
    }

    private void dibujarRanurasObjetos(final Graphics g){
        final int anchoRanura = 32;
        final int numeroRanura = 10;
        final int espaciadoRanuras = 10;
        final int anchoTotal = anchoRanura * numeroRanura + espaciadoRanuras + numeroRanura;
        final int xInicial = (Constantes.ANCHO_JUEGO - anchoTotal) / 4 ;
        final int anchoRanuraYEspacio = anchoRanura + espaciadoRanuras;
        g.setColor(Color.WHITE);
        for(int i = 0; i < numeroRanura; i++){
            int xActual = xInicial + anchoRanuraYEspacio * i;
            Rectangle ranura = new Rectangle(xActual, areaInventario.y + 10, anchoRanura, anchoRanura);
            DibujoDebug.dibujarRectanguloRelleno(g, ranura);
            DibujoDebug.dibujarString(g, ""+ i, xActual + 13,areaInventario.y + 55 );
        }
    }
}
