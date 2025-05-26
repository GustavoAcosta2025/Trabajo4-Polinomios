package entidades;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

public class Polinomio {

    private Nodo cabeza;

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

    public Polinomio getDerivada() {
        Polinomio derivada = new Polinomio();
        var actual = cabeza;
        while (actual != null) {
            if (actual.getExponente() != 0) {
                Nodo nodo = new Nodo(actual.getExponente() - 1, actual.getExponente() * actual.getCoeficiente());
                derivada.agregar(nodo);
            }
            actual = actual.siguiente;
        }

        return derivada;
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
