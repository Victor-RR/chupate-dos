package src;

/**
 * Enumerado que representa todas las posibles cartas de la baraja española.
 *
 * Cada carta tiene un número y un palo (definidos en la clase Suit).
 *
 * Los palos posibles son: OROS, COPAS, ESPADAS y BASTOS.
 *
 * Las cartas numeradas van del 1 al 7 y las figuras: sota, caballo y rey tienen
 * valores 10, 11 y 12 respectivamente.
 */
public enum Card {

    // Palos de OROS
    AS_OROS(1, Suit.OROS),
    DOS_OROS(2, Suit.OROS),
    TRES_OROS(3, Suit.OROS),
    CUATRO_OROS(4, Suit.OROS),
    CINCO_OROS(5, Suit.OROS),
    SEIS_OROS(6, Suit.OROS),
    SIETE_OROS(7, Suit.OROS),
    SOTA_OROS(10, Suit.OROS),
    CABALLO_OROS(11, Suit.OROS),
    REY_OROS(12, Suit.OROS),
    // Palos de COPAS
    AS_COPAS(1, Suit.COPAS),
    DOS_COPAS(2, Suit.COPAS),
    TRES_COPAS(3, Suit.COPAS),
    CUATRO_COPAS(4, Suit.COPAS),
    CINCO_COPAS(5, Suit.COPAS),
    SEIS_COPAS(6, Suit.COPAS),
    SIETE_COPAS(7, Suit.COPAS),
    SOTA_COPAS(10, Suit.COPAS),
    CABALLO_COPAS(11, Suit.COPAS),
    REY_COPAS(12, Suit.COPAS),
    // Palos de ESPADAS
    AS_ESPADAS(1, Suit.ESPADAS),
    DOS_ESPADAS(2, Suit.ESPADAS),
    TRES_ESPADAS(3, Suit.ESPADAS),
    CUATRO_ESPADAS(4, Suit.ESPADAS),
    CINCO_ESPADAS(5, Suit.ESPADAS),
    SEIS_ESPADAS(6, Suit.ESPADAS),
    SIETE_ESPADAS(7, Suit.ESPADAS),
    SOTA_ESPADAS(10, Suit.ESPADAS),
    CABALLO_ESPADAS(11, Suit.ESPADAS),
    REY_ESPADAS(12, Suit.ESPADAS),
    // Palos de BASTOS
    AS_BASTOS(1, Suit.BASTOS),
    DOS_BASTOS(2, Suit.BASTOS),
    TRES_BASTOS(3, Suit.BASTOS),
    CUATRO_BASTOS(4, Suit.BASTOS),
    CINCO_BASTOS(5, Suit.BASTOS),
    SEIS_BASTOS(6, Suit.BASTOS),
    SIETE_BASTOS(7, Suit.BASTOS),
    SOTA_BASTOS(10, Suit.BASTOS),
    CABALLO_BASTOS(11, Suit.BASTOS),
    REY_BASTOS(12, Suit.BASTOS);

    private final int number; // Podrá tomar valores desde 1 hasta 12
    private final Suit suit;

    /**
     * Constructor para crear una carta con un número y palo específicos.
     *
     * @param {int} number: el valor de la carta.
     * @param {enum} suit: el palo de la carta.
     */
    Card(int number, Suit suit) {
        this.number = number;
        this.suit = suit;
    }

    public int getNumber() {
        return this.number;
    }

    public Suit getSuit() {
        return this.suit;
    }

    /**
     * Decuelve una representación en forma de cadena de la carta, formateada
     * con el palo y el número correspondiente.
     *
     * @return {String} La cadena que representa la carta.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        StringBuilder suitToDisplay = new StringBuilder();
        switch (this.suit.name()) {
            case "OROS" ->
                suitToDisplay.append(" ").append(this.suit).append("  ");
            case "COPAS" ->
                suitToDisplay.append(" ").append(this.suit).append(" ");
            case "ESPADAS" ->
                suitToDisplay.append(this.suit);
            case "BASTOS" ->
                suitToDisplay.append(this.suit).append(" ");
        }
        StringBuilder numberToDisplay = new StringBuilder();
        numberToDisplay.append(this.number < 10 ? "0" + this.number : this.number);
        sb.append("[").append(suitToDisplay).append("|").append(numberToDisplay).append("]");

        return sb.toString();
    }

}
