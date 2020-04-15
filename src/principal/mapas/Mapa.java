package principal.mapas;

import principal.Constantes;
import principal.control.GestorControles;
import principal.herramientas.CargadorRecursos;
import principal.herramientas.DibujoDebug;
import principal.inventario.ContenedorObjetos;
import principal.sprites.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static principal.Constantes.LADO_SPRITES;
import static principal.ElementosPrincipales.inventario;
import static principal.ElementosPrincipales.jugador;

public class Mapa {
    private String[] partes;
    private final int ancho;
    private final int alto;
    private final Point posicionInicial;
    private final Point puntoSalida;
    private String siguienteMapa;
    private final Sprite[] paleta;
    private final boolean[] colisiones;
    public ArrayList<Rectangle> areasColision = new ArrayList<Rectangle>();
    public ArrayList<ContenedorObjetos> objetosMapa;

    private final int[] sprites;
    private final int margenX = Constantes.ANCHO_JUEGO / 2 - LADO_SPRITES / 2;
    private final int margenY = Constantes.ALTO_JUEGO / 2 - LADO_SPRITES / 2;
    private Rectangle zonaSalida;

    public Mapa(final String ruta){
        String contenido = CargadorRecursos.leerArchivoTexto(ruta);
        partes = contenido.split("\\*");
        ancho = Integer.parseInt(partes[0]);
        alto = Integer.parseInt(partes[1]);

        //partes de las hojas usadas
        String hojasUtilizadas = partes[2];
        String hojasSeparadas[] = hojasUtilizadas.split(",");

        //partes de la paleta de Sprites
        String paletaEntera = partes[3];
        String[] partesPaleta = paletaEntera.split("#");
        paleta = setSprites(partesPaleta, hojasSeparadas);

        //partes de colisiones
        String colisionesEnteras = partes[4];
        colisiones = extraerColisiones(colisionesEnteras);

        //partes de los sprites en el mapa
        String spritesEnteros = partes[5];
        String cadenasSprites[] = spritesEnteros.split(" ");
        sprites = extraerSprites(cadenasSprites);

        String posicion = partes[6];
        String[] posiciones = posicion.split("-");
        posicionInicial = new Point();
        posicionInicial.x = Integer.parseInt(posiciones[0]) * LADO_SPRITES;
        posicionInicial.y = Integer.parseInt(posiciones[1]) * LADO_SPRITES;

        String salida = partes[7];
        String[] datosSalida = salida.split("-");
        puntoSalida = new Point();
        puntoSalida.x = Integer.parseInt(datosSalida[0]);
        puntoSalida.y = Integer.parseInt(datosSalida[1]);
        siguienteMapa = datosSalida[2];
        zonaSalida = new Rectangle();
        if(partes.length > 7){
            String informacionObjetos = partes[8];
            objetosMapa = asignarObjetos(informacionObjetos);
        }
    }

    private Sprite[] setSprites(final String[] partesPaleta, final String[] hojasSeparadas){
        Sprite[] paleta = new Sprite[partesPaleta.length];
        HojaSprites hoja = new HojaSprites("/texturas/"+hojasSeparadas[0]+".PNG", 32, false);
        for(int i = 0; i < partesPaleta.length; i++){
            String spriteTemp = partesPaleta[i];
            String[] partesSprite = spriteTemp.split("-");
            int indicePaleta = Integer.parseInt(partesSprite[0]);
            int indiceSpriteHoja = Integer.parseInt(partesSprite[2]);
            paleta[indicePaleta] = hoja.getSprite(indiceSpriteHoja);
        }
        return paleta;
    }

    private boolean[] extraerColisiones(final String cadenasColisiones ){
        boolean[] colisiones = new boolean[cadenasColisiones.length()];
        for(int i = 0; i < cadenasColisiones.length(); i++){
            if(cadenasColisiones.charAt(i) == '0')
                colisiones[i] = false;
            else
                colisiones[i] = true;
        }
        return colisiones;
    }

    private ArrayList<ContenedorObjetos> asignarObjetos(final String informacionObjetos) {
        final ArrayList<ContenedorObjetos> objetos = new ArrayList<ContenedorObjetos>();
        String[] contenedoresObjetos = informacionObjetos.split("#");
        for(String contenedoresIndividual: contenedoresObjetos){
            ArrayList<Integer> idObjeto = new ArrayList<>();
            ArrayList<Integer> cantidaObjeto = new ArrayList<>();
            String[] divisionInformacionObjeto = contenedoresIndividual.split(":");
            String[] coordenadas = divisionInformacionObjeto[0].split(",");
            Point posicionContenedor = new Point(Integer.parseInt(coordenadas[0]), Integer.parseInt(coordenadas[1]));
            String[] objetosCantidades = divisionInformacionObjeto[1].split("/");
            for(String objetoActual: objetosCantidades){
                String[] datosObjetosActual = objetoActual.split("-");
                idObjeto.add(Integer.parseInt(datosObjetosActual[0]));
                cantidaObjeto.add(Integer.parseInt(datosObjetosActual[1]));
            }
            int[] idObjetosArray = new int[idObjeto.size()];
            int[] cantidaObjetoArray = new int[cantidaObjeto.size()];

            for(int i = 0; i < idObjetosArray.length; i++){
                idObjetosArray[i] = idObjeto.get(i);
                cantidaObjetoArray[i] = cantidaObjeto.get(i);
            }
            try{
                ContenedorObjetos contenedor = new ContenedorObjetos(posicionContenedor, idObjetosArray, cantidaObjetoArray);
                objetos.add(contenedor);
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("no lo hizo");
            }
        }
        return objetos;
    }

