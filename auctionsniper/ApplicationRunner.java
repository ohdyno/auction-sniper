package auctionsniper;

import auctionsniper.main.Main;
import auctionsniper.ui.MainWindow;

/**
 * ApplicationRunner
 * Responsibility:
 */
public class ApplicationRunner {

  private static final String SNIPER_ID = "sniper";
  private static final String SNIPER_PASSWORD = "sniper";
  private AuctionSniperDriver driver;

  public void startBiddingIn(final FakeAuctionServer auction) {
    final Thread thread = new Thread("Test Application") {
      @Override
      public void run() {
        try {
          Main.main(FakeAuctionServer.XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD,
              auction.getItemId());
        } catch (final Exception e) {
          e.printStackTrace();
        }
      }
    };
    thread.setDaemon(true);
    thread.start();
    driver = new AuctionSniperDriver(1000);
    driver.showsSniperStatus(MainWindow.STATUS_JOINING);
  }

  public void showsSniperHasLostAuction() {
    driver.showsSniperStatus(MainWindow.STATUS_LOST);
  }

  public void stop() {
    if (driver != null) {
      driver.dispose();
    }
  }
}