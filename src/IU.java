package src;

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
    public int lineaEscribir = 6;

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

    public void displayMessage(String msg, int position){
        System.out.printf("\033[%d;0H", lineaEscribir);
        System.out.print("║" +   " ".repeat(80)   + "║");
        System.out.printf("\033[%d;2H", lineaEscribir);
        System.out.print(msg);
        try{
        Thread.sleep(5000);
        }catch(Exception e){

        }
    }
    public void mostrarJugadorNoPreguntar(int numeroCartasRestantes,String nombreJugadorActual,String cartaSuperior,String cartasJugador,String cartasJugables){

        int contadorLineas = 0;
        
        String cartasQuedan = String.format("%-39s", "Numero de cartas Restantes: " + numeroCartasRestantes);
        String turno = String.format("%-39s", "Jugador actual: " + nombreJugadorActual);
        String estadoMesa = String.format("%-80s", "Carta superior: " + cartaSuperior);

        String tusCartas = cartasJugador;
        String[] arrayTusCartas = tusCartas.split("->");

        String tusOpciones = cartasJugables;
        String[] arrayTusOpciones = tusOpciones.split("->");

        System.out.print("\033[H\033[2J\033[3J");//Limpiar pantalla

        System.out.println("╔" + "═".repeat(39) + "╗" + "╔" + "═".repeat(39) + "╗");
        System.out.println("║" + cartasQuedan   + "║" + "║" +      turno     + "║");
        System.out.println("╚" + "═".repeat(39) + "╝" + "╚" + "═".repeat(39) + "╝");
        contadorLineas += 3;

        System.out.println("╔" + "═".repeat(80) + "╗");
        System.out.println("║" +   estadoMesa   + "║");
        System.out.println("╚" + "═".repeat(80) + "╝");
        contadorLineas += 3;

        System.out.println("╔" + "═".repeat(80) + "╗");
        contadorLineas += 1;
        for(int i = 1;i<arrayTusCartas.length;i++){            
            if(i%4==1){
                System.out.print("║");
            }
            System.out.print(arrayTusCartas[i]);
            if(i%4==0 || i+1 == arrayTusCartas.length){
                if(i+1 == arrayTusCartas.length){
                    if(i%4 == 0){
                        System.out.println(" ".repeat(32) + "║");
                    } else if(i%4 == 1){
                        System.out.println(" ".repeat(68) + "║");
                    } else if(i%4 == 2){
                        System.out.println(" ".repeat(56) + "║");
                    } else if(i%4 == 3){
                        System.out.println(" ".repeat(44) + "║");
                    }
                } else {
                    System.out.println(" ".repeat(32) + "║");
                }
                contadorLineas += 1;
            }
                
        }
        System.out.println("╚" + "═".repeat(80) + "╝");
        contadorLineas += 1;

        System.out.println("╔" + "═".repeat(80) + "╗");
        System.out.println("║" + String.format("%-80s", "Cartas Jugables:")   + "║");
        contadorLineas += 2;
        for(int i = 1;i<arrayTusOpciones.length;i++){            
            if(i%4==1){
                System.out.print("║");
            }
            
            System.out.print(i + " ");
            //System.out.print(" ");
            System.out.print(arrayTusOpciones[i]);
            if(i%4==0 || i+1 == arrayTusOpciones.length){
                if(i+1 == arrayTusOpciones.length){
                    if(i%4 == 0){
                        System.out.println(" ".repeat(17) + "║");
                    } else if(i%4 == 1){
                        System.out.println(" ".repeat(65) + "║");
                    } else if(i%4 == 2){
                        System.out.println(" ".repeat(49) + "║");
                    } else if(i%4 == 3){
                        System.out.println(" ".repeat(33) + "║");
                    }
                } else {
                    System.out.println(" ".repeat(16) + "║");
                }
                contadorLineas += 1;
            }
                
        }
        System.out.println("╚" + "═".repeat(80) + "╝");
        contadorLineas += 1;

        System.out.println("╔" + "═".repeat(80) + "╗");
        System.out.println("║" + " ".repeat(80) + "║");
        System.out.println("╚" + "═".repeat(80) + "╝");
        contadorLineas += 2;

        System.out.print("\033[" + contadorLineas + ";3H"); //Poner cursor en linea dinamica y fila 3
        
        lineaEscribir = contadorLineas;
    }

    public int mostrarJugadorPreguntar(int numeroCartasRestantes,String nombreJugadorActual,String cartaSuperior,String cartasJugador,String cartasJugables){

        int contadorLineas = 0;
        
        String cartasQuedan = String.format("%-39s", "Numero de cartas Restantes: " + numeroCartasRestantes);
        String turno = String.format("%-39s", "Jugador actual: " + nombreJugadorActual);
        String estadoMesa = String.format("%-80s", "Carta superior: " + cartaSuperior);

        String tusCartas = cartasJugador;
        String[] arrayTusCartas = tusCartas.split("->");

        String tusOpciones = cartasJugables;
        String[] arrayTusOpciones = tusOpciones.split("->");

        System.out.print("\033[H\033[2J\033[3J");//Limpiar pantalla

        System.out.println("╔" + "═".repeat(39) + "╗" + "╔" + "═".repeat(39) + "╗");
        System.out.println("║" + cartasQuedan   + "║" + "║" +      turno     + "║");
        System.out.println("╚" + "═".repeat(39) + "╝" + "╚" + "═".repeat(39) + "╝");
        contadorLineas += 3;

        System.out.println("╔" + "═".repeat(80) + "╗");
        System.out.println("║" +   estadoMesa   + "║");
        System.out.println("╚" + "═".repeat(80) + "╝");
        contadorLineas += 3;

        System.out.println("╔" + "═".repeat(80) + "╗");
        contadorLineas += 1;
        for(int i = 1;i<arrayTusCartas.length;i++){            
            if(i%4==1){
                System.out.print("║");
            }
            System.out.print(arrayTusCartas[i]);
            //System.out.print(String.format("%-80s", arrayTusCartas[i]));
            if(i%4==0 || i+1 == arrayTusCartas.length){
                if(i+1 == arrayTusCartas.length){
                    if(i%4 == 0){
                        System.out.println(" ".repeat(32) + "║");
                    } else if(i%4 == 1){
                        System.out.println(" ".repeat(68) + "║");
                    } else if(i%4 == 2){
                        System.out.println(" ".repeat(56) + "║");
                    } else if(i%4 == 3){
                        System.out.println(" ".repeat(44) + "║");
                    }
                } else {
                    System.out.println(" ".repeat(32) + "║");
                }
                contadorLineas += 1;
            }
                
        }
        System.out.println("╚" + "═".repeat(80) + "╝");
        contadorLineas += 1;

        System.out.println("╔" + "═".repeat(80) + "╗");
        System.out.println("║" + String.format("%-80s", "Cartas Jugables:")   + "║");
        contadorLineas += 2;
        for(int i = 1;i<arrayTusOpciones.length;i++){            
            if(i%4==1){
                System.out.print("║");
            }
            
            System.out.print(i + " ");
            System.out.print(arrayTusOpciones[i]);
            if(i%4==0 || i+1 == arrayTusOpciones.length){
                if(i+1 == arrayTusOpciones.length){
                    if(i%4 == 0){
                        System.out.println(" ".repeat(17) + "║");
                    } else if(i%4 == 1){
                        System.out.println(" ".repeat(65) + "║");
                    } else if(i%4 == 2){
                        System.out.println(" ".repeat(49) + "║");
                    } else if(i%4 == 3){
                        System.out.println(" ".repeat(33) + "║");
                    }
                } else {
                    System.out.println(" ".repeat(16) + "║");
                }
                contadorLineas += 1;
            }
                
        }
        System.out.println("╚" + "═".repeat(80) + "╝");
        contadorLineas += 1;

        System.out.println("╔" + "═".repeat(80) + "╗");
        System.out.println("║" + " ".repeat(80) + "║");
        System.out.println("╚" + "═".repeat(80) + "╝");
        contadorLineas += 2;

        System.out.print("\033[" + contadorLineas + ";3H"); //Poner cursor en linea dinamica y fila 3
        
        lineaEscribir = contadorLineas;
        Scanner scanner = new Scanner(System.in);
        int decision = scanner.nextInt();

        return decision;
    }

    public void mostrarGanador(String nombreJugador){
         String Ganador = String.format("%-80s", "¡" + nombreJugador + " ha ganado la partida!");

            System.out.print("\033[H\033[2J\033[3J");//Limpiar pantalla

            System.out.println("╔" + "═".repeat(80) + "╗");
            System.out.println("║" +     Ganador     + "║");
            System.out.println("╚" + "═".repeat(80) + "╝");

            System.out.print("\033[4;0H"); //Poner cursor en linea 4 y fila 0
            try{
            Thread.sleep(5000);
            }catch(Exception e){

            }
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
        int numJugadores = 0;

         do{
            String titulo = String.format("%-80s", " ".repeat(36) + "Chupatedos");
            String pregunta = String.format("%-80s", "¿Cuántos jugadores son?");

            System.out.print("\033[H\033[2J\033[3J");//Limpiar pantalla

            System.out.println("╔" + "═".repeat(80) + "╗");
            System.out.println("║" +     titulo     + "║");
            System.out.println("╚" + "═".repeat(80) + "╝");
            System.out.println("╔" + "═".repeat(80) + "╗");
            System.out.println("║" +    pregunta    + "║");
            System.out.println("╚" + "═".repeat(80) + "╝");
            System.out.println("╔" + "═".repeat(80) + "╗");
            System.out.println("║" + " ".repeat(80) + "║");
            System.out.println("╚" + "═".repeat(80) + "╝");

            System.out.print("\033[8;3H"); //Poner cursor en linea 8 y fila 3
            Scanner scanner = new Scanner(System.in);
            numJugadores = scanner.nextInt();
        } while(numJugadores < 2 || numJugadores > 5);
        //pedirNombreJugadores(respuesta);

        System.out.print("\033[10;3H"); //Por si se cierra bruscamente
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

            int jugadorActual = i+1;
            String titulo = String.format("%-80s", " ".repeat(36) + "Chupatedos");
            String pregunta = String.format("%-80s", "¿Cual es su nombre jugador "+ jugadorActual + " ?");

            System.out.print("\033[H\033[2J\033[3J");//Limpiar pantalla

            System.out.println("╔" + "═".repeat(80) + "╗");
            System.out.println("║" +     titulo     + "║");
            System.out.println("╚" + "═".repeat(80) + "╝");
            System.out.println("╔" + "═".repeat(80) + "╗");
            System.out.println("║" +    pregunta    + "║");
            System.out.println("╚" + "═".repeat(80) + "╝");
            System.out.println("╔" + "═".repeat(80) + "╗");
            System.out.println("║" + " ".repeat(80) + "║");
            System.out.println("╚" + "═".repeat(80) + "╝");

            System.out.print("\033[8;3H"); //Poner cursor en linea 8 y fila 3
            Scanner scanner = new Scanner(System.in);
            nombre = scanner.next();
            nombres[i] = nombre;
        }
        System.out.println("\n");
        System.out.print("\033[10;3H"); //Por si se cierra bruscamente
        return nombres;

    }

}
