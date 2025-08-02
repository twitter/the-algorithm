package com.twitter.frigate.pushservice.model.ntab

import com.twitter.frigate.common.rec_types.RecTypes
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.params.PushConstants
import com.twitter.frigate.thriftscala.{CommonRecommendationType => CRT}
import com.twitter.notificationservice.thriftscala._
import com.twitter.util.Future
import com.twitter.util.Time

trait DiscoverTwitterNtabRequestHydrator etwittertends NTabRequestHydrator {
  self: PushCandidate =>

  override val senderIdFut: Future[Long] = Future.value(0L)

  override val tapThroughFut: Future[String] =
    commonRecType match {
      case CRT.AddressBookUploadPush => Future.value(PushConstants.AddressBookUploadTapThrough)
      case CRT.InterestPickerPush => Future.value(PushConstants.InterestPickerTapThrough)
      case CRT.CompleteOnboardingPush =>
        Future.value(PushConstants.CompleteOnboardingInterestAddressTapThrough)
      case _ =>
        Future.value(PushConstants.ConnectTabPushTapThrough)
    }

  override val displayTetwittertEntitiesFut: Future[Seq[DisplayTetwittertEntity]] = Future.Nil

  override val facepileUsersFut: Future[Seq[Long]] = Future.Nil

  override val storyContetwittert: Option[StoryContetwittert] = None

  override val inlineCard: Option[InlineCard] = None

  override val socialProofDisplayTetwittert: Option[DisplayTetwittert] = Some(DisplayTetwittert())

  override lazy val ntabRequest: Future[Option[CreateGenericNotificationRequest]] =
    if (self.commonRecType == CRT.ConnectTabPush || RecTypes.isOnboardingFlowType(
        self.commonRecType)) {
      Future.join(senderIdFut, displayTetwittertEntitiesFut, facepileUsersFut, tapThroughFut).map {
        case (senderId, displayTetwittertEntities, facepileUsers, tapThrough) =>
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
              refreshableType = refreshableType
            ))
      }
    } else Future.None
}
