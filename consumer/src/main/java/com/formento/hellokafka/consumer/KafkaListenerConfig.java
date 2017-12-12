package com.formento.hellokafka.consumer;

import com.formento.hellokafka.config.KafkaProperties;
import com.formento.hellokafka.domain.WorkUnit;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaListenerConfig {

	@Autowired
	private KafkaProperties kafkaProperties;

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, WorkUnit> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, WorkUnit> factory =
				new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConcurrency(1);
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	private ConsumerFactory<String, WorkUnit> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerProps(), stringKeyDeserializer(), workUnitJsonValueDeserializer());
	}

	@Bean
	public Map<String, Object> consumerProps() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrap());
		props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getGroup());
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
		return props;
	}

	@Bean
	Deserializer stringKeyDeserializer() {
		return new StringDeserializer();
	}

	@Bean
	Deserializer workUnitJsonValueDeserializer() {
		return new JsonDeserializer(WorkUnit.class);
	}

}
