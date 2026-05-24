package com.twitter.search.feature_update_service.modules;

import javax.inject.Named;
import javax.inject.Singleton;

import com.google.inject.Provides;

import com.twitter.app.Flaggable;
import com.twitter.common.util.Clock;
import com.twitter.finatra.kafka.producers.BlockingFinagleKafkaProducer;
import com.twitter.inject.TwitterModule;
import com.twitter.inject.annotations.Flag;
import com.twitter.search.common.indexing.thriftjava.ThriftVersionedEvents;
import com.twitter.search.common.util.io.kafka.CompactThriftSerializer;
import com.twitter.search.common.util.io.kafka.FinagleKafkaClientUtils;
import com.twitter.search.common.util.io.kafka.SearchPartitioner;
import com.twitter.search.common.util.io.kafka.SearchPartitionerRealtimeCg;

public class FinagleKafkaProducerModule extends TwitterModule {
  public static final String KAFKA_DEST_FLAG = "kafka.dest";
  public static final String KAFKA_TOPIC_NAME_UPDATE_EVENTS_FLAG =
      "kafka.topic.name.update_events";
  public static final String KAFKA_TOPIC_NAME_UPDATE_EVENTS_FLAG_REALTIME_CG =
          "kafka.topic.name.update_events_realtime_cg";
  public static final String KAFKA_ENABLE_S2S_AUTH_FLAG = "kafka.enable_s2s_auth";

  public FinagleKafkaProducerModule() {
    flag(KAFKA_DEST_FLAG, "Kafka cluster destination", "", Flaggable.ofString());
    flag(KAFKA_TOPIC_NAME_UPDATE_EVENTS_FLAG, "",
        "Topic name for update events", Flaggable.ofString());
    flag(KAFKA_TOPIC_NAME_UPDATE_EVENTS_FLAG_REALTIME_CG, "",
            "Topic name for update events", Flaggable.ofString());
    flag(KAFKA_ENABLE_S2S_AUTH_FLAG, true, "enable s2s authentication configs",
        Flaggable.ofBoolean());
  }

  @Provides
  @Named("KafkaProducer")
  public BlockingFinagleKafkaProducer<Long, ThriftVersionedEvents> kafkaProducer(
      @Flag(KAFKA_DEST_FLAG) String kafkaDest,
      @Flag(KAFKA_ENABLE_S2S_AUTH_FLAG) boolean enableKafkaAuth) {
    return FinagleKafkaClientUtils.newFinagleKafkaProducer(
        kafkaDest, enableKafkaAuth, new CompactThriftSerializer<ThriftVersionedEvents>(),
        "search_cluster", SearchPartitioner.class);
  }

  @Provides
  @Named("KafkaProducerRealtimeCg")
  public BlockingFinagleKafkaProducer<Long, ThriftVersionedEvents> kafkaProducerRealtimeCg(
          @Flag(KAFKA_DEST_FLAG) String kafkaDest,
          @Flag(KAFKA_ENABLE_S2S_AUTH_FLAG) boolean enableKafkaAuth) {
    return FinagleKafkaClientUtils.newFinagleKafkaProducer(
            kafkaDest, enableKafkaAuth, new CompactThriftSerializer<ThriftVersionedEvents>(),
            "search_cluster", SearchPartitionerRealtimeCg.class);
  }

  @Provides
  @Singleton
  public Clock clock() {
    return Clock.SYSTEM_CLOCK;
  }
}
