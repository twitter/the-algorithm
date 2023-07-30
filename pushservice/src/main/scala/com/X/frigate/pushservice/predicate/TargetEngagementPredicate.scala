package com.X.frigate.pushservice.predicate

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.TweetCandidate
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.hermit.predicate.NamedPredicate
import com.X.hermit.predicate.tweetypie.EngagementsPredicate
import com.X.hermit.predicate.tweetypie.Perspective
import com.X.hermit.predicate.tweetypie.UserTweet
import com.X.storehaus.ReadableStore

object TargetEngagementPredicate {
  val name = "target_engagement"
  def apply(
    perspectiveStore: ReadableStore[UserTweet, Perspective],
    defaultForMissing: Boolean
  )(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[PushCandidate with TweetCandidate] = {
    EngagementsPredicate(perspectiveStore, defaultForMissing)
      .on { candidate: PushCandidate with TweetCandidate =>
        UserTweet(candidate.target.targetId, candidate.tweetId)
      }
      .withStats(statsReceiver.scope(s"predicate_$name"))
      .withName(name)
  }
}
