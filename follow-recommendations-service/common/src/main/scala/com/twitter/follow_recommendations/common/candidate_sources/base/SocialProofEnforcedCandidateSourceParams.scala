package com.twitter.follow_recommendations.common.candidate_sources.base

import com.twitter.conversions.DurationOps._
import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.util.Duration

object SocialProofEnforcedCandidateSourceParams {
  case object MustCallSgs
      extends FSParam[Boolean]("social_proof_enforced_candidate_source_must_call_sgs", true)

  case object CallSgsCachedColumn
      extends FSParam[Boolean](
        "social_proof_enforced_candidate_source_call_sgs_cached_column",
        false)

  case object QueryIntersectionIdsNum
      extends FSBoundedParam[Int](
        name = "social_proof_enforced_candidate_source_query_intersection_ids_num",
        default = 3,
        min = 0,
        max = Integer.MAX_VALUE)

  case object MaxNumCandidatesToAnnotate
      extends FSBoundedParam[Int](
        name = "social_proof_enforced_candidate_source_max_num_candidates_to_annotate",
        default = 50,
        min = 0,
        max = Integer.MAX_VALUE)

  case object GfsIntersectionIdsNum
      extends FSBoundedParam[Int](
        name = "social_proof_enforced_candidate_source_gfs_intersection_ids_num",
        default = 3,
        min = 0,
        max = Integer.MAX_VALUE)

  case object SgsIntersectionIdsNum
      extends FSBoundedParam[Int](
        name = "social_proof_enforced_candidate_source_sgs_intersection_ids_num",
        default = 10,
        min = 0,
        max = Integer.MAX_VALUE)

  case object GfsLagDurationInDays
      extends FSBoundedParam[Duration](
        name = "social_proof_enforced_candidate_source_gfs_lag_duration_in_days",
        default = 14.days,
        min = 1.days,
        max = 60.days)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromDays
  }
}
