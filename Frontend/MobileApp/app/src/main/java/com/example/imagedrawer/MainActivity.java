package com.example.imagedrawer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {
    private PixelBoard pixelBoard;
    private TextView predictionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pixelBoard = findViewById(R.id.pixel_board);
        predictionText = findViewById(R.id.prediction_text);
    }

    public void onPredictClick(View view) {
        String ipAddress = getIntent().getStringExtra("IP_ADDRESS");
        Intent serverIntent = new Intent(this, ServerIpSetup.class);
        PixelBoard pixelBoard = findViewById(R.id.pixel_board);
        int[][] pixels = pixelBoard.getPixels();
        view.setEnabled(false);
        AtomicReference<String> responseString = new AtomicReference<String>("");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + ipAddress + "/");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("pixels", new JSONArray(pixels));

                    byte[] jsonData = jsonObject.toString().getBytes(StandardCharsets.UTF_8);

                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("Content-Length", String.valueOf(jsonData.length));
                    connection.setDoOutput(true);

                    try (OutputStream os = connection.getOutputStream()) {
                        os.write(jsonData);
                    }

                    try (InputStream is = connection.getInputStream();
                         BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }

                        responseString.set(response.toString());
                    }

                    connection.disconnect();
                    runOnUiThread(() -> {
                        predictionText.setText(responseString.get());
                        view.setEnabled(true);
                    });
                } catch (IOException e) {
                    runOnUiThread(() -> {
                        view.setEnabled(true);
                        startActivity(serverIntent);
                    });
                } catch (JSONException e) {
                    runOnUiThread(() -> {
                        view.setEnabled(true);
                        e.printStackTrace();
                    });
                }
            }
        }).start();
    }

    public void onEraseClick(View view) {
        pixelBoard.clearPixels();
    }
}
