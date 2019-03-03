package ozturkburak.com.mqttsample;

/**
 * Created by burakzturk on 2.03.2019.
 */
public class Constants {
    public static final String SERVER_URI = "tcp://m24.cloudmqtt.com:17128";    // degisecek

    public static final String CLIENT_ID = "androidClient"+ System.currentTimeMillis();
    public static final String TOPIC = "test";  // degisecek

    public static final String MQTT_USERNAME = "wlzvwrnr";  // degisecek
    public static final char[] MQTT_PASSWORD = "0TL-XjBHXl8p".toCharArray();    // degisecek
}
