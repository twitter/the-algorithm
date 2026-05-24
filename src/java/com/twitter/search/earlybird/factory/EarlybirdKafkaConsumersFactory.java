package com.twitter.search.earlybird.factory;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.twitter.search.common.indexing.thriftjava.ThriftVersionedEvents;

public interface EarlybirdKafkaConsumersFactory {
  /**
   * Create a kafka consumer with default records to be polled.
   */
  KafkaConsumer<Long, ThriftVersionedEvents> createKafkaConsumer(
      String clientID);

  /**
   * Create a kafka consumer with a set number of records to be polled.
   */
  KafkaConsumer<Long, ThriftVersionedEvents> createKafkaConsumer(
      String clientID, int maxPollRecords);
}
