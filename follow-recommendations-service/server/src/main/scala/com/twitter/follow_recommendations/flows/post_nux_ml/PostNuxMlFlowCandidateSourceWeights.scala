package com.ExTwitter.follow_recommendations.flows.post_nux_ml

import com.ExTwitter.follow_recommendations.common.candidate_sources.addressbook.ForwardEmailBookSource
import com.ExTwitter.follow_recommendations.common.candidate_sources.addressbook.ForwardPhoneBookSource
import com.ExTwitter.follow_recommendations.common.candidate_sources.addressbook.ReverseEmailBookSource
import com.ExTwitter.follow_recommendations.common.candidate_sources.addressbook.ReversePhoneBookSource
import com.ExTwitter.follow_recommendations.common.candidate_sources.crowd_search_accounts.CrowdSearchAccountsSource
import com.ExTwitter.follow_recommendations.common.candidate_sources.geo.PopCountryBackFillSource
import com.ExTwitter.follow_recommendations.common.candidate_sources.geo.PopCountrySource
import com.ExTwitter.follow_recommendations.common.candidate_sources.geo.PopGeohashQualityFollowSource
import com.ExTwitter.follow_recommendations.common.candidate_sources.geo.PopGeohashSource
import com.ExTwitter.follow_recommendations.common.candidate_sources.ppmi_locale_follow.PPMILocaleFollowSource
import com.ExTwitter.follow_recommendations.common.candidate_sources.real_graph.RealGraphOonV2Source
import com.ExTwitter.follow_recommendations.common.candidate_sources.recent_engagement.RecentEngagementNonDirectFollowSource
import com.ExTwitter.follow_recommendations.common.candidate_sources.recent_engagement.RepeatedProfileVisitsSource
import com.ExTwitter.follow_recommendations.common.candidate_sources.salsa.RecentEngagementDirectFollowSalsaExpansionSource
import com.ExTwitter.follow_recommendations.common.candidate_sources.sims_expansion.RecentEngagementSimilarUsersSource
import com.ExTwitter.follow_recommendations.common.candidate_sources.sims_expansion.RecentFollowingSimilarUsersSource
import com.ExTwitter.follow_recommendations.common.candidate_sources.sims.Follow2vecNearestNeighborsStore
import com.ExTwitter.follow_recommendations.common.candidate_sources.stp.BaseOnlineSTPSource
import com.ExTwitter.follow_recommendations.common.candidate_sources.stp.OfflineStrongTiePredictionSource
import com.ExTwitter.follow_recommendations.common.candidate_sources.top_organic_follows_accounts.TopOrganicFollowsAccountsSource
import com.ExTwitter.follow_recommendations.common.candidate_sources.triangular_loops.TriangularLoopsSource
import com.ExTwitter.follow_recommendations.common.candidate_sources.two_hop_random_walk.TwoHopRandomWalkSource
import com.ExTwitter.follow_recommendations.common.candidate_sources.user_user_graph.UserUserGraphCandidateSource
import com.ExTwitter.follow_recommendations.flows.post_nux_ml.PostNuxMlCandidateSourceWeightParams._
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.ExTwitter.timelines.configapi.Params

object PostNuxMlFlowCandidateSourceWeights {

  def getWeights(params: Params): Map[CandidateSourceIdentifier, Double] = {
    Map[CandidateSourceIdentifier, Double](
      // Social based
      PPMILocaleFollowSource.Identifier -> params(CandidateWeightPPMILocaleFollow),
      Follow2vecNearestNeighborsStore.IdentifierF2vLinearRegression -> params(
        CandidateWeightFollow2vecNearestNeighbors),
      RecentFollowingSimilarUsersSource.Identifier -> params(
        CandidateWeightRecentFollowingSimilarUsers),
      BaseOnlineSTPSource.Identifier -> params(CandidateWeightOnlineStp),
      OfflineStrongTiePredictionSource.Identifier -> params(
        CandidateWeightOfflineStrongTiePrediction),
      ForwardEmailBookSource.Identifier -> params(CandidateWeightForwardEmailBook),
      ForwardPhoneBookSource.Identifier -> params(CandidateWeightForwardPhoneBook),
      ReverseEmailBookSource.Identifier -> params(CandidateWeightReverseEmailBook),
      ReversePhoneBookSource.Identifier -> params(CandidateWeightReversePhoneBook),
      TriangularLoopsSource.Identifier -> params(CandidateWeightTriangularLoops),
      TwoHopRandomWalkSource.Identifier -> params(CandidateWeightTwoHopRandomWalk),
      UserUserGraphCandidateSource.Identifier -> params(CandidateWeightUserUserGraph),
      // Geo based
      PopCountrySource.Identifier -> params(CandidateWeightPopCountry),
      PopCountryBackFillSource.Identifier -> params(CandidateWeightPopGeoBackfill),
      PopGeohashSource.Identifier -> params(CandidateWeightPopGeohash),
      PopGeohashQualityFollowSource.Identifier -> params(CandidateWeightPopGeohashQualityFollow),
      CrowdSearchAccountsSource.Identifier -> params(CandidateWeightCrowdSearch),
      TopOrganicFollowsAccountsSource.Identifier -> params(CandidateWeightTopOrganicFollow),
      // Engagement based
      RealGraphOonV2Source.Identifier -> params(CandidateWeightRealGraphOonV2),
      RecentEngagementNonDirectFollowSource.Identifier -> params(
        CandidateWeightRecentEngagementNonDirectFollow),
      RecentEngagementSimilarUsersSource.Identifier -> params(
        CandidateWeightRecentEngagementSimilarUsers),
      RepeatedProfileVisitsSource.Identifier -> params(CandidateWeightRepeatedProfileVisits),
      RecentEngagementDirectFollowSalsaExpansionSource.Identifier -> params(
        CandidateWeightRecentEngagementDirectFollowSalsaExpansion),
    )
  }
}
