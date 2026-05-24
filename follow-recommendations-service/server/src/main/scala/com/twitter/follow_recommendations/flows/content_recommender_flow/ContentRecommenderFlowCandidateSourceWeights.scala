package com.twitter.follow_recommendations.flows.content_recommender_flow

import com.twitter.follow_recommendations.common.candidate_sources.addressbook.ForwardEmailBookSource
import com.twitter.follow_recommendations.common.candidate_sources.addressbook.ForwardPhoneBookSource
import com.twitter.follow_recommendations.common.candidate_sources.addressbook.ReverseEmailBookSource
import com.twitter.follow_recommendations.common.candidate_sources.addressbook.ReversePhoneBookSource
import com.twitter.follow_recommendations.common.candidate_sources.geo.PopCountryBackFillSource
import com.twitter.follow_recommendations.common.candidate_sources.geo.PopCountrySource
import com.twitter.follow_recommendations.common.candidate_sources.geo.PopGeohashSource
import com.twitter.follow_recommendations.common.candidate_sources.real_graph.RealGraphOonV2Source
import com.twitter.follow_recommendations.common.candidate_sources.recent_engagement.RepeatedProfileVisitsSource
import com.twitter.follow_recommendations.common.candidate_sources.sims_expansion.RecentEngagementSimilarUsersSource
import com.twitter.follow_recommendations.common.candidate_sources.sims_expansion.RecentFollowingSimilarUsersSource
import com.twitter.follow_recommendations.common.candidate_sources.stp.OfflineStrongTiePredictionSource
import com.twitter.follow_recommendations.common.candidate_sources.triangular_loops.TriangularLoopsSource
import com.twitter.follow_recommendations.common.candidate_sources.user_user_graph.UserUserGraphCandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.follow_recommendations.common.candidate_sources.crowd_search_accounts.CrowdSearchAccountsSource
import com.twitter.follow_recommendations.common.candidate_sources.ppmi_locale_follow.PPMILocaleFollowSource
import com.twitter.follow_recommendations.common.candidate_sources.socialgraph.RecentFollowingRecentFollowingExpansionSource
import com.twitter.follow_recommendations.common.candidate_sources.top_organic_follows_accounts.TopOrganicFollowsAccountsSource
import com.twitter.timelines.configapi.Params

object ContentRecommenderFlowCandidateSourceWeights {

  def getWeights(
    params: Params
  ): Map[CandidateSourceIdentifier, Double] = {
    Map[CandidateSourceIdentifier, Double](
      // Social based
      UserUserGraphCandidateSource.Identifier -> params(
        ContentRecommenderFlowCandidateSourceWeightsParams.UserUserGraphSourceWeight),
      ForwardPhoneBookSource.Identifier -> params(
        ContentRecommenderFlowCandidateSourceWeightsParams.ForwardPhoneBookSourceWeight),
      ReversePhoneBookSource.Identifier -> params(
        ContentRecommenderFlowCandidateSourceWeightsParams.ReversePhoneBookSourceWeight),
      ForwardEmailBookSource.Identifier -> params(
        ContentRecommenderFlowCandidateSourceWeightsParams.ForwardEmailBookSourceWeight),
      ReverseEmailBookSource.Identifier -> params(
        ContentRecommenderFlowCandidateSourceWeightsParams.ReverseEmailBookSourceWeight),
      TriangularLoopsSource.Identifier -> params(
        ContentRecommenderFlowCandidateSourceWeightsParams.TriangularLoopsSourceWeight),
      OfflineStrongTiePredictionSource.Identifier -> params(
        ContentRecommenderFlowCandidateSourceWeightsParams.OfflineStrongTiePredictionSourceWeight),
      RecentFollowingRecentFollowingExpansionSource.Identifier -> params(
        ContentRecommenderFlowCandidateSourceWeightsParams.NewFollowingNewFollowingExpansionSourceWeight),
      RecentFollowingSimilarUsersSource.Identifier -> params(
        ContentRecommenderFlowCandidateSourceWeightsParams.NewFollowingSimilarUserSourceWeight),
      // Activity based
      RealGraphOonV2Source.Identifier -> params(
        ContentRecommenderFlowCandidateSourceWeightsParams.RealGraphOonSourceWeight),
      RecentEngagementSimilarUsersSource.Identifier -> params(
        ContentRecommenderFlowCandidateSourceWeightsParams.RecentEngagementSimilarUserSourceWeight),
      RepeatedProfileVisitsSource.Identifier -> params(
        ContentRecommenderFlowCandidateSourceWeightsParams.RepeatedProfileVisitsSourceWeight),
      // Geo based
      PopCountrySource.Identifier -> params(
        ContentRecommenderFlowCandidateSourceWeightsParams.PopCountrySourceWeight),
      PopGeohashSource.Identifier -> params(
        ContentRecommenderFlowCandidateSourceWeightsParams.PopGeohashSourceWeight),
      PopCountryBackFillSource.Identifier -> params(
        ContentRecommenderFlowCandidateSourceWeightsParams.PopCountryBackfillSourceWeight),
      PPMILocaleFollowSource.Identifier -> params(
        ContentRecommenderFlowCandidateSourceWeightsParams.PPMILocaleFollowSourceWeight),
      CrowdSearchAccountsSource.Identifier -> params(
        ContentRecommenderFlowCandidateSourceWeightsParams.CrowdSearchAccountSourceWeight),
      TopOrganicFollowsAccountsSource.Identifier -> params(
        ContentRecommenderFlowCandidateSourceWeightsParams.TopOrganicFollowsAccountsSourceWeight),
    )
  }
}
