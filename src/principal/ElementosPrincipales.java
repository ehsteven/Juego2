package principal;

import principal.entes.Jugador;
import principal.inventario.Inventario;
import principal.mapas.Mapa;

public class ElementosPrincipales {
    //public static MapaTiled mapa = new MapaTiled(Constantes.RUTA_MAPA_2);
    public static Mapa mapa = new Mapa(Constantes.RUTA_MAPA);
    public static Jugador jugador = new Jugador(mapa);
    public static Inventario inventario = new Inventario();

    public ElementosPrincipales() {
    }



}