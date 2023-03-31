package com.twitter.search.earlybird.search.relevance.scoring;

import java.io.IOException;

import com.google.common.annotations.VisibleForTesting;

import org.apache.lucene.search.Explanation;

import com.twitter.search.common.relevance.features.RelevanceSignalConstants;
import com.twitter.search.common.schema.base.ImmutableSchemaInterface;
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant;
import com.twitter.search.earlybird.common.config.EarlybirdConfig;
import com.twitter.search.earlybird.thrift.ThriftSearchResultMetadata;
import com.twitter.search.earlybird.thrift.ThriftSearchResultMetadataOptions;
import com.twitter.search.earlybird.thrift.ThriftSearchResultsRelevanceStats;

public class SpamVectorScoringFunction extends ScoringFunction {
  private static final int MIN_TWEEPCRED_WITH_LINK =
      EarlybirdConfig.getInt("min_tweepcred_with_non_whitelisted_link", 25);

  // The engagement threshold that prevents us from filtering users with low tweepcred.
  private static final int ENGAGEMENTS_NO_FILTER = 1;

  @VisibleForTesting
  static final float NOT_SPAM_SCORE = 0.5f;
  @VisibleForTesting
  static final float SPAM_SCORE = -0.5f;

  public SpamVectorScoringFunction(ImmutableSchemaInterface schema) {
    super(schema);
  }

  @Override
  protected float score(float luceneQueryScore) throws IOException {
    if (documentFeatures.isFlagSet(EarlybirdFieldConstant.FROM_VERIFIED_ACCOUNT_FLAG)) {
      return NOT_SPAM_SCORE;
    }

    int tweepCredThreshold = 0;
    if (documentFeatures.isFlagSet(EarlybirdFieldConstant.HAS_LINK_FLAG)
        && !documentFeatures.isFlagSet(EarlybirdFieldConstant.HAS_IMAGE_URL_FLAG)
        && !documentFeatures.isFlagSet(EarlybirdFieldConstant.HAS_VIDEO_URL_FLAG)
        && !documentFeatures.isFlagSet(EarlybirdFieldConstant.HAS_NEWS_URL_FLAG)) {
      // Contains a non-media non-news link, definite spam vector.
      tweepCredThreshold = MIN_TWEEPCRED_WITH_LINK;
    }

    int tweepcred = (int) documentFeatures.getFeatureValue(EarlybirdFieldConstant.USER_REPUTATION);

    // For new user, tweepcred is set to a sentinel value of -128, specified at
    // src/thrift/com/twitter/search/common/indexing/status.thrift
    if (tweepcred >= tweepCredThreshold
        || tweepcred == (int) RelevanceSignalConstants.UNSET_REPUTATION_SENTINEL) {
      return NOT_SPAM_SCORE;
    }

    double retweetCount =
        documentFeatures.getUnnormalizedFeatureValue(EarlybirdFieldConstant.RETWEET_COUNT);
    double replyCount =
        documentFeatures.getUnnormalizedFeatureValue(EarlybirdFieldConstant.REPLY_COUNT);
    double favoriteCount =
        documentFeatures.getUnnormalizedFeatureValue(EarlybirdFieldConstant.FAVORITE_COUNT);

    // If the tweet has enough engagements, do not mark it as spam.
    if (retweetCount + replyCount + favoriteCount >= ENGAGEMENTS_NO_FILTER) {
      return NOT_SPAM_SCORE;
    }

    return SPAM_SCORE;
  }

  @Override
  protected Explanation doExplain(float luceneScore) {
    return null;
  }

  @Override
  public ThriftSearchResultMetadata getResultMetadata(ThriftSearchResultMetadataOptions options) {
    return null;
  }

  @Override
  public void updateRelevanceStats(ThriftSearchResultsRelevanceStats relevanceStats) {
  }
}
