package com.twitter.search.common.schema.earlybird;

import java.util.Map;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import com.twitter.common.text.util.TokenStreamSerializer;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.schema.AnalyzerFactory;
import com.twitter.search.common.schema.DynamicSchema;
import com.twitter.search.common.schema.ImmutableSchema;
import com.twitter.search.common.schema.SchemaBuilder;
import com.twitter.search.common.schema.base.FeatureConfiguration;
import com.twitter.search.common.schema.base.Schema;
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant;
import com.twitter.search.common.schema.thriftjava.ThriftCSFType;
import com.twitter.search.common.schema.thriftjava.ThriftFeatureUpdateConstraint;
import com.twitter.search.common.schema.thriftjava.ThriftSchema;

import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.BLINK_FAVORITE_COUNT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.BLINK_QUOTE_COUNT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.BLINK_REPLY_COUNT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.BLINK_RETWEET_COUNT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.COMPOSER_SOURCE_IS_CAMERA_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.DECAYED_FAVORITE_COUNT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.DECAYED_QUOTE_COUNT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.DECAYED_REPLY_COUNT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.DECAYED_RETWEET_COUNT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.EMBEDS_IMPRESSION_COUNT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.EMBEDS_IMPRESSION_COUNT_V2;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.EMBEDS_URL_COUNT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.EMBEDS_URL_COUNT_V2;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.EXPERIMENTAL_HEALTH_MODEL_SCORE_1;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.EXPERIMENTAL_HEALTH_MODEL_SCORE_2;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.EXPERIMENTAL_HEALTH_MODEL_SCORE_3;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.EXPERIMENTAL_HEALTH_MODEL_SCORE_4;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.EXTENDED_FEATURE_UNUSED_BITS_0_24_8;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.EXTENDED_TEST_FEATURE_UNUSED_BITS_12_30_2;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.EXTENDED_TEST_FEATURE_UNUSED_BITS_13_30_2;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.EXTENDED_TEST_FEATURE_UNUSED_BITS_14_10_22;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.EXTENDED_TEST_FEATURE_UNUSED_BITS_16;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.EXTENDED_TEST_FEATURE_UNUSED_BITS_17;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.EXTENDED_TEST_FEATURE_UNUSED_BITS_18;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.EXTENDED_TEST_FEATURE_UNUSED_BITS_19;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.EXTENDED_TEST_FEATURE_UNUSED_BITS_20;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.EXTENDED_TEST_FEATURE_UNUSED_BITS_4_31_1;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.EXTENDED_TEST_FEATURE_UNUSED_BITS_7_6_26;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.FAKE_FAVORITE_COUNT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.FAKE_QUOTE_COUNT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.FAKE_REPLY_COUNT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.FAKE_RETWEET_COUNT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.FAVORITE_COUNT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.FAVORITE_COUNT_V2;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.FROM_BLUE_VERIFIED_ACCOUNT_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.FROM_VERIFIED_ACCOUNT_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.HAS_CARD_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.HAS_CONSUMER_VIDEO_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.HAS_EXPANDO_CARD_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.HAS_IMAGE_URL_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.HAS_LINK_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.HAS_MULTIPLE_HASHTAGS_OR_TRENDS_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.HAS_MULTIPLE_MEDIA_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.HAS_NATIVE_IMAGE_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.HAS_NEWS_URL_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.HAS_PERISCOPE_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.HAS_PRO_VIDEO_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.HAS_QUOTE_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.HAS_TREND_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.HAS_VIDEO_URL_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.HAS_VINE_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.HAS_VISIBLE_LINK_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.IS_NULLCAST_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.IS_OFFENSIVE_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.IS_REPLY_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.IS_RETWEET_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.IS_SENSITIVE_CONTENT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.IS_TRENDING_NOW_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.IS_USER_BOT_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.IS_USER_NEW_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.IS_USER_NSFW_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.IS_USER_SPAM_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.LABEL_ABUSIVE_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.LABEL_ABUSIVE_HI_RCL_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.LABEL_DUP_CONTENT_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.LABEL_NSFW_HI_PRC_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.LABEL_NSFW_HI_RCL_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.LABEL_SPAM_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.LABEL_SPAM_HI_RCL_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.LANGUAGE;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.LAST_FAVORITE_SINCE_CREATION_HRS;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.LAST_QUOTE_SINCE_CREATION_HRS;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.LAST_REPLY_SINCE_CREATION_HRS;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.LAST_RETWEET_SINCE_CREATION_HRS;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.LINK_LANGUAGE;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.NORMALIZED_FAVORITE_COUNT_GREATER_THAN_OR_EQUAL_TO_FIELD;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.NORMALIZED_REPLY_COUNT_GREATER_THAN_OR_EQUAL_TO_FIELD;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.NORMALIZED_RETWEET_COUNT_GREATER_THAN_OR_EQUAL_TO_FIELD;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.NUM_HASHTAGS;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.NUM_HASHTAGS_V2;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.NUM_MENTIONS;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.NUM_MENTIONS_V2;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.NUM_STOCKS;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.PARUS_SCORE;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.PBLOCK_SCORE;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.PERISCOPE_EXISTS;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.PERISCOPE_HAS_BEEN_FEATURED;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.PERISCOPE_IS_CURRENTLY_FEATURED;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.PERISCOPE_IS_FROM_QUALITY_SOURCE;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.PERISCOPE_IS_LIVE;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.PREV_USER_TWEET_ENGAGEMENT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.PROFILE_IS_EGG_FLAG;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.P_REPORTED_TWEET_SCORE;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.P_SPAMMY_TWEET_SCORE;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.QUOTE_COUNT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.REFERENCE_AUTHOR_ID_LEAST_SIGNIFICANT_INT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.REFERENCE_AUTHOR_ID_MOST_SIGNIFICANT_INT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.REPLY_COUNT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.REPLY_COUNT_V2;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.RETWEET_COUNT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.RETWEET_COUNT_V2;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.SPAMMY_TWEET_CONTENT_SCORE;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.TEXT_SCORE;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.TOXICITY_SCORE;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.TWEET_SIGNATURE;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.USER_REPUTATION;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.VIDEO_VIEW_COUNT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.VIDEO_VIEW_COUNT_V2;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.VISIBLE_TOKEN_RATIO;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.WEIGHTED_FAVORITE_COUNT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.WEIGHTED_QUOTE_COUNT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.WEIGHTED_REPLY_COUNT;
import static com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant.WEIGHTED_RETWEET_COUNT;

