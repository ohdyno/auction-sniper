
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

/**
 * SingleMessageListener
 * Responsibility:
 */
public class SingleMessageListener implements MessageListener {

  private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<>(1);

  @Override
  public void processMessage(final Chat chat, final Message message) {
    messages.add(message);
  }

  public void receivesAMessage() throws InterruptedException {
    assertThat("Message", messages.poll(5, TimeUnit.SECONDS), is(notNullValue()));
  }
}
