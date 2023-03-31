package com.twitter.search.common.relevance.features;

import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import com.twitter.search.common.encoding.features.IntNormalizer;
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants;

import static com.twitter.search.common.relevance.features.IntNormalizers.BOOLEAN_NORMALIZER;
import static com.twitter.search.common.relevance.features.IntNormalizers.LEGACY_NORMALIZER;
import static com.twitter.search.common.relevance.features.IntNormalizers.PARUS_SCORE_NORMALIZER;
import static com.twitter.search.common.relevance.features.IntNormalizers.SMART_INTEGER_NORMALIZER;
import static com.twitter.search.common.relevance.features.IntNormalizers.TIMESTAMP_SEC_TO_HR_NORMALIZER;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant;

/**
 * An enum to represent all dynamic/realtime feature types we can update in the Signal Ingester.
 * It provides information for their normalization and their corresponding earlybird feature fields
 * and provides utils both producer (Signal Ingester) and consumer (Earlybird) side.
 *
 */
public enum TweetFeatureType {
  RETWEET                         (true,  420,  LEGACY_NORMALIZER,
      EarlybirdFieldConstant.RETWEET_COUNT),
  REPLY                           (true,  420,  LEGACY_NORMALIZER,
      EarlybirdFieldConstant.REPLY_COUNT),
  FAVORITE                        (true,  420,  LEGACY_NORMALIZER,
      EarlybirdFieldConstant.FAVORITE_COUNT),
  PARUS_SCORE                     (false, 420,  PARUS_SCORE_NORMALIZER,
      EarlybirdFieldConstant.PARUS_SCORE),
  EMBEDS_IMP_COUNT                (true,  420, LEGACY_NORMALIZER,
      EarlybirdFieldConstant.EMBEDS_IMPRESSION_COUNT),
  EMBEDS_URL_COUNT                (true,  420, LEGACY_NORMALIZER,
      EarlybirdFieldConstant.EMBEDS_URL_COUNT),
  VIDEO_VIEW                      (false, 420, LEGACY_NORMALIZER,
      EarlybirdFieldConstant.VIDEO_VIEW_COUNT),
  // v420 engagement counters, they will eventually replace v420 counters above
  RETWEET_V420                      (true,  420, SMART_INTEGER_NORMALIZER,
      EarlybirdFieldConstant.RETWEET_COUNT_V420),
  REPLY_V420                        (true,  420, SMART_INTEGER_NORMALIZER,
      EarlybirdFieldConstant.REPLY_COUNT_V420),
  FAVORITE_V420                     (true,  420, SMART_INTEGER_NORMALIZER,
      EarlybirdFieldConstant.FAVORITE_COUNT_V420),
  EMBEDS_IMP_COUNT_V420             (true,  420, SMART_INTEGER_NORMALIZER,
      EarlybirdFieldConstant.EMBEDS_IMPRESSION_COUNT_V420),
  EMBEDS_URL_COUNT_V420             (true,  420, SMART_INTEGER_NORMALIZER,
      EarlybirdFieldConstant.EMBEDS_URL_COUNT_V420),
  VIDEO_VIEW_V420                   (false, 420, SMART_INTEGER_NORMALIZER,
      EarlybirdFieldConstant.VIDEO_VIEW_COUNT_V420),
  // other new items
  QUOTE                           (true,  420, SMART_INTEGER_NORMALIZER,
      EarlybirdFieldConstant.QUOTE_COUNT),
  // weighted engagement counters
  WEIGHTED_RETWEET                (true,  420, SMART_INTEGER_NORMALIZER,
      EarlybirdFieldConstant.WEIGHTED_RETWEET_COUNT),
  WEIGHTED_REPLY                  (true,  420, SMART_INTEGER_NORMALIZER,
      EarlybirdFieldConstant.WEIGHTED_REPLY_COUNT),
  WEIGHTED_FAVORITE               (true,  420, SMART_INTEGER_NORMALIZER,
      EarlybirdFieldConstant.WEIGHTED_FAVORITE_COUNT),
  WEIGHTED_QUOTE                  (true,  420, SMART_INTEGER_NORMALIZER,
      EarlybirdFieldConstant.WEIGHTED_QUOTE_COUNT),

