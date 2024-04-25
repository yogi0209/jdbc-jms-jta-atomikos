package com.yogendra;

import com.yogendra.config.account.AccountProperties;
import com.yogendra.config.jms.JmsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.yogendra.config", "com.yogendra.account"})
@EnableConfigurationProperties({AccountProperties.class, JmsProperties.class})
public class JdbcJmsJtaAtomikosApplication {

	public static void main(String[] args) {
		SpringApplication.run(JdbcJmsJtaAtomikosApplication.class, args);
	}

}
