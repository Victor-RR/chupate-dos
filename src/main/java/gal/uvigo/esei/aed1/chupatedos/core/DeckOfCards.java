package gal.uvigo.esei.aed1.chupatedos.core;

import java.util.Collections;
import java.util.Random;
import java.util.Stack;

/**
 * Representa una baraja de cartas utilizando una pila (Stack).
 *
 * La baraja se inicializa con todas las cartas definidas en el enumerado
 * "Card". Dispone de métodos para extraer una carta, devolver una carta a la
 * baraja, barajar, consultar si la baraja está vacía y obtener el número de
 * cartas restantes.
 */
public class DeckOfCards {

    private final Stack<Card> cartas; //El Stack cartas no se puede reasignar por ser constante

    /**
     * Crea una pila (Stak) y la inicializa con todos los elementos definidos en
     * "Card".
     */
    public DeckOfCards() {
        cartas = new Stack<>();
        for (Card card : Card.values()) {
            cartas.push(card);
        }
    }

    /**
     * Extrae y devuelve la carta en la parte superior de la baraja.
     *
     * @return {enum} La carta extraída de la baraja.
     * @throws java.util.EmptyStackException si la baraja está vacía.
     */
    public Card sacarCarta() {
        return this.cartas.pop();
    }

    /**
     * Coloca una carta en la parte superior del Stack.
     *
     * @param c {enum}: La carta a agregar.
     */
    public void ponerCarta(Card c) {
        cartas.push(c);
    }

    /**
     * Comprueba si la baraja está vacía.
     *
     * @return {boolean} 'true' si no quedan cartas en la baraja; {boolean}
     * 'false' si aún quedan.
     */
    public boolean isEmpty() {
        return this.cartas.empty();
    }

    /**
     * Comprueba si la baraja tiene al menos 2 cartas (para que si hay que robar
     * por carta especial [2] haya suficientes).
     *
     * @return {boolean} 'true' si no quedan al menos dos cartas en la baraja;
     * {boolean} 'false' si quedan más.
     */
    public boolean noSuficientesCartas() {//Se podría poner la lógica de esta función directamente como condición
        return cartasRestantes() < 2;
    }

    /**
     * Devuelve el número de cartas que quedan en la baraja.
     *
     * @return {int} La cantidad de cartas restantes.
     */
    public int cartasRestantes() {
        return cartas.size();
    }

    /**
     * Baraja de manera aleatoria las cartas de la baraja.
     */
    public void barajar() {
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        Collections.shuffle(cartas, random);
    }

    /**
     * Devuelve una representación en cadena del estado actual de la baraja,
     * indicando cuántas cartas quedan.
     *
     * @return {String} La cadena con la información de la baraja.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("En la baraja quedan: ");
        sb.append(this.cartasRestantes()).append("\n");
        //System.out.println(this.cartas);  Para hacer Debugging
        return sb.toString();
    }
}
