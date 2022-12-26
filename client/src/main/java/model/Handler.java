package model;

import api.Command;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Podvorchan Ruslan 26.12.2022
 */
public class Handler implements Runnable {

  private static final Map<String, Command> COMMANDS = new HashMap<>();

  private Socket socket;
  private DataInputStream in;
  private DataOutputStream out;

  public Handler(Socket socket) {
    this.socket = socket;
    try {
      in = new DataInputStream(socket.getInputStream());
      out = new DataOutputStream(socket.getOutputStream());
    } catch (IOException e) {
      closeConnection();
    }
    initCommands();
  }

  @Override
  public void run() {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      try {
        String input = scanner.nextLine();
        String[] arguments = input.split("\\s+");
        if (COMMANDS.containsKey(arguments[0])) {
          if (COMMANDS.get(arguments[0]).execute(arguments)) {
            break;
          }
        } else {
          out.writeUTF(input);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    closeConnection();
  }

  private void closeConnection() {
    try {
      in.close();
      out.close();
      socket.close();
    } catch (IOException e) {
      System.out.println("Disconnected.");
    }
  }

  private void initCommands() {
    COMMANDS.put("-exit", new CommandExit());
    COMMANDS.put("-file", new SendFile(out));
  }

}
