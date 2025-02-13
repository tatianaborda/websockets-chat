package org.example;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat/{room}")
public class ChatServer {
    private static final ConcurrentHashMap<String, Set<Session>> rooms = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("room") String room) throws IOException {
        System.out.println("Intentando conectar a la sala: " + room);
        rooms.computeIfAbsent(room, k -> Collections.newSetFromMap(new ConcurrentHashMap<>())).add(session);
        System.out.println("Usuario conectado a la sala: " + room);
    }
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("room") String room) throws IOException {
        System.out.println("Mensaje recibido en la sala " + room + ": " + message);
        broadcast(room, session.getId() + ": " + message); // Enviar el mensaje a todos en la sala
    }

    @OnClose
    public void onClose(Session session, @PathParam("room") String room) throws IOException {
        // Eliminar la sesión de la sala
        rooms.getOrDefault(room, Collections.emptySet()).remove(session);
        System.out.println("Usuario desconectado de la sala: " + room);
    }

    @OnError
    public void onError(Session session, Throwable throwable, @PathParam("room") String room) {
        System.err.println("Error en la sala " + room + ": " + throwable.getMessage());
    }

    // Método para enviar un mensaje a todos los usuarios en una sala
    private void broadcast(String room, String message) {
        rooms.getOrDefault(room, Collections.emptySet()).forEach(session -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}