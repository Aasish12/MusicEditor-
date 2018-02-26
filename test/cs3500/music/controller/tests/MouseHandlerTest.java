package cs3500.music.controller.tests;

import static junit.framework.TestCase.assertEquals;
import cs3500.music.controller.code.MouseHandler;
import cs3500.music.model.Song;
import cs3500.music.view.IView;
import cs3500.music.view.visual.GuiViewFrame;
import java.awt.Component;
import java.awt.event.MouseEvent;
import org.junit.Test;

/**
 * Tests methods of the class MouseHandler.
 */
public class MouseHandlerTest {

  private MouseHandler mh;

  private void setMouseHandler() {
    Song song = new Song();
    IView view = new GuiViewFrame(song);
    this.mh = new MouseHandler(view);
  }

  private MouseEvent makeMouseEvent(int event) {
    return new MouseEvent(new Component() {},0,1,2,85,345,1,
        true,1);
  }

  @Test
  public void testMouse() {
    this.setMouseHandler();
    int clicksBefore = this.mh.getLog();
    this.mh.mouseClicked(this.makeMouseEvent(MouseEvent.MOUSE_CLICKED));
    int clicksAfter = this.mh.getLog();
    assertEquals(clicksBefore + 1, clicksAfter);
  }
}
