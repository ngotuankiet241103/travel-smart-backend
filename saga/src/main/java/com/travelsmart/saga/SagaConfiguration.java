//package com.yas.saga;
//
//import io.eventuate.messaging.kafka.basic.consumer.DefaultKafkaConsumerFactory;
//import io.eventuate.messaging.kafka.basic.consumer.KafkaConsumerFactory;
//import io.eventuate.tram.spring.optimisticlocking.OptimisticLockingDecoratorConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//
//@Configuration
//@Import(OptimisticLockingDecoratorConfiguration.class)
//public class SagaConfiguration {
//    @Bean
//    public KafkaConsumerFactory kafkaEventuateConsumerFactory() {
//        return new DefaultKafkaConsumerFactory();
//    }
//}
////com.yas.saga.SagaConfiguration
////        io.eventuate.tram.sagas.spring.participant.autoconfigure.SpringParticipantAutoConfiguration
////        io.eventuate.tram.sagas.spring.orchestration.autoconfigure.SpringOrchestratorSimpleDslAutoConfiguration
////        io.eventuate.tram.spring.messaging.autoconfigure.EventuateTramActiveMQMessageConsumerAutoConfiguration
////        io.eventuate.tram.spring.messaging.autoconfigure.EventuateTramKafkaMessageConsumerAutoConfiguration
////        io.eventuate.tram.spring.messaging.autoconfigure.EventuateTramRabbitMQMessageConsumerAutoConfiguration
////        io.eventuate.tram.spring.messaging.autoconfigure.EventuateTramRedisMessageConsumerAutoConfiguration
////        io.eventuate.tram.spring.messaging.autoconfigure.TramMessageProducerJdbcAutoConfiguration
////        io.eventuate.tram.spring.messaging.common.TramMessagingCommonAutoConfiguration
////        io.eventuate.tram.spring.commands.common.TramCommandsCommonAutoConfiguration
////        io.eventuate.tram.spring.consumer.jdbc.TramConsumerJdbcAutoConfiguration