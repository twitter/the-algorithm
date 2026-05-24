package com.twitter.follow_recommendations.flows.post_nux_ml

object PostNuxMlFlowCandidateSourceWeightsFeatureSwitchKeys {
  val CandidateWeightCrowdSearch = "post_nux_ml_flow_candidate_source_weights_user_crowd_search"
  val CandidateWeightTopOrganicFollow =
    "post_nux_ml_flow_candidate_source_weights_top_organic_follow"
  val CandidateWeightPPMILocaleFollow =
    "post_nux_ml_flow_candidate_source_weights_user_ppmi_locale_follow"
  val CandidateWeightForwardEmailBook =
    "post_nux_ml_flow_candidate_source_weights_user_forward_email_book"
  val CandidateWeightForwardPhoneBook =
    "post_nux_ml_flow_candidate_source_weights_user_forward_phone_book"
  val CandidateWeightOfflineStrongTiePrediction =
    "post_nux_ml_flow_candidate_source_weights_user_offline_strong_tie_prediction"
  val CandidateWeightOnlineStp = "post_nux_ml_flow_candidate_source_weights_user_online_stp"
  val CandidateWeightPopCountry = "post_nux_ml_flow_candidate_source_weights_user_pop_country"
  val CandidateWeightPopGeohash = "post_nux_ml_flow_candidate_source_weights_user_pop_geohash"
  val CandidateWeightPopGeohashQualityFollow =
    "post_nux_ml_flow_candidate_source_weights_user_pop_geohash_quality_follow"
  val CandidateWeightPopGeoBackfill =
    "post_nux_ml_flow_candidate_source_weights_user_pop_geo_backfill"
  val CandidateWeightRecentFollowingSimilarUsers =
    "post_nux_ml_flow_candidate_source_weights_user_recent_following_similar_users"
  val CandidateWeightRecentEngagementDirectFollowSalsaExpansion =
    "post_nux_ml_flow_candidate_source_weights_user_recent_engagement_direct_follow_salsa_expansion"
  val CandidateWeightRecentEngagementNonDirectFollow =
    "post_nux_ml_flow_candidate_source_weights_user_recent_engagement_non_direct_follow"
  val CandidateWeightRecentEngagementSimilarUsers =
    "post_nux_ml_flow_candidate_source_weights_user_recent_engagement_similar_users"
  val CandidateWeightRepeatedProfileVisits =
    "post_nux_ml_flow_candidate_source_weights_user_repeated_profile_visits"
  val CandidateWeightFollow2vecNearestNeighbors =
    "post_nux_ml_flow_candidate_source_weights_user_follow2vec_nearest_neighbors"
  val CandidateWeightReverseEmailBook =
    "post_nux_ml_flow_candidate_source_weights_user_reverse_email_book"
  val CandidateWeightReversePhoneBook =
    "post_nux_ml_flow_candidate_source_weights_user_reverse_phone_book"
  val CandidateWeightTriangularLoops =
    "post_nux_ml_flow_candidate_source_weights_user_triangular_loops"
  val CandidateWeightTwoHopRandomWalk =
    "post_nux_ml_flow_candidate_source_weights_user_two_hop_random_walk"
  val CandidateWeightUserUserGraph =
    "post_nux_ml_flow_candidate_source_weights_user_user_user_graph"
  val CandidateWeightRealGraphOonV2 =
    "post_nux_ml_flow_candidate_source_weights_user_real_graph_oon_v2"
}
