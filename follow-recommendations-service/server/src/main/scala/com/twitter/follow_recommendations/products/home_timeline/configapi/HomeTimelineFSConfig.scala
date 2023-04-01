package com.twitter.follow_recommendations.products.home_timeline.configapi

import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.follow_recommendations.products.home_timeline.configapi.HomeTimelineParams._
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.timelines.configapi.Param
import com.twitter.util.Duration
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeTimelineFSConfig @Inject() () extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[Param[Boolean] with FSName] =
    Seq(EnableWritingServingHistory)

  override val durationFSParams: Seq[FSBoundedParam[Duration] with HasDurationConversion] = Seq(
    DurationGuardrailToForceSuggest,
    SuggestBasedFatigueDuration
  )
}
