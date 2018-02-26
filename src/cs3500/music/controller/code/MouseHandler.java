package cs3500.music.controller.code;

import cs3500.music.view.IView;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * Class that handles all mouse clicks.
 */
public class MouseHandler implements MouseListener {

  private IView view;
  // for testing purposes
  private int log;

  // constructor
  public MouseHandler(IView view) {
    this.view = view;
    this.log = 0;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    view.addNote(e.getX(), e.getY());
    this.log++;
  }

  @Override
  public void mousePressed(MouseEvent e) {
    // do nothing
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // do nothing
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // do nothing
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // do nothing
  }

  /**
   * Returns the log, for testing purposes only.
   * @return integer representing how many mouse clicks have occured
   */
  public int getLog() {
    return this.log;
  }
}