package auctionsniper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

/**
 * FakeAuctionServer
 * Responsibility:
 */
public class FakeAuctionServer {

  public static final String ITEM_ID_AS_LOGIN = "auction-%s";
  public static final String AUCTION_RESOURCE = "Auction";
  public static final String XMPP_HOSTNAME = "localhost";

  private static final String AUCTION_PASSWORD = "auction";

  private final String itemId;
  private final XMPPConnection connection;
  private Chat currentChat;
  private final SingleMessageListener messageListener = new SingleMessageListener();

  public FakeAuctionServer(final String itemId) {
    this.itemId = itemId;
    this.connection = new XMPPConnection(XMPP_HOSTNAME);
  }

  public void startSellingItem() throws XMPPException {
    connection.connect();
    connection.login(String.format(ITEM_ID_AS_LOGIN, itemId),
        AUCTION_PASSWORD, AUCTION_RESOURCE);
    connection.getChatManager().addChatListener(
        (Chat chat, boolean createdLocally) -> {
          currentChat = chat;
          chat.addMessageListener(messageListener);
        }
    );
  }

  public void reportPrice(final int currentPrice, final int minimumIncrement, final String winningBidder)
      throws XMPPException {
    final String message = AuctionMessageFactory.createPrice(currentPrice, minimumIncrement, winningBidder);
    currentChat.sendMessage(message);
  }

  public String getItemId() {
    return itemId;
  }

  public void announceClosed() throws XMPPException {
    currentChat.sendMessage(AuctionMessageFactory.createClose());
  }

  public void stop() {
    connection.disconnect();
  }

  //region Expectations for tests
  public void hasReceivedJoinRequestFromSniper(final String sniperId) throws InterruptedException {
    receivesAMessageMatching(sniperId, AuctionMessageFactory.createJoin());
  }

  public void hasReceivedBid(final int bid, final String sniperId) throws InterruptedException {
    receivesAMessageMatching(sniperId, AuctionMessageFactory.createBid(bid));
  }

  private void receivesAMessageMatching(final String sniperId, final String message) throws InterruptedException {
    messageListener.receivesAMessage(equalTo(message));
    assertThat(currentChat.getParticipant(), equalTo(sniperId));
  }
  //endregion
}
