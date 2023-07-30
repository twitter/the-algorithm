package com.X.frigate.pushservice.model.ibis

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.F1FirstDegree
import com.X.frigate.common.base.TweetAuthorDetails
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.util.Future

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
