package com.twitter.follow_recommendations.flows.content_recommender_flow

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.base.CandidateSourceRegistry
import com.twitter.follow_recommendations.common.candidate_sources.addressbook.ForwardEmailBookSource
import com.twitter.follow_recommendations.common.candidate_sources.addressbook.ForwardPhoneBookSource
import com.twitter.follow_recommendations.common.candidate_sources.addressbook.ReverseEmailBookSource
import com.twitter.follow_recommendations.common.candidate_sources.addressbook.ReversePhoneBookSource
import com.twitter.follow_recommendations.common.candidate_sources.geo.PopCountryBackFillSource
import com.twitter.follow_recommendations.common.candidate_sources.geo.PopCountrySource
import com.twitter.follow_recommendations.common.candidate_sources.geo.PopGeohashSource
import com.twitter.follow_recommendations.common.candidate_sources.crowd_search_accounts.CrowdSearchAccountsSource
import com.twitter.follow_recommendations.common.candidate_sources.ppmi_locale_follow.PPMILocaleFollowSource
import com.twitter.follow_recommendations.common.candidate_sources.top_organic_follows_accounts.TopOrganicFollowsAccountsSource
import com.twitter.follow_recommendations.common.candidate_sources.real_graph.RealGraphOonV2Source
import com.twitter.follow_recommendations.common.candidate_sources.recent_engagement.RepeatedProfileVisitsSource
import com.twitter.follow_recommendations.common.candidate_sources.sims_expansion.RecentEngagementSimilarUsersSource
import com.twitter.follow_recommendations.common.candidate_sources.sims_expansion.RecentFollowingSimilarUsersSource
import com.twitter.follow_recommendations.common.candidate_sources.socialgraph.RecentFollowingRecentFollowingExpansionSource
import com.twitter.follow_recommendations.common.candidate_sources.stp.OfflineStrongTiePredictionSource
import com.twitter.follow_recommendations.common.candidate_sources.triangular_loops.TriangularLoopsSource
import com.twitter.follow_recommendations.common.candidate_sources.user_user_graph.UserUserGraphCandidateSource
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentRecommenderFlowCandidateSourceRegistry @Inject() (
  // social based
  forwardPhoneBookSource: ForwardPhoneBookSource,
  forwardEmailBookSource: ForwardEmailBookSource,
  reversePhoneBookSource: ReversePhoneBookSource,
  reverseEmailBookSource: ReverseEmailBookSource,
  offlineStrongTiePredictionSource: OfflineStrongTiePredictionSource,
  triangularLoopsSource: TriangularLoopsSource,
  userUserGraphCandidateSource: UserUserGraphCandidateSource,
  realGraphOonSource: RealGraphOonV2Source,
  recentFollowingRecentFollowingExpansionSource: RecentFollowingRecentFollowingExpansionSource,
  // activity based
  recentFollowingSimilarUsersSource: RecentFollowingSimilarUsersSource,
  recentEngagementSimilarUsersSource: RecentEngagementSimilarUsersSource,
  repeatedProfileVisitsSource: RepeatedProfileVisitsSource,
  // geo based
  popCountrySource: PopCountrySource,
  popGeohashSource: PopGeohashSource,
  popCountryBackFillSource: PopCountryBackFillSource,
  crowdSearchAccountsSource: CrowdSearchAccountsSource,
  topOrganicFollowsAccountsSource: TopOrganicFollowsAccountsSource,
  ppmiLocaleFollowSource: PPMILocaleFollowSource,
  baseStatsReceiver: StatsReceiver)
    extends CandidateSourceRegistry[ContentRecommenderRequest, CandidateUser] {

  override val statsReceiver = baseStatsReceiver
    .scope("content_recommender_flow", "candidate_sources")

  override val sources: Set[CandidateSource[ContentRecommenderRequest, CandidateUser]] = Seq(
    forwardPhoneBookSource,
    forwardEmailBookSource,
    reversePhoneBookSource,
    reverseEmailBookSource,
    offlineStrongTiePredictionSource,
    triangularLoopsSource,
    userUserGraphCandidateSource,
    realGraphOonSource,
    recentFollowingRecentFollowingExpansionSource,
    recentFollowingSimilarUsersSource,
    recentEngagementSimilarUsersSource,
    repeatedProfileVisitsSource,
    popCountrySource,
    popGeohashSource,
    popCountryBackFillSource,
    crowdSearchAccountsSource,
    topOrganicFollowsAccountsSource,
    ppmiLocaleFollowSource,
  ).toSet
}
