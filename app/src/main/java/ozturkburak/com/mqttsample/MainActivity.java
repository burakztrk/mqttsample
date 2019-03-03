package ozturkburak.com.mqttsample;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import ozturkburak.com.mqttsample.model.MessageInfo;

import com.blankj.utilcode.util.LogUtils;





public class MainActivity extends AppCompatActivity implements MqttController.MessageArrivedListener , View.OnClickListener {

    private MqttController controller;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new MqttController(this, this);
        controller.connect();

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button_text:
                controller.sendMessageCmd("Text Test Message");
                break;
            case R.id.button_smiley:
                controller.sendSmileyCmd(MessageInfo.SMILEY_SADFACE);
                break;
            case R.id.button_addWifi:
                controller.sendNewWifiInfoCmd("ssid" ,"pass");
                break;
            case R.id.button_getInfo:
                controller.sendGetInfoCmd();
                break;
            case R.id.button_sendTimeZone:
                controller.sendTimeZoneCmd(3);
                break;
            case R.id.button_restart:
                controller.sendRestartCmd();
                break;
        }

    }



    @Override
    public void newMessageArrived(String topic, String message)
    {
        LogUtils.d(topic , message);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        controller.close();
    }
}
