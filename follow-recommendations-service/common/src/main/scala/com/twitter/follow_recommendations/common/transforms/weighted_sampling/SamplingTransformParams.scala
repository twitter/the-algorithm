package com.twitter.follow_recommendations.common.transforms.weighted_sampling

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

object SamplingTransformParams {

  case object TopKFixed // indicates how many of the fisrt K who-to-follow recommendations are reserved for the candidates with largest K CandidateUser.score where these candidates are sorted in decreasing order of score
      extends FSBoundedParam[Int](
        name = "post_nux_ml_flow_weighted_sampling_top_k_fixed",
        default = 0,
        min = 0,
        max = 100)

  case object MultiplicativeFactor // CandidateUser.score gets transformed to multiplicativeFactor*CandidateUser.score before sampling from the Plackett-Luce distribution
      extends FSBoundedParam[Double](
        name = "post_nux_ml_flow_weighted_sampling_multiplicative_factor",
        default = 1.0,
        min = -1000.0,
        max = 1000.0)

  case object ScribeRankingInfoInSamplingTransform
      extends FSParam[Boolean]("sampling_transform_scribe_ranking_info", false)

}
