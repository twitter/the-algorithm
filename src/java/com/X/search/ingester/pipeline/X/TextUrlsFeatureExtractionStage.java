package com.X.search.ingester.pipeline.X;

import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducesConsumed;

import com.X.search.common.relevance.classifiers.TweetOffensiveEvaluator;
import com.X.search.common.relevance.entities.XMessage;
import com.X.search.common.relevance.scorers.TweetTextScorer;
import com.X.search.common.relevance.text.TweetParser;
import com.X.search.ingester.model.IngesterXMessage;

@ConsumedTypes(XMessage.class)
@ProducesConsumed
public class TextUrlsFeatureExtractionStage extends XBaseStage
    <IngesterXMessage, IngesterXMessage> {
  private final TweetParser tweetParser = new TweetParser();
  private TweetOffensiveEvaluator offensiveEvaluator;
  private final TweetTextScorer tweetTextScorer = new TweetTextScorer(null);

  @Override
  protected void doInnerPreprocess()  {
    innerSetup();
  }

  @Override
  protected void innerSetup() {
    offensiveEvaluator = wireModule.getTweetOffensiveEvaluator();
  }

  @Override
  public void innerProcess(Object obj) throws StageException {
    if (!(obj instanceof IngesterXMessage)) {
      throw new StageException(this, "Object is not a XMessage instance: " + obj);
    }

    IngesterXMessage message = IngesterXMessage.class.cast(obj);
    extract(message);
    emitAndCount(message);
  }

  private void extract(IngesterXMessage message) {
    tweetParser.parseUrls(message);
    offensiveEvaluator.evaluate(message);
    tweetTextScorer.scoreTweet(message);
  }

  @Override
  protected IngesterXMessage innerRunStageV2(IngesterXMessage message) {
    extract(message);
    return message;
  }
}
