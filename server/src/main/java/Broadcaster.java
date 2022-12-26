import java.util.List;

/**
 * @author Podvorchan Ruslan 26.12.2022
 */
public class Broadcaster {

  public void sendAll(List<ClientHandler> activeClients, ClientHandler clientHandler,
      String message) {
    for (ClientHandler client : activeClients) {
      if (client != clientHandler) {
        client.sendMessage(message);
      }
    }
  }

}
