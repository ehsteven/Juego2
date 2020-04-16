package principal.maquinaEstado.estados.menuJuego;

import principal.GestorPrincipal;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.DibujoDebug;
import principal.herramientas.EscaladorElementos;
import principal.herramientas.GeneradorTooltip;
import principal.herramientas.MedidorString;
import principal.inventario.Objeto;
import principal.inventario.armas.Arma;
import principal.inventario.armas.Desarmado;

import java.awt.*;

import static principal.Constantes.*;
import static principal.ElementosPrincipales.*;

public class MenuEquipo extends SeccionMenu{
    final Rectangle panelObjetos = new Rectangle(em.FONDO.x + margenGeneral, barraPeso.y + barraPeso.height + margenGeneral,
            248, ALTO_JUEGO - barraPeso.y - barraPeso.height - margenGeneral * 45);
    final Rectangle tituloObjetos = new Rectangle(panelObjetos.x, panelObjetos.y, panelObjetos.width, 30);

    final Rectangle panelEquipo = new Rectangle(panelObjetos.x + panelObjetos.width + margenGeneral, panelObjetos.y,
            90, panelObjetos.height);
    final Rectangle tituloEquipo = new Rectangle(panelEquipo.x, panelEquipo.y, panelEquipo.width, 30);

    final Rectangle panelAtributos = new Rectangle(panelEquipo.x + panelEquipo.width + margenGeneral, panelObjetos.y,
            275, panelObjetos.height);
    final Rectangle tituloAtributos = new Rectangle(panelAtributos.x, panelAtributos.y, panelAtributos.width, 30);

    final Rectangle etiquitaArma = new Rectangle(tituloEquipo.x + margenGeneral, tituloEquipo.y + tituloEquipo.height + margenGeneral,
            tituloEquipo.width - margenGeneral * 2, margenGeneral * 2 + MedidorString.medirAltoPix(GestorPrincipal.getSd().getGraphics(), "ARMA"));

    final Rectangle contenedorArma = new Rectangle(etiquitaArma.x + 1, etiquitaArma.y + etiquitaArma.height, etiquitaArma.width - 2,
            LADO_SPRITES);
    Objeto objetoSeleccionado = null;


    public MenuEquipo(String nombreSeccion, Rectangle etiquetaMenu, EstructuraMenu em) {
        super(nombreSeccion, etiquetaMenu, em);
    }

    @Override
    public void actualizar() {
        actualizarPosicionesMenu();
        actualizarSeleccionRaton();
        actualizarObjetoSeleccionado();
    }

    private void actualizarPosicionesMenu(){
        if(inventario.getArmas().isEmpty())
            return;
        for(int i = 0; i < inventario.getArmas().size(); i++){
            final Point puntoInicial = new Point(tituloObjetos.x  + margenGeneral,
                    tituloObjetos.y + tituloObjetos.height + margenGeneral);
            final int lado = LADO_SPRITES;
            int idXActual = inventario.getArmas().get(i).getId_x();
            int idYActual = inventario.getArmas().get(i).getId_y();
            inventario.getObjeto(idXActual, idYActual).setPosicionMenu(new Rectangle(puntoInicial.x + i * (lado + margenGeneral), puntoInicial.y,
                    lado, lado));
        }
    }

    private void actualizarSeleccionRaton() {
        Rectangle posicionRaton = GestorPrincipal.getSd().getRaton().getRectanguloPosicion();
        if (posicionRaton.intersects(EscaladorElementos.escalarRectanguloArriba(panelObjetos))){
            if(inventario.getArmas().isEmpty())
                return;
            for (Objeto objeto: inventario.getArmas()) {
                if(GestorPrincipal.getSd().getRaton().isClickIzq() && posicionRaton.intersects(EscaladorElementos.escalarRectanguloArriba(objeto.getPosicionMenu()))){
                    objetoSeleccionado = objeto;
                }
            }
        }else if(posicionRaton.intersects(EscaladorElementos.escalarRectanguloArriba(panelEquipo))){
            if (objetoSeleccionado != null  && objetoSeleccionado instanceof Arma && GestorPrincipal.getSd().getRaton().isClickIzq() &&
            posicionRaton.intersects(EscaladorElementos.escalarRectanguloArriba(contenedorArma))){
                jugador.getAlmacenEquipo().setArma((Arma) objetoSeleccionado);
                objetoSeleccionado = null;
            }
        }else if(posicionRaton.intersects(EscaladorElementos.escalarRectanguloArriba(panelAtributos))){

        }
    }

