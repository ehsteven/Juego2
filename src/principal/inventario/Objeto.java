package principal.inventario;

import principal.Constantes;
import principal.sprites.*;

import java.awt.*;

public abstract class Objeto {
    protected String nombre;
    protected int id_x;
    protected int id_y;
    protected String descripcion;
    protected int cantida;
    protected int cantidaMax;
    protected Rectangle posicionMenu;
    protected Rectangle posicionFlotante;

    public Objeto(final int id_x, final int id_y, final String nombre, final String descripcion) {
        this.nombre = nombre;
        this.id_x = id_x;
        this.id_y = id_y;
        this.descripcion = descripcion;
        cantida = 0;
        cantidaMax = 99;
        posicionMenu = new Rectangle(0,0,0,0);
        posicionFlotante = new Rectangle(0,0,0,0);
    }

    public Objeto(int id_x, int id_y, String nombre, String descripcion, int cantida) {
        this(id_x, id_y, nombre, descripcion);
        if (cantida <= cantidaMax)
            this.cantida = cantida;
        posicionMenu = new Rectangle(0,0,0,0);
        posicionFlotante = new Rectangle(0,0,0,0);
    }


    public boolean incrementarCantidad(final int incremento) {
        boolean incrementado = false;
        if (cantida + incremento <= cantidaMax) {
            cantida += incremento;
            incrementado = true;
        }
        return incrementado;
    }

    public boolean reducirCantidad(final int decremento) {
        boolean reducido = false;
        if (cantida - decremento >= cantidaMax) {
            cantida -= decremento;
            reducido = true;
        }
        return reducido;
    }

    public abstract Sprite getSprite();

    public int getId_x() {
        return id_x;
    }

    public int getId_y() {
        return id_y;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCantidaMax() {
        return cantidaMax;
    }

    public int getCantida() {
        return cantida;
    }

    public String getNombre(){
        return nombre;
    }

    public void setId_y(int id_y) {
        this.id_y = id_y;
    }

    public void setId_x(int id_x) {
        this.id_x = id_x;
    }

    public Rectangle getPosicionMenu() {
        return posicionMenu;
    }

    public void setPosicionMenu(Rectangle posicionMenu) {
        this.posicionMenu = posicionMenu;
    }

    public Rectangle getPosicionFlotante() {
        return posicionFlotante;
    }

    public void setPosicionFlotante(Rectangle posicionFlotante) {
        this.posicionFlotante = posicionFlotante;
    }
}
