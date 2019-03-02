package ozturkburak.com.mqttsample.model;

/**
 * Created by burakzturk on 2.03.2019.
 */
public class MessageInfo
{
    MessageType type;
    String message;

    public MessageInfo(MessageType type, String message) {
        this.type = type;
        this.message = message;
    }
}
