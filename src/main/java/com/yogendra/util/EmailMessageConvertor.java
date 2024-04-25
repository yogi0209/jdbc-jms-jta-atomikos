package com.yogendra.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

public class EmailMessageConvertor implements MessageConverter {

    private final ObjectMapper mapper;

    public EmailMessageConvertor() {
        mapper = new ObjectMapper();
    }

    @Override
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        String objectAsString = null;
        try {
            objectAsString = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        TextMessage textMessage = session.createTextMessage();
        textMessage.setText(objectAsString);
        return textMessage;
    }

    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        TextMessage textMessage = (TextMessage) message;
        Email email = null;

        try {
            email = mapper.readValue(textMessage.getText(), Email.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return  email;
    }
}
