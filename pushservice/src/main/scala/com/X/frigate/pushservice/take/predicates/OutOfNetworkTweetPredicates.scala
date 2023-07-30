package com.X.frigate.pushservice.take.predicates

import com.X.frigate.common.base.TweetCandidate
import com.X.frigate.common.base.TweetDetails
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.predicate.PredicatesForCandidate
import com.X.hermit.predicate.NamedPredicate

trait OutOfNetworkTweetPredicates[C <: PushCandidate with TweetCandidate with TweetDetails]
    extends BasicTweetPredicatesForRFPH[C] {

  override lazy val preCandidateSpecificPredicates: List[NamedPredicate[C]] =
    List(
      PredicatesForCandidate.authorNotBeingFollowed(config.edgeStore)
    )
}
