package com.X.frigate.pushservice

import com.X.frigate.common.base.Candidate
import com.X.frigate.common.base.SocialGraphServiceRelationshipMap
import com.X.frigate.common.base.TweetAuthor
import com.X.frigate.common.rec_types.RecTypes.isInNetworkTweetType
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.hermit.predicate.Predicate

package object predicate {
  implicit class CandidatesWithAuthorFollowPredicates(
    predicate: Predicate[
      PushCandidate with TweetAuthor with SocialGraphServiceRelationshipMap
    ]) {
    def applyOnlyToAuthorBeingFollowPredicates: Predicate[Candidate] =
      predicate.optionalOn[Candidate](
        {
          case candidate: PushCandidate with TweetAuthor with SocialGraphServiceRelationshipMap
              if isInNetworkTweetType(candidate.commonRecType) =>
            Some(candidate)
          case _ =>
            None
        },
        missingResult = true
      )
  }

  implicit class TweetCandidateWithTweetAuthor(
    predicate: Predicate[
      PushCandidate with TweetAuthor with SocialGraphServiceRelationshipMap
    ]) {
    def applyOnlyToBasicTweetPredicates: Predicate[Candidate] =
      predicate.optionalOn[Candidate](
        {
          case candidate: PushCandidate with TweetAuthor with SocialGraphServiceRelationshipMap
              if isInNetworkTweetType(candidate.commonRecType) =>
            Some(candidate)
          case _ =>
            None
        },
        missingResult = true
      )
  }
}
