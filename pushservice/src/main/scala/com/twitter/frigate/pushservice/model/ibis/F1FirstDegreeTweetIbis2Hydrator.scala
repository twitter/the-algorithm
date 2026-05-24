package com.twitter.frigate.pushservice.model.ibis

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.base.F1FirstDegree
import com.twitter.frigate.common.base.TweetAuthorDetails
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.util.Future

trait F1FirstDegreeTweetIbis2HydratorForCandidate
    extends TweetCandidateIbis2Hydrator
    with RankedSocialContextIbis2Hydrator {
  self: PushCandidate with F1FirstDegree with TweetAuthorDetails =>

  override lazy val scopedStats: StatsReceiver = statsReceiver.scope(getClass.getSimpleName)

  override lazy val tweetModelValues: Future[Map[String, String]] = {
    for {
      superModelValues <- super.tweetModelValues
      tweetInlineModelValues <- tweetInlineActionModelValue
    } yield {
      superModelValues ++ otherModelValues ++ mediaModelValue ++ tweetInlineModelValues ++ inlineVideoMediaMap
    }
  }
}
