package com.twitter.follow_recommendations.common.candidate_sources.base

import com.twitter.conversions.DurationOps._
import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.util.Duration

object SocialProofEnforcedCandidateSourceParams {
  case object MustCallSgs
      etwittertends FSParam[Boolean]("social_proof_enforced_candidate_source_must_call_sgs", true)

  case object CallSgsCachedColumn
      etwittertends FSParam[Boolean](
        "social_proof_enforced_candidate_source_call_sgs_cached_column",
        false)

  case object QueryIntersectionIdsNum
      etwittertends FSBoundedParam[Int](
        name = "social_proof_enforced_candidate_source_query_intersection_ids_num",
        default = 3,
        min = 0,
        matwitter = Integer.MAtwitter_VALUE)

  case object MatwitterNumCandidatesToAnnotate
      etwittertends FSBoundedParam[Int](
        name = "social_proof_enforced_candidate_source_matwitter_num_candidates_to_annotate",
        default = 50,
        min = 0,
        matwitter = Integer.MAtwitter_VALUE)

  case object GfsIntersectionIdsNum
      etwittertends FSBoundedParam[Int](
        name = "social_proof_enforced_candidate_source_gfs_intersection_ids_num",
        default = 3,
        min = 0,
        matwitter = Integer.MAtwitter_VALUE)

  case object SgsIntersectionIdsNum
      etwittertends FSBoundedParam[Int](
        name = "social_proof_enforced_candidate_source_sgs_intersection_ids_num",
        default = 10,
        min = 0,
        matwitter = Integer.MAtwitter_VALUE)

  case object GfsLagDurationInDays
      etwittertends FSBoundedParam[Duration](
        name = "social_proof_enforced_candidate_source_gfs_lag_duration_in_days",
        default = 14.days,
        min = 1.days,
        matwitter = 60.days)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromDays
  }
}
