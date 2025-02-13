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
        String room = scanner.nextLine().trim();

        if (room.isEmpty()) {
            System.out.println("El nombre de la sala no puede estar vacío.");
            return;
        }

        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        ChatClient client = new ChatClient();
        try {
            container.connectToServer(client, new URI("ws://localhost:7070/chat/" + room));
            System.out.println("Escribe tus mensajes (escribe 'salir' para terminar):");
            while (true) {
                String message = scanner.nextLine();
                if ("salir".equalsIgnoreCase(message)) {
                    break;
                }
                client.sendMessage(message);
            }
        } catch (Exception e) {
            System.err.println("Error conectando al servidor: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}