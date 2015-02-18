import com.cinchcast.telephony.commons.connectors.mq.BaseMessage;
import com.google.gson.Gson;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.nio.charset.Charset;

public class TestMessage extends BaseMessage {
    private String message;

    public TestMessage() {
    }

    public TestMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public byte[] toBytes() {
        String json = getGson().toJson(this);
        return json.getBytes(Charset.forName("UTF-8"));
    }

    @Override
    public void fromBytes(byte[] bytes) {
        String json = new String(bytes, Charset.forName("UTF-8"));
        TestMessage instance = getGson().fromJson(json, TestMessage.class);

        this.message = instance.message;
    }
}
