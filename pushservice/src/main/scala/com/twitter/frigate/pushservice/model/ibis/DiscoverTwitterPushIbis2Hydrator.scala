package com.twitter.frigate.pushservice.model.ibis

import com.twitter.frigate.common.base.DiscoverTwitterCandidate
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.util.PushIbisUtil.mergeFutModelValues
import com.twitter.util.Future

trait DiscoverTwitterPushIbis2Hydrator extends Ibis2HydratorForCandidate {
  self: PushCandidate with DiscoverTwitterCandidate =>

  private lazy val targetModelValues: Map[String, String] = Map(
    "target_user" -> target.targetId.toString
  )

  override lazy val modelValues: Future[Map[String, String]] =
    mergeFutModelValues(super.modelValues, Future.value(targetModelValues))
}
