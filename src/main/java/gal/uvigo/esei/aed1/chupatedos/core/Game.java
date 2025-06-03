package gal.uvigo.esei.aed1.chupatedos.core;

import gal.uvigo.esei.aed1.chupatedos.iu.IU;
import java.util.List;
import java.util.Stack;

/**
 * Gestiona el flujo principal del juego.
 *
 * Esta clase se encarga de coordinar la ejecución del juego, desde la creación
 * de los jugadores y el reparto de cartas hasta la realización de los turnos y
 * el control del estado del juego. Utiliza instancias de IU para la interacción
 * con el usuario, DeckOfCards para la baraja y Table para la mesa donde se
 * colocan las cartas.
 */
public class Game {
    
    private final IU iu;
    private Player[] players; //Se crea varible global para almacenar los jugadores, en crearjugadores(), se establece tamaño al conocer el número de jugadores
    private final DeckOfCards deck;
    private final Table table;
    private boolean sentidoDeJuego; //True si horario, false si antiorario
    private int numJugadorActual;
    private boolean primeraJugada;
      
    /**
     * Construye una instancia de Game, inicializando la interfaz de usuario, la
     * baraja de cartas y la mesa de juego.
     *
     * @param iu {Object}: La interfaz de usuario para la interacción durante la
     * partida.
     */
    public Game(IU iu) {
        this.iu = iu;
        this.deck = new DeckOfCards();
        this.table = new Table();
        this.sentidoDeJuego = false;
        this.numJugadorActual = 0;
        this.primeraJugada=true;
        
    }

    /**
     * Método principal que ejecuta el juego y maneja su lógica y flujo.
     *
     * Realiza las siguientes acciones: Creación de los jugadores. Barajado de
     * la baraja y reparto inicial de cartas a las manos de los jugadores.
     * Colocación inicial de una carta en la mesa. Muestra sel estado inicial de
     * la mesa, la baraja y la mano de cada jugador. Ejecución de los turnos de
     * los jugadores hasta que alguno se queda sin cartas. Notifica el ganador y
     * finaliza la partida.
     *
     */
    public void play() {
        crearJugadores();//Llena el array Players con todos los jugadores de la partida
        deck.barajar();
        repartir();
        colocarPrimeraCarta();
        iu.displayMessage(table.toString());
        iu.displayMessage(deck.toString());
        for (Player jugador : players) {
            iu.displayMessage(jugador.toString());
        }
        boolean finPartida = false;
        while (!finPartida) {
            Player jugadorActual = players[numJugadorActual];
            turnoJugadorActivo();
            if (jugadorActual.getManoDeCartas().isEmpty()) {
                iu.displayMessage("\n ¡" + jugadorActual.getNombre() + " ha ganado la partida!");
                finPartida = true;
            }
            // Se procede al turno del siguiente jugador (la iteración va en orden inverso)
            siguienteJugador();
            
        }
    }

    /**
     * Avanza el turno al siguiente jugador según el sentido de juego.
     *
     * Si el sentido de juego es horario (true), el índice del jugador actual
     * incrementa. Si es antihorario (false), el índice decrementa. En ambos
     * casos, si el índice se sale del rango de jugadores, se reinicia
     * adecuadamente para mantener el ciclo de turnos.
     */
    private void siguienteJugador() {
        int totalJugadores = players.length;
        if (this.sentidoDeJuego) {
            numJugadorActual++;
            if (numJugadorActual > totalJugadores - 1) {
                numJugadorActual = 0;
            }
        } else {
            numJugadorActual--;
            if (numJugadorActual < 0) {
                numJugadorActual = totalJugadores - 1;
            }
        }
        this.primeraJugada = false;
    }

    /**
     * Coloca la primera carta en la mesa al inicio del juego.
     *
     * Extrae una carta de la baraja, la coloca en la mesa y muestra un mensaje
     * con la carta colocada. Luego, verifica si la carta es especial y ejecuta
     * la lógica correspondiente (si es 2 o si es 7).
     */
    private void colocarPrimeraCarta() {
        Card temp = deck.sacarCarta();
        table.colocarCarta(temp);
        iu.displayMessage("Carta colocada en la mesa: " + temp);
        
        esCartaEspecial(temp);
    }

