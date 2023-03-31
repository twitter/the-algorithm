package com.twitter.search.common.relevance.features;

import com.twitter.search.common.encoding.features.EncodedFeatures;

/**
 * Holds engagement features for a particular tweet and encodes them as a single int.
 * The features are: retweet count, favorite count, itweet score, reply count.
 */
public class TweetEngagementFeatures extends EncodedFeatures {
  private static final int RETWEET_COUNT_BIT_SHIFT = 0;
  private static final long RETWEET_COUNT_INVERSE_BIT_MASK =  0xffffff00L;

  private static final int ITWEET_SCORE_BIT_SHIFT = 8;
  private static final long ITWEET_SCORE_INVERSE_BIT_MASK = 0xffff00ffL;

  private static final int FAV_COUNT_BIT_SHIFT = 16;
  private static final long FAV_COUNT_INVERSE_BIT_MASK =    0xff00ffffL;

  private static final int REPLY_COUNT_BIT_SHIFT = 24;
  private static final long REPLY_COUNT_INVERSE_BIT_MASK =    0x00ffffffL;

  public TweetEngagementFeatures setRetweetCount(byte count) {
    setByteIfGreater(count, RETWEET_COUNT_BIT_SHIFT, RETWEET_COUNT_INVERSE_BIT_MASK);
    return this;
  }

  public int getRetweetCount() {
    return getByte(RETWEET_COUNT_BIT_SHIFT);
  }

  public TweetEngagementFeatures setITweetScore(byte score) {
    setByteIfGreater(score, ITWEET_SCORE_BIT_SHIFT, ITWEET_SCORE_INVERSE_BIT_MASK);
    return this;
  }

  public int getITweetScore() {
    return getByte(ITWEET_SCORE_BIT_SHIFT);
  }

  public TweetEngagementFeatures setFavCount(byte count) {
    setByteIfGreater(count, FAV_COUNT_BIT_SHIFT, FAV_COUNT_INVERSE_BIT_MASK);
    return this;
  }

  public int getFavCount() {
    return getByte(FAV_COUNT_BIT_SHIFT);
  }

  public TweetEngagementFeatures setReplyCount(byte count) {
    setByteIfGreater(count, REPLY_COUNT_BIT_SHIFT, REPLY_COUNT_INVERSE_BIT_MASK);
    return this;
  }

  public int getReplyCount() {
    return getByte(REPLY_COUNT_BIT_SHIFT);
  }
}