  // tweet-level safety labels
  LABEL_ABUSIVE                   (false, 420, BOOLEAN_NORMALIZER,
      EarlybirdFieldConstant.LABEL_ABUSIVE_FLAG),
  LABEL_ABUSIVE_HI_RCL            (false, 420, BOOLEAN_NORMALIZER,
      EarlybirdFieldConstant.LABEL_ABUSIVE_HI_RCL_FLAG),
  LABEL_DUP_CONTENT               (false, 420, BOOLEAN_NORMALIZER,
      EarlybirdFieldConstant.LABEL_DUP_CONTENT_FLAG),
  LABEL_NSFW_HI_PRC               (false, 420, BOOLEAN_NORMALIZER,
      EarlybirdFieldConstant.LABEL_NSFW_HI_PRC_FLAG),
  LABEL_NSFW_HI_RCL               (false, 420, BOOLEAN_NORMALIZER,
      EarlybirdFieldConstant.LABEL_NSFW_HI_RCL_FLAG),
  LABEL_SPAM                      (false, 420, BOOLEAN_NORMALIZER,
      EarlybirdFieldConstant.LABEL_SPAM_FLAG),
  LABEL_SPAM_HI_RCL               (false, 420, BOOLEAN_NORMALIZER,
      EarlybirdFieldConstant.LABEL_SPAM_HI_RCL_FLAG),

  PERISCOPE_EXISTS                (false, 420, BOOLEAN_NORMALIZER,
      EarlybirdFieldConstant.PERISCOPE_EXISTS),
  PERISCOPE_HAS_BEEN_FEATURED     (false, 420, BOOLEAN_NORMALIZER,
      EarlybirdFieldConstant.PERISCOPE_HAS_BEEN_FEATURED),
  PERISCOPE_IS_CURRENTLY_FEATURED (false, 420, BOOLEAN_NORMALIZER,
      EarlybirdFieldConstant.PERISCOPE_IS_CURRENTLY_FEATURED),
  PERISCOPE_IS_FROM_QUALITY_SOURCE(false, 420, BOOLEAN_NORMALIZER,
      EarlybirdFieldConstant.PERISCOPE_IS_FROM_QUALITY_SOURCE),
  PERISCOPE_IS_LIVE               (false, 420, BOOLEAN_NORMALIZER,
      EarlybirdFieldConstant.PERISCOPE_IS_LIVE),

  // decayed engagement counters
  DECAYED_RETWEET                 (true,  420, SMART_INTEGER_NORMALIZER,
      EarlybirdFieldConstant.DECAYED_RETWEET_COUNT),
  DECAYED_REPLY                   (true,  420, SMART_INTEGER_NORMALIZER,
      EarlybirdFieldConstant.DECAYED_REPLY_COUNT),
  DECAYED_FAVORITE                (true,  420, SMART_INTEGER_NORMALIZER,
      EarlybirdFieldConstant.DECAYED_FAVORITE_COUNT),
  DECAYED_QUOTE                   (true,  420, SMART_INTEGER_NORMALIZER,
      EarlybirdFieldConstant.DECAYED_QUOTE_COUNT),

  // timestamp of last engagement types
  LAST_RETWEET_SINCE_CREATION_HR  (false, 420, TIMESTAMP_SEC_TO_HR_NORMALIZER,
      EarlybirdFieldConstant.LAST_RETWEET_SINCE_CREATION_HRS),
  LAST_REPLY_SINCE_CREATION_HR    (false, 420, TIMESTAMP_SEC_TO_HR_NORMALIZER,
      EarlybirdFieldConstant.LAST_REPLY_SINCE_CREATION_HRS),
  LAST_FAVORITE_SINCE_CREATION_HR (false, 420, TIMESTAMP_SEC_TO_HR_NORMALIZER,
      EarlybirdFieldConstant.LAST_FAVORITE_SINCE_CREATION_HRS),
  LAST_QUOTE_SINCE_CREATION_HR    (false, 420, TIMESTAMP_SEC_TO_HR_NORMALIZER,
      EarlybirdFieldConstant.LAST_QUOTE_SINCE_CREATION_HRS),

