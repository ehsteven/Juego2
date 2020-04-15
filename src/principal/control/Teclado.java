package principal.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Teclado implements KeyListener{
    public Tecla arriba = new Tecla();
    public Tecla abajo = new Tecla();
    public Tecla derecha = new Tecla();
    public Tecla izquierda = new Tecla();
    public boolean recogiendo = false;
    public boolean corriendo = false;
    public boolean debug = false;
    public boolean inventarioActivo = false;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W:
                arriba.teclaPulseada();
                break;
            case KeyEvent.VK_S:
                abajo.teclaPulseada();
                break;
            case KeyEvent.VK_A:
                izquierda.teclaPulseada();
                break;
            case KeyEvent.VK_D:
                derecha.teclaPulseada();
                break;
            case KeyEvent.VK_E:
                recogiendo = true;
                break;
            case KeyEvent.VK_SHIFT:
                corriendo = true;
                break;
            case KeyEvent.VK_F1:
                debug = !debug;
                break;
            case KeyEvent.VK_Q:
                inventarioActivo = !inventarioActivo;
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W:
                arriba.teclaLiberada();
                break;
            case KeyEvent.VK_S:
                abajo.teclaLiberada();
                break;
            case KeyEvent.VK_A:
                izquierda.teclaLiberada();
                break;
            case KeyEvent.VK_D:
                derecha.teclaLiberada();
                break;
            case KeyEvent.VK_E:
                recogiendo = false;
                break;
            case KeyEvent.VK_SHIFT:
                corriendo = false;
                break;

        }
    }
}