    /**
     * Crea los jugadores de la partida.
     *
     * Solicita a la interfaz de usuario (IU) los nombres de los jugadores y
     * genera un Array con dichos nombres.
     */
    private void crearJugadores() {
        String[] names = iu.pedirJugadores();
        players = new Player[names.length]; //Al array creado como variable global antes se le asigna su tamaño fijo
        for (int i = 0; i < names.length; i++) {
            players[i] = new Player(names[i]); //Se mete en cada posicion del array de jugadores uno nuevo con su nombre

        }
    }

    /**
     * Reparte cartas a cada jugador.
     *
     * A cada jugador se le asignan 7 cartas para llenar su mano, las cuales se
     * extraen de la baraja.
     */
    private void repartir() {
        final int numCartasJugador = 7; //Cambiar en caso de necesitar una mano de distinto tamaño
        for (int i = 0; i < numCartasJugador; i++) {
            for (int j = 0; j < players.length; j++) {
                players[j].insertarCartaALaMano(deck.sacarCarta());
            }
        }
    }

    /**
     * Realiza el turno de un jugador activo.
     *
     * En el turno se muestra la mano del jugador, se determina qué cartas puede
     * jugar en función de la carta superior en la mesa y, dependiendo de si
     * tiene cartas jugables, el jugador juega una carta o roba una carta del
     * mazo.
     */
    private void turnoJugadorActivo() {
        Player jugador = this.players[numJugadorActual];
        iu.displayMessage("Turno de " + jugador.getNombre());
        
        iu.displayMessage(jugador.toString());
        
        List<Card> jugables = jugador.obtenerCartasJugables(table.getCartaSuperior());
        
        if (!jugables.isEmpty()) {
            jugarCarta(jugables);
        } else {
            iu.displayMessage("No tienes cartas jugables.");
            jugarCartaRobada(jugador, robarSiNoPuedeJugar());
        }
        
        iu.displayMessage(table.toString());
        iu.displayMessage(deck.toString());
    }

    /**
     * Permite al jugador seleccionar y jugar una carta desde su mano.
     *
     * Este método muestra al jugador todas las cartas jugables, enumerándolas
     * con un índice numérico. Solicita al usuario que seleccione, mediante la
     * interfaz de usuario, la carta que desea jugar. Una vez elegida la carta,
     * el método la elimina de la mano del jugador y la coloca en la mesa,
     * mostrando un mensaje que indica la carta que se ha jugado.
     *
     * @param jugador {Object}: El jugador que realiza la jugada.
     * @param jugables {List<>}: Las cartas jugables disponibles para el
     * jugador.
     */
    private void jugarCarta(List<Card> jugables) {
        Player jugador = this.players[numJugadorActual];
        iu.displayMessage("\nCartas jugables:");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < jugables.size(); i++) {
            sb.append(" ").append(i + 1).append(" -> ").append(jugables.get(i));
        }
        iu.displayMessage(sb.toString());
        
        String prompt;
        if (jugables.size() == 1) {
            prompt = "Selecciona la carta a jugar (1): ";
        } else {
            prompt = "Selecciona la carta a jugar (1 - " + jugables.size() + "): ";
        }
        
        int eleccion;
        do {
            eleccion = iu.readNumber(prompt) - 1;
        } while (eleccion < 0 || eleccion >= jugables.size());
        
