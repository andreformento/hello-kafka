package com.formento.hellokafka.producer;

import com.formento.hellokafka.config.KafkaProperties;
import com.formento.hellokafka.domain.WorkUnit;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

	@Autowired
	private KafkaProperties kafkaProperties;

	@Bean
	public ProducerFactory<String, WorkUnit> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs(), stringKeySerializer(), workUnitJsonSerializer());
	}

	private Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrap());
		return props;
	}

	@Bean
	public KafkaTemplate<String, WorkUnit> workUnitsKafkaTemplate() {
		KafkaTemplate<String, WorkUnit> kafkaTemplate = new KafkaTemplate<>(producerFactory());
		kafkaTemplate.setDefaultTopic(kafkaProperties.getTopic());
		return kafkaTemplate;
	}

	@Bean
	Serializer stringKeySerializer() {
		return new StringSerializer();
	}

	@Bean
	Serializer workUnitJsonSerializer() {
		return new JsonSerializer();
	}

}