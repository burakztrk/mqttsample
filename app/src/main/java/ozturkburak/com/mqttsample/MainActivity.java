package ozturkburak.com.mqttsample;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import ozturkburak.com.mqttsample.model.MessageInfo;
import ozturkburak.com.mqttsample.model.MessageType;

import com.blankj.utilcode.util.LogUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;





public class MainActivity extends AppCompatActivity implements MqttController.MessageArrivedListener , View.OnClickListener {

    private MqttController controller;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new MqttController(this, this);
        controller.connect();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                MessageInfo message = new MessageInfo(MessageType.TEXT, "Text Test Message");
                controller.publishMessage(message);
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
