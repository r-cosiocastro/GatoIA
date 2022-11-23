/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gatoia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author krawz
 */
public class GatoIA {

    Tablero tableroPrincipal;
    int movimientos = 0;
    int dificultad = 1;
    List<Jugador> jugadores = new ArrayList<>();
    boolean ganador = false;
    boolean imprimirNumeros = true;
    Scanner sc = new Scanner(System.in);
    final float infinito_negativo = Float.NEGATIVE_INFINITY;
    final int PROF_MAX = 9;

    public GatoIA() {
        tableroPrincipal = new Tablero();
        Jugador jugador1 = new Jugador("Jugador 1", 'X', true);
        Jugador jugador2 = new Jugador('O', false);
        jugadores.add(jugador1);
        jugadores.add(jugador2);
        if (!jugador1.isHuman() && !jugador2.isHuman()) {
            jugador1.nombre = "CPU 1";
            jugador2.nombre = "CPU 2";
        }
        Jugador jugadorActual = jugadores.get(0);
        int posicion = 1;
        while (!tableroPrincipal.tableroLleno()) {
            if (jugadorActual.equals(jugador1)) {
                print("\t----------------Turno de " + jugadorActual.nombre + "-----------------");
                tableroPrincipal.imprimirTablero(imprimirNumeros, 1);
            } else {
                print("\t\t\t\t----------------Turno de " + jugadorActual.nombre + "-----------------");
                tableroPrincipal.imprimirTablero(imprimirNumeros, 4);
            }
            if (jugadorActual.isHuman()) {
                boolean valorValido = false;
                while (!valorValido) {
                    println("Ingrese el espacio donde se quiere colocar (1-9)");
                    try {
                        posicion = sc.nextInt();
                        if (posicion > 9 || posicion < 1) {
                            System.err.println("Ingresa una posición válida");
                        } else {
                            valorValido = true;
                        }
                    } catch (Exception ex) {
                        System.err.println("Ingresa un número válido");
                        sc.next();
                    }
                }
                if (!marcar(tableroPrincipal, posicion - 1, jugadorActual)) {
                    jugadorActual = cambiarTurno(jugadorActual);
                }
            } else {
                juegaIA(tableroPrincipal, jugadorActual);
                movimientos++;
            }

            if (!tableroPrincipal.ganador(jugadorActual)) {
                jugadorActual = cambiarTurno(jugadorActual);
            } else {
                ganador = true;
                break;
            }
        }
        tableroPrincipal.imprimirTablero(imprimirNumeros, 2);
        if (ganador) {
            if (jugadorActual.isHuman()) {
                println("\t\tGanó el jugador " + jugadorActual.nombre);
            } else {
                println("\t\tGanó la CPU");
            }
        } else {
            println("\t      ¡Empate!");
            println("\n");
        }
    }

    void juegaIA(Tablero tablero, Jugador jugadorActual) {
        int mejorJugada = 0;
        float valor = infinito_negativo;
        float t;
        List<Integer> movs = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            if (tablero.casillas.get(i).jugador == null) {
                marcar(tablero, i, jugadorActual);
                t = -miniMax(tablero, cambiarTurno(jugadorActual), 0);
                tablero.casillas.get(i).jugador = null;
                if (t > valor) {
                    valor = t;
                    mejorJugada = i;
                }
                movs.add(i);
            }
        }
        if (movimientos == 0) {
            if (dificultad == 1 && tablero.casillas.get(4).jugador == null) {
                mejorJugada = 4;
            }
        }
        marcar(tablero, mejorJugada, jugadorActual);
        System.out.println(jugadorActual.nombre + " ocupó el espacio " + (mejorJugada + 1) + "\n");
    }

    float miniMax(Tablero tablero, Jugador jugador, int profundidad) {
        float valor, t;

        if (tablero.ganador(cambiarTurno(jugador))) {
            return -1;
        }
        if (tablero.tableroLleno()) {
            return 0;
        }
        if (profundidad >= PROF_MAX) {
            return 0;
        }

        valor = infinito_negativo;
        for (int i = 0; i < 9; i++) {
            if (tablero.casillas.get(i).jugador == null) {
                marcar(tablero, i, jugador);
                t = -miniMax(tablero, cambiarTurno(jugador), profundidad + 1);
                if (t > valor) {
                    valor = t;
                }
                tablero.casillas.get(i).jugador = null;
            }
        }
        return valor;
    }

    Jugador cambiarTurno(Jugador jugadorAct) {
        Jugador jugadorActual = jugadorAct;
        for (Jugador jugador : jugadores) {
            if (!jugador.equals(jugadorAct)) {
                jugadorActual = jugador;
                break;
            }
        }
        return jugadorActual;
    }

    boolean marcar(Tablero tablero, int posicion, Jugador jugadorActual) {
        if (!tablero.marcarCasilla(posicion, jugadorActual)) {
            System.err.println("Casilla " + (posicion + 1) + " ocupada");
            return false;
        }
        return true;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new GatoIA();
    }

    void println(Object obj) {
        System.out.println(obj);

    }

    void print(Object obj) {
        System.out.print(obj);
    }

}
