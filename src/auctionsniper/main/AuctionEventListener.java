package auctionsniper.main;

import org.jivesoftware.smack.XMPPException;

/**
 * AuctionEventListener
 * Responsibility:
 */
public interface AuctionEventListener {

  void auctionClosed();

  void currentPrice(int currentBid, int minimumIncrement) throws XMPPException;
}
