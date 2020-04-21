package principal.sonido;

import principal.herramientas.CargadorRecursos;

import javax.sound.sampled.Clip;

public class Sonido {
    final private Clip sonido;

    public Sonido(final String ruta) {
        sonido = CargadorRecursos.cargarSonido(ruta);
    }

    public void reproducir(){
        sonido.stop();
        sonido.flush();
        sonido.setMicrosecondPosition(0);
        sonido.start();
    }

    public void repetir(){
        sonido.stop();
        sonido.flush();
        sonido.setMicrosecondPosition(0);
        sonido.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
