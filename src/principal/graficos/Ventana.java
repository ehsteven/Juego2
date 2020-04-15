package principal.graficos;

import principal.herramientas.CargadorRecursos;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Ventana extends JFrame {
    private static final long serialVersionUID = 5979421777239930009L;
    private String titulo;
    private final ImageIcon icono;
    public Ventana(final String titulo, final SuperficieDibujo sd){
        this.titulo = titulo;
        BufferedImage imagen = CargadorRecursos.cargarImagenCompatibleTranslucido("/icono/poo.png");
        this.icono = new ImageIcon(imagen);
        configurarVentana(sd);
    }

    private void configurarVentana(SuperficieDibujo sd) {
        setTitle(titulo);
        setIconImage(icono.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        add(sd, BorderLayout.CENTER);
        setUndecorated(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
