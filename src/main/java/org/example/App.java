package org.example;

import org.glassfish.tyrus.server.Server;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        // Configuración adicional (en este caso, un mapa vacío)
        Map<String, Object> properties = Collections.emptyMap();

        // Iniciar el servidor WebSocket
        Server server = new Server("localhost", 7070, "/", properties, ChatServer.class);

        try {
            server.start();
            System.out.println("Servidor WebSocket iniciado en ws://localhost:7070/");

            // Mantener el servidor en ejecución
            System.out.println("Presiona Enter para detener el servidor...");
            new Scanner(System.in).nextLine(); // Esperar entrada del usuario
        } finally {
            server.stop();
            System.out.println("Servidor WebSocket detenido.");
        }
    }
}