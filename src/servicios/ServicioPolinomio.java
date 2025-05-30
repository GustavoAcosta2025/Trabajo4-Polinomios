package servicios;

import entidades.Nodo;
import entidades.Polinomio;

import static entidades.Polinomio.multiplicarTermino;

public class ServicioPolinomio {

    public static Polinomio sumar(Polinomio p1, Polinomio p2) {
        Polinomio pR = new Polinomio();


        Nodo actual1 = p1.getCabeza();
        Nodo actual2 = p2.getCabeza();

        while (actual1 != null || actual2 != null) {
            Nodo nR = null;
            if (actual1 != null && actual2 != null && actual1.getExponente() == actual2.getExponente()) {
                if (actual1.getCoeficiente() + actual2.getCoeficiente() != 0) {
                    nR = new Nodo(actual1.getExponente(), actual1.getCoeficiente() + actual2.getCoeficiente());
                }
                actual1 = actual1.siguiente;
                actual2 = actual2.siguiente;
            } else if (actual2 == null || (actual1 != null && actual1.getExponente() < actual2.getExponente())) {
                nR = new Nodo(actual1.getExponente(), actual1.getCoeficiente());
                actual1 = actual1.siguiente;
            } else {
                nR = new Nodo(actual2.getExponente(), actual2.getCoeficiente());
                actual2 = actual2.siguiente;
            }
            if (nR != null) {
                pR.agregar(nR);
            }
        }
        return pR;
    }

    public static Polinomio restar(Polinomio p1, Polinomio p2) {
        Polinomio pR = new Polinomio();

        Nodo actual1 = p1.getCabeza();
        Nodo actual2 = p2.getCabeza();

        while (actual1 != null || actual2 != null) {
            Nodo nR = null;
            if (actual1 != null && actual2 != null && actual1.getExponente() == actual2.getExponente()) {
                if (actual1.getCoeficiente() - actual2.getCoeficiente() != 0) {
                    nR = new Nodo(actual1.getExponente(), actual1.getCoeficiente() - actual2.getCoeficiente());
                }
                actual1 = actual1.siguiente;
                actual2 = actual2.siguiente;
            } else if (actual2 == null || (actual1 != null && actual1.getExponente() < actual2.getExponente())) {
                nR = new Nodo(actual1.getExponente(), actual1.getCoeficiente());
                actual1 = actual1.siguiente;
            } else {
                nR = new Nodo(actual2.getExponente(), -1 * actual2.getCoeficiente());
                actual2 = actual2.siguiente;
            }
            if (nR != null) {
                pR.agregar(nR);
            }
        }
        return pR;
    }

    public static Polinomio multiplicar(Polinomio p1, Polinomio p2) {
        Polinomio pR = new Polinomio();

        Nodo actual1 = p1.getCabeza();
        while (actual1 != null) {
            Nodo actual2 = p2.getCabeza();
            while (actual2 != null) {
                int exponente = actual1.getExponente() + actual2.getExponente();
                double coeficiente = actual1.getCoeficiente() * actual2.getCoeficiente();
                pR.agregar(new Nodo(exponente, coeficiente));
                actual2 = actual2.siguiente;
            }
            actual1 = actual1.siguiente;
        }
        return pR;
    }

    public static Polinomio dividir(Polinomio dividendo, Polinomio divisor) {
        if (divisor.esCero()) {
            throw new ArithmeticException("No se puede dividir entre cero.");
        }

        Polinomio cociente = new Polinomio();
        Polinomio resto = dividendo.copiar();

        while (!resto.esCero() && resto.grado() >= divisor.grado()) {
            System.out.println("Resto actual: " + resto);

            Nodo mayorResto = resto.obtenerMayor();
            Nodo mayorDivisor = divisor.obtenerMayor();

            int nuevoExponente = mayorResto.getExponente() - mayorDivisor.getExponente();
            if (nuevoExponente < 0) break;
            double nuevoCoeficiente = mayorResto.getCoeficiente() / mayorDivisor.getCoeficiente();

            Nodo nuevoTermino = new Nodo(nuevoExponente, nuevoCoeficiente);
            System.out.println("Nuevo término del cociente: " + nuevoTermino);

            cociente.agregar(nuevoTermino);

            Polinomio producto = multiplicarTermino(divisor, nuevoTermino);
            System.out.println("Producto del divisor con el nuevo término: " + producto);

            resto = restar(resto, producto);
        }

        return cociente;
    }

    public static Polinomio derivada(Polinomio p) {
        Polinomio resultado = new Polinomio();
        for (Nodo actual = p.getCabeza(); actual != null; actual = actual.siguiente) {
            if (actual.getExponente() > 0) {
                double nuevoCoef = actual.getCoeficiente() * actual.getExponente();
                int nuevoExpo = actual.getExponente() - 1;
                if (nuevoCoef != 0) {
                    resultado.agregar(new Nodo(nuevoExpo, nuevoCoef));
                }
            }
        }
        return resultado;
    }
}
