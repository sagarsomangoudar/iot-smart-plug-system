package ece448.iot_hub;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;

@RestController
public class PlugsResource {
   
    private final MqttController mqtt;
    public PlugsResource(MqttController mqtt) throws Exception{
        this.mqtt = mqtt;
        
    }

    protected Object makePlug(String plug) throws Exception{
        HashMap<String, Object> ret = new HashMap<>();
        ret.put("name", plug);
        ret.put("state", mqtt.getState(plug));
        ret.put("power", mqtt.getPower(plug));
        
        return ret;
    }

    @GetMapping("/api/plugs")
    public Object getPlugList() throws Exception    {
		List<HashMap<String, Object>> ret = new ArrayList<>();
        for(String plug: mqtt.getStates().keySet()){
            HashMap<String, Object> hashMap = (HashMap<String, Object>) makePlug(plug);
            ret.add(hashMap);

        }
		return ret;
	}

    @GetMapping("/api/plugs/{plug:.+}")
    public Object getplug(@PathVariable("plug") String plug, 
    @RequestParam(value = "action", required= false) String action) throws Exception{
        if(action == null){
            Object ret = makePlug(plug);
            logger.info("plugss {}: {}", plug,ret);
            return ret;
        }
        
		mqtt.publishAction(plug, action);
        Object ret = makePlug(plug);
        logger.info("plug {}: {}", plug, ret);
        return ret;
        
    }
   
    private static final Logger logger = LoggerFactory.getLogger(PlugsResource.class);

}