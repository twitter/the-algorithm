package com.twitter.timelines.prediction.common.aggregates

import com.twitter.ml.api.Feature
import com.twitter.timelines.prediction.features.common.TimelinesSharedFeatures
import com.twitter.timelines.prediction.features.engagement_features.EngagementDataRecordFeatures
import com.twitter.timelines.prediction.features.real_graph.RealGraphDataRecordFeatures
import com.twitter.timelines.prediction.features.recap.RecapFeatures
import com.twitter.timelines.prediction.features.time_features.TimeDataRecordFeatures

object RecapUserFeatureAggregation {
  val RecapFeaturesForAggregation: Set[Feature[_]] =
    Set(
      RecapFeatures.HAS_IMAGE,
      RecapFeatures.HAS_VIDEO,
      RecapFeatures.FROM_MUTUAL_FOLLOW,
      RecapFeatures.HAS_CARD,
      RecapFeatures.HAS_NEWS,
      RecapFeatures.REPLY_COUNT,
      RecapFeatures.FAV_COUNT,
      RecapFeatures.RETWEET_COUNT,
      RecapFeatures.BLENDER_SCORE,
      RecapFeatures.CONVERSATIONAL_COUNT,
      RecapFeatures.IS_BUSINESS_SCORE,
      RecapFeatures.CONTAINS_MEDIA,
      RecapFeatures.RETWEET_SEARCHER,
      RecapFeatures.REPLY_SEARCHER,
      RecapFeatures.MENTION_SEARCHER,
      RecapFeatures.REPLY_OTHER,
      RecapFeatures.RETWEET_OTHER,
      RecapFeatures.MATCH_UI_LANG,
      RecapFeatures.MATCH_SEARCHER_MAIN_LANG,
      RecapFeatures.MATCH_SEARCHER_LANGS,
      RecapFeatures.TWEET_COUNT_FROM_USER_IN_SNAPSHOT,
      RecapFeatures.TEXT_SCORE,
      RealGraphDataRecordFeatures.NUM_RETWEETS_EWMA,
      RealGraphDataRecordFeatures.NUM_RETWEETS_NON_ZERO_DAYS,
      RealGraphDataRecordFeatures.NUM_RETWEETS_ELAPSED_DAYS,
      RealGraphDataRecordFeatures.NUM_RETWEETS_DAYS_SINCE_LAST,
      RealGraphDataRecordFeatures.NUM_FAVORITES_EWMA,
      RealGraphDataRecordFeatures.NUM_FAVORITES_NON_ZERO_DAYS,
      RealGraphDataRecordFeatures.NUM_FAVORITES_ELAPSED_DAYS,
      RealGraphDataRecordFeatures.NUM_FAVORITES_DAYS_SINCE_LAST,
      RealGraphDataRecordFeatures.NUM_MENTIONS_EWMA,
      RealGraphDataRecordFeatures.NUM_MENTIONS_NON_ZERO_DAYS,
      RealGraphDataRecordFeatures.NUM_MENTIONS_ELAPSED_DAYS,
      RealGraphDataRecordFeatures.NUM_MENTIONS_DAYS_SINCE_LAST,
      RealGraphDataRecordFeatures.NUM_TWEET_CLICKS_EWMA,
      RealGraphDataRecordFeatures.NUM_TWEET_CLICKS_NON_ZERO_DAYS,
      RealGraphDataRecordFeatures.NUM_TWEET_CLICKS_ELAPSED_DAYS,
      RealGraphDataRecordFeatures.NUM_TWEET_CLICKS_DAYS_SINCE_LAST,
      RealGraphDataRecordFeatures.NUM_PROFILE_VIEWS_EWMA,
      RealGraphDataRecordFeatures.NUM_PROFILE_VIEWS_NON_ZERO_DAYS,
      RealGraphDataRecordFeatures.NUM_PROFILE_VIEWS_ELAPSED_DAYS,
      RealGraphDataRecordFeatures.NUM_PROFILE_VIEWS_DAYS_SINCE_LAST,
      RealGraphDataRecordFeatures.TOTAL_DWELL_TIME_EWMA,
      RealGraphDataRecordFeatures.TOTAL_DWELL_TIME_NON_ZERO_DAYS,
      RealGraphDataRecordFeatures.TOTAL_DWELL_TIME_ELAPSED_DAYS,
      RealGraphDataRecordFeatures.TOTAL_DWELL_TIME_DAYS_SINCE_LAST,
      RealGraphDataRecordFeatures.NUM_INSPECTED_TWEETS_EWMA,
      RealGraphDataRecordFeatures.NUM_INSPECTED_TWEETS_NON_ZERO_DAYS,
      RealGraphDataRecordFeatures.NUM_INSPECTED_TWEETS_ELAPSED_DAYS,
      RealGraphDataRecordFeatures.NUM_INSPECTED_TWEETS_DAYS_SINCE_LAST
    )

