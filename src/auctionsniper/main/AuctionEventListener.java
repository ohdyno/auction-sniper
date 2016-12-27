package auctionsniper.main;

/**
 * AuctionEventListener
 * Responsibility:
 */
public interface AuctionEventListener {

  void auctionClosed();

  void currentPrice(int currentBid, int minimumIncrement);
}
