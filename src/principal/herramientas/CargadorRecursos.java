package principal.herramientas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

public class CargadorRecursos {
    public static BufferedImage cargarImagenCompatibleOpaca(final String ruta) {
        Image imagen = null;
        File file = null;
        try {
            URL resource = CargadorRecursos.class.getResource(ruta);
            file = new File(resource.getFile());
            imagen = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage imagenAcelerada = gc.createCompatibleImage(imagen.getWidth(null), imagen.getHeight(null), Transparency.OPAQUE);
        Graphics g = imagenAcelerada.getGraphics();
        g.drawImage(imagen, 0, 0, null);
        g.dispose();
        return imagenAcelerada;
    }

    public static BufferedImage cargarImagenCompatibleTranslucido(final String ruta){
        Image imagen = null;
        File file = null;
        try {
            URL resource = CargadorRecursos.class.getResource(ruta);
            file = new File(resource.getFile());
            imagen = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage imagenAcelerada = gc.createCompatibleImage(imagen.getWidth(null), imagen.getHeight(null), Transparency.TRANSLUCENT);
        Graphics g = imagenAcelerada.getGraphics();
        g.drawImage(imagen, 0, 0, null);
        g.dispose();
        return imagenAcelerada;
    }

    public static String leerArchivoTexto(final String ruta){
        String contenido = "";
        InputStream entradaBytes = CargadorRecursos.class.getResourceAsStream(ruta);
        BufferedReader lector = new BufferedReader(new InputStreamReader(entradaBytes));
        String linea;
        try{
            while( (linea = lector.readLine()) != null ) {
                contenido += linea;
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(entradaBytes != null){
                    entradaBytes.close();
                }
                if(lector != null){
                    lector.close();
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
        return contenido;
    }

    public static Font cargarFuente(final String ruta){
        Font fuente = null;
        InputStream entradaBytes  = CargadorRecursos.class.getResourceAsStream(ruta);
        try {
            fuente = Font.createFont(Font.TRUETYPE_FONT, entradaBytes);
            fuente = fuente.deriveFont(12f);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fuente;
    }
}