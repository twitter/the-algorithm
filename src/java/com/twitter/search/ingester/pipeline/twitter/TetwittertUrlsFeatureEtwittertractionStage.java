package com.twitter.search.ingester.pipeline.twitter;

import org.apache.commons.pipeline.StageEtwitterception;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducesConsumed;

import com.twitter.search.common.relevance.classifiers.TweetOffensiveEvaluator;
import com.twitter.search.common.relevance.entities.TwitterMessage;
import com.twitter.search.common.relevance.scorers.TweetTetwittertScorer;
import com.twitter.search.common.relevance.tetwittert.TweetParser;
import com.twitter.search.ingester.model.IngesterTwitterMessage;

@ConsumedTypes(TwitterMessage.class)
@ProducesConsumed
public class TetwittertUrlsFeatureEtwittertractionStage etwittertends TwitterBaseStage
    <IngesterTwitterMessage, IngesterTwitterMessage> {
  private final TweetParser tweetParser = new TweetParser();
  private TweetOffensiveEvaluator offensiveEvaluator;
  private final TweetTetwittertScorer tweetTetwittertScorer = new TweetTetwittertScorer(null);

  @Override
  protected void doInnerPreprocess()  {
    innerSetup();
  }

  @Override
  protected void innerSetup() {
    offensiveEvaluator = wireModule.getTweetOffensiveEvaluator();
  }

  @Override
  public void innerProcess(Object obj) throws StageEtwitterception {
    if (!(obj instanceof IngesterTwitterMessage)) {
      throw new StageEtwitterception(this, "Object is not a TwitterMessage instance: " + obj);
    }

    IngesterTwitterMessage message = IngesterTwitterMessage.class.cast(obj);
    etwittertract(message);
    emitAndCount(message);
  }

  private void etwittertract(IngesterTwitterMessage message) {
    tweetParser.parseUrls(message);
    offensiveEvaluator.evaluate(message);
    tweetTetwittertScorer.scoreTweet(message);
  }

  @Override
  protected IngesterTwitterMessage innerRunStageV2(IngesterTwitterMessage message) {
    etwittertract(message);
    return message;
  }
}
