package service;

import model.ResultadoLogin;
import util.Codificador;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class LoginClienteService {

    private static final String URL_LOGIN = "http://localhost:8080/login";
    private final HttpClient httpClient;

    public LoginClienteService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }

    public ResultadoLogin enviarLogin(String usuario, String passwordPlano) throws Exception {
        String passwordCodificada = Codificador.codificarPassword(passwordPlano);

        String jsonBody = "{\n"
                + "  \"usuario\": \"" + escaparJson(usuario) + "\",\n"
                + "  \"password\": \"" + escaparJson(passwordCodificada) + "\"\n"
                + "}";

        System.out.println("\nPOST " + URL_LOGIN);
        System.out.println(jsonBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL_LOGIN))
                .header("Content-Type", "application/json; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)
        );

        String respuestaJson = formatearJson(response.body());
        System.out.println("Respuesta:\n" + respuestaJson);

        return interpretarRespuesta(respuestaJson);
    }

    private String formatearJson(String json) {
        if (json == null) {
            return "";
        }
        String limpio = json.trim();
        if (limpio.contains("\n")) {
            return limpio.replace(" : ", ": ");
        }
        if (limpio.startsWith("{") && limpio.endsWith("}")) {
            String interior = limpio.substring(1, limpio.length() - 1);
            String[] campos = interior.split(",(?=\"[^\"]*\":)");
            StringBuilder sb = new StringBuilder("{\n");
            for (int i = 0; i < campos.length; i++) {
                sb.append("  ").append(campos[i].trim().replace("\":", "\": "));
                if (i < campos.length - 1) {
                    sb.append(",");
                }
                sb.append("\n");
            }
            sb.append("}");
            return sb.toString();
        }
        return limpio;
    }

    private ResultadoLogin interpretarRespuesta(String json) {
        boolean autenticado = extraerBooleano(json, "autenticado");
        String mensaje = extraerTexto(json, "mensaje");
        String usuario = extraerTexto(json, "usuario");
        String nombre = extraerTexto(json, "nombre");

        if (mensaje == null || mensaje.isEmpty()) {
            mensaje = autenticado
                    ? "Usuario autenticado correctamente"
                    : "Usuario o contraseña incorrectos";
        }

        return new ResultadoLogin(autenticado, mensaje, usuario, nombre);
    }

    private boolean extraerBooleano(String json, String clave) {
        String patron = "\"" + clave + "\"";
        int idx = json.indexOf(patron);
        if (idx == -1) {
            return false;
        }
        int dosPuntos = json.indexOf(':', idx + patron.length());
        if (dosPuntos == -1) {
            return false;
        }
        String resto = json.substring(dosPuntos + 1).trim();
        return resto.startsWith("true");
    }

    private String extraerTexto(String json, String clave) {
        String patron = "\"" + clave + "\"";
        int idx = json.indexOf(patron);
        if (idx == -1) {
            return null;
        }
        int dosPuntos = json.indexOf(':', idx + patron.length());
        if (dosPuntos == -1) {
            return null;
        }
        String resto = json.substring(dosPuntos + 1).trim();
        if (resto.startsWith("null") || !resto.startsWith("\"")) {
            return null;
        }
        StringBuilder valor = new StringBuilder();
        for (int i = 1; i < resto.length(); i++) {
            char c = resto.charAt(i);
            if (c == '\\' && i + 1 < resto.length()) {
                valor.append(resto.charAt(++i));
            } else if (c == '"') {
                break;
            } else {
                valor.append(c);
            }
        }
        return valor.toString();
    }

    private String escaparJson(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
