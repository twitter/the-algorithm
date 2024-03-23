package com.ExTwitter.follow_recommendations.products.home_timeline.configapi

import com.ExTwitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.ExTwitter.follow_recommendations.products.home_timeline.configapi.HomeTimelineParams._
import com.ExTwitter.timelines.configapi.FSBoundedParam
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.HasDurationConversion
import com.ExTwitter.timelines.configapi.Param
import com.ExTwitter.util.Duration
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
