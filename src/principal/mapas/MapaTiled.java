package principal.mapas;

import org.json.simple.*;
import org.json.simple.parser.*;
import org.w3c.dom.css.Rect;
import principal.Constantes;
import principal.control.GestorControles;
import principal.dijkstra.Dijkstra;
import principal.entes.Enemigo;
import principal.entes.RegistroEnemigos;
import principal.herramientas.CargadorRecursos;
import principal.herramientas.DibujoDebug;
import principal.inventario.ContenedorObjetos;
import principal.inventario.Objeto;
import principal.inventario.ObjetoUnicoTiled;
import principal.inventario.RegistroObjeto;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

import java.awt.*;
import java.util.ArrayList;

import static principal.Constantes.*;
import static principal.ElementosPrincipales.inventario;
import static principal.ElementosPrincipales.jugador;

public class MapaTiled {
    private int anchoMapaTile;
    private int altoMapaTile;
    private Point puntoInicial;

    private ArrayList<CapaSprites> capasSprites;
    private ArrayList<CapaColisiones> capasColiciones;

    private ArrayList<Rectangle> areasColicionOriginales;
    public ArrayList<Rectangle> areasColicionPorActualizacion;

    private Dijkstra d;

    private Sprite[] paletaSprites;

    private ArrayList<ObjetoUnicoTiled> objetosMapa;
    private ArrayList<Enemigo> enemigosMapa;

