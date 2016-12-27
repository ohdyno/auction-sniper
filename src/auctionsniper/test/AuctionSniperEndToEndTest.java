package auctionsniper.test;

import auctionsniper.ApplicationRunner;
import auctionsniper.FakeAuctionServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * AuctionSniperEndToEndTest
 * Responsibility:
 */
public class AuctionSniperEndToEndTest {

  private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
  private final ApplicationRunner application = new ApplicationRunner();

  @Test
  public void sniperJoinsAuctionUntilAuctionCloses() throws Exception {
    auction.startSellingItem();
    application.startBiddingIn(auction);
    auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
    auction.announceClosed();
    application.showsSniperHasLostAuction();
  }

  @Test
  void sniperMakesAHigherBidButLoses() throws Exception {
    auction.startSellingItem();

    application.startBiddingIn(auction);
    auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);

    auction.reportPrice(1000, 98, "other bidder");
    application.hasShowSniperIsBidding();

    auction.hasReceivedBid(1000 + 98, ApplicationRunner.SNIPER_XMPP_ID);

    auction.announceClosed();
    application.showsSniperHasLostAuction();
  }

  @AfterEach
  void stopAuction() {
    auction.stop();
  }

  @AfterEach
  void stopApplication() {
    application.stop();
  }

}