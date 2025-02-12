package org.example;

import org.glassfish.tyrus.server.Server;

public class App {
    public static void main(String[] args) throws Exception {
        // Configuración del servidor WebSocket con la clase de configuración personalizada
        Server server = new Server("localhost", 7070, "/websocket", null,  MyServerConfig.class);

        try {
            server.start();
            System.out.println("Servidor WebSocket iniciado");
            Thread.sleep(Long.MAX_VALUE); // Mantener el servidor corriendo
        } finally {
            server.stop();
        }
    }
}
