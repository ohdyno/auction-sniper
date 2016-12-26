package auctionsniper.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import auctionsniper.AuctionMessageFactory;
import auctionsniper.main.AuctionEventListener;
import auctionsniper.main.AuctionMessageTranslator;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.junit.jupiter.api.Test;

/**
 * AuctionMessageTranslatorTest
 * Responsibility:
 */
public class AuctionMessageTranslatorTest {

  private final Chat notUsed = null;
  private final AuctionEventListener listener = mock(AuctionEventListener.class);
  private final AuctionMessageTranslator translator = new AuctionMessageTranslator(listener);

  @Test
  public void notifiesAuctionClosedWhenCloseMessageReceived() {
    final Message message = new Message();
    message.setBody(AuctionMessageFactory.createClose());
    translator.processMessage(notUsed, message);
    verify(listener).auctionClosed();
  }

  @Test
  void notifiesAuctionBidWhenBidMessageReceived() throws XMPPException {
    final Message message = new Message();
    message.setBody(AuctionMessageFactory.createPrice(1000, 98, "other bidder"));

    translator.processMessage(notUsed, message);
    verify(listener).currentPrice(1000, 98);
  }
}
