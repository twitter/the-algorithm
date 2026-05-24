package com.twitter.frigate.pushservice.model.ntab

import com.twitter.frigate.common.base.TweetAuthorDetails
import com.twitter.frigate.common.base.TweetCandidate
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.take.NotificationServiceSender
import com.twitter.notificationservice.thriftscala.DisplayTextEntity
import com.twitter.util.Future

trait F1FirstDegreeTweetNTabRequestHydrator extends TweetNTabRequestHydrator {
  self: PushCandidate with TweetCandidate with TweetAuthorDetails =>

  override val displayTextEntitiesFut: Future[Seq[DisplayTextEntity]] =
    NotificationServiceSender.getDisplayTextEntityFromUser(tweetAuthor, "author", true).map(_.toSeq)

  override lazy val facepileUsersFut: Future[Seq[Long]] = senderIdFut.map(Seq(_))

}
