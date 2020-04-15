package principal.inventario;

import principal.inventario.armas.Arma;
import principal.inventario.consumibles.Consumible;

import java.util.ArrayList;

public class Inventario{
    public final ArrayList<Objeto> objetos;

    public Inventario() {
        objetos = new ArrayList<Objeto>();
    }

    public boolean incrementarObjeto(final Objeto objeto, final int cantidad){
        boolean incrementado = false;
        for(Objeto objetoActual : objetos){
            if(objetoActual.getId_x() == objeto.getId_x() && objetoActual.getId_y() == objeto.getId_y()){
                objetoActual.incrementarCantidad(cantidad);
                incrementado = true;
                break;
            }
        }
        return incrementado;
    }

    public void recogerObjeto(final ContenedorObjetos co){
        for(Objeto objeto : co.getObjetos()){
            if (objetoExiste(objeto))
                incrementarObjeto(objeto, objeto.getCantida());
            else
                objetos.add(objeto);
        }
    }

    public boolean objetoExiste(final Objeto objeto){
        boolean existe = false;
        for (Objeto objetoActual : objetos){
            if(objetoActual.getId_x() == objeto.getId_x() && objetoActual.getId_y() == objeto.getId_y()){
                existe = true;
                break;
            }
        }
        return existe;
    }

    public ArrayList<Objeto> getConsumibles(){
        ArrayList<Objeto> consumibles = new ArrayList<>();
        for(Objeto objeto : objetos){
            if (objeto instanceof Consumible)
                consumibles.add(objeto);
        }
        return consumibles;
    }

    public ArrayList<Objeto> getArmas(){
        ArrayList<Objeto> armas = new ArrayList<>();
        for(Objeto objeto : objetos){
            if (objeto instanceof Arma)
                armas.add(objeto);
        }
        return armas;
    }

    public Objeto getObjeto(final int idx, final int idy){
        for (Objeto objetoActual : objetos){
            if(objetoActual.getId_x() == idx && objetoActual.getId_y() == idy)
                return objetoActual;
        }
        return null;
    }
}