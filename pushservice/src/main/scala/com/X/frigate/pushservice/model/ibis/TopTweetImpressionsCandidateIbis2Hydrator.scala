package com.X.frigate.pushservice.model.ibis

import com.X.frigate.common.base.TopTweetImpressionsCandidate
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.util.PushIbisUtil.mergeFutModelValues
import com.X.util.Future

trait TopTweetImpressionsCandidateIbis2Hydrator extends Ibis2HydratorForCandidate {
  self: PushCandidate with TopTweetImpressionsCandidate =>

  private lazy val targetModelValues: Map[String, String] = {
    Map(
      "target_user" -> target.targetId.toString,
      "tweet" -> tweetId.toString,
      "impressions_count" -> impressionsCount.toString
    )
  }

  override lazy val modelValues: Future[Map[String, String]] =
    mergeFutModelValues(super.modelValues, Future.value(targetModelValues))
}
