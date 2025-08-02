package src;

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
    public void play() throws Exception{

        Card cartaSuperior;
        int numeroDeCartasMesa;//Se recoge por si en un futuro se quiere modificar el programa y se necesita este dato
        int numeroCartasRestantes;
        String nombreJugadorActual;
        String cartasJugables = "";
        String cartasJugador;

        //crearJugadores()
        String[] names = iu.pedirJugadores();
        players = new Player[names.length]; //Al array creado como variable global antes se le asigna su tamaño fijo
        for (int i = 0; i < names.length; i++) {
            players[i] = new Player(names[i]); //Se mete en cada posicion del array de jugadores uno nuevo con su nombre

        }

        deck.barajar();

        //reparir
        final int numCartasJugador = 7; //Cambiar en caso de necesitar una mano de distinto tamaño
        for (int i = 0; i < numCartasJugador; i++) {
            for (int j = 0; j < players.length; j++) {
                players[j].insertarCartaALaMano(deck.sacarCarta());
            }
        }

        //colocarPrimeraCarta
        Card temp = deck.sacarCarta();
        table.colocarCarta(temp);
        int numCarta = temp.getNumber();
        if (numCarta == 2) {
            //esDos();
            Player jugador;
            if (this.primeraJugada) {
                jugador = players[numJugadorActual];
                
            } else {

                try{
                iu.displayMessage("CARTA ESPECIAL",iu.lineaEscribir);
                Thread.sleep(5000);
                }catch(Exception e){

                }

                siguienteJugador();//!!! PASO DE TURNO
                jugador = players[numJugadorActual];
            }
 
            iu.displayMessage("Ha salido un dos pierdes un turno y roba cartas",iu.lineaEscribir);
            
            
            for (int i = 0; i < 2; i++) { //Cambiar el 2 si se quieren robar más cartas (o menos)
                jugador.insertarCartaALaMano(robarSiNoPuedeJugar());
            }
            
            siguienteJugador();
        } else if (numCarta == 7) {
            //esSiete();
            this.sentidoDeJuego = !sentidoDeJuego;
            iu.displayMessage("Cambio de sentido",iu.lineaEscribir);
            if (this.primeraJugada) {
                iu.displayMessage(players[numJugadorActual].getNombre() + " pierde el turno",iu.lineaEscribir);
                siguienteJugador();
            }
        }

        cartaSuperior = table.getCartaSuperior();
        numeroDeCartasMesa = table.numCartasEnMesa();
        numeroCartasRestantes = deck.cartasRestantes();

        boolean finPartida = false;

        while (!finPartida) {

            Player jugadorActual = players[numJugadorActual];

            //turnoJugadorActivo
            Player jugador = this.players[numJugadorActual];
            nombreJugadorActual = jugador.getNombre();
            
            cartasJugador = jugador.toString();
            
            List<Card> jugables = jugador.obtenerCartasJugables(table.getCartaSuperior());
            
            if (!jugables.isEmpty()) {
                //jugar Carta
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < jugables.size(); i++) {
                    sb.append(" -> ").append(jugables.get(i));
                }
                cartasJugables = sb.toString();
                
                int eleccion;
                do {
                    eleccion = iu.mostrarJugadorPreguntar(numeroCartasRestantes,nombreJugadorActual,cartaSuperior.toString(),cartasJugador,cartasJugables) - 1;
                } while (eleccion < 0 || eleccion >= jugables.size());
                
                Card cartaElegida = jugables.get(eleccion);
                jugador.getManoDeCartas().remove(cartaElegida);
                table.colocarCarta(cartaElegida);
                //esCartaEspecial(cartaElegida);
                numCarta = cartaElegida.getNumber();
                if (numCarta == 2) {
                    //esDos();
                    if (this.primeraJugada) {
                        jugador = players[numJugadorActual];
                        
                    } else {
                        siguienteJugador();
                        jugador = players[numJugadorActual];
                    }
                    iu.mostrarJugadorNoPreguntar(numeroCartasRestantes,nombreJugadorActual,cartaSuperior.toString(),cartasJugador,cartasJugables);
                    iu.displayMessage("Ha salido un dos pierdes un turno y roba cartas",iu.lineaEscribir);
                    
                    for (int i = 0; i < 2; i++) { //Cambiar el 2 si se quieren robar más cartas (o menos)
                        iu.mostrarJugadorNoPreguntar(numeroCartasRestantes,nombreJugadorActual,cartaSuperior.toString(),cartasJugador,cartasJugables);
                        jugador.insertarCartaALaMano(robarSiNoPuedeJugar());
                    }
                    
                    siguienteJugador();
                } else if (numCarta == 7) {

                    iu.mostrarJugadorNoPreguntar(numeroCartasRestantes,nombreJugadorActual,cartaSuperior.toString(),cartasJugador,cartasJugables);
                    iu.displayMessage("CARTA ESPECIAL: 7",iu.lineaEscribir);
                    this.sentidoDeJuego = !sentidoDeJuego;
                    iu.mostrarJugadorNoPreguntar(numeroCartasRestantes,nombreJugadorActual,cartaSuperior.toString(),cartasJugador,cartasJugables);
                    iu.displayMessage("Cambio de sentido",iu.lineaEscribir);
                    if (this.primeraJugada) {
                        iu.mostrarJugadorNoPreguntar(numeroCartasRestantes,nombreJugadorActual,cartaSuperior.toString(),cartasJugador,cartasJugables);
                        iu.displayMessage(players[numJugadorActual].getNombre() + " pierde el turno",iu.lineaEscribir);
                        siguienteJugador();
                    }
                }

            } else {
                iu.mostrarJugadorNoPreguntar(numeroCartasRestantes,nombreJugadorActual,cartaSuperior.toString(),cartasJugador,cartasJugables);
                iu.displayMessage("No tienes cartas jugables.",iu.lineaEscribir);
                Card robada = robarSiNoPuedeJugar();
                cartaSuperior = table.getCartaSuperior();
                if (robada.getNumber() == cartaSuperior.getNumber() || robada.getSuit() == cartaSuperior.getSuit()) {
                    table.colocarCarta(robada);
                    iu.mostrarJugadorNoPreguntar(numeroCartasRestantes,nombreJugadorActual,cartaSuperior.toString(),cartasJugador,cartasJugables);
                    iu.displayMessage("La carta robada es jugable y ha sido colocada en la mesa.",iu.lineaEscribir);
                } else {
                    jugador.insertarCartaALaMano(robada);
                    iu.mostrarJugadorNoPreguntar(numeroCartasRestantes,nombreJugadorActual,cartaSuperior.toString(),cartasJugador,cartasJugables);
                    iu.displayMessage("La carta no es jugable. Turno perdido.",iu.lineaEscribir);
                }
            }

            cartaSuperior = table.getCartaSuperior();
            numeroDeCartasMesa = table.numCartasEnMesa();
            numeroCartasRestantes = deck.cartasRestantes();
            iu.mostrarJugadorNoPreguntar(numeroCartasRestantes,nombreJugadorActual,cartaSuperior.toString(),cartasJugador,cartasJugables);

            if (jugadorActual.getManoDeCartas().isEmpty()) {
                iu.mostrarGanador(jugadorActual.getNombre());
                finPartida = true;
            }
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
        iu.displayMessage("Roba una carta", iu.lineaEscribir);
        reciclarBaraja(); //Ya está dentro de reciclarBaraja() la lógica de cuando hacerlo
        Card robada = deck.sacarCarta();
        iu.displayMessage("Carta robada: " + robada, iu.lineaEscribir);
        return robada;
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
            iu.displayMessage("La baraja no tiene cartas suficientes. Reciclando cartas de la mesa...",iu.lineaEscribir);
            
            Stack<Card> recicladas = table.retirarCartasMenosUltima();
            while (!recicladas.isEmpty()) {
                deck.ponerCarta(recicladas.pop());
            }
            
            deck.barajar();
            iu.displayMessage("Baraja reciclada y barajada",iu.lineaEscribir);
        }
    }
}
