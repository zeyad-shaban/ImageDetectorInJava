package Server;

import com.sun.net.httpserver.HttpServer;

import NeuralNetwork.NeuralNetwork;
import NeuralNetwork.Math.Math;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class ImageProcessingHttpServer {
    private static NeuralNetwork model;

    public static void start(NeuralNetwork model) throws IOException {
        ImageProcessingHttpServer.model = model;
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/", new MyHandler());

        server.setExecutor(null);
        server.start();

        InetAddress localHost = InetAddress.getLocalHost();
        String hostAddress = localHost.getHostAddress();
        System.out.println("Server running on " + hostAddress + ":8000");
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (exchange.getRequestMethod().equals("POST")) {
                InputStream is = exchange.getRequestBody();
                String requestBody = new String(is.readAllBytes());

                try {
                    JSONObject jsonRequest = new JSONObject(requestBody);
                    if (jsonRequest.has("pixels")) {
                        JSONArray pixelsArray = jsonRequest.getJSONArray("pixels");
                        float[][] pixels = parsePixels(pixelsArray);

                        float[] result_probs = model.forward(pixels);
                        int result = Math.argMax(result_probs);
                        String response = String.valueOf(result);
                        exchange.getResponseHeaders().set("Content-Type", "text/plain");
                        exchange.sendResponseHeaders(200, response.length());

                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    } else {
                        sendErrorResponse(exchange, "Missing 'pixels' field");
                    }
                } catch (Exception e) {
                    sendErrorResponse(exchange, "Invalid request: " + e.getMessage());
                }
            } else if (exchange.getRequestMethod().equals("GET")) {
                exchange.sendResponseHeaders(200, -1);
                exchange.close();
            } else {
                sendErrorResponse(exchange, "Only POST and GET requests are supported");
            }
        }

        private float[][] parsePixels(JSONArray pixelsArray) {
            int rows = pixelsArray.length();
            float[][] pixels = new float[rows][];
            for (int i = 0; i < rows; i++) {
                JSONArray rowArray = pixelsArray.getJSONArray(i);
                int cols = rowArray.length();
                pixels[i] = new float[cols];
                for (int j = 0; j < cols; j++) {
                    pixels[i][j] = (float) rowArray.getDouble(j);
                }
            }
            return pixels;
        }

        private void sendErrorResponse(HttpExchange exchange, String message) throws IOException {
            exchange.sendResponseHeaders(400, message.length());
            OutputStream os = exchange.getResponseBody();
            os.write(message.getBytes());
            os.close();
        }
    }
}