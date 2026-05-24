package com.twitter.timelines.prediction.features.time_features

import com.twitter.dal.personal_data.thriftjava.PersonalDataType._
import com.twitter.ml.api.Feature._
import scala.collection.JavaConverters._
import com.twitter.util.Duration
import com.twitter.conversions.DurationOps._

object TimeDataRecordFeatures {
  val TIME_BETWEEN_NON_POLLING_REQUESTS_AVG = new Continuous(
    "time_features.time_between_non_polling_requests_avg",
    Set(PrivateTimestamp).asJava
  )
  val TIME_SINCE_TWEET_CREATION = new Continuous("time_features.time_since_tweet_creation")
  val TIME_SINCE_SOURCE_TWEET_CREATION = new Continuous(
    "time_features.time_since_source_tweet_creation"
  )
  val TIME_SINCE_LAST_NON_POLLING_REQUEST = new Continuous(
    "time_features.time_since_last_non_polling_request",
    Set(PrivateTimestamp).asJava
  )
  val NON_POLLING_REQUESTS_SINCE_TWEET_CREATION = new Continuous(
    "time_features.non_polling_requests_since_tweet_creation",
    Set(PrivateTimestamp).asJava
  )
  val TWEET_AGE_RATIO = new Continuous("time_features.tweet_age_ratio")
  val IS_TWEET_RECYCLED = new Binary("time_features.is_tweet_recycled")
  // Last Engagement features
  val LAST_FAVORITE_SINCE_CREATION_HRS = new Continuous(
    "time_features.earlybird.last_favorite_since_creation_hrs",
    Set(CountOfPrivateLikes, CountOfPublicLikes).asJava
  )
  val LAST_RETWEET_SINCE_CREATION_HRS = new Continuous(
    "time_features.earlybird.last_retweet_since_creation_hrs",
    Set(CountOfPrivateRetweets, CountOfPublicRetweets).asJava
  )
  val LAST_REPLY_SINCE_CREATION_HRS = new Continuous(
    "time_features.earlybird.last_reply_since_creation_hrs",
    Set(CountOfPrivateReplies, CountOfPublicReplies).asJava
  )
  val LAST_QUOTE_SINCE_CREATION_HRS = new Continuous(
    "time_features.earlybird.last_quote_since_creation_hrs",
    Set(CountOfPrivateRetweets, CountOfPublicRetweets).asJava
  )
  val TIME_SINCE_LAST_FAVORITE_HRS = new Continuous(
    "time_features.earlybird.time_since_last_favorite",
    Set(CountOfPrivateLikes, CountOfPublicLikes).asJava
  )
  val TIME_SINCE_LAST_RETWEET_HRS = new Continuous(
    "time_features.earlybird.time_since_last_retweet",
    Set(CountOfPrivateRetweets, CountOfPublicRetweets).asJava
  )
  val TIME_SINCE_LAST_REPLY_HRS = new Continuous(
    "time_features.earlybird.time_since_last_reply",
    Set(CountOfPrivateReplies, CountOfPublicReplies).asJava
  )
  val TIME_SINCE_LAST_QUOTE_HRS = new Continuous(
    "time_features.earlybird.time_since_last_quote",
    Set(CountOfPrivateRetweets, CountOfPublicRetweets).asJava
  )

  val TIME_SINCE_VIEWER_ACCOUNT_CREATION_SECS =
    new Continuous(
      "time_features.time_since_viewer_account_creation_secs",
      Set(AccountCreationTime, AgeOfAccount).asJava)

  val USER_ID_IS_SNOWFLAKE_ID =
    new Binary("time_features.time_user_id_is_snowflake_id", Set(UserType).asJava)

  val IS_30_DAY_NEW_USER =
    new Binary("time_features.is_day_30_new_user", Set(AccountCreationTime, AgeOfAccount).asJava)
  val IS_12_MONTH_NEW_USER =
    new Binary("time_features.is_month_12_new_user", Set(AccountCreationTime, AgeOfAccount).asJava)
  val ACCOUNT_AGE_INTERVAL =
    new Discrete("time_features.account_age_interval", Set(AgeOfAccount).asJava)
}

object AccountAgeInterval extends Enumeration {
  val LTE_1_DAY, GT_1_DAY_LTE_5_DAY, GT_5_DAY_LTE_14_DAY, GT_14_DAY_LTE_30_DAY = Value

  def fromDuration(accountAge: Duration): Option[AccountAgeInterval.Value] = {
    accountAge match {
      case a if (a <= 1.day) => Some(LTE_1_DAY)
      case a if (1.day < a && a <= 5.days) => Some(GT_1_DAY_LTE_5_DAY)
      case a if (5.days < a && a <= 14.days) => Some(GT_5_DAY_LTE_14_DAY)
      case a if (14.days < a && a <= 30.days) => Some(GT_14_DAY_LTE_30_DAY)
      case _ => None
    }
  }
}

case class TimeFeatures(
  isTweetRecycled: Boolean,
  timeSinceTweetCreation: Double,
  isDay30NewUser: Boolean,
  isMonth12NewUser: Boolean,
  timeSinceSourceTweetCreation: Double, // same as timeSinceTweetCreation for non-retweets
  timeSinceViewerAccountCreationSecs: Option[Double],
  timeBetweenNonPollingRequestsAvg: Option[Double] = None,
  timeSinceLastNonPollingRequest: Option[Double] = None,
  nonPollingRequestsSinceTweetCreation: Option[Double] = None,
  tweetAgeRatio: Option[Double] = None,
  lastFavSinceCreationHrs: Option[Double] = None,
  lastRetweetSinceCreationHrs: Option[Double] = None,
  lastReplySinceCreationHrs: Option[Double] = None,
  lastQuoteSinceCreationHrs: Option[Double] = None,
  timeSinceLastFavoriteHrs: Option[Double] = None,
  timeSinceLastRetweetHrs: Option[Double] = None,
  timeSinceLastReplyHrs: Option[Double] = None,
  timeSinceLastQuoteHrs: Option[Double] = None,
  accountAgeInterval: Option[AccountAgeInterval.Value] = None)