    public MapaTiled(final String ruta) {
        String contenido = CargadorRecursos.leerArchivoTexto(ruta);

        JSONObject globalJSON = getObjetoJSON(contenido);
        //Alto y Ancho del mapa
        anchoMapaTile = getIntJSON(globalJSON, "width");
        altoMapaTile = getIntJSON(globalJSON, "height");

        //Punto inicial
        JSONObject puntoInicial = getObjetoJSON(globalJSON.get("start").toString());
        this.puntoInicial = new Point(getIntJSON(puntoInicial, "x"), getIntJSON(puntoInicial, "y"));

        //Capas del mapa
        JSONArray capas = getArrayJSON(globalJSON.get("layers").toString());
        this.capasSprites = new ArrayList<>();
        this.capasColiciones = new ArrayList<>();

        //Iniciar Capas;
        for (int i = 0; i < capas.size(); i++) {
            JSONObject datosCapa = getObjetoJSON(capas.get(i).toString());
            int anchoCapa = getIntJSON(datosCapa, "width");
            int altoCapa = getIntJSON(datosCapa, "height");
            int xCapa = getIntJSON(datosCapa, "x");
            int yCapa = getIntJSON(datosCapa, "y");
            String tipo = datosCapa.get("type").toString();
            switch (tipo) {
                case "tilelayer":
                    JSONArray sprites = getArrayJSON(datosCapa.get("data").toString());
                    int[] spritesCapa = new int[sprites.size()];
                    for (int h = 0; h < sprites.size(); h++) {
                        int codigoSprites = Integer.parseInt(sprites.get(h).toString());
                        spritesCapa[h] = codigoSprites - 1;
                    }
                    this.capasSprites.add(new CapaSprites(anchoCapa, altoCapa, xCapa, yCapa, spritesCapa));
                    break;
                case "objectgroup":
                    JSONArray rectangulos = getArrayJSON(datosCapa.get("objects").toString());
                    Rectangle[] rectangulosCapa = new Rectangle[rectangulos.size()];
                    for (int h = 0; h < rectangulos.size(); h++) {
                        JSONObject datosRect = getObjetoJSON(rectangulos.get(h).toString());
                        int x = getIntJSON(datosRect, "x");
                        int y = getIntJSON(datosRect, "y");
                        int ancho = getIntJSON(datosRect, "width");
                        int alto = getIntJSON(datosRect, "height");
                        if (x == 0)
                            x = 1;
                        if (y == 0)
                            y = 1;
                        if (ancho == 0)
                            ancho = 1;
                        if (alto == 0)
                            alto = 1;

                        Rectangle rectangulo = new Rectangle(x, y, ancho, alto);
                        rectangulosCapa[h] = rectangulo;
                    }
                    this.capasColiciones.add(new CapaColisiones(anchoCapa, altoCapa, xCapa, yCapa, rectangulosCapa));
                    break;
            }
        }

        //COMBINAR COLICIONES EN UN SOLO ARRAYLIST POR EFICIENCIA
        areasColicionOriginales = new ArrayList<>();
        for (int i = 0; i < capasColiciones.size(); i++) {
            Rectangle[] rectangulos = capasColiciones.get(i).getColisionables();
            for (int h = 0; h < rectangulos.length; h++) {
                areasColicionOriginales.add(rectangulos[h]);
            }
        }
        //Objeto Dijstra
        d = new Dijkstra(new Point(10, 10), anchoMapaTile, altoMapaTile, areasColicionOriginales);

        //AVERIGUAR TOTAL DE SPRITES EXISTENTES EN TODAS LAS CAPAS
        JSONArray coleccionesSprites = getArrayJSON(globalJSON.get("tilesets").toString());
        int totalSprites = 0;
        for (int i = 0; i < coleccionesSprites.size(); i++) {
            JSONObject datosGrupo = getObjetoJSON(coleccionesSprites.get(i).toString());
            totalSprites += getIntJSON(datosGrupo, "tilecount");
        }
        paletaSprites = new Sprite[totalSprites];

        //ASIGNAR SPRITES NECESARIOS A LA PALETA DE LAS CAPAS;
        for (int i = 0; i < coleccionesSprites.size(); i++) {
            JSONObject datosGrupo = getObjetoJSON(coleccionesSprites.get(i).toString());
            String nombreImagen = datosGrupo.get("image").toString();
            int anchoTiles = getIntJSON(datosGrupo, "tilewidth");
            int altoTile = getIntJSON(datosGrupo, "tileheight");
            HojaSprites hoja = new HojaSprites("/mapas/" + nombreImagen,
                    anchoTiles, altoTile, false);
            int primerSpriteColeccion = getIntJSON(datosGrupo, "firstgid") - 1;
            int ultimoSpriteColeccion = primerSpriteColeccion + getIntJSON(datosGrupo, "tilecount") - 1;
            for (int h = 0; h < this.capasSprites.size(); h++) {
                CapaSprites capaActual = this.capasSprites.get(h);
                int[] spritesCapa = capaActual.getArraySprites();
                for (int j = 0; j < spritesCapa.length; j++) {
                    int idSpriteActual = spritesCapa[j];
                    if (idSpriteActual >= primerSpriteColeccion && idSpriteActual <= ultimoSpriteColeccion) {
                        if (paletaSprites[idSpriteActual] == null) {
                            paletaSprites[idSpriteActual] = hoja.getSprite(idSpriteActual - primerSpriteColeccion);
                        }
                    }
                }
            }
        }
        //OBTENER LOS OBJETOS
        objetosMapa = new ArrayList<>();
        JSONArray coleccionObjetos = getArrayJSON(globalJSON.get("objetos").toString());
        for (int i = 0; i < coleccionesSprites.size(); i++) {
            JSONObject datosObjetos = getObjetoJSON(coleccionObjetos.get(i).toString());
            int idObjeto = getIntJSON(datosObjetos, "id");
            int cantidadObjeto = getIntJSON(datosObjetos, "cantidad");
            int xObjeto = getIntJSON(datosObjetos, "x");
            int yObjeto = getIntJSON(datosObjetos, "y");

            Point posicionObjeto = new Point(xObjeto, yObjeto);
            Objeto objeto = RegistroObjeto.getObjeto(idObjeto);
            ObjetoUnicoTiled objetoUnico = new ObjetoUnicoTiled(posicionObjeto, objeto);
            objetosMapa.add(objetoUnico);
        }
        //OBTENER LOS ENEMIGOS
        enemigosMapa = new ArrayList<>();
        JSONArray coleccionEnemigos = getArrayJSON(globalJSON.get("enemigos").toString());
        for (int i = 0; i < coleccionEnemigos.size(); i++) {
            JSONObject datosEnemigos = getObjetoJSON(coleccionEnemigos.get(i).toString());
            int idEnemigo = getIntJSON(datosEnemigos, "id");
            int xEnemigo = getIntJSON(datosEnemigos, "x");
            int yEnemigo = getIntJSON(datosEnemigos, "y");

            Point posicionEnemigo = new Point(xEnemigo, yEnemigo);
            Enemigo enemigo = RegistroEnemigos.getEnemigo(idEnemigo);
            enemigo.setposiciones(posicionEnemigo.x, posicionEnemigo.y);
            enemigosMapa.add(enemigo);
        }
        areasColicionPorActualizacion = new ArrayList<>();
    }

    public void actualizar() {
        actualizarAreasColision();
        actualizarRecogidaObjetos();
        actualizarEnemigos();

        Point punto = new Point(jugador.getPosicionXInt(), jugador.getPosicionYInt());
        Point puntoCoincidente = d.getCoordenadasNodoCoinsidente(punto);
        d.reiniciarYEvaluar(puntoCoincidente);
    }

    private void actualizarEnemigos() {
        if (!enemigosMapa.isEmpty()) {
            for (Enemigo enemigo : enemigosMapa) {
                enemigo.setNodoSiguiente(d.encontrarSiguienteNodoEnemigo(enemigo));
                enemigo.actualizar();
            }
        }
    }

    private void actualizarAreasColision() {
        if (!areasColicionPorActualizacion.isEmpty()) {
            areasColicionPorActualizacion.clear();
        }
        for (int i = 0; i < areasColicionOriginales.size(); i++) {
            Rectangle rInicial = areasColicionOriginales.get(i);
            int puntoX = rInicial.x - jugador.getPosicionXInt() + MARGEN_X;
            int puntoY = rInicial.y - jugador.getPosicionYInt() + MARGEN_Y;
            final Rectangle rFinal = new Rectangle(puntoX, puntoY, rInicial.width, rInicial.height);
            areasColicionPorActualizacion.add(rFinal);
        }
    }

