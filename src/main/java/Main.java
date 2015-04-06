
import com.cinchcast.telephony.commons.connectors.mq.MqConnection;
import com.cinchcast.telephony.commons.connectors.mq.ReceiveOptions;
import com.cinchcast.telephony.commons.connectors.mq.ReceiveResponse;
import com.cinchcast.telephony.commons.connectors.mq.rabbit.RabbitConfiguration;
import com.cinchcast.telephony.commons.connectors.mq.rabbit.RabbitConnection;
import com.cinchcast.telephony.commons.connectors.mq.rabbit.RabbitReceiveOptions;
import org.joda.time.DateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("mqconsumer starting: " + DateTime.now().toString());

        RabbitConfiguration config = new RabbitConfiguration("cc-msgq-dev.sip.blogtalkradio.com", 5673, "guest", "guest", "mqc");
        ReceiveOptions receiveOptions = new RabbitReceiveOptions("com.cinchcast.telephony.mq.test", true);
        MqConnection mqConnection = null;

        try {
            mqConnection = new RabbitConnection(config);

            while (true) {
                ReceiveResponse<TestMessage> response = mqConnection.receive(receiveOptions, TestMessage.class);

                if (response.getMessage() != null) {
                    System.out.println(response.getMessage().getMessage());
                } else {
                    System.out.println("No more messages, exiting..");
                    break;
                }

                Thread.sleep(1000);
            }

        /*} catch (InterruptedException iEx) {
            System.out.println("InterruptedException: " + iEx.toString());
        */} catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mqConnection.close();
            System.out.println("mqconsumer ending: " + DateTime.now().toString());
        }
    }
}
