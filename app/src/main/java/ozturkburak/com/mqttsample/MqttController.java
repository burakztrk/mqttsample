package ozturkburak.com.mqttsample;

import android.content.Context;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import androidx.annotation.NonNull;
import ozturkburak.com.mqttsample.model.MessageInfo;

/**
 * Created by burakzturk on 2.03.2019.
 */
public class MqttController implements MqttCallbackExtended{

    private MqttAndroidClient mqttAndroidClient;
    private MessageArrivedListener messageArrivedListener;

    public interface MessageArrivedListener{ void newMessageArrived(String topic , String message); }

    public MqttController(Context context ,@NonNull MessageArrivedListener listener)
    {
        messageArrivedListener = listener;
        mqttAndroidClient = new MqttAndroidClient(context, Constants.SERVER_URI, Constants.CLIENT_ID);
        mqttAndroidClient.setCallback(this);

    }

    public void connect()
    {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setUserName(Constants.MQTT_USERNAME);
        mqttConnectOptions.setPassword(Constants.MQTT_PASSWORD);
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);

        try {
            LogUtils.a("Connecting to " + Constants.SERVER_URI);
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    LogUtils.a("Failed to connect to: " + Constants.SERVER_URI);
                    exception.printStackTrace();
                }
            });


        } catch (MqttException ex){
            ex.printStackTrace();
        }

    }



    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        if (reconnect) {
            LogUtils.a("Reconnected to : " + serverURI);
            subscribeToTopic();
        } else
            LogUtils.a("Connected to : " + serverURI);
    }

    @Override
    public void connectionLost(Throwable cause) {
        LogUtils.a("The Connection was lost. ");

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String messageStr = new String(message.getPayload());
        messageArrivedListener.newMessageArrived(topic,messageStr);
    }

    @Override public void deliveryComplete(IMqttDeliveryToken token) { }




    private void subscribeToTopic(){


        try {
            mqttAndroidClient.subscribe(Constants.TOPIC, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    LogUtils.a("Subscribed");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    LogUtils.a("Failed to subscribe");
                    exception.printStackTrace();
                }
            });

        } catch (MqttException ex){
            LogUtils.e("Exception whilst subscribing");
            ex.printStackTrace();
        }


    }


    private void publishMessage(String message)
    {

        try {

            mqttAndroidClient.publish(Constants.TOPIC, message.getBytes(),0,false);
            LogUtils.a("Message Published:" , message);
            if(!mqttAndroidClient.isConnected()){
                LogUtils.a(mqttAndroidClient.getBufferedMessageCount() + " messages in buffer.");
            }
        } catch (Throwable e) {
            LogUtils.a("Error Publishing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            mqttAndroidClient.unsubscribe(Constants.TOPIC);
            mqttAndroidClient.close();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }





    public void sendTimeZoneCmd(int timeZone){
        MessageInfo message = MessageInfo.SystemInstance(MessageInfo.CMD_SYSTEM_SET_TIMEZONE , String.valueOf(timeZone));
        publishMessage(message.toJson());
    }



    public void sendNewWifiInfoCmd(String ssid , String pass)
    {
        if (StringUtils.isTrimEmpty(ssid) || StringUtils.isTrimEmpty(pass))
            return;

        MessageInfo message = MessageInfo.SystemInstance(MessageInfo.CMD_SYSTEM_ADD_WIFI , ssid , pass);
        publishMessage(message.toJson());
    }

    public void sendRestartCmd(){
        MessageInfo message = MessageInfo.SystemInstance(MessageInfo.CMD_SYSTEM_RESTART );
        publishMessage(message.toJson());
    }


    public void sendGetInfoCmd(){
        MessageInfo message = MessageInfo.SystemInstance(MessageInfo.CMD_SYSTEM_GET_INFO );
        publishMessage(message.toJson());
    }


    public void sendMessageCmd(String text){

        if (StringUtils.isTrimEmpty(text))
            return;

        MessageInfo message = MessageInfo.MessageInstance(text);
        publishMessage(message.toJson());
    }

    public void sendSmileyCmd(String text){

        if (StringUtils.isTrimEmpty(text))
            return;

        MessageInfo message = MessageInfo.SmileyInstance(text);
        publishMessage(message.toJson());
    }







}
