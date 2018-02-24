package com.payconiq.stocksdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration;

@SpringBootApplication(exclude = { ActiveMQAutoConfiguration.class, JmxAutoConfiguration.class, WebSocketAutoConfiguration.class })
public class StocksDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(StocksDemoApplication.class, args);
	}
}
