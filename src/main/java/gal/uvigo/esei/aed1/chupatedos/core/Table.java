package gal.uvigo.esei.aed1.chupatedos.core;

import java.util.Stack;

/**
 * Representa la mesa de juego donde se colocan las cartas.
 *
 * Esta clase utiliza una pila (Stack) para almacenar las cartas que se colocan
 * sobre la mesa. Permite agregar cartas a la mesa, obtener la carta superior,
 * contar cuántas cartas hay en la mesa y retirar todas las cartas excepto la
 * carta superior.
 */
public class Table {

    private final Stack<Card> mesa; //El Stack mesa no se puede reasignar por ser constante

    public Table() {
        mesa = new Stack<>();
    }

    /**
     * Coloca una carta en la mesa.
     *
     * @param carta {enum}: La carta a introducir en Stack.
     */
    public void colocarCarta(Card carta) {
        mesa.add(carta);
    }

    /**
     * Obtiene la carta superior de la mesa, la última ingresada.
     *
     * @return {enum} card: La carta superior (técnicamente devuelve lo que haya
     * en la posicion mesa.size() - 1 del Stack mesa, o 'null' si la mesa está
     * vacía.
     */
    public Card getCartaSuperior() {
        if (mesa.size() > 0) {
            return mesa.get(mesa.size() - 1);
        }
        return null;
    }

    /**
     * Obtiene el número de cartas que hay en la mesa.
     *
     * @return {int} La cantidad de cartas actualmente en el Stack.
     */
    public int numCartasEnMesa() {
        return mesa.size();
    }

    /**
     * Retira todas las cartas de la mesa excepto la carta superior.
     *
     * @return {Stack<>} reciclables: Una pila (Stack) de las cartas removidas.
     */
    public Stack<Card> retirarCartasMenosUltima() {
        Stack<Card> reciclables = new Stack<>();
        Card temp = mesa.pop();
        while (mesa.size() > 0) {
            reciclables.push(mesa.pop());
        }
        mesa.push(temp);
        return reciclables;
    }

    /**
     * Devuelve una representación en forma de cadena del estado actual de la
     * mesa, inlcuyendo la carta superior y el número total de cartas de la
     * mesa.
     *
     * @return {String} La cadena que describe el estado de la mesa.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("** ESTADO DE LA MESA **\t");
        sb.append("Carta superior: ").append(getCartaSuperior()).append("\t");
        sb.append("Cartas boca arriba en la mesa: ").append(numCartasEnMesa()).append("\n");
        //System.out.println(this.mesa); Para hacer Debugging
        return sb.toString();
    }
}
