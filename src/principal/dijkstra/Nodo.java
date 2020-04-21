package principal.dijkstra;

import org.w3c.dom.css.Rect;
import principal.Constantes;

import java.awt.*;

import static principal.Constantes.*;

public class Nodo {
    private Point posicion;
    private double distancia;

    public Nodo(Point posicion, double distancia) {
        this.posicion = posicion;
        this.distancia = distancia;
    }

    public Rectangle getAreaPixeles(){
        return new Rectangle(posicion.x * LADO_SPRITES, posicion.y * LADO_SPRITES,
                LADO_SPRITES, LADO_SPRITES);
    }

    public Rectangle getArea(){
        return new Rectangle(posicion.x, posicion.y, LADO_SPRITES, LADO_SPRITES);
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
