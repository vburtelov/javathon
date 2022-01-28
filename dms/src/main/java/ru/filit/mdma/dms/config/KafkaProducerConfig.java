package ru.filit.mdma.dms.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.filit.mdma.dms.auditor.model.AuditMessage;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

    private String bootstrapAddress;
    private String groupId;

    public KafkaProducerConfig(@Value("${system.element.queue.host}") String host, @Value("${system.element.queue.port}") String port) {
        this.bootstrapAddress = host+":"+port;
        this.groupId="dm-aw";
    }

    @Bean
    public ProducerFactory<String, AuditMessage> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                JsonSerializer.ADD_TYPE_INFO_HEADERS,
                false
        );
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        configProps.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                groupId);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, AuditMessage> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
