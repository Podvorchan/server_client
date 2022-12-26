package model;

import api.Command;

/**
 * @author Podvorchan Ruslan 26.12.2022
 */
public class CommandExit implements Command {
  @Override
  public boolean execute(String... args) {
    System.out.println("Exiting the server...");
    return true;
  }

}
