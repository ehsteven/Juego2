package principal.mapas;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import principal.herramientas.CargadorRecursos;
import principal.inventario.Objeto;

public class MapaTiled {
    private int anchoMapaTile;
    private int altoMapaTile;
    public MapaTiled(final String ruta) {
        String contenido = CargadorRecursos.leerArchivoTexto(ruta);

        JSONObject globalJSON = getObjetoJSON(contenido);
        anchoMapaTile = getIntJSON(globalJSON, "width");
        altoMapaTile = getIntJSON(globalJSON, "height");



    }

    private JSONObject getObjetoJSON(final String codigoJSON){
        JSONParser lector = new JSONParser();
        JSONObject jsonObject = null;
        try{
            Object recuperado = lector.parse(codigoJSON);
            jsonObject = (JSONObject) recuperado;

        }catch (ParseException e){
            System.out.println("Posicion: "+e.getPosition());
            System.out.println(e);
        }
        return jsonObject;
    }

    private JSONArray getArrayJSON(final String codigoJSON){
        JSONParser lector = new JSONParser();
        JSONArray jsonArray = null;
        try{
            Object recuperado = lector.parse(codigoJSON);
            jsonArray = (JSONArray) recuperado;

        }catch (ParseException e){
            System.out.println("Posicion: "+e.getPosition());
            System.out.println(e);
        }
        return jsonArray;
    }

    private int getIntJSON(final JSONObject jsonObject, final String clave){

        return Integer.parseInt(jsonObject.get(clave).toString());
    }
}
