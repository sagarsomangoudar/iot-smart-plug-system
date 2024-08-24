package ece448.iot_sim;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.Test;

public class HTTPSCommandsTests {


    @Test
    public void test01() {
        HTTPCommands httpCommands = new HTTPCommands(Arrays.asList(new PlugSim("a")));
        HashMap<String, String> params = new HashMap<>();
            params.put("action", "on");
            String output = httpCommands.handleGet("/a", params);
            // Plug a is on

    }
    
    @Test
    public void test02() {
        HTTPCommands httpCommands = new HTTPCommands(Arrays.asList(new PlugSim("a")));
        HashMap<String, String> params = new HashMap<>();
            params.put("action", "off");
            String output = httpCommands.handleGet("/a", params);
            // Plug a is off

    }

    @Test
    public void test03() {
        HTTPCommands httpCommands = new HTTPCommands(Arrays.asList(new PlugSim("a")));
        HashMap<String, String> params = new HashMap<>();
            params.put("action", null);
            String output = httpCommands.handleGet("/a", params);
            // Plug a with no value

    }

    @Test
    public void test04() {
        HTTPCommands httpCommands = new HTTPCommands(Arrays.asList(new PlugSim("a")));
        HashMap<String, String> params = new HashMap<>();
            params.put("action", null);
            String output = httpCommands.handleGet("/", params);
            // empty plug name
    }

    @Test
    public void test05() {
        HTTPCommands httpCommands = new HTTPCommands(Arrays.asList(new PlugSim("a")));
        HashMap<String, String> params = new HashMap<>();
            params.put("action", "toggle");
            String output = httpCommands.handleGet("/a", params);
            // plug a with toggle

    }

    @Test
    public void test06() {
        HTTPCommands httpCommands = new HTTPCommands(Arrays.asList(new PlugSim("b.100")));
        HashMap<String, String> params = new HashMap<>();
            params.put("action", "toggle");
            String output = httpCommands.handleGet("/b.100", params);
            // plug b.100 with toggle
    }
    
    @Test
    public void test07() {
        HTTPCommands httpCommands = new HTTPCommands(Arrays.asList(new PlugSim("b.100")));
        HashMap<String, String> params = new HashMap<>();
            params.put("action", "hhhhh");
            String output = httpCommands.handleGet("/b.100", params);
            // Invalid plug action in b.100

    }

    @Test
    public void test08() {
        HTTPCommands httpCommands = new HTTPCommands(Arrays.asList(new PlugSim("b.100")));
        HashMap<String, String> params = new HashMap<>();
            params.put("action", null);
            String output = httpCommands.handleGet("hafgddga", params);
            // Invalid path

    }

    @Test
    public void test09() {
        HTTPCommands httpCommands = new HTTPCommands(Arrays.asList(new PlugSim("b.100")));
        HashMap<String, String> params = new HashMap<>();
            params.put("action", "asdg");
            String output = httpCommands.handleGet("/b.100", params);
            // Valid plug with invalid action

    }
    
    @Test
    public void test10() {
        HTTPCommands httpCommands = new HTTPCommands(Arrays.asList(new PlugSim("b.100")));
        HashMap<String, String> params = new HashMap<>();
            params.put("action", "");
            String output = httpCommands.handleGet("/b.100", params);
            // empty action in b.100

    }

}
    

    
