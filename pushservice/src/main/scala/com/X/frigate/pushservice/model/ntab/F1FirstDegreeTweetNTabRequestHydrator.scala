package com.X.frigate.pushservice.model.ntab

import com.X.frigate.common.base.TweetAuthorDetails
import com.X.frigate.common.base.TweetCandidate
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.take.NotificationServiceSender
import com.X.notificationservice.thriftscala.DisplayTextEntity
import com.X.util.Future

trait F1FirstDegreeTweetNTabRequestHydrator extends TweetNTabRequestHydrator {
  self: PushCandidate with TweetCandidate with TweetAuthorDetails =>

  override val displayTextEntitiesFut: Future[Seq[DisplayTextEntity]] =
    NotificationServiceSender.getDisplayTextEntityFromUser(tweetAuthor, "author", true).map(_.toSeq)

  override lazy val facepileUsersFut: Future[Seq[Long]] = senderIdFut.map(Seq(_))

}
