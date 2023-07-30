package com.X.follow_recommendations.common.candidate_sources.base

import com.X.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.X.timelines.configapi.FSBoundedParam
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.HasDurationConversion
import com.X.timelines.configapi.Param
import com.X.util.Duration
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocialProofEnforcedCandidateSourceFSConfig @Inject() () extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[Param[Boolean] with FSName] =
    Seq(
      SocialProofEnforcedCandidateSourceParams.MustCallSgs,
      SocialProofEnforcedCandidateSourceParams.CallSgsCachedColumn,
    )
  override val intFSParams: Seq[FSBoundedParam[Int]] =
    Seq(
      SocialProofEnforcedCandidateSourceParams.QueryIntersectionIdsNum,
      SocialProofEnforcedCandidateSourceParams.MaxNumCandidatesToAnnotate,
      SocialProofEnforcedCandidateSourceParams.GfsIntersectionIdsNum,
      SocialProofEnforcedCandidateSourceParams.SgsIntersectionIdsNum,
    )

  override val durationFSParams: Seq[FSBoundedParam[Duration] with HasDurationConversion] = Seq(
    SocialProofEnforcedCandidateSourceParams.GfsLagDurationInDays
  )
}
