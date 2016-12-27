package auctionsniper.main;

import java.util.HashMap;
import java.util.Map;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
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
    final AuctionEvent event = AuctionEvent.from(message.getBody());

    final String type = event.type();

    if ("CLOSE".equals(type)) {
      listener.auctionClosed();
    } else if ("PRICE".equals(type)) {
      listener.currentPrice(event.currentPrice(), event.increment());
    }
  }

  public static class AuctionEvent {

    public String type() {
      return get("Event");
    }

    public static AuctionEvent from(final String messageBody) {
      final AuctionEvent event = new AuctionEvent();

      for (final String element : fieldsIn(messageBody)) {
        event.addField(element);
      }

      return event;
    }

    private static String[] fieldsIn(final String messageBody) {
      return messageBody.split(";");
    }

    private final Map<String, String> fields = new HashMap<>();

    private void addField(final String fieldName) {
      final String[] pair = fieldName.split(":");
      fields.put(pair[0].trim(), pair[1].trim());
    }

    private int currentPrice() {
      return getInt(get("CurrentPrice"));
    }

    private int increment() {
      return getInt(get("Increment"));
    }

    private int getInt(final String increment) {
      return Integer.parseInt(increment);
    }

    private String get(final String fieldName) {
      return fields.get(fieldName);
    }
  }
}
