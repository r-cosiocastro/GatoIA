package gatoia;

import java.util.ArrayList;
import java.util.List;

public class Tablero {

    List<Casilla> casillas = new ArrayList<>();

    public Tablero() {
        inicializarTablero();
    }

    private void inicializarTablero() {
        for (int x = 0; x < 9; x++) {
            casillas.add(new Casilla(this));
        }
    }

    void imprimirTablero(boolean imprimirNumero, int tabs) {
        System.out.println("");
        System.out.println("");
        int j = 0;
        String tab = "\t\t\t";
        for (int x = 0; x < 3; x++) {
            if (x == 1 || x == 2) {
                printTab(tabs,"-- --\n");
            }
            printTab(tabs,"");
            for (int y = 0; y < 3; y++) {
                Casilla c = this.casillas.get(j);
                if (c.jugador != null) {
                    if (y == 1) {
                        System.out.print("|" + c.jugador.ficha + "|");
                    } else {
                        System.out.print(c.jugador.ficha);
                    }
                } else {
                    if (y == 1) {
                        if (imprimirNumero) {
                            System.out.print("|" + (j + 1) + "|");
                        } else {
                            System.out.print("| |");
                        }
                    } else {
                        if (imprimirNumero) {
                            System.out.print(j + 1);
                        } else {
                            System.out.print(" ");
                        }
                    }
                }
                j++;
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println("");
    }

    void printTab(int tabs, Object obj) {
        for (int x = 0; x < tabs; x++) {
            System.out.print("\t");
        }
        System.out.print(obj);
    }

    boolean marcarCasilla(int posicion, Jugador jugadorActual) {
        if (this.casillas.get(posicion).jugador == null) {
            this.casillas.get(posicion).jugador = jugadorActual;
            return true;
        } else {
            return false;
        }
    }

    boolean tableroLleno() {
        return this.casillas.stream().noneMatch((c) -> (c.jugador == null));
    }

    boolean ganador(Jugador jugadorActual) {
        boolean win;

        // Horizontal
        for (int y = 0; y < 9; y += 3) {
            win = true;
            for (int x = 0; x < 3; x++) {
                if (this.casillas.get(y + x).jugador == null) {
                    win = false;
                    break;
                } else {
                    if (!this.casillas.get(y + x).jugador.equals(jugadorActual)) {
                        win = false;
                        break;
                    }
                }
            }
            if (win) {
                return win;
            }
        }

        win = true;

        // Vertical
        for (int y = 0; y < 3; y++) {
            win = true;
            for (int x = 0; x < 9; x += 3) {
                if (this.casillas.get(y + x).jugador == null) {
                    win = false;
                    break;
                } else {
                    if (!this.casillas.get(y + x).jugador.equals(jugadorActual)) {
                        win = false;
                        break;
                    }
                }
            }
            if (win) {
                return win;
            }
        }

        win = true;

        // Diagonal
        for (int x = 0; x < 9; x += 4) {
            if (this.casillas.get(x).jugador == null) {
                win = false;
                break;
            } else {
                if (!this.casillas.get(x).jugador.equals(jugadorActual)) {
                    win = false;
                    break;
                }
            }
        }
        if (win) {
            return win;
        }

        win = true;

        // Diagonal invertida
        for (int x = 2; x <= 6; x += 2) {
            if (this.casillas.get(x).jugador == null) {
                win = false;
                break;
            } else {
                if (!this.casillas.get(x).jugador.equals(jugadorActual)) {
                    win = false;
                    break;
                }
            }
        }

        if (win) {
            return win;
        }

        return win;
    }
}