  val RecapLabelsForAggregation: Set[Feature.Binary] =
    Set(
      RecapFeatures.IS_FAVORITED,
      RecapFeatures.IS_RETWEETED,
      RecapFeatures.IS_CLICKED,
      RecapFeatures.IS_PROFILE_CLICKED,
      RecapFeatures.IS_OPEN_LINKED
    )

  val DwellDuration: Set[Feature[_]] =
    Set(
      TimelinesSharedFeatures.DWELL_TIME_MS,
    )

  val UserFeaturesV2: Set[Feature[_]] = RecapFeaturesForAggregation ++ Set(
    RecapFeatures.HAS_VINE,
    RecapFeatures.HAS_PERISCOPE,
    RecapFeatures.HAS_PRO_VIDEO,
    RecapFeatures.HAS_VISIBLE_LINK,
    RecapFeatures.BIDIRECTIONAL_FAV_COUNT,
    RecapFeatures.UNIDIRECTIONAL_FAV_COUNT,
    RecapFeatures.BIDIRECTIONAL_REPLY_COUNT,
    RecapFeatures.UNIDIRECTIONAL_REPLY_COUNT,
    RecapFeatures.BIDIRECTIONAL_RETWEET_COUNT,
    RecapFeatures.UNIDIRECTIONAL_RETWEET_COUNT,
    RecapFeatures.EMBEDS_URL_COUNT,
    RecapFeatures.EMBEDS_IMPRESSION_COUNT,
    RecapFeatures.VIDEO_VIEW_COUNT,
    RecapFeatures.IS_RETWEET,
    RecapFeatures.IS_REPLY,
    RecapFeatures.IS_EXTENDED_REPLY,
    RecapFeatures.HAS_LINK,
    RecapFeatures.HAS_TREND,
    RecapFeatures.LINK_LANGUAGE,
    RecapFeatures.NUM_HASHTAGS,
    RecapFeatures.NUM_MENTIONS,
    RecapFeatures.IS_SENSITIVE,
    RecapFeatures.HAS_MULTIPLE_MEDIA,
    RecapFeatures.USER_REP,
    RecapFeatures.FAV_COUNT_V2,
    RecapFeatures.RETWEET_COUNT_V2,
    RecapFeatures.REPLY_COUNT_V2,
    RecapFeatures.LINK_COUNT,
    EngagementDataRecordFeatures.InNetworkFavoritesCount,
    EngagementDataRecordFeatures.InNetworkRetweetsCount,
    EngagementDataRecordFeatures.InNetworkRepliesCount
  )

