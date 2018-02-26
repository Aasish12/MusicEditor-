package cs3500.music.controller.code;

/**
 * Interface that handles actions such as mouse clicks and keyboard presses.
 */
public interface IController {

  /**
   * Listens for key presses and/or mouse clicks & performs the appropriate method in the
   * view/model.
   */
  void handle();
}
