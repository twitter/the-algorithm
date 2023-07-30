package com.X.visibility.builder.tweets

import com.X.finagle.stats.StatsReceiver
import com.X.notificationservice.model.notification._
import com.X.servo.util.Gate
import com.X.stitch.Stitch
import com.X.tweetypie.thriftscala.SettingsUnmentions
import com.X.visibility.builder.FeatureMapBuilder
import com.X.visibility.common.TweetSource
import com.X.visibility.features.NotificationIsOnUnmentionedViewer

object UnmentionNotificationFeatures {
  def ForNonUnmentionNotificationFeatures: FeatureMapBuilder => FeatureMapBuilder = {
    _.withConstantFeature(NotificationIsOnUnmentionedViewer, false)
  }
}

class UnmentionNotificationFeatures(
  tweetSource: TweetSource,
  enableUnmentionHydration: Gate[Long],
  statsReceiver: StatsReceiver) {

  private[this] val scopedStatsReceiver =
    statsReceiver.scope("unmention_notification_features")
  private[this] val requestsCounter = scopedStatsReceiver.counter("requests")
  private[this] val hydrationsCounter = scopedStatsReceiver.counter("hydrations")
  private[this] val notificationsUnmentionUserCounter =
    scopedStatsReceiver
      .scope(NotificationIsOnUnmentionedViewer.name).counter("unmentioned_users")

  def forNotification(notification: Notification): FeatureMapBuilder => FeatureMapBuilder = {
    requestsCounter.incr()

    val isUnmentionNotification = tweetId(notification) match {
      case Some(tweetId) if enableUnmentionHydration(notification.target) =>
        hydrationsCounter.incr()
        tweetSource
          .getTweet(tweetId)
          .map {
            case Some(tweet) =>
              tweet.settingsUnmentions match {
                case Some(SettingsUnmentions(Some(unmentionedUserIds))) =>
                  if (unmentionedUserIds.contains(notification.target)) {
                    notificationsUnmentionUserCounter.incr()
                    true
                  } else {
                    false
                  }
                case _ => false
              }
            case _ => false
          }
      case _ => Stitch.False
    }
    _.withFeature(NotificationIsOnUnmentionedViewer, isUnmentionNotification)
  }

  private[this] def tweetId(notification: Notification): Option[Long] = {
    notification match {
      case n: MentionNotification => Some(n.mentioningTweetId)
      case n: FavoritedMentioningTweetNotification => Some(n.mentioningTweetId)
      case n: FavoritedReplyToYourTweetNotification => Some(n.replyTweetId)
      case n: MentionQuoteNotification => Some(n.mentioningTweetId)
      case n: ReactionMentioningTweetNotification => Some(n.mentioningTweetId)
      case n: ReplyNotification => Some(n.replyingTweetId)
      case n: RetweetedMentionNotification => Some(n.mentioningTweetId)
      case n: RetweetedReplyToYourTweetNotification => Some(n.replyTweetId)
      case n: ReplyToConversationNotification => Some(n.replyingTweetId)
      case n: ReactionReplyToYourTweetNotification => Some(n.replyTweetId)
      case _ => None
    }

  }

}
