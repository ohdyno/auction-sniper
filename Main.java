import auctionsniper.ui.MainWindow;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

/**
 * Main
 * Responsibility:
 */
public class Main {

  private static final int ARG_HOSTNAME = 0;
  private static final int ARG_USERNAME = 1;
  private static final int ARG_PASSWORD = 2;
  private static final int ARG_ITEM_ID = 3;

  public static final String AUCTION_RESOURCE = "Auction";
  public static final String ITEM_ID_AS_LOGIN = "auction-%s";
  public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE;

  private static MainWindow ui;

  @SuppressWarnings("unused")
  private Chat notToBeGCed;

  public Main() throws InvocationTargetException, InterruptedException {
    startUserInterface();
  }

  private void startUserInterface() throws InvocationTargetException, InterruptedException {
    SwingUtilities.invokeAndWait(() -> ui = new MainWindow());
  }

  public static void main(final String... args) throws Exception {
    final Main main = new Main();
    main.joinAuction(connectTo(args[ARG_HOSTNAME], args[ARG_USERNAME], args[ARG_PASSWORD]), args[ARG_ITEM_ID]);
  }

  private void joinAuction(final XMPPConnection connection, final String itemId) throws XMPPException {
    final Chat chat = connection.getChatManager().createChat(
        auctionId(itemId, connection),
        (Chat aChat, Message message) -> SwingUtilities.invokeLater(() -> ui.showStatus(MainWindow.STATUS_LOST))
    );

    this.notToBeGCed = chat;
    chat.sendMessage(new Message());
  }

  private static String auctionId(final String itemId, final XMPPConnection connection) {
    return String.format(AUCTION_ID_FORMAT, itemId, connection.getServiceName());
  }

  private static XMPPConnection connectTo(final String hostname, final String username, final String password)
      throws XMPPException {
    final XMPPConnection connection = new XMPPConnection(hostname);
    connection.connect();
    connection.login(username, password, AUCTION_RESOURCE);
    return connection;
  }
}
