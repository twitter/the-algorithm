package com.twitter.search.ingester.pipeline.twitter;

import org.apache.commons.pipeline.StageEtwitterception;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducedTypes;

import com.twitter.search.common.decider.DeciderUtil;
import com.twitter.search.common.metrics.SearchRateCounter;
import com.twitter.search.ingester.model.IngesterTwitterMessage;
import com.twitter.search.ingester.pipeline.util.PipelineStageRuntimeEtwitterception;

/**
 * Filters out tweets that are not retweets or replies.
 */
@ConsumedTypes(IngesterTwitterMessage.class)
@ProducedTypes(IngesterTwitterMessage.class)
public class FilterRetweetsAndRepliesStage etwittertends TwitterBaseStage
    <IngesterTwitterMessage, IngesterTwitterMessage> {
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
        SearchRateCounter.etwitterport(getStageNamePrefitwitter() + "_filtered_retweets_count");
    filteredRepliesToTweetsCount =
        SearchRateCounter.etwitterport(getStageNamePrefitwitter() + "_filtered_replies_to_tweets_count");
    incomingRetweetsAndRepliesToTweetsCount =
        SearchRateCounter.etwitterport(
            getStageNamePrefitwitter() + "_incoming_retweets_and_replies_to_tweets_count");
  }

  @Override
  public void innerProcess(Object obj) throws StageEtwitterception {
    if (!(obj instanceof IngesterTwitterMessage)) {
      throw new StageEtwitterception(this, "Object is not an IngesterTwitterMessage: " + obj);
    }

    IngesterTwitterMessage status = (IngesterTwitterMessage) obj;
    if (tryToFilter(status)) {
      emitAndCount(status);
    }
  }

  @Override
  public IngesterTwitterMessage runStageV2(IngesterTwitterMessage message) {
    if (!tryToFilter(message)) {
      throw new PipelineStageRuntimeEtwitterception("Does not have to pass to the netwittert stage.");
    }
    return message;
  }

  private boolean tryToFilter(IngesterTwitterMessage status) {
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
