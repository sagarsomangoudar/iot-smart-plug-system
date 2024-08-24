package ece448.iot_hub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;


@Component
public class GroupsModel {


    private HashMap<String, HashSet<String>> groups = new HashMap<>();

	MqttController mqtt;

	public GroupsModel(MqttController mqtt) {
		this.mqtt = mqtt;
	}
	
	

	synchronized public List<String> getGroups() {
		return new ArrayList<>(groups.keySet());
	}

	synchronized public void publishGroup(String group, String action) 
	{
		HashSet<String> members = groups.get(group);

		for (String val : members) {

			logger.info(" Plug {} Publishing... ", val);
			mqtt.publishAction(val, action);
			logger.info(" Plug {} Published", val);

		}
	
	}

	synchronized public List<Map<String, Object>> getGroupMembers(String group) {

		HashSet<String> members = groups.get(group);

		List<Map<String, Object>> ret = new ArrayList<>();
    	if (members == null) 
			return ret;

    	for (String val : members) {
        	Map<String, Object> PlugMap = new HashMap<>();
        	String stateValue = mqtt.getState(val);
			String powerValue = mqtt.getPower(val);

			PlugMap.put("name", val);
			PlugMap.put("state", stateValue);
			PlugMap.put("power", powerValue);
        	ret.add(PlugMap);
    	}
		logger.info("PlugMap: {}", ret);
		return ret;
	
	}

	
	synchronized public void setGroupMembers(String group, List<String> members) {
		groups.put(group, new HashSet<>(members));
	}

	synchronized public void removeGroup(String group) {
		groups.remove(group);
    }

	 private static final Logger logger = LoggerFactory.getLogger(GroupsResource.class);
}
