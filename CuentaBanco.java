package mx.unam.fi.poo.g1.p12;

/**
 * Clase CuentaBanco 
 * @author Axel Cote 
 * @version Noviembre-2024
**/

public class CuentaBanco {
  private String nombre;
  private String cuenta;
  private double saldo;
  private final Object lock = new Object();
  private boolean turno = true;

  /**
   * Metodo Constructor
   * @param nombre -> Atributo para el nombre de CuentaBanco 
   * @param cuenta -> Atributo para el cuenta de CuentaBanco 
   * @param saldoInicial -> Atributo para el saldoInicial de CuentaBanco 
   **/

  public CuentaBanco(String nombre, String cuenta, double saldoInicial) {
    setNombre(nombre);
    setCuenta(cuenta);
    setSaldo(saldoInicial);
  }

  /**
   * Metodo set
   * @param nombre -> Para cambiar el dato de nombre de CuentaBanco
  **/

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * Metodo set
   * @param cuenta -> Para cambiar el dato de cuenta de CuentaBanco
  **/

  public void setCuenta(String cuenta) {
    this.cuenta = cuenta;
  }

  /**
   * Metodo get 
   * @return saldo -> Regresa el atributo saldo
  **/


  public double getSaldo() {
    return saldo;
  }

  /**
   * Metodo set
   * @param saldo -> Para cambiar el dato de saldo de CuentaBanco
  **/

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }


  /**
   * Metodo depositar
   * @param monto -> Monto a depositar 
  **/

  public void depositar(double monto) {
    synchronized(lock) {
      while (!turno) { 
        try {
          lock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      saldo += monto;
      System.out.println("Deposito de " + monto);
      System.out.println("Saldo actual: " + saldo);
      turno = false; 
      lock.notify(); 
    }
  }

  /**
   * Metodo retirar
   * @param monto -> Monto a retirar 
  **/

  public void retirar(double monto) {
    synchronized(lock) {
      while (turno) { 
        try {
          lock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      if (monto > saldo) {
        System.out.println("Fondos insuficientes para retirar: " + monto);
      } else {
        saldo -= monto;
        System.out.println("Retiro de " + monto);
        System.out.println("Saldo actual: " + saldo);
      }
      turno = true; 
      lock.notify();
    }
  }

  /**
   * Metodo mostrarInfoCuenta
   * Muestra la informacion de la cuenta
  **/

  public void mostrarInfoCuenta() {
    System.out.println("Beneficiario: " + nombre);
    System.out.println("No. de cuenta: " + cuenta);
    System.out.println("Saldo actual: " + saldo);
  }
}
