package com.twitter.frigate.pushservice.model.ibis

import com.twitter.frigate.magic_events.thriftscala.CreatorFanoutType
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.model.MagicFanoutCreatorEventPushCandidate
import com.twitter.frigate.pushservice.util.PushIbisUtil.mergeModelValues
import com.twitter.util.Future

trait MagicFanoutCreatorEventIbis2Hydrator
    extends CustomConfigurationMapForIbis
    with Ibis2HydratorForCandidate {
  self: PushCandidate with MagicFanoutCreatorEventPushCandidate =>

  val userMap = Map(
    "handle" -> userProfile.screenName,
    "display_name" -> userProfile.name
  )

  override val senderId = hydratedCreator.map(_.id)

  override lazy val modelValues: Future[Map[String, String]] =
    mergeModelValues(super.modelValues, userMap)

  override val ibis2Request = creatorFanoutType match {
    case CreatorFanoutType.UserSubscription => Future.None
    case CreatorFanoutType.NewCreator => super.ibis2Request
    case _ => super.ibis2Request
  }
}
