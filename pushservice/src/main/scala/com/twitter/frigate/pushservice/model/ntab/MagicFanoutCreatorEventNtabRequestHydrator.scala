package com.twitter.frigate.pushservice.model.ntab

import com.twitter.frigate.magic_events.thriftscala.CreatorFanoutType
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.model.MagicFanoutCreatorEventPushCandidate
import com.twitter.frigate.pushservice.take.NotificationServiceSender
import com.twitter.notificationservice.thriftscala.CreateGenericNotificationRequest
import com.twitter.notificationservice.thriftscala.DisplayTetwittert
import com.twitter.notificationservice.thriftscala.DisplayTetwittertEntity
import com.twitter.notificationservice.thriftscala.GenericType
import com.twitter.notificationservice.thriftscala.InlineCard
import com.twitter.notificationservice.thriftscala.StoryContetwittert
import com.twitter.notificationservice.thriftscala.TetwittertValue
import com.twitter.notificationservice.thriftscala.TapThroughAction
import com.twitter.util.Future
import com.twitter.util.Time

trait MagicFanoutCreatorEventNtabRequestHydrator etwittertends NTabRequestHydrator {
  self: PushCandidate with MagicFanoutCreatorEventPushCandidate =>

  override val senderIdFut: Future[Long] = Future.value(creatorId)

  override lazy val tapThroughFut: Future[String] =
    Future.value(s"/${userProfile.screenName}/superfollows/subscribe")

  lazy val optionalTweetCountEntityFut: Future[Option[DisplayTetwittertEntity]] = {
    creatorFanoutType match {
      case CreatorFanoutType.UserSubscription =>
        numberOfTweetsFut.map {
          _.flatMap {
            case numberOfTweets if numberOfTweets >= 10 =>
              Some(
                DisplayTetwittertEntity(
                  name = "tweet_count",
                  emphasis = true,
                  value = TetwittertValue.Tetwittert(numberOfTweets.toString)))
            case _ => None
          }
        }
      case _ => Future.None
    }
  }

  override lazy val displayTetwittertEntitiesFut: Future[Seq[DisplayTetwittertEntity]] =
    optionalTweetCountEntityFut
      .map { tweetCountOpt =>
        Seq(
          NotificationServiceSender
            .getDisplayTetwittertEntityFromUser(hydratedCreator, "display_name", isBold = true),
          tweetCountOpt).flatten
      }

  override lazy val facepileUsersFut: Future[Seq[Long]] = Future.value(Seq(creatorId))

  override val storyContetwittert: Option[StoryContetwittert] = None

  override val inlineCard: Option[InlineCard] = None

  lazy val refreshableTypeFut = {
    creatorFanoutType match {
      case CreatorFanoutType.UserSubscription =>
        numberOfTweetsFut.map {
          _.flatMap {
            case numberOfTweets if numberOfTweets >= 10 =>
              Some("MagicFanoutCreatorSubscriptionWithTweets")
            case _ => super.refreshableType
          }
        }
      case _ => Future.value(super.refreshableType)
    }
  }

  override lazy val socialProofDisplayTetwittert: Option[DisplayTetwittert] = {
    creatorFanoutType match {
      case CreatorFanoutType.UserSubscription =>
        Some(
          DisplayTetwittert(values = Seq(
            DisplayTetwittertEntity(name = "handle", value = TetwittertValue.Tetwittert(userProfile.screenName)))))
      case CreatorFanoutType.NewCreator => None
      case _ => None
    }
  }

  override lazy val ntabRequest = {
    Future
      .join(
        senderIdFut,
        displayTetwittertEntitiesFut,
        facepileUsersFut,
        tapThroughFut,
        refreshableTypeFut).map {
        case (senderId, displayTetwittertEntities, facepileUsers, tapThrough, refreshableTypeOpt) =>
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
              refreshableType = refreshableTypeOpt
            ))
      }
  }
}
