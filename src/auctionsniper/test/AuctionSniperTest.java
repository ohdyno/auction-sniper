package auctionsniper.test;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import auctionsniper.AuctionSniper;
import auctionsniper.main.Auction;
import auctionsniper.main.SniperListener;
import org.junit.jupiter.api.Test;

/**
 * AuctionSniperTest
 * Responsibility:
 */
public class AuctionSniperTest {

  private final SniperListener sniperListener = mock(SniperListener.class);
  private final Auction auction = mock(Auction.class);
  private final AuctionSniper sniper = new AuctionSniper(sniperListener, auction);

  @Test
  void reportsLostWhenAuctionCloses() {
    sniper.auctionClosed();
    verify(sniperListener).sniperLost();
  }

  @Test
  void bidsHigherAndReportsBiddingWhenNewPriceArrives() {
    final int price = 10001;
    final int increment = 25;
    sniper.currentPrice(price, increment);
    verify(sniperListener, atLeastOnce()).sniperBidding();
    verify(auction).bid(price + increment);
  }
}
