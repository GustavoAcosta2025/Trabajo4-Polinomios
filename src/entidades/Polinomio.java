package entidades;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

public class Polinomio {

    private Nodo cabeza;

    public static Polinomio multiplicarTermino(Polinomio p, Nodo termino) {
        Polinomio resultado = new Polinomio();
        Nodo actual = p.getCabeza();

        while (actual != null) {
            int nuevoExponente = actual.getExponente() + termino.getExponente();
            double nuevoCoeficiente = actual.getCoeficiente() * termino.getCoeficiente();
            if (nuevoCoeficiente != 0) {
                resultado.agregar(new Nodo(nuevoExponente, nuevoCoeficiente));
            }
            actual = actual.siguiente;
        }

        return resultado;
    }


    public Polinomio copiar() {
        Polinomio copia = new Polinomio();
        Nodo actual = this.cabeza;

        while (actual != null) {
            copia.agregar(new Nodo(actual.getExponente(), actual.getCoeficiente()));
            actual = actual.getSiguiente();
        }

        return copia;
    }

    public Nodo obtenerMayor() {
        Nodo actual = cabeza;
        Nodo mayor = cabeza;
        while (actual != null) {
            if (actual.getExponente() > mayor.getExponente()) {
                mayor = actual;
            }
            actual = actual.getSiguiente();
        }
        return mayor;
    }

    public boolean esCero() {
        return cabeza == null;
    }

    public int grado() {
        return cabeza != null ? cabeza.getExponente() : -1;
    }

    public Polinomio() {
        cabeza = null;
    }

    public Nodo getCabeza() {
        return cabeza;
    }

    public void limpiar() {
        cabeza = null;
    }

    public void agregar(Nodo nodo) {
        if (nodo != null) {
            if (cabeza == null) {
                cabeza = nodo;
                cabeza.siguiente = null;
            } else {
                int encontrado = 0;
                Nodo actual = cabeza;
                Nodo anterior = null;
                while (actual != null && encontrado == 0) {
                    if (nodo.getExponente() == actual.getExponente()) {
                        encontrado = 1;
                    } else if (nodo.getExponente() < actual.getExponente()) {
                        encontrado = 2;
                    } else {
                        anterior = actual;
                        actual = actual.siguiente;
                    }
                }
                if (encontrado == 1) {
                    double coeficiente = actual.getCoeficiente() + nodo.getCoeficiente();
                    if (coeficiente == 0) {
                        // quitar el nodo
                        if (anterior == null) {
                            cabeza = actual.siguiente;
                        } else {
                            anterior.siguiente = actual.siguiente;
                        }
                    } else {
                        actual.setCoeficiente(coeficiente);
                    }
                } else {
                    insertar(nodo, anterior);
                }
            }
        }
    }

    private void insertar(Nodo nodo, Nodo anterior) {
        if (nodo != null) {
            if (anterior == null) {
                nodo.siguiente = cabeza;
                cabeza = nodo;
            } else {
                nodo.siguiente = anterior.siguiente;
                anterior.siguiente = nodo;
            }
        }
    }

    private String[] getTextos() {
        String[] lineas = new String[2];
        lineas[0] = "";
        lineas[1] = "";

        Nodo actual = cabeza;
        while (actual != null) {
            String texto = (actual.getCoeficiente() >= 0 ? "+" : "")
                    + String.valueOf(actual.getCoeficiente()) + " X";
            lineas[0] += String.format("%0" + texto.length() + "d", 0).replace("0", " ");
            lineas[1] += texto;

            texto = String.valueOf(actual.getExponente());
            lineas[0] += texto;
            lineas[1] += String.format("%0" + texto.length() + "d", 0).replace("0", " ");

            actual = actual.siguiente;
        }
        return lineas;
    }

    public void mostrar(JLabel lbl) {
        lbl.setFont(new Font("Courier New", Font.PLAIN, 12));
        if (cabeza != null) {
            String[] lineas = getTextos();
            String espacio = "&nbsp;";
            lineas[0] = lineas[0].replace("", espacio);
            lineas[1] = lineas[1].replace("", espacio);

            lbl.setText("<html>" + lineas[0] + "<br>" + lineas[1] + "</html>");
        } else {
            lbl.setText("0");
        }
    }

    public List<Monomio> toDTO() {
        List<Monomio> lista = new ArrayList<>();
        var actual = cabeza;
        while (actual != null) {
            lista.add(new Monomio(actual.getExponente(), actual.getCoeficiente()));
            actual = actual.siguiente;
        }
        return lista;
    }

    public void fromDTO(List<Monomio> lista) {
        cabeza = null;
        for (Monomio monomio : lista) {
            agregar(new Nodo(monomio.getExponente(), monomio.getCoeficiente()));
        }
    }
}