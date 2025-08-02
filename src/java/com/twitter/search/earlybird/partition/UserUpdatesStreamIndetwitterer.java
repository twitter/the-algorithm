package com.twitter.search.earlybird.partition;

import java.util.Date;

import com.google.common.annotations.VisibleForTesting;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.common.indetwittering.thriftjava.AntisocialUserUpdate;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.metrics.SearchRateCounter;
import com.twitter.search.common.metrics.SearchTimer;
import com.twitter.search.common.util.io.kafka.CompactThriftDeserializer;
import com.twitter.search.common.util.io.kafka.FinagleKafkaClientUtils;
import com.twitter.search.earlybird.common.config.EarlybirdProperty;
import com.twitter.search.earlybird.common.userupdates.UserUpdate;
import com.twitter.search.earlybird.etwitterception.MissingKafkaTopicEtwitterception;

public class UserUpdatesStreamIndetwitterer etwittertends SimpleStreamIndetwitterer<Long, AntisocialUserUpdate> {
  private static final Logger LOG = LoggerFactory.getLogger(UserUpdatesStreamIndetwitterer.class);

  private static final SearchCounter NUM_CORRUPT_DATA_ERRORS =
      SearchCounter.etwitterport("num_user_updates_kafka_consumer_corrupt_data_errors");
  protected static String kafkaClientId = "";

  private final SegmentManager segmentManager;
  private final SearchIndetwitteringMetricSet searchIndetwitteringMetricSet;

  public UserUpdatesStreamIndetwitterer(KafkaConsumer<Long, AntisocialUserUpdate> kafkaConsumer,
                                  String topic,
                                  SearchIndetwitteringMetricSet searchIndetwitteringMetricSet,
                                  SegmentManager segmentManager)
      throws MissingKafkaTopicEtwitterception {
    super(kafkaConsumer, topic);
    this.segmentManager = segmentManager;
    this.searchIndetwitteringMetricSet = searchIndetwitteringMetricSet;

    indetwitteringSuccesses = SearchRateCounter.etwitterport("user_update_indetwittering_successes");
    indetwitteringFailures = SearchRateCounter.etwitterport("user_update_indetwittering_failures");
  }

  /**
   * Provides user updates kafka consumer to EarlybirdWireModule.
   * @return
   */
  public static KafkaConsumer<Long, AntisocialUserUpdate> provideKafkaConsumer() {
    return FinagleKafkaClientUtils.newKafkaConsumerForAssigning(
        EarlybirdProperty.KAFKA_PATH.get(),
        new CompactThriftDeserializer<>(AntisocialUserUpdate.class),
        kafkaClientId,
        MAtwitter_POLL_RECORDS);
  }

  UserUpdate convertToUserInfoUpdate(AntisocialUserUpdate update) {
    return new UserUpdate(
        update.getUserID(),
        update.getType(),
        update.isValue() ? 1 : 0,
        new Date(update.getUpdatedAt()));
  }

  @VisibleForTesting
  protected void validateAndIndetwitterRecord(ConsumerRecord<Long, AntisocialUserUpdate> record) {
    AntisocialUserUpdate update = record.value();
    if (update == null) {
      LOG.warn("null value returned from poll");
      return;
    }
    if (update.getType() == null) {
      LOG.error("User update does not have type set: " + update);
      NUM_CORRUPT_DATA_ERRORS.increment();
      return;
    }

    SearchTimer timer = searchIndetwitteringMetricSet.userUpdateIndetwitteringStats.startNewTimer();
    boolean isUpdateIndetwittered = segmentManager.indetwitterUserUpdate(
        convertToUserInfoUpdate(update));
    searchIndetwitteringMetricSet.userUpdateIndetwitteringStats.stopTimerAndIncrement(timer);

    if (isUpdateIndetwittered) {
      indetwitteringSuccesses.increment();
    } else {
      indetwitteringFailures.increment();
    }
  }
}
