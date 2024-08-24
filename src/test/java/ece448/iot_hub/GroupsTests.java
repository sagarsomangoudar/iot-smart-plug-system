package ece448.iot_hub;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class GroupsTests {

    private static final String broker = "tcp://127.0.0.1";
    private static final String topicPrefix = "iot_ece448";
    private static String clientId = "iot_hub";

@Test
public void test1_groupnull() throws Exception {

    GroupsModel gm =new GroupsModel(null);
    GroupsResource gr = new GroupsResource(gm);

    HashMap<String, Object> obj = (HashMap<String, Object>) gr.getGroup("A", null);
    System.out.println(obj);
    assertEquals(obj.get("A"), null );

}

@Test
public void test2_postgroup() throws Exception {

    GroupsModel gm =new GroupsModel(null);
    GroupsResource gr = new GroupsResource(gm);
    List<String> mems = new ArrayList<>();
    mems.add("x");
    mems.add("y");
    mems.add("z");
    gr.createGroup("B", mems);
    
}
@Test
public void test3_getemptygroup() throws Exception {

    GroupsModel gm =new GroupsModel(null);
    GroupsResource gr = new GroupsResource(gm);

    Collection<Map<String, Object>> col = gr.getGroups();
    assertEquals(0, col.size());
    
}

@Test
public void test4_groupmembers(){

    GroupsModel gm =new GroupsModel(null);
    List<Map<String, Object>> mems = gm.getGroupMembers("o");
    assertEquals(0, mems.size());
}

@Test
public void test5_groupwithmemberlist() throws Exception {

    MqttController mqtt = new MqttController(broker, clientId, topicPrefix);
    GroupsModel gm =new GroupsModel(mqtt);
    GroupsResource gr = new GroupsResource(gm);
    List<String> myList = new ArrayList<>();
    myList.add("x");
    myList.add("y");
    gm.setGroupMembers("GP2", myList);
    assertEquals(2, myList.size());
    
    gr.getGroups();

}


@Test
public void test6_makegroup() throws Exception {

    GroupsModel gm =new GroupsModel(null);
    GroupsResource gr = new GroupsResource(gm);

    Map<String, Object> gp = gr.makeGroup("GP1");
    Collection<Map<String, Object>> groups = gr.getGroups();

    assertNotEquals(gp, groups);

}

@Test
public void test7_groupaction_on() throws Exception {

    MqttController mqtt = new MqttController(broker, clientId, topicPrefix);
    GroupsModel gm =new GroupsModel(mqtt);
    GroupsResource gr = new GroupsResource(gm);

    List<String> myList = new ArrayList<>();
    myList.add("p");
    myList.add("q");
    gm.setGroupMembers("G1", myList);
    gr.getGroup("G1", "on");
    List<Map<String, Object>> ret =gm.getGroupMembers("G1");
   
}

@Test
public void test8_groupaction_toggle() throws Exception {

    MqttController mqtt = new MqttController(broker, clientId, topicPrefix);
    GroupsModel gm =new GroupsModel(mqtt);
    GroupsResource gr = new GroupsResource(gm);

    List<String> myList = new ArrayList<>();
    myList.add("p");
    myList.add("q");
    gm.setGroupMembers("G1", myList);
    gr.getGroup("G1", "toggle");


}

@Test
public void test9_removegroup() {

    GroupsModel gm =new GroupsModel(null);
    GroupsResource gr = new GroupsResource(gm);

    gr.removeGroup("B");
    List<Map<String, Object>> mems = gm.getGroupMembers("B");
    assertEquals(0, mems.size());

}

@Test
public void test10_grpnamewithdecimal() throws Exception {

    MqttController mqtt = new MqttController(broker, clientId, topicPrefix);
    GroupsModel gm =new GroupsModel(mqtt);
    GroupsResource gr = new GroupsResource(gm);
    List<String> myList = new ArrayList<>();
    myList.add("w");
    gm.setGroupMembers("osf.86d", myList);

    HashMap<String, Object> obj = (HashMap<String, Object>) gr.getGroup("osf.86d", "off");
  
    assertEquals(2, obj.size() );

}

}
