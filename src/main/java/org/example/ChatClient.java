package org.example;

import jakarta.websocket.*;
import java.net.URI;
import java.util.Scanner;

@ClientEndpoint
public class ChatClient {
    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("Conectado al servidor de chat.");
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Mensaje recibido: " + message); // Mostrar mensajes recibidos
    }

    @OnClose
    public void onClose() {
        System.out.println("Desconectado del servidor de chat.");
    }

    @OnError
    public void onError(Throwable throwable) {
        System.err.println("Error: " + throwable.getMessage());
    }

    public void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingresa el nombre de la sala: ");
        String room = scanner.nextLine();

        // Asegúrate de que la URL sea correcta
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        ChatClient client = new ChatClient();
        container.connectToServer(client, new URI("ws://localhost:7070/websocket/chat"));

        System.out.println("Escribe tus mensajes (escribe 'salir' para terminar):");
        while (true) {
            String message = scanner.nextLine();
            if ("salir".equalsIgnoreCase(message)) {
                break;
            }
            client.sendMessage(message);
        }

        scanner.close();
    }
}
