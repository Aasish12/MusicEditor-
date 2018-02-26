package cs3500.music.controller.code;

import java.awt.event.KeyListener;
import java.util.Map;

/**
 * Class that handles all key events.
 */
public class KeyHandler implements KeyListener {

  private Map<Integer, Runnable> keyPressedMap;

  /**
   * constructor for main method.
   */
  public KeyHandler() {
    // does nothing
  }

  /**
   * constructor for testing.
   * @param keyMap keymap to be used for the listener
   */
  public KeyHandler(Map<Integer, Runnable> keyMap) {
    this.keyPressedMap = keyMap;
  }

  @Override
  public void keyTyped(java.awt.event.KeyEvent e) {
    // do nothing
  }

  @Override
  public void keyPressed(java.awt.event.KeyEvent e) {
    if (keyPressedMap.containsKey(e.getKeyCode())) {
      keyPressedMap.get(e.getKeyCode()).run();
    }
  }

  @Override
  public void keyReleased(java.awt.event.KeyEvent e) {
    // do nothing
  }

  /**
   * Set the map for key pressed events. Key pressed events in Java Swing are integer codes
   */
  public void setKeyPressedMap(Map<Integer, Runnable> map) {
    this.keyPressedMap = map;
  }
}