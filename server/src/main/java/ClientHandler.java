import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;

/**
 * @author Podvorchan Ruslan 26.12.2022
 */
public class ClientHandler implements Runnable {

  private final Socket socket;
  private final String name;
  private final LocalDateTime time;
  private DataInputStream in;
  private DataOutputStream out;
  private final OnDisconnect handler;

  public String getName() {
    return name;
  }

  public interface OnDisconnect {

    void disconnect(ClientHandler clientHandler);
  }

  public ClientHandler(Socket socket, String name, OnDisconnect handler) {
    this.socket = socket;
    this.name = name;
    this.handler = handler;
    time = LocalDateTime.now();
    try {
      in = new DataInputStream(socket.getInputStream());
      out = new DataOutputStream(socket.getOutputStream());
    } catch (IOException e) {
      closeInputOutput();
    }
  }

  @Override
  public void run() {
    try {
      while (true) {
        String response = in.readUTF();
        if (response.startsWith("-file")) {
          String path = response.split("-file\\s+")[1];
          new FileReceiver().receive(path, name);
        } else {
          Server.log.info("[MESSAGE] " + name + ": " + response);
          out.writeUTF("Echo: " + response);
        }
      }
    } catch (IOException ex) {
      handler.disconnect(this);
      closeInputOutput();
    }
  }
  public void sendMessage(String message) {
    try {
      out.writeUTF(message);
    } catch (IOException e) {
      closeInputOutput();
    }
  }
  private void closeInputOutput() {
    try {
      in.close();
      out.close();
    } catch (IOException e) {
      throw new RuntimeException("Something went wrong with client I/O", e);
    }
  }
}
