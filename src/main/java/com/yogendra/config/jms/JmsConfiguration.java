package com.yogendra.config.jms;

import com.atomikos.jms.AtomikosConnectionFactoryBean;
import com.yogendra.util.EmailMessageConvertor;
import jakarta.jms.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;

@Configuration
public class JmsConfiguration {

    private final JmsProperties jmsProperties;

    public JmsConfiguration(JmsProperties jmsProperties){
        this.jmsProperties = jmsProperties;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQXAConnectionFactory activeMQXAConnectionFactory = new ActiveMQXAConnectionFactory();
        activeMQXAConnectionFactory.setBrokerURL(jmsProperties.url());
        activeMQXAConnectionFactory.setUserName(jmsProperties.username());
        activeMQXAConnectionFactory.setPassword(jmsProperties.password());
        AtomikosConnectionFactoryBean atomikosConnectionFactoryBean = new AtomikosConnectionFactoryBean();
        atomikosConnectionFactoryBean.setUniqueResourceName("xa-mq");
        atomikosConnectionFactoryBean.setLocalTransactionMode(false);
        atomikosConnectionFactoryBean.setXaConnectionFactory(activeMQXAConnectionFactory);
        return atomikosConnectionFactoryBean;
    }

    @Bean
    public MessageConverter messageConverter(){
        return new EmailMessageConvertor();
    }

    @Bean
    JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory());
        jmsTemplate.setMessageConverter(messageConverter());
        return jmsTemplate;
    }


}