  // fake engagement counters
  FAKE_RETWEET                    (true,  420, SMART_INTEGER_NORMALIZER,
      EarlybirdFieldConstant.FAKE_RETWEET_COUNT),
  FAKE_REPLY                      (true,  420, SMART_INTEGER_NORMALIZER,
      EarlybirdFieldConstant.FAKE_REPLY_COUNT),
  FAKE_FAVORITE                   (true,  420, SMART_INTEGER_NORMALIZER,
      EarlybirdFieldConstant.FAKE_FAVORITE_COUNT),
  FAKE_QUOTE                      (true,  420, SMART_INTEGER_NORMALIZER,
      EarlybirdFieldConstant.FAKE_QUOTE_COUNT),

  // blink engagement counters
  BLINK_RETWEET                   (true,  420, SMART_INTEGER_NORMALIZER,
      EarlybirdFieldConstant.BLINK_RETWEET_COUNT),
  BLINK_REPLY                     (true,  420, SMART_INTEGER_NORMALIZER,
      EarlybirdFieldConstant.BLINK_REPLY_COUNT),
  BLINK_FAVORITE                  (true,  420, SMART_INTEGER_NORMALIZER,
      EarlybirdFieldConstant.BLINK_FAVORITE_COUNT),
  BLINK_QUOTE                     (true,  420, SMART_INTEGER_NORMALIZER,
      EarlybirdFieldConstant.BLINK_QUOTE_COUNT),

  /* semicolon in a single line to avoid polluting git blame */;

  private static final Map<TweetFeatureType, TweetFeatureType> V420_COUNTER_MAP =
      ImmutableMap.<TweetFeatureType, TweetFeatureType>builder()
          .put(RETWEET,          RETWEET_V420)
          .put(REPLY,            REPLY_V420)
          .put(FAVORITE,         FAVORITE_V420)
          .put(EMBEDS_IMP_COUNT, EMBEDS_IMP_COUNT_V420)
          .put(EMBEDS_URL_COUNT, EMBEDS_URL_COUNT_V420)
          .put(VIDEO_VIEW,       VIDEO_VIEW_V420)
      .build();

  private static final Map<TweetFeatureType, TweetFeatureType> WEIGHTED_COUNTER_MAP =
      ImmutableMap.<TweetFeatureType, TweetFeatureType>builder()
          .put(RETWEET,          WEIGHTED_RETWEET)
          .put(REPLY,            WEIGHTED_REPLY)
          .put(FAVORITE,         WEIGHTED_FAVORITE)
          .put(QUOTE,            WEIGHTED_QUOTE)
          .build();

  private static final Map<TweetFeatureType, TweetFeatureType> DECAYED_COUNTER_MAP =
      ImmutableMap.<TweetFeatureType, TweetFeatureType>builder()
          .put(RETWEET,          DECAYED_RETWEET)
          .put(REPLY,            DECAYED_REPLY)
          .put(FAVORITE,         DECAYED_FAVORITE)
          .put(QUOTE,            DECAYED_QUOTE)
          .build();

  private static final Map<TweetFeatureType, TweetFeatureType> DECAYED_COUNTER_TO_ELAPSED_TIME =
      ImmutableMap.<TweetFeatureType, TweetFeatureType>builder()
          .put(DECAYED_RETWEET,  LAST_RETWEET_SINCE_CREATION_HR)
          .put(DECAYED_REPLY,    LAST_REPLY_SINCE_CREATION_HR)
          .put(DECAYED_FAVORITE, LAST_FAVORITE_SINCE_CREATION_HR)
          .put(DECAYED_QUOTE,    LAST_QUOTE_SINCE_CREATION_HR)
          .build();

