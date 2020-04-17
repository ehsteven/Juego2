package principal.mapas;

import org.json.simple.*;
import org.json.simple.parser.*;
import principal.herramientas.CargadorRecursos;
import principal.inventario.Objeto;
import principal.sprites.Sprite;

import java.awt.*;
import java.util.ArrayList;

public class MapaTiled {
    private int anchoMapaTile;
    private int altoMapaTile;
    private Point puntoInicial;

    private ArrayList<CapaSprites> capasSprites;
    private ArrayList<CapaColisiones> capasColiciones;

    private ArrayList<Rectangle> areasColicionOriginales;

    private Sprite[] paletaSprites;

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

        //AVERIGUAR TOTAL DE SPRITES EXISTENTES EN TODAS LAS CAPAS
        JSONArray coleccionesSprites = getArrayJSON(globalJSON.get("tilesets").toString());
        int totalSprites = 0;
        for(int i = 0; i < coleccionesSprites.size(); i++){
            JSONObject datosGrupo = getObjetoJSON(coleccionesSprites.get(i).toString());
            totalSprites += getIntJSON(datosGrupo, "tilecount");
        }
        paletaSprites = new Sprite[totalSprites];

        //ASIGNAR SPRITES NECESARIOS A LA PALETA DE LAS CAPAS;

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
}