  val UserAuthorFeaturesV2: Set[Feature[_]] = Set(
    RecapFeatures.HAS_IMAGE,
    RecapFeatures.HAS_VINE,
    RecapFeatures.HAS_PERISCOPE,
    RecapFeatures.HAS_PRO_VIDEO,
    RecapFeatures.HAS_VIDEO,
    RecapFeatures.HAS_CARD,
    RecapFeatures.HAS_NEWS,
    RecapFeatures.HAS_VISIBLE_LINK,
    RecapFeatures.REPLY_COUNT,
    RecapFeatures.FAV_COUNT,
    RecapFeatures.RETWEET_COUNT,
    RecapFeatures.BLENDER_SCORE,
    RecapFeatures.CONVERSATIONAL_COUNT,
    RecapFeatures.IS_BUSINESS_SCORE,
    RecapFeatures.CONTAINS_MEDIA,
    RecapFeatures.RETWEET_SEARCHER,
    RecapFeatures.REPLY_SEARCHER,
    RecapFeatures.MENTION_SEARCHER,
    RecapFeatures.REPLY_OTHER,
    RecapFeatures.RETWEET_OTHER,
    RecapFeatures.MATCH_UI_LANG,
    RecapFeatures.MATCH_SEARCHER_MAIN_LANG,
    RecapFeatures.MATCH_SEARCHER_LANGS,
    RecapFeatures.TWEET_COUNT_FROM_USER_IN_SNAPSHOT,
    RecapFeatures.TEXT_SCORE,
    RecapFeatures.BIDIRECTIONAL_FAV_COUNT,
    RecapFeatures.UNIDIRECTIONAL_FAV_COUNT,
    RecapFeatures.BIDIRECTIONAL_REPLY_COUNT,
    RecapFeatures.UNIDIRECTIONAL_REPLY_COUNT,
    RecapFeatures.BIDIRECTIONAL_RETWEET_COUNT,
    RecapFeatures.UNIDIRECTIONAL_RETWEET_COUNT,
    RecapFeatures.EMBEDS_URL_COUNT,
    RecapFeatures.EMBEDS_IMPRESSION_COUNT,
    RecapFeatures.VIDEO_VIEW_COUNT,
    RecapFeatures.IS_RETWEET,
    RecapFeatures.IS_REPLY,
    RecapFeatures.HAS_LINK,
    RecapFeatures.HAS_TREND,
    RecapFeatures.LINK_LANGUAGE,
    RecapFeatures.NUM_HASHTAGS,
    RecapFeatures.NUM_MENTIONS,
    RecapFeatures.IS_SENSITIVE,
    RecapFeatures.HAS_MULTIPLE_MEDIA,
    RecapFeatures.FAV_COUNT_V2,
    RecapFeatures.RETWEET_COUNT_V2,
    RecapFeatures.REPLY_COUNT_V2,
    RecapFeatures.LINK_COUNT,
    EngagementDataRecordFeatures.InNetworkFavoritesCount,
    EngagementDataRecordFeatures.InNetworkRetweetsCount,
    EngagementDataRecordFeatures.InNetworkRepliesCount
  )

  val UserAuthorFeaturesV2Count: Set[Feature[_]] = Set(
    RecapFeatures.HAS_IMAGE,
    RecapFeatures.HAS_VINE,
    RecapFeatures.HAS_PERISCOPE,
    RecapFeatures.HAS_PRO_VIDEO,
    RecapFeatures.HAS_VIDEO,
    RecapFeatures.HAS_CARD,
    RecapFeatures.HAS_NEWS,
    RecapFeatures.HAS_VISIBLE_LINK,
    RecapFeatures.FAV_COUNT,
    RecapFeatures.CONTAINS_MEDIA,
    RecapFeatures.RETWEET_SEARCHER,
    RecapFeatures.REPLY_SEARCHER,
    RecapFeatures.MENTION_SEARCHER,
    RecapFeatures.REPLY_OTHER,
    RecapFeatures.RETWEET_OTHER,
    RecapFeatures.MATCH_UI_LANG,
    RecapFeatures.MATCH_SEARCHER_MAIN_LANG,
    RecapFeatures.MATCH_SEARCHER_LANGS,
    RecapFeatures.IS_RETWEET,
    RecapFeatures.IS_REPLY,
    RecapFeatures.HAS_LINK,
    RecapFeatures.HAS_TREND,
    RecapFeatures.IS_SENSITIVE,
    RecapFeatures.HAS_MULTIPLE_MEDIA,
    EngagementDataRecordFeatures.InNetworkFavoritesCount
  )

