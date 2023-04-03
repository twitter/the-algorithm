package com.twitter.timelines.data_processing.ad_hoc.earlybird_ranking.common

import com.twitter.ml.api.Feature
import com.twitter.timelines.prediction.features.recap.RecapFeatures

class EarlybirdTrainingRecapConfiguration extends EarlybirdTrainingConfiguration {
  override val labels: Map[String, Feature.Binary] = Map(
    "detail_expanded" -> RecapFeatures.IS_CLICKED,
    "favorited" -> RecapFeatures.is_favorite,
    "open_linked" -> RecapFeatures.is_linked,
    "photo_expanded" -> RecapFeatures.IS_PHOTO_EXPANDED,
    "profile_clicked" -> RecapFeatures.IS_PROFILE_CLICKED,
    "replied" -> RecapFeatures.IS_REPLIED,
    "retweeted" -> RecapFeatures.IS_RETWEETED,
    "video_playback50" -> RecapFeatures.IS_VIDEO_PLAYBACK_50
  )
}
