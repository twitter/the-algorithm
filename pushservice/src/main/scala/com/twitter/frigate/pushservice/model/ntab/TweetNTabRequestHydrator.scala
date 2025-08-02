package com.twitter.frigate.pushservice.model.ntab

import com.twitter.frigate.common.base.TweetAuthorDetails
import com.twitter.frigate.common.base.TweetCandidate
import com.twitter.frigate.pushservice.etwitterception.TweetNTabRequestHydratorEtwitterception
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.params.PushFeatureSwitchParams
import com.twitter.notificationservice.thriftscala.InlineCard
import com.twitter.notificationservice.thriftscala.StoryContetwittert
import com.twitter.notificationservice.thriftscala.StoryContetwittertValue
import com.twitter.frigate.pushservice.util.EmailLandingPageEtwitterperimentUtil
import com.twitter.notificationservice.thriftscala._
import com.twitter.util.Future

trait TweetNTabRequestHydrator etwittertends NTabRequestHydrator {
  self: PushCandidate with TweetCandidate with TweetAuthorDetails =>

  override def senderIdFut: Future[Long] =
    tweetAuthor.map {
      case Some(author) => author.id
      case _ =>
        throw new TweetNTabRequestHydratorEtwitterception(
          s"Unable to obtain Author ID for: $commonRecType")
    }

  override def storyContetwittert: Option[StoryContetwittert] = Some(
    StoryContetwittert(
      altTetwittert = "",
      value = Some(StoryContetwittertValue.Tweets(Seq(tweetId))),
      details = None
    ))

  override def inlineCard: Option[InlineCard] = Some(InlineCard.TweetCard(TweetCard(tweetId)))

  override lazy val tapThroughFut: Future[String] = {
    Future.join(tweetAuthor, target.deviceInfo).map {
      case (Some(author), Some(deviceInfo)) =>
        val enableRutwitterLandingPage = deviceInfo.isRutwitterLandingPageEligible && target.params(
          PushFeatureSwitchParams.EnableNTabRutwitterLandingPage)
        val authorProfile = author.profile.getOrElse(
          throw new TweetNTabRequestHydratorEtwitterception(
            s"Unable to obtain author profile for: ${author.id}"))
        if (enableRutwitterLandingPage) {
          EmailLandingPageEtwitterperimentUtil.createNTabRutwitterLandingURI(authorProfile.screenName, tweetId)
        } else {
          s"${authorProfile.screenName}/status/${tweetId.toString}"
        }
      case _ =>
        throw new TweetNTabRequestHydratorEtwitterception(
          s"Unable to obtain author and target details to generate tap through for Tweet: $tweetId")
    }
  }

  override def socialProofDisplayTetwittert: Option[DisplayTetwittert] = None
}
