//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cs3500.music.view;

import cs3500.music.controller.code.Controller;
import cs3500.music.model.Song;
import cs3500.music.model.Song.TrackBuilder;
import java.io.FileReader;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;

/**
 * Class used to run the different midi views.
 */
public class MusicEditor {

  /**
   * Main method that runs the program.
   *
   * @param args arguments to pass the main
   * @throws IOException exception thrown during main for creating views
   * @throws InvalidMidiDataException error thrown by parsing a file
   */
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    try {
      String type = args[1];
      TrackBuilder trackbuild = new TrackBuilder();
      Song track = MusicReader.parseFile(new FileReader(args[0]), trackbuild);
      ViewFactory vFac = new ViewFactory(track);
      IView view = vFac.createView(type);

      Controller controller = new Controller();
      controller.setView(view);
      view.initialize();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}