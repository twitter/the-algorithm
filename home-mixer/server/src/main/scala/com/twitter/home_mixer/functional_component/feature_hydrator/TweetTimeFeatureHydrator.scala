package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.EarlybirdFeature
import com.twitter.home_mixer.model.HomeFeatures.NonPollingTimesFeature
import com.twitter.home_mixer.model.HomeFeatures.SourceTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetAgeFeature
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.util.FDsl._
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.stitch.Stitch
import com.twitter.timelines.prediction.features.time_features.TimeDataRecordFeatures._
import com.twitter.util.Duration

import scala.collection.Searching._

object TweetTimeDataRecordFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

object TweetTimeFeatureHydrator
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with WithDefaultFeatureMap {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("TweetTime")

  override val features: Set[Feature[_, _]] = Set(TweetTimeDataRecordFeature, TweetAgeFeature)

  override val defaultFeatureMap: FeatureMap =
    FeatureMap(
      TweetTimeDataRecordFeature,
      TweetTimeDataRecordFeature.defaultValue,
      TweetAgeFeature,
      None
    )

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]],
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offload {

    val nonPollingTimestampsMs = query.features.get.getOrElse(NonPollingTimesFeature, Seq.empty)

    candidates.map { candidate =>
      val tweetFeatures = candidate.features.getOrElse(EarlybirdFeature, None)
      val timeSinceTweetCreation =
        SnowflakeId.timeFromIdOpt(candidate.candidate.id).map(query.queryTime.since)
      val timeSinceTweetCreationMs = timeSinceTweetCreation.map(_.inMillis)

      val timeSinceSourceTweetCreationOpt = candidate.features
        .getOrElse(SourceTweetIdFeature, None)
        .flatMap { sourceTweetId =>
          SnowflakeId.timeFromIdOpt(sourceTweetId).map(query.queryTime.since)
        }.orElse(timeSinceTweetCreation)

      val lastFavSinceCreationHrs =
        tweetFeatures.flatMap(_.lastFavSinceCreationHrs).map(_.toDouble)
      val lastRetweetSinceCreationHrs =
        tweetFeatures.flatMap(_.lastRetweetSinceCreationHrs).map(_.toDouble)
      val lastReplySinceCreationHrs =
        tweetFeatures.flatMap(_.lastReplySinceCreationHrs).map(_.toDouble)
      val lastQuoteSinceCreationHrs =
        tweetFeatures.flatMap(_.lastQuoteSinceCreationHrs).map(_.toDouble)
      val timeSinceLastFavoriteHrs =
        getTimeSinceLastEngagementHrs(lastFavSinceCreationHrs, timeSinceSourceTweetCreationOpt)
      val timeSinceLastRetweetHrs =
        getTimeSinceLastEngagementHrs(lastRetweetSinceCreationHrs, timeSinceSourceTweetCreationOpt)
      val timeSinceLastReplyHrs =
        getTimeSinceLastEngagementHrs(lastReplySinceCreationHrs, timeSinceSourceTweetCreationOpt)
      val timeSinceLastQuoteHrs =
        getTimeSinceLastEngagementHrs(lastQuoteSinceCreationHrs, timeSinceSourceTweetCreationOpt)

      val timeSinceLastNonPollingRequest =
        nonPollingTimestampsMs.headOption.map(query.queryTime.inMillis - _)

      val nonPollingRequestsSinceTweetCreation =
        if (nonPollingTimestampsMs.nonEmpty && timeSinceTweetCreationMs.isDefined) {
          nonPollingTimestampsMs
            .search(timeSinceTweetCreationMs.get)(Ordering[Long].reverse)
            .insertionPoint
        } else 0.0

      val tweetAgeRatio =
        if (timeSinceTweetCreationMs.exists(_ > 0.0) && timeSinceLastNonPollingRequest.isDefined) {
          timeSinceLastNonPollingRequest.get / timeSinceTweetCreationMs.get.toDouble
        } else 0.0

      val dataRecord = new DataRecord()
        .setFeatureValue(IS_TWEET_RECYCLED, false)
        .setFeatureValue(TWEET_AGE_RATIO, tweetAgeRatio)
        .setFeatureValueFromOption(
          TIME_SINCE_TWEET_CREATION,
          timeSinceTweetCreationMs.map(_.toDouble)
        )
        .setFeatureValue(
          NON_POLLING_REQUESTS_SINCE_TWEET_CREATION,
          nonPollingRequestsSinceTweetCreation
        )
        .setFeatureValueFromOption(LAST_FAVORITE_SINCE_CREATION_HRS, lastFavSinceCreationHrs)
        .setFeatureValueFromOption(LAST_RETWEET_SINCE_CREATION_HRS, lastRetweetSinceCreationHrs)
        .setFeatureValueFromOption(LAST_REPLY_SINCE_CREATION_HRS, lastReplySinceCreationHrs)
        .setFeatureValueFromOption(LAST_QUOTE_SINCE_CREATION_HRS, lastQuoteSinceCreationHrs)
        .setFeatureValueFromOption(TIME_SINCE_LAST_FAVORITE_HRS, timeSinceLastFavoriteHrs)
        .setFeatureValueFromOption(TIME_SINCE_LAST_RETWEET_HRS, timeSinceLastRetweetHrs)
        .setFeatureValueFromOption(TIME_SINCE_LAST_REPLY_HRS, timeSinceLastReplyHrs)
        .setFeatureValueFromOption(TIME_SINCE_LAST_QUOTE_HRS, timeSinceLastQuoteHrs)

      FeatureMap(TweetTimeDataRecordFeature, dataRecord, TweetAgeFeature, timeSinceTweetCreationMs)
    }
  }

  private def getTimeSinceLastEngagementHrs(
    lastEngagementTimeSinceCreationHrsOpt: Option[Double],
    timeSinceTweetCreation: Option[Duration]
  ): Option[Double] = lastEngagementTimeSinceCreationHrsOpt.flatMap { lastEngagementTimeHrs =>
    timeSinceTweetCreation.map(_.inHours - lastEngagementTimeHrs)
  }
}
