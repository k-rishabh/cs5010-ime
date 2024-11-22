package controller;

/**
 * Interface for a controller that specifies a singular function - execute. This function is called
 * from the main function and is responsible for starting the program.
 */
public interface ImageController {
  /**
   * The main (go) function of the controller that is responsible for starting the program.
   * It receives input from CLI/view and parses it using a command map. The work is then
   * delegated to the command package. The command package will in turn use model functions to
   * operate on the model.
   * The command package automatically adds the resulting image(s) to the map, since it is
   * provided as input, along with the parameters of the command.
   */
  void execute();
}
