package cs3500.music.view.tests;

import org.junit.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

import cs3500.music.model.Song;
import cs3500.music.view.IView;
import cs3500.music.view.MusicReader;
import cs3500.music.view.midi.MidiViewImpl;
import cs3500.music.view.midi.MockSequencer;

import static org.junit.Assert.assertEquals;


/**
 * This class holds all of the methods used in the MidiViewImpl class.
 */
public class MidiViewTest {

  private void writeToFile(String textline) throws IOException {
    FileWriter write = new FileWriter(textline, false);
    PrintWriter println = new PrintWriter(write);

    println.println(textline);
    println.close();
  }

  @Test
  public void testMockSequencer() {
    try {
      Synthesizer syn = MidiSystem.getSynthesizer();
      syn.loadAllInstruments(syn.getDefaultSoundbank());
      Sequence sequence = new Sequence(Sequence.PPQ, 1);
      Track track = sequence.createTrack();
      MidiMessage on = new ShortMessage(ShortMessage.NOTE_ON, 1,
          64,
          68);
      MidiEvent noteOn = new MidiEvent(on, -1);

      MidiMessage off = new ShortMessage(ShortMessage.NOTE_OFF, 1, 64, 68);
      MidiEvent noteOff = new MidiEvent(off, 20);
      track.add(noteOn);
      track.add(noteOff);
      MockSequencer sequencer = new MockSequencer();
      sequencer.setTempoInMPQ(200000);
      sequencer.setSequence(sequence);
      assertEquals("Tempo: " + 200000.0 + "\n" + "Command: 144, Instrument: 1, Pitch: 64, Volume "
          + "68\n"
          + "Command: 128, Instrument: 1, Pitch: 64, Volume 68\n", sequencer.getLog().toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void createMidiTranscript() {
    Song.TrackBuilder trackbuild = new Song.TrackBuilder();
    String filePath = "C:\\Users\\Guest1\\Documents\\Music-Editor\\Resources\\mary-little-lamb"
        +
        ".txt";
    try {
      FileReader file = new FileReader(filePath);
      BufferedReader bf = new BufferedReader(file);
      Song track = MusicReader.parseFile(bf,
          trackbuild);
      MockSequencer sequencer = new MockSequencer();
      IView view = new MidiViewImpl(track, sequencer);
      view.initialize();
      //only used this once to create the txt file
      //  writeToFile("midi-transcript.txt");
      assertEquals("Tempo: 200000.0\n" + "Command: 144, Instrument: 0, Pitch: 64, Volume 72\n"
          +
          "Command: 144, Instrument: 0, Pitch: 55, Volume 70\n"
          +
          "Command: 128, Instrument: 0, Pitch: 64, Volume 72\n"
          +
          "Command: 144, Instrument: 0, Pitch: 62, Volume 72\n"
          +
          "Command: 144, Instrument: 0, Pitch: 60, Volume 71\n"
          +
          "Command: 128, Instrument: 0, Pitch: 62, Volume 72\n"
          +
          "Command: 144, Instrument: 0, Pitch: 62, Volume 79\n"
          +
          "Command: 128, Instrument: 0, Pitch: 60, Volume 71\n"
          +
          "Command: 128, Instrument: 0, Pitch: 55, Volume 70\n"
          +
          "Command: 144, Instrument: 0, Pitch: 64, Volume 85\n"
          +
          "Command: 128, Instrument: 0, Pitch: 62, Volume 79\n"
          +
          "Command: 144, Instrument: 0, Pitch: 55, Volume 79\n"
          +
          "Command: 128, Instrument: 0, Pitch: 64, Volume 85\n"
          +
          "Command: 144, Instrument: 0, Pitch: 64, Volume 78\n"
          +
          "Command: 128, Instrument: 0, Pitch: 64, Volume 78\n"
          +
          "Command: 144, Instrument: 0, Pitch: 64, Volume 74\n"
          +
          "Command: 128, Instrument: 0, Pitch: 55, Volume 79\n"
          +
          "Command: 128, Instrument: 0, Pitch: 64, Volume 74\n"
          +
          "Command: 144, Instrument: 0, Pitch: 62, Volume 75\n"
          +
          "Command: 144, Instrument: 0, Pitch: 55, Volume 77\n"
          +
          "Command: 128, Instrument: 0, Pitch: 62, Volume 75\n"
          +
          "Command: 144, Instrument: 0, Pitch: 62, Volume 77\n"
          +
          "Command: 144, Instrument: 0, Pitch: 62, Volume 75\n"
          +
          "Command: 128, Instrument: 0, Pitch: 62, Volume 77\n"
          +
          "Command: 144, Instrument: 0, Pitch: 64, Volume 82\n"
          +
          "Command: 128, Instrument: 0, Pitch: 62, Volume 75\n"
          +
          "Command: 144, Instrument: 0, Pitch: 55, Volume 79\n"
          +
          "Command: 128, Instrument: 0, Pitch: 55, Volume 77\n"
          +
          "Command: 128, Instrument: 0, Pitch: 64, Volume 82\n"
          +
          "Command: 144, Instrument: 0, Pitch: 67, Volume 84\n"
          +
          "Command: 128, Instrument: 0, Pitch: 55, Volume 79\n"
          +
          "Command: 128, Instrument: 0, Pitch: 67, Volume 84\n"
          +
          "Command: 144, Instrument: 0, Pitch: 67, Volume 75\n"
          +
          "Command: 144, Instrument: 0, Pitch: 64, Volume 73\n"
          +
          "Command: 128, Instrument: 0, Pitch: 67, Volume 75\n"
          +
          "Command: 144, Instrument: 0, Pitch: 55, Volume 78\n"
          +
          "Command: 128, Instrument: 0, Pitch: 64, Volume 73\n"
          +
          "Command: 144, Instrument: 0, Pitch: 62, Volume 69\n"
          +
          "Command: 128, Instrument: 0, Pitch: 62, Volume 69\n"
          +
          "Command: 144, Instrument: 0, Pitch: 60, Volume 71\n"
          +
          "Command: 128, Instrument: 0, Pitch: 60, Volume 71\n"
          +
          "Command: 144, Instrument: 0, Pitch: 62, Volume 80\n"
          +
          "Command: 144, Instrument: 0, Pitch: 64, Volume 84\n"
          +
          "Command: 128, Instrument: 0, Pitch: 55, Volume 78\n"
          +
          "Command: 144, Instrument: 0, Pitch: 55, Volume 79\n"
          +
          "Command: 128, Instrument: 0, Pitch: 62, Volume 80\n"
          +
          "Command: 128, Instrument: 0, Pitch: 64, Volume 84\n"
          +
          "Command: 144, Instrument: 0, Pitch: 64, Volume 76\n"
          +
          "Command: 144, Instrument: 0, Pitch: 64, Volume 74\n"
          +
          "Command: 128, Instrument: 0, Pitch: 64, Volume 76\n"
          +
          "Command: 144, Instrument: 0, Pitch: 64, Volume 77\n"
          +
          "Command: 128, Instrument: 0, Pitch: 64, Volume 74\n"
          +
          "Command: 144, Instrument: 0, Pitch: 55, Volume 78\n"
          +
          "Command: 144, Instrument: 0, Pitch: 62, Volume 75\n"
          +
          "Command: 128, Instrument: 0, Pitch: 64, Volume 77\n"
          +
          "Command: 128, Instrument: 0, Pitch: 55, Volume 79\n"
          +
          "Command: 144, Instrument: 0, Pitch: 62, Volume 74\n"
          +
          "Command: 128, Instrument: 0, Pitch: 62, Volume 75\n"
          +
          "Command: 128, Instrument: 0, Pitch: 62, Volume 74\n"
          +
          "Command: 144, Instrument: 0, Pitch: 64, Volume 81\n"
          +
          "Command: 144, Instrument: 0, Pitch: 62, Volume 70\n"
          +
          "Command: 128, Instrument: 0, Pitch: 64, Volume 81\n"
          +
          "Command: 144, Instrument: 0, Pitch: 52, Volume 72\n"
          +
          "Command: 128, Instrument: 0, Pitch: 62, Volume 70\n"
          +
          "Command: 128, Instrument: 0, Pitch: 55, Volume 78\n"
          +
          "Command: 144, Instrument: 0, Pitch: 60, Volume 73\n"
          +
          "Command: 128, Instrument: 0, Pitch: 52, Volume 72\n"
          +
          "Command: 128, Instrument: 0, Pitch: 60, Volume 73\n", sequencer.getLog().toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}