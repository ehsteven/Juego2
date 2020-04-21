package principal.dijkstra;

import principal.Constantes;
import principal.entes.Enemigo;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import static principal.Constantes.LADO_SPRITES;

public class Dijkstra {
    private int anchoMapaTiles;
    private int altoMapaTiles;
    private ArrayList<Nodo> nodosMapa;
    private ArrayList<Nodo> pendientes;
    private ArrayList<Nodo> visitados;
    private boolean constructor = true;

    public Dijkstra(final Point centroCalculo, final int anchoMapaTiles, final int altoMapaTiles,
                    final ArrayList<Rectangle> zonasSolidas) {
        this.anchoMapaTiles = anchoMapaTiles;
        this.altoMapaTiles = altoMapaTiles;
        nodosMapa = new ArrayList<>();
        for (int y = 0; y < altoMapaTiles; y++) {
            for (int x = 0; x < anchoMapaTiles; x++) {
                final int lado = LADO_SPRITES;
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
        pendientes = new ArrayList<>(nodosMapa);
        reiniciarYEvaluar(centroCalculo);
        constructor = false;
    }

    public Point getCoordenadasNodoCoinsidente(final Point puntoJugador) {
        Rectangle rectPuntoExacto = new Rectangle(puntoJugador.x / LADO_SPRITES, puntoJugador.y / LADO_SPRITES,
                1, 1);
        Point puntoExacto = null;
        for (Nodo nodo : nodosMapa) {
            if (nodo.getArea().intersects(rectPuntoExacto)) {
                puntoExacto = new Point(rectPuntoExacto.x, rectPuntoExacto.y);
                return puntoExacto;
            }
        }
        return puntoExacto;
    }

    private ArrayList<Nodo> clonarNodosMapaANodoPendientes() {
        ArrayList<Nodo> nodosClonados = new ArrayList<>();
        for (Nodo nodo : nodosMapa) {
            Point posicion = nodo.getPosicion();
            double distancia = nodo.getDistancia();
            Nodo nodoClonado = new Nodo(posicion, distancia);
            nodosClonados.add(nodoClonado);
        }
        return nodosClonados;
    }

    public void reiniciarYEvaluar(final Point centroCalculo) {
        if (!constructor){
            if (visitados.size() == 0)
                clonarNodosMapaANodoPendientes();
            else{
                pendientes = new ArrayList<>(visitados);
                for (Nodo nodo : pendientes)
                    nodo.setDistancia(Double.MAX_VALUE);
            }
        }
        definirCentroCalculoPendientes(centroCalculo);
        visitados = new ArrayList<>();
        evaluarHeuristicaGlobal();
    }

    private void definirCentroCalculoPendientes(final Point centroCalculo) {
        for (Nodo nodo : pendientes) {
            if (nodo.getPosicion().equals(centroCalculo))
                nodo.setDistancia(0.0);
        }
    }

    private void evaluarHeuristicaGlobal() {
        while (!pendientes.isEmpty()) {
            int cambios = 0;
            for (Iterator<Nodo> iterator = pendientes.iterator(); iterator.hasNext(); ) {
                Nodo nodo = iterator.next();
                if (nodo.getDistancia() == Double.MAX_VALUE)
                    continue;
                else {
                    evaluarHeuristicaVecinos(nodo);
                    visitados.add(nodo);
                    iterator.remove();
                    cambios++;
                }
            }
            if (cambios == 0)
                break;
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
                int indiceNodo = getIndiceNodoPendientes(new Point(x, y));
                if (indiceNodo == -1)
                    continue;
                //solo se cambia la distancia si es transitable y si no ha sido cambiada
                double distancia;
                if (pendientes.get(indiceNodo).getDistancia() == Double.MAX_VALUE - 1) {
                    //distancia recta vs diagonal
                    if (inicialX != x && inicialY != y)
                        distancia = DISTANCIA_DIAGONAL;
                    else
                        distancia = 1;
                    pendientes.get(indiceNodo).setDistancia(nodo.getDistancia() + distancia);
                }
            }
        }
    }

    private ArrayList<Nodo> getNodosVecinos(Nodo nodo) {
        int inicialY = nodo.getPosicion().y;
        int inicialX = nodo.getPosicion().x;
        ArrayList<Nodo> nodosVecinos = new ArrayList<>();
        for (int y = inicialY - 1; y < inicialY + 2; y++) {
            for (int x = inicialX - 1; x < inicialX + 2; x++) {
                if (x <= -1 || y <= -1 || x >= anchoMapaTiles || y >= altoMapaTiles)
                    continue;
                if (inicialX == x && inicialY == y)
                    continue;
                int indiceNodo = getIndiceNodoVisitados(new Point(x, y));
                if (indiceNodo == -1)
                    continue;
                nodosVecinos.add(visitados.get(indiceNodo));
            }
        }
        return nodosVecinos;
    }

    public Nodo encontrarSiguienteNodoEnemigo(Enemigo enemigo) {
        ArrayList<Nodo> nodosAfectados = new ArrayList<>();
        Nodo siguenteNodo = null;
        for (Nodo nodo : visitados) {
            if(enemigo.getAreaPosicional().intersects(nodo.getAreaPixeles()))
                nodosAfectados.add(nodo);
        }
        if(nodosAfectados.size() == 1){
            Nodo nodoBase = nodosAfectados.get(0);
            nodosAfectados = getNodosVecinos(nodoBase);
        }
        for (int i = 0; i < nodosAfectados.size(); i++){
            if (i == 0)
                siguenteNodo = nodosAfectados.get(0);
            else if (siguenteNodo.getDistancia() > nodosAfectados.get(i).getDistancia())
                    siguenteNodo = nodosAfectados.get(i);
        }
        return siguenteNodo;
    }


    private int getIndiceNodoPendientes(final Point posicion) {
        for (Nodo nodo : pendientes) {
            if (nodo.getPosicion().equals(posicion)) {
                return pendientes.indexOf(nodo);
            }
        }
        return -1;
    }

    private int getIndiceNodoVisitados(final Point posicion) {
        for (Nodo nodo : visitados) {
            if (nodo.getPosicion().equals(posicion)) {
                return visitados.indexOf(nodo);
            }
        }
        return -1;
    }

    public ArrayList<Nodo> getPendientes() {
        return pendientes;
    }

    public ArrayList<Nodo> getVisitados() {
        return visitados;
    }
}
