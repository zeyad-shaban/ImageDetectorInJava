import Server.ImageProcessingHttpServer;
import NeuralNetwork.Layer.Layer;

import java.io.IOException;

import NeuralNetwork.*;

public class Main {
  public static void main(String[] args) {
    NeuralNetwork model = createNeuralModel();
    startServer(args, model);
  }
  
  public static void startServer(String[] args, NeuralNetwork model) {
    try {
      ImageProcessingHttpServer.start(args, model);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static NeuralNetwork createNeuralModel() {
    NeuralNetwork model = new NeuralNetwork(
        new Layer(28 * 28, 512, "relu"),
        new Layer(512, 256, "relu"),
        new Layer(256, 128, "relu"),
        new Layer(128, 32, "relu"),
        new Layer(32, 10, "relu"));

    model.loadParamsFromJson("./src/main/notebook/model_params.json");

    return model;
  }
}