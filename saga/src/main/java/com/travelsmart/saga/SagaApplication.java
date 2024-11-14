package com.travelsmart.saga;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SagaApplication {
    public static void main(String[] args) {
        SpringApplication.run(SagaApplication.class, args);
    }
}
//com.yas.saga.SagaConfiguration
//        io.eventuate.tram.sagas.spring.participant.autoconfigure.SpringParticipantAutoConfiguration
//        io.eventuate.tram.sagas.spring.orchestration.autoconfigure.SpringOrchestratorSimpleDslAutoConfiguration
//        io.eventuate.tram.spring.messaging.autoconfigure.EventuateTramActiveMQMessageConsumerAutoConfiguration
//        io.eventuate.tram.spring.messaging.autoconfigure.EventuateTramKafkaMessageConsumerAutoConfiguration
//        io.eventuate.tram.spring.messaging.autoconfigure.EventuateTramRabbitMQMessageConsumerAutoConfiguration
//        io.eventuate.tram.spring.messaging.autoconfigure.EventuateTramRedisMessageConsumerAutoConfiguration
//        io.eventuate.tram.spring.messaging.autoconfigure.TramMessageProducerJdbcAutoConfiguration
//        io.eventuate.tram.spring.messaging.common.TramMessagingCommonAutoConfiguration
//        io.eventuate.tram.spring.commands.common.TramCommandsCommonAutoConfiguration
//        io.eventuate.tram.spring.consumer.jdbc.TramConsumerJdbcAutoConfiguration