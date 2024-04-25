package com.yogendra.config.account;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("account.datasource")
public record AccountProperties(String url, String username, String password) {
}
