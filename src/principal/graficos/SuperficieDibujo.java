package principal.graficos;

import principal.Constantes;
import principal.GestorPrincipal;
import principal.control.GestorControles;
import principal.control.Raton;
import principal.herramientas.DatosDebug;
import principal.herramientas.DibujoDebug;
import principal.maquinaEstado.GestorEstados;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class SuperficieDibujo extends Canvas {
    public static final long serialVersionID = -6227038142688953660L;
    private int ancho;
    private int alto;
    private Raton raton;

    public SuperficieDibujo(final int ancho, final int alto){
        this.alto = alto;
        this.ancho = ancho;
        this.raton = new Raton(this);

        setCursor(raton.getCursor());
        setIgnoreRepaint(true);
        setPreferredSize(new Dimension(ancho, alto));
        addKeyListener(GestorControles.teclado);
        addMouseListener(raton);
        setFocusable(true);
        requestFocus();
    }

    public void actualizar(){
        raton.actualizar(this);
    }

    public void dibujar(GestorEstados ge){
        BufferStrategy buffer = getBufferStrategy();
        if (buffer == null){
            createBufferStrategy(3);
            return;
        }
        final Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
        DibujoDebug.reiniciarContadorObjetos();
        g.setFont(Constantes.FUENTE_PIXEL);
        DibujoDebug.dibujarRectanguloRelleno(g, 0,0, Constantes.ANCHO_PANTALLA_COMPLETA, Constantes.ALTO_PANTALLA_COMPLETA, Color.BLACK);

        if (Constantes.FACTOR_ESCALADO_X != 1.0 || Constantes.FACTOR_ESCALADO_Y != 1.0) {
            g.scale(Constantes.FACTOR_ESCALADO_X, Constantes.FACTOR_ESCALADO_Y);
        }

        ge.dibujar(g);
        g.setColor(Color.WHITE);
        DibujoDebug.dibujarString(g,"FPS: "+ GestorPrincipal.getFps(), 20, 20);
        DibujoDebug.dibujarString(g, "APS: "+ GestorPrincipal.getAps(), 20, 30);

        DatosDebug.enviarDatos("ESCALA X: "+ Constantes.FACTOR_ESCALADO_X);
        DatosDebug.enviarDatos("ESCALA X: "+ Constantes.FACTOR_ESCALADO_Y);
        DatosDebug.enviarDatos("OPS: "+DibujoDebug.getObjetosDibujados());

        if(GestorControles.teclado.debug)
            DatosDebug.dibujarDatos(g);
        else
            DatosDebug.vaciarDatos();

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
        buffer.show();
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public Raton getRaton() {
        return raton;
    }
}
