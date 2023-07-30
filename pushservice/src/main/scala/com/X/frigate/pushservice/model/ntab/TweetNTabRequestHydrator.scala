package com.X.frigate.pushservice.model.ntab

import com.X.frigate.common.base.TweetAuthorDetails
import com.X.frigate.common.base.TweetCandidate
import com.X.frigate.pushservice.exception.TweetNTabRequestHydratorException
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.params.PushFeatureSwitchParams
import com.X.notificationservice.thriftscala.InlineCard
import com.X.notificationservice.thriftscala.StoryContext
import com.X.notificationservice.thriftscala.StoryContextValue
import com.X.frigate.pushservice.util.EmailLandingPageExperimentUtil
import com.X.notificationservice.thriftscala._
import com.X.util.Future

trait TweetNTabRequestHydrator extends NTabRequestHydrator {
  self: PushCandidate with TweetCandidate with TweetAuthorDetails =>

  override def senderIdFut: Future[Long] =
    tweetAuthor.map {
      case Some(author) => author.id
      case _ =>
        throw new TweetNTabRequestHydratorException(
          s"Unable to obtain Author ID for: $commonRecType")
    }

  override def storyContext: Option[StoryContext] = Some(
    StoryContext(
      altText = "",
      value = Some(StoryContextValue.Tweets(Seq(tweetId))),
      details = None
    ))

  override def inlineCard: Option[InlineCard] = Some(InlineCard.TweetCard(TweetCard(tweetId)))

  override lazy val tapThroughFut: Future[String] = {
    Future.join(tweetAuthor, target.deviceInfo).map {
      case (Some(author), Some(deviceInfo)) =>
        val enableRuxLandingPage = deviceInfo.isRuxLandingPageEligible && target.params(
          PushFeatureSwitchParams.EnableNTabRuxLandingPage)
        val authorProfile = author.profile.getOrElse(
          throw new TweetNTabRequestHydratorException(
            s"Unable to obtain author profile for: ${author.id}"))
        if (enableRuxLandingPage) {
          EmailLandingPageExperimentUtil.createNTabRuxLandingURI(authorProfile.screenName, tweetId)
        } else {
          s"${authorProfile.screenName}/status/${tweetId.toString}"
        }
      case _ =>
        throw new TweetNTabRequestHydratorException(
          s"Unable to obtain author and target details to generate tap through for Tweet: $tweetId")
    }
  }

  override def socialProofDisplayText: Option[DisplayText] = None
}
