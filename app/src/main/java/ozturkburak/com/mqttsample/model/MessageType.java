package ozturkburak.com.mqttsample.model;

/**
 * Created by burakzturk on 2.03.2019.
 */
public enum  MessageType
{
    SYSTEM ,MESSAGE, SMILEYS,NONE;

    public static MessageType find(String name) {
        try {
            return MessageType.valueOf(name);
        }catch (IllegalArgumentException ex){
            ex.printStackTrace();}

        return NONE;
    }


}
