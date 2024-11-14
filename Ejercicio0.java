/**
 * Clase Ejercicio0 
 * @author Axel Cote 
 * @version Noviembre-2024
**/

public class Ejercicio0 {
  private static final int NUM_MAX = 20;
  private static Object lock = new Object();
  private static boolean esPar = true;

  /**
   * Metodo main
   * Se ejecuta todo el funcionamiento de la aplicacion
   * @param args -> Arreglo por defecto del metodo main
  **/
  public static void main(String[] args) {
    Thread imparH = new Thread(() -> {
      for (int i = 1; i <= NUM_MAX; i+=2) {
        synchronized(lock) {
          while (esPar) {
            try {
              lock.wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
          System.out.println("Numeros impares: " + i);
          esPar = true;
          lock.notify();
        }
      }
    });

    Thread parH = new Thread(() -> {
      for (int i = 2; i <= NUM_MAX; i+=2) {
        synchronized(lock) {
          while (!esPar) {
            try {
              lock.wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
          System.out.println("Numeros pares: " + i);
          esPar = false;
          lock.notify();
        }
      }
    });

    parH.start();
    imparH.start();

    try {
      parH.join();
      imparH.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}