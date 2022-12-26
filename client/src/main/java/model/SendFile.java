package model;

import api.Command;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Podvorchan Ruslan 26.12.2022
 */
public class SendFile implements Command {

  private DataOutputStream out;

  public SendFile(DataOutputStream out) {
    this.out = out;
  }

  @Override
  public boolean execute(String... args) throws IOException {
    if (args.length < 2) {
      System.out.println("Select a path to file!");
    } else {
      new FileSender().send(args[1], out);
    }
    return false;
  }

}
