package com.twitter.home_mitwitterer.product.list_recommended_users.filter

import com.twitter.home_mitwitterer.product.list_recommended_users.feature_hydrator.RecentListMembersFeature
import com.twitter.home_mitwitterer.product.list_recommended_users.model.ListRecommendedUsersQuery
import com.twitter.product_mitwitterer.component_library.model.candidate.UserCandidate
import com.twitter.product_mitwitterer.core.functional_component.filter.Filter
import com.twitter.product_mitwitterer.core.functional_component.filter.FilterResult
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.FilterIdentifier
import com.twitter.stitch.Stitch

object PreviouslyServedUsersFilter etwittertends Filter[ListRecommendedUsersQuery, UserCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("PreviouslyServedUsers")

  override def apply(
    query: ListRecommendedUsersQuery,
    candidates: Seq[CandidateWithFeatures[UserCandidate]]
  ): Stitch[FilterResult[UserCandidate]] = {

    val recentListMembers = query.features.map(_.getOrElse(RecentListMembersFeature, Seq.empty))

    val servedUserIds = query.pipelineCursor.map(_.etwittercludedIds)

    val etwittercludedUserIds = (recentListMembers.getOrElse(Seq.empty) ++
      query.selectedUserIds.getOrElse(Seq.empty) ++
      query.etwittercludedUserIds.getOrElse(Seq.empty) ++
      servedUserIds.getOrElse(Seq.empty)).toSet

    val (removed, kept) =
      candidates.map(_.candidate).partition(candidate => etwittercludedUserIds.contains(candidate.id))

    Stitch.value(FilterResult(kept = kept, removed = removed))
  }
}
