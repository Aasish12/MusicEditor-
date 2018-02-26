package cs3500.music.model;

import cs3500.music.model.Note.Pitch;

// 6/13 added class

/**
 * Class representing a single key on the keyboard.
 */
public class Key {

  public Pitch pitch;
  public int octave;
  public int xpos;
  public int ypos;
  public boolean isCurrent;

  /**
   * constructor.
   * @param pitch pitch of the key on the keyboard
   * @param octave octave the key is on
   * @param xpos x position of the key on the panel
   * @param ypos y position of the key on the panel
   * @param isCurrent is the key currently being played?
   */
  public Key(Pitch pitch, int octave, int xpos, int ypos, boolean isCurrent) {
    this.pitch = pitch;
    this.octave = octave;
    this.xpos = xpos;
    this.ypos = ypos;
    this.isCurrent = isCurrent;
  }
}