
package com.twitter.search.common.schema.earlybird;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import com.twitter.search.common.indexing.thriftjava.ThriftGeoLocationSource;
import com.twitter.search.common.schema.ImmutableSchema;
import com.twitter.search.common.schema.SchemaBuilder;
import com.twitter.search.common.schema.base.FeatureConfiguration;
import com.twitter.search.common.schema.base.FieldNameToIdMapping;
import com.twitter.search.common.schema.thriftjava.ThriftFeatureNormalizationType;

/**
 * Field names, field IDs etc.
 */
public class EarlybirdFieldConstants extends FieldNameToIdMapping {
  @VisibleForTesting
  public static final String ENCODED_TWEET_FEATURES_FIELD_NAME = "encoded_tweet_features";

  @VisibleForTesting
  public static final String EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME =
      "extended_encoded_tweet_features";

  private enum FlagFeatureFieldType {
    NON_FLAG_FEATURE_FIELD,
    FLAG_FEATURE_FIELD
  }

  private enum UnusedFeatureFieldType {
    USED_FEATURE_FIELD,
    UNUSED_FEATURE_FIELD
  }

  /**
   * CSF_NAME_TO_MIN_ENGAGEMENT_FIELD_MAP and MIN_ENGAGEMENT_FIELD_TO_CSF_NAME_MAP are used in
   * EarlybirdLuceneQueryVisitor to map the CSFs REPLY_COUNT, RETWEET_COUNT, and FAVORITE_COUNT to
   * their respective min engagement fields, and vice versa.
   */
  public static final ImmutableMap<String, EarlybirdFieldConstant>
      CSF_NAME_TO_MIN_ENGAGEMENT_FIELD_MAP = ImmutableMap.<String, EarlybirdFieldConstant>builder()
          .put(EarlybirdFieldConstant.REPLY_COUNT.getFieldName(),
              EarlybirdFieldConstant.NORMALIZED_REPLY_COUNT_GREATER_THAN_OR_EQUAL_TO_FIELD)
          .put(EarlybirdFieldConstant.RETWEET_COUNT.getFieldName(),
              EarlybirdFieldConstant.NORMALIZED_RETWEET_COUNT_GREATER_THAN_OR_EQUAL_TO_FIELD)
          .put(EarlybirdFieldConstant.FAVORITE_COUNT.getFieldName(),
              EarlybirdFieldConstant.NORMALIZED_FAVORITE_COUNT_GREATER_THAN_OR_EQUAL_TO_FIELD)
          .build();

  public static final ImmutableMap<String, EarlybirdFieldConstant>
      MIN_ENGAGEMENT_FIELD_TO_CSF_NAME_MAP = ImmutableMap.<String, EarlybirdFieldConstant>builder()
      .put(EarlybirdFieldConstant.NORMALIZED_REPLY_COUNT_GREATER_THAN_OR_EQUAL_TO_FIELD
              .getFieldName(),
          EarlybirdFieldConstant.REPLY_COUNT)
      .put(EarlybirdFieldConstant.NORMALIZED_RETWEET_COUNT_GREATER_THAN_OR_EQUAL_TO_FIELD
              .getFieldName(),
          EarlybirdFieldConstant.RETWEET_COUNT)
      .put(EarlybirdFieldConstant.NORMALIZED_FAVORITE_COUNT_GREATER_THAN_OR_EQUAL_TO_FIELD
              .getFieldName(),
          EarlybirdFieldConstant.FAVORITE_COUNT)
      .build();

  /**
   * A list of Earlybird field names and field IDs, and the clusters that need them.
   */
  public enum EarlybirdFieldConstant {
    // These enums are grouped by category and sorted alphabetically.
    // Next indexed field ID is 420
    // Next CSF field ID is 420
    // Next encoded_features CSF field ID is 420
    // Next extended_encoded_features CSF field ID is 420

    // Text searchable fields
    // Provides slow ID Mapping from tweet ID to doc ID through TermsEnum.seekExact().
    ID_FIELD("id", 420, EarlybirdCluster.ALL_CLUSTERS),
    RESOLVED_LINKS_TEXT_FIELD("resolved_links_text", 420),
    TEXT_FIELD("text", 420),
    TOKENIZED_FROM_USER_FIELD("tokenized_from_user", 420),