        Card cartaElegida = jugables.get(eleccion);
        jugador.getManoDeCartas().remove(cartaElegida);
        table.colocarCarta(cartaElegida);
        esCartaEspecial(cartaElegida);
        
    }

    /**
     * Verifica si la carta jugada tiene efectos especiales y aplica su lógica
     * correspondiente.
     *
     * Si la carta es un "2", se activa la regla de robo de cartas. Si es un
     * "7", se cambia el sentido del juego.
     *
     * @param carta La carta jugada que se evaluará para efectos especiales.
     */
    private void esCartaEspecial(Card carta) {
        int numCarta = carta.getNumber();
        if (numCarta == 2) {
            esDos();
        } else if (numCarta == 7) {
            esSiete();
        }
    }

    /**
     * Aplica la condición especial de las cartas con número "7", cambiar de
     * sentido.
     *
     * Si la mesa está vacía (primera carta colocada), el turno avanza al
     * siguiente jugador.
     */
    private void esSiete() {
        this.sentidoDeJuego = !sentidoDeJuego;
        iu.displayMessage("Cambio de sentido");
        if (this.primeraJugada) {
            iu.displayMessage(players[numJugadorActual].getNombre() + " pierde el turno");
            siguienteJugador();
        }
    }

    /**
     * Aplica la condición especial de las cartas con número "2".
     *
     * Si la carta superior en la mesa es un "2", el siguiente jugador debe
     * robar dos cartas del mazo. Si la mesa está vacía (primera carta
     * colocada), el primer jugador roba las cartas.
     */
    private void esDos() {
        Player jugador;
        if (this.primeraJugada) {
            jugador = players[numJugadorActual];
            
        } else {
            siguienteJugador();
            jugador = players[numJugadorActual];
        }
        iu.displayMessage("Turno de " + jugador.getNombre());    
        iu.displayMessage("Ha salido un dos pierdes un turno y roba cartas"); 
        
        for (int i = 0; i < 2; i++) { //Cambiar el 2 si se quieren robar más cartas (o menos)
            jugador.insertarCartaALaMano(robarSiNoPuedeJugar());
        }
        
        siguienteJugador();
    }

    /**
     * Gestiona la acción de robar una carta cuando el jugador no tiene cartas
     * jugables.
     *
     * Si la baraja está vacía, se reciclan las cartas de la mesa para rellenar
     * de nuevo la baraja (exceptuando la superior). Luego se extrae una carta
     * de la baraja, si no se puede obtener una carta informa que no hay cartas
     * dispobibles y abandona el proceso. Muestra la carta robada y llama a
     * jugarCartaRobada que evalua si es jugable o no.
     */
    private Card robarSiNoPuedeJugar() {
        iu.displayMessage("Roba una carta");
        reciclarBaraja(); //Ya está dentro de reciclarBaraja() la lógica de cuando hacerlo
        Card robada = deck.sacarCarta();
        iu.displayMessage("Carta robada: " + robada);
        return robada;
    }

    /**
     * Juega la carta robada de acuerdo a las reglas del juego.
     *
     * Si la carta robada es jugable (tiene el mismo número o palo que la carta
     * superior de la mesa), se coloca en la mesa. Sino, la carta se añade
     * nuevamente a la mano del jugador y se notifica que el turno se pierde.
     *
     * @param jugador {Object}: El jugador que ha robado la carta.
     * @param robada {Enum}: La carta que ha sido robada.
     */
    private void jugarCartaRobada(Player jugador, Card robada) {
        Card cartaSuperior = table.getCartaSuperior();
        if (robada.getNumber() == cartaSuperior.getNumber() || robada.getSuit() == cartaSuperior.getSuit()) {
            table.colocarCarta(robada);
            iu.displayMessage("La carta robada es jugable y ha sido colocada en la mesa.");
        } else {
            jugador.insertarCartaALaMano(robada);
            iu.displayMessage("La carta no es jugable. Turno perdido.");
        }
    }

    /**
     * Recicla la baraja utilizando las cartas de la mesa cuando la baraja está
     * vacía.
     *
     * Se retiran de la mesa todas las cartas excepto la carta superior, se
     * añaden a la baraja, y se baraja nuevamente.
     */
    private void reciclarBaraja() {
        if (deck.noSuficientesCartas()) { //La baraja se recicla si no hay al menos 2 cartas en ella.
            iu.displayMessage("La baraja no tiene cartas suficientes. Reciclando cartas de la mesa...");
            
            Stack<Card> recicladas = table.retirarCartasMenosUltima();
            while (!recicladas.isEmpty()) {
                deck.ponerCarta(recicladas.pop());
            }
            
            deck.barajar();
            iu.displayMessage("Baraja reciclada y barajada");
        }
    }
}
