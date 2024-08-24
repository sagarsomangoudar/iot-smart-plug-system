package ece448.iot_sim;

import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttCommands {
    private final TreeMap<String, PlugSim>plugs = new TreeMap<>();
    private final String topicPrefix;
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public MqttCommands(List<PlugSim> plugs, String topicPrefix)
    {
        for (PlugSim plug: plugs)
            this.plugs.put(plug.getName(), plug);
        this.topicPrefix = topicPrefix;

    }
    
    public String getTopic()
    {
        return topicPrefix+"/action/#";
    }
    public void handleMessage(String topic, MqttMessage msg)
    {
        try
        {
            logger.info("MqttCmd {}", topic);
            String[] f = topic.substring(topicPrefix.length()+1).split("/");
            PlugSim plug = plugs.get(f[1]);
            
            MeasurePower measurePower = new MeasurePower(new ArrayList<>(plugs.values()));

           
            if ((f.length != 3) || !f[0].equals("action"))
            return;

            if (f[2].equals("on")) {
                plug.switchOn();
                measurePower.start();
                } else if (f[2].equals("off")) {
                plug.switchOff();
                measurePower.start();
                } else if (f[2].equals("toggle")) {
                plug.toggle();
                measurePower.start();
                }
                 
            

        }
        catch(Throwable th)
        {
            logger.error("MqttCmd{}: {}", th.getMessage(), th);

        }
        
    }
}
