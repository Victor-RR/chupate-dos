package src;

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
        try{
            chupateDos.play();
        }catch(Exception e){

        }
    }
}
