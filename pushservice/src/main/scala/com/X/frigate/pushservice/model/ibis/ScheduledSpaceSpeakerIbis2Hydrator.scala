package com.X.frigate.pushservice.model.ibis

import com.X.frigate.pushservice.model.ScheduledSpaceSpeakerPushCandidate
import com.X.frigate.pushservice.util.PushIbisUtil._
import com.X.frigate.thriftscala.SpaceNotificationType
import com.X.util.Future

trait ScheduledSpaceSpeakerIbis2Hydrator extends Ibis2HydratorForCandidate {
  self: ScheduledSpaceSpeakerPushCandidate =>

  override lazy val senderId: Option[Long] = None

  private lazy val targetModelValues: Future[Map[String, String]] = {
    hostId match {
      case Some(spaceHostId) =>
        audioSpaceFut.map { audioSpace =>
          val isStartNow = frigateNotification.spaceNotification.exists(
            _.spaceNotificationType.contains(SpaceNotificationType.AtSpaceBroadcast))

          Map(
            "host_id" -> s"$spaceHostId",
            "space_id" -> spaceId,
            "is_start_now" -> s"$isStartNow"
          ) ++ audioSpace.flatMap(_.title.map("space_title" -> _))
        }
      case _ =>
        Future.exception(
          new IllegalStateException("Unable to get host id for ScheduledSpaceSpeakerIbis2Hydrator"))
    }
  }

  override lazy val modelValues: Future[Map[String, String]] =
    mergeFutModelValues(super.modelValues, targetModelValues)
}
