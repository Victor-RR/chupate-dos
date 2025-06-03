package gal.uvigo.esei.aed1.chupatedos.iu;

import gal.uvigo.esei.aed1.chupatedos.core.Game;

// ULTIMA MODIFICACIÓN 19/05/2025 15:30, EVA
/**
 * Clase principal para la ejecución de la aplicación.
 *
 * En el método main se crea una instancia de la interfaz de usuario (IU), se
 * inicializa la lógica del juego con una instancia de Game y se inicia el juego
 * mediante el método play().
 */
public class Main {

    public static void main(String[] args) {
        IU iu = new IU();
        Game chupateDos = new Game(iu);
        chupateDos.play();
    }
}