/**
 * Field configurations for Earlybird.
 */
public final class EarlybirdSchemaCreateTool {
  // How many times a schema is built
  private static final SearchCounter SCHEMA_BUILD_COUNT =
      SearchCounter.export("schema_build_count");

  // Number of integers for the column of ENCODED_TWEET_FEATURES_FIELD.
  @VisibleForTesting
  public static final int NUMBER_OF_INTEGERS_FOR_FEATURES = 5;

  // Number of integers for the column of EXTENDED_ENCODED_TWEET_FEATURES_FIELD.
  // extra 80 bytes
  // In realtime cluster, assuming 19 segments total, and 8388608 docs per segment
  // this would amount to about 12.75GB of memory needed
  //
  @VisibleForTesting
  public static final int NUMBER_OF_INTEGERS_FOR_EXTENDED_FEATURES = 20;

  @VisibleForTesting
  public static final Map<String, FeatureConfiguration> FEATURE_CONFIGURATION_MAP
      = Maps.newLinkedHashMap();

  public static final String BASE_FIELD_NAME =
      EarlybirdFieldConstant.ENCODED_TWEET_FEATURES_FIELD.getFieldName();

  private static String getBaseFieldName(String fullName) {
    int index = fullName.indexOf(SchemaBuilder.CSF_VIEW_NAME_SEPARATOR);
    Preconditions.checkArgument(index > 0);
    return fullName.substring(0, index);
  }

  private static String getBaseFieldName(EarlybirdFieldConstant fieldConstant) {
    return getBaseFieldName(fieldConstant.getFieldName());
  }

  private static String getFeatureNameInField(EarlybirdFieldConstant fieldConstant) {
    int index = fieldConstant.getFieldName().indexOf(SchemaBuilder.CSF_VIEW_NAME_SEPARATOR);
    Preconditions.checkArgument(index > 0);
    return fieldConstant.getFieldName().substring(index + 1);
  }

