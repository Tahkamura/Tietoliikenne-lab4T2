package com.example.lab4t2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URI;


// Lab4 tehtävä 2
public class MainActivity extends AppCompatActivity implements View.OnClickListener, ChatClientInterface {

    ChatClient client;
    EditText text;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        findViewById(R.id.sendBtn).setOnClickListener(this);

        openConnection();

    }
    // Avataan yhteys webSockettiin
    private void openConnection() {

        try{
            client = new ChatClient(new URI("wss://obscure-waters-98157.herokuapp.com"),this);
            client.connect();
        }catch (Exception e){

        }
    }

    // Lähetetään viesti ja nollataan tekstikenttä
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.sendBtn){
            if(text.length() != 0){
                sendMessage();
                text.setText("");
            }
        }
    }

    private void sendMessage() {
        if(client != null && client.isOpen()){
            String message = text.getText().toString();
            client.send(message);
        }
    }

    @Override
    public void onMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.append(message + "\n");
            }
        });
    }

    @Override
    public void onStatusChange(final String status) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.append(status + "\n");
            }
        });
    }
}