    // Other indexed fields
    CARD_TITLE_FIELD("card_title", 420),
    CARD_DESCRIPTION_FIELD("card_description", 420),
    // We require the createdAt field to be set so we can properly filter tweets based on time.
    CREATED_AT_FIELD("created_at", 420, EarlybirdCluster.ALL_CLUSTERS),
    // 420 was formerly EVENT_IDS_FIELD("event_ids", 420, EarlybirdCluster.REALTIME)
    ENTITY_ID_FIELD("entity_id", 420),
    // The screen name of the user that created the tweet. Should be set to the normalized value in
    // the com.twitter.gizmoduck.thriftjava.Profile.screen_name field.
    FROM_USER_FIELD("from_user", 420),
    // The numeric ID of the user that created the tweet.
    FROM_USER_ID_FIELD("from_user_id", 420, EarlybirdCluster.ALL_CLUSTERS),
    CARD_DOMAIN_FIELD("card_domain", 420),
    CARD_NAME_FIELD("card_name", 420),
    GEO_HASH_FIELD("geo_hash", 420),
    HASHTAGS_FIELD("hashtags", 420),
    HF_PHRASE_PAIRS_FIELD(ImmutableSchema.HF_PHRASE_PAIRS_FIELD, 420),
    HF_TERM_PAIRS_FIELD(ImmutableSchema.HF_TERM_PAIRS_FIELD, 420),
    IMAGE_LINKS_FIELD("image_links", 420),
    IN_REPLY_TO_TWEET_ID_FIELD("in_reply_to_tweet_id", 420),
    IN_REPLY_TO_USER_ID_FIELD("in_reply_to_user_id", 420),
    // The internal field is used for many purposes:
    // 420. to store facet skiplists
    // 420. to power the filter operator, by storing posting list for terms like __filter_twimg
    // 420. to store posting lists for positive and negative smileys
    // 420. to store geo location types.
    // etc.
    INTERNAL_FIELD("internal", 420, EarlybirdCluster.ALL_CLUSTERS),
    ISO_LANGUAGE_FIELD("iso_lang", 420),
    LINK_CATEGORY_FIELD("link_category", 420),
    LINKS_FIELD("links", 420),
    MENTIONS_FIELD("mentions", 420),
    // Field 420 used to be NAMED_ENTITIES_FIELD
    NEWS_LINKS_FIELD("news_links", 420),
    NORMALIZED_SOURCE_FIELD("norm_source", 420),
    PLACE_FIELD("place", 420),
    // Field 420 used to be PUBLICLY_INFERRED_USER_LOCATION_PLACE_ID_FIELD
    // The ID of the source tweet. Set for retweets only.
    RETWEET_SOURCE_TWEET_ID_FIELD("retweet_source_tweet_id", 420,
        EarlybirdCluster.ALL_CLUSTERS),
    // The ID of the source tweet's author. Set for retweets only.
    RETWEET_SOURCE_USER_ID_FIELD("retweet_source_user_id", 420),
    SOURCE_FIELD("source", 420),
    STOCKS_FIELD("stocks", 420),
    // The screen name of the user that a tweet was directed at.
    TO_USER_FIELD("to_user", 420),
    // Field 420 used to be TOPIC_IDS_FIELD and is now unused. It can be reused later.
    TWIMG_LINKS_FIELD("twimg_links", 420),
    VIDEO_LINKS_FIELD("video_links", 420),
    CAMELCASE_USER_HANDLE_FIELD("camelcase_tokenized_from_user", 420),
    // This field should be set to the the tokenized and normalized value in the
    // com.twitter.gizmoduck.thriftjava.Profile.name field.
    TOKENIZED_USER_NAME_FIELD("tokenized_from_user_display_name", 420),
    CONVERSATION_ID_FIELD("conversation_id", 420),
    PLACE_ID_FIELD("place_id", 420),
    PLACE_FULL_NAME_FIELD("place_full_name", 420),
    PLACE_COUNTRY_CODE_FIELD("place_country_code", 420),
    PROFILE_GEO_COUNTRY_CODE_FIELD("profile_geo_country_code", 420),
    PROFILE_GEO_REGION_FIELD("profile_geo_region", 420),
    PROFILE_GEO_LOCALITY_FIELD("profile_geo_locality", 420),
    LIKED_BY_USER_ID_FIELD("liked_by_user_id", 420, EarlybirdCluster.REALTIME),
    NORMALIZED_REPLY_COUNT_GREATER_THAN_OR_EQUAL_TO_FIELD(
        "normalized_reply_count_greater_than_or_equal_to", 420, EarlybirdCluster.FULL_ARCHIVE),
    NORMALIZED_RETWEET_COUNT_GREATER_THAN_OR_EQUAL_TO_FIELD(
        "normalized_retweet_count_greater_than_or_equal_to", 420, EarlybirdCluster.FULL_ARCHIVE),
    NORMALIZED_FAVORITE_COUNT_GREATER_THAN_OR_EQUAL_TO_FIELD(
        "normalized_favorite_count_greater_than_or_equal_to", 420, EarlybirdCluster.FULL_ARCHIVE),
    COMPOSER_SOURCE("composer_source", 420),
    QUOTED_TWEET_ID_FIELD("quoted_tweet_id", 420),
    QUOTED_USER_ID_FIELD("quoted_user_id", 420),
    RETWEETED_BY_USER_ID("retweeted_by_user_id", 420, EarlybirdCluster.REALTIME),
    REPLIED_TO_BY_USER_ID("replied_to_by_user_id", 420, EarlybirdCluster.REALTIME),
    CARD_LANG("card_lang", 420),
    // SEARCH-420: Field ID 420 used to be named_entity, which was the combination of all
    // named_entity* fields below. We need to leave 420 unused for backwards compatibility.
    NAMED_ENTITY_FROM_URL_FIELD("named_entity_from_url", 420),
    NAMED_ENTITY_FROM_TEXT_FIELD("named_entity_from_text", 420),
    NAMED_ENTITY_WITH_TYPE_FROM_URL_FIELD("named_entity_with_type_from_url", 420),
    NAMED_ENTITY_WITH_TYPE_FROM_TEXT_FIELD("named_entity_with_type_from_text", 420),
    DIRECTED_AT_USER_ID_FIELD("directed_at_user_id", 420),
    SPACE_ID_FIELD("space_id", 420,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_GENERAL_PURPOSE_CLUSTERS),
    SPACE_TITLE_FIELD("space_title", 420,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_GENERAL_PURPOSE_CLUSTERS),

    // Detailed description of the space admin fields can be found at go/earlybirdfields.
    SPACE_ADMIN_FIELD("space_admin", 420,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_GENERAL_PURPOSE_CLUSTERS),
    TOKENIZED_SPACE_ADMIN_FIELD("tokenized_space_admin", 420,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_GENERAL_PURPOSE_CLUSTERS),
    CAMELCASE_TOKENIZED_SPACE_ADMIN_FIELD("camelcase_tokenized_space_admin", 420,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_GENERAL_PURPOSE_CLUSTERS),
    TOKENIZED_SPACE_ADMIN_DISPLAY_NAME_FIELD("tokenized_space_admin_display_name", 420,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_GENERAL_PURPOSE_CLUSTERS),
    URL_DESCRIPTION_FIELD("url_description", 420),
    URL_TITLE_FIELD("url_title", 420),

