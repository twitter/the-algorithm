package com.twitter.timelines.prediction.common.aggregates

import com.twitter.ml.api.Feature
import com.twitter.timelines.prediction.features.engagement_features.EngagementDataRecordFeatures
import com.twitter.timelines.prediction.features.itl.ITLFeatures

object RectweetUserFeatureAggregation {
  val RectweetLabelsForAggregation: Set[Feature.Binary] =
    Set(
      ITLFeatures.IS_FAVORITED,
      ITLFeatures.IS_RETWEETED,
      ITLFeatures.IS_REPLIED,
      ITLFeatures.IS_CLICKED,
      ITLFeatures.IS_PROFILE_CLICKED,
      ITLFeatures.IS_OPEN_LINKED,
      ITLFeatures.IS_PHOTO_EXPANDED,
      ITLFeatures.IS_VIDEO_PLAYBACK_50
    )

  val TweetFeatures: Set[Feature[_]] = Set(
    ITLFeatures.HAS_IMAGE,
    ITLFeatures.HAS_CARD,
    ITLFeatures.HAS_NEWS,
    ITLFeatures.REPLY_COUNT,
    ITLFeatures.FAV_COUNT,
    ITLFeatures.REPLY_COUNT,
    ITLFeatures.RETWEET_COUNT,
    ITLFeatures.MATCHES_UI_LANG,
    ITLFeatures.MATCHES_SEARCHER_MAIN_LANG,
    ITLFeatures.MATCHES_SEARCHER_LANGS,
    ITLFeatures.TEXT_SCORE,
    ITLFeatures.LINK_LANGUAGE,
    ITLFeatures.NUM_HASHTAGS,
    ITLFeatures.NUM_MENTIONS,
    ITLFeatures.IS_SENSITIVE,
    ITLFeatures.HAS_VIDEO,
    ITLFeatures.HAS_LINK,
    ITLFeatures.HAS_VISIBLE_LINK,
    EngagementDataRecordFeatures.InNetworkFavoritesCount
    // nice to have, but currently not hydrated in the RecommendedTweet payload
    //EngagementDataRecordFeatures.InNetworkRetweetsCount,
    //EngagementDataRecordFeatures.InNetworkRepliesCount
  )

  val ReciprocalLabels: Set[Feature.Binary] = Set(
    ITLFeatures.IS_REPLIED_REPLY_IMPRESSED_BY_AUTHOR,
    ITLFeatures.IS_REPLIED_REPLY_REPLIED_BY_AUTHOR,
    ITLFeatures.IS_REPLIED_REPLY_FAVORITED_BY_AUTHOR,
    ITLFeatures.IS_REPLIED_REPLY_RETWEETED_BY_AUTHOR,
    ITLFeatures.IS_REPLIED_REPLY_QUOTED_BY_AUTHOR
  )
}
