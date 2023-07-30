package com.X.search.earlybird.document;

import com.X.common.text.token.TokenProcessor;
import com.X.common.text.token.XTokenStream;
import com.X.decider.Decider;
import com.X.search.common.decider.DeciderUtil;
import com.X.search.common.metrics.SearchCounter;
import com.X.search.common.metrics.SearchLongGauge;
import com.X.search.common.schema.SchemaDocumentFactory;
import com.X.search.common.schema.base.Schema;
import com.X.search.common.schema.earlybird.EarlybirdCluster;
import com.X.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant;

public class TruncationTokenStreamWriter implements SchemaDocumentFactory.TokenStreamRewriter {
  private static final int NEVER_TRUNCATE_CHARS_BELOW_POSITION = 140;
  private static final String TRUNCATE_LONG_TWEETS_DECIDER_KEY_PREFIX =
      "truncate_long_tweets_in_";
  private static final String NUM_TWEET_CHARACTERS_SUPPORTED_DECIDER_KEY_PREFIX =
      "num_tweet_characters_supported_in_";

  private static final SearchCounter NUM_TWEETS_TRUNCATED =
      SearchCounter.export("num_tweets_truncated");
  private static final SearchLongGauge NUM_TWEET_CHARACTERS_SUPPORTED =
      SearchLongGauge.export("num_tweet_characters_supported");

  private final Decider decider;
  private final String truncateLongTweetsDeciderKey;
  private final String numCharsSupportedDeciderKey;

  /**
   * Creates a TruncationTokenStreamWriter
   */
  public TruncationTokenStreamWriter(EarlybirdCluster cluster, Decider decider) {
    this.decider = decider;

    this.truncateLongTweetsDeciderKey =
        TRUNCATE_LONG_TWEETS_DECIDER_KEY_PREFIX + cluster.name().toLowerCase();
    this.numCharsSupportedDeciderKey =
        NUM_TWEET_CHARACTERS_SUPPORTED_DECIDER_KEY_PREFIX + cluster.name().toLowerCase();
  }

  @Override
  public XTokenStream rewrite(Schema.FieldInfo fieldInfo, XTokenStream stream) {
    if (EarlybirdFieldConstant.TEXT_FIELD.getFieldName().equals(fieldInfo.getName())) {
      final int maxPosition = getTruncatePosition();
      NUM_TWEET_CHARACTERS_SUPPORTED.set(maxPosition);
      if (maxPosition >= NEVER_TRUNCATE_CHARS_BELOW_POSITION) {
        return new TokenProcessor(stream) {
          @Override
          public final boolean incrementToken() {
            if (incrementInputStream()) {
              if (offset() < maxPosition) {
                return true;
              }
              NUM_TWEETS_TRUNCATED.increment();
            }

            return false;
          }
        };
      }
    }

    return stream;
  }

  /**
   * Get the truncation position.
   *
   * @return the truncation position or -1 if truncation is disabled.
   */
  private int getTruncatePosition() {
    int maxPosition;
    if (!DeciderUtil.isAvailableForRandomRecipient(decider, truncateLongTweetsDeciderKey)) {
      return -1;
    }
    maxPosition = DeciderUtil.getAvailability(decider, numCharsSupportedDeciderKey);

    if (maxPosition < NEVER_TRUNCATE_CHARS_BELOW_POSITION) {
      // Never truncate below NEVER_TRUNCATE_CHARS_BELOW_POSITION chars
      maxPosition = NEVER_TRUNCATE_CHARS_BELOW_POSITION;
    }

    return maxPosition;
  }
}
