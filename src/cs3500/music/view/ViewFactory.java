package cs3500.music.view;

import cs3500.music.view.composite.CompositeView;
import cs3500.music.model.Song;
import cs3500.music.view.console.TextView;
import cs3500.music.view.midi.MidiViewImpl;
import cs3500.music.view.visual.GuiViewFrame;

/**
 * Builder class of the given type of view.
 */
public class ViewFactory {

  // song to be showed/played
  Song song;

  // constructor
  ViewFactory(Song song) {
    this.song = song;
  }

  /**
   * Creates a view based on the given type.
   *
   * @param type type of view wanted to make
   * @return view of the given type
   */
  public IView createView(String type) {
    IView view = null;
    switch (type) {
      case "console":
        view = new TextView(song);
        break;
      case "visual":
        view = new GuiViewFrame(song);
        break;
      case "midi":
        view = new MidiViewImpl(song);
        break;
      case "composite":
        view = new CompositeView(song);
        break;
      default:
    }
    return view;
  }
}
