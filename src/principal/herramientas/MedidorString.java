package principal.herramientas;

import java.awt.*;

public class MedidorString {
    public static int medirAnchoPix(final Graphics g, final String s){
        FontMetrics fm = g.getFontMetrics();
        return fm.stringWidth(s);
    }

    public static int medirAltoPix(final Graphics g, final String s){
        FontMetrics fm = g.getFontMetrics();
        return (int)fm.getLineMetrics(s, g).getHeight();
    }
}
