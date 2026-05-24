package com.twitter.timelines.prediction.features.p_home_latest

import com.twitter.ml.api.Feature.{Continuous, Discrete}
import com.twitter.dal.personal_data.thriftjava.PersonalDataType._
import scala.collection.JavaConverters._

object HomeLatestUserFeatures {
  val LAST_LOGIN_TIMESTAMP_MS =
    new Discrete("home_latest.user_feature.last_login_timestamp_ms", Set(PrivateTimestamp).asJava)
}

object HomeLatestUserAggregatesFeatures {

  /**
   * Used as `timestampFeature` in `OfflineAggregateSource` required by feature aggregations, set to
   * the `dateRange` end timestamp by default
   */
  val AGGREGATE_TIMESTAMP_MS =
    new Discrete("home_latest.user_feature.aggregate_timestamp_ms", Set(PrivateTimestamp).asJava)
  val HOME_TOP_IMPRESSIONS =
    new Continuous("home_latest.user_feature.home_top_impressions", Set(CountOfImpression).asJava)
  val HOME_LATEST_IMPRESSIONS =
    new Continuous(
      "home_latest.user_feature.home_latest_impressions",
      Set(CountOfImpression).asJava)
  val HOME_TOP_LAST_LOGIN_TIMESTAMP_MS =
    new Discrete(
      "home_latest.user_feature.home_top_last_login_timestamp_ms",
      Set(PrivateTimestamp).asJava)
  val HOME_LATEST_LAST_LOGIN_TIMESTAMP_MS =
    new Discrete(
      "home_latest.user_feature.home_latest_last_login_timestamp_ms",
      Set(PrivateTimestamp).asJava)
  val HOME_LATEST_MOST_RECENT_CLICK_TIMESTAMP_MS =
    new Discrete(
      "home_latest.user_feature.home_latest_most_recent_click_timestamp_ms",
      Set(PrivateTimestamp).asJava)
}

case class HomeLatestUserFeatures(userId: Long, lastLoginTimestampMs: Long)

case class HomeLatestUserAggregatesFeatures(
  userId: Long,
  aggregateTimestampMs: Long,
  homeTopImpressions: Option[Double],
  homeLatestImpressions: Option[Double],
  homeTopLastLoginTimestampMs: Option[Long],
  homeLatestLastLoginTimestampMs: Option[Long],
  homeLatestMostRecentClickTimestampMs: Option[Long])
