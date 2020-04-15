package principal.control;

public class Tecla {
    private boolean pulseada = false;
    private long ultimaPulsacion = System.nanoTime();

    public void teclaPulseada() {
        pulseada = true;
        ultimaPulsacion = System.nanoTime();
    }

    public void teclaLiberada(){
        pulseada = false;
    }

    public boolean isPulseada() {
        return pulseada;
    }

    public long getUltimaPulsacion() {
        return ultimaPulsacion;
    }
}
