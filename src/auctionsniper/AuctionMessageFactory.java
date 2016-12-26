package auctionsniper;

/**
 * AuctionMessageFactory
 * Responsibility:
 */
public class AuctionMessageFactory {

  private static final String MESSAGE_TEMPLATE = "SOLVersion: 1.1; %s";

  public static String createClose() {
    return String.format(MESSAGE_TEMPLATE, "Event: CLOSE;");
  }

  public static String createPrice(final int currentPrice, final int minimumIncrement,
      final String winningBidder) {
    return String.format(MESSAGE_TEMPLATE,
        String.format("Event: PRICE; CurrentPrice: %d; Increment: %d; Bidder: %s;",
            currentPrice, minimumIncrement, winningBidder));
  }

  public static String createJoin() {
    return String.format(MESSAGE_TEMPLATE, String.format("Command: JOIN;"));
  }

  public static String createBid(final int bid) {
    return String.format(MESSAGE_TEMPLATE, String.format("Command: BID; Price: %d;", bid));
  }
}
