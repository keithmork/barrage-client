package me.keithmo.barrage.client;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author keithmo
 */
@SpringBootApplication
@EnableDubbo
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
