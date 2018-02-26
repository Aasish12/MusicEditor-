package cs3500.music.view.visual;

import cs3500.music.model.Note;
import cs3500.music.model.Song;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.JPanel;

/**
 * Panel showing the composition of notes.
 */
public class TrackPanel extends JPanel {

  // width of a measure
  int measureWidth = 120;

  // width of a beat
  int beatWidth = measureWidth / 4;

  // height of a beat
  int beatHeight = 16;

  // x position of top left corner of composition
  int xOffset = 40;

  // y position of the top left corner of the composition
  int yOffset = 30;

  // song to be rendered
  Song song;


  /**
   * constructor.
   *
   * @param song song to be displayed on the panel
   */
  public TrackPanel(Song song) {
    this.song = song;
    Dimension size = getPreferredSize();
    size.width = xOffset + measureWidth * (song.getNumBeats() / 4 + 1);
    size.height = song.noteLabels().size() * beatHeight + yOffset;
    setPreferredSize(size);
  }

  /**
   * Renders the string on the panel considering newlines (\n).
   *
   * @param g current graphics
   * @param text String to be rendered
   * @param x x position of the start of the String
   * @param y y position of the start of the String
   */
  public void drawString(Graphics g, String text, int x, int y) {
    for (String line : text.split("\n")) {
      g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
  }

  // renders the view
  @Override
  public void paintComponent(Graphics g) {
    this.setBackground(Color.white);
    // Handle the default painting
    super.paintComponent(g);

    ArrayList<String> labels = song.noteLabels();
    int labelSize = labels.size();

    // create a string of pitches
    String pitchLabels = "";
    ArrayList<String> pitches = labels;
    Collections.reverse(pitches);
    for (String s : pitches) {
      pitchLabels += s;
      pitchLabels += "\n";
    }

    // place the pitches on the canvas
    this.drawString(g, pitchLabels, 10, 25);

    // create border for composition
    g.drawRect(xOffset, yOffset, song.getNumBeats() * measureWidth,
        16 * song.noteLabels().size());

    // number of measures
    int numMeasures = song.getNumBeats() / 4;

    // add measure lines and beat numbers
    for (int i = 0; i < numMeasures + 1; i++) {

      // add measure lines
      g.drawLine(xOffset + measureWidth * i, yOffset, xOffset + measureWidth * i,
          yOffset + 16 * labelSize);

      // add beat numbers
      g.drawString(Integer.toString(i * 4), 36 + i * measureWidth, 20);
    }

    // add horizontal lines for each pitch
    int numBeats = song.getNumBeats();
    for (int i = 0; i < labelSize; i++) {
      // add lines
      g.drawLine(xOffset, yOffset + beatHeight * i,
          xOffset + measureWidth * numBeats / 4, yOffset + beatHeight * i);
    }

    // add notes to the composition
    Set<Entry<String, Note>> allNotes = song.getNotes().entrySet();
    for (Map.Entry<String, Note> entry : allNotes) {

      // durations are green
      if (entry.getKey().contains("-")) {
        g.setColor(Color.GREEN);
      }

      // start of notes are black
      else {
        g.setColor(Color.BLACK);
      }

      // add note to canvas
      int numBeat = Integer.parseInt(entry.getKey().split(" ")[0]);
      int numPitch = pitches.indexOf(entry.getValue().toString());
      g.fillRect(xOffset + numBeat * beatWidth,
          yOffset + numPitch * beatHeight,
          beatWidth, beatHeight);
    }

    // draw current beat line
    int currentBeat = song.getCurrentBeat();
    // change color to red
    g.setColor(Color.red);
    // add the beatLine to the canvas
    g.fillRect(xOffset + beatWidth * currentBeat, yOffset, 3,
        16 * labelSize);
  }
}