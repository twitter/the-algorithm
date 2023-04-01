package com.twitter.follow_recommendations.common.constants

object GuiceNamedConstants {
  final val PRODUCER_SIDE_FEATURE_SWITCHES = "PRODUCER_SIDE_FEATURE_SWITCHES"
  final val CLIENT_EVENT_LOGGER = "CLIENT_EVENT_LOGGER"
  final val COSINE_FOLLOW_FETCHER = "cosine_follow_fetcher"
  final val COSINE_LIST_FETCHER = "cosine_list_fetcher"
  final val CURATED_CANDIDATES_FETCHER = "curated_candidates_fetcher"
  final val CURATED_COMPETITOR_ACCOUNTS_FETCHER = "curated_competitor_accounts_fetcher"
  final val POP_USERS_IN_PLACE_FETCHER = "pop_users_in_place_fetcher"
  final val PROFILE_SIDEBAR_BLACKLIST_SCANNER = "profile_sidebar_blacklist_scanner"
  final val REQUEST_LOGGER = "REQUEST_LOGGER"
  final val FLOW_LOGGER = "FLOW_LOGGER"
  final val REAL_TIME_INTERACTIONS_FETCHER = "real_time_interactions_fetcher"
  final val SIMS_FETCHER = "sims_fetcher"
  final val DBV2_SIMS_FETCHER = "dbv2_sims_fetcher"

  final val TRIANGULAR_LOOPS_FETCHER = "triangular_loops_fetcher"
  final val TWO_HOP_RANDOM_WALK_FETCHER = "two_hop_random_walk_fetcher"
  final val USER_RECOMMENDABILITY_FETCHER = "user_recommendability_fetcher"
  final val USER_STATE_FETCHER = "user_state_fetcher"
  final val UTT_ACCOUNT_RECOMMENDATIONS_FETCHER = "utt_account_recomendations_fetcher"
  final val UTT_SEED_ACCOUNTS_FETCHER = "utt_seed_accounts_fetcher"

  final val ELECTION_CANDIDATES_FETCHER = "election_candidates_fetcher"
  final val POST_NUX_WTF_FEATURES_FETCHER = "post_nux_wtf_features_fetcher"

  final val USER_USER_GRAPH_FETCHER = "user_user_graph_fetcher"
  final val DISMISS_STORE_SCANNER = "dismiss_store_scanner"
  final val LABELED_NOTIFICATION_FETCHER = "labeled_notification_scanner"

  final val STP_EP_SCORER = "stp_ep_scorer"
  final val STP_DBV2_SCORER = "stp_dbv2_scorer"
  final val STP_RAB_DBV2_SCORER = "stp_rab_dbv2_scorer"

  final val EXTENDED_NETWORK = "extended_network_candidates"

  // scoring client constants
  final val WTF_PROD_DEEPBIRDV2_CLIENT = "wtf_prod_deepbirdv2_client"

  // ann clients
  final val RELATABLE_ACCOUNTS_FETCHER = "relatable_accounts_fetcher"
}
