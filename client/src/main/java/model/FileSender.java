package model;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * @author Podvorchan Ruslan 26.12.2022
 */
public class FileSender {

  public void send(String path, DataOutputStream out) throws IOException {
    if (!new File(path).exists()) {
      System.out.println("The file '" + path + "' doesn't exist!");
      return;
    }
    out.writeUTF("-file " + path);
    System.out.println("The file has been sent.");
  }

}
