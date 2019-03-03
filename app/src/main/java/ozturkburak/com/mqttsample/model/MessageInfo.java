package ozturkburak.com.mqttsample.model;

import com.blankj.utilcode.util.GsonUtils;

/**
 * Created by burakzturk on 2.03.2019.
 */


/*
    message     {"arguments":["Text Test Message"],"command":"","type":"MESSAGE"}
    smile       {"arguments":[],"command":"sadFace","type":"SMILEYS"}
    addWifi     {"arguments":["ssid","pass"],"command":"addWifi","type":"SYSTEM"}
    getInfo     {"arguments":[],"command":"getInfo","type":"SYSTEM"}
    setTimeZone {"arguments":["3"],"command":"setTimeZone","type":"SYSTEM"}
    restart     {"arguments":[],"command":"restart","type":"SYSTEM"}
 */


public class MessageInfo
{
    MessageType type;
    String command;
    String[] arguments;


    public static String SMILEY_SMILE ="smile";
    public static String SMILEY_SADFACE ="sadFace";
    public static String CMD_SYSTEM_SET_TIMEZONE ="setTimeZone";
    public static String CMD_SYSTEM_ADD_WIFI ="addWifi";
    public static String CMD_SYSTEM_RESTART ="restart";
    public static String CMD_SYSTEM_GET_INFO ="getInfo";



    public MessageInfo(MessageType type, String command, String... arguments) {
        this.type = type;
        this.command = command;
        this.arguments = arguments;
    }

    public String toJson(){
        return GsonUtils.toJson(this);
    }



    public static MessageInfo MessageInstance(String message){
        return new MessageInfo(MessageType.MESSAGE , "",message);
    }

    public static MessageInfo SystemInstance(String command , String... arguments){
        return new MessageInfo(MessageType.SYSTEM , command,arguments);
    }


    public static MessageInfo SmileyInstance(String smiley){
        return new MessageInfo(MessageType.SMILEYS, smiley);
    }


}
