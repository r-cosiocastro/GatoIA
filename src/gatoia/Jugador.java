package gatoia;

public class Jugador {

    String nombre;
    char ficha;
    boolean human;

    public char getFicha() {
        return ficha;
    }

    public void setFicha(char ficha) {
        this.ficha = ficha;
    }

    public Jugador(char ficha, boolean human) {
        this.ficha = ficha;
        this.human = human;
        if (!human) {
            this.nombre = "CPU";
        }
    }

    public Jugador(String nombre, char ficha, boolean human) {
        this.nombre = nombre;
        this.ficha = ficha;
        this.human = human;
    }

    public boolean isHuman() {
        return human;
    }

}
