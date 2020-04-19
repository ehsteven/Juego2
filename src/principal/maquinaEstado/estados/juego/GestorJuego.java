package principal.maquinaEstado.estados.juego;

import principal.herramientas.DatosDebug;
import principal.interfaz_usuario.MenuInferior;
import principal.inventario.Inventario;
import principal.mapas.Mapa;
import principal.maquinaEstado.EstadoJuego;

import java.awt.*;

import static principal.ElementosPrincipales.*;

public class GestorJuego implements EstadoJuego {
    MenuInferior menuInferiror;

    public GestorJuego(){
        menuInferiror = new MenuInferior();
    }

/*    private void recargarJuego() {
        final String ruta = "/mapas/" + mapa.getSiguienteMapa();
        //mapa = new Mapa(ruta);
        //inventario = new Inventario();
    }*/

    @Override
    public void actualizar() {
//        if(jugador.getLIMITE_ABAJO().intersects(mapa.getZonaSalida()))
//                recargarJuego();
        jugador.actualizar();
        mapa.actualizar();
    }

    @Override
    public void dibujar(Graphics g) {
        mapa.dibujar(g);
        jugador.dib(g);
        menuInferiror.dibujar(g);
        DatosDebug.enviarDatos("X: "+ jugador.getPosicionXInt());
        DatosDebug.enviarDatos("Y: "+ jugador.getPosicionYInt());
//        DatosDebug.enviarDatos("Siguiente mapa: "+ mapa.getSiguienteMapa());
//        DatosDebug.enviarDatos("Posicion salida X: "+ mapa.getPuntoSalida().x + " Y: "+ mapa.getPuntoSalida().y);
    }
}