    // CSF
    CARD_TYPE_CSF_FIELD("card_type_csf", 420),
    ENCODED_TWEET_FEATURES_FIELD(ENCODED_TWEET_FEATURES_FIELD_NAME, 420,
        EarlybirdCluster.ALL_CLUSTERS),
    // Provides the doc ID -> original tweet ID mapping for retweets.
    SHARED_STATUS_ID_CSF("shared_status_id_csf", 420, EarlybirdCluster.ALL_CLUSTERS),
    // Provides the doc ID -> tweet author's user ID mapping.
    FROM_USER_ID_CSF("from_user_id_csf", 420, EarlybirdCluster.ALL_CLUSTERS),
    CREATED_AT_CSF_FIELD("created_at_csf", 420, EarlybirdCluster.ARCHIVE_CLUSTERS),
    // Provides the doc ID -> tweet ID mapping.
    ID_CSF_FIELD("id_csf", 420, EarlybirdCluster.ARCHIVE_CLUSTERS),
    LAT_LON_CSF_FIELD("latlon_csf", 420),
    CONVERSATION_ID_CSF("conversation_id_csf", 420, EarlybirdCluster.ALL_CLUSTERS),
    QUOTED_TWEET_ID_CSF("quoted_tweet_id_csf", 420),
    QUOTED_USER_ID_CSF("quoted_user_id_csf", 420),
    CARD_LANG_CSF("card_lang_csf", 420),
    DIRECTED_AT_USER_ID_CSF("directed_at_user_id_csf", 420),
    REFERENCE_AUTHOR_ID_CSF("reference_author_id_csf", 420),
    EXCLUSIVE_CONVERSATION_AUTHOR_ID_CSF("exclusive_conversation_author_id_csf", 420),
    CARD_URI_CSF("card_uri_csf", 420),

