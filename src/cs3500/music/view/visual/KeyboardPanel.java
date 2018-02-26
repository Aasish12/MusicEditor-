package cs3500.music.view.visual;

import cs3500.music.model.Key;
import cs3500.music.model.Note;
import cs3500.music.model.Note.Pitch;
import cs3500.music.model.Song;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Panel showing the keyboard.
 */
public class KeyboardPanel extends JPanel {

  // number of octaves
  private static final int NUM_OCTAVES = 10;
  // width of the panel
  private static final int PANEL_WIDTH = 1200;
  // height of the panel
  private static final int PANEL_HEIGHT = 300;
  // width of a single white key
  private static final int WHITE_KEY_WIDTH = 15;
  // width of a single black key
  private static final int BLACK_KEY_WIDTH = 7;
  // length of a black key
  // 6/19 added for convenience
  private static final int BLACK_KEY_LENGTH = PANEL_WIDTH / 16;
  // x position of the leftmost white key
  private static final int WHITE_KEY_START = PANEL_WIDTH / 16;
  // y position of the top of the keyboard
  // 6/19 added for convenience
  private static final int KEYBOARD_TOP = 50;
  // 6/19 added for convenience
  // length of each key
  private static final int KEY_LENGTH = 4 * PANEL_HEIGHT / 5;

  Song song;
  ArrayList<Key> keys;

  /**
   * constructor.
   *
   * @param song song to be linked with the keyboard
   */
  public KeyboardPanel(Song song) {
    Dimension size = getPreferredSize();
    size.width = 1200;
    size.height = 300;
    setPreferredSize(size);
    this.song = song;
    this.keys = new ArrayList<Key>();
  }

  // renders the panel
  @Override
  public void paintComponent(Graphics g) {

    this.setBackground(Color.PINK);
    // Handle the default painting
    super.paintComponent(g);

    int xposCurrent = WHITE_KEY_START;

    // add keys
    for (int o = 0; o < NUM_OCTAVES; o++) {
      for (int p = 0; p < 12; p++) {
        String keyTone = Pitch.values()[p] + Integer.toString(o + 1);

        // see if current key is on the current beat
        boolean isCurrentBeat = false;
        ArrayList<Note> currentNotes = song.notesInBeat(song.getCurrentBeat());
        for (Note n : currentNotes) {
          if (n.toString().equals(keyTone)) {
            isCurrentBeat = true;
          }
        }

        if (keyTone.contains("#")) {
          this.keys.add(
              new Key(Pitch.values()[p], o, xposCurrent - WHITE_KEY_WIDTH / 5,
                  KEYBOARD_TOP, isCurrentBeat));
        } else {
          // add key
          this.keys.add(new Key(Pitch.values()[p], o, xposCurrent, KEYBOARD_TOP, isCurrentBeat));
          // add to position counter
          xposCurrent += WHITE_KEY_WIDTH;
        }
      }
    }

    // draw keys
    for (Key k : this.keys) {
      // draw black keys
      if (!k.pitch.toString().contains("#")) {

        // set current keys to orange
        if (k.isCurrent) {
          g.setColor(Color.orange);
        }
        // not current keys to white
        else {
          g.setColor(Color.white);
        }
        g.fillRect(k.xpos, k.ypos, WHITE_KEY_WIDTH, 4 * PANEL_HEIGHT / 5);

        // key border
        g.setColor(Color.BLACK);
        g.drawRect(k.xpos, k.ypos, WHITE_KEY_WIDTH, 4 * PANEL_HEIGHT / 5);
      }
    }

    // draw keys
    for (Key k : this.keys) {
      // draw black keys
      if (k.pitch.toString().contains("#")) {
        if (k.isCurrent) {
          g.setColor(Color.orange);
        } else {
          g.setColor(Color.black);
        }
        g.fillRect(k.xpos, k.ypos, BLACK_KEY_WIDTH, PANEL_WIDTH / 16);
      }
    }
  }

  /**
   * Based on x,y of mouse click on keyboard, add a note to the composition.
   *
   * @param x x position of mouse click
   * @param y y position of mouse click
   */
  public Note noteToAdd(int x, int y) {
    // click to the left of keyboard
    boolean tooFarLeft = x < 83;
    // click to the right of keyboard
    boolean tooFarRight = x > 1133;
    // click above keyboard
    boolean tooHighUp = y < 342;
    // click below keyboard
    boolean tooFarDown = y > 580;
    System.out.println(y);
    // check to see if the click was on the keyboard
    if (tooFarLeft || tooFarRight || tooHighUp || tooFarDown) {
      throw new IllegalArgumentException("No key at the given location");
    }

    // arraylist of only the black keys
    ArrayList<Key> blackKeys = new ArrayList<Key>();
    ArrayList<Key> whiteKeys = new ArrayList<Key>();

    // add keys to appropriate list
    for (Key k : this.keys) {
      if (k.pitch.toString().contains("#")) {
        blackKeys.add(k);
      } else {
        whiteKeys.add(k);
      }
    }

    Key key = new Key(Pitch.A_NAT, 0, 1, 1, false);

    // check if location is white key
    for (Key k : whiteKeys) {
      int xDist = x - (k.xpos + 8);
      int yDist = y - (k.ypos + PANEL_HEIGHT - 6);
      boolean validXDist = xDist >= 0 && xDist < WHITE_KEY_WIDTH;
      boolean validYDist = yDist >= 0 && yDist < KEY_LENGTH;
      if (validXDist && validYDist) {
        key = k;
        break;
      }
    }

    // check if location is black key
    for (Key k : blackKeys) {
      int xDist = x - (k.xpos + 8);
      int yDist = y - (k.ypos + PANEL_HEIGHT - 6);
      boolean validXDist = xDist >= 0 && xDist < BLACK_KEY_WIDTH;
      boolean validYDist = yDist >= 0 && yDist < BLACK_KEY_LENGTH;
      if (validXDist && validYDist) {
        key = k;
        break;
      }
    }
    // replace last three args
    return new Note(key.pitch, key.octave, 1, 1, 1);
  }
}