package com.formento.hellokafka.producer;

import com.formento.hellokafka.config.KafkaProperties;
import com.formento.hellokafka.domain.WorkUnit;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class WorkUnitDispatcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(WorkUnitDispatcher.class);
	@Autowired
	private KafkaTemplate<String, WorkUnit> workUnitsKafkaTemplate;
	@Autowired
	private KafkaProperties kafkaProperties;

	public boolean dispatch(WorkUnit workUnit) {
		try {
			SendResult<String, WorkUnit> sendResult = workUnitsKafkaTemplate.sendDefault(workUnit.getId(), workUnit).get();
			RecordMetadata recordMetadata = sendResult.getRecordMetadata();
			LOGGER.info("topic = {}, partition = {}, offset = {}, workUnit = {}",
					recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset(), workUnit);
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