  // defining all features
  static {
    // Add individual tweet encoded features as views on top of
    // EarlybirdFieldConstant.ENCODED_TWEET_FEATURES_FIELD

    // int intIndex, int bitStartPos, int bitLength
    newEarlybirdFeatureConfiguration(IS_RETWEET_FLAG, ThriftCSFType.BOOLEAN, 0, 0, 1);
    newEarlybirdFeatureConfiguration(IS_OFFENSIVE_FLAG, ThriftCSFType.BOOLEAN, 0, 1, 1);
    newEarlybirdFeatureConfiguration(HAS_LINK_FLAG, ThriftCSFType.BOOLEAN, 0, 2, 1);
    newEarlybirdFeatureConfiguration(HAS_TREND_FLAG, ThriftCSFType.BOOLEAN, 0, 3, 1);
    newEarlybirdFeatureConfiguration(IS_REPLY_FLAG, ThriftCSFType.BOOLEAN, 0, 4, 1);
    newEarlybirdFeatureConfiguration(IS_SENSITIVE_CONTENT, ThriftCSFType.BOOLEAN, 0, 5, 1);
    newEarlybirdFeatureConfiguration(HAS_MULTIPLE_HASHTAGS_OR_TRENDS_FLAG,
        ThriftCSFType.BOOLEAN, 0, 6, 1);
    newEarlybirdFeatureConfiguration(FROM_VERIFIED_ACCOUNT_FLAG, ThriftCSFType.BOOLEAN, 0, 7, 1);
    newEarlybirdFeatureConfiguration(TEXT_SCORE, ThriftCSFType.INT, 0, 8, 8);
    newEarlybirdFeatureConfiguration(LANGUAGE, ThriftCSFType.INT, 0, 16, 8);
    newEarlybirdFeatureConfiguration(LINK_LANGUAGE, ThriftCSFType.INT, 0, 24, 8);

    newEarlybirdFeatureConfiguration(HAS_IMAGE_URL_FLAG, ThriftCSFType.BOOLEAN, 1, 0, 1);
    newEarlybirdFeatureConfiguration(HAS_VIDEO_URL_FLAG, ThriftCSFType.BOOLEAN, 1, 1, 1);
    newEarlybirdFeatureConfiguration(HAS_NEWS_URL_FLAG, ThriftCSFType.BOOLEAN, 1, 2, 1);
    newEarlybirdFeatureConfiguration(HAS_EXPANDO_CARD_FLAG, ThriftCSFType.BOOLEAN, 1, 3, 1);
    newEarlybirdFeatureConfiguration(HAS_MULTIPLE_MEDIA_FLAG, ThriftCSFType.BOOLEAN, 1, 4, 1);
    newEarlybirdFeatureConfiguration(PROFILE_IS_EGG_FLAG, ThriftCSFType.BOOLEAN, 1, 5, 1);
    newEarlybirdFeatureConfiguration(NUM_MENTIONS, ThriftCSFType.INT, 1, 6, 2);     // 0, 1, 2, 3+
    newEarlybirdFeatureConfiguration(NUM_HASHTAGS, ThriftCSFType.INT, 1, 8, 2);     // 0, 1, 2, 3+
    newEarlybirdFeatureConfiguration(HAS_CARD_FLAG, ThriftCSFType.BOOLEAN, 1, 10, 1);
    newEarlybirdFeatureConfiguration(HAS_VISIBLE_LINK_FLAG, ThriftCSFType.BOOLEAN, 1, 11, 1);
    newEarlybirdFeatureConfiguration(USER_REPUTATION, ThriftCSFType.INT, 1, 12, 8);
    newEarlybirdFeatureConfiguration(IS_USER_SPAM_FLAG, ThriftCSFType.BOOLEAN, 1, 20, 1);
    newEarlybirdFeatureConfiguration(IS_USER_NSFW_FLAG, ThriftCSFType.BOOLEAN, 1, 21, 1);
    newEarlybirdFeatureConfiguration(IS_USER_BOT_FLAG, ThriftCSFType.BOOLEAN, 1, 22, 1);
    newEarlybirdFeatureConfiguration(IS_USER_NEW_FLAG, ThriftCSFType.BOOLEAN, 1, 23, 1);
    newEarlybirdFeatureConfiguration(PREV_USER_TWEET_ENGAGEMENT, ThriftCSFType.INT, 1, 24, 6);
    newEarlybirdFeatureConfiguration(COMPOSER_SOURCE_IS_CAMERA_FLAG,
        ThriftCSFType.BOOLEAN, 1, 30, 1);
    newEarlybirdFeatureConfiguration(IS_NULLCAST_FLAG, ThriftCSFType.BOOLEAN, 1, 31, 1);

    newEarlybirdFeatureConfiguration(RETWEET_COUNT, ThriftCSFType.DOUBLE, 2, 0, 8,
        ThriftFeatureUpdateConstraint.INC_ONLY);
    newEarlybirdFeatureConfiguration(FAVORITE_COUNT, ThriftCSFType.DOUBLE, 2, 8, 8,
        ThriftFeatureUpdateConstraint.INC_ONLY);
    newEarlybirdFeatureConfiguration(REPLY_COUNT, ThriftCSFType.DOUBLE, 2, 16, 8,
        ThriftFeatureUpdateConstraint.INC_ONLY);
    newEarlybirdFeatureConfiguration(PARUS_SCORE, ThriftCSFType.DOUBLE, 2, 24, 8);

    newEarlybirdFeatureConfiguration(HAS_CONSUMER_VIDEO_FLAG, ThriftCSFType.BOOLEAN, 3, 0, 1);
    newEarlybirdFeatureConfiguration(HAS_PRO_VIDEO_FLAG, ThriftCSFType.BOOLEAN, 3, 1, 1);
    newEarlybirdFeatureConfiguration(HAS_VINE_FLAG, ThriftCSFType.BOOLEAN, 3, 2, 1);
    newEarlybirdFeatureConfiguration(HAS_PERISCOPE_FLAG, ThriftCSFType.BOOLEAN, 3, 3, 1);
    newEarlybirdFeatureConfiguration(HAS_NATIVE_IMAGE_FLAG, ThriftCSFType.BOOLEAN, 3, 4, 1);
    // NOTE: There are 3 bits left in the first byte of INT 3, if possible, please reserve them
    // for future media types (SEARCH-9131)
    // newEarlybirdFeatureConfiguration(FUTURE_MEDIA_BITS, ThriftCSFType.INT, 3, 5, 3);

    newEarlybirdFeatureConfiguration(VISIBLE_TOKEN_RATIO, ThriftCSFType.INT, 3, 8, 4);
    newEarlybirdFeatureConfiguration(HAS_QUOTE_FLAG, ThriftCSFType.BOOLEAN, 3, 12, 1);
    newEarlybirdFeatureConfiguration(FROM_BLUE_VERIFIED_ACCOUNT_FLAG,
        ThriftCSFType.BOOLEAN, 3, 13, 1);
    // Unused bits from bit 14 to bit 31 (18 bits)
    // newEarlybirdFeatureConfiguration(UNUSED_BITS, ThriftCSFType.INT, 3, 14, 18);

    newEarlybirdFeatureConfiguration(TWEET_SIGNATURE, ThriftCSFType.INT, 4, 0, 32);

    newEarlybirdFeatureConfiguration(EMBEDS_IMPRESSION_COUNT,
        ThriftCSFType.DOUBLE, 0, 0, 8, ThriftFeatureUpdateConstraint.INC_ONLY);
    newEarlybirdFeatureConfiguration(EMBEDS_URL_COUNT,
        ThriftCSFType.DOUBLE, 0, 8, 8, ThriftFeatureUpdateConstraint.INC_ONLY);
    newEarlybirdFeatureConfiguration(VIDEO_VIEW_COUNT,
        ThriftCSFType.DOUBLE, 0, 16, 8, ThriftFeatureUpdateConstraint.INC_ONLY);

    // Unused bits from bit 24 to bit 31 (8 bits).
    // This used to be a feature that was decommissioned (SEARCHQUAL-10321)
    newEarlybirdFeatureConfiguration(EXTENDED_FEATURE_UNUSED_BITS_0_24_8,
        ThriftCSFType.INT, 0, 24, 8);

    newEarlybirdFeatureConfiguration(REFERENCE_AUTHOR_ID_LEAST_SIGNIFICANT_INT,
        ThriftCSFType.INT, 1, 0, 32, ThriftFeatureUpdateConstraint.IMMUTABLE);
    newEarlybirdFeatureConfiguration(REFERENCE_AUTHOR_ID_MOST_SIGNIFICANT_INT,
        ThriftCSFType.INT, 2, 0, 32, ThriftFeatureUpdateConstraint.IMMUTABLE);

    newEarlybirdFeatureConfiguration(RETWEET_COUNT_V2,
        ThriftCSFType.DOUBLE, 3, 0, 8, ThriftFeatureUpdateConstraint.INC_ONLY);
    newEarlybirdFeatureConfiguration(FAVORITE_COUNT_V2,
        ThriftCSFType.DOUBLE, 3, 8, 8, ThriftFeatureUpdateConstraint.INC_ONLY);
    newEarlybirdFeatureConfiguration(REPLY_COUNT_V2,
        ThriftCSFType.DOUBLE, 3, 16, 8, ThriftFeatureUpdateConstraint.INC_ONLY);
    newEarlybirdFeatureConfiguration(EMBEDS_IMPRESSION_COUNT_V2,
        ThriftCSFType.DOUBLE, 3, 24, 8, ThriftFeatureUpdateConstraint.INC_ONLY);

    newEarlybirdFeatureConfiguration(EMBEDS_URL_COUNT_V2,
        ThriftCSFType.DOUBLE, 4, 0, 8, ThriftFeatureUpdateConstraint.INC_ONLY);
    newEarlybirdFeatureConfiguration(VIDEO_VIEW_COUNT_V2,
        ThriftCSFType.DOUBLE, 4, 8, 8, ThriftFeatureUpdateConstraint.INC_ONLY);
    newEarlybirdFeatureConfiguration(QUOTE_COUNT,
        ThriftCSFType.DOUBLE, 4, 16, 8);

    newEarlybirdFeatureConfiguration(LABEL_ABUSIVE_FLAG,        ThriftCSFType.BOOLEAN, 4, 24, 1);
    newEarlybirdFeatureConfiguration(LABEL_ABUSIVE_HI_RCL_FLAG, ThriftCSFType.BOOLEAN, 4, 25, 1);
    newEarlybirdFeatureConfiguration(LABEL_DUP_CONTENT_FLAG,    ThriftCSFType.BOOLEAN, 4, 26, 1);
    newEarlybirdFeatureConfiguration(LABEL_NSFW_HI_PRC_FLAG,    ThriftCSFType.BOOLEAN, 4, 27, 1);
    newEarlybirdFeatureConfiguration(LABEL_NSFW_HI_RCL_FLAG,    ThriftCSFType.BOOLEAN, 4, 28, 1);
    newEarlybirdFeatureConfiguration(LABEL_SPAM_FLAG,           ThriftCSFType.BOOLEAN, 4, 29, 1);
    newEarlybirdFeatureConfiguration(LABEL_SPAM_HI_RCL_FLAG,    ThriftCSFType.BOOLEAN, 4, 30, 1);

    newEarlybirdFeatureConfiguration(EXTENDED_TEST_FEATURE_UNUSED_BITS_4_31_1,
        ThriftCSFType.INT, 4, 31, 1);

    newEarlybirdFeatureConfiguration(WEIGHTED_RETWEET_COUNT,
        ThriftCSFType.DOUBLE, 5, 0, 8, ThriftFeatureUpdateConstraint.INC_ONLY);
    newEarlybirdFeatureConfiguration(WEIGHTED_REPLY_COUNT,
        ThriftCSFType.DOUBLE, 5, 8, 8, ThriftFeatureUpdateConstraint.INC_ONLY);
    newEarlybirdFeatureConfiguration(WEIGHTED_FAVORITE_COUNT,
        ThriftCSFType.DOUBLE, 5, 16, 8, ThriftFeatureUpdateConstraint.INC_ONLY);
    newEarlybirdFeatureConfiguration(WEIGHTED_QUOTE_COUNT,
        ThriftCSFType.DOUBLE, 5, 24, 8, ThriftFeatureUpdateConstraint.INC_ONLY);

    newEarlybirdFeatureConfiguration(PERISCOPE_EXISTS,
        ThriftCSFType.BOOLEAN, 6, 0, 1);
    newEarlybirdFeatureConfiguration(PERISCOPE_HAS_BEEN_FEATURED,
        ThriftCSFType.BOOLEAN, 6, 1, 1);
    newEarlybirdFeatureConfiguration(PERISCOPE_IS_CURRENTLY_FEATURED,
        ThriftCSFType.BOOLEAN, 6, 2, 1);
    newEarlybirdFeatureConfiguration(PERISCOPE_IS_FROM_QUALITY_SOURCE,
        ThriftCSFType.BOOLEAN, 6, 3, 1);
    newEarlybirdFeatureConfiguration(PERISCOPE_IS_LIVE,
        ThriftCSFType.BOOLEAN, 6, 4, 1);

    newEarlybirdFeatureConfiguration(IS_TRENDING_NOW_FLAG,
        ThriftCSFType.BOOLEAN, 6, 5, 1);

    // remaining bits for integer 6
    newEarlybirdFeatureConfiguration(EXTENDED_TEST_FEATURE_UNUSED_BITS_7_6_26,
        ThriftCSFType.INT, 6, 6, 26);

    // The decaying counters can become smaller
    newEarlybirdFeatureConfiguration(DECAYED_RETWEET_COUNT,
        ThriftCSFType.DOUBLE, 7, 0, 8, ThriftFeatureUpdateConstraint.POSITIVE);
    newEarlybirdFeatureConfiguration(DECAYED_REPLY_COUNT,
        ThriftCSFType.DOUBLE, 7, 8, 8, ThriftFeatureUpdateConstraint.POSITIVE);
    newEarlybirdFeatureConfiguration(DECAYED_FAVORITE_COUNT,
        ThriftCSFType.DOUBLE, 7, 16, 8, ThriftFeatureUpdateConstraint.POSITIVE);
    newEarlybirdFeatureConfiguration(DECAYED_QUOTE_COUNT,
        ThriftCSFType.DOUBLE, 7, 24, 8, ThriftFeatureUpdateConstraint.POSITIVE);

    // The fake engagement counters.
    newEarlybirdFeatureConfiguration(FAKE_RETWEET_COUNT,
        ThriftCSFType.DOUBLE, 8, 0, 8, ThriftFeatureUpdateConstraint.POSITIVE);
    newEarlybirdFeatureConfiguration(FAKE_REPLY_COUNT,
        ThriftCSFType.DOUBLE, 8, 8, 8, ThriftFeatureUpdateConstraint.POSITIVE);
    newEarlybirdFeatureConfiguration(FAKE_FAVORITE_COUNT,
        ThriftCSFType.DOUBLE, 8, 16, 8, ThriftFeatureUpdateConstraint.POSITIVE);
    newEarlybirdFeatureConfiguration(FAKE_QUOTE_COUNT,
        ThriftCSFType.DOUBLE, 8, 24, 8, ThriftFeatureUpdateConstraint.POSITIVE);

    newEarlybirdFeatureConfiguration(LAST_RETWEET_SINCE_CREATION_HRS,
        ThriftCSFType.INT, 9, 0, 8, ThriftFeatureUpdateConstraint.INC_ONLY);
    newEarlybirdFeatureConfiguration(LAST_REPLY_SINCE_CREATION_HRS,
        ThriftCSFType.INT, 9, 8, 8, ThriftFeatureUpdateConstraint.INC_ONLY);
    newEarlybirdFeatureConfiguration(LAST_FAVORITE_SINCE_CREATION_HRS,
        ThriftCSFType.INT, 9, 16, 8, ThriftFeatureUpdateConstraint.INC_ONLY);
    newEarlybirdFeatureConfiguration(LAST_QUOTE_SINCE_CREATION_HRS,
        ThriftCSFType.INT, 9, 24, 8, ThriftFeatureUpdateConstraint.INC_ONLY);

    newEarlybirdFeatureConfiguration(NUM_HASHTAGS_V2,
        ThriftCSFType.INT, 10, 0, 4);
    newEarlybirdFeatureConfiguration(NUM_MENTIONS_V2,
        ThriftCSFType.INT, 10, 4, 4);
    newEarlybirdFeatureConfiguration(NUM_STOCKS,
        ThriftCSFType.INT, 10, 8, 4);

    // Remaining bits for integer 10
    // Production Toxicity and PBlock score from HML (go/toxicity, go/pblock)
    newEarlybirdFeatureConfiguration(TOXICITY_SCORE,
        ThriftCSFType.DOUBLE, 10, 12, 10);
    newEarlybirdFeatureConfiguration(PBLOCK_SCORE,
        ThriftCSFType.DOUBLE, 10, 22, 10);

    // The blink engagement counters
    newEarlybirdFeatureConfiguration(BLINK_RETWEET_COUNT,
        ThriftCSFType.DOUBLE, 11, 0, 8, ThriftFeatureUpdateConstraint.POSITIVE);
    newEarlybirdFeatureConfiguration(BLINK_REPLY_COUNT,
        ThriftCSFType.DOUBLE, 11, 8, 8, ThriftFeatureUpdateConstraint.POSITIVE);
    newEarlybirdFeatureConfiguration(BLINK_FAVORITE_COUNT,
        ThriftCSFType.DOUBLE, 11, 16, 8, ThriftFeatureUpdateConstraint.POSITIVE);
    newEarlybirdFeatureConfiguration(BLINK_QUOTE_COUNT,
        ThriftCSFType.DOUBLE, 11, 24, 8, ThriftFeatureUpdateConstraint.POSITIVE);

    // Experimental health model scores from HML
    newEarlybirdFeatureConfiguration(EXPERIMENTAL_HEALTH_MODEL_SCORE_1,
        ThriftCSFType.DOUBLE, 12, 0, 10);
    newEarlybirdFeatureConfiguration(EXPERIMENTAL_HEALTH_MODEL_SCORE_2,
        ThriftCSFType.DOUBLE, 12, 10, 10);
    newEarlybirdFeatureConfiguration(EXPERIMENTAL_HEALTH_MODEL_SCORE_3,
        ThriftCSFType.DOUBLE, 12, 20, 10);
    // remaining bits for integer 12
    newEarlybirdFeatureConfiguration(EXTENDED_TEST_FEATURE_UNUSED_BITS_12_30_2,
        ThriftCSFType.INT, 12, 30, 2);

    // Experimental health model scores from HML (cont.)
    newEarlybirdFeatureConfiguration(EXPERIMENTAL_HEALTH_MODEL_SCORE_4,
        ThriftCSFType.DOUBLE, 13, 0, 10);
    // Production pSpammyTweet score from HML (go/pspammytweet)
    newEarlybirdFeatureConfiguration(P_SPAMMY_TWEET_SCORE,
        ThriftCSFType.DOUBLE, 13, 10, 10);
    // Production pReportedTweet score from HML (go/preportedtweet)
    newEarlybirdFeatureConfiguration(P_REPORTED_TWEET_SCORE,
        ThriftCSFType.DOUBLE, 13, 20, 10);
    // remaining bits for integer 13
    newEarlybirdFeatureConfiguration(EXTENDED_TEST_FEATURE_UNUSED_BITS_13_30_2,
        ThriftCSFType.INT, 13, 30, 2);

    // Experimental health model scores from HML (cont.)
    // Prod Spammy Tweet Content model score from Platform Manipulation (go/spammy-tweet-content)
    newEarlybirdFeatureConfiguration(SPAMMY_TWEET_CONTENT_SCORE,
        ThriftCSFType.DOUBLE, 14, 0, 10);
    // remaining bits for integer 14
    newEarlybirdFeatureConfiguration(EXTENDED_TEST_FEATURE_UNUSED_BITS_14_10_22,
        ThriftCSFType.INT, 14, 10, 22);

    // Note that the integer index below is 0-based, but the index j in UNUSED_BITS_{j} below
    // is 1-based.
    newEarlybirdFeatureConfiguration(EXTENDED_TEST_FEATURE_UNUSED_BITS_16,
        ThriftCSFType.INT, 15, 0, 32);
    newEarlybirdFeatureConfiguration(EXTENDED_TEST_FEATURE_UNUSED_BITS_17,
        ThriftCSFType.INT, 16, 0, 32);
    newEarlybirdFeatureConfiguration(EXTENDED_TEST_FEATURE_UNUSED_BITS_18,
        ThriftCSFType.INT, 17, 0, 32);
    newEarlybirdFeatureConfiguration(EXTENDED_TEST_FEATURE_UNUSED_BITS_19,
        ThriftCSFType.INT, 18, 0, 32);
    newEarlybirdFeatureConfiguration(EXTENDED_TEST_FEATURE_UNUSED_BITS_20,
        ThriftCSFType.INT, 19, 0, 32);
  }