    private int[] extraerSprites(final String[] cadenaSprites){
        ArrayList<Integer> sprites = new ArrayList<>();
        for(int i = 0; i < cadenaSprites.length; i++){
            if(cadenaSprites[i].length() == 2){
                sprites.add(Integer.parseInt(cadenaSprites[i]));
            }else{
                String uno = "";
                String dos = "";
                String error = cadenaSprites[i];
                uno += error.charAt(0);
                uno += error.charAt(1);
                dos += error.charAt(2);
                dos += error.charAt(3);
                sprites.add(Integer.parseInt(uno));
                sprites.add(Integer.parseInt(dos));
            }
        }
        int[] vectorSprites = new int[sprites.size()];
        for(int i = 0; i < sprites.size(); i++ ){
            vectorSprites[i] = sprites.get(i);
        }
        return vectorSprites;
    }

    public void actualizar(){
        actualizarAreasColision();
        actualizarZonaSalida();
        actualizarRecogidaObjetos();
    }

    private void actualizarAreasColision(){
        if(!areasColision.isEmpty()){
            areasColision.clear();
        }
        for(int y = 0; y < this.alto; y++){
            for(int x = 0; x < this.ancho; x++){
                int puntoX = x * LADO_SPRITES - jugador.getPosicionXInt() + margenX;
                int puntoY = y * LADO_SPRITES - jugador.getPosicionYInt() + margenY;
                if (colisiones[x + y * this.ancho]){
                    final Rectangle r = new Rectangle(puntoX, puntoY, LADO_SPRITES, LADO_SPRITES);
                    areasColision.add(r);
                }
            }
        }
    }

    private void actualizarZonaSalida(){
        int puntoX = ((int) puntoSalida.getX()) * LADO_SPRITES - jugador.getPosicionXInt() + margenX;
        int puntoY = ((int) puntoSalida.getY()) * LADO_SPRITES - jugador.getPosicionYInt() + margenY;
        zonaSalida = new Rectangle(puntoX, puntoY+15, LADO_SPRITES, LADO_SPRITES - 15);
    }

    private void actualizarRecogidaObjetos() {
        if (!objetosMapa.isEmpty()) {
            final Rectangle areaJugador = new Rectangle(jugador.getPosicionXInt(), jugador.getPosicionYInt(), LADO_SPRITES, LADO_SPRITES);
            for (int i = 0; i < objetosMapa.size(); i++) {
                final ContenedorObjetos contenedor = objetosMapa.get(i);
                final Rectangle posicionContenedor = new Rectangle(contenedor.getPosicion().x * LADO_SPRITES, contenedor.getPosicion().y * LADO_SPRITES,
                        LADO_SPRITES, LADO_SPRITES);
                if (areaJugador.intersects(posicionContenedor) && GestorControles.teclado.recogiendo){
                    inventario.recogerObjeto(contenedor);
                    objetosMapa.remove(i);
                }
            }
        }
    }

    public void dibujar(Graphics g) {
        for (int y = 0; y < this.alto; y++) {
            for (int x = 0; x < this.ancho; x++) {
                int s = sprites[x + y * this.ancho];
                BufferedImage imagen = paleta[sprites[x + y * this.ancho]].getImagen();
                int puntoX = x * LADO_SPRITES - jugador.getPosicionXInt() + margenX;
                int puntoY = y * LADO_SPRITES - jugador.getPosicionYInt() + margenY;
                DibujoDebug.dibujarImagen(g, imagen, puntoX, puntoY);
            }
        }
        if(!objetosMapa.isEmpty()){
            for(ContenedorObjetos contenedor : objetosMapa){
                final int puntoX = contenedor.getPosicion().x * LADO_SPRITES - jugador.getPosicionXInt() + margenX;
                final int puntoY = contenedor.getPosicion().y * LADO_SPRITES - jugador.getPosicionYInt() + margenY;
                contenedor.dibujar(g, puntoX, puntoY);
            }
        }
    }

    public Rectangle getBordes(final int posicionX, final int posicionY){
        int x = margenX - posicionX + jugador.getAnchoJugador();
        int y = margenY - posicionY + jugador.getAltoJugador();
        int ancho = this.ancho * LADO_SPRITES - jugador.getAnchoJugador() * 2;
        int alto  = this.alto * LADO_SPRITES - jugador.getAltoJugador() * 2;
        return new Rectangle(x, y, ancho, alto);
    }
    public Point getPosicionInicial() {
        return posicionInicial;
    }

    public Rectangle getZonaSalida() {
        return zonaSalida;
    }

    public Point getPuntoSalida() {
        return puntoSalida;
    }

    public String getSiguienteMapa() {
        return siguienteMapa;
    }
}