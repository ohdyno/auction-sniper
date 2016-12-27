package auctionsniper;

import auctionsniper.main.Auction;
import auctionsniper.main.AuctionEventListener;
import auctionsniper.main.SniperListener;

/**
 * AuctionSniper
 * Responsibility:
 */
public class AuctionSniper implements AuctionEventListener {

  private final SniperListener listener;
  private final Auction auction;

  public AuctionSniper(final SniperListener sniperListener, final Auction auction) {
    this.listener = sniperListener;
    this.auction = auction;
  }

  public void auctionClosed() {
    listener.sniperLost();
  }

  @Override
  public void currentPrice(final int currentBid, final int minimumIncrement) {
    listener.sniperBidding();
    auction.bid(currentBid + minimumIncrement);
  }
}
