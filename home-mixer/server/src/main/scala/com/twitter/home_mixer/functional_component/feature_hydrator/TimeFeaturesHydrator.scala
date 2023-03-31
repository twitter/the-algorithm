package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.conversions.DurationOps._
import com.twitter.home_mixer.model.HomeFeatures.EarlybirdFeature
import com.twitter.home_mixer.model.HomeFeatures.NonPollingTimesFeature
import com.twitter.home_mixer.model.HomeFeatures.SourceTweetIdFeature
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.RichDataRecord
import com.twitter.ml.api.util.FDsl._
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.CandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.search.common.features.{thriftscala => sc}
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.stitch.Stitch
import com.twitter.timelines.prediction.features.time_features.AccountAgeInterval
import com.twitter.timelines.prediction.features.time_features.TimeDataRecordFeatures._
import com.twitter.timelines.prediction.features.time_features.TimeFeatures
import com.twitter.util.Duration
import scala.collection.Searching._

object TimeFeaturesDataRecordFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

object TimeFeaturesHydrator extends CandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("TimeFeatures")

  override val features: Set[Feature[_, _]] = Set(TimeFeaturesDataRecordFeature)

  override def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    existingFeatures: FeatureMap
  ): Stitch[FeatureMap] = {
    Stitch.value {
      val richDataRecord = new RichDataRecord()
      setTimeFeatures(richDataRecord, candidate, existingFeatures, query)
      FeatureMapBuilder()
        .add(TimeFeaturesDataRecordFeature, richDataRecord.getRecord)
        .build()
    }
  }

  private def setTimeFeatures(
    richDataRecord: RichDataRecord,
    candidate: TweetCandidate,
    existingFeatures: FeatureMap,
    query: PipelineQuery,
  ): Unit = {
    val timeFeaturesOpt = getTimeFeatures(query, candidate, existingFeatures)
    timeFeaturesOpt.foreach(timeFeatures => setFeatures(timeFeatures, richDataRecord))
  }

  private[feature_hydrator] def getTimeFeatures(
    query: PipelineQuery,
    candidate: TweetCandidate,
    existingFeatures: FeatureMap,
  ): Option[TimeFeatures] = {
    for {
      requestTimestampMs <- Some(query.queryTime.inMilliseconds)
      tweetId <- Some(candidate.id)
      viewerId <- query.getOptionalUserId
      tweetCreationTimeMs <- timeFromTweetOrUserId(tweetId)
      timeSinceTweetCreation = requestTimestampMs - tweetCreationTimeMs
      accountAgeDurationOpt = timeFromTweetOrUserId(viewerId).map { viewerAccountCreationTimeMs =>
        Duration.fromMilliseconds(requestTimestampMs - viewerAccountCreationTimeMs)
      }
      timeSinceSourceTweetCreation =
        existingFeatures
          .getOrElse(SourceTweetIdFeature, None)
          .flatMap { sourceTweetId =>
            timeFromTweetOrUserId(sourceTweetId).map { sourceTweetCreationTimeMs =>
              requestTimestampMs - sourceTweetCreationTimeMs
            }
          }
          .getOrElse(timeSinceTweetCreation)
      if (timeSinceTweetCreation > 0 && timeSinceSourceTweetCreation > 0)
    } yield {
      val timeFeatures = TimeFeatures(
        timeSinceTweetCreation = timeSinceTweetCreation,
        timeSinceSourceTweetCreation = timeSinceSourceTweetCreation,
        timeSinceViewerAccountCreationSecs = accountAgeDurationOpt.map(_.inSeconds),
        isDay30NewUser = accountAgeDurationOpt.map(_ < 30.days).getOrElse(false),
        isMonth12NewUser = accountAgeDurationOpt.map(_ < 365.days).getOrElse(false),
        accountAgeInterval = accountAgeDurationOpt.flatMap(AccountAgeInterval.fromDuration),
        isTweetRecycled = false // only set in RecyclableTweetCandidateFilter, but it's not used
      )

      val timeFeaturesWithLastEngagement = addLastEngagementTimeFeatures(
        existingFeatures.getOrElse(EarlybirdFeature, None),
        timeFeatures,
        timeSinceSourceTweetCreation
      ).getOrElse(timeFeatures)

      val nonPollingTimestampsMs =
        query.features.map(_.getOrElse(NonPollingTimesFeature, Seq.empty))
      val timeFeaturesWithNonPollingOpt = addNonPollingTimeFeatures(
        timeFeaturesWithLastEngagement,
        requestTimestampMs,
        tweetCreationTimeMs,
        nonPollingTimestampsMs
      )
      timeFeaturesWithNonPollingOpt.getOrElse(timeFeaturesWithLastEngagement)
    }
  }

  private def timeFromTweetOrUserId(tweetOrUserId: Long): Option[Long] = {
    if (SnowflakeId.isSnowflakeId(tweetOrUserId))
      Some(SnowflakeId(tweetOrUserId).time.inMilliseconds)
    else None
  }

  private def addLastEngagementTimeFeatures(
    tweetFeaturesOpt: Option[sc.ThriftTweetFeatures],
    timeFeatures: TimeFeatures,
    timeSinceSourceTweetCreation: Long
  ): Option[TimeFeatures] = {
    tweetFeaturesOpt.map { tweetFeatures =>
      val lastFavSinceCreationHrs = tweetFeatures.lastFavSinceCreationHrs.map(_.toDouble)
      val lastRetweetSinceCreationHrs = tweetFeatures.lastRetweetSinceCreationHrs.map(_.toDouble)
      val lastReplySinceCreationHrs = tweetFeatures.lastReplySinceCreationHrs.map(_.toDouble)
      val lastQuoteSinceCreationHrs = tweetFeatures.lastQuoteSinceCreationHrs.map(_.toDouble)

      timeFeatures.copy(
        lastFavSinceCreationHrs = lastFavSinceCreationHrs,
        lastRetweetSinceCreationHrs = lastRetweetSinceCreationHrs,
        lastReplySinceCreationHrs = lastReplySinceCreationHrs,
        lastQuoteSinceCreationHrs = lastQuoteSinceCreationHrs,
        timeSinceLastFavoriteHrs = getTimeSinceLastEngagementHrs(
          lastFavSinceCreationHrs,
          timeSinceSourceTweetCreation
        ),
        timeSinceLastRetweetHrs = getTimeSinceLastEngagementHrs(
          lastRetweetSinceCreationHrs,
          timeSinceSourceTweetCreation
        ),
        timeSinceLastReplyHrs = getTimeSinceLastEngagementHrs(
          lastReplySinceCreationHrs,
          timeSinceSourceTweetCreation
        ),
        timeSinceLastQuoteHrs = getTimeSinceLastEngagementHrs(
          lastQuoteSinceCreationHrs,
          timeSinceSourceTweetCreation
        )
      )
    }
  }

  private def addNonPollingTimeFeatures(
    timeFeatures: TimeFeatures,
    requestTimestampMs: Long,
    creationTimeMs: Long,
    nonPollingTimestampsMs: Option[Seq[Long]]
  ): Option[TimeFeatures] = {
    for {
      nonPollingTimestampsMs <- nonPollingTimestampsMs
      lastNonPollingTimestampMs <- nonPollingTimestampsMs.headOption
      earliestNonPollingTimestampMs <- nonPollingTimestampsMs.lastOption
    } yield {
      val timeSinceLastNonPollingRequest = requestTimestampMs - lastNonPollingTimestampMs
      val tweetAgeRatio = timeSinceLastNonPollingRequest / math.max(
        1.0,
        timeFeatures.timeSinceTweetCreation
      )
      /*
       * Non-polling timestamps are stored in chronological order.
       * The latest timestamps occur first, therefore we need to explicitly search in reverse order.
       */
      val nonPollingRequestsSinceTweetCreation =
        if (nonPollingTimestampsMs.nonEmpty) {
          nonPollingTimestampsMs.search(creationTimeMs)(Ordering[Long].reverse).insertionPoint
        } else {
          0
        }
      /*
       * Calculate the average time between non-polling requests; include
       * request time in this calculation as latest timestamp.
       */
      val timeBetweenNonPollingRequestsAvg =
        (requestTimestampMs - earliestNonPollingTimestampMs) / math
          .max(1.0, nonPollingTimestampsMs.size)
      val timeFeaturesWithNonPolling = timeFeatures.copy(
        timeBetweenNonPollingRequestsAvg = Some(timeBetweenNonPollingRequestsAvg),
        timeSinceLastNonPollingRequest = Some(timeSinceLastNonPollingRequest),
        nonPollingRequestsSinceTweetCreation = Some(nonPollingRequestsSinceTweetCreation),
        tweetAgeRatio = Some(tweetAgeRatio)
      )
      timeFeaturesWithNonPolling
    }
  }

  private[this] def getTimeSinceLastEngagementHrs(
    lastEngagementTimeSinceCreationHrsOpt: Option[Double],
    timeSinceTweetCreation: Long
  ): Option[Double] = {
    lastEngagementTimeSinceCreationHrsOpt.map { lastEngagementTimeSinceCreationHrs =>
      val timeSinceTweetCreationHrs = (timeSinceTweetCreation / (60 * 60 * 1000)).toInt
      timeSinceTweetCreationHrs - lastEngagementTimeSinceCreationHrs
    }
  }

  private def setFeatures(features: TimeFeatures, richDataRecord: RichDataRecord): Unit = {
    val record = richDataRecord.getRecord
      .setFeatureValue(IS_TWEET_RECYCLED, features.isTweetRecycled)
      .setFeatureValue(TIME_SINCE_TWEET_CREATION, features.timeSinceTweetCreation)
      .setFeatureValueFromOption(
        TIME_SINCE_VIEWER_ACCOUNT_CREATION_SECS,
        features.timeSinceViewerAccountCreationSecs)
      .setFeatureValue(
        USER_ID_IS_SNOWFLAKE_ID,
        features.timeSinceViewerAccountCreationSecs.isDefined
      )
      .setFeatureValueFromOption(ACCOUNT_AGE_INTERVAL, features.accountAgeInterval.map(_.id.toLong))
      .setFeatureValue(IS_30_DAY_NEW_USER, features.isDay30NewUser)
      .setFeatureValue(IS_12_MONTH_NEW_USER, features.isMonth12NewUser)
      .setFeatureValueFromOption(LAST_FAVORITE_SINCE_CREATION_HRS, features.lastFavSinceCreationHrs)
      .setFeatureValueFromOption(
        LAST_RETWEET_SINCE_CREATION_HRS,
        features.lastRetweetSinceCreationHrs
      )
      .setFeatureValueFromOption(LAST_REPLY_SINCE_CREATION_HRS, features.lastReplySinceCreationHrs)
      .setFeatureValueFromOption(LAST_QUOTE_SINCE_CREATION_HRS, features.lastQuoteSinceCreationHrs)
      .setFeatureValueFromOption(TIME_SINCE_LAST_FAVORITE_HRS, features.timeSinceLastFavoriteHrs)
      .setFeatureValueFromOption(TIME_SINCE_LAST_RETWEET_HRS, features.timeSinceLastRetweetHrs)
      .setFeatureValueFromOption(TIME_SINCE_LAST_REPLY_HRS, features.timeSinceLastReplyHrs)
      .setFeatureValueFromOption(TIME_SINCE_LAST_QUOTE_HRS, features.timeSinceLastQuoteHrs)
    /*
     * set features whose values are optional as some users do not have non-polling timestamps
     */
    features.timeBetweenNonPollingRequestsAvg.foreach(
      record.setFeatureValue(TIME_BETWEEN_NON_POLLING_REQUESTS_AVG, _)
    )
    features.timeSinceLastNonPollingRequest.foreach(
      record.setFeatureValue(TIME_SINCE_LAST_NON_POLLING_REQUEST, _)
    )
    features.nonPollingRequestsSinceTweetCreation.foreach(
      record.setFeatureValue(NON_POLLING_REQUESTS_SINCE_TWEET_CREATION, _)
    )
    features.tweetAgeRatio.foreach(record.setFeatureValue(TWEET_AGE_RATIO, _))
  }
}
