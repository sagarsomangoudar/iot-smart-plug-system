package ece448.iot_hub;

import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class App {
	@Autowired
	public App(Environment env) throws Exception {
	}

	@Bean(destroyMethod = "close")	
	public MqttController mqtt(Environment env) throws Exception{
		String broker = env.getProperty("mqtt.broker");		
		String clientId = env.getProperty("mqtt.clientId");		
		String topicPrefix = env.getProperty("mqtt.topicPrefix");		
		MqttController mqtt = new MqttController(broker, clientId, topicPrefix);		
		mqtt.start();		
		logger.info("MqttClient {} connected: {}", clientId, broker);		
		return mqtt;	
	}	
private static final Logger logger = LoggerFactory.getLogger(App.class);
}