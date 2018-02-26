package cs3500.music.controller.tests;

import org.junit.Test;


import javax.sound.midi.Sequencer;

import cs3500.music.model.Note;
import cs3500.music.model.Song;
import cs3500.music.view.composite.CompositeView;
import cs3500.music.view.midi.MidiViewImpl;
import cs3500.music.view.visual.GuiViewFrame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ControllerTest {
  Song song2 = new Song();
  Note noteQ;
  Note noteH;
  Note noteW;
  MidiViewImpl midi;
  GuiViewFrame visual;
  CompositeView composite;


  /**
   * Constructor will build the classes being tested along with a song to pass into them.
   */
  public ControllerTest() {
    this.noteQ = new Note(Note.Pitch.A_NAT, 0, 1, 1, 0);
    this.noteH = new Note(Note.Pitch.C_NAT, 0, 2, 1, 0);
    this.noteW = new Note(Note.Pitch.G_SHARP, 0, 4, 1, 0);
    this.song2.addNote(this.noteQ, 4);
    this.song2.addNote(this.noteH, 0);
    this.song2.addNote(this.noteW, 1);
    this.song2.addNote(this.noteQ, 10);
    this.midi = new MidiViewImpl(this.song2);
    this.visual = new GuiViewFrame(this.song2);
    this.composite = new CompositeView(this.song2);
  }

  @Test
  public void testMidiViewPause() {
    this.midi.startMidi();
    this.midi.keyP();
    Sequencer seq = this.midi.getSeq();
    assertFalse(seq.isRunning());
  }

  @Test
  public void testMidiViewPlay() {
    this.midi.startMidi();
    this.midi.keyP();
    this.midi.keyP();
    Sequencer seq = this.midi.getSeq();
    assertTrue(seq.isRunning());
  }

  @Test
  public void testVisualViewValidKeyLeft() {
    this.visual.initialize();
    this.visual.keyRight();
    this.visual.keyLeft();
    assertEquals(this.visual.getSong().getNumBeats(), 0);
  }



  @Test
  public void testVisualViewKeyEnd() {
    this.visual.initialize();
    this.visual.keyEnd();
    assertEquals(this.visual.getSong().getNumBeats(), song2.getNumBeats());
  }

  @Test
  public void testMidiViewValidKeyLeft() {
    this.midi.startMidi();
    this.midi.keyRight();
    this.midi.keyLeft();
    Sequencer seq = this.midi.getSeq();
    assertEquals(seq.getTickPosition(), 0);
  }


  @Test
  public void testMidiViewKeyHome() {
    this.midi.startMidi();
    Sequencer seq = this.midi.getSeq();
    this.midi.keyHome();
    assertEquals(seq.getTickPosition(), 0);
  }

  @Test
  public void testMidiViewKeyEnd() {
    this.midi.startMidi();
    Sequencer seq = this.midi.getSeq();
    this.midi.keyEnd();
    assertEquals(seq.getTickPosition(), this.song2.getNumBeats());
  }

}


