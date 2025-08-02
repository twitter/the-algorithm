package com.twitter.frigate.pushservice.model.ntab

import com.twitter.frigate.common.base.MagicFanoutProductLaunchCandidate
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.params.PushFeatureSwitchParams
import com.twitter.notificationservice.thriftscala._
import com.twitter.util.Future
import com.twitter.util.Time

trait MagicFanoutProductLaunchNtabRequestHydrator etwittertends NTabRequestHydrator {
  self: PushCandidate with MagicFanoutProductLaunchCandidate =>

  override val senderIdFut: Future[Long] = Future.value(0L)

  override lazy val tapThroughFut: Future[String] = Future.value(getProductLaunchTapThrough())

  override lazy val displayTetwittertEntitiesFut: Future[Seq[DisplayTetwittertEntity]] = {
    Future.value(
      frigateNotification.magicFanoutProductLaunchNotification
        .flatMap {
          _.productInfo.flatMap {
            _.body.map { body =>
              Seq(
                DisplayTetwittertEntity(name = "body", value = TetwittertValue.Tetwittert(body)),
              )
            }
          }
        }.getOrElse(Nil))
  }

  override lazy val facepileUsersFut: Future[Seq[Long]] = {
    Future.value(
      frigateNotification.magicFanoutProductLaunchNotification
        .flatMap {
          _.productInfo.flatMap {
            _.facepileUsers
          }
        }.getOrElse(Nil))
  }

  override val storyContetwittert: Option[StoryContetwittert] = None

  override val inlineCard: Option[InlineCard] = None

  override lazy val socialProofDisplayTetwittert: Option[DisplayTetwittert] = {
    frigateNotification.magicFanoutProductLaunchNotification.flatMap {
      _.productInfo.flatMap {
        _.title.map { title =>
          DisplayTetwittert(values =
            Seq(DisplayTetwittertEntity(name = "social_contetwittert", value = TetwittertValue.Tetwittert(title))))
        }
      }
    }
  }

  lazy val defaultTapThrough = target.params(PushFeatureSwitchParams.ProductLaunchTapThrough)

  private def getProductLaunchTapThrough(): String = {
    frigateNotification.magicFanoutProductLaunchNotification match {
      case Some(productLaunchNotif) =>
        productLaunchNotif.productInfo match {
          case Some(productInfo) => productInfo.tapThrough.getOrElse(defaultTapThrough)
          case _ => defaultTapThrough
        }
      case _ => defaultTapThrough
    }
  }

  private lazy val productLaunchNtabRequest: Future[Option[CreateGenericNotificationRequest]] = {
    Future
      .join(senderIdFut, displayTetwittertEntitiesFut, facepileUsersFut, tapThroughFut)
      .map {
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
  }

  override lazy val ntabRequest: Future[Option[CreateGenericNotificationRequest]] = {
    if (target.params(PushFeatureSwitchParams.EnableNTabEntriesForProductLaunchNotifications)) {
      productLaunchNtabRequest
    } else Future.None
  }
}
