package principal.maquinaEstado;
import principal.graficos.SuperficieDibujo;
import principal.maquinaEstado.estados.juego.GestorJuego;
import principal.maquinaEstado.estados.menuJuego.GestorMenu;

import java.awt.*;

public class GestorEstados {
    private EstadoJuego[] estados;
    private EstadoJuego estadoActual;

    public GestorEstados(final SuperficieDibujo sd){
        iniciarEstados(sd);
        iniciarEstadoActual();
    }

    private void iniciarEstados(final SuperficieDibujo sd) {
        estados = new EstadoJuego[2];
        estados[0] = new GestorJuego();
        estados[1] = new GestorMenu(sd);
        //añadir e iniciar los demas estados a medida que los creemos;
    }

    private void iniciarEstadoActual() {
        estadoActual = estados[0];
    }

    public void actualizar(){
        estadoActual.actualizar();
    }

    public void dibujar(final Graphics g){
        estadoActual.dibujar(g);
    }

    public void cambiarEstadoActual(final int nuevoEstado){
        estadoActual = estados[nuevoEstado];
    }

    public EstadoJuego getEstadoActual(){
        return estadoActual;
    }

}
