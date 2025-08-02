package com.twitter.frigate.pushservice.model.ntab

import com.twitter.frigate.pushservice.model.TopicProofTweetPushCandidate
import com.twitter.frigate.pushservice.etwitterception.TweetNTabRequestHydratorEtwitterception
import com.twitter.frigate.pushservice.etwitterception.UttEntityNotFoundEtwitterception
import com.twitter.frigate.pushservice.take.NotificationServiceSender
import com.twitter.notificationservice.thriftscala.DisplayTetwittert
import com.twitter.notificationservice.thriftscala.DisplayTetwittertEntity
import com.twitter.notificationservice.thriftscala.StoryContetwittert
import com.twitter.notificationservice.thriftscala.StoryContetwittertValue
import com.twitter.notificationservice.thriftscala.TetwittertValue
import com.twitter.util.Future

trait TopicProofTweetNtabRequestHydrator etwittertends NTabRequestHydrator {
  self: TopicProofTweetPushCandidate =>

  override def displayTetwittertEntitiesFut: Future[Seq[DisplayTetwittertEntity]] = NotificationServiceSender
    .getDisplayTetwittertEntityFromUser(tweetAuthor, "tweetAuthorName", true)
    .map(_.toSeq)

  private lazy val uttEntity = localizedUttEntity.getOrElse(
    throw new UttEntityNotFoundEtwitterception(
      s"${getClass.getSimpleName} UttEntity missing for $tweetId")
  )

  override lazy val tapThroughFut: Future[String] = {
    tweetAuthor.map {
      case Some(author) =>
        val authorProfile = author.profile.getOrElse(
          throw new TweetNTabRequestHydratorEtwitterception(
            s"Unable to obtain author profile for: ${author.id}"))
        s"${authorProfile.screenName}/status/${tweetId.toString}"
      case _ =>
        throw new TweetNTabRequestHydratorEtwitterception(
          s"Unable to obtain author and target details to generate tap through for Tweet: $tweetId")
    }
  }

  override lazy val socialProofDisplayTetwittert: Option[DisplayTetwittert] = {
    Some(
      DisplayTetwittert(values =
        Seq(DisplayTetwittertEntity("topic_name", TetwittertValue.Tetwittert(uttEntity.localizedNameForDisplay))))
    )
  }

  override lazy val facepileUsersFut: Future[Seq[Long]] = senderIdFut.map(Seq(_))

  override val inlineCard = None

  override def storyContetwittert: Option[StoryContetwittert] = Some(
    StoryContetwittert("", Some(StoryContetwittertValue.Tweets(Seq(tweetId)))))

  override def senderIdFut: Future[Long] =
    tweetAuthor.map {
      case Some(author) => author.id
      case _ =>
        throw new TweetNTabRequestHydratorEtwitterception(
          s"Unable to obtain Author ID for: $commonRecType")
    }
}
