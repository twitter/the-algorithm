package com.twitter.follow_recommendations.flows.content_recommender_flow

object ContentRecommenderFlowFeatureSwitchKeys {
  val TargetUserEligible = "content_recommender_flow_target_eligible"
  val ResultSize = "content_recommender_flow_result_size"
  val BatchSize = "content_recommender_flow_batch_size"
  val RecentFollowingPredicateBudgetInMillisecond =
    "content_recommender_flow_recent_following_predicate_budget_in_ms"
  val CandidateGenerationBudgetInMillisecond =
    "content_recommender_flow_candidate_generation_budget_in_ms"
  val EnableRecentFollowingPredicate = "content_recommender_flow_enable_recent_following_predicate"
  val EnableGizmoduckPredicate = "content_recommender_flow_enable_gizmoduck_predicate"
  val EnableInactivePredicate = "content_recommender_flow_enable_inactive_predicate"
  val EnableInvalidTargetCandidateRelationshipPredicate =
    "content_recommender_flow_enable_invalid_target_candidate_relationship_predicate"
  val IncludeActivityBasedCandidateSource =
    "content_recommender_flow_include_activity_based_candidate_source"
  val IncludeSocialBasedCandidateSource =
    "content_recommender_flow_include_social_based_candidate_source"
  val IncludeGeoBasedCandidateSource =
    "content_recommender_flow_include_geo_based_candidate_source"
  val IncludeHomeTimelineTweetRecsCandidateSource =
    "content_recommender_flow_include_home_timeline_tweet_recs_candidate_source"
  val IncludeSocialProofEnforcedCandidateSource =
    "content_recommender_flow_include_social_proof_enforced_candidate_source"
  val IncludeNewFollowingNewFollowingExpansionCandidateSource =
    "content_recommender_flow_include_new_following_new_following_expansion_candidate_source"
  val IncludeMoreGeoBasedCandidateSource =
    "content_recommender_flow_include_more_geo_based_candidate_source"
  val GetFollowersFromSgs = "content_recommender_flow_get_followers_from_sgs"
  val EnableInvalidRelationshipPredicate =
    "content_recommender_flow_enable_invalid_relationship_predicate"

  // Candidate source weight param keys
  // Social based
  val ForwardPhoneBookSourceWeight =
    "content_recommender_flow_candidate_source_weight_forward_phone_book"
  val ForwardEmailBookSourceWeight =
    "content_recommender_flow_candidate_source_weight_forward_email_book"
  val ReversePhoneBookSourceWeight =
    "content_recommender_flow_candidate_source_weight_reverse_phone_book"
  val ReverseEmailBookSourceWeight =
    "content_recommender_flow_candidate_source_weight_reverse_email_book"
  val OfflineStrongTiePredictionSourceWeight =
    "content_recommender_flow_candidate_source_weight_offline_stp"
  val TriangularLoopsSourceWeight =
    "content_recommender_flow_candidate_source_weight_triangular_loops"
  val UserUserGraphSourceWeight = "content_recommender_flow_candidate_source_weight_user_user_graph"
  val NewFollowingNewFollowingExpansionSourceWeight =
    "content_recommender_flow_candidate_source_weight_new_following_new_following_expansion"
  // Activity based
  val NewFollowingSimilarUserSourceWeight =
    "content_recommender_flow_candidate_source_weight_new_following_similar_user"
  val RecentEngagementSimilarUserSourceWeight =
    "content_recommender_flow_candidate_source_weight_recent_engagement_similar_user"
  val RepeatedProfileVisitsSourceWeight =
    "content_recommender_flow_candidate_source_weight_repeated_profile_visits"
  val RealGraphOonSourceWeight = "content_recommender_flow_candidate_source_weight_real_graph_oon"
  // Geo based
  val PopCountrySourceWeight = "content_recommender_flow_candidate_source_weight_pop_country"
  val PopGeohashSourceWeight = "content_recommender_flow_candidate_source_weight_pop_geohash"
  val PopCountryBackfillSourceWeight =
    "content_recommender_flow_candidate_source_weight_pop_country_backfill"
  val PPMILocaleFollowSourceWeight =
    "content_recommender_flow_candidate_source_weight_ppmi_locale_follow"
  val TopOrganicFollowsAccountsSourceWeight =
    "content_recommender_flow_candidate_source_weight_top_organic_follow_account"
  val CrowdSearchAccountSourceWeight =
    "content_recommender_flow_candidate_source_weight_crowd_search_account"
}
