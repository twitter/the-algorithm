package com.X.frigate.pushservice.model.ntab

import com.X.frigate.common.base.SocialContextActions
import com.X.frigate.common.base.SocialContextUserDetails
import com.X.frigate.common.base.TweetAuthor
import com.X.frigate.common.base.TweetAuthorDetails
import com.X.frigate.common.base.TweetCandidate
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.take.NotificationServiceSender
import com.X.notificationservice.thriftscala.DisplayTextEntity
import com.X.util.Future

trait TweetFavoriteNTabRequestHydrator extends TweetNTabRequestHydrator with NTabSocialContext {
  self: PushCandidate
    with TweetCandidate
    with TweetAuthor
    with TweetAuthorDetails
    with SocialContextActions
    with SocialContextUserDetails =>

  override lazy val displayTextEntitiesFut: Future[Seq[DisplayTextEntity]] = {
    Future
      .join(
        NotificationServiceSender
          .getDisplayTextEntityFromUser(tweetAuthor, "tweetAuthorName", isBold = false),
        NotificationServiceSender
          .generateSocialContextTextEntities(
            rankedNtabDisplayNamesAndIds(defaultToRecency = false),
            otherCount)
      )
      .map {
        case (authorDisplay, socialContextDisplay) =>
          socialContextDisplay ++ authorDisplay
      }
  }

  override lazy val facepileUsersFut: Future[Seq[Long]] = Future.value(socialContextUserIds)
}
