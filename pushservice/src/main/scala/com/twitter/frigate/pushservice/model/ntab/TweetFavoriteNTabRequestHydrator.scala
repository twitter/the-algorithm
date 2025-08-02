package com.twitter.frigate.pushservice.model.ntab

import com.twitter.frigate.common.base.SocialContetwittertActions
import com.twitter.frigate.common.base.SocialContetwittertUserDetails
import com.twitter.frigate.common.base.TweetAuthor
import com.twitter.frigate.common.base.TweetAuthorDetails
import com.twitter.frigate.common.base.TweetCandidate
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.take.NotificationServiceSender
import com.twitter.notificationservice.thriftscala.DisplayTetwittertEntity
import com.twitter.util.Future

trait TweetFavoriteNTabRequestHydrator etwittertends TweetNTabRequestHydrator with NTabSocialContetwittert {
  self: PushCandidate
    with TweetCandidate
    with TweetAuthor
    with TweetAuthorDetails
    with SocialContetwittertActions
    with SocialContetwittertUserDetails =>

  override lazy val displayTetwittertEntitiesFut: Future[Seq[DisplayTetwittertEntity]] = {
    Future
      .join(
        NotificationServiceSender
          .getDisplayTetwittertEntityFromUser(tweetAuthor, "tweetAuthorName", isBold = false),
        NotificationServiceSender
          .generateSocialContetwittertTetwittertEntities(
            rankedNtabDisplayNamesAndIds(defaultToRecency = false),
            otherCount)
      )
      .map {
        case (authorDisplay, socialContetwittertDisplay) =>
          socialContetwittertDisplay ++ authorDisplay
      }
  }

  override lazy val facepileUsersFut: Future[Seq[Long]] = Future.value(socialContetwittertUserIds)
}
