package principal.control;

import principal.Constantes;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Raton extends MouseAdapter {
    private final Cursor cursor;
    private Point posicion;
    private boolean clickIzq;
    private boolean clickDer;

    public Raton(final SuperficieDibujo sd){
        Toolkit config = Toolkit.getDefaultToolkit();
        BufferedImage icono = CargadorRecursos.cargarImagenCompatibleTranslucido("/icono/cursor.png");
        Constantes.LADO_CURSOR = icono.getWidth();
        Point punta = new Point(0,0 );
        this.cursor = config.createCustomCursor(icono, punta, "cursor");
        posicion = new Point();
        actualizarPosicion(sd);
        clickIzq = false;
        clickDer = false;
    }

    public void actualizar(SuperficieDibujo sd){
        actualizarPosicion(sd);
    }

    public void dibujar(Graphics g){
        DatosDebug.enviarDatos("RX: "+ posicion.getX());
        DatosDebug.enviarDatos("RY: "+ posicion.getY());
    }

    public Cursor getCursor() {
        return this.cursor;
    }

    private void actualizarPosicion(final SuperficieDibujo sd){
        final Point posicionInicial = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(posicionInicial, sd);
        posicion.setLocation(posicionInicial.getX(), posicionInicial.getY());
    }

    public Point getPosicion() {
        return posicion;
    }

    public Rectangle getRectanguloPosicion(){
        final Rectangle area = new Rectangle(posicion.x, posicion.y, 1,1 );
        return area;
    }

    public void mousePressed(MouseEvent e){
        if(SwingUtilities.isLeftMouseButton(e)){
            clickIzq = true;
        }else if(SwingUtilities.isRightMouseButton(e)){
            clickDer = true;
        }
    }

    public void mouseReleased(MouseEvent e){
        if(SwingUtilities.isLeftMouseButton(e)){
            clickIzq = false;
        }else if(SwingUtilities.isRightMouseButton(e)){
            clickDer = false;
        }
    }

    public boolean isClickIzq() {
        return clickIzq;
    }

    public boolean isClickDer() {
        return clickDer;
    }
}
