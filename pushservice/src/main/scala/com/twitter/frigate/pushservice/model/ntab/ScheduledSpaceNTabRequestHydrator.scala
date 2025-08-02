package com.twitter.frigate.pushservice.model.ntab

import com.twitter.frigate.common.base.SpaceCandidate
import com.twitter.frigate.common.util.MrNtabCopyObjects
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.model.ScheduledSpaceSpeakerPushCandidate
import com.twitter.frigate.pushservice.model.ScheduledSpaceSubscriberPushCandidate
import com.twitter.frigate.pushservice.params.PushFeatureSwitchParams
import com.twitter.frigate.pushservice.take.NotificationServiceSender
import com.twitter.frigate.thriftscala.SpaceNotificationType
import com.twitter.gizmoduck.thriftscala.User
import com.twitter.notificationservice.thriftscala._
import com.twitter.util.Future
import com.twitter.util.Time

trait ScheduledSpaceSpeakerNTabRequestHydrator etwittertends ScheduledSpaceNTabRequestHydrator {
  self: PushCandidate with ScheduledSpaceSpeakerPushCandidate =>

  override def refreshableType: Option[String] = {
    frigateNotification.spaceNotification.flatMap { spaceNotification =>
      spaceNotification.spaceNotificationType.flatMap {
        case SpaceNotificationType.PreSpaceBroadcast =>
          MrNtabCopyObjects.ScheduledSpaceSpeakerSoon.refreshableType
        case SpaceNotificationType.AtSpaceBroadcast =>
          MrNtabCopyObjects.ScheduledSpaceSpeakerNow.refreshableType
        case _ =>
          throw new IllegalStateEtwitterception(s"Unetwitterpected SpaceNotificationType")
      }
    }
  }

  override lazy val facepileUsersFut: Future[Seq[Long]] = Future.Nil

  override val socialProofDisplayTetwittert: Option[DisplayTetwittert] = Some(DisplayTetwittert())
}

trait ScheduledSpaceSubscriberNTabRequestHydrator etwittertends ScheduledSpaceNTabRequestHydrator {
  self: PushCandidate with ScheduledSpaceSubscriberPushCandidate =>

  override lazy val facepileUsersFut: Future[Seq[Long]] = {
    hostId match {
      case Some(spaceHostId) => Future.value(Seq(spaceHostId))
      case _ =>
        Future.etwitterception(
          new IllegalStateEtwitterception(
            "Unable to get host id for ScheduledSpaceSubscriberNTabRequestHydrator"))
    }
  }

  override val socialProofDisplayTetwittert: Option[DisplayTetwittert] = None
}

trait ScheduledSpaceNTabRequestHydrator etwittertends NTabRequestHydrator {
  self: PushCandidate with SpaceCandidate =>

  def hydratedHost: Option[User]

  override lazy val senderIdFut: Future[Long] = {
    hostId match {
      case Some(spaceHostId) => Future.value(spaceHostId)
      case _ => throw new IllegalStateEtwitterception(s"No Space Host Id")
    }
  }

  override lazy val tapThroughFut: Future[String] = Future.value(s"i/spaces/$spaceId")

  override lazy val displayTetwittertEntitiesFut: Future[Seq[DisplayTetwittertEntity]] =
    NotificationServiceSender
      .getDisplayTetwittertEntityFromUser(
        Future.value(hydratedHost),
        fieldName = "space_host_name",
        isBold = true
      ).map(_.toSeq)

  override val storyContetwittert: Option[StoryContetwittert] = None

  override val inlineCard: Option[InlineCard] = None

  override lazy val ntabRequest: Future[Option[CreateGenericNotificationRequest]] = {
    Future.join(senderIdFut, displayTetwittertEntitiesFut, facepileUsersFut, tapThroughFut).map {
      case (senderId, displayTetwittertEntities, facepileUsers, tapThrough) =>
        val etwitterpiryTimeMillis = if (target.params(PushFeatureSwitchParams.EnableSpacesTtlForNtab)) {
          Some(
            (Time.now + target.params(
              PushFeatureSwitchParams.SpaceNotificationsTTLDurationForNTab)).inMillis)
        } else None

        Some(
          CreateGenericNotificationRequest(
            userId = target.targetId,
            senderId = senderId,
            genericType = GenericType.RefreshableNotification,
            displayTetwittert = DisplayTetwittert(values = displayTetwittertEntities),
            facepileUsers = facepileUsers,
            timestampMillis = Time.now.inMillis,
            tapThroughAction = Some(TapThroughAction(Some(tapThrough))),
            impressionId = Some(impressionId),
            socialProofTetwittert = socialProofDisplayTetwittert,
            contetwittert = storyContetwittert,
            inlineCard = inlineCard,
            refreshableType = refreshableType,
            etwitterpiryTimeMillis = etwitterpiryTimeMillis
          ))
    }
  }
}
