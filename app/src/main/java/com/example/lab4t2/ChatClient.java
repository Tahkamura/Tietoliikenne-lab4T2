package com.example.lab4t2;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;


interface ChatClientInterface{
    void onMessage(String message);
    void onStatusChange(String status);
}


public class ChatClient extends WebSocketClient {

    ChatClientInterface observer;

    public ChatClient(URI serverUri,ChatClientInterface observer) {
        super(serverUri);
        this.observer = observer;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        observer.onStatusChange("Connected to socket");
    }

    @Override
    public void onMessage(String message) {
        observer.onMessage(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        observer.onStatusChange("Connection closed");
    }

    @Override
    public void onError(Exception ex) {
        observer.onStatusChange("Error" + ex.toString());
    }
}
