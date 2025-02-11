package com.example.producer.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class StringProducerService {


    private final KafkaTemplate<String, String> kafkaTemplate;

    public StringProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String text) {
        kafkaTemplate.send("str-topic", text).whenComplete(
                (success, error) -> {
                    if (error == null) {
                        System.out.println("Mensagem enviada com sucesso para o tópico: " +
                                success.getProducerRecord().topic() +
                                ", Partição: " + success.getProducerRecord().partition() +
                                ", Offset: " + success.getRecordMetadata().offset());
                    } else {
                        System.err.println("Erro ao enviar a mensagem: " + error.getMessage() + ", " + error);
                    }
                }
        );
    }
}