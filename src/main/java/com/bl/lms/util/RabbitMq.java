package com.bl.lms.util;

import com.bl.lms.dto.EmailDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class RabbitMq {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMessageToQueue(EmailDTO mailDto) {
        final String exchange = "QueueExchangeConn";
        final String routingKey = "RoutingKey";
        rabbitTemplate.convertAndSend(exchange, routingKey, mailDto);

    }

    @RabbitListener(queues = "${spring.rabbitmq.template.default-receive-queue}")
    public void sendMail(EmailDTO email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getTo());
        message.setFrom(email.getFrom());
        message.setSubject(email.getSubject());
        message.setText(email.getBody());
        javaMailSender.send(message);
    }

    @RabbitListener(queues = "${spring.rabbitmq.template.default-receive-queue}")
    public void messageReceiver(EmailDTO email) {
        sendMail(email);
    }
}
