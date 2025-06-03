package gal.uvigo.esei.aed1.chupatedos.iu;

import java.util.Scanner;

/**
 * Clase de Interfaz de Usuario que permite la interacción con el usuario a
 * través de la consola.
 *
 * Dispone de métodos para leer números y cadenas de texto, mostrar mensajes y
 * solicitar la entrada de nombres de jugadores.
 */
public class IU {

    private final Scanner keyboard;

    public IU() {
        keyboard = new Scanner(System.in);
    }

    /**
     * Solicita al usuario un número como String y lo convierte a entero, si el
     * número ingresado no es válido vuelve a solicitarlo.
     *
     * Además muestra un mensaje al usuario (pidiendo el número)
     *
     * @param msg {String}: El mensaje a visualizar por el usuario.
     * @return toret {int}: El numero leído como entero.
     */
    public int readNumber(String msg) {
        boolean repeat;
        int toret = 0;

        do {
            repeat = false;
            System.out.print(msg);
            try {
                toret = Integer.parseInt(keyboard.nextLine());
            } catch (NumberFormatException exc) {
                repeat = true;
            }
        } while (repeat);

        return toret;
    }

    /**
     * Lee un String de teclado y muestra un mensaje al usuario (pidiendo la
     * cadena)
     *
     * @param msg {String}: El mensaje a visualizar por el usuario.
     * @return toret {String}: El string leído
     */
    public String readString(String msg) {
        String toret;
        System.out.print(msg);
        toret = keyboard.nextLine();
        return toret;
    }

    /**
     * Muestra un mensaje por pantalla
     *
     * @param msg {String}: El mensaje a visualizar por el usuario.
     */
    public void displayMessage(String msg) {
        System.out.println(msg);
    }

    /**
     * Solicita y almacena los jugadores en un array jugadores.
     *
     * Primero se obtiene el número de jugadores (entre 2 y 5) y luego se
     * solicita el nombre de cada uno.
     *
     * @return {String[]} jugadores: Array con los nombres de los jugadores.
     */
    public String[] pedirJugadores() {
        int numJugadores = obtenerNumJugadores();
        String[] jugadores = obtenerNombreJugadores(numJugadores);
        return jugadores;
    }

    /**
     * Solicita al usuario el número de jugadores.
     *
     * El número ha de ser válido, entre 2 y 5.
     *
     * @return {int} numJugadores: El número de jugadores.
     */
    private int obtenerNumJugadores() {
        int numJugadores = -1;
        do {
            numJugadores = readNumber("Introduce el número de jugadores: ");

        } while (numJugadores < 2 || numJugadores > 5);

        return numJugadores;
    }

    /**
     * Solicita los nombres de los jugadores.
     *
     * @param {int} numJugadores: El número total de jugadores y el que será el
     * tamaño del Array de nombres.
     * @return {String[]} nombres: Array con los nombres introducidos.
     */
    private String[] obtenerNombreJugadores(int numJugadores) {

        String[] nombres = new String[numJugadores];
        String nombre;

        for (int i = 0; i < numJugadores; i++) {
            nombre = readString("Intoduzca el nombre jugador " + (i + 1) + ": " + "\t");
            nombres[i] = nombre;
        }
        System.out.println("\n");
        return nombres;

    }

}
