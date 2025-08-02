package com.twitter.frigate.pushservice.model.ntab

import com.twitter.frigate.common.base.TrendTweetCandidate
import com.twitter.frigate.common.base.TweetAuthorDetails
import com.twitter.frigate.common.base.TweetCandidate
import com.twitter.frigate.pushservice.etwitterception.TweetNTabRequestHydratorEtwitterception
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.params.PushFeatureSwitchParams
import com.twitter.frigate.pushservice.take.NotificationServiceSender
import com.twitter.frigate.pushservice.util.EmailLandingPageEtwitterperimentUtil
import com.twitter.notificationservice.thriftscala.DisplayTetwittert
import com.twitter.notificationservice.thriftscala.DisplayTetwittertEntity
import com.twitter.notificationservice.thriftscala.TetwittertValue
import com.twitter.util.Future

trait TrendTweetNtabHydrator etwittertends TweetNTabRequestHydrator {
  self: PushCandidate with TrendTweetCandidate with TweetCandidate with TweetAuthorDetails =>

  private lazy val trendTweetNtabStats = self.statsReceiver.scope("trend_tweet_ntab")

  private lazy val rutwitterLandingOnNtabCounter =
    trendTweetNtabStats.counter("use_rutwitter_landing_on_ntab")

  override lazy val displayTetwittertEntitiesFut: Future[Seq[DisplayTetwittertEntity]] =
    NotificationServiceSender
      .getDisplayTetwittertEntityFromUser(tweetAuthor, fieldName = "author_name", isBold = true)
      .map(
        _.toSeq :+ DisplayTetwittertEntity(
          name = "trend_name",
          value = TetwittertValue.Tetwittert(trendName),
          emphasis = true)
      )

  override lazy val facepileUsersFut: Future[Seq[Long]] = senderIdFut.map(Seq(_))

  override lazy val socialProofDisplayTetwittert: Option[DisplayTetwittert] = None

  override def refreshableType: Option[String] = ntabCopy.refreshableType

  override lazy val tapThroughFut: Future[String] = {
    Future.join(tweetAuthor, target.deviceInfo).map {
      case (Some(author), Some(deviceInfo)) =>
        val enableRutwitterLandingPage = deviceInfo.isRutwitterLandingPageEligible && target.params(
          PushFeatureSwitchParams.EnableNTabRutwitterLandingPage)
        val authorProfile = author.profile.getOrElse(
          throw new TweetNTabRequestHydratorEtwitterception(
            s"Unable to obtain author profile for: ${author.id}"))

        if (enableRutwitterLandingPage) {
          rutwitterLandingOnNtabCounter.incr()
          EmailLandingPageEtwitterperimentUtil.createNTabRutwitterLandingURI(authorProfile.screenName, tweetId)
        } else {
          s"${authorProfile.screenName}/status/${tweetId.toString}"
        }

      case _ =>
        throw new TweetNTabRequestHydratorEtwitterception(
          s"Unable to obtain author and target details to generate tap through for Tweet: $tweetId")
    }
  }
}
