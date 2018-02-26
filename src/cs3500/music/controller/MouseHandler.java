package cs3500.music.controller;

import cs3500.music.view.IView;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * Class that handles all mouse clicks.
 */
public class MouseHandler implements MouseListener {

  private IView view;

  // constructor
  MouseHandler(IView view) {
    this.view = view;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    view.addNote(e.getX(), e.getY());
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
}