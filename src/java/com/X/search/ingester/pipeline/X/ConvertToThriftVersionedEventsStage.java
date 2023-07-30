package com.X.search.ingester.pipeline.X;

import javax.naming.NamingException;

import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducedTypes;

import com.X.search.common.indexing.thriftjava.ThriftVersionedEvents;
import com.X.search.common.schema.earlybird.EarlybirdFieldConstants;
import com.X.search.ingester.model.IngesterThriftVersionedEvents;
import com.X.search.ingester.model.IngesterXMessage;
import com.X.search.ingester.pipeline.util.PipelineStageRuntimeException;

@ConsumedTypes(IngesterXMessage.class)
@ProducedTypes(ThriftVersionedEvents.class)
public class ConvertToThriftVersionedEventsStage extends XBaseStage
    <IngesterXMessage, IngesterThriftVersionedEvents> {
  private ThriftVersionedEventsConverter converter;

  @Override
  public void doInnerPreprocess() throws StageException, NamingException {
    super.doInnerPreprocess();
    innerSetup();
  }

  @Override
  protected void innerSetup() throws NamingException {
    converter = new ThriftVersionedEventsConverter(wireModule.getPenguinVersions());
  }

  @Override
  public void innerProcess(Object obj) throws StageException {
    if (!(obj instanceof IngesterXMessage)) {
      throw new StageException(this, "Object is not an IngesterXMessage: " + obj);
    }

    IngesterXMessage ingesterXMessage = (IngesterXMessage) obj;
    IngesterThriftVersionedEvents maybeEvents = tryToConvert(ingesterXMessage);

    if (maybeEvents == null) {
      throw new StageException(
          this, "Object is not a retweet or a reply: " + ingesterXMessage);
    }

    emitAndCount(maybeEvents);

  }

  @Override
  protected IngesterThriftVersionedEvents innerRunStageV2(IngesterXMessage message) {
    IngesterThriftVersionedEvents maybeEvents = tryToConvert(message);

    if (maybeEvents == null) {
      throw new PipelineStageRuntimeException("Object is not a retweet or reply, does not have to"
          + " pass to next stage");
    }

    return maybeEvents;
  }

  private IngesterThriftVersionedEvents tryToConvert(IngesterXMessage message) {
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