    private void actualizarObjetoSeleccionado(){
        if(objetoSeleccionado != null){
            if(GestorPrincipal.getSd().getRaton().isClickDer()){
                objetoSeleccionado = null;
                return;
            }
            Point posicionRaton = EscaladorElementos.escalarPuntoAbajo(GestorPrincipal.getSd().getRaton().getPosicion());
            objetoSeleccionado.setPosicionFlotante(new Rectangle(posicionRaton.x, posicionRaton.y, LADO_SPRITES, LADO_SPRITES));

        }
    }

    @Override
    public void dibujar(Graphics g, SuperficieDibujo sd, EstructuraMenu em) {
        dibujarLimitePeso(g, em);
        dibujarPanales(g);

        if(sd.getRaton().getRectanguloPosicion().intersects(EscaladorElementos.escalarRectanguloArriba(barraPeso))){
            GeneradorTooltip.dibujarTooltip(g, sd,  jugador.pesoActual+ "/" + jugador.limitePeso);
        }
    }

    private void dibujarPanales(final Graphics g){
        dibujarPanelObjetos(g, panelObjetos, tituloObjetos, "EQUIPABLES");
        dibujarPanelEquipo(g, panelEquipo, tituloEquipo, "EQUIPO");
        dibujarPanelAtributos(g, panelAtributos, tituloAtributos, "ATRIBUTOS");
    }

    private void dibujarPanelObjetos(final Graphics g, final Rectangle panel, final Rectangle titularPanel, final String nombrePanel){
        dibujarPaneles(g, panelObjetos, tituloObjetos, nombrePanel);
        dibujarElementosEquipables(g, panel, titularPanel);

    }

    private void dibujarElementosEquipables(final Graphics g, final Rectangle panelObjetos, final Rectangle titularPanel){
        if(inventario.getArmas().isEmpty())
            return ;

        final Point puntoinicial = new Point(titularPanel.x + margenGeneral, titularPanel.y + titularPanel.height + margenGeneral);
        for (int i = 0; i < inventario.getArmas().size(); i++){
            int idActualX = inventario.getArmas().get(i).getId_x();
            int idActualY = inventario.getArmas().get(i).getId_y();
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
        if(objetoSeleccionado != null){
            DibujoDebug.dibujarImagen(g, objetoSeleccionado.getSprite().getImagen(), objetoSeleccionado.getPosicionFlotante().x, objetoSeleccionado.getPosicionFlotante().y);
        }
    }

    private void dibujarPanelEquipo(final Graphics g, final Rectangle panel, final Rectangle titularPanel, final String nombrePanel){
        dibujarPaneles(g, panel, titularPanel, nombrePanel);
        g.setColor(Color.BLACK);
        DibujoDebug.dibujarRectanguloRelleno(g, etiquitaArma);
        DibujoDebug.dibujarRectanguloContorno(g, contenedorArma);
        if(!(jugador.getAlmacenEquipo().getArma() instanceof Desarmado)){
            Point coordenadaImagen = new Point(contenedorArma.x + contenedorArma.width /2 - LADO_SPRITES / 2,
                    contenedorArma.y);
            DibujoDebug.dibujarImagen(g, jugador.getAlmacenEquipo().getArma().getSprite().getImagen(), coordenadaImagen);
        }
        g.setColor(Color.WHITE);
        DibujoDebug.dibujarString(g, "Arma", new Point(etiquitaArma.x + etiquitaArma.width / 2 - MedidorString.medirAnchoPix(g, "Arma")/2,
                etiquitaArma.y + etiquitaArma.height/ 2 + MedidorString.medirAltoPix(g, "Arma") / 2));

    }

    private void dibujarPanelAtributos(final Graphics g, final Rectangle panel, final Rectangle titularPanel, final String nombrePanel) {
        dibujarPaneles(g, panel, titularPanel, nombrePanel);

    }

    private void dibujarPaneles(final Graphics g, final Rectangle panel, final Rectangle tituloPanel, final String nombrePanel){
        g.setColor(COLOR_NARANJA);
        DibujoDebug.dibujarRectanguloContorno(g, panel);
        DibujoDebug.dibujarRectanguloRelleno(g, tituloPanel);
        DibujoDebug.dibujarString(g, nombrePanel, new Point(tituloPanel.x + tituloPanel.width / 2 - MedidorString.medirAnchoPix(g, nombrePanel)/2,
                tituloPanel.y + tituloPanel.height - MedidorString.medirAltoPix(g, nombrePanel) / 2 - 5), Color.WHITE);
    }

    public Objeto getObjetoSeleccionado() {
        return objetoSeleccionado;
    }
    public void eliminarObjetoSeleccionado(){
        objetoSeleccionado = null;
    }
}
