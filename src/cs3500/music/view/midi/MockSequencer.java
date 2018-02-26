package cs3500.music.view.midi;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;

/**
 * This is the mock sequencer which can build a log of all the messages.
 */
public class MockSequencer implements Sequencer {
  public StringBuilder log = new StringBuilder();
  public Sequence sequence;
  public float tempo;

  /**
   * default constructor will not do anything. The log will be built immediately.
   */
  public MockSequencer() {
    // does nothing
  }

  /**
   * This will parse through the sequence to get the all the messages in the track events. This
   * model assumes we only have one track in the sequence. For our use we never build more than 1
   * track so this is fine.
   *
   * @return the log that is built
   */
  public StringBuilder getLog() {
    this.log.append("Tempo: " + tempo + "\n");
    Track[] track = sequence.getTracks();
    for (int i = 0; i < track[0].size(); i++) {
      MidiEvent e = track[0].get(i);
      if (e.getMessage() instanceof ShortMessage) {
        ShortMessage s = (ShortMessage) e.getMessage();
        this.log.append("Command: " + s.getCommand() + ", Instrument: " + s.getChannel()
                + ", Pitch: " + s.getData1() + ", Volume " + s.getData2() + "\n");

      }
    }
    return log;
  }

  @Override
  public void setSequence(Sequence sequence) throws InvalidMidiDataException {
    this.sequence = sequence;
    //do nothing
  }

  @Override
  public void setSequence(InputStream stream) throws IOException, InvalidMidiDataException {
    //do nothing
  }

  @Override
  public Sequence getSequence() {
    return this.sequence;
  }

  @Override
  public void start() {
    //do nothing
  }

  @Override
  public void stop() {
    //do nothing
  }

  @Override
  public boolean isRunning() {
    return false;
  }

  @Override
  public void startRecording() {
    //do nothing
  }

  @Override
  public void stopRecording() {
    //do nothing
  }

  @Override
  public boolean isRecording() {
    return false;
  }

  @Override
  public void recordEnable(Track track, int channel) {
    // do nothing
  }

  @Override
  public void recordDisable(Track track) {
    //do nothing
  }

  @Override
  public float getTempoInBPM() {
    return tempo;
  }

  @Override
  public void setTempoInBPM(float bpm) {
    tempo = bpm;
  }

  @Override
  public float getTempoInMPQ() {
    return tempo;
  }

  @Override
  public void setTempoInMPQ(float mpq) {
    tempo = mpq;
  }

  @Override
  public void setTempoFactor(float factor) {
    tempo = factor;
  }

  @Override
  public float getTempoFactor() {
    return tempo;
  }

  @Override
  public long getTickLength() {
    return 0;
  }

  @Override
  public long getTickPosition() {
    return 0;
  }

  @Override
  public void setTickPosition(long tick) {
    //do nothing
  }

  @Override
  public long getMicrosecondLength() {
    return 0;
  }

  @Override
  public Info getDeviceInfo() {
    return null;
  }

  @Override
  public void open() throws MidiUnavailableException {
    //do nothing
  }

  @Override
  public void close() {
    //do nothing
  }

  @Override
  public boolean isOpen() {
    return false;
  }

  @Override
  public long getMicrosecondPosition() {
    return 0;
  }

  @Override
  public int getMaxReceivers() {
    return 0;
  }

  @Override
  public int getMaxTransmitters() {
    return 0;
  }

  @Override
  public Receiver getReceiver() throws MidiUnavailableException {
    return null;
  }

  @Override
  public List<Receiver> getReceivers() {
    return null;
  }

  @Override
  public Transmitter getTransmitter() throws MidiUnavailableException {
    return null;
  }

  @Override
  public List<Transmitter> getTransmitters() {
    return null;
  }

  @Override
  public void setMicrosecondPosition(long microseconds) {
    //do nothing
  }

  @Override
  public void setMasterSyncMode(SyncMode sync) {
    //do nothing
  }

  @Override
  public SyncMode getMasterSyncMode() {
    return null;
  }

  @Override
  public SyncMode[] getMasterSyncModes() {
    return new SyncMode[0];
  }

  @Override
  public void setSlaveSyncMode(SyncMode sync) {
    //do nothing
  }

  @Override
  public SyncMode getSlaveSyncMode() {
    return null;
  }

  @Override
  public SyncMode[] getSlaveSyncModes() {
    return new SyncMode[0];
  }

  @Override
  public void setTrackMute(int track, boolean mute) {
    //do nothing
  }

  @Override
  public boolean getTrackMute(int track) {
    return false;
  }

  @Override
  public void setTrackSolo(int track, boolean solo) {
    //do nothing
  }

  @Override
  public boolean getTrackSolo(int track) {
    return false;
  }

  @Override
  public boolean addMetaEventListener(MetaEventListener listener) {
    return false;
  }

  @Override
  public void removeMetaEventListener(MetaEventListener listener) {
    //do nothing
  }

  @Override
  public int[] addControllerEventListener(ControllerEventListener listener, int[] controllers) {
    return new int[0];
  }

  @Override
  public int[] removeControllerEventListener(ControllerEventListener listener, int[] controllers) {
    return new int[0];
  }

  @Override
  public void setLoopStartPoint(long tick) {
    //do nothing
  }

  @Override
  public long getLoopStartPoint() {
    return 0;
  }

  @Override
  public void setLoopEndPoint(long tick) {
    //do nothing
  }

  @Override
  public long getLoopEndPoint() {
    return 0;
  }

  @Override
  public void setLoopCount(int count) {
    //do nothing
  }

  @Override
  public int getLoopCount() {
    return 0;
  }
}
