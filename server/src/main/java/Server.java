import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author Podvorchan Ruslan 26.12.2022
 */
public class Server implements ClientHandler.OnDisconnect {

  public static final Logger log = LogManager.getLogger(Server.class);
  public static final int PORT = 8081;

  private final List<ClientHandler> activeClients = new ArrayList<>();
  private final Broadcaster broadcaster;
  private int connections = 0;

  public Server() {
    log.info("Starting up server...");
    broadcaster = new Broadcaster();
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      log.debug("Server has started successfully and ready to accept connections!");
      while (true) {
        Socket socket = serverSocket.accept();
        String clientName = "Client - " + connections++;
        ClientHandler clientHandler = new ClientHandler(socket, clientName, this);
        new Thread(clientHandler).start();
        activeClients.add(clientHandler);
        broadcaster.sendAll(activeClients, clientHandler,
            clientName + " successfully connected to the server.");
        log.info("[CONNECTION] " + clientName + " connected.");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void disconnect(ClientHandler clientHandler) {
    String name = clientHandler.getName();
    log.info("[CONNECTION] " + name + " disconnected.");
    activeClients.remove(clientHandler);
   // broadcaster.sendAll(activeClients, clientHandler, name + " disconnected from the server.");
  }

}
