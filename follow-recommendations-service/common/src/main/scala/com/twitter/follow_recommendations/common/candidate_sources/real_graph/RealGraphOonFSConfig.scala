package com.twitter.follow_recommendations.common.candidate_sources.real_graph

import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.Param
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealGraphOonFSConfig @Inject() () extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[Param[Boolean] with FSName] =
    Seq(
      RealGraphOonParams.IncludeRealGraphOonCandidates,
      RealGraphOonParams.TryToReadRealGraphOonCandidates,
      RealGraphOonParams.UseV2
    )
  override val doubleFSParams: Seq[FSBoundedParam[Double]] =
    Seq(
      RealGraphOonParams.ScoreThreshold
    )
  override val intFSParams: Seq[FSBoundedParam[Int]] =
    Seq(
      RealGraphOonParams.RealGraphOonResultCountThreshold,
      RealGraphOonParams.MaxResults,
    )
}
