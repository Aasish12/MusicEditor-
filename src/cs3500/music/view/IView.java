package cs3500.music.view;

import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * Interface holding all methods for and overarching all views.
 */
public interface IView {

  /**
   * Begin running the view.
   */
  public void initialize();

  /**
   * This method gets a Dimension to set the size of the frame.
   */
  public Dimension getPreferredSize();

  /**
   * Revalidates and repaints the view.
   */
  void reDraw();

  /**
   * Refocuses the view so that it is ready to show.
   */
  void resetFocus();

  /**
   * Links the KeyListener to the view.
   * @param kl KeyListener to be linked with view
   */
  void addKeyListener(KeyListener kl);

  /**
   * Links the MouseListener to the view.
   * @param ml MouseListener to be linked with view
   */
  void addMouseListener(MouseListener ml);

  /**
   * Adds a note to the view's corresponding model.
   * @param x x position of the click on the keyboard panel
   * @param y y position of the click on the keyboard panel
   */
  void addNote(int x, int y);

  /**
   * Plays/pauses the player.
   */
  void keyP();

  /**
   * Moves the current beat forward one.
   */
  void keyRight();

  /**
   * Moves the current beat back one.
   */
  void keyLeft();

  /**
   * Moves the current beat to the beginning of the Song.
   */
  void keyHome();

  /**
   * Moves the current beat to the end of the song.
   */
  void keyEnd();
}

