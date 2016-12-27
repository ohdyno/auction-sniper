package auctionsniper.main;

import auctionsniper.AuctionMessageFactory;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;

/**
 * XMPPAuction
 * Responsibility:
 */
public class XMPPAuction implements Auction {

  private final Chat chat;

  public XMPPAuction(final Chat chat) {
    this.chat = chat;
  }

  public void bid(final int amount) {
    sendMessage(AuctionMessageFactory.createBid(amount));
  }

  public void join() {
    sendMessage(AuctionMessageFactory.createJoin());
  }

  private void sendMessage(final String message) {
    try {
      chat.sendMessage(message);
    } catch (final XMPPException e) {
      e.printStackTrace();
    }
  }
}
