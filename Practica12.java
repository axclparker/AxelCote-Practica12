package mx.unam.fi.poo.g1.p12;

/**
 * Clase Practica12 
 * @author Axel Cote 
 * @version Noviembre-2024
**/

import mx.unam.fi.poo.g1.p12.CuentaBanco;

public class Practica12 {
  /**
   * Metodo main
   * Se ejecuta todo el funcionamiento de la aplicacion
   * @param args -> Arreglo por defecto del metodo main
  **/
  public static void main(String[] args) {
    CuentaBanco cuenta1 = new CuentaBanco("Rene Davila", "2423621965", 10000);
    CuentaBanco cuenta2 = new CuentaBanco("Axel Cote", "424121462", 1000);

    Thread depositarHilo = new Thread(() -> {
      try {
        System.out.println("Se depositara 1000 a cada cuenta");
        cuenta1.depositar(1000); 
        cuenta2.depositar(1000); 
        Thread.sleep(200); 
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    Thread retirarHilo = new Thread(() -> {
      try {
        System.out.println("Se retirara 500 a cada cuenta");
        cuenta1.retirar(500); 
        cuenta2.retirar(500); 
        Thread.sleep(200); 
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    System.out.println();
    cuenta1.mostrarInfoCuenta();
    System.out.println();
    cuenta2.mostrarInfoCuenta();
    
    depositarHilo.start();
    System.out.println();
    retirarHilo.start();

    try {
      depositarHilo.join();
      retirarHilo.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println();
    cuenta1.mostrarInfoCuenta();
    System.out.println();
    cuenta2.mostrarInfoCuenta();
  }
}