    private void actualizarRecogidaObjetos() {
        if (!objetosMapa.isEmpty()) {
            final Rectangle areaJugador = new Rectangle(jugador.getPosicionXInt(), jugador.getPosicionYInt(), LADO_SPRITES, LADO_SPRITES);
            for (int i = 0; i < objetosMapa.size(); i++) {
                final ObjetoUnicoTiled objetoActual = objetosMapa.get(i);
                final Rectangle posicionObjetoActual = new Rectangle(objetoActual.getPosicion().x, objetoActual.getPosicion().y,
                        LADO_SPRITES, LADO_SPRITES);
                if (areaJugador.intersects(posicionObjetoActual) && GestorControles.teclado.recogiendo) {
                    inventario.recogerObjeto(objetoActual);
                    objetosMapa.remove(i);
                }
            }
        }
    }

    public void dibujar(final Graphics g) {
        int intentosDibujo = 0;
        for (int i = 0; i < capasSprites.size(); i++) {
            int[] spritesCapa = capasSprites.get(i).getArraySprites();
            for (int y = 0; y < altoMapaTile; y++) {
                for (int x = 0; x < anchoMapaTile; x++) {
                    int idSpriteActual = spritesCapa[x + y * anchoMapaTile];
                    if (idSpriteActual != -1) {
                        int puntoX = x * LADO_SPRITES - (int) jugador.getPosicionX() +
                                MARGEN_X;
                        int puntoY = y * LADO_SPRITES - (int) jugador.getPosicionY() +
                                MARGEN_Y;
                        //17-19 puntoX
                        //18-20 ancho
                        //19-20 puntoY
                        //20
                        if(puntoX < 0 - LADO_SPRITES || puntoX > ANCHO_JUEGO ||
                                puntoY < 0 - LADO_SPRITES || puntoY > ALTO_JUEGO - 90)
                            continue;
                        intentosDibujo ++;
                        DibujoDebug.dibujarImagen(g, paletaSprites[idSpriteActual].getImagen(), puntoX, puntoY);
                    }
                }
            }
        }
        for (int i = 0; i < objetosMapa.size(); i++) {
            ObjetoUnicoTiled objetoActual = objetosMapa.get(i);
            int puntoX = objetoActual.getPosicion().x - jugador.getPosicionXInt() +
                    MARGEN_X;
            int puntoY = objetoActual.getPosicion().y - jugador.getPosicionYInt() +
                    MARGEN_Y;
            DibujoDebug.dibujarImagen(g, objetoActual.getObjeto().getSprite().getImagen(), puntoX, puntoY);
        }
        for (int i = 0; i < enemigosMapa.size(); i++) {
            Enemigo enemigo = enemigosMapa.get(i);
            int puntoX = (int) enemigo.getPosicionX() - jugador.getPosicionXInt() +
                    MARGEN_X;
            int puntoY = (int) enemigo.getPosicionY() - jugador.getPosicionYInt() +
                    MARGEN_Y;
            enemigo.dibujar(g, puntoX, puntoY);

        }

    }

    private JSONObject getObjetoJSON(final String codigoJSON) {
        JSONParser lector = new JSONParser();
        JSONObject jsonObject = null;
        try {
            Object recuperado = lector.parse(codigoJSON);
            jsonObject = (JSONObject) recuperado;

        } catch (ParseException e) {
            System.out.println("Posicion: " + e.getPosition());
            System.out.println(e);
        }
        return jsonObject;
    }

    private JSONArray getArrayJSON(final String codigoJSON) {
        JSONParser lector = new JSONParser();
        JSONArray jsonArray = null;
        try {
            Object recuperado = lector.parse(codigoJSON);
            jsonArray = (JSONArray) recuperado;

        } catch (ParseException e) {
            System.out.println("Posicion: " + e.getPosition());
            System.out.println(e);
        }
        return jsonArray;
    }

    private int getIntJSON(final JSONObject jsonObject, final String clave) {
        return Integer.parseInt(jsonObject.get(clave).toString());
    }

    public Point getPuntoInicial() {
        return puntoInicial;
    }

    public Rectangle getBordes(final int posicionX, final int posicionY) {
        int x = MARGEN_X - posicionX + jugador.getAnchoJugador();
        int y = MARGEN_Y - posicionY + jugador.getAltoJugador();
        int ancho = this.anchoMapaTile * LADO_SPRITES - jugador.getAnchoJugador() * 2;
        int alto = this.altoMapaTile * LADO_SPRITES - jugador.getAltoJugador() * 2;
        return new Rectangle(x, y, ancho, alto);
    }

}
