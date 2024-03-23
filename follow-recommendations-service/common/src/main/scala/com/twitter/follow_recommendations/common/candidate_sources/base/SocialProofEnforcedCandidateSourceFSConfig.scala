package com.ExTwitter.follow_recommendations.common.candidate_sources.base

import com.ExTwitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.ExTwitter.timelines.configapi.FSBoundedParam
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.HasDurationConversion
import com.ExTwitter.timelines.configapi.Param
import com.ExTwitter.util.Duration
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
