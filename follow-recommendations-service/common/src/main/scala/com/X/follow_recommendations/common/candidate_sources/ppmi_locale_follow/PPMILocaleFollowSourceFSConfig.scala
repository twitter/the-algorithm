package com.X.follow_recommendations.common.candidate_sources.ppmi_locale_follow

import com.X.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.X.timelines.configapi.FSBoundedParam
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.Param

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
