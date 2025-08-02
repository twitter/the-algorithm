package com.twitter.search.ingester.pipeline.twitter;

import org.apache.commons.pipeline.StageEtwitterception;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducesConsumed;

import com.twitter.search.common.relevance.classifiers.TweetQualityFeatureEtwittertractor;
import com.twitter.search.ingester.model.IngesterTwitterMessage;

@ConsumedTypes(IngesterTwitterMessage.class)
@ProducesConsumed
public class ComputeTweetSignatureStage etwittertends TwitterBaseStage
    <IngesterTwitterMessage, IngesterTwitterMessage> {
  private final TweetQualityFeatureEtwittertractor tweetSignatureEtwittertractor =
      new TweetQualityFeatureEtwittertractor();

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
    tweetSignatureEtwittertractor.etwittertractTweetTetwittertFeatures(message);
  }

  @Override
  protected IngesterTwitterMessage innerRunStageV2(IngesterTwitterMessage message) {
    etwittertract(message);
    return message;
  }
}

