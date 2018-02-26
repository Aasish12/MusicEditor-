package cs3500.music.view.visual;

import cs3500.music.model.Note;
import cs3500.music.model.Song;
import cs3500.music.view.IView;
import cs3500.music.view.midi.MidiViewImpl;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

/**
 * Frame for the visual view of the Song.
 */
public class GuiViewFrame extends JFrame implements IView {

  // panel for the keyboard
  private final KeyboardPanel keyboardPanel;
  // panel for the song composition
  private final TrackPanel trackPanel;
  // song to be rendered
  private Song song;
  // key listener to be used for panelling back and forth
  private KeyListener kl;
  // mouse listener to be used
  private MouseListener ml;
  // scroll
  private JScrollPane scroll;

  private MidiViewImpl midi;

  /**
   * constructor for the guiviewframe with the composite view.
   *
   * @param song the song (model) to be used
   * @param midi the midi view linked to the gui view
   */
  public GuiViewFrame(Song song, MidiViewImpl midi) {
    this(song);
    this.midi = midi;
  }

  /**
   * constructor for the guiviewframe without using composite view.
   *
   * @param song song to be used by the view
   */
  public GuiViewFrame(Song song) {

    this.song = song;

    // set layout as border layout
    this.setLayout(new BorderLayout());

    // initialze panels
    this.keyboardPanel = new KeyboardPanel(song);
    this.trackPanel = new TrackPanel(this.song);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    // closes when 'x' button is clicked

    this.createPanel();
  }

  private void createPanel() {
    // add panels to frame
    Container c = this.getContentPane();

    c.add(keyboardPanel, BorderLayout.SOUTH);
    c.add(trackPanel, BorderLayout.NORTH);

    // 6/19 removed old KeyListener

    this.scroll = new JScrollPane(trackPanel);
    trackPanel.setAutoscrolls(true);
    scroll.setPreferredSize(new Dimension(1200, 300));
    this.add(scroll);
    this.trackPanel.setFocusable(true);
    this.keyboardPanel.setFocusable(true);
    this.pack();
  }

  /**
   * Sets up and renders the view.
   */
  public void initialize() {
    this.setVisible(true);
    if (midi != null) {

      this.midi.startMidi();
      new java.util.Timer().scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
          float tick = midi.getSeq().getTickPosition();
          if (song.getCurrentBeat() != Math.round(tick)) {
            song.setCurrentBeat(Math.round(tick));
            scroll.getHorizontalScrollBar().setValue(song.getCurrentBeat() * 30);
            reDraw();
          }
        }
      }, 0, 30);
      this.midi.maintainMidi();
    }
  }

  /**
   * Sets the dimensions of the frame.
   *
   * @return the dimension of the frame
   */
  public Dimension getPreferredSize() {
    return new Dimension(1200, 600);
  }

  /**
   * Rerenders the view.
   */
  public void reDraw() {
    revalidate();
    repaint();
  }

  /**
   * Makes it so that you can click and use key events on the view.
   */
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  /**
   * This method adds a note to the song that shows up in both the keyboard and track panels.
   *
   * @param x x position of the click on the keyboard panel
   * @param y y position of the click on the keyboard panel
   */
  public void addNote(int x, int y) {
    try {
      Note noteToAdd = this.keyboardPanel.noteToAdd(x, y);
      song.addNote(noteToAdd, song.getCurrentBeat());
      this.reDraw();

    } catch (Exception e) {
      // do nothing
    }
  }

  /**
   * Cannot play/pause in the gui view.
   */
  public void keyP() {
    if (midi != null) {
      this.midi.getSeq().setTempoInMPQ(song.getTempo());
    }
  }

  /**
   * Moves the current beat up by one.
   */
  public void keyRight() {
    if (midi == null) {
      if (song.getCurrentBeat() != song.getNumBeats()) {
        song.setCurrentBeat(song.getCurrentBeat() + 1);
      } else {
        this.midi.keyRight();
      }
      int beatWidth = 30;
      if ((song.getCurrentBeat() * beatWidth) % 1110 == 0) {
        scroll.getHorizontalScrollBar().setValue(song.getCurrentBeat() * beatWidth);
      }
      this.reDraw();
    }
  }

  /**
   * Moves the current beat down by one.
   */
  public void keyLeft() {
    if (midi == null) {
      if (song.getCurrentBeat() != 0) {
        song.setCurrentBeat(song.getCurrentBeat() - 1);
      } else {
        this.midi.keyLeft();
      }
      int beatWidth = 30;
      if ((song.getCurrentBeat() * beatWidth) % 1110 == 0) {
        scroll.getHorizontalScrollBar().setValue((song.getCurrentBeat() * beatWidth) - 1110);
      }
      this.reDraw();
    }
  }

  /**
   * Resets the current beat to the beginning of the song.
   */
  public void keyHome() {
    song.setCurrentBeat(0);
    this.reDraw();
  }

  /**
   * Resets the current beat to the end of the song.
   */
  public void keyEnd() {
    this.reDraw();
  }

  /**
   * Class used for testing the gui.
   *
   * @return The song being updated by key board clicks.
   */
  public Song getSong() {
    return this.song;
  }
}