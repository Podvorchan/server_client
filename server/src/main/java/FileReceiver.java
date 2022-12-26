import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Podvorchan Ruslan 26.12.2022
 */
public class FileReceiver {

  private static final String PATH_FOR_SAVING = "server/src/main/resources/";

  public void receive(String path, String name) throws IOException {
    Path fileName = Path.of(path).getFileName();
    Path newPath = Path.of(PATH_FOR_SAVING + fileName);
    System.out.println(fileName);
    try (FileInputStream fin = new FileInputStream(path);
        FileOutputStream fos = new FileOutputStream(newPath.toString())) {
      byte[] buffer = new byte[fin.available()];
      fin.read(buffer, 0, buffer.length);
      fos.write(buffer, 0, buffer.length);
      Server.log.info("[FILE] " + name + ": '" + fileName + "'");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
