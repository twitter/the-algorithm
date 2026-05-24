package com.twitter.follow_recommendations.configapi.params

import com.twitter.follow_recommendations.models.CandidateSourceType
import com.twitter.timelines.configapi.FSEnumParam
import com.twitter.timelines.configapi.FSParam

/**
 * When adding Producer side experiments, make sure to register the FS Key in [[ProducerFeatureFilter]]
 * in [[FeatureSwitchesModule]], otherwise, the FS will not work.
 */
object GlobalParams {

  object EnableCandidateParamHydrations
      extends FSParam[Boolean]("frs_receiver_enable_candidate_params", false)

  object KeepUserCandidate
      extends FSParam[Boolean]("frs_receiver_holdback_keep_user_candidate", true)

  object KeepSocialUserCandidate
      extends FSParam[Boolean]("frs_receiver_holdback_keep_social_user_candidate", true)

  case object EnableGFSSocialProofTransform
      extends FSParam("social_proof_transform_use_graph_feature_service", true)

  case object EnableWhoToFollowProducts extends FSParam("who_to_follow_product_enabled", true)

  case object CandidateSourcesToFilter
      extends FSEnumParam[CandidateSourceType.type](
        "candidate_sources_type_filter_id",
        CandidateSourceType.None,
        CandidateSourceType)

  object EnableRecommendationFlowLogs
      extends FSParam[Boolean]("frs_recommendation_flow_logs_enabled", false)
}
