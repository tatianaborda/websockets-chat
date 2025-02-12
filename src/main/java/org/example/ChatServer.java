package org.example;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/chat/{room}")
public class ChatServer {

    @OnOpen
    public void onOpen(Session session, @PathParam("room") String room) throws IOException {
        System.out.println("Usuario conectado a la sala: " + room);
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("room") String room) throws IOException {
        System.out.println("Mensaje recibido en la sala " + room + ": " + message);
    }

    @OnClose
    public void onClose(Session session, @PathParam("room") String room) throws IOException {
        System.out.println("Usuario desconectado de la sala: " + room);
    }

    @OnError
    public void onError(Session session, Throwable throwable, @PathParam("room") String room) {
        System.err.println("Error en la sala " + room + ": " + throwable.getMessage());
    }
}
