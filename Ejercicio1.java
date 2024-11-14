import java.util.Arrays;

/**
 * Clase Ejercicio1 
 * @author Axel Cote 
 * @version Noviembre-2024
**/

public class Ejercicio1 {
  private static final int ARRAY_S = 400;
  private static final int NUM_HILOS = 4;

  /**
   * Metodo main
   * Se ejecuta todo el funcionamiento de la aplicacion
   * @param args -> Arreglo por defecto del metodo main
  **/

  public static void main(String[] args) {
    int[] array = crearArray();
    System.out.println("Arreglo antes de ordenar:");
    System.out.println(Arrays.toString(array));
    System.out.println();

    Thread[] hilos = new Thread[NUM_HILOS];
    int tam = ARRAY_S / NUM_HILOS;

    for (int i = 0; i < NUM_HILOS; i++) {
      int indiceInicio = i * tam;
      int indiceFinal = (i == NUM_HILOS - 1) ? (ARRAY_S - 1) : (indiceInicio + tam - 1);
      hilos[i] = new Thread(new Ordena(array, indiceInicio, indiceFinal));
      hilos[i].start(); 
    }

    for (Thread thread : hilos) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    mergeSort(array, 0, ARRAY_S - 1);
    System.out.println("Arreglo ordenado:");
    System.out.println(Arrays.toString(array));
  }

  /**
   * Metodo crearArray
   * @return array -> Arreglo de tamaño n de numeros random
  **/

  private static int[] crearArray() {
    int[] array = new int[ARRAY_S];
    for (int i = 0; i < ARRAY_S; i++) {
      array[i] = (int)(Math.random() * 400);
    }
    return array;
  }

  /**
   * Metodo mergeSort
  **/

  private static void mergeSort(int[] array, int left, int right) {
    if (left < right) {
      int mid = (left + right) / 2;
      mergeSort(array, left, mid);
      mergeSort(array, mid + 1, left);
      merge(array, left, mid, right);
    }
  }  

  /**
   * Metodo merge
  **/

  private static void merge(int[] array, int left, int mid, int right){
    int[] tmp = new int[right - left + 1];
    int i = left;
    int j = mid + 1;
    int k = 0;

    while (i <= mid && j <= right) {
      if (array[i] <= array[j]) {
        tmp[k++] = array[i++];
      } else {
        tmp[k++] = array[j++];
      }
    }

    while (i <= mid) {
      tmp[k++] = array[i++];
    }

    while (j <= right) {
      tmp[k++] = array[j++];
    }

    System.arraycopy(tmp, 0, array, left, tmp.length);
  }

  /**
   * Clase Ordena
  **/

  static class Ordena implements Runnable {
    private int[] array;
    private int indiceInicio;
    private int indiceFinal;

    /**
     * Metodo Constructor
     * @param array -> Atributo para el array de Ordena 
     * @param indiceInicio -> Atributo para el indiceInicio de Ordena 
     * @param indiceFinal -> Atributo para el indiceFinal de Ordena 
    **/

    public Ordena(int[] array, int indiceInicio, int indiceFinal) {
      this.array = array;
      this.indiceInicio = indiceInicio;
      this.indiceFinal = indiceFinal;
    }

    /**
     * Metodo run (sobreescrito)
    **/

    @Override
    public void run() {
      Arrays.sort(array, indiceInicio, indiceFinal + 1);
    }
  }
}