  val UserTopicFeaturesV2Count: Set[Feature[_]] = Set(
    RecapFeatures.HAS_IMAGE,
    RecapFeatures.HAS_VIDEO,
    RecapFeatures.HAS_CARD,
    RecapFeatures.HAS_NEWS,
    RecapFeatures.FAV_COUNT,
    RecapFeatures.CONTAINS_MEDIA,
    RecapFeatures.RETWEET_SEARCHER,
    RecapFeatures.REPLY_SEARCHER,
    RecapFeatures.MENTION_SEARCHER,
    RecapFeatures.REPLY_OTHER,
    RecapFeatures.RETWEET_OTHER,
    RecapFeatures.MATCH_UI_LANG,
    RecapFeatures.MATCH_SEARCHER_MAIN_LANG,
    RecapFeatures.MATCH_SEARCHER_LANGS,
    RecapFeatures.IS_RETWEET,
    RecapFeatures.IS_REPLY,
    RecapFeatures.HAS_LINK,
    RecapFeatures.HAS_TREND,
    RecapFeatures.IS_SENSITIVE,
    EngagementDataRecordFeatures.InNetworkFavoritesCount,
    EngagementDataRecordFeatures.InNetworkRetweetsCount,
    TimelinesSharedFeatures.NUM_CAPS,
    TimelinesSharedFeatures.ASPECT_RATIO_DEN,
    TimelinesSharedFeatures.NUM_NEWLINES,
    TimelinesSharedFeatures.IS_360,
    TimelinesSharedFeatures.IS_MANAGED,
    TimelinesSharedFeatures.IS_MONETIZABLE,
    TimelinesSharedFeatures.HAS_SELECTED_PREVIEW_IMAGE,
    TimelinesSharedFeatures.HAS_TITLE,
    TimelinesSharedFeatures.HAS_DESCRIPTION,
    TimelinesSharedFeatures.HAS_VISIT_SITE_CALL_TO_ACTION,
    TimelinesSharedFeatures.HAS_WATCH_NOW_CALL_TO_ACTION
  )

  val UserFeaturesV5Continuous: Set[Feature[_]] = Set(
    TimelinesSharedFeatures.QUOTE_COUNT,
    TimelinesSharedFeatures.VISIBLE_TOKEN_RATIO,
    TimelinesSharedFeatures.WEIGHTED_FAV_COUNT,
    TimelinesSharedFeatures.WEIGHTED_RETWEET_COUNT,
    TimelinesSharedFeatures.WEIGHTED_REPLY_COUNT,
    TimelinesSharedFeatures.WEIGHTED_QUOTE_COUNT,
    TimelinesSharedFeatures.EMBEDS_IMPRESSION_COUNT_V2,
    TimelinesSharedFeatures.EMBEDS_URL_COUNT_V2,
    TimelinesSharedFeatures.DECAYED_FAVORITE_COUNT,
    TimelinesSharedFeatures.DECAYED_RETWEET_COUNT,
    TimelinesSharedFeatures.DECAYED_REPLY_COUNT,
    TimelinesSharedFeatures.DECAYED_QUOTE_COUNT,
    TimelinesSharedFeatures.FAKE_FAVORITE_COUNT,
    TimelinesSharedFeatures.FAKE_RETWEET_COUNT,
    TimelinesSharedFeatures.FAKE_REPLY_COUNT,
    TimelinesSharedFeatures.FAKE_QUOTE_COUNT,
    TimeDataRecordFeatures.LAST_FAVORITE_SINCE_CREATION_HRS,
    TimeDataRecordFeatures.LAST_RETWEET_SINCE_CREATION_HRS,
    TimeDataRecordFeatures.LAST_REPLY_SINCE_CREATION_HRS,
    TimeDataRecordFeatures.LAST_QUOTE_SINCE_CREATION_HRS,
    TimeDataRecordFeatures.TIME_SINCE_LAST_FAVORITE_HRS,
    TimeDataRecordFeatures.TIME_SINCE_LAST_RETWEET_HRS,
    TimeDataRecordFeatures.TIME_SINCE_LAST_REPLY_HRS,
    TimeDataRecordFeatures.TIME_SINCE_LAST_QUOTE_HRS
  )

  val UserFeaturesV5Boolean: Set[Feature[_]] = Set(
    TimelinesSharedFeatures.LABEL_ABUSIVE_FLAG,
    TimelinesSharedFeatures.LABEL_ABUSIVE_HI_RCL_FLAG,
    TimelinesSharedFeatures.LABEL_DUP_CONTENT_FLAG,
    TimelinesSharedFeatures.LABEL_NSFW_HI_PRC_FLAG,
    TimelinesSharedFeatures.LABEL_NSFW_HI_RCL_FLAG,
    TimelinesSharedFeatures.LABEL_SPAM_FLAG,
    TimelinesSharedFeatures.LABEL_SPAM_HI_RCL_FLAG,
    TimelinesSharedFeatures.PERISCOPE_EXISTS,
    TimelinesSharedFeatures.PERISCOPE_IS_LIVE,
    TimelinesSharedFeatures.PERISCOPE_HAS_BEEN_FEATURED,
    TimelinesSharedFeatures.PERISCOPE_IS_CURRENTLY_FEATURED,
    TimelinesSharedFeatures.PERISCOPE_IS_FROM_QUALITY_SOURCE,
    TimelinesSharedFeatures.HAS_QUOTE
  )

