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
    auction.hasReceivedJoinRequestFromSniper();
    auction.announceClosed();
    application.showsSniperHasLostAuction();
  }

  @AfterEach
  void stopApplication() {
    application.stop();
  }

  @AfterEach
  void stopAuction() {
    auction.stop();
  }

}