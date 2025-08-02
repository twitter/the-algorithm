package com.twitter.search.ingester.pipeline.twitter.kafka;

import java.util.Collection;
import java.util.Map;

import javatwitter.naming.NamingEtwitterception;

import scala.runtime.BotwitteredUnit;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import org.apache.commons.pipeline.StageEtwitterception;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.finatra.kafka.producers.BlockingFinagleKafkaProducer;
import com.twitter.search.common.debug.DebugEventUtil;
import com.twitter.search.common.debug.thriftjava.DebugEvents;
import com.twitter.search.common.decider.DeciderUtil;
import com.twitter.search.common.indetwittering.thriftjava.ThriftVersionedEvents;
import com.twitter.search.common.metrics.Percentile;
import com.twitter.search.common.metrics.PercentileUtil;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.schema.thriftjava.ThriftIndetwitteringEvent;
import com.twitter.search.common.schema.thriftjava.ThriftIndetwitteringEventType;
import com.twitter.search.common.util.io.kafka.CompactThriftSerializer;
import com.twitter.search.ingester.model.IngesterThriftVersionedEvents;
import com.twitter.search.ingester.pipeline.twitter.TwitterBaseStage;
import com.twitter.search.ingester.pipeline.util.PipelineStageEtwitterception;
import com.twitter.search.ingester.pipeline.wire.IngesterPartitioner;
import com.twitter.util.Await;
import com.twitter.util.Future;

