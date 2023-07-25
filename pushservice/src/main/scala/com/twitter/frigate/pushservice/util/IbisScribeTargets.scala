package com.twitter.frigate.pushservice.util

import com.twitter.frigate.thriftscala.CommonRecommendationType
import com.twitter.frigate.thriftscala.CommonRecommendationType._

object IbisScribeTargets {
  val User2 = "magic_rec_user_2"
  val User4 = "magic_rec_user_4"
  val Tweet2 = "magic_rec_tweet_2"
  val Tweet4 = "magic_rec_tweet_4"
  val Tweet5 = "magic_rec_tweet_5"
  val Tweet9 = "magic_rec_tweet_9"
  val Tweet10 = "magic_rec_tweet_10"
  val Tweet11 = "magic_rec_tweet_11"
  val Tweet12 = "magic_rec_tweet_12"
  val Tweet16 = "magic_rec_tweet_16"
  val Hashtag = "magic_rec_hashtag"
  val UnreadBadgeCount17 = "magic_rec_unread_badge_count_17"
  val Highlights = "highlights"
  val TweetAnalytics = "magic_rec_tweet_analytics"
  val Untracked = "untracked"

  def crtToScribeTarget(crt: CommonRecommendationType): String = crt match {
    case UserFollow =>
      User2
    case HermitUser =>
      User4
    case TweetRetweet | TweetFavorite =>
      Tweet2
    case TweetRetweetPhoto | TweetFavoritePhoto =>
      Tweet4
    case TweetRetweetVideo | TweetFavoriteVideo =>
      Tweet5
    case UrlTweetLanding =>
      Tweet9
    case F1FirstdegreeTweet | F1FirstdegreePhoto | F1FirstdegreeVideo =>
      Tweet10
    case AuthorTargetingTweet =>
      Tweet11
    case PeriscopeShare =>
      Tweet12
    case CommonRecommendationType.Highlights =>
      Highlights
    case HashtagTweet | HashtagTweetRetweet =>
      Hashtag
    case PinnedTweet =>
      Tweet16
    case UnreadBadgeCount =>
      UnreadBadgeCount17
    case TweetImpressions =>
      TweetAnalytics
    case _ =>
      Untracked
  }
}
