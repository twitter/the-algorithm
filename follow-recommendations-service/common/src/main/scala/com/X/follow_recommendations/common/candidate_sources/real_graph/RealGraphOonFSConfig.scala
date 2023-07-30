package com.X.follow_recommendations.common.candidate_sources.real_graph

import com.X.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.X.timelines.configapi.FSBoundedParam
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.Param
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
