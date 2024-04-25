package com.yogendra.config.jms;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jms-broker")
public record JmsProperties(String url, String username, String password) {
}
