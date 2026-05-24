package com.twitter.frigate.pushservice.model

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.base.SocialContextActions
import com.twitter.frigate.common.base.TweetCandidate
import com.twitter.frigate.common.base.TweetDetails
import com.twitter.frigate.pushservice.model.PushTypes._
import com.twitter.frigate.pushservice.config.Config
import com.twitter.frigate.pushservice.predicate._
import com.twitter.frigate.pushservice.take.predicates.BasicTweetPredicatesForRFPH

case class TweetActionCandidatePredicates(override val config: Config)
    extends BasicTweetPredicatesForRFPH[
      PushCandidate with TweetCandidate with TweetDetails with SocialContextActions
    ] {

  implicit val statsReceiver: StatsReceiver = config.statsReceiver.scope(getClass.getSimpleName)

  override val preCandidateSpecificPredicates = List(PredicatesForCandidate.minSocialContext(1))

  override val postCandidateSpecificPredicates = List(
    PredicatesForCandidate.socialContextBeingFollowed(config.edgeStore),
    PredicatesForCandidate.socialContextBlockingOrMuting(config.edgeStore),
    PredicatesForCandidate.socialContextNotRetweetFollowing(config.edgeStore)
  )
}
