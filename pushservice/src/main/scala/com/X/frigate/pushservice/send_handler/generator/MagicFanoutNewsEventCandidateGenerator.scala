package com.X.frigate.pushservice.send_handler.generator

import com.X.frigate.common.base.MagicFanoutNewsEventCandidate
import com.X.frigate.magic_events.thriftscala.MagicEventsReason
import com.X.frigate.pushservice.model.PushTypes.RawCandidate
import com.X.frigate.pushservice.model.PushTypes.Target
import com.X.frigate.thriftscala.CommonRecommendationType
import com.X.frigate.thriftscala.FrigateNotification
import com.X.frigate.thriftscala.MagicFanoutEventNotificationDetails
import com.X.util.Future

object MagicFanoutNewsEventCandidateGenerator extends CandidateGenerator {

  override def getCandidate(
    targetUser: Target,
    notification: FrigateNotification
  ): Future[RawCandidate] = {

    /**
     * frigateNotification recommendation type should be [[CommonRecommendationType.MagicFanoutNewsEvent]]
     * AND pushId field should be set
     **/
    require(
      notification.commonRecommendationType == CommonRecommendationType.MagicFanoutNewsEvent,
      "MagicFanoutNewsEvent: unexpected CRT " + notification.commonRecommendationType
    )

    require(
      notification.magicFanoutEventNotification.exists(_.pushId.isDefined),
      "MagicFanoutNewsEvent: pushId is not defined")

    val magicFanoutEventNotification = notification.magicFanoutEventNotification.get

    val candidate = new RawCandidate with MagicFanoutNewsEventCandidate {

      override val target: Target = targetUser

      override val eventId: Long = magicFanoutEventNotification.eventId

      override val pushId: Long = magicFanoutEventNotification.pushId.get

      override val candidateMagicEventsReasons: Seq[MagicEventsReason] =
        magicFanoutEventNotification.eventReasons.getOrElse(Seq.empty)

      override val momentId: Option[Long] = magicFanoutEventNotification.momentId

      override val eventLanguage: Option[String] = magicFanoutEventNotification.eventLanguage

      override val details: Option[MagicFanoutEventNotificationDetails] =
        magicFanoutEventNotification.details

      override val frigateNotification: FrigateNotification = notification
    }

    Future.value(candidate)
  }
}
