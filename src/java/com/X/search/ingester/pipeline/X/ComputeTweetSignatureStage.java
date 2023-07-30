package com.X.search.ingester.pipeline.X;

import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducesConsumed;

import com.X.search.common.relevance.classifiers.TweetQualityFeatureExtractor;
import com.X.search.ingester.model.IngesterXMessage;

@ConsumedTypes(IngesterXMessage.class)
@ProducesConsumed
public class ComputeTweetSignatureStage extends XBaseStage
    <IngesterXMessage, IngesterXMessage> {
  private final TweetQualityFeatureExtractor tweetSignatureExtractor =
      new TweetQualityFeatureExtractor();

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
    tweetSignatureExtractor.extractTweetTextFeatures(message);
  }

  @Override
  protected IngesterXMessage innerRunStageV2(IngesterXMessage message) {
    extract(message);
    return message;
  }
}

