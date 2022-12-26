package model;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Podvorchan Ruslan 26.12.2022
 */
public class Client {

  public static final int PORT = 8081;

  public Client() {
    System.out.println("Connecting to the server...");
    try (Socket socket = new Socket("localhost", PORT)) {
      System.out.println("You have successfully connected to the server!");
      try (DataInputStream in = new DataInputStream(socket.getInputStream())) {
        new Thread(new Handler(socket)).start();
        while (true) {
          System.out.println("Message: " + in.readUTF());
        }
      } catch (IOException e) {
        throw new RuntimeException("Disconnected from the server", e);
      }
    } catch (IOException e) {
      throw new RuntimeException("Something went wrong with the connection.", e);
    }
  }

}
