package com.twitter.search.ingester.pipeline.twitter.kafka;

import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducedTypes;
import org.apache.kafka.common.serialization.Deserializer;

import com.twitter.finatra.kafka.serde.internal.BaseDeserializer;
import com.twitter.search.ingester.model.KafkaRawRecord;
import com.twitter.util.Time;

/**
 * Kafka consumer stage that emits the binary payload wrapped in {@code ByteArray}.
 */
@ConsumedTypes(String.class)
@ProducedTypes(KafkaRawRecord.class)
public class KafkaRawRecordConsumerStage extends KafkaConsumerStage<KafkaRawRecord> {
  public KafkaRawRecordConsumerStage() {
    super(getDeserializer());
  }

  private static Deserializer<KafkaRawRecord> getDeserializer() {
    return new BaseDeserializer<KafkaRawRecord>() {
      @Override
      public KafkaRawRecord deserialize(String topic, byte[] data) {
        return new KafkaRawRecord(data, Time.now().inMillis());
      }
    };
  }

  public KafkaRawRecordConsumerStage(String kafkaClientId, String kafkaTopicName,
                                     String kafkaConsumerGroupId, String kafkaClusterPath,
                                     String deciderKey) {
    super(kafkaClientId, kafkaTopicName, kafkaConsumerGroupId, kafkaClusterPath, deciderKey,
        getDeserializer());
  }
}