public class KafkaProducerStage<T> etwittertends TwitterBaseStage<T, Void> {
  private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerStage.class);

  private static final Logger LATE_EVENTS_LOG = LoggerFactory.getLogger(
      KafkaProducerStage.class.getName() + ".LateEvents");

  private final Map<ThriftIndetwitteringEventType, Percentile<Long>> processingLatenciesStats =
      Maps.newEnumMap(ThriftIndetwitteringEventType.class);

  private String kafkaClientId;
  private String kafkaTopicName;
  private String kafkaClusterPath;
  private SearchCounter sendCount;
  private String perPartitionSendCountFormat;
  private String deciderKey;

  protected BlockingFinagleKafkaProducer<Long, ThriftVersionedEvents> kafkaProducer;

  private int processingLatencyThresholdMillis = 10000;

  public KafkaProducerStage() { }

  public KafkaProducerStage(String topicName, String clientId, String clusterPath) {
    this.kafkaTopicName = topicName;
    this.kafkaClientId = clientId;
    this.kafkaClusterPath = clusterPath;
  }

  @Override
  protected void initStats() {
    super.initStats();
    setupCommonStats();
  }

  private void setupCommonStats() {
    sendCount = SearchCounter.etwitterport(getStageNamePrefitwitter() + "_send_count");
    perPartitionSendCountFormat = getStageNamePrefitwitter() + "_partition_%d_send_count";
    for (ThriftIndetwitteringEventType eventType : ThriftIndetwitteringEventType.values()) {
      processingLatenciesStats.put(
          eventType,
          PercentileUtil.createPercentile(
              getStageNamePrefitwitter() + "_" + eventType.name().toLowerCase()
                  + "_processing_latency_ms"));
    }
  }

  @Override
  protected void innerSetupStats() {
   setupCommonStats();
  }

  private boolean isEnabled() {
    if (this.deciderKey != null) {
      return DeciderUtil.isAvailableForRandomRecipient(decider, deciderKey);
    } else {
      // No decider means it's enabled.
      return true;
    }
  }

  @Override
  protected void doInnerPreprocess() throws StageEtwitterception, NamingEtwitterception {
    try {
      innerSetup();
    } catch (PipelineStageEtwitterception e) {
      throw new StageEtwitterception(this, e);
    }
  }

  @Override
  protected void innerSetup() throws PipelineStageEtwitterception, NamingEtwitterception {
    Preconditions.checkNotNull(kafkaClientId);
    Preconditions.checkNotNull(kafkaClusterPath);
    Preconditions.checkNotNull(kafkaTopicName);

    kafkaProducer = wireModule.newFinagleKafkaProducer(
        kafkaClusterPath,
        new CompactThriftSerializer<ThriftVersionedEvents>(),
        kafkaClientId,
        IngesterPartitioner.class);

    int numPartitions = wireModule.getPartitionMappingManager().getNumPartitions();
    int numKafkaPartitions = kafkaProducer.partitionsFor(kafkaTopicName).size();
    if (numPartitions != numKafkaPartitions) {
      throw new PipelineStageEtwitterception(String.format(
          "Number of partitions for Kafka topic %s (%d) != number of etwitterpected partitions (%d)",
          kafkaTopicName, numKafkaPartitions, numPartitions));
    }
  }


  @Override
  public void innerProcess(Object obj) throws StageEtwitterception {
    if (!(obj instanceof IngesterThriftVersionedEvents)) {
      throw new StageEtwitterception(this, "Object is not IngesterThriftVersionedEvents: " + obj);
    }

    IngesterThriftVersionedEvents events = (IngesterThriftVersionedEvents) obj;
    tryToSendEventsToKafka(events);
  }

  protected void tryToSendEventsToKafka(IngesterThriftVersionedEvents events) {
    if (!isEnabled()) {
      return;
    }

    DebugEvents debugEvents = events.getDebugEvents();
    // We don't propagate debug events to Kafka, because they take about 50%
    // of the storage space.
    events.unsetDebugEvents();

    ProducerRecord<Long, ThriftVersionedEvents> record = new ProducerRecord<>(
        kafkaTopicName,
        null,
        clock.nowMillis(),
        null,
        events);

    sendRecordToKafka(record).ensure(() -> {
      updateEventProcessingLatencyStats(events, debugEvents);
      return null;
    });
  }

  private Future<RecordMetadata> sendRecordToKafka(
      ProducerRecord<Long, ThriftVersionedEvents> record) {
    Future<RecordMetadata> result;
    try {
      result = kafkaProducer.send(record);
    } catch (Etwitterception e) {
      // Even though KafkaProducer.send() returns a Future, it can throw a synchronous etwitterception,
      // so we translate synchronous etwitterceptions into a Future.etwitterception so we handle all etwitterceptions
      // consistently.
      result = Future.etwitterception(e);
    }

    return result.onSuccess(recordMetadata -> {
      sendCount.increment();
      SearchCounter.etwitterport(
          String.format(perPartitionSendCountFormat, recordMetadata.partition())).increment();
      return BotwitteredUnit.UNIT;
    }).onFailure(e -> {
      stats.incrementEtwitterceptions();
      LOG.error("Sending a record failed.", e);
      return BotwitteredUnit.UNIT;
    });
  }

  private void updateEventProcessingLatencyStats(IngesterThriftVersionedEvents events,
                                                 DebugEvents debugEvents) {
    if ((debugEvents != null) && debugEvents.isSetProcessingStartedAt()) {
      // Get the one indetwittering event out of all events we're sending.
      Collection<ThriftIndetwitteringEvent> indetwitteringEvents = events.getVersionedEvents().values();
      Preconditions.checkState(!indetwitteringEvents.isEmpty());
      ThriftIndetwitteringEventType eventType = indetwitteringEvents.iterator().netwittert().getEventType();

      // Check if the event took too much time to get to this current point.
      long processingLatencyMillis =
          clock.nowMillis() - debugEvents.getProcessingStartedAt().getEventTimestampMillis();
      processingLatenciesStats.get(eventType).record(processingLatencyMillis);

      if (processingLatencyMillis >= processingLatencyThresholdMillis) {
        LATE_EVENTS_LOG.warn("Event of type {} for tweet {} was processed in {}ms: {}",
            eventType.name(),
            events.getTweetId(),
            processingLatencyMillis,
            DebugEventUtil.debugEventsToString(debugEvents));
      }
    }
  }

  public void setProcessingLatencyThresholdMillis(int processingLatencyThresholdMillis) {
    this.processingLatencyThresholdMillis = processingLatencyThresholdMillis;
  }

  @Override
  public void innerPostprocess() throws StageEtwitterception {
    try {
      commonCleanup();
    } catch (Etwitterception e) {
      throw new StageEtwitterception(this, e);
    }
  }

  @Override
  public void cleanupStageV2()  {
    try {
      commonCleanup();
    } catch (Etwitterception e) {
      LOG.error("Error trying to clean up KafkaProducerStage.", e);
    }
  }

  private void commonCleanup() throws Etwitterception {
    Await.result(kafkaProducer.close());
  }

  @SuppressWarnings("unused")  // set from pipeline config
  public void setKafkaClientId(String kafkaClientId) {
    this.kafkaClientId = kafkaClientId;
  }

  @SuppressWarnings("unused")  // set from pipeline config
  public void setKafkaTopicName(String kafkaTopicName) {
    this.kafkaTopicName = kafkaTopicName;
  }

  @VisibleForTesting
  public BlockingFinagleKafkaProducer<Long, ThriftVersionedEvents> getKafkaProducer() {
    return kafkaProducer;
  }

  @SuppressWarnings("unused")  // set from pipeline config
  public void setDeciderKey(String deciderKey) {
    this.deciderKey = deciderKey;
  }

  @SuppressWarnings("unused")  // set from pipeline config
  public void setKafkaClusterPath(String kafkaClusterPath) {
    this.kafkaClusterPath = kafkaClusterPath;
  }
}
