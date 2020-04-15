package principal.maquinaEstado.estados.menuJuego;

import principal.Constantes;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.DibujoDebug;
import principal.herramientas.EscaladorElementos;
import principal.herramientas.GeneradorTooltip;
import principal.herramientas.MedidorString;
import principal.inventario.Objeto;

import java.awt.*;

import static principal.Constantes.LADO_SPRITES;
import static principal.ElementosPrincipales.*;

public class MenuInventario extends SeccionMenu {
    private final int margenIzquierdo = 420;

    private final EstructuraMenu em;

    public MenuInventario(String nombreSeccion, Rectangle etiquetaMenu, EstructuraMenu em) {
        super(nombreSeccion, etiquetaMenu, em);
        this.em = em;
    }

    @Override
    public void actualizar() {
        actualizarPosicionesMenu();
    }

    private void actualizarPosicionesMenu(){
        if(inventario.getConsumibles().isEmpty())
            return;
        for(int i = 0; i < inventario.getConsumibles().size(); i++){
            final Point puntoInicial = new Point(em.FONDO.x + 16, barraPeso.y + barraPeso.height + margenGeneral);
            final int lado = LADO_SPRITES;
            int idXActual = inventario.getConsumibles().get(i).getId_x();
            int idYActual = inventario.getConsumibles().get(i).getId_y();
            inventario.getObjeto(idXActual, idYActual).setPosicionMenu(new Rectangle(puntoInicial.x + i *
                    (lado + margenGeneral), puntoInicial.y, lado, lado));
        }
    }

    @Override
    public void dibujar(Graphics g, SuperficieDibujo sd, EstructuraMenu em) {
        dibujarLimitePeso(g, em);
        if(sd.getRaton().getRectanguloPosicion().intersects(EscaladorElementos.escalarRectanguloArriba(barraPeso))){
            GeneradorTooltip.dibujarTooltip(g, sd,  jugador.pesoActual+ "/" + jugador.limitePeso);
        }
        dibujarElementosConsumibles(g, em);
        dibujarPaginador(g, em);
    }

    private void dibujarPaginador(final Graphics g, final EstructuraMenu em){
        final int anchoBoton = 32;
        final int altoBoton = 16;

        final Rectangle anterior = new Rectangle(em.FONDO.x + em.FONDO.width - (margenGeneral + 7) * 30 - anchoBoton * 2 + 22,
                em.FONDO.y + em.FONDO.height - (margenGeneral + 10)* 20 - altoBoton, anchoBoton, altoBoton);
        final Rectangle siguiente = new Rectangle(anterior.x + anterior.width + margenGeneral, anterior.y,
                anchoBoton, altoBoton);
        g.setColor(Color.BLUE);
        DibujoDebug.dibujarRectanguloRelleno(g, anterior);
        DibujoDebug.dibujarRectanguloRelleno(g, siguiente);
    }

    private void dibujarElementosConsumibles(final Graphics g, final EstructuraMenu em){
        if(inventario.getConsumibles().isEmpty())
            return ;

        //final Point puntoinicial = new Point(titularPanel.x + margenGeneral, titularPanel.y + titularPanel.height + margenGeneral);
        final Point puntoinicial = new Point(em.FONDO.x + 16, barraPeso.y + barraPeso.height + margenGeneral);

        for (int i = 0; i < inventario.getConsumibles().size(); i++){
            int idActualX = inventario.getConsumibles().get(i).getId_x();
            int idActualY = inventario.getConsumibles().get(i).getId_y();
            Objeto objetoActual = inventario.getObjeto(idActualX, idActualY);
            DibujoDebug.dibujarImagen(g, objetoActual.getSprite().getImagen(), objetoActual.getPosicionMenu().x, objetoActual.getPosicionMenu().y);
            DibujoDebug.dibujarRectanguloRelleno(g, puntoinicial.x + i * (LADO_SPRITES + margenGeneral) + LADO_SPRITES - 12,
                    puntoinicial.y + 32 - 8, 12, 8, Color.BLACK);
            String texto = "";
            if(objetoActual.getCantida() < 10)
                texto = "0" + objetoActual.getCantida();
            else
                texto = ""+ objetoActual.getCantida();
            g.setFont(g.getFont().deriveFont(10f));
            DibujoDebug.dibujarString(g, texto, puntoinicial.x + i * (LADO_SPRITES + margenGeneral) + LADO_SPRITES - MedidorString.medirAnchoPix(g, texto) - 2,
                    puntoinicial.y + 31, Color.WHITE);
        }
        g.setFont(g.getFont().deriveFont(12f));
    }
}
