package com.twitter.frigate.pushservice.take.predicates

import com.twitter.frigate.common.base.TweetCandidate
import com.twitter.frigate.common.base.TweetDetails
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.predicate.PredicatesForCandidate
import com.twitter.hermit.predicate.NamedPredicate

trait OutOfNetworkTweetPredicates[C <: PushCandidate with TweetCandidate with TweetDetails]
    extends BasicTweetPredicatesForRFPH[C] {

  override lazy val preCandidateSpecificPredicates: List[NamedPredicate[C]] =
    List(
      PredicatesForCandidate.authorNotBeingFollowed(config.edgeStore)
    )
}
