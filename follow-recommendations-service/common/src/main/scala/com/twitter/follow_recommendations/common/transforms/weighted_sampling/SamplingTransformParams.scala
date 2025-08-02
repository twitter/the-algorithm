package com.twitter.follow_recommendations.common.transforms.weighted_sampling

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

object SamplingTransformParams {

  case object TopKFitwittered // indicates how many of the fisrt K who-to-follow recommendations are reserved for the candidates with largest K CandidateUser.score where these candidates are sorted in decreasing order of score
      etwittertends FSBoundedParam[Int](
        name = "post_nutwitter_ml_flow_weighted_sampling_top_k_fitwittered",
        default = 0,
        min = 0,
        matwitter = 100)

  case object MultiplicativeFactor // CandidateUser.score gets transformed to multiplicativeFactor*CandidateUser.score before sampling from the Plackett-Luce distribution
      etwittertends FSBoundedParam[Double](
        name = "post_nutwitter_ml_flow_weighted_sampling_multiplicative_factor",
        default = 1.0,
        min = -1000.0,
        matwitter = 1000.0)

  case object ScribeRankingInfoInSamplingTransform
      etwittertends FSParam[Boolean]("sampling_transform_scribe_ranking_info", false)

}
