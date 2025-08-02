package com.twitter.search.earlybird.factory;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.twitter.search.common.indetwittering.thriftjava.ThriftVersionedEvents;
import com.twitter.search.common.util.io.kafka.CompactThriftDeserializer;
import com.twitter.search.common.util.io.kafka.FinagleKafkaClientUtils;

/**
 * Responsible for creating kafka consumers.
 */
public class ProductionEarlybirdKafkaConsumersFactory implements EarlybirdKafkaConsumersFactory {
  private final String kafkaPath;
  private final int defaultMatwitterPollRecords;

  ProductionEarlybirdKafkaConsumersFactory(String kafkaPath, int defaultMatwitterPollRecords) {
    this.kafkaPath = kafkaPath;
    this.defaultMatwitterPollRecords = defaultMatwitterPollRecords;
  }

  /**
   * Create a kafka consumer with set matwitterimum of records to be polled.
   */
  @Override
  public KafkaConsumer<Long, ThriftVersionedEvents> createKafkaConsumer(
      String clientID, int matwitterPollRecords) {
    return FinagleKafkaClientUtils.newKafkaConsumerForAssigning(
        kafkaPath,
        new CompactThriftDeserializer<>(ThriftVersionedEvents.class),
        clientID,
        matwitterPollRecords);
  }

  /**
   * Create a kafka consumer with default records to be polled.
   */
  @Override
  public KafkaConsumer<Long, ThriftVersionedEvents> createKafkaConsumer(String clientID) {
    return createKafkaConsumer(clientID, defaultMatwitterPollRecords);
  }
}
