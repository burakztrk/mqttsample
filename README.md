
## Android Mqtt Sample for Iot devices

File -> New -> Project from Version Control -> GitHub -> https://github.com/burakztrk/mqttsample.git


```
        MqttController controller = new MqttController(this, new MqttController.MessageArrivedListener() {
            @Override
            public void newMessageArrived(String topic, String message) {
                LogUtils.d(topic , message);
            }
        });
        controller.connect();
```

```
       MessageInfo message = new MessageInfo(MessageType.TEXT, "Text Test Message");
       controller.publishMessage(message);
```