  val UserAuthorFeaturesV5: Set[Feature[_]] = Set(
    TimelinesSharedFeatures.HAS_QUOTE,
    TimelinesSharedFeatures.LABEL_ABUSIVE_FLAG,
    TimelinesSharedFeatures.LABEL_ABUSIVE_HI_RCL_FLAG,
    TimelinesSharedFeatures.LABEL_DUP_CONTENT_FLAG,
    TimelinesSharedFeatures.LABEL_NSFW_HI_PRC_FLAG,
    TimelinesSharedFeatures.LABEL_NSFW_HI_RCL_FLAG,
    TimelinesSharedFeatures.LABEL_SPAM_FLAG,
    TimelinesSharedFeatures.LABEL_SPAM_HI_RCL_FLAG
  )

  val UserTweetSourceFeaturesV1Continuous: Set[Feature[_]] = Set(
    TimelinesSharedFeatures.NUM_CAPS,
    TimelinesSharedFeatures.NUM_WHITESPACES,
    TimelinesSharedFeatures.TWEET_LENGTH,
    TimelinesSharedFeatures.ASPECT_RATIO_DEN,
    TimelinesSharedFeatures.ASPECT_RATIO_NUM,
    TimelinesSharedFeatures.BIT_RATE,
    TimelinesSharedFeatures.HEIGHT_1,
    TimelinesSharedFeatures.HEIGHT_2,
    TimelinesSharedFeatures.HEIGHT_3,
    TimelinesSharedFeatures.HEIGHT_4,
    TimelinesSharedFeatures.VIDEO_DURATION,
    TimelinesSharedFeatures.WIDTH_1,
    TimelinesSharedFeatures.WIDTH_2,
    TimelinesSharedFeatures.WIDTH_3,
    TimelinesSharedFeatures.WIDTH_4,
    TimelinesSharedFeatures.NUM_MEDIA_TAGS
  )

  val UserTweetSourceFeaturesV1Boolean: Set[Feature[_]] = Set(
    TimelinesSharedFeatures.HAS_QUESTION,
    TimelinesSharedFeatures.RESIZE_METHOD_1,
    TimelinesSharedFeatures.RESIZE_METHOD_2,
    TimelinesSharedFeatures.RESIZE_METHOD_3,
    TimelinesSharedFeatures.RESIZE_METHOD_4
  )

  val UserTweetSourceFeaturesV2Continuous: Set[Feature[_]] = Set(
    TimelinesSharedFeatures.NUM_EMOJIS,
    TimelinesSharedFeatures.NUM_EMOTICONS,
    TimelinesSharedFeatures.NUM_NEWLINES,
    TimelinesSharedFeatures.NUM_STICKERS,
    TimelinesSharedFeatures.NUM_FACES,
    TimelinesSharedFeatures.NUM_COLOR_PALLETTE_ITEMS,
    TimelinesSharedFeatures.VIEW_COUNT,
    TimelinesSharedFeatures.TWEET_LENGTH_TYPE
  )

  val UserTweetSourceFeaturesV2Boolean: Set[Feature[_]] = Set(
    TimelinesSharedFeatures.IS_360,
    TimelinesSharedFeatures.IS_MANAGED,
    TimelinesSharedFeatures.IS_MONETIZABLE,
    TimelinesSharedFeatures.IS_EMBEDDABLE,
    TimelinesSharedFeatures.HAS_SELECTED_PREVIEW_IMAGE,
    TimelinesSharedFeatures.HAS_TITLE,
    TimelinesSharedFeatures.HAS_DESCRIPTION,
    TimelinesSharedFeatures.HAS_VISIT_SITE_CALL_TO_ACTION,
    TimelinesSharedFeatures.HAS_WATCH_NOW_CALL_TO_ACTION
  )

  val UserAuthorTweetSourceFeaturesV1: Set[Feature[_]] = Set(
    TimelinesSharedFeatures.HAS_QUESTION,
    TimelinesSharedFeatures.TWEET_LENGTH,
    TimelinesSharedFeatures.VIDEO_DURATION,
    TimelinesSharedFeatures.NUM_MEDIA_TAGS
  )

