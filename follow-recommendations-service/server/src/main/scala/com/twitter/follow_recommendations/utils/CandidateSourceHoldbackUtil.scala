package com.twitter.follow_recommendations.utils

import com.twitter.follow_recommendations.common.candidate_sources.addressbook._
import com.twitter.follow_recommendations.common.candidate_sources.geo.PopCountrySource
import com.twitter.follow_recommendations.common.candidate_sources.geo.PopCountryBackFillSource
import com.twitter.follow_recommendations.common.candidate_sources.geo.PopGeoSource
import com.twitter.follow_recommendations.common.candidate_sources.geo.PopGeohashSource
import com.twitter.follow_recommendations.common.candidate_sources.ppmi_locale_follow.PPMILocaleFollowSource
import com.twitter.follow_recommendations.common.candidate_sources.recent_engagement.RecentEngagementNonDirectFollowSource
import com.twitter.follow_recommendations.common.candidate_sources.sims.SwitchingSimsSource
import com.twitter.follow_recommendations.common.candidate_sources.sims_expansion.RecentEngagementSimilarUsersSource
import com.twitter.follow_recommendations.common.candidate_sources.sims_expansion.RecentFollowingSimilarUsersSource
import com.twitter.follow_recommendations.common.candidate_sources.sims_expansion.RecentStrongEngagementDirectFollowSimilarUsersSource
import com.twitter.follow_recommendations.common.candidate_sources.socialgraph.RecentFollowingRecentFollowingExpansionSource
import com.twitter.follow_recommendations.common.candidate_sources.stp.MutualFollowStrongTiePredictionSource
import com.twitter.follow_recommendations.common.candidate_sources.stp.OfflineStrongTiePredictionSource
import com.twitter.follow_recommendations.common.candidate_sources.stp.BaseOnlineSTPSource
import com.twitter.follow_recommendations.common.candidate_sources.stp.SocialProofEnforcedOfflineStrongTiePredictionSource
import com.twitter.follow_recommendations.common.candidate_sources.triangular_loops.TriangularLoopsSource
import com.twitter.follow_recommendations.common.candidate_sources.two_hop_random_walk.TwoHopRandomWalkSource
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.configapi.params.GlobalParams
import com.twitter.follow_recommendations.models.CandidateSourceType
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.timelines.configapi.HasParams

trait CandidateSourceHoldbackUtil {
  import CandidateSourceHoldbackUtil._
  def filterCandidateSources[T <: HasParams](
    request: T,
    sources: Seq[CandidateSource[T, CandidateUser]]
  ): Seq[CandidateSource[T, CandidateUser]] = {
    val typeToFilter = request.params(GlobalParams.CandidateSourcesToFilter)
    val sourcesToFilter = CandidateSourceTypeToMap.get(typeToFilter).getOrElse(Set.empty)
    sources.filterNot { source => sourcesToFilter.contains(source.identifier) }
  }
}

object CandidateSourceHoldbackUtil {
  final val ContextualActivityCandidateSourceIds: Set[CandidateSourceIdentifier] =
    Set(
      RecentFollowingSimilarUsersSource.Identifier,
      RecentEngagementNonDirectFollowSource.Identifier,
      RecentEngagementSimilarUsersSource.Identifier,
      RecentStrongEngagementDirectFollowSimilarUsersSource.Identifier,
      SwitchingSimsSource.Identifier,
    )

  final val SocialCandidateSourceIds: Set[CandidateSourceIdentifier] =
    Set(
      ForwardEmailBookSource.Identifier,
      ForwardPhoneBookSource.Identifier,
      ReverseEmailBookSource.Identifier,
      ReversePhoneBookSource.Identifier,
      RecentFollowingRecentFollowingExpansionSource.Identifier,
      BaseOnlineSTPSource.Identifier,
      MutualFollowStrongTiePredictionSource.Identifier,
      OfflineStrongTiePredictionSource.Identifier,
      SocialProofEnforcedOfflineStrongTiePredictionSource.Identifier,
      TriangularLoopsSource.Identifier,
      TwoHopRandomWalkSource.Identifier
    )

  final val GeoCandidateSourceIds: Set[CandidateSourceIdentifier] =
    Set(
      PPMILocaleFollowSource.Identifier,
      PopCountrySource.Identifier,
      PopGeohashSource.Identifier,
      PopCountryBackFillSource.Identifier,
      PopGeoSource.Identifier,
    )

  final val CandidateSourceTypeToMap: Map[CandidateSourceType.Value, Set[
    CandidateSourceIdentifier
  ]] =
    Map(
      CandidateSourceType.Social -> SocialCandidateSourceIds,
      CandidateSourceType.ActivityContextual -> ContextualActivityCandidateSourceIds,
      CandidateSourceType.GeoAndInterests -> GeoCandidateSourceIds
    )
}
