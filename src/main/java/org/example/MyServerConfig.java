package org.example;

import jakarta.websocket.Endpoint;
import jakarta.websocket.server.ServerApplicationConfig;
import jakarta.websocket.server.ServerEndpointConfig;

import java.util.Set;
import java.util.HashSet;

public class MyServerConfig implements ServerApplicationConfig {

    public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> scanned) {
        Set<ServerEndpointConfig> endpoints = new HashSet<>();
        // Agregar el servidor WebSocket para la ruta "/chat/{room}"
        endpoints.add(ServerEndpointConfig.Builder.create(ChatServer.class, "/chat/{room}").build());
        return endpoints;
    }

    @Override
    public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned) {
        // Agregar el servidor WebSocket anotado
        Set<Class<?>> classes = new HashSet<>();
        classes.add(ChatServer.class);
        return classes;
    }
}
