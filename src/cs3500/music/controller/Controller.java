package cs3500.music.controller;

import cs3500.music.view.IView;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Takes in all mouse and key events and makes changes in the appropriate model/view.
 */
public class Controller {

  // view to be used with the controller
  private IView view;

  /**
   * Sets the view and configures the mouse and key listeners. Links the view with the controller.
   * @param v view to be linked to the controller.
   */
  public void setView(IView v) {
    this.view = v;
    configureKeyBoardListener();
    configureMouseListener();
  }

  /**
   * Sets up the keyboard listener to begin taking in key events and making changes.
   */
  public void configureKeyBoardListener() {
    Map<Integer, Runnable> keyPresses = new HashMap<Integer, Runnable>();
    KeyHandler kh = new KeyHandler();

    keyPresses.put(KeyEvent.VK_LEFT, () -> view.keyLeft());
    keyPresses.put(KeyEvent.VK_RIGHT, () -> view.keyRight());
    keyPresses.put(KeyEvent.VK_P, () -> view.keyP());
    keyPresses.put(KeyEvent.VK_HOME, () -> view.keyHome());
    keyPresses.put(KeyEvent.VK_END, () -> view.keyEnd());

    kh.setKeyPressedMap(keyPresses);
    this.view.addKeyListener(kh);
    view.resetFocus();
  }

  /**
   * Sets up the mouse listener to begin taking in mouse events and making changes.
   */
  public void configureMouseListener() {
    MouseHandler mh = new MouseHandler(view);

    this.view.addMouseListener(mh);
    view.resetFocus();
  }
}