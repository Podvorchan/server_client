package api;

import java.io.IOException;

/**
 * @author Podvorchan Ruslan 26.12.2022
 */
public interface Command {

  boolean execute(String... args) throws IOException;

}
