package com.twitter.frigate.pushservice.model

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.base.SocialContetwittertActions
import com.twitter.frigate.common.base.TweetCandidate
import com.twitter.frigate.common.base.TweetDetails
import com.twitter.frigate.pushservice.model.PushTypes._
import com.twitter.frigate.pushservice.config.Config
import com.twitter.frigate.pushservice.predicate._
import com.twitter.frigate.pushservice.take.predicates.BasicTweetPredicatesForRFPH

case class TweetActionCandidatePredicates(override val config: Config)
    etwittertends BasicTweetPredicatesForRFPH[
      PushCandidate with TweetCandidate with TweetDetails with SocialContetwittertActions
    ] {

  implicit val statsReceiver: StatsReceiver = config.statsReceiver.scope(getClass.getSimpleName)

  override val preCandidateSpecificPredicates = List(PredicatesForCandidate.minSocialContetwittert(1))

  override val postCandidateSpecificPredicates = List(
    PredicatesForCandidate.socialContetwittertBeingFollowed(config.edgeStore),
    PredicatesForCandidate.socialContetwittertBlockingOrMuting(config.edgeStore),
    PredicatesForCandidate.socialContetwittertNotRetweetFollowing(config.edgeStore)
  )
}
