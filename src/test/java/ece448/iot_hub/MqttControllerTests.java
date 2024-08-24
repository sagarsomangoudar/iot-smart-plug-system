package ece448.iot_hub;

import static org.junit.Assert.*;

import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MqttControllerTests {
    private static final Logger logger = LoggerFactory.getLogger(MqttControllerTests.class);
    private static final String broker = "tcp://127.0.0.1";
    private static final String topicPrefix = "iot_ece448";
    private static String clientId = "iot_hub";
   


    
  @Test 
  public void testinit() throws Exception {
    MqttController mqtt = new MqttController(broker, clientId, topicPrefix);
    mqtt.setState("p", "off");
    mqtt.setPower("p", "0");
    mqtt.setState("q", "on");
    mqtt.setPower("q", "300");
   
  }
    
  @Test 
    public void testsettersequals() throws Exception {
      MqttController mqtt = new MqttController(broker,clientId,topicPrefix);

      
      mqtt.setState("p", "on");
      String state = mqtt.getState("p");
      assertEquals("on", state );

      mqtt.setPower("p", "567");
      assertEquals(mqtt.getPower("p"), "567");

  }
 


@Test
public void testhandleupdate() throws Exception {
  MqttController mqtt = new MqttController(broker,clientId,topicPrefix);

  mqtt.handleUpdate("iot_ece448/update/p/state", new MqttMessage("on".getBytes()));
  assertEquals("on", mqtt.getState("p"));

    mqtt.handleUpdate("iot_ece448/update/p/power", new MqttMessage("330".getBytes()));
    assertEquals("330", mqtt.getPower("p"));



}

@Test
  public void testpowers() throws Exception {

    MqttController mqtt = new MqttController(broker,clientId,topicPrefix);

      mqtt.setPower("p", "454");
      mqtt.setPower("q", "93");

      Map<String, String> powers = mqtt.getPowers();
      
      assertTrue(powers.containsKey("p"));
      assertTrue(powers.containsKey("q"));

      assertEquals("454", powers.get("p"));
      assertEquals("93", powers.get("q"));
  }

@Test
    public void testinvalidinput() throws Exception{
        MqttController mqtt = new MqttController(broker, clientId, topicPrefix);

        mqtt.handleUpdate("grade_p4/iot_ece448/aoudfios/p/power", new MqttMessage("off".getBytes()));
        assertNull(null);

        mqtt.handleUpdate("grade_p4/iot_ece448/oauaodsp/p", new MqttMessage("off".getBytes()));
        assertNull(null);

        mqtt.handleUpdate("grade_p4/iot_ece448/update/p/", new MqttMessage("off".getBytes()));
        assertNull(null);
    }

@Test
    public void teststates() throws Exception
    {
        MqttController mqtt = new MqttController(broker, clientId, topicPrefix);

        mqtt.setState("p", "on");
      mqtt.setState("q", "off");

      Map<String, String> states = mqtt.getStates();

      assertEquals("on", states.get("p"));
      assertEquals("off", states.get("q"));

    }


@Test 
    public void teststartclose() throws Exception {
      MqttController mqtt = new MqttController(broker,clientId,topicPrefix);
      mqtt.start();
      mqtt.close();
  }

}