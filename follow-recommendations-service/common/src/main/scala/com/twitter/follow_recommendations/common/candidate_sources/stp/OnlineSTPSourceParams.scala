package com.twitter.follow_recommendations.common.candidate_sources.stp

import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.Param

object OnlineSTPSourceParams {
  // This replaces the old scorer module, located at EpStpScorer.scala, with the new scorer, located
  // at Dbv2StpScorer.scala.
  case object UseDBv2Scorer
      etwittertends FSParam[Boolean]("online_stp_source_dbv2_scorer_enabled", default = false)

  // For etwitterperiments that test the impact of an improved OnlineSTP source, this controls the usage
  // of the PostNutwitter heavy-ranker. Note that this FS should *NOT* trigger bucket impressions.
  case object DisableHeavyRanker
      etwittertends FSParam[Boolean]("online_stp_source_disable_heavy_ranker", default = false)

  case object SetPredictionDetails etwittertends Param[Boolean](default = false)

}
