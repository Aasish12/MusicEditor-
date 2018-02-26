package cs3500.music.view.midi;

import cs3500.music.model.Note;
import cs3500.music.model.Song;
import cs3500.music.view.IView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A skeleton for MIDI playback.
 */
public class MidiViewImpl extends JFrame implements IView {

  private Song song;
  // 6/19 added
  private boolean isPlay;
  // 6/19 added as a field
  private Sequencer seq;
  // 6/19 added
  private long tickPos;
  private MouseListener ml;
  private KeyListener kl;

  private JFrame frame;

  /**
   * Constructor for the MIDI view.
   *
   * @param song song to be played
   */
  public MidiViewImpl(Song song) {
    this.song = song;
  }

  public MidiViewImpl(Song song, Sequencer seq) {
    this.song = song;
    this.seq = seq;
  }

  /**
   * Relevant classes and methods from the javax.sound.midi library:
   * <ul>
   *  <li>{@link MidiSystem#getSynthesizer()}</li>
   *  <li>{@link Synthesizer}
   *    <ul>
   *      <li>{@link Synthesizer#open()}</li>
   *      <li>{@link Synthesizer#getReceiver()}</li>
   *      <li>{@link Synthesizer#getChannels()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link Receiver}
   *    <ul>
   *      <li>{@link Receiver#send(MidiMessage, long)}</li>
   *      <li>{@link Receiver#close()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link MidiMessage}</li>
   *  <li>{@link ShortMessage}</li>
   *  <li>{@link MidiChannel}
   *    <ul>
   *      <li>{@link MidiChannel#getProgram()}</li>
   *      <li>{@link MidiChannel#programChange(int)}</li>
   *    </ul>
   *  </li>
   * </ul>
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI">
   *   https://en.wikipedia.org/wiki/General_MIDI
   *   </a>
   */

  /**
   * Starts the view so that it renders its data.
   */
  public void startMidi() {
    try {
      this.buildSeq();
      this.seq.open();
      // 6/19 added
      this.isPlay = true;
      // 6/19 added
      this.tickPos = this.seq.getTickPosition();

      this.seq.start();
      seq.setTempoInMPQ(song.getTempo());

    } catch (Exception e) {
      // do nothing
    }
  }

  /**
   * Starts the sequencer in the midi view.
   */
  public void maintainMidi() {
    try {
      Thread.sleep(2000000000);
      // end sequencer
      this.seq.close();
      System.exit(0);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * Sets up the view so that it renders its data.
   */
  public void initialize() {
    try {
      this.buildJFrame();
      this.frame.setVisible(true);
      this.frame.setFocusable(true);
      this.startMidi();
      this.maintainMidi();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void buildJFrame() {
    this.frame = new JFrame();
    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.frame.setSize(1200, 600);
    JPanel panel = new JPanel();
    panel.setPreferredSize(new Dimension(1200, 600));
    this.frame.getContentPane().add(panel, BorderLayout.CENTER);
    this.frame.setLocationRelativeTo(null);
    this.frame.pack();
    this.frame.setResizable(false);
  }

  private void buildSeq() {
    try {
      // set up synthesizer, get instruments
      Synthesizer syn = MidiSystem.getSynthesizer();
      syn.loadAllInstruments(syn.getDefaultSoundbank());

      // set up sequencer
      this.seq = MidiSystem.getSequencer();
      this.seq.open();
      Sequence mySeq = new Sequence(Sequence.PPQ, 1);
      seq.setTempoInMPQ(song.getTempo());

      // set up track
      Track track = mySeq.createTrack();

      // play notes by sending midi messages (durations won't be directly played!)
      HashMap<String, Note> playable = song.getNotes();
      for (Map.Entry<String, Note> entry : playable.entrySet()) {
        // check to see if it is the start of the note (not duration!)
        if (!entry.getKey().contains("-")) {
          Note n = entry.getValue();
          Scanner scan = new Scanner(entry.getKey());
          int startBeat = scan.nextInt();
          try {
            MidiMessage on = new ShortMessage(ShortMessage.NOTE_ON, n.getInstrument() - 1,
                n.getPitch().ordinal() + 12 * n.getOctave(),
                n.getVolume());
            MidiEvent noteOn = new MidiEvent(on, startBeat);

            MidiMessage off = new ShortMessage(ShortMessage.NOTE_OFF, n.getInstrument() - 1,
                n.getPitch().ordinal() + 12 * n.getOctave(),
                n.getVolume());
            MidiEvent noteOff = new MidiEvent(off, n.getBeats() + startBeat);
            track.add(noteOn);
            track.add(noteOff);
            // start sequencer playing

          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }
      }
      this.seq.setSequence(mySeq);
      this.tickPos = this.seq.getTickPosition();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public Dimension getPreferredSize() {
    return new Dimension();
  }

  /**
   * Functionality only for gui view.
   */
  public void reDraw() {
    throw new IllegalArgumentException("Can't reDraw in the MIDI view");
  }

  /**
   * Functionality not in midi view.
   */
  public void resetFocus() {
    // does nothing
  }

  /**
   * Method used to add a note on a click. (functionality not in midi view)
   */
  public void addNote(int x, int y) {
    //do nothing p
  }

  /**
   * Method used to play/pause the game.
   */
  // added 6/20
  public void keyP() {
    this.seq.setTempoInMPQ(song.getTempo());
    this.isPlay = !this.isPlay;
    this.resetSequencer();
  }

  /**
   * This method adds one to the current tick.
   */
  // added 6/20
  public void keyRight() {
    this.tickPos = seq.getTickPosition();
    if (this.isPlay) {
      this.isPlay = false;
    }
    if (tickPos < song.getNumBeats() - 1) {
      tickPos += 1;
      this.seq.setTickPosition(tickPos);
      this.resetSequencer();
    }
  }

  /**
   * This method subtracts one from the current tick.
   */
  // added 6/20
  public void keyLeft() {
    this.tickPos = seq.getTickPosition();
    if (this.isPlay) {
      this.isPlay = false;
    }
    if (tickPos > 0) {
      seq.setTickPosition(tickPos - 1);
      this.resetSequencer();
    }
  }

  /**
   * Method used to start over the song on key.
   */
  // added 6/20
  public void keyHome() {
    this.seq.setTickPosition(0);
    this.resetSequencer();
    this.seq.setTempoInMPQ(song.getTempo());
  }

  /**
   * Method used to end the song on key.
   */
  // added 6/20
  public void keyEnd() {
    this.seq.setTickPosition(song.getNumBeats());
    this.resetSequencer();
    this.seq.setTempoInMPQ(song.getTempo());
  }

  /**
   * This method places the sequencer at the point in the Song where the current beat lies.
   */
  // 6/19 added
  private void resetSequencer() {
    float tempo = this.seq.getTempoInMPQ();
    if (this.isPlay) {
      // star the Song at the tick it was paused at
      this.seq.setTempoInMPQ(tempo);
      this.seq.start();
      this.seq.setTempoInMPQ(tempo);
    } else {
      this.seq.stop();
    }
  }

  //not returning a copy, we want to be able to modify outside of the class
  public Sequencer getSeq() {
    return this.seq;
  }
}