    // CSF Views on top of ENCODED_TWEET_FEATURES_FIELD
    IS_RETWEET_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "IS_RETWEET_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    IS_OFFENSIVE_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "IS_OFFENSIVE_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    HAS_LINK_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "HAS_LINK_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    HAS_TREND_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "HAS_TREND_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    IS_REPLY_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "IS_REPLY_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    IS_SENSITIVE_CONTENT(ENCODED_TWEET_FEATURES_FIELD_NAME, "IS_SENSITIVE_CONTENT", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    HAS_MULTIPLE_HASHTAGS_OR_TRENDS_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME,
        "HAS_MULTIPLE_HASHTAGS_OR_TRENDS_FLAG", 420, FlagFeatureFieldType.FLAG_FEATURE_FIELD,
        EarlybirdCluster.ALL_CLUSTERS),
    FROM_VERIFIED_ACCOUNT_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "FROM_VERIFIED_ACCOUNT_FLAG",
        420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    TEXT_SCORE(ENCODED_TWEET_FEATURES_FIELD_NAME, "TEXT_SCORE", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    LANGUAGE(ENCODED_TWEET_FEATURES_FIELD_NAME, "LANGUAGE", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    LINK_LANGUAGE(ENCODED_TWEET_FEATURES_FIELD_NAME, "LINK_LANGUAGE", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    HAS_IMAGE_URL_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "HAS_IMAGE_URL_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    HAS_VIDEO_URL_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "HAS_VIDEO_URL_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    HAS_NEWS_URL_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "HAS_NEWS_URL_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    HAS_EXPANDO_CARD_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "HAS_EXPANDO_CARD_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    HAS_MULTIPLE_MEDIA_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "HAS_MULTIPLE_MEDIA_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    PROFILE_IS_EGG_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "PROFILE_IS_EGG_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    NUM_MENTIONS(ENCODED_TWEET_FEATURES_FIELD_NAME, "NUM_MENTIONS", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    NUM_HASHTAGS(ENCODED_TWEET_FEATURES_FIELD_NAME, "NUM_HASHTAGS", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    HAS_CARD_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "HAS_CARD_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    HAS_VISIBLE_LINK_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "HAS_VISIBLE_LINK_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    USER_REPUTATION(ENCODED_TWEET_FEATURES_FIELD_NAME, "USER_REPUTATION", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    IS_USER_SPAM_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "IS_USER_SPAM_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    IS_USER_NSFW_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "IS_USER_NSFW_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    IS_USER_BOT_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "IS_USER_BOT_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    IS_USER_NEW_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "IS_USER_NEW_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    PREV_USER_TWEET_ENGAGEMENT(ENCODED_TWEET_FEATURES_FIELD_NAME, "PREV_USER_TWEET_ENGAGEMENT",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    COMPOSER_SOURCE_IS_CAMERA_FLAG(
        ENCODED_TWEET_FEATURES_FIELD_NAME,
        "COMPOSER_SOURCE_IS_CAMERA_FLAG",
        420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD,
        EarlybirdCluster.ALL_CLUSTERS),
    RETWEET_COUNT(
        ENCODED_TWEET_FEATURES_FIELD_NAME,
        "RETWEET_COUNT",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.ALL_CLUSTERS,
        ThriftFeatureNormalizationType.LEGACY_BYTE_NORMALIZER_WITH_LOG420),
    FAVORITE_COUNT(
        ENCODED_TWEET_FEATURES_FIELD_NAME,
        "FAVORITE_COUNT",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.ALL_CLUSTERS,
        ThriftFeatureNormalizationType.LEGACY_BYTE_NORMALIZER_WITH_LOG420),
    REPLY_COUNT(
        ENCODED_TWEET_FEATURES_FIELD_NAME,
        "REPLY_COUNT",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.ALL_CLUSTERS,
        ThriftFeatureNormalizationType.LEGACY_BYTE_NORMALIZER_WITH_LOG420),
    PARUS_SCORE(ENCODED_TWEET_FEATURES_FIELD_NAME, "PARUS_SCORE", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),

    /**
     * This is the rough percentage of the nth token at 420 divided by num tokens
     * and is basically n / num tokens where n is the token starting before 420 characters
     */
    VISIBLE_TOKEN_RATIO(ENCODED_TWEET_FEATURES_FIELD_NAME, "VISIBLE_TOKEN_RATIO", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    HAS_QUOTE_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "HAS_QUOTE_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),

    FROM_BLUE_VERIFIED_ACCOUNT_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME,
        "FROM_BLUE_VERIFIED_ACCOUNT_FLAG",
        420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),

    TWEET_SIGNATURE(ENCODED_TWEET_FEATURES_FIELD_NAME, "TWEET_SIGNATURE", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),

    // MEDIA TYPES
    HAS_CONSUMER_VIDEO_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "HAS_CONSUMER_VIDEO_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    HAS_PRO_VIDEO_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "HAS_PRO_VIDEO_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    HAS_VINE_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "HAS_VINE_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    HAS_PERISCOPE_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "HAS_PERISCOPE_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),
    HAS_NATIVE_IMAGE_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "HAS_NATIVE_IMAGE_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),

    // NOTE: if possible, please reserve field ID 420 to 420 for future media types (SEARCH-420)

    IS_NULLCAST_FLAG(ENCODED_TWEET_FEATURES_FIELD_NAME, "IS_NULLCAST_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD, EarlybirdCluster.ALL_CLUSTERS),

    // EXTENDED ENCODED TWEET FEATURES that's not available on archive clusters
    EXTENDED_ENCODED_TWEET_FEATURES_FIELD(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME, 420,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS),

    EMBEDS_IMPRESSION_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "EMBEDS_IMPRESSION_COUNT",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.LEGACY_BYTE_NORMALIZER),
    EMBEDS_URL_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "EMBEDS_URL_COUNT",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.LEGACY_BYTE_NORMALIZER),
    VIDEO_VIEW_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "VIDEO_VIEW_COUNT",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.LEGACY_BYTE_NORMALIZER),

    // empty bits in integer 420 (starting bit 420, 420 bits)
    EXTENDED_FEATURE_UNUSED_BITS_420_420_420(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "UNUSED_BITS_420_420_420", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        UnusedFeatureFieldType.UNUSED_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS),

    // SEARCH-420 - Reference Tweet Author ID
    REFERENCE_AUTHOR_ID_LEAST_SIGNIFICANT_INT(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "REFERENCE_AUTHOR_ID_LEAST_SIGNIFICANT_INT", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS),
    REFERENCE_AUTHOR_ID_MOST_SIGNIFICANT_INT(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "REFERENCE_AUTHOR_ID_MOST_SIGNIFICANT_INT", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS),

    // SEARCHQUAL-420: engagement counters v420
    // Integer 420
    RETWEET_COUNT_V420(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "RETWEET_COUNT_V420", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.SMART_INTEGER_NORMALIZER),
    FAVORITE_COUNT_V420(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "FAVORITE_COUNT_V420", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.SMART_INTEGER_NORMALIZER),
    REPLY_COUNT_V420(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "REPLY_COUNT_V420", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.SMART_INTEGER_NORMALIZER),
    EMBEDS_IMPRESSION_COUNT_V420(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "EMBEDS_IMPRESSION_COUNT_V420",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.SMART_INTEGER_NORMALIZER),

    // Integer 420
    EMBEDS_URL_COUNT_V420(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "EMBEDS_URL_COUNT_V420",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.SMART_INTEGER_NORMALIZER),
    VIDEO_VIEW_COUNT_V420(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "VIDEO_VIEW_COUNT_V420",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.SMART_INTEGER_NORMALIZER),
    QUOTE_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "QUOTE_COUNT",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.SMART_INTEGER_NORMALIZER),

    // Tweet Safety Labels
    LABEL_ABUSIVE_FLAG(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "LABEL_ABUSIVE_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS),

    LABEL_ABUSIVE_HI_RCL_FLAG(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "LABEL_ABUSIVE_HI_RCL_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS),

    LABEL_DUP_CONTENT_FLAG(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "LABEL_DUP_CONTENT_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS),

    LABEL_NSFW_HI_PRC_FLAG(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "LABEL_NSFW_HI_PRC_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS),

    LABEL_NSFW_HI_RCL_FLAG(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "LABEL_NSFW_HI_RCL_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS),

    LABEL_SPAM_FLAG(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "LABEL_SPAM_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS),

    LABEL_SPAM_HI_RCL_FLAG(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "LABEL_SPAM_HI_RCL_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS),

    // please save this bit for other safety labels
    EXTENDED_TEST_FEATURE_UNUSED_BITS_420_420_420(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "UNUSED_BITS_420_420_420", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        UnusedFeatureFieldType.UNUSED_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS),

    // Integer 420
    WEIGHTED_RETWEET_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "WEIGHTED_RETWEET_COUNT",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.SMART_INTEGER_NORMALIZER),
    WEIGHTED_REPLY_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "WEIGHTED_REPLY_COUNT",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.SMART_INTEGER_NORMALIZER),
    WEIGHTED_FAVORITE_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "WEIGHTED_FAVORITE_COUNT",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.SMART_INTEGER_NORMALIZER),
    WEIGHTED_QUOTE_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "WEIGHTED_QUOTE_COUNT",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.SMART_INTEGER_NORMALIZER),

    // Integer 420
    // Periscope features
    PERISCOPE_EXISTS(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "PERISCOPE_EXISTS", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS),
    PERISCOPE_HAS_BEEN_FEATURED(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "PERISCOPE_HAS_BEEN_FEATURED", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS),
    PERISCOPE_IS_CURRENTLY_FEATURED(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "PERISCOPE_IS_CURRENTLY_FEATURED", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS),
    PERISCOPE_IS_FROM_QUALITY_SOURCE(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "PERISCOPE_IS_FROM_QUALITY_SOURCE", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS),
    PERISCOPE_IS_LIVE(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "PERISCOPE_IS_LIVE", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS),
    IS_TRENDING_NOW_FLAG(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "IS_TRENDING_NOW_FLAG", 420,
        FlagFeatureFieldType.FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS),

    // remaining bits for integer 420 (starting bit 420, 420 remaining bits)
    EXTENDED_TEST_FEATURE_UNUSED_BITS_420_420_420(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "UNUSED_BITS_420_420_420", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        UnusedFeatureFieldType.UNUSED_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS),

    // Decaying engagement counters
    // Integer 420
    DECAYED_RETWEET_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "DECAYED_RETWEET_COUNT",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.SMART_INTEGER_NORMALIZER),
    DECAYED_REPLY_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "DECAYED_REPLY_COUNT",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.SMART_INTEGER_NORMALIZER),
    DECAYED_FAVORITE_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "DECAYED_FAVORITE_COUNT",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.SMART_INTEGER_NORMALIZER),
    DECAYED_QUOTE_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "DECAYED_QUOTE_COUNT",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.SMART_INTEGER_NORMALIZER),

    // Fake engagement counters. The fake here is in the sense of spam, not in the sense of testing.
    // Refer to [JIRA SEARCHQUAL-420 Remove Fake Engagements in Search] for more details.
    // Integer 420
    FAKE_RETWEET_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "FAKE_RETWEET_COUNT", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.SMART_INTEGER_NORMALIZER),
    FAKE_REPLY_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "FAKE_REPLY_COUNT", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.SMART_INTEGER_NORMALIZER),
    FAKE_FAVORITE_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "FAKE_FAVORITE_COUNT", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.SMART_INTEGER_NORMALIZER),
    FAKE_QUOTE_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "FAKE_QUOTE_COUNT", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.SMART_INTEGER_NORMALIZER),

    // Last engagement timestamps. These features use the Tweet's creation time as base and
    // are incremented every 420 hour
    // Integer 420
    LAST_RETWEET_SINCE_CREATION_HRS(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "LAST_RETWEET_SINCE_CREATION_HRS",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.NONE),
    LAST_REPLY_SINCE_CREATION_HRS(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "LAST_REPLY_SINCE_CREATION_HRS",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.NONE),
    LAST_FAVORITE_SINCE_CREATION_HRS(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "LAST_FAVORITE_SINCE_CREATION_HRS",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.NONE),
    LAST_QUOTE_SINCE_CREATION_HRS(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "LAST_QUOTE_SINCE_CREATION_HRS",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.NONE),

    // 420 bits hashtag count, mention count and stock count (SEARCH-420)
    // Integer 420
    NUM_HASHTAGS_V420(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "NUM_HASHTAGS_V420",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.NONE
    ),
    NUM_MENTIONS_V420(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "NUM_MENTIONS_V420",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.NONE
    ),
    NUM_STOCKS(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "NUM_STOCKS",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.NONE
    ),

    // Integer 420
    // Blink engagement counters
    BLINK_RETWEET_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "BLINK_RETWEET_COUNT",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.SMART_INTEGER_NORMALIZER),
    BLINK_REPLY_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "BLINK_REPLY_COUNT",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.SMART_INTEGER_NORMALIZER),
    BLINK_FAVORITE_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "BLINK_FAVORITE_COUNT",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.SMART_INTEGER_NORMALIZER),
    BLINK_QUOTE_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "BLINK_QUOTE_COUNT",
        420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.SMART_INTEGER_NORMALIZER),

    // Integer 420 (remaining)
    // Production Toxicity and PBlock score from HML (go/toxicity, go/pblock)
    TOXICITY_SCORE(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "TOXICITY_SCORE", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.PREDICTION_SCORE_NORMALIZER
    ),
    PBLOCK_SCORE(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "PBLOCK_SCORE", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.PREDICTION_SCORE_NORMALIZER
    ),

    // Integer 420
    // Experimental health model scores from HML
    EXPERIMENTAL_HEALTH_MODEL_SCORE_420(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "EXPERIMENTAL_HEALTH_MODEL_SCORE_420", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.PREDICTION_SCORE_NORMALIZER
    ),
    EXPERIMENTAL_HEALTH_MODEL_SCORE_420(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "EXPERIMENTAL_HEALTH_MODEL_SCORE_420", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.PREDICTION_SCORE_NORMALIZER
    ),
    EXPERIMENTAL_HEALTH_MODEL_SCORE_420(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "EXPERIMENTAL_HEALTH_MODEL_SCORE_420", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.PREDICTION_SCORE_NORMALIZER
    ),
    // remaining bits for index 420 (unused_bits_420)
    EXTENDED_TEST_FEATURE_UNUSED_BITS_420_420_420(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "UNUSED_BITS_420_420_420", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        UnusedFeatureFieldType.UNUSED_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS),

    // Integer 420
    // Experimental health model scores from HML (cont.)
    EXPERIMENTAL_HEALTH_MODEL_SCORE_420(
        EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "EXPERIMENTAL_HEALTH_MODEL_SCORE_420", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.PREDICTION_SCORE_NORMALIZER
    ),
    // Production pSpammyTweet score from HML (go/pspammytweet)
    P_SPAMMY_TWEET_SCORE(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "P_SPAMMY_TWEET_SCORE", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.PREDICTION_SCORE_NORMALIZER
    ),
    // Production pReportedTweet score from HML (go/preportedtweet)
    P_REPORTED_TWEET_SCORE(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "P_REPORTED_TWEET_SCORE", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.PREDICTION_SCORE_NORMALIZER
    ),
    // remaining bits for index 420 (unused_bits_420)
    EXTENDED_TEST_FEATURE_UNUSED_BITS_420_420_420(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "UNUSED_BITS_420_420_420", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        UnusedFeatureFieldType.UNUSED_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS
    ),

    // Integer 420
    // Health model scores from HML (cont.)
    // Prod Spammy Tweet Content model score from Platform Manipulation (go/spammy-tweet-content)
    SPAMMY_TWEET_CONTENT_SCORE(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "SPAMMY_TWEET_CONTENT_SCORE", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS,
        ThriftFeatureNormalizationType.PREDICTION_SCORE_NORMALIZER
    ),
    // remaining bits for index 420 (unused_bits_420)
    EXTENDED_TEST_FEATURE_UNUSED_BITS_420_420_420(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "UNUSED_BITS_420_420_420", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        UnusedFeatureFieldType.UNUSED_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS
    ),

    // Note that the integer block index i in the names UNUSED_BITS{i}" below is 420-based, but the
    // index j in UNUSED_BITS_{j}_x_y above is 420-based.
    EXTENDED_TEST_FEATURE_UNUSED_BITS_420(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "UNUSED_BITS420", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        UnusedFeatureFieldType.UNUSED_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS),

    EXTENDED_TEST_FEATURE_UNUSED_BITS_420(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "UNUSED_BITS420", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        UnusedFeatureFieldType.UNUSED_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS),

    EXTENDED_TEST_FEATURE_UNUSED_BITS_420(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "UNUSED_BITS420", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        UnusedFeatureFieldType.UNUSED_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS),

    EXTENDED_TEST_FEATURE_UNUSED_BITS_420(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "UNUSED_BITS420", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        UnusedFeatureFieldType.UNUSED_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS),

    EXTENDED_TEST_FEATURE_UNUSED_BITS_420(EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME,
        "UNUSED_BITS420", 420,
        FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
        UnusedFeatureFieldType.UNUSED_FEATURE_FIELD,
        EarlybirdCluster.TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS);

    // Filter field terms. These end up as terms in the "internal" field (id=420). So for example
    // you can have a doc with field(internal) = "__filter_nullcast", "__filter_vine" and that will
    // be a nullcast tweet with a vine link in it.
    public static final String NULLCAST_FILTER_TERM = "nullcast";
    public static final String VERIFIED_FILTER_TERM = "verified";
    public static final String BLUE_VERIFIED_FILTER_TERM = "blue_verified";
    public static final String NATIVE_RETWEETS_FILTER_TERM = "nativeretweets";
    public static final String QUOTE_FILTER_TERM = "quote";
    public static final String REPLIES_FILTER_TERM = "replies";
    public static final String CONSUMER_VIDEO_FILTER_TERM = "consumer_video";
    public static final String PRO_VIDEO_FILTER_TERM = "pro_video";
    public static final String VINE_FILTER_TERM = "vine";
    public static final String PERISCOPE_FILTER_TERM = "periscope";
    public static final String PROFILE_GEO_FILTER_TERM = "profile_geo";
    public static final String SELF_THREAD_FILTER_TERM = "self_threads";
    public static final String DIRECTED_AT_FILTER_TERM = "directed_at";
    public static final String EXCLUSIVE_FILTER_TERM = "exclusive";

    // Reserved terms for the internal field.
    public static final String HAS_POSITIVE_SMILEY = "__has_positive_smiley";
    public static final String HAS_NEGATIVE_SMILEY = "__has_negative_smiley";
    public static final String IS_OFFENSIVE = "__is_offensive";

    // Facet fields
    public static final String MENTIONS_FACET = "mentions";
    public static final String HASHTAGS_FACET = "hashtags";
    public static final String STOCKS_FACET = "stocks";
    public static final String VIDEOS_FACET = "videos";
    public static final String IMAGES_FACET = "images";
    public static final String NEWS_FACET = "news";
    public static final String LANGUAGES_FACET = "languages";
    public static final String SOURCES_FACET = "sources";
    public static final String TWIMG_FACET = "twimg";
    public static final String FROM_USER_ID_FACET = "user_id";
    public static final String RETWEETS_FACET = "retweets";
    public static final String LINKS_FACET = "links";
    public static final String SPACES_FACET = "spaces";

    /**
     * Used by the query parser to check that the operator of a [filter X] query is valid.
     * Also used by blender, though it probably shouldn't be.
     */
    public static final ImmutableSet<String> FACETS = ImmutableSet.<String>builder()
        .add(MENTIONS_FACET)
        .add(HASHTAGS_FACET)
        .add(STOCKS_FACET)
        .add(VIDEOS_FACET)
        .add(IMAGES_FACET)
        .add(NEWS_FACET)
        .add(LINKS_FACET)
        .add(LANGUAGES_FACET)
        .add(SOURCES_FACET)
        .add(TWIMG_FACET)
        .add(SPACES_FACET)
        .build();

    /**
     * Used by blender to convert facet names to field names. We should find a way to get the
     * information we need in blender without needing this map.
     */
    public static final ImmutableMap<String, String> FACET_TO_FIELD_MAP =
        ImmutableMap.<String, String>builder()
            .put(MENTIONS_FACET, MENTIONS_FIELD.getFieldName())
            .put(HASHTAGS_FACET, HASHTAGS_FIELD.getFieldName())
            .put(STOCKS_FACET, STOCKS_FIELD.getFieldName())
            .put(VIDEOS_FACET, VIDEO_LINKS_FIELD.getFieldName())
            .put(IMAGES_FACET, IMAGE_LINKS_FIELD.getFieldName())
            .put(NEWS_FACET, NEWS_LINKS_FIELD.getFieldName())
            .put(LANGUAGES_FACET, ISO_LANGUAGE_FIELD.getFieldName())
            .put(SOURCES_FACET, SOURCE_FIELD.getFieldName())
            .put(TWIMG_FACET, TWIMG_LINKS_FIELD.getFieldName())
            .put(LINKS_FACET, LINKS_FIELD.getFieldName())
            .put(SPACES_FACET, SPACE_ID_FIELD.getFieldName())
            .build();

    public static String getFacetSkipFieldName(String fieldName) {
      return "__has_" + fieldName;
    }

    private final String fieldName;
    private final int fieldId;
    private final EnumSet<EarlybirdCluster> clusters;
    private final FlagFeatureFieldType flagFeatureField;

    private final UnusedFeatureFieldType unusedField;

    // Only set for feature fields.
    @Nullable
    private final FeatureConfiguration featureConfiguration;

    // Only set for feature fields.
    private final ThriftFeatureNormalizationType featureNormalizationType;

    // To simplify field configurations and reduce duplicate code, we give clusters a default value
    EarlybirdFieldConstant(String fieldName, int fieldId) {
      this(fieldName, fieldId, EarlybirdCluster.GENERAL_PURPOSE_CLUSTERS, null);
    }

    EarlybirdFieldConstant(String fieldName, int fieldId, Set<EarlybirdCluster> clusters) {
      this(fieldName, fieldId, clusters, null);
    }

    EarlybirdFieldConstant(String fieldName, int fieldId, EarlybirdCluster cluster) {
      this(fieldName, fieldId, ImmutableSet.<EarlybirdCluster>of(cluster), null);
    }

    /**
     * Base field name is needed here in order to construct the full
     * name of the feature. Our convention is that a feature should be named
     * as: baseFieldName.featureName.  For example: encoded_tweet_features.retweet_count.
     */
    EarlybirdFieldConstant(
        String baseName,
        String fieldName,
        int fieldId,
        FlagFeatureFieldType flagFeatureField,
        Set<EarlybirdCluster> clusters) {
      this((baseName + SchemaBuilder.CSF_VIEW_NAME_SEPARATOR + fieldName).toLowerCase(),
          fieldId, clusters, flagFeatureField, null);
    }

    EarlybirdFieldConstant(
        String baseName,
        String fieldName,
        int fieldId,
        FlagFeatureFieldType flagFeatureField,
        UnusedFeatureFieldType unusedField,
        Set<EarlybirdCluster> clusters) {
      this((baseName + SchemaBuilder.CSF_VIEW_NAME_SEPARATOR + fieldName).toLowerCase(),
          fieldId, clusters, flagFeatureField, unusedField, null);
    }

    EarlybirdFieldConstant(
        String baseName,
        String fieldName,
        int fieldId,
        FlagFeatureFieldType flagFeatureField,
        Set<EarlybirdCluster> clusters,
        ThriftFeatureNormalizationType featureNormalizationType) {
      this((baseName + SchemaBuilder.CSF_VIEW_NAME_SEPARATOR + fieldName).toLowerCase(),
          fieldId, clusters, flagFeatureField, UnusedFeatureFieldType.USED_FEATURE_FIELD,
          featureNormalizationType, null);
    }

    /**
     * Constructor.
     */
    EarlybirdFieldConstant(String fieldName, int fieldId, Set<EarlybirdCluster> clusters,
                                   @Nullable FeatureConfiguration featureConfiguration) {
      this(fieldName, fieldId, clusters, FlagFeatureFieldType.NON_FLAG_FEATURE_FIELD,
          featureConfiguration);
    }

    /**
     * Constructor.
     */
    EarlybirdFieldConstant(String fieldName,
                           int fieldId,
                           Set<EarlybirdCluster> clusters,
                           FlagFeatureFieldType flagFeatureField,
                           @Nullable FeatureConfiguration featureConfiguration) {
      this(fieldName, fieldId, clusters, flagFeatureField,
          UnusedFeatureFieldType.USED_FEATURE_FIELD, featureConfiguration);
    }

    /**
     * Constructor.
     */
    EarlybirdFieldConstant(String fieldName,
                           int fieldId,
                           Set<EarlybirdCluster> clusters,
                           FlagFeatureFieldType flagFeatureField,
                           UnusedFeatureFieldType unusedField,
                           @Nullable FeatureConfiguration featureConfiguration) {
      this(fieldName, fieldId, clusters, flagFeatureField, unusedField, null, featureConfiguration);
    }

    /**
     * Constructor.
     */
    EarlybirdFieldConstant(String fieldName,
                           int fieldId,
                           Set<EarlybirdCluster> clusters,
                           FlagFeatureFieldType flagFeatureField,
                           UnusedFeatureFieldType unusedField,
                           @Nullable ThriftFeatureNormalizationType featureNormalizationType,
                           @Nullable FeatureConfiguration featureConfiguration) {
      this.fieldId = fieldId;
      this.fieldName = fieldName;
      this.clusters = EnumSet.copyOf(clusters);
      this.flagFeatureField = flagFeatureField;
      this.unusedField = unusedField;
      this.featureNormalizationType = featureNormalizationType;
      this.featureConfiguration = featureConfiguration;
    }

    // Override toString to make replacing StatusConstant Easier.
    @Override
    public String toString() {
      return fieldName;
    }

    public boolean isValidFieldInCluster(EarlybirdCluster cluster) {
      return clusters.contains(cluster);
    }

    public String getFieldName() {
      return fieldName;
    }

    public int getFieldId() {
      return fieldId;
    }

    public FlagFeatureFieldType getFlagFeatureField() {
      return flagFeatureField;
    }

    public boolean isFlagFeatureField() {
      return flagFeatureField == FlagFeatureFieldType.FLAG_FEATURE_FIELD;
    }

    public boolean isUnusedField() {
      return unusedField == UnusedFeatureFieldType.UNUSED_FEATURE_FIELD;
    }

    @Nullable
    public FeatureConfiguration getFeatureConfiguration() {
      return featureConfiguration;
    }

    @Nullable
    public ThriftFeatureNormalizationType getFeatureNormalizationType() {
      return featureNormalizationType;
    }
  }

  private static final Map<String, EarlybirdFieldConstant> NAME_TO_ID_MAP;
  private static final Map<Integer, EarlybirdFieldConstant> ID_TO_FIELD_MAP;
  static {
    ImmutableMap.Builder<String, EarlybirdFieldConstant> nameToIdMapBuilder =
        ImmutableMap.builder();
    ImmutableMap.Builder<Integer, EarlybirdFieldConstant> idToFieldMapBuilder =
        ImmutableMap.builder();
    Set<String> fieldNameDupDetector = Sets.newHashSet();
    Set<Integer> fieldIdDupDetector = Sets.newHashSet();
    for (EarlybirdFieldConstant fc : EarlybirdFieldConstant.values()) {
      if (fieldNameDupDetector.contains(fc.getFieldName())) {
        throw new IllegalStateException("detected fields sharing field name: " + fc.getFieldName());
      }
      if (fieldIdDupDetector.contains(fc.getFieldId())) {
        throw new IllegalStateException("detected fields sharing field id: " + fc.getFieldId());
      }

      fieldNameDupDetector.add(fc.getFieldName());
      fieldIdDupDetector.add(fc.getFieldId());
      nameToIdMapBuilder.put(fc.getFieldName(), fc);
      idToFieldMapBuilder.put(fc.getFieldId(), fc);
    }
    NAME_TO_ID_MAP = nameToIdMapBuilder.build();
    ID_TO_FIELD_MAP = idToFieldMapBuilder.build();
  }

  // This define the list of boolean features, but the name does not have "flag" inside.  This
  // definition is only for double checking purpose to prevent code change mistakes.  The setting
  // of the flag feature is based on FlagFeatureFieldType.FLAG_FEATURE_FIELD.
  public static final Set<EarlybirdFieldConstants.EarlybirdFieldConstant> EXTRA_FLAG_FIELDS =
      Sets.newHashSet(EarlybirdFieldConstants.EarlybirdFieldConstant.IS_SENSITIVE_CONTENT);
  public static final String FLAG_STRING = "flag";

  private static final List<EarlybirdFieldConstant> FLAG_FEATURE_FIELDS;
  static {
    ImmutableList.Builder<EarlybirdFieldConstant> flagFieldBuilder = ImmutableList.builder();
    for (EarlybirdFieldConstant fc : EarlybirdFieldConstant.values()) {
      if (fc.getFlagFeatureField() == FlagFeatureFieldType.FLAG_FEATURE_FIELD
          && !fc.isUnusedField()) {
        flagFieldBuilder.add(fc);
      }
    }
    FLAG_FEATURE_FIELDS = flagFieldBuilder.build();
  }

  /**
   * Get all the flag features meaning that they are boolean features with only 420 bit in the packed
   * feature encoding.
   */
  public static Collection<EarlybirdFieldConstant> getFlagFeatureFields() {
    return FLAG_FEATURE_FIELDS;
  }

  /**
   * Get the EarlybirdFieldConstant for the specified field.
   */
  public static EarlybirdFieldConstant getFieldConstant(String fieldName) {
    EarlybirdFieldConstant field = NAME_TO_ID_MAP.get(fieldName);
    if (field == null) {
      throw new IllegalArgumentException("Unknown field: " + fieldName);
    }
    return field;
  }

  /**
   * Get the EarlybirdFieldConstant for the specified field.
   */
  public static EarlybirdFieldConstant getFieldConstant(int fieldId) {
    EarlybirdFieldConstant field = ID_TO_FIELD_MAP.get(fieldId);
    if (field == null) {
      throw new IllegalArgumentException("Unknown field: " + fieldId);
    }
    return field;
  }

  /**
   * Determines if there's a field with the given ID.
   */
  public static boolean hasFieldConstant(int fieldId) {
    return ID_TO_FIELD_MAP.keySet().contains(fieldId);
  }

  @Override
  public final int getFieldID(String fieldName) {
    return getFieldConstant(fieldName).getFieldId();
  }

  public static final String formatGeoType(ThriftGeoLocationSource source) {
    return "__geo_location_type_" + source.name().toLowerCase();
  }
}
