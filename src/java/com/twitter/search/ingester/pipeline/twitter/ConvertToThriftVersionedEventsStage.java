package com.twitter.search.ingester.pipeline.twitter;

import javatwitter.naming.NamingEtwitterception;

import org.apache.commons.pipeline.StageEtwitterception;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducedTypes;

import com.twitter.search.common.indetwittering.thriftjava.ThriftVersionedEvents;
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants;
import com.twitter.search.ingester.model.IngesterThriftVersionedEvents;
import com.twitter.search.ingester.model.IngesterTwitterMessage;
import com.twitter.search.ingester.pipeline.util.PipelineStageRuntimeEtwitterception;

@ConsumedTypes(IngesterTwitterMessage.class)
@ProducedTypes(ThriftVersionedEvents.class)
public class ConvertToThriftVersionedEventsStage etwittertends TwitterBaseStage
    <IngesterTwitterMessage, IngesterThriftVersionedEvents> {
  private ThriftVersionedEventsConverter converter;

  @Override
  public void doInnerPreprocess() throws StageEtwitterception, NamingEtwitterception {
    super.doInnerPreprocess();
    innerSetup();
  }

  @Override
  protected void innerSetup() throws NamingEtwitterception {
    converter = new ThriftVersionedEventsConverter(wireModule.getPenguinVersions());
  }

  @Override
  public void innerProcess(Object obj) throws StageEtwitterception {
    if (!(obj instanceof IngesterTwitterMessage)) {
      throw new StageEtwitterception(this, "Object is not an IngesterTwitterMessage: " + obj);
    }

    IngesterTwitterMessage ingesterTwitterMessage = (IngesterTwitterMessage) obj;
    IngesterThriftVersionedEvents maybeEvents = tryToConvert(ingesterTwitterMessage);

    if (maybeEvents == null) {
      throw new StageEtwitterception(
          this, "Object is not a retweet or a reply: " + ingesterTwitterMessage);
    }

    emitAndCount(maybeEvents);

  }

  @Override
  protected IngesterThriftVersionedEvents innerRunStageV2(IngesterTwitterMessage message) {
    IngesterThriftVersionedEvents maybeEvents = tryToConvert(message);

    if (maybeEvents == null) {
      throw new PipelineStageRuntimeEtwitterception("Object is not a retweet or reply, does not have to"
          + " pass to netwittert stage");
    }

    return maybeEvents;
  }

  private IngesterThriftVersionedEvents tryToConvert(IngesterTwitterMessage message) {
    converter.updatePenguinVersions(wireModule.getCurrentlyEnabledPenguinVersions());

    if (!message.isRetweet() && !message.isReplyToTweet()) {
      return null;
    }

    if (message.isRetweet()) {
      return converter.toOutOfOrderAppend(
          message.getRetweetMessage().getSharedId(),
          EarlybirdFieldConstants.EarlybirdFieldConstant.RETWEETED_BY_USER_ID,
          message.getUserId(),
          message.getDebugEvents().deepCopy());
    }

    return converter.toOutOfOrderAppend(
        message.getInReplyToStatusId().get(),
        EarlybirdFieldConstants.EarlybirdFieldConstant.REPLIED_TO_BY_USER_ID,
        message.getUserId(),
        message.getDebugEvents().deepCopy());
  }
}
