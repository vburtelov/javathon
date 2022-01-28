package ru.filit.mdma.aw.config;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.filit.mdma.aw.auditor.model.AuditMessage;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    private String bootstrapAddress;
    private String groupId;

    public KafkaConsumerConfig(@Value("${system.element.queue.host}") String host, @Value("${system.element.queue.port}") String port) {
        this.bootstrapAddress = host+":"+port;
        this.groupId="dm-aw";
    }



    @Bean
    public ConsumerFactory<String, AuditMessage> consumerFactory() {
        JsonDeserializer<AuditMessage> jsonDeserializer =new JsonDeserializer<>(AuditMessage.class);
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                groupId);
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                jsonDeserializer);
        props.put(
                JsonDeserializer.VALUE_DEFAULT_TYPE,
                AuditMessage.class
        );
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AuditMessage> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AuditMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
