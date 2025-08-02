package com.twitter.frigate.pushservice.model.ntab

import com.twitter.frigate.pushservice.model.SubscribedSearchTweetPushCandidate
import com.twitter.frigate.pushservice.take.NotificationServiceSender
import com.twitter.notificationservice.thriftscala.DisplayTetwittert
import com.twitter.notificationservice.thriftscala.DisplayTetwittertEntity
import com.twitter.notificationservice.thriftscala.TetwittertValue
import com.twitter.util.Future

trait SubscribedSearchTweetNtabRequestHydrator etwittertends TweetNTabRequestHydrator {
  self: SubscribedSearchTweetPushCandidate =>
  override def displayTetwittertEntitiesFut: Future[Seq[DisplayTetwittertEntity]] = NotificationServiceSender
    .getDisplayTetwittertEntityFromUser(tweetAuthor, "tweetAuthor", isBold = true).map(_.toSeq)

  override def socialProofDisplayTetwittert: Option[DisplayTetwittert] = {
    Some(DisplayTetwittert(values = Seq(DisplayTetwittertEntity("search_query", TetwittertValue.Tetwittert(searchTerm)))))
  }

  override lazy val facepileUsersFut: Future[Seq[Long]] = senderIdFut.map(Seq(_))

  override lazy val tapThroughFut: Future[String] =
    Future.value(self.ntabLandingUrl)
}
