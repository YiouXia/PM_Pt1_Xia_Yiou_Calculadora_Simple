package com.example.calculadora;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Botones numéricos
    Button btn0;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;

    // Botones de operaciones
    Button btn_equal;
    Button btn_sum;
    Button btn_sub;
    Button btn_div;
    Button btn_mult;

    // Botones adicionales
    Button btn_power;
    Button btn_sqr_root;
    Button btn_clear;
    Button btn_dot;

    TextView textView;

    // Primer número de la operación
    double primerNumero = 0;

    // Operación seleccionada
    String operacion = "";

    // Número que está escribiendo el usuario
    String numeroActual = "";

    // Indica si se empieza a escribir un número nuevo
    boolean nuevaEntrada = true;

    // Indica si lo que aparece en pantalla es un resultado
    boolean hayResultado = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Botones numéricos
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);


        // Botón igual
        btn_equal = findViewById(R.id.btn_equal);


        // Operaciones
        btn_sum = findViewById(R.id.btn_sum);
        btn_sub = findViewById(R.id.btn_sub);
        btn_div = findViewById(R.id.btn_div);
        btn_mult = findViewById(R.id.btn_mult);


        // Operaciones adicionales
        btn_power = findViewById(R.id.btn_power);
        btn_sqr_root = findViewById(R.id.btn_sqr_root);


        // Limpiar y decimal
        btn_clear = findViewById(R.id.btn_clear);
        btn_dot = findViewById(R.id.btn_dot);


        // TextView
        textView = findViewById(R.id.textView);


        // Números
        btn0.setOnClickListener(v -> escribirNumero("0"));
        btn1.setOnClickListener(v -> escribirNumero("1"));
        btn2.setOnClickListener(v -> escribirNumero("2"));
        btn3.setOnClickListener(v -> escribirNumero("3"));
        btn4.setOnClickListener(v -> escribirNumero("4"));
        btn5.setOnClickListener(v -> escribirNumero("5"));
        btn6.setOnClickListener(v -> escribirNumero("6"));
        btn7.setOnClickListener(v -> escribirNumero("7"));
        btn8.setOnClickListener(v -> escribirNumero("8"));
        btn9.setOnClickListener(v -> escribirNumero("9"));


        // Decimal
        btn_dot.setOnClickListener(v -> escribirDecimal());


        // Suma
        btn_sum.setOnClickListener(v -> seleccionarOperacion("+"));


        // Resta y números negativos
        btn_sub.setOnClickListener(v -> pulsarMenos());


        // Multiplicación
        btn_mult.setOnClickListener(v -> seleccionarOperacion("*"));


        // División
        btn_div.setOnClickListener(v -> seleccionarOperacion("/"));


        // Igual
        btn_equal.setOnClickListener(v -> calcularResultado());


        // Potencia
        btn_power.setOnClickListener(v -> calcularPotencia());


        // Raíz cuadrada
        btn_sqr_root.setOnClickListener(v -> calcularRaiz());


        // Limpiar
        btn_clear.setOnClickListener(v -> limpiar());
    }


    // Escribir número
    private void escribirNumero(String numero) {

        // Si había un resultado, empieza una operación nueva
        if (hayResultado && operacion.isEmpty()) {

            numeroActual = "";
            primerNumero = 0;
            hayResultado = false;
        }

        if (nuevaEntrada) {

            numeroActual = numero;
            nuevaEntrada = false;

        } else {

            numeroActual += numero;
        }

        actualizarPantalla();
    }


    // Número decimal
    private void escribirDecimal() {

        // Si había un resultado, empieza un número nuevo
        if (hayResultado && operacion.isEmpty()) {

            numeroActual = "";
            primerNumero = 0;
            hayResultado = false;
        }

        if (nuevaEntrada || numeroActual.isEmpty()) {

            numeroActual = "0.";
            nuevaEntrada = false;

        } else if (numeroActual.equals("-")) {

            numeroActual = "-0.";

        } else if (!numeroActual.contains(".")) {

            numeroActual += ".";
        }

        actualizarPantalla();
    }


    // Botón menos: sirve para restar o escribir números negativos
    private void pulsarMenos() {

        // Número negativo al comenzar
        if (numeroActual.isEmpty() && operacion.isEmpty() && !hayResultado) {

            numeroActual = "-";
            nuevaEntrada = false;
            actualizarPantalla();

            return;
        }


        // Número negativo después de una operación
        if (numeroActual.isEmpty() && !operacion.isEmpty()) {

            numeroActual = "-";
            nuevaEntrada = false;
            actualizarPantalla();

            return;
        }


        // Si hay un resultado, hace una resta con ese resultado
        if (hayResultado && operacion.isEmpty()) {

            seleccionarOperacion("-");

            return;
        }


        // Resta normal
        seleccionarOperacion("-");
    }


    // Seleccionar operación
    private void seleccionarOperacion(String nuevaOperacion) {

        // Permite seguir operando después de pulsar =
        if (hayResultado && operacion.isEmpty()) {

            operacion = nuevaOperacion;
            numeroActual = "";
            nuevaEntrada = true;
            hayResultado = false;

            actualizarPantalla();

            return;
        }


        // No permite operar sin número
        if (numeroActual.isEmpty() || numeroActual.equals("-")) {
            return;
        }

        try {

            primerNumero = Double.parseDouble(numeroActual);

            operacion = nuevaOperacion;

            numeroActual = "";

            nuevaEntrada = true;

            hayResultado = false;

            actualizarPantalla();

        } catch (NumberFormatException e) {

            mostrarError(R.string.error_invalid_number);
        }
    }


    // Calcular resultado
    private void calcularResultado() {

        if (numeroActual.isEmpty()
                || numeroActual.equals("-")
                || operacion.isEmpty()) {

            return;
        }

        try {

            double segundoNumero = Double.parseDouble(numeroActual);

            double resultado;

            switch (operacion) {

                // Suma
                case "+":
                    resultado = primerNumero + segundoNumero;
                    break;


                // Resta
                case "-":
                    resultado = primerNumero - segundoNumero;
                    break;


                // Multiplicación
                case "*":
                    resultado = primerNumero * segundoNumero;
                    break;


                // División
                case "/":

                    // Control de división entre 0
                    if (segundoNumero == 0) {

                        mostrarError(R.string.error_division_zero);

                        return;
                    }

                    resultado = primerNumero / segundoNumero;
                    break;


                default:
                    return;
            }


            mostrarResultado(resultado);

            // Guarda el resultado para poder seguir operando
            primerNumero = resultado;

            numeroActual = "";

            operacion = "";

            nuevaEntrada = true;

            hayResultado = true;

        } catch (NumberFormatException e) {

            mostrarError(R.string.error_invalid_number);
        }
    }


    // Potencia al cuadrado
    private void calcularPotencia() {

        double numero;

        try {

            // Usa el resultado anterior si existe
            if (hayResultado) {

                numero = primerNumero;

            } else {

                if (numeroActual.isEmpty() || numeroActual.equals("-")) {
                    return;
                }

                numero = Double.parseDouble(numeroActual);
            }


            double resultado = numero * numero;

            mostrarResultado(resultado);

            // Guarda el resultado
            primerNumero = resultado;

            numeroActual = "";

            operacion = "";

            nuevaEntrada = true;

            hayResultado = true;

        } catch (NumberFormatException e) {

            mostrarError(R.string.error_invalid_number);
        }
    }


    // Raíz cuadrada
    private void calcularRaiz() {

        double numero;

        try {

            // Usa el resultado anterior si existe
            if (hayResultado) {

                numero = primerNumero;

            } else {

                if (numeroActual.isEmpty() || numeroActual.equals("-")) {
                    return;
                }

                numero = Double.parseDouble(numeroActual);
            }


            // No permite raíz cuadrada de números negativos
            if (numero < 0) {

                mostrarError(R.string.error_negative_root);

                return;
            }


            double resultado = Math.sqrt(numero);

            mostrarResultado(resultado);

            // Guarda el resultado
            primerNumero = resultado;

            numeroActual = "";

            operacion = "";

            nuevaEntrada = true;

            hayResultado = true;

        } catch (NumberFormatException e) {

            mostrarError(R.string.error_invalid_number);
        }
    }


    // Actualizar pantalla
    private void actualizarPantalla() {

        // Número sin operación
        if (operacion.isEmpty()) {

            textView.setText(numeroActual);

            return;
        }


        String simbolo = "";

        switch (operacion) {

            // Suma
            case "+":
                simbolo = "+";
                break;


            // Resta
            case "-":
                simbolo = "-";
                break;


            // Multiplicación
            case "*":
                simbolo = "×";
                break;


            // División
            case "/":
                simbolo = "÷";
                break;
        }


        String primerNumeroTexto = convertirNumeroATexto(primerNumero);


        // Operación sin segundo número
        if (numeroActual.isEmpty()) {

            textView.setText(
                    primerNumeroTexto + " " + simbolo
            );

        } else {

            // Operación completa
            textView.setText(
                    primerNumeroTexto
                            + " "
                            + simbolo
                            + " "
                            + numeroActual
            );
        }
    }


    // Mostrar resultado
    private void mostrarResultado(double resultado) {

        textView.setText(
                convertirNumeroATexto(resultado)
        );
    }


    // Evita mostrar números enteros como 5.0, ademas de dar un resultado de un máximo de 3 decimales.
    private String convertirNumeroATexto(double numero) {

        if (numero == (long) numero) {
            return String.valueOf((long) numero);
        }

        return String.format(java.util.Locale.US, "%.3f", numero)
                .replaceAll("0+$", "")
                .replaceAll("\\.$", "");
    }


    // Mostrar error y reiniciar la calculadora
    private void mostrarError(int mensaje) {

        textView.setText(mensaje);

        primerNumero = 0;

        numeroActual = "";

        operacion = "";

        nuevaEntrada = true;

        hayResultado = false;
    }


    // Limpiar calculadora
    private void limpiar() {

        textView.setText("");

        primerNumero = 0;

        numeroActual = "";

        operacion = "";

        nuevaEntrada = true;

        hayResultado = false;
    }
}