  private EarlybirdSchemaCreateTool() { }

  /**
   * Get schema for the Earlybird.
   */
  public static DynamicSchema buildSchema(EarlybirdCluster cluster)
      throws Schema.SchemaValidationException {
    SCHEMA_BUILD_COUNT.increment();
    return new DynamicSchema(new ImmutableSchema(buildThriftSchema(cluster),
                                                 new AnalyzerFactory(),
                                                 cluster.getNameForStats()));
  }

  /**
   * Get schema for the Earlybird, can throw runtime exception.  This is mostly for static schema
   * usage, which does not care about schema updates.
   */
  @VisibleForTesting
  public static DynamicSchema buildSchemaWithRuntimeException(EarlybirdCluster cluster) {
    try {
      return buildSchema(cluster);
    } catch (Schema.SchemaValidationException e) {
      throw new RuntimeException(e);
    }
  }

  private static FeatureConfiguration newEarlybirdFeatureConfiguration(
      EarlybirdFieldConstant fieldConstant,
      ThriftCSFType type,
      int intIndex, int bitStartPos, int bitLength,
      ThriftFeatureUpdateConstraint... constraints) {

    if (!fieldConstant.isFlagFeatureField() && type == ThriftCSFType.BOOLEAN) {
      throw new IllegalArgumentException(
          "Non-flag feature field configured with boolean Thrift type: " + fieldConstant);
    }
    if (fieldConstant.isFlagFeatureField() && type != ThriftCSFType.BOOLEAN) {
      throw new IllegalArgumentException(
          "Flag feature field configured with non-boolean Thrift type: " + fieldConstant);
    }

    String baseFieldName = getBaseFieldName(fieldConstant);
    String name = getFeatureNameInField(fieldConstant);
    FeatureConfiguration.Builder builder = FeatureConfiguration.builder()
        .withName(name)
        .withType(type)
        .withBitRange(intIndex, bitStartPos, bitLength);
    // remove the following line once we configure features purely by the schema
    builder.withBaseField(baseFieldName);

    if (!fieldConstant.isUnusedField()) {
      builder.withOutputType(type);
    }
    if (fieldConstant.getFeatureNormalizationType() != null) {
      builder.withFeatureNormalizationType(fieldConstant.getFeatureNormalizationType());
    }

    for (ThriftFeatureUpdateConstraint constraint : constraints) {
      builder.withFeatureUpdateConstraint(constraint);
    }
    FeatureConfiguration featureConfiguration = builder.build();
    FEATURE_CONFIGURATION_MAP.put(fieldConstant.getFieldName(), featureConfiguration);
    return featureConfiguration;
  }

