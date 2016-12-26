package auctionsniper.main;

import java.util.HashMap;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

/**
 * AuctionMessageTranslator
 * Responsibility:
 */
public class AuctionMessageTranslator implements MessageListener {

  private final AuctionEventListener listener;

  public AuctionMessageTranslator(final AuctionEventListener listener) {
    this.listener = listener;
  }

  public void processMessage(final Chat chat, final Message message) {
    final HashMap<String, String> event = unpackEventFrom(message);
    final String type = event.get("Event");
    if ("CLOSE".equals(type)) {
      listener.auctionClosed();
    } else if ("PRICE".equals(type)) {
      try {
        listener.currentPrice(Integer.parseInt(event.get("CurrentPrice")), Integer.parseInt(event.get("Increment")));
      } catch (final XMPPException e) {
        e.printStackTrace();
      }
    }
  }

  private HashMap<String, String> unpackEventFrom(final Message message) {
    final HashMap<String, String> event = new HashMap<>();
    final String body = message.getBody();
    for (final String element : body.split(";")) {
      final String[] pair = element.split(":");
      event.put(pair[0].trim(), pair[1].trim());
    }

    return event;
  }
}
