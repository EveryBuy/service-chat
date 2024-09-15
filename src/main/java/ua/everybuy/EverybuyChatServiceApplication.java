package ua.everybuy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EverybuyChatServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EverybuyChatServiceApplication.class, args);
	}

}
