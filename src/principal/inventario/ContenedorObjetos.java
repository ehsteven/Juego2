package principal.inventario;

import principal.herramientas.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ContenedorObjetos {
    private static final BufferedImage sprite = CargadorRecursos.cargarImagenCompatibleTranslucido("/texturas/cofre.png");
    private Point posicion;
    private Objeto[] objetos;

    public ContenedorObjetos(final Point posicion, final int[] objetos, final int[] cantidades) {
        this.posicion = posicion;
        this.objetos = new Objeto[objetos.length];
        for (int i = 0; i < objetos.length; i++){
            this.objetos[i] = RegistroObjeto.getObjeto(i);
            this.objetos[i].incrementarCantidad(cantidades[i]);
        }
    }

    public void dibujar(final Graphics g, final int puntoX, final int puntoY){
        DibujoDebug.dibujarImagen(g, sprite, puntoX, puntoY);
    }

    public Point getPosicion() {
        return posicion;
    }

    public Objeto[] getObjetos() {
        return objetos;
    }
}
