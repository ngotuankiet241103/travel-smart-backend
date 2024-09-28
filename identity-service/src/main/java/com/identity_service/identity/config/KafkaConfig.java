//package com.identity_service.identity.config;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.StreamsBuilder;
//import org.apache.kafka.streams.kstream.*;
//import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
//import org.apache.kafka.streams.state.Stores;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.TopicBuilder;
//import org.springframework.kafka.support.serializer.JsonSerde;
//
//import java.time.Duration;
//
//@Configuration
//public class KafkaConfig {
//    @Bean
//    public NewTopic orders() {
//
//        return TopicBuilder.name("users")
//                .partitions(3)
//                .build();
//    }
//
//    @Bean
//    public NewTopic paymentTopic() {
//        return TopicBuilder.name("profile-users")
//                .partitions(3)
//                .build();
//    }
//
//    @Bean
//    public NewTopic stockTopic() {
//        return TopicBuilder.name("email-users")
//                .partitions(3)
//                .build();
//    }
////    @Bean
////    public KStream<Long, User> stream(StreamsBuilder builder) {
////        JsonSerde<Order> orderSerde = new JsonSerde<>(Order.class);
////        KStream<Long, Order> stream = builder
////                .stream("payment-orders", Consumed.with(Serdes.Long(), orderSerde));
////
////        stream.join(
////                        builder.stream("stock-orders"),
////                        orderManageService::confirm,
////                        JoinWindows.of(Duration.ofSeconds(10)),
////                        StreamJoined.with(Serdes.Long(), orderSerde, orderSerde))
////                .peek((k, o) -> LOG.info("Output: {}", o))
////                .to("orders");
////
////
////        return stream;
////    }
////
////    @Bean
////    public KTable<Long, Order> table(StreamsBuilder builder) {
////        KeyValueBytesStoreSupplier store =
////                Stores.persistentKeyValueStore("orders");
////        JsonSerde<Order> orderSerde = new JsonSerde<>(Order.class);
////        KStream<Long, Order> stream = builder
////                .stream("orders", Consumed.with(Serdes.Long(), orderSerde));
////        return stream.toTable(Materialized.<Long, Order>as(store)
////                .withKeySerde(Serdes.Long())
////                .withValueSerde(orderSerde));
////    }
//}