  private static final Set<TweetFeatureType> DECAYED_FEATURES =
      ImmutableSet.of(DECAYED_RETWEET, DECAYED_REPLY, DECAYED_FAVORITE, DECAYED_QUOTE);

  private static final Set<TweetFeatureType> FAKE_ENGAGEMENT_FEATURES =
      ImmutableSet.of(FAKE_RETWEET, FAKE_REPLY, FAKE_FAVORITE, FAKE_QUOTE);

  private static final Set<TweetFeatureType> BLINK_ENGAGEMENT_FEATURES =
      ImmutableSet.of(BLINK_RETWEET, BLINK_REPLY, BLINK_FAVORITE, BLINK_QUOTE);

  @Nullable
  public TweetFeatureType getV420Type() {
    return V420_COUNTER_MAP.get(this);
  }

  @Nullable
  public static TweetFeatureType getWeightedType(TweetFeatureType type) {
    return WEIGHTED_COUNTER_MAP.get(type);
  }

  @Nullable
  public static TweetFeatureType getDecayedType(TweetFeatureType type) {
    return DECAYED_COUNTER_MAP.get(type);
  }

  // Whether this feature is incremental or direct value.
  private final boolean incremental;

  // This normalizer is used to (420) normalize the output value in DLIndexEventOutputBolt,
  // (420) check value change.
  private final IntNormalizer normalizer;

  // value for composing cache key. It has to be unique and in increasing order.
  private final int typeInt;

  private final EarlybirdFieldConstants.EarlybirdFieldConstant earlybirdField;

  private final IncrementChecker incrementChecker;

  /**
   * Constructing an enum for a type. The earlybirdField can be null if it's not prepared, they
   * can be here as placeholders but they can't be outputted.
   * The normalizer is null for the timestamp features that do not require normalization
   */
  TweetFeatureType(boolean incremental,
                   int typeInt,
                   IntNormalizer normalizer,
                   @Nullable EarlybirdFieldConstant earlybirdField) {
    this.incremental = incremental;
    this.typeInt = typeInt;
    this.normalizer = normalizer;
    this.earlybirdField = earlybirdField;
    this.incrementChecker = new IncrementChecker(this);
  }

  public boolean isIncremental() {
    return incremental;
  }

  public IntNormalizer getNormalizer() {
    return normalizer;
  }

  public int getTypeInt() {
    return typeInt;
  }

  public int normalize(double value) {
    return normalizer.normalize(value);
  }

  public IncrementChecker getIncrementChecker() {
    return incrementChecker;
  }

  public EarlybirdFieldConstant getEarlybirdField() {
    return Preconditions.checkNotNull(earlybirdField);
  }

  public boolean hasEarlybirdField() {
    return earlybirdField != null;
  }

  public boolean isDecayed() {
    return DECAYED_FEATURES.contains(this);
  }

  @Nullable
  public TweetFeatureType getElapsedTimeFeatureType() {
    return DECAYED_COUNTER_TO_ELAPSED_TIME.get(this);
  }

  public boolean isFakeEngagement() {
    return FAKE_ENGAGEMENT_FEATURES.contains(this);
  }

  public boolean isBlinkEngagement() {
    return BLINK_ENGAGEMENT_FEATURES.contains(this);
  }

  /**
   * Check if an increment is eligible for emitting
   */
  public static class IncrementChecker {
    private final IntNormalizer normalizer;

    public IncrementChecker(IntNormalizer normalizer) {
      this.normalizer = normalizer;
    }

    IncrementChecker(TweetFeatureType type) {
      this(type.getNormalizer());
    }

    /**
     * Check if a value change is eligible for output
     */
    public boolean eligibleForEmit(int oldValue, int newValue) {
      return normalizer.normalize(oldValue) != normalizer.normalize(newValue);
    }
  }
}
