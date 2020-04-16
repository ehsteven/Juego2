package principal.entes;

public class RegistroEnemigos {
    public static Enemigo getEnemigo(final int idEnemigo){
        Enemigo enemigo = null;
        switch (idEnemigo){
            case 1:
                enemigo = new Zombie(idEnemigo, "Zombie", 10);
                break;
        }
        return enemigo;
    }
}
