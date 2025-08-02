package com.twitter.follow_recommendations.flows.post_nutwitter_ml

object PostNutwitterMlFlowFeatureSwitchKeys {
  val UseMlRanker = "post_nutwitter_ml_flow_use_ml_ranker"
  val EnableCandidateParamHydration = "post_nutwitter_ml_flow_enable_candidate_param_hydration"
  val OnlineSTPEnabled = "post_nutwitter_ml_flow_online_stp_source_enabled"
  val Follow2VecLinearRegressionEnabled = "post_nutwitter_ml_flow_follow_to_vec_lr_source_enabled"
  val EnableRandomDataCollection = "post_nutwitter_ml_flow_random_data_collection_enabled"
  val EnableAdhocRanker = "post_nutwitter_ml_flow_adhoc_ranker_enabled"
  val EnableFatigueRanker = "post_nutwitter_ml_flow_fatigue_ranker_enabled"
  val EnableInterleaveRanker = "post_nutwitter_ml_flow_interleave_ranker_enabled"
  val IncludeRepeatedProfileVisitsCandidateSource =
    "post_nutwitter_ml_flow_include_repeated_profile_visits_candidate_source"
  val MLRankerBudget = "post_nutwitter_ml_flow_ml_ranker_budget_millis"
  val EnableNoShuffler = "post_nutwitter_ml_flow_no_shuffler"
  val SamplingTransformEnabled = "post_nutwitter_ml_flow_sampling_transform_enabled"
  val EtwittercludeNearZeroCandidates = "post_nutwitter_ml_flow_etwitterclude_near_zero_candidates"
  val EnableInterestsOptOutPredicate = "post_nutwitter_ml_flow_enable_interests_opt_out_predicate"
  val EnableRemoveAccountProofTransform = "post_nutwitter_ml_flow_enable_remove_account_proof_transform"
  val EnablePPMILocaleFollowSourceInPostNutwitter = "post_nutwitter_ml_flow_enable_ppmilocale_follow_source"
  val EnableInvalidRelationshipPredicate = "post_nutwitter_ml_flow_enable_invalid_relationship_predicate"
  val EnableRealGraphOonV2 = "post_nutwitter_ml_flow_enable_real_graph_oon_v2"
  val EnableSGSPredicate = "post_nutwitter_ml_flow_enable_sgs_predicate"
  val EnableHssPredicate = "post_nutwitter_ml_flow_enable_hss_predicate"
  val GetFollowersFromSgs = "post_nutwitter_ml_flow_get_followers_from_sgs"
  val TurnOffMLScorerQFThreshold = "post_nutwitter_ml_flow_turn_off_ml_scorer_threhsold"
}
