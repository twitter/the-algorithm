package com.X.follow_recommendations.flows.post_nux_ml

import com.X.finagle.stats.StatsReceiver
import com.X.follow_recommendations.common.base.CandidateSourceRegistry
import com.X.follow_recommendations.common.base.EnrichedCandidateSource
import com.X.follow_recommendations.common.candidate_sources.addressbook.ForwardEmailBookSource
import com.X.follow_recommendations.common.candidate_sources.addressbook.ForwardPhoneBookSource
import com.X.follow_recommendations.common.candidate_sources.addressbook.ReverseEmailBookSource
import com.X.follow_recommendations.common.candidate_sources.addressbook.ReversePhoneBookSource
import com.X.follow_recommendations.common.candidate_sources.crowd_search_accounts.CrowdSearchAccountsSource
import com.X.follow_recommendations.common.candidate_sources.top_organic_follows_accounts.TopOrganicFollowsAccountsSource
import com.X.follow_recommendations.common.candidate_sources.geo.PopCountrySource
import com.X.follow_recommendations.common.candidate_sources.geo.PopCountryBackFillSource
import com.X.follow_recommendations.common.candidate_sources.geo.PopGeohashQualityFollowSource
import com.X.follow_recommendations.common.candidate_sources.geo.PopGeohashSource
import com.X.follow_recommendations.common.candidate_sources.ppmi_locale_follow.PPMILocaleFollowSource
import com.X.follow_recommendations.common.candidate_sources.real_graph.RealGraphOonV2Source
import com.X.follow_recommendations.common.candidate_sources.recent_engagement.RecentEngagementNonDirectFollowSource
import com.X.follow_recommendations.common.candidate_sources.recent_engagement.RepeatedProfileVisitsSource
import com.X.follow_recommendations.common.candidate_sources.salsa.RecentEngagementDirectFollowSalsaExpansionSource
import com.X.follow_recommendations.common.candidate_sources.sims.LinearRegressionFollow2vecNearestNeighborsStore
import com.X.follow_recommendations.common.candidate_sources.sims_expansion.RecentEngagementSimilarUsersSource
import com.X.follow_recommendations.common.candidate_sources.sims_expansion.RecentFollowingSimilarUsersSource
import com.X.follow_recommendations.common.candidate_sources.stp.OnlineSTPSourceScorer
import com.X.follow_recommendations.common.candidate_sources.stp.OfflineStrongTiePredictionSource
import com.X.follow_recommendations.common.candidate_sources.triangular_loops.TriangularLoopsSource
import com.X.follow_recommendations.common.candidate_sources.user_user_graph.UserUserGraphCandidateSource
import com.X.follow_recommendations.common.models.CandidateUser
import com.X.product_mixer.core.functional_component.candidate_source.CandidateSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostNuxMlCandidateSourceRegistry @Inject() (
  crowdSearchAccountsCandidateSource: CrowdSearchAccountsSource,
  topOrganicFollowsAccountsSource: TopOrganicFollowsAccountsSource,
  linearRegressionfollow2vecNearestNeighborsStore: LinearRegressionFollow2vecNearestNeighborsStore,
  forwardEmailBookSource: ForwardEmailBookSource,
  forwardPhoneBookSource: ForwardPhoneBookSource,
  offlineStrongTiePredictionSource: OfflineStrongTiePredictionSource,
  onlineSTPSource: OnlineSTPSourceScorer,
  popCountrySource: PopCountrySource,
  popCountryBackFillSource: PopCountryBackFillSource,
  popGeohashSource: PopGeohashSource,
  recentEngagementDirectFollowSimilarUsersSource: RecentEngagementSimilarUsersSource,
  recentEngagementNonDirectFollowSource: RecentEngagementNonDirectFollowSource,
  recentEngagementDirectFollowSalsaExpansionSource: RecentEngagementDirectFollowSalsaExpansionSource,
  recentFollowingSimilarUsersSource: RecentFollowingSimilarUsersSource,
  realGraphOonV2Source: RealGraphOonV2Source,
  repeatedProfileVisitSource: RepeatedProfileVisitsSource,
  reverseEmailBookSource: ReverseEmailBookSource,
  reversePhoneBookSource: ReversePhoneBookSource,
  triangularLoopsSource: TriangularLoopsSource,
  userUserGraphCandidateSource: UserUserGraphCandidateSource,
  ppmiLocaleFollowSource: PPMILocaleFollowSource,
  popGeohashQualityFollowSource: PopGeohashQualityFollowSource,
  baseStatsReceiver: StatsReceiver,
) extends CandidateSourceRegistry[PostNuxMlRequest, CandidateUser] {
  import EnrichedCandidateSource._

  override val statsReceiver = baseStatsReceiver
    .scope("post_nux_ml_flow", "candidate_sources")

  // sources primarily based on social graph signals
  private[this] val socialSources = Seq(
    linearRegressionfollow2vecNearestNeighborsStore.mapKeys[PostNuxMlRequest](
      _.getOptionalUserId.toSeq),
    forwardEmailBookSource,
    forwardPhoneBookSource,
    offlineStrongTiePredictionSource,
    onlineSTPSource,
    reverseEmailBookSource,
    reversePhoneBookSource,
    triangularLoopsSource,
  )

  // sources primarily based on geo signals
  private[this] val geoSources = Seq(
    popCountrySource,
    popCountryBackFillSource,
    popGeohashSource,
    popGeohashQualityFollowSource,
    topOrganicFollowsAccountsSource,
    crowdSearchAccountsCandidateSource,
    ppmiLocaleFollowSource,
  )

  // sources primarily based on recent activity signals
  private[this] val activitySources = Seq(
    repeatedProfileVisitSource,
    recentEngagementDirectFollowSalsaExpansionSource.mapKeys[PostNuxMlRequest](
      _.getOptionalUserId.toSeq),
    recentEngagementDirectFollowSimilarUsersSource,
    recentEngagementNonDirectFollowSource.mapKeys[PostNuxMlRequest](_.getOptionalUserId.toSeq),
    recentFollowingSimilarUsersSource,
    realGraphOonV2Source,
    userUserGraphCandidateSource,
  )

  override val sources: Set[CandidateSource[PostNuxMlRequest, CandidateUser]] = (
    geoSources ++ socialSources ++ activitySources
  ).toSet
}
