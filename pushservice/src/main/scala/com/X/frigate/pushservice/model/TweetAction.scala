package com.X.frigate.pushservice.model

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.SocialContextActions
import com.X.frigate.common.base.TweetCandidate
import com.X.frigate.common.base.TweetDetails
import com.X.frigate.pushservice.model.PushTypes._
import com.X.frigate.pushservice.config.Config
import com.X.frigate.pushservice.predicate._
import com.X.frigate.pushservice.take.predicates.BasicTweetPredicatesForRFPH

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
