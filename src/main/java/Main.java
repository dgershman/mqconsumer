import com.cinchcast.telephony.commons.connectors.mq.MqConnectionListener;
import com.cinchcast.telephony.commons.connectors.mq.MqWorkerCallback;
import com.cinchcast.telephony.commons.connectors.mq.RabbitConfiguration;
import com.cinchcast.telephony.commons.connectors.mq.RabbitMqConsumer;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("mqconsumer starting: " + DateTime.now().toString());
        RabbitConfiguration config = new RabbitConfiguration("cc-msgq-dev.sip.blogtalkradio.com", 5673, "guest", "guest", "mqc");
        config.setConnectionListener(new MqConnectionListener() {
            @Override
            public void onConnect(Map<String, Object> stringObjectMap) {
                System.out.println("connected: " + stringObjectMap.get("information"));
            }
        });
        try {
            new RabbitMqConsumer(config, "com.cinchcast.telephony.mq.test", new MqWorkerCallback() {
                @Override
                public void callback(String s) {
                    System.out.println("message receive: " + s);
                }
            }).listen();

            while (true) {
                Thread.sleep(10000);
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            System.out.println("mqconsumer ending: " + DateTime.now().toString());
        }
    }
}
