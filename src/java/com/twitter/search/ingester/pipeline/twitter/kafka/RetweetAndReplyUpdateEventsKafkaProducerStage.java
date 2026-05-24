package com.twitter.search.ingester.pipeline.twitter.kafka;

import org.apache.commons.pipeline.validation.ConsumedTypes;

import com.twitter.search.common.indexing.thriftjava.ThriftVersionedEvents;
import com.twitter.search.ingester.model.IngesterThriftVersionedEvents;

@ConsumedTypes(ThriftVersionedEvents.class)
public class RetweetAndReplyUpdateEventsKafkaProducerStage extends KafkaProducerStage
    <IngesterThriftVersionedEvents> {
  public RetweetAndReplyUpdateEventsKafkaProducerStage(String kafkaTopic, String clientId,
                                            String clusterPath) {
    super(kafkaTopic, clientId, clusterPath);
  }

  public RetweetAndReplyUpdateEventsKafkaProducerStage() {
    super();
  }

  @Override
  protected void innerRunFinalStageOfBranchV2(IngesterThriftVersionedEvents events) {
    super.tryToSendEventsToKafka(events);
  }
}