  /**
   * Build ThriftSchema for the Earlybird. Note that the schema returned can be used
   * all Earlybird clusters. However, some clusters may not use all the field configurations.
   */
  @VisibleForTesting
  public static ThriftSchema buildThriftSchema(EarlybirdCluster cluster) {
    EarlybirdSchemaBuilder builder = new EarlybirdSchemaBuilder(
        new EarlybirdFieldConstants(), cluster, TokenStreamSerializer.Version.VERSION_2);

    builder.withSchemaVersion(
        FlushVersion.CURRENT_FLUSH_VERSION.getVersionNumber(),
        FlushVersion.CURRENT_FLUSH_VERSION.getMinorVersion(),
        FlushVersion.CURRENT_FLUSH_VERSION.getDescription(),
        FlushVersion.CURRENT_FLUSH_VERSION.isOfficial());

    // ID field, used for partitioning
    builder.withPartitionFieldId(0)
        .withSortableLongTermField(EarlybirdFieldConstant.ID_FIELD.getFieldName())
        // Text Fields that are searched by default
        .withTextField(EarlybirdFieldConstant.RESOLVED_LINKS_TEXT_FIELD.getFieldName(), true)
        .withSearchFieldByDefault(
            EarlybirdFieldConstant.RESOLVED_LINKS_TEXT_FIELD.getFieldName(), 0.1f)
        .withPretokenizedTextField(EarlybirdFieldConstant.TEXT_FIELD.getFieldName(), true)
        .withSearchFieldByDefault(EarlybirdFieldConstant.TEXT_FIELD.getFieldName(), 1.0f);
    builder.withTweetSpecificNormalization(EarlybirdFieldConstant.TEXT_FIELD.getFieldName())
        .withTextField(EarlybirdFieldConstant.TOKENIZED_FROM_USER_FIELD.getFieldName(), true)
        .withSearchFieldByDefault(
            EarlybirdFieldConstant.TOKENIZED_FROM_USER_FIELD.getFieldName(), 0.2f)

        // Text fields not searched by default
        .withTextField(EarlybirdFieldConstant.FROM_USER_FIELD.getFieldName(), false)
        .withTextField(EarlybirdFieldConstant.TO_USER_FIELD.getFieldName(), false)

        // cards are not searched by default, and have weight 0.
        .withPretokenizedTextField(EarlybirdFieldConstant.CARD_TITLE_FIELD.getFieldName(), false)
        .withPretokenizedTextField(
            EarlybirdFieldConstant.CARD_DESCRIPTION_FIELD.getFieldName(), false)
        .withTextField(EarlybirdFieldConstant.CARD_LANG.getFieldName(), false)

        // Out-of-order append fields
        .withLongTermField(EarlybirdFieldConstant.LIKED_BY_USER_ID_FIELD.getFieldName())
        .withLongTermField(EarlybirdFieldConstant.RETWEETED_BY_USER_ID.getFieldName())
        .withLongTermField(EarlybirdFieldConstant.REPLIED_TO_BY_USER_ID.getFieldName())

        // No Position fields, sorted alphabetically
        .withPretokenizedNoPositionField(EarlybirdFieldConstant.CARD_DOMAIN_FIELD.getFieldName())
        .withIndexedNotTokenizedField(EarlybirdFieldConstant.CARD_NAME_FIELD.getFieldName())
        .withIntTermField(EarlybirdFieldConstant.CREATED_AT_FIELD.getFieldName())
        .withIndexedNotTokenizedField(EarlybirdFieldConstant.ENTITY_ID_FIELD.getFieldName())
        .withIndexedNotTokenizedField(EarlybirdFieldConstant.GEO_HASH_FIELD.getFieldName())
        .withLongTermField(EarlybirdFieldConstant.FROM_USER_ID_FIELD.getFieldName())
        .withLongTermField(EarlybirdFieldConstant.IN_REPLY_TO_TWEET_ID_FIELD.getFieldName())
        .withLongTermField(EarlybirdFieldConstant.IN_REPLY_TO_USER_ID_FIELD.getFieldName())
        .withLongTermField(EarlybirdFieldConstant.RETWEET_SOURCE_TWEET_ID_FIELD.getFieldName())
        .withLongTermField(EarlybirdFieldConstant.RETWEET_SOURCE_USER_ID_FIELD.getFieldName())
        .withLongTermField(EarlybirdFieldConstant.CONVERSATION_ID_FIELD.getFieldName())
        .withIndexedNotTokenizedField(EarlybirdFieldConstant.PLACE_ID_FIELD.getFieldName())
        .withTextField(EarlybirdFieldConstant.PLACE_FULL_NAME_FIELD.getFieldName(), false)
        .withIndexedNotTokenizedField(
            EarlybirdFieldConstant.PLACE_COUNTRY_CODE_FIELD.getFieldName())
        .withIndexedNotTokenizedField(
            EarlybirdFieldConstant.PROFILE_GEO_COUNTRY_CODE_FIELD.getFieldName())
        .withTextField(EarlybirdFieldConstant.PROFILE_GEO_REGION_FIELD.getFieldName(), false)
        .withTextField(EarlybirdFieldConstant.PROFILE_GEO_LOCALITY_FIELD.getFieldName(), false)
        .withTermTextLookup(EarlybirdFieldConstant.FROM_USER_ID_FIELD.getFieldName())
        .withTermTextLookup(EarlybirdFieldConstant.IN_REPLY_TO_USER_ID_FIELD.getFieldName())
        .withPretokenizedNoPositionField(EarlybirdFieldConstant.HASHTAGS_FIELD.getFieldName())
        .withIndexedNotTokenizedField(ImmutableSchema.HF_PHRASE_PAIRS_FIELD)
        .withIndexedNotTokenizedField(ImmutableSchema.HF_TERM_PAIRS_FIELD)
        .withIndexedNotTokenizedField(EarlybirdFieldConstant.IMAGE_LINKS_FIELD.getFieldName())
        .withIndexedNotTokenizedField(EarlybirdFieldConstant.INTERNAL_FIELD.getFieldName())
        .withIndexedNotTokenizedField(EarlybirdFieldConstant.ISO_LANGUAGE_FIELD.getFieldName())
        .withIndexedNotTokenizedField(EarlybirdFieldConstant.LINKS_FIELD.getFieldName())
        .withIntTermField(EarlybirdFieldConstant.LINK_CATEGORY_FIELD.getFieldName())
        .withIndexedNotTokenizedField(EarlybirdFieldConstant.MENTIONS_FIELD.getFieldName())
        .withIndexedNotTokenizedField(EarlybirdFieldConstant.NEWS_LINKS_FIELD.getFieldName())
        .withIndexedNotTokenizedField(EarlybirdFieldConstant.NORMALIZED_SOURCE_FIELD.getFieldName())
        .withIndexedNotTokenizedField(EarlybirdFieldConstant.PLACE_FIELD.getFieldName())
        .withIndexedNotTokenizedField(EarlybirdFieldConstant.SOURCE_FIELD.getFieldName())
        .withPretokenizedNoPositionField(EarlybirdFieldConstant.STOCKS_FIELD.getFieldName())
        .withIndexedNotTokenizedField(EarlybirdFieldConstant.VIDEO_LINKS_FIELD.getFieldName())
        .withIntTermField(NORMALIZED_FAVORITE_COUNT_GREATER_THAN_OR_EQUAL_TO_FIELD.getFieldName())
        .withIntTermField(NORMALIZED_REPLY_COUNT_GREATER_THAN_OR_EQUAL_TO_FIELD.getFieldName())
        .withIntTermField(NORMALIZED_RETWEET_COUNT_GREATER_THAN_OR_EQUAL_TO_FIELD.getFieldName())

        .withIntTermField(EarlybirdFieldConstant.COMPOSER_SOURCE.getFieldName())

        .withLongTermField(EarlybirdFieldConstant.QUOTED_TWEET_ID_FIELD.getFieldName())
        .withLongTermField(EarlybirdFieldConstant.QUOTED_USER_ID_FIELD.getFieldName())
        .withLongTermField(EarlybirdFieldConstant.DIRECTED_AT_USER_ID_FIELD.getFieldName())

        // Named entity fields
        .withIndexedNotTokenizedField(
            EarlybirdFieldConstant.NAMED_ENTITY_FROM_URL_FIELD.getFieldName(), true)
        .withIndexedNotTokenizedField(
            EarlybirdFieldConstant.NAMED_ENTITY_FROM_TEXT_FIELD.getFieldName(), true)
        .withIndexedNotTokenizedField(
            EarlybirdFieldConstant.NAMED_ENTITY_WITH_TYPE_FROM_URL_FIELD.getFieldName(), true)
        .withIndexedNotTokenizedField(
            EarlybirdFieldConstant.NAMED_ENTITY_WITH_TYPE_FROM_TEXT_FIELD.getFieldName(), true)

        // camelCase-tokenized user handles and tokenized user names, not searchable by default
        .withPretokenizedTextField(
            EarlybirdFieldConstant.CAMELCASE_USER_HANDLE_FIELD.getFieldName(), false)
        .withPretokenizedTextField(
            EarlybirdFieldConstant.TOKENIZED_USER_NAME_FIELD.getFieldName(), false)

        .withIndexedNotTokenizedField(
            EarlybirdFieldConstant.SPACE_ID_FIELD.getFieldName())
        .withTextField(EarlybirdFieldConstant.SPACE_ADMIN_FIELD.getFieldName(), false)
        .withPretokenizedTextField(EarlybirdFieldConstant.SPACE_TITLE_FIELD.getFieldName(), false)
        .withTextField(EarlybirdFieldConstant.TOKENIZED_SPACE_ADMIN_FIELD.getFieldName(), true)
        .withPretokenizedTextField(
            EarlybirdFieldConstant.CAMELCASE_TOKENIZED_SPACE_ADMIN_FIELD.getFieldName(), false)
        .withPretokenizedTextField(
            EarlybirdFieldConstant.TOKENIZED_SPACE_ADMIN_DISPLAY_NAME_FIELD.getFieldName(), false)
        .withPretokenizedTextField(
            EarlybirdFieldConstant.URL_DESCRIPTION_FIELD.getFieldName(), false)
        .withPretokenizedTextField(
            EarlybirdFieldConstant.URL_TITLE_FIELD.getFieldName(), false);

    builder
        .withPhotoUrlFacetField(EarlybirdFieldConstant.TWIMG_LINKS_FIELD.getFieldName())
        .withOutOfOrderEnabledForField(
            EarlybirdFieldConstant.LIKED_BY_USER_ID_FIELD.getFieldName())
        .withOutOfOrderEnabledForField(
            EarlybirdFieldConstant.RETWEETED_BY_USER_ID.getFieldName())
        .withOutOfOrderEnabledForField(
            EarlybirdFieldConstant.REPLIED_TO_BY_USER_ID.getFieldName());

    // ColumnStrideFields.
    boolean loadCSFIntoRAMDefault = cluster != EarlybirdCluster.FULL_ARCHIVE;

    builder
        .withColumnStrideField(EarlybirdFieldConstants.ENCODED_TWEET_FEATURES_FIELD_NAME,
                ThriftCSFType.INT, NUMBER_OF_INTEGERS_FOR_FEATURES,
                true, loadCSFIntoRAMDefault)
        .withColumnStrideField(EarlybirdFieldConstant.FROM_USER_ID_CSF.getFieldName(),
            ThriftCSFType.LONG, 1, false, /* the full archive loads this field into RAM */ true)
        .withColumnStrideField(EarlybirdFieldConstant.SHARED_STATUS_ID_CSF.getFieldName(),
                ThriftCSFType.LONG, 1, false, loadCSFIntoRAMDefault)
        .withColumnStrideField(EarlybirdFieldConstant.CARD_TYPE_CSF_FIELD.getFieldName(),
                ThriftCSFType.BYTE, 1, false, loadCSFIntoRAMDefault)
         // CSF Used by archive mappers
        .withColumnStrideField(EarlybirdFieldConstant.CREATED_AT_CSF_FIELD.getFieldName(),
            ThriftCSFType.INT, 1, false, /* the full archive loads this field into RAM */ true)
        .withColumnStrideField(EarlybirdFieldConstant.ID_CSF_FIELD.getFieldName(),
            ThriftCSFType.LONG, 1, false, /* the full archive loads this field into RAM */ true)
        .withColumnStrideField(EarlybirdFieldConstant.LAT_LON_CSF_FIELD.getFieldName(),
            ThriftCSFType.LONG, 1, false, loadCSFIntoRAMDefault)
        .withColumnStrideField(EarlybirdFieldConstant.CONVERSATION_ID_CSF.getFieldName(),
            ThriftCSFType.LONG, 1, false, loadCSFIntoRAMDefault)
        .withColumnStrideField(EarlybirdFieldConstant.QUOTED_TWEET_ID_CSF.getFieldName(),
            ThriftCSFType.LONG, 1, false, loadCSFIntoRAMDefault)
        .withColumnStrideField(EarlybirdFieldConstant.QUOTED_USER_ID_CSF.getFieldName(),
            ThriftCSFType.LONG, 1, false, loadCSFIntoRAMDefault)
        .withColumnStrideField(EarlybirdFieldConstant.CARD_LANG_CSF.getFieldName(),
            ThriftCSFType.INT, 1, false, loadCSFIntoRAMDefault)
        .withColumnStrideField(EarlybirdFieldConstant.CARD_URI_CSF.getFieldName(),
            ThriftCSFType.LONG, 1, false, loadCSFIntoRAMDefault)
        .withColumnStrideField(EarlybirdFieldConstant.DIRECTED_AT_USER_ID_CSF.getFieldName(),
            ThriftCSFType.LONG, 1, false, loadCSFIntoRAMDefault)
        .withColumnStrideField(EarlybirdFieldConstant.REFERENCE_AUTHOR_ID_CSF.getFieldName(),
            ThriftCSFType.LONG, 1, false, loadCSFIntoRAMDefault)
        .withColumnStrideField(
            EarlybirdFieldConstant.EXCLUSIVE_CONVERSATION_AUTHOR_ID_CSF.getFieldName(),
            ThriftCSFType.LONG, 1, false, loadCSFIntoRAMDefault)

    /* Semicolon on separate line to preserve git blame. */;

    builder.withColumnStrideField(
        EarlybirdFieldConstants.EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        ThriftCSFType.INT, NUMBER_OF_INTEGERS_FOR_EXTENDED_FEATURES,
        true, loadCSFIntoRAMDefault);

    for (Map.Entry<String, FeatureConfiguration> entry : FEATURE_CONFIGURATION_MAP.entrySet()) {
      String fullName = entry.getKey();
      String baseName = getBaseFieldName(fullName);
      EarlybirdFieldConstant fieldConstant = EarlybirdFieldConstants.getFieldConstant(fullName);
      if (fieldConstant.isValidFieldInCluster(cluster)) {
        builder.withFeatureConfiguration(baseName, fullName, entry.getValue());
      }
    }
    // Add facet settings for facet fields
    // boolean args are respectively whether to use skiplist, whether offensive, whether to use CSF
    builder
        .withFacetConfigs(EarlybirdFieldConstant.MENTIONS_FIELD.getFieldName(),
            EarlybirdFieldConstant.MENTIONS_FACET, true, false, false)
        .withFacetConfigs(EarlybirdFieldConstant.HASHTAGS_FIELD.getFieldName(),
            EarlybirdFieldConstant.HASHTAGS_FACET, true, false, false)
        .withFacetConfigs(EarlybirdFieldConstant.STOCKS_FIELD.getFieldName(),
            EarlybirdFieldConstant.STOCKS_FACET, true, false, false)
        .withFacetConfigs(EarlybirdFieldConstant.IMAGE_LINKS_FIELD.getFieldName(),
            EarlybirdFieldConstant.IMAGES_FACET, true, true, false)
        .withFacetConfigs(EarlybirdFieldConstant.VIDEO_LINKS_FIELD.getFieldName(),
            EarlybirdFieldConstant.VIDEOS_FACET, true, true, false)
        .withFacetConfigs(EarlybirdFieldConstant.NEWS_LINKS_FIELD.getFieldName(),
            EarlybirdFieldConstant.NEWS_FACET, true, false, false)
        .withFacetConfigs(EarlybirdFieldConstant.ISO_LANGUAGE_FIELD.getFieldName(),
            EarlybirdFieldConstant.LANGUAGES_FACET, false, false, false)
        .withFacetConfigs(EarlybirdFieldConstant.SOURCE_FIELD.getFieldName(),
            EarlybirdFieldConstant.SOURCES_FACET, false, false, false)
        .withFacetConfigs(EarlybirdFieldConstant.TWIMG_LINKS_FIELD.getFieldName(),
            EarlybirdFieldConstant.TWIMG_FACET, true, true, false)
        .withFacetConfigs(EarlybirdFieldConstant.FROM_USER_ID_CSF.getFieldName(),
            EarlybirdFieldConstant.FROM_USER_ID_FACET, false, false, true /* facet on CSF */)
        .withFacetConfigs(EarlybirdFieldConstant.SHARED_STATUS_ID_CSF.getFieldName(),
            EarlybirdFieldConstant.RETWEETS_FACET, false, false, true /* facet on CSF */)
        .withFacetConfigs(EarlybirdFieldConstant.LINKS_FIELD.getFieldName(),
            EarlybirdFieldConstant.LINKS_FACET, true, false, false)
        .withFacetConfigs(
            EarlybirdFieldConstant.NAMED_ENTITY_WITH_TYPE_FROM_URL_FIELD.getFieldName(),
            true, false, false)
        .withFacetConfigs(
            EarlybirdFieldConstant.NAMED_ENTITY_WITH_TYPE_FROM_TEXT_FIELD.getFieldName(),
            true, false, false)
        .withFacetConfigs(
            EarlybirdFieldConstant.ENTITY_ID_FIELD.getFieldName(),
            true, false, false)
        .withFacetConfigs(EarlybirdFieldConstant.SPACE_ID_FIELD.getFieldName(),
            EarlybirdFieldConstant.SPACES_FACET, true, false, false);
    return builder.build();
  }
}
