package com.twitter.follow_recommendations.common.candidate_sources.ppmi_locale_follow

import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.Param

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PPMILocaleFollowSourceFSConfig @Inject() () extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[Param[Boolean] with FSName] = Seq(
    PPMILocaleFollowSourceParams.CandidateSourceEnabled,
  )

  override val stringSeqFSParams: Seq[Param[Seq[String]] with FSName] = Seq(
    PPMILocaleFollowSourceParams.LocaleToExcludeFromRecommendation,
  )

  override val doubleFSParams: Seq[FSBoundedParam[Double]] = Seq(
    PPMILocaleFollowSourceParams.CandidateSourceWeight,
  )
}
