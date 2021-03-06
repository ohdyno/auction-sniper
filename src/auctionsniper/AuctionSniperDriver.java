package auctionsniper;

import static org.hamcrest.Matchers.equalTo;

import auctionsniper.ui.MainWindow;
import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

/**
 * AuctionSniperDriver
 * Responsibility:
 */
public class AuctionSniperDriver extends JFrameDriver {

  public AuctionSniperDriver(final int timeoutMillis) {
    super(new GesturePerformer(),
        JFrameDriver.topLevelFrame(named(MainWindow.MAIN_WINDOW_NAME), showingOnScreen()),
        new AWTEventQueueProber(timeoutMillis, 100));
  }

  public void showsSniperStatus(final String statusText) {
    new JLabelDriver(this, named(MainWindow.SNIPER_STATUS_NAME)).hasText(equalTo(statusText));
  }
}
