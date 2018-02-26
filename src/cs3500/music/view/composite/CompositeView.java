package cs3500.music.view.composite;

import cs3500.music.model.Song;
import cs3500.music.view.IView;
import cs3500.music.view.midi.MidiViewImpl;
import cs3500.music.view.visual.GuiViewFrame;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

/**
 * View that uses both the MIDI and Visual views concurrently.
 */
public class CompositeView extends JFrame implements IView {
  private MidiViewImpl midi;
  private GuiViewFrame gui;

  /**
   * Constructor for the composite view.
   *
   * @param song Song to be linked to the view
   */
  public CompositeView(Song song) {
    this.midi = new MidiViewImpl(song);
    this.gui = new GuiViewFrame(song, this.midi);
  }

  /**
   * Sets up the view so that it renders its data.
   */
  public void initialize() {
    this.gui.initialize();
  }

  @Override
  public void addKeyListener(KeyListener kh) {
    this.gui.addKeyListener(kh);
  }

  @Override
  public void addMouseListener(MouseListener ml) {
    this.gui.addMouseListener(ml);
  }

  public Dimension getPreferredSize() {
    return new Dimension();
  }

  public void reDraw() {
    this.gui.reDraw();
  }

  public void resetFocus() {
    this.gui.resetFocus();
  }


  public void addNote(int x, int y) {
    this.gui.addNote(x, y);
  }

  public void keyP() {
    this.midi.keyP();
    this.gui.keyP();
  }

  public void keyRight() {
    this.gui.keyRight();
    this.midi.keyRight();
  }

  public void keyLeft() {
    this.gui.keyLeft();
    this.midi.keyLeft();
  }

  public void keyHome() {
    this.gui.keyHome();
    this.midi.keyHome();
  }

  public void keyEnd() {
    this.gui.keyEnd();
    this.midi.keyEnd();
  }
}