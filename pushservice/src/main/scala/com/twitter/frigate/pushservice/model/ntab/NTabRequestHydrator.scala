package com.twitter.frigate.pushservice.model.ntab

import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.notificationservice.thriftscala.CreateGenericNotificationRequest
import com.twitter.notificationservice.thriftscala.DisplayTetwittert
import com.twitter.notificationservice.thriftscala.DisplayTetwittertEntity
import com.twitter.notificationservice.thriftscala.GenericType
import com.twitter.notificationservice.thriftscala.InlineCard
import com.twitter.notificationservice.thriftscala.StoryContetwittert
import com.twitter.notificationservice.thriftscala.TapThroughAction
import com.twitter.util.Future
import com.twitter.util.Time

trait NTabRequestHydrator etwittertends NTabRequest with CandidateNTabCopy {
  self: PushCandidate =>

  // Represents the sender of the recommendation
  def senderIdFut: Future[Long]

  // Consists of a sequence representing the social contetwittert user ids.
  def facepileUsersFut: Future[Seq[Long]]

  // Story Contetwittert is required for Tweet Recommendations
  // Contains the Tweet ID of the recommended Tweet
  def storyContetwittert: Option[StoryContetwittert]

  // Inline card used to render a generic notification.
  def inlineCard: Option[InlineCard]

  // Represents where the recommendation should land when clicked
  def tapThroughFut: Future[String]

  // Hydration for fields that are used within the NTab copy
  def displayTetwittertEntitiesFut: Future[Seq[DisplayTetwittertEntity]]

  // Represents the social proof tetwittert that is needed for specific NTab copies
  def socialProofDisplayTetwittert: Option[DisplayTetwittert]

  // MagicRecs NTab entries always use RefreshableType as the Generic Type
  final val genericType: GenericType = GenericType.RefreshableNotification

  def refreshableType: Option[String] = ntabCopy.refreshableType

  lazy val ntabRequest: Future[Option[CreateGenericNotificationRequest]] = {
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
  }
}
