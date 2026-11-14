package util;

public class Codificador {

    // Constante solicitada en el enunciado para sumar al valor entero de cada caracter
    private static final int CONSTANTE_CODIGO = 5;

    public static String codificarPassword(String password) {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < password.length(); i++) {
            char caracter = password.charAt(i);
            int valorEntero = (int) caracter;
            int valorCodificado = valorEntero + CONSTANTE_CODIGO;

            if (i > 0) {
                resultado.append("-");
            }
            resultado.append(valorCodificado);
        }

        return resultado.toString();
    }
}

