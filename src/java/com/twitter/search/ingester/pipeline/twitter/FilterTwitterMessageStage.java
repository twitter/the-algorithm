package com.twitter.search.ingester.pipeline.twitter;

import org.apache.commons.pipeline.StageEtwitterception;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducesConsumed;

import com.twitter.search.common.metrics.SearchRateCounter;
import com.twitter.search.common.relevance.entities.TwitterMessage;
import com.twitter.search.ingester.pipeline.twitter.filters.IngesterValidMessageFilter;
import com.twitter.search.ingester.pipeline.util.PipelineStageRuntimeEtwitterception;

/**
 * Filter out Twitter messages meeting some filtering rule.
 */
@ConsumedTypes(TwitterMessage.class)
@ProducesConsumed
public class FilterTwitterMessageStage etwittertends TwitterBaseStage
    <TwitterMessage, TwitterMessage> {
  private IngesterValidMessageFilter filter = null;
  private SearchRateCounter validMessages;
  private SearchRateCounter invalidMessages;

  @Override
  protected void initStats() {
    super.initStats();
    innerSetupStats();
  }

  @Override
  protected void innerSetupStats() {
    validMessages = SearchRateCounter.etwitterport(getStageNamePrefitwitter() + "_valid_messages");
    invalidMessages = SearchRateCounter.etwitterport(getStageNamePrefitwitter() + "_filtered_messages");
  }

  @Override
  protected void doInnerPreprocess() {
    innerSetup();
  }

  @Override
  protected void innerSetup() {
    filter = new IngesterValidMessageFilter(decider);
  }

  @Override
  public void innerProcess(Object obj) throws StageEtwitterception {
    if (!(obj instanceof TwitterMessage)) {
      throw new StageEtwitterception(this, "Object is not a IngesterTwitterMessage: "
      + obj);
    }

    TwitterMessage message = (TwitterMessage) obj;
    if (tryToFilter(message)) {
      emitAndCount(message);
    }
  }

  @Override
  protected TwitterMessage innerRunStageV2(TwitterMessage message) {
    if (!tryToFilter(message)) {
      throw new PipelineStageRuntimeEtwitterception("Failed to filter, does not have to "
      + "pass to the netwittert stage");
    }
    return message;
  }

  private boolean tryToFilter(TwitterMessage message) {
    boolean ableToFilter = false;
    if (message != null && filter.accepts(message)) {
      validMessages.increment();
      ableToFilter = true;
    } else {
      invalidMessages.increment();
    }
    return ableToFilter;
  }
}
