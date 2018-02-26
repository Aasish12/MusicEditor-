package cs3500.music.controller.tests;

import static junit.framework.TestCase.assertEquals;

import cs3500.music.controller.code.KeyHandler;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 * Tests the methods of the KeyHandler class.
 */
public class KeyHandlerTest {

  private KeyHandler kh;
  ArrayList<Integer> log;

  private void setKeyHandler() {
    Map<Integer, Runnable> keyPressed = new HashMap<>();
    this.log = new ArrayList<>();

    keyPressed.put(KeyEvent.VK_LEFT, () -> this.log.add(KeyEvent.VK_LEFT));
    keyPressed.put(KeyEvent.VK_RIGHT, () -> this.log.add(KeyEvent.VK_RIGHT));
    keyPressed.put(KeyEvent.VK_P, () -> this.log.add(KeyEvent.VK_P));
    keyPressed.put(KeyEvent.VK_HOME, () -> this.log.add(KeyEvent.VK_HOME));
    keyPressed.put(KeyEvent.VK_END, () -> this.log.add(KeyEvent.VK_END));

    this.kh = new KeyHandler(keyPressed);
  }

  private KeyEvent makeKeyEvent(int event) {
    return new KeyEvent(new Component(){}, 1, 20, 1,
        KeyEvent.VK_RIGHT, 'a');
  }

  @Test
  public void testKeyRight() {
    this.setKeyHandler();
    int sizeBefore = this.log.size();
    this.kh.keyPressed(this.makeKeyEvent(KeyEvent.VK_RIGHT));
    int sizeAfter = this.log.size();
    assertEquals(sizeBefore + 1,sizeAfter);
  }

  @Test
  public void testKeyLeft() {
    this.setKeyHandler();
    int sizeBefore = this.log.size();
    this.kh.keyPressed(this.makeKeyEvent(KeyEvent.VK_LEFT));
    int sizeAfter = this.log.size();
    assertEquals(sizeBefore + 1,sizeAfter);
  }

  @Test
  public void testKeyP() {
    this.setKeyHandler();
    int sizeBefore = this.log.size();
    this.kh.keyPressed(this.makeKeyEvent(KeyEvent.VK_P));
    int sizeAfter = this.log.size();
    assertEquals(sizeBefore + 1,sizeAfter);
  }

  @Test
  public void testKeyHome() {
    this.setKeyHandler();
    int sizeBefore = this.log.size();
    this.kh.keyPressed(this.makeKeyEvent(KeyEvent.VK_HOME));
    int sizeAfter = this.log.size();
    assertEquals(sizeBefore + 1,sizeAfter);
  }

  @Test
  public void testKeyEnd() {
    this.setKeyHandler();
    int sizeBefore = this.log.size();
    this.kh.keyPressed(this.makeKeyEvent(KeyEvent.VK_END));
    int sizeAfter = this.log.size();
    assertEquals(sizeBefore + 1,sizeAfter);
  }
}