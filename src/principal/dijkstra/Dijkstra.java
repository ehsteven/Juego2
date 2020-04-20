package principal.dijkstra;

import org.w3c.dom.css.Rect;
import principal.Constantes;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Dijkstra {
    private int anchoMapaTiles;
    private int altoMapaTiles;
    private ArrayList<Nodo> nodosMapa;
    private ArrayList<Nodo> pendiente;
    private ArrayList<Nodo> visitados;

    public Dijkstra(final Point centroCalculo, final int anchoMapaTiles, final int altoMapaTiles,
                    final ArrayList<Rectangle> zonasSolidas) {
        this.anchoMapaTiles = anchoMapaTiles;
        this.altoMapaTiles = altoMapaTiles;
        nodosMapa = new ArrayList<>();
        for (int y = 0; y < altoMapaTiles; y++) {
            for (int x = 0; x < anchoMapaTiles; x++) {
                final int lado = Constantes.LADO_SPRITES;
                final Rectangle ubicacionNodo = new Rectangle(x * lado, y * lado, lado, lado);
                boolean transitable = true;
                for (Rectangle area : zonasSolidas) {
                    if (ubicacionNodo.intersects(area)) {
                        transitable = false;
                        break;
                    }
                }
                if (!transitable)
                    continue;
                Nodo nodo = new Nodo(new Point(x, y), Double.MAX_VALUE);
                nodosMapa.add(nodo);
            }
        }
        reiniciarYEvaluar(centroCalculo);
    }

    public void reiniciarYEvaluar(final Point centroCalculo) {
        pendiente = new ArrayList<>(nodosMapa);
        definirCentroCalculoPendientes(centroCalculo);
        visitados = new ArrayList<>();
        evaluarHeuristicaGlobal();
    }

    private void definirCentroCalculoPendientes(final Point centroCalculo) {
        for (Nodo nodo : pendiente) {
            if (nodo.getPosicion().equals(centroCalculo))
                nodo.setDistancia(0.0);
        }
    }

    private void evaluarHeuristicaGlobal() {
        while (!pendiente.isEmpty()) {
            int cambios = 0;
            for (Iterator<Nodo> iterator = pendiente.iterator(); iterator.hasNext(); ) {
                Nodo nodo = iterator.next();
                if (nodo.getDistancia() == Double.MAX_VALUE)
                    continue;
                else {
                    evaluarHeuristicaVecinos(nodo);
                }
            }
        }
    }

    private void evaluarHeuristicaVecinos(final Nodo nodo) {
        int inicialY = nodo.getPosicion().y;
        int inicialX = nodo.getPosicion().x;
        final double DISTANCIA_DIAGONAL = 1.42412;
        for (int y = inicialY - 1; y < inicialY + 2; y++) {
            for (int x = inicialX - 1; x < inicialX + 2; x++) {
                //dentro del rango del mapa;
                if (x <= -1 || y <= -1 || x >= anchoMapaTiles || y >= altoMapaTiles)
                    continue;
                //omitimos el propio nodo;
                if (inicialX == x && inicialY == y)
                    continue;
                //nodo existe en la posicion?;
                //minuto 35 episocio 123; !!!!!!!!!!!!!!!!!!!!!!!!!!
            }
        }
    }
    private int getIndiceNodoPendientes(final Point posicion){
        for(Nodo nodo : pendiente){
            if(nodo.getPosicion().equals(posicion)){
                return pendiente.indexOf(nodo);
            }
        }
        return -1;
    }

    private int getIndiceNodoVisitados(final Point posicion){
        for(Nodo nodo : visitados){
            if(nodo.getPosicion().equals(posicion)){
                return visitados.indexOf(nodo);
            }
        }
        return -1;
    }
}
