package com.twitter.follow_recommendations.common.candidate_sources.base

import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.timelines.configapi.Param
import com.twitter.util.Duration
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class SocialProofEnforcedCandidateSourceFSConfig @Inject() () etwittertends FeatureSwitchConfig {
  override val booleanFSParams: Seq[Param[Boolean] with FSName] =
    Seq(
      SocialProofEnforcedCandidateSourceParams.MustCallSgs,
      SocialProofEnforcedCandidateSourceParams.CallSgsCachedColumn,
    )
  override val intFSParams: Seq[FSBoundedParam[Int]] =
    Seq(
      SocialProofEnforcedCandidateSourceParams.QueryIntersectionIdsNum,
      SocialProofEnforcedCandidateSourceParams.MatwitterNumCandidatesToAnnotate,
      SocialProofEnforcedCandidateSourceParams.GfsIntersectionIdsNum,
      SocialProofEnforcedCandidateSourceParams.SgsIntersectionIdsNum,
    )

  override val durationFSParams: Seq[FSBoundedParam[Duration] with HasDurationConversion] = Seq(
    SocialProofEnforcedCandidateSourceParams.GfsLagDurationInDays
  )
}
