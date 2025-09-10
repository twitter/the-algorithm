package com.twitter.home_mixer.product.scored_tweets.feature_hydrator

import com.twitter.conversions.DurationOps._
import com.twitter.home_mixer.model.HomeFeatures.FollowingLastNonPollingTimeFeature
import com.twitter.home_mixer.model.HomeFeatures.LastNonPollingTimeFeature
import com.twitter.home_mixer.model.HomeFeatures.NonPollingTimesFeature
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.util.FDsl._
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.stitch.Stitch
import com.twitter.timelines.prediction.features.time_features.AccountAgeInterval
import com.twitter.timelines.prediction.features.time_features.TimeDataRecordFeatures.ACCOUNT_AGE_INTERVAL
import com.twitter.timelines.prediction.features.time_features.TimeDataRecordFeatures.IS_12_MONTH_NEW_USER
import com.twitter.timelines.prediction.features.time_features.TimeDataRecordFeatures.IS_30_DAY_NEW_USER
import com.twitter.timelines.prediction.features.time_features.TimeDataRecordFeatures.TIME_BETWEEN_NON_POLLING_REQUESTS_AVG
import com.twitter.timelines.prediction.features.time_features.TimeDataRecordFeatures.TIME_SINCE_LAST_NON_POLLING_REQUEST
import com.twitter.timelines.prediction.features.time_features.TimeDataRecordFeatures.TIME_SINCE_VIEWER_ACCOUNT_CREATION_SECS
import com.twitter.timelines.prediction.features.time_features.TimeDataRecordFeatures.USER_ID_IS_SNOWFLAKE_ID
import com.twitter.user_session_store.ReadRequest
import com.twitter.user_session_store.ReadWriteUserSessionStore
import com.twitter.user_session_store.UserSessionDataset
import com.twitter.user_session_store.UserSessionDataset.UserSessionDataset
import com.twitter.util.Time
import javax.inject.Inject
import javax.inject.Singleton

object RequestTimeDataRecordFeature
    extends DataRecordInAFeature[PipelineQuery]
    with FeatureWithDefaultOnFailure[PipelineQuery, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
case class RequestTimeQueryFeatureHydrator @Inject() (
  userSessionStore: ReadWriteUserSessionStore)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("RequestTime")

  override val features: Set[Feature[_, _]] = Set(
    FollowingLastNonPollingTimeFeature,
    LastNonPollingTimeFeature,
    NonPollingTimesFeature,
    RequestTimeDataRecordFeature
  )

  private val datasets: Set[UserSessionDataset] = Set(UserSessionDataset.NonPollingTimes)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    userSessionStore
      .read(ReadRequest(query.getRequiredUserId, datasets))
      .map { userSession =>
        val nonPollingTimestamps = userSession.flatMap(_.nonPollingTimestamps)

        val lastNonPollingTime = nonPollingTimestamps
          .flatMap(_.nonPollingTimestampsMs.headOption)
          .map(Time.fromMilliseconds)

        val followingLastNonPollingTime = nonPollingTimestamps
          .flatMap(_.mostRecentHomeLatestNonPollingTimestampMs)
          .map(Time.fromMilliseconds)

        val nonPollingTimes = nonPollingTimestamps
          .map(_.nonPollingTimestampsMs)
          .getOrElse(Seq.empty)

        val requestTimeDataRecord = getRequestTimeDataRecord(query, nonPollingTimes)

        FeatureMapBuilder()
          .add(FollowingLastNonPollingTimeFeature, followingLastNonPollingTime)
          .add(LastNonPollingTimeFeature, lastNonPollingTime)
          .add(NonPollingTimesFeature, nonPollingTimes)
          .add(RequestTimeDataRecordFeature, requestTimeDataRecord)
          .build()
      }
  }

  def getRequestTimeDataRecord(query: PipelineQuery, nonPollingTimes: Seq[Long]): DataRecord = {
    val requestTimeMs = query.queryTime.inMillis
    val accountAge = SnowflakeId.timeFromIdOpt(query.getRequiredUserId)
    val timeSinceAccountCreation = accountAge.map(query.queryTime.since)
    val timeSinceEarliestNonPollingRequest =
      nonPollingTimes.lastOption.map(requestTimeMs - _)
    val timeSinceLastNonPollingRequest =
      nonPollingTimes.headOption.map(requestTimeMs - _)

    new DataRecord()
      .setFeatureValue(USER_ID_IS_SNOWFLAKE_ID, accountAge.isDefined)
      .setFeatureValue(
        IS_30_DAY_NEW_USER,
        timeSinceAccountCreation.map(_ < 30.days).getOrElse(false)
      )
      .setFeatureValue(
        IS_12_MONTH_NEW_USER,
        timeSinceAccountCreation.map(_ < 365.days).getOrElse(false)
      )
      .setFeatureValueFromOption(
        ACCOUNT_AGE_INTERVAL,
        timeSinceAccountCreation.flatMap(AccountAgeInterval.fromDuration).map(_.id.toLong)
      )
      .setFeatureValueFromOption(
        TIME_SINCE_VIEWER_ACCOUNT_CREATION_SECS,
        timeSinceAccountCreation.map(_.inSeconds.toDouble)
      )
      .setFeatureValueFromOption(
        TIME_BETWEEN_NON_POLLING_REQUESTS_AVG,
        timeSinceEarliestNonPollingRequest.map(_.toDouble / math.max(1.0, nonPollingTimes.size))
      )
      .setFeatureValueFromOption(
        TIME_SINCE_LAST_NON_POLLING_REQUEST,
        timeSinceLastNonPollingRequest.map(_.toDouble)
      )
  }
}
