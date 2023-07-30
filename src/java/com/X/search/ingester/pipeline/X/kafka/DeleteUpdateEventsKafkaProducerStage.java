package com.X.search.ingester.pipeline.X.kafka;

import javax.naming.NamingException;

import com.google.common.base.Preconditions;

import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.validation.ConsumedTypes;

import com.X.search.ingester.model.IngesterXMessage;
import com.X.search.ingester.pipeline.X.ThriftVersionedEventsConverter;
import com.X.search.ingester.pipeline.util.PipelineStageException;

@ConsumedTypes(IngesterXMessage.class)
public class DeleteUpdateEventsKafkaProducerStage extends KafkaProducerStage
    <IngesterXMessage> {
  private ThriftVersionedEventsConverter converter;

  public DeleteUpdateEventsKafkaProducerStage() {
    super();
  }

  public DeleteUpdateEventsKafkaProducerStage(String topicName, String clientId,
                                              String clusterPath) {
    super(topicName, clientId, clusterPath);
  }

  @Override
  protected void innerSetup() throws PipelineStageException, NamingException {
    super.innerSetup();
    commonInnerSetup();
  }

  @Override
  protected void doInnerPreprocess() throws StageException, NamingException {
    super.doInnerPreprocess();
    commonInnerSetup();
  }

  private void commonInnerSetup() throws NamingException {
    converter = new ThriftVersionedEventsConverter(wireModule.getPenguinVersions());

  }
  @Override
  public void innerProcess(Object obj) throws StageException {
    if (!(obj instanceof IngesterXMessage)) {
      throw new StageException(this, "Object is not an IngesterXMessage: " + obj);
    }

    IngesterXMessage message = (IngesterXMessage) obj;
    innerRunFinalStageOfBranchV2(message);
  }

  @Override
  protected void innerRunFinalStageOfBranchV2(IngesterXMessage message) {
    converter.updatePenguinVersions(wireModule.getCurrentlyEnabledPenguinVersions());

    Preconditions.checkArgument(message.getFromUserXId().isPresent(),
        "Missing user ID.");

    super.tryToSendEventsToKafka(converter.toDelete(
        message.getTweetId(), message.getUserId(), message.getDebugEvents()));
  }


}
