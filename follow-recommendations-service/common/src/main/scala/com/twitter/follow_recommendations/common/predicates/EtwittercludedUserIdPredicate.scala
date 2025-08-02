package com.twitter.follow_recommendations.common.predicates

import com.twitter.follow_recommendations.common.base.Predicate
import com.twitter.follow_recommendations.common.base.PredicateResult
import com.twitter.follow_recommendations.common.models.FilterReason.EtwittercludedId
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.models.HasEtwittercludedUserIds
import com.twitter.stitch.Stitch

object EtwittercludedUserIdPredicate etwittertends Predicate[(HasEtwittercludedUserIds, CandidateUser)] {

  val ValidStitch: Stitch[PredicateResult.Valid.type] = Stitch.value(PredicateResult.Valid)
  val EtwittercludedStitch: Stitch[PredicateResult.Invalid] =
    Stitch.value(PredicateResult.Invalid(Set(EtwittercludedId)))

  override def apply(pair: (HasEtwittercludedUserIds, CandidateUser)): Stitch[PredicateResult] = {
    val (etwittercludedUserIds, candidate) = pair
    if (etwittercludedUserIds.etwittercludedUserIds.contains(candidate.id)) {
      EtwittercludedStitch
    } else {
      ValidStitch
    }
  }
}
