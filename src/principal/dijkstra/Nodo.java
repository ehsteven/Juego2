package principal.dijkstra;

import java.awt.*;

public class Nodo {
    private Point posicion;
    private double distancia;

    public Nodo(Point posicion, double distancia) {
        this.posicion = posicion;
        this.distancia = distancia;
    }

    public Point getPosicion() {
        return posicion;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setPosicion(Point posicion) {
        this.posicion = posicion;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }
}
