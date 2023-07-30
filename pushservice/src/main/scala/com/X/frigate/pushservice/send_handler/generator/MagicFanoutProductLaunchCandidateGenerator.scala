package com.X.frigate.pushservice.send_handler.generator

import com.X.frigate.common.base.MagicFanoutProductLaunchCandidate
import com.X.frigate.magic_events.thriftscala.MagicEventsReason
import com.X.frigate.magic_events.thriftscala.ProductType
import com.X.frigate.pushservice.model.PushTypes.RawCandidate
import com.X.frigate.pushservice.model.PushTypes.Target
import com.X.frigate.pushservice.model.PushTypes
import com.X.frigate.thriftscala.CommonRecommendationType
import com.X.frigate.thriftscala.FrigateNotification
import com.X.util.Future

object MagicFanoutProductLaunchCandidateGenerator extends CandidateGenerator {

  override def getCandidate(
    targetUser: PushTypes.Target,
    notification: FrigateNotification
  ): Future[PushTypes.RawCandidate] = {

    require(
      notification.commonRecommendationType == CommonRecommendationType.MagicFanoutProductLaunch,
      "MagicFanoutProductLaunch: unexpected CRT " + notification.commonRecommendationType
    )
    require(
      notification.magicFanoutProductLaunchNotification.isDefined,
      "MagicFanoutProductLaunch: magicFanoutProductLaunchNotification is not defined")
    require(
      notification.magicFanoutProductLaunchNotification.exists(_.magicFanoutPushId.isDefined),
      "MagicFanoutProductLaunch: magicFanoutPushId is not defined")
    require(
      notification.magicFanoutProductLaunchNotification.exists(_.fanoutReasons.isDefined),
      "MagicFanoutProductLaunch: fanoutReasons is not defined")

    val magicFanoutProductLaunchNotification = notification.magicFanoutProductLaunchNotification.get

    val candidate = new RawCandidate with MagicFanoutProductLaunchCandidate {

      override val target: Target = targetUser

      override val pushId: Long =
        magicFanoutProductLaunchNotification.magicFanoutPushId.get

      override val candidateMagicEventsReasons: Seq[MagicEventsReason] =
        magicFanoutProductLaunchNotification.fanoutReasons.get

      override val productLaunchType: ProductType =
        magicFanoutProductLaunchNotification.productLaunchType

      override val frigateNotification: FrigateNotification = notification
    }

    Future.value(candidate)
  }
}
