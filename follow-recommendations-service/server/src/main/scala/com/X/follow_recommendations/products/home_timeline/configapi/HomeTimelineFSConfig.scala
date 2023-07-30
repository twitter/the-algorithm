package com.X.follow_recommendations.products.home_timeline.configapi

import com.X.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.X.follow_recommendations.products.home_timeline.configapi.HomeTimelineParams._
import com.X.timelines.configapi.FSBoundedParam
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.HasDurationConversion
import com.X.timelines.configapi.Param
import com.X.util.Duration
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
