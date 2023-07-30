package com.X.search.ingester.pipeline.X;

import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducedTypes;

import com.X.search.common.decider.DeciderUtil;
import com.X.search.common.metrics.SearchRateCounter;
import com.X.search.ingester.model.IngesterXMessage;
import com.X.search.ingester.pipeline.util.PipelineStageRuntimeException;

/**
 * Filters out tweets that are not retweets or replies.
 */
@ConsumedTypes(IngesterXMessage.class)
@ProducedTypes(IngesterXMessage.class)
public class FilterRetweetsAndRepliesStage extends XBaseStage
    <IngesterXMessage, IngesterXMessage> {
  private static final String EMIT_RETWEET_AND_REPLY_ENGAGEMENTS_DECIDER_KEY =
      "ingester_realtime_emit_retweet_and_reply_engagements";

  private SearchRateCounter filteredRetweetsCount;
  private SearchRateCounter filteredRepliesToTweetsCount;
  private SearchRateCounter incomingRetweetsAndRepliesToTweetsCount;

  @Override
  public void initStats() {
    super.initStats();
    innerSetupStats();
  }

  @Override
  protected void innerSetupStats() {
    filteredRetweetsCount =
        SearchRateCounter.export(getStageNamePrefix() + "_filtered_retweets_count");
    filteredRepliesToTweetsCount =
        SearchRateCounter.export(getStageNamePrefix() + "_filtered_replies_to_tweets_count");
    incomingRetweetsAndRepliesToTweetsCount =
        SearchRateCounter.export(
            getStageNamePrefix() + "_incoming_retweets_and_replies_to_tweets_count");
  }

  @Override
  public void innerProcess(Object obj) throws StageException {
    if (!(obj instanceof IngesterXMessage)) {
      throw new StageException(this, "Object is not an IngesterXMessage: " + obj);
    }

    IngesterXMessage status = (IngesterXMessage) obj;
    if (tryToFilter(status)) {
      emitAndCount(status);
    }
  }

  @Override
  public IngesterXMessage runStageV2(IngesterXMessage message) {
    if (!tryToFilter(message)) {
      throw new PipelineStageRuntimeException("Does not have to pass to the next stage.");
    }
    return message;
  }

  private boolean tryToFilter(IngesterXMessage status) {
    boolean shouldEmit = false;
    if (status.isRetweet() || status.isReplyToTweet()) {
      incomingRetweetsAndRepliesToTweetsCount.increment();
      if (DeciderUtil.isAvailableForRandomRecipient(
          decider, EMIT_RETWEET_AND_REPLY_ENGAGEMENTS_DECIDER_KEY)) {
        if (status.isRetweet()) {
          filteredRetweetsCount.increment();
        } else {
          filteredRepliesToTweetsCount.increment();
        }
        shouldEmit = true;
      }
    }
    return shouldEmit;
  }
}
