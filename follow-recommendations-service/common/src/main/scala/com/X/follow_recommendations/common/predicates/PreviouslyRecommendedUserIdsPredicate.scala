package com.X.follow_recommendations.common.predicates

import com.X.follow_recommendations.common.base.Predicate
import com.X.follow_recommendations.common.base.PredicateResult
import com.X.follow_recommendations.common.models.CandidateUser
import com.X.follow_recommendations.common.models.FilterReason
import com.X.follow_recommendations.common.models.HasPreviousRecommendationsContext
import com.X.stitch.Stitch
import javax.inject.Singleton

@Singleton
class PreviouslyRecommendedUserIdsPredicate
    extends Predicate[(HasPreviousRecommendationsContext, CandidateUser)] {
  override def apply(
    pair: (HasPreviousRecommendationsContext, CandidateUser)
  ): Stitch[PredicateResult] = {

    val (targetUser, candidate) = pair

    val previouslyRecommendedUserIDs = targetUser.previouslyRecommendedUserIDs

    if (!previouslyRecommendedUserIDs.contains(candidate.id)) {
      PreviouslyRecommendedUserIdsPredicate.ValidStitch
    } else {
      PreviouslyRecommendedUserIdsPredicate.AlreadyRecommendedStitch
    }
  }
}

object PreviouslyRecommendedUserIdsPredicate {
  val ValidStitch: Stitch[PredicateResult.Valid.type] = Stitch.value(PredicateResult.Valid)
  val AlreadyRecommendedStitch: Stitch[PredicateResult.Invalid] =
    Stitch.value(PredicateResult.Invalid(Set(FilterReason.AlreadyRecommended)))
}
