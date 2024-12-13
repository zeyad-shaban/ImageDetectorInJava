package com.example.imagedrawer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class ServerIpSetup extends AppCompatActivity {
    final static String ipAddressRegex = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])(:[0-9]+)?$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_server_ip_setup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onConnectClick(View view) {
        view.setEnabled(false);
        Intent intent = new Intent(this, MainActivity.class);
        EditText ipText = findViewById(R.id.server_ip_input);
        String ipAddress = ipText.getText().toString();

        if (!ipAddress.matches(ipAddressRegex)) {
            ipText.setError("Invalid IP address");
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + ipAddress + "/");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);

                    if (connection.getResponseCode() != 200)
                        throw new IOException();

                    runOnUiThread(() -> {
                        intent.putExtra("IP_ADDRESS", ipAddress);
                        startActivity(intent);
                        view.setEnabled(true);
                    });
                } catch (SocketTimeoutException e) {
                    runOnUiThread(() -> {
                        ipText.setError("Connection time out");
                        view.setEnabled(true);
                    });
                } catch (IOException e) {
                    runOnUiThread(() -> {
                        ipText.setError("Server down...");
                        view.setEnabled(true);
                    });
                } catch (Exception e) {
                    runOnUiThread(() -> {
                        ipText.setError("Unknown Error Occured...");
                        e.printStackTrace();
                        view.setEnabled(true);
                    });
                }
            }
        }).start();
    }
}