package ece448.iot_sim;
import java.beans.Transient;
import java.util.Arrays;

import org.junit.Test;

public class MqttCommandsTests {


@Test
    public void test1()
    {
        // Garbage input
        String x = "ajgdfd/sdfgds/agsd/n/sb";
        MqttCommands mq = new MqttCommands(Arrays.asList(new PlugSim("b.100")), "ajgdfd"); 
        mq.handleMessage(x, null);
        mq.getTopic();

    }

@Test
    public void test2()
    {
        //Input with '/'
        String x = "/action/b.100/toggle" ;
        MqttCommands mq = new MqttCommands(Arrays.asList(new PlugSim("b.100")), "/");
        mq.handleMessage(x, null);
        mq.getTopic();
        
    }

@Test
    public void test3()
    {
        // Plug b.100 with action on
        String x = "iot_ece448/action/b.100/on" ;
        MqttCommands mq = new MqttCommands(Arrays.asList(new PlugSim("b.100")), "iot_ece448");
        mq.handleMessage(x, null);
        String y = mq.getTopic();
        
    }

@Test
    public void test4()
    {
        //plug dddd with action off
        String x = "iot_ece448/action/dddd/off" ;
        MqttCommands mq = new MqttCommands(Arrays.asList(new PlugSim("dddd")), "iot_ece448");
         mq.handleMessage(x, null);
         String y = mq.getTopic();
        
    }

@Test
    public void test5()
    {
        //plug a with wrong topic prefix
        String x = "iot_ece448/action/a/off" ;
        MqttCommands mq = new MqttCommands(Arrays.asList(new PlugSim("a")), "iot_ece448/action");
        mq.handleMessage(x, null);
        String y = mq.getTopic();
    }

@Test
    public void test6()
    {
        // plug dddd with toggle action
        String x = "iot_ece448/action/dddd/toggle" ;
        MqttCommands mq = new MqttCommands(Arrays.asList(new PlugSim("dddd")), "iot_ece448");
        mq.handleMessage(x, null);
        String y = mq.getTopic();
        
    }

@Test
    public void test7()
    {
        // Plug name with wildcard #
        String x = "iot_ece448/action/#/on" ;
        MqttCommands mq = new MqttCommands(Arrays.asList(new PlugSim("a")), "iot_action");
        mq.handleMessage(x, null);
        String y = mq.getTopic();
        
    }
    @Test
    public void test8()
    {
        //plug vakue with wildcard +
        String x = "iot_ece448/action/a/+" ;
        MqttCommands mq = new MqttCommands(Arrays.asList(new PlugSim("cc")), "iot_ece448");
        mq.handleMessage(x, null);
        String y = mq.getTopic();
        
    }

    @Test
    public void test9()
    {
        // Empty value in the plugName field
        String x = "iot_ece448/action//toggle" ;
        MqttCommands mq = new MqttCommands(Arrays.asList(new PlugSim("a")), "iot_ece448");
        mq.handleMessage(x, null);
        
    }

    @Test
    public void test10()
    {
        //Null topic and null topicPrefix 
        String x = null;
        MqttCommands mq = new MqttCommands(Arrays.asList(new PlugSim("cc")), null);
        mq.handleMessage(null, null);
        
    }





}
