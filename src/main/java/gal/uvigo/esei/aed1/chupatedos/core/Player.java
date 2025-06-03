package gal.uvigo.esei.aed1.chupatedos.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Representa a un jugador en el juego.
 *
 * Cada jugador tiene un nombre y una mano de cartas (7).
 */
public class Player {

    private final String nombre;
    private final List<Card> manoDeCartas; //La Lista manoDeCartas no se puede reasignar por ser constante

    /**
     * Crea un nuevo jugador con el nombre especificado y una mano de cartas
     * vacía.
     *
     * @param nombre {String}: El nombre del jugador.
     */
    public Player(String nombre) {
        this.nombre = nombre;
        manoDeCartas = new LinkedList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public List<Card> getManoDeCartas() {
        return manoDeCartas;
    }

    /**
     * Inserta una carta en la mano del jugador.
     *
     * @param carta {enum}: La carta a insertar.
     */
    public void insertarCartaALaMano(Card carta) {
        this.manoDeCartas.add(carta);
    }

    /**
     * Obtiene una lista de cartas jugables a partir de la carta en la mesa.
     *
     * Se considera jugable una carta de la mano si tiene el mismo número o el
     * mismo palo que la carta indicada.
     *
     * @param cartaMesa {enum}: La carta que está en la mesa para comparar.
     * @return {List<>}: La lista de cartas que se pueden jugar.
     */
    public List<Card> obtenerCartasJugables(Card cartaMesa) {
        List<Card> jugables = new ArrayList<>();
        for (Card c : manoDeCartas) {
            if (c.getNumber() == cartaMesa.getNumber() || c.getSuit() == cartaMesa.getSuit()) {
                jugables.add(c);
            }
        }
        return jugables;
    }

    /**
     * Devuelve una representación en cadena de la mano de cartas del jugador,
     * incluyendo el nombre del jugador y una enumeración de las cartas en su
     * mano.
     *
     * @return {String} La cadena que representa al jugador y sus cartas.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cartas de ").append(nombre);
        int i = 1;
        for (Card carta : manoDeCartas) {
            sb.append("(").append(i).append(")->").append(carta);
            i++;
        }
        return sb.toString();
    }

}