  val UserAuthorTweetSourceFeaturesV2: Set[Feature[_]] = Set(
    TimelinesSharedFeatures.NUM_CAPS,
    TimelinesSharedFeatures.NUM_WHITESPACES,
    TimelinesSharedFeatures.ASPECT_RATIO_DEN,
    TimelinesSharedFeatures.ASPECT_RATIO_NUM,
    TimelinesSharedFeatures.BIT_RATE,
    TimelinesSharedFeatures.TWEET_LENGTH_TYPE,
    TimelinesSharedFeatures.NUM_EMOJIS,
    TimelinesSharedFeatures.NUM_EMOTICONS,
    TimelinesSharedFeatures.NUM_NEWLINES,
    TimelinesSharedFeatures.NUM_STICKERS,
    TimelinesSharedFeatures.NUM_FACES,
    TimelinesSharedFeatures.IS_360,
    TimelinesSharedFeatures.IS_MANAGED,
    TimelinesSharedFeatures.IS_MONETIZABLE,
    TimelinesSharedFeatures.HAS_SELECTED_PREVIEW_IMAGE,
    TimelinesSharedFeatures.HAS_TITLE,
    TimelinesSharedFeatures.HAS_DESCRIPTION,
    TimelinesSharedFeatures.HAS_VISIT_SITE_CALL_TO_ACTION,
    TimelinesSharedFeatures.HAS_WATCH_NOW_CALL_TO_ACTION
  )

  val UserAuthorTweetSourceFeaturesV2Count: Set[Feature[_]] = Set(
    TimelinesSharedFeatures.NUM_CAPS,
    TimelinesSharedFeatures.ASPECT_RATIO_DEN,
    TimelinesSharedFeatures.NUM_NEWLINES,
    TimelinesSharedFeatures.IS_360,
    TimelinesSharedFeatures.IS_MANAGED,
    TimelinesSharedFeatures.IS_MONETIZABLE,
    TimelinesSharedFeatures.HAS_SELECTED_PREVIEW_IMAGE,
    TimelinesSharedFeatures.HAS_TITLE,
    TimelinesSharedFeatures.HAS_DESCRIPTION,
    TimelinesSharedFeatures.HAS_VISIT_SITE_CALL_TO_ACTION,
    TimelinesSharedFeatures.HAS_WATCH_NOW_CALL_TO_ACTION
  )

  val LabelsV2: Set[Feature.Binary] = RecapLabelsForAggregation ++ Set(
    RecapFeatures.IS_REPLIED,
    RecapFeatures.IS_PHOTO_EXPANDED,
    RecapFeatures.IS_VIDEO_PLAYBACK_50
  )

  val TwitterWideFeatures: Set[Feature[_]] = Set(
    RecapFeatures.IS_REPLY,
    TimelinesSharedFeatures.HAS_QUOTE,
    RecapFeatures.HAS_MENTION,
    RecapFeatures.HAS_HASHTAG,
    RecapFeatures.HAS_LINK,
    RecapFeatures.HAS_CARD,
    RecapFeatures.CONTAINS_MEDIA
  )

  val TwitterWideLabels: Set[Feature.Binary] = Set(
    RecapFeatures.IS_FAVORITED,
    RecapFeatures.IS_RETWEETED,
    RecapFeatures.IS_REPLIED
  )

  val ReciprocalLabels: Set[Feature.Binary] = Set(
    RecapFeatures.IS_REPLIED_REPLY_IMPRESSED_BY_AUTHOR,
    RecapFeatures.IS_REPLIED_REPLY_REPLIED_BY_AUTHOR,
    RecapFeatures.IS_REPLIED_REPLY_FAVORITED_BY_AUTHOR
  )

  val NegativeEngagementLabels: Set[Feature.Binary] = Set(
    RecapFeatures.IS_REPORT_TWEET_CLICKED,
    RecapFeatures.IS_BLOCK_CLICKED,
    RecapFeatures.IS_MUTE_CLICKED,
    RecapFeatures.IS_DONT_LIKE
  )

  val GoodClickLabels: Set[Feature.Binary] = Set(
    RecapFeatures.IS_GOOD_CLICKED_CONVO_DESC_V1,
    RecapFeatures.IS_GOOD_CLICKED_CONVO_DESC_V2,
  )
}
