package com.twitter.follow_recommendations.common.predicates

import com.twitter.follow_recommendations.common.base.Predicate
import com.twitter.follow_recommendations.common.base.PredicateResult
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.models.FilterReason
import com.twitter.follow_recommendations.common.models.HasPreviousRecommendationsContetwittert
import com.twitter.stitch.Stitch
import javatwitter.inject.Singleton

@Singleton
class PreviouslyRecommendedUserIdsPredicate
    etwittertends Predicate[(HasPreviousRecommendationsContetwittert, CandidateUser)] {
  override def apply(
    pair: (HasPreviousRecommendationsContetwittert, CandidateUser)
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
