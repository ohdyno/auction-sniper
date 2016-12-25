package auctionsniper.ui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

/**
 * MainWindow
 * Responsibility: The main GUI window for the Auction Sniper application.
 */
public class MainWindow extends JFrame {

  public static final String MAIN_WINDOW_NAME = "Auction Sniper auctionsniper.main.Main";
  public static final String SNIPER_STATUS_NAME = "sniper status";
  public static final String STATUS_JOINING = "Joining";
  public static final String STATUS_LOST = "Lost";

  private final JLabel sniperStatus = createLabel(STATUS_JOINING);

  public MainWindow() {
    super("Auction Sniper");
    setName(MAIN_WINDOW_NAME);
    add(sniperStatus);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setSize(new Dimension(300, 100));
    setVisible(true);
  }

  private static JLabel createLabel(final String initialText) {
    final JLabel result = new JLabel(initialText);
    result.setName(SNIPER_STATUS_NAME);
    result.setBorder(new LineBorder(Color.BLACK));
    return result;
  }

  public void showStatus(final String status) {
    sniperStatus.setText(status);
  }
}
