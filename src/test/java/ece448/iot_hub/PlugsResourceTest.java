package ece448.iot_hub;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlugsResourceTest {

    private static final String broker = "tcp://127.0.0.1";
    private static final String topicPrefix = "grade_p4/iot_ece448";
    private static String clientId = "iot_hub";
    
    
  
    @Test
    public void testGetAllThePlugsEmpty() throws Exception {
    MqttController mqtt = new MqttController(broker, clientId, topicPrefix);

    PlugsResource pr = new PlugsResource(mqtt);

    Object plugs = pr.getPlugList();

    assertNotNull(plugs);
    assertTrue(((List<HashMap<String, Object>>) plugs).isEmpty());
}

    @Test
    public void testplugwithnull() throws Exception{
        MqttController mqtt = new MqttController(broker, clientId, topicPrefix);
        mqtt.setState("a", "on");
        mqtt.setPower("a", "837");
        PlugsResource pr = new PlugsResource(mqtt);

        Object result = pr.getplug("a",null);
        HashMap<String, Object> ret = (HashMap<String, Object>) result;

        assertEquals(ret.get("state"), "on");
    }

    @Test
    public void testPublish() throws Exception {
        MqttController mqtt = new MqttController(broker, clientId, topicPrefix);

        PlugsResource pr = new PlugsResource(mqtt);

        // Publishing with action on
        mqtt.publishAction("p", "on");

    }
    @Test
    public void testPlugSwitchOn() throws Exception {
        MqttController mqtt = new MqttController(broker, clientId, topicPrefix);

        PlugsResource pr = new PlugsResource(mqtt);

        mqtt.setState("plug1", "off");
        Object plug = pr.getplug("plug1", "on");

        assertNotNull(plug);
    
    }

    @Test
    public void testPlugSwitchOff() throws Exception {
        MqttController mqtt = new MqttController(broker, clientId, topicPrefix);

        PlugsResource pr = new PlugsResource(mqtt);

        mqtt.setState("p", "on");
        Object plug = pr.getplug("p", "off");

        assertNotNull(plug);
    
    }
 
    @Test
    public void testGetPlugwithtoggle() throws Exception {

        MqttController mqtt = new MqttController(broker, clientId, topicPrefix);
        PlugsResource pr = new PlugsResource(mqtt);

        mqtt.setState("a", "off");

        Object plug = pr.getplug("a", "toggle");

        assertNotNull(plug);
    }

    @Test
    public void testgetAllPlugs() throws Exception {

        MqttController mqtt = new MqttController(broker, clientId, topicPrefix);
        PlugsResource pr = new PlugsResource(mqtt);

        Object ret = pr.getPlugList();

        assertNotNull(ret);
    }

private static final Logger logger = LoggerFactory.getLogger(PlugsResource.class);
   

}