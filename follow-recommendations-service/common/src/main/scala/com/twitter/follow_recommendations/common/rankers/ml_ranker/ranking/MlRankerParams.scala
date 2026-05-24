package com.twitter.follow_recommendations.common.rankers.ml_ranker.ranking

import com.twitter.follow_recommendations.common.rankers.common.RankerId
import com.twitter.timelines.configapi.FSEnumParam
import com.twitter.timelines.configapi.FSParam

/**
 * When adding Producer side experiments, make sure to register the FS Key in [[ProducerFeatureFilter]]
 * in [[FeatureSwitchesModule]], otherwise, the FS will not work.
 */
object MlRankerParams {
  // which ranker to use by default for the given request
  case object RequestScorerIdParam
      extends FSEnumParam[RankerId.type](
        name = "post_nux_ml_flow_ml_ranker_id",
        default = RankerId.PostNuxProdRanker,
        enum = RankerId
      )

  // which ranker to use for the given candidate
  case object CandidateScorerIdParam
      extends FSEnumParam[RankerId.type](
        name = "post_nux_ml_flow_candidate_user_scorer_id",
        default = RankerId.None,
        enum = RankerId
      )

  case object ScribeRankingInfoInMlRanker
      extends FSParam[Boolean]("post_nux_ml_flow_scribe_ranking_info_in_ml_ranker", true)
}
