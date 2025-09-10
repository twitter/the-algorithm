package com.twitter.home_mixer.functional_component.feature_hydrator.real_time_aggregates

import com.google.inject.name.Named
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.module.RealtimeAggregateFeatureRepositoryModule.keyTransformD1
import com.twitter.home_mixer.module.RealtimeAggregateFeatureRepositoryModule.tweetIdFeature
import com.twitter.home_mixer.param.HomeGlobalParams.EnableTweetRTAMhFallbackParam
import com.twitter.home_mixer.param.HomeGlobalParams.EnableTweetRTAMhOnlyParam
import com.twitter.home_mixer.param.HomeMixerInjectionNames.RTAManhattanStore
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TweetEngagementCache
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.home_mixer_features.{thriftjava => t}
import com.twitter.ml.api.DataRecord
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.servo.cache.ReadCache
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregateGroup
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregationKey
import com.twitter.timelines.prediction.common.aggregates.real_time.TimelinesOnlineAggregationFeaturesOnlyConfig._
import com.twitter.util.Future
import com.twitter.util.Try
import com.twitter.storehaus.ReadableStore
import com.twitter.timelines.realtime_aggregates.{thriftscala => thrift}
import com.twitter.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

object TweetEngagementRealTimeAggregateFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class TweetEngagementRealTimeAggregateFeatureHydrator @Inject() (
  override val homeMixerFeatureService: t.HomeMixerFeatures.ServiceToClient,
  @Named(TweetEngagementCache) override val client: ReadCache[Long, DataRecord],
  @Named(RTAManhattanStore) mhClient: Option[ReadableStore[thrift.AggregationKey, DataRecord]],
  override val statsReceiver: StatsReceiver)
    extends FlagBasedRealTimeAggregateBulkCandidateFeatureHydrator[Long] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TweetEngagementRealTimeAggregate")

  override val outputFeature: DataRecordInAFeature[TweetCandidate] =
    TweetEngagementRealTimeAggregateFeature

  val batchSize = 64

  override val aggregateGroups: Seq[AggregateGroup] = Seq(
    tweetEngagement30MinuteCountsProd,
    tweetEngagementTotalCountsProd,
    tweetEngagementUserStateRealTimeAggregatesProd,
    tweetNegativeEngagementUserStateRealTimeAggregates,
    tweetNegativeEngagement6HourCounts,
    tweetNegativeEngagementTotalCounts,
    tweetShareEngagementsRealTimeAggregates,
    tweetBCEDwellEngagementsRealTimeAggregates
  )

  override val aggregateGroupToPrefix: Map[AggregateGroup, String] = Map(
    tweetShareEngagementsRealTimeAggregates -> "original_tweet.timelines.tweet_share_engagements_real_time_aggregates.",
    tweetBCEDwellEngagementsRealTimeAggregates -> "original_tweet.timelines.tweet_bce_dwell_engagements_real_time_aggregates."
  )

  def serializeKey(key: Long): String = {
    keyTransformD1(tweetIdFeature)(key)
  }

  override def keysFromQueryAndCandidates(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Seq[Option[Long]] = {
    val keys = candidates
      .map(candidate => Some(CandidatesUtil.getOriginalTweetId(candidate)))
    keys
  }

  def convert(key: Long): thrift.AggregationKey = {
    val ak = AggregationKey(Map(tweetIdFeature -> key), Map.empty)
    thrift.AggregationKey(
      ak.discreteFeaturesById,
      ak.textFeaturesById
    )
  }

  def fetchAndConstructDataRecordFromMh(
    possiblyKeys: Seq[Option[Long]]
  ): Future[Seq[Try[DataRecord]]] = {
    Future
      .collect {
        possiblyKeys.flatten
          .map { convert(_) }
          .grouped(batchSize).map { keyGroup =>
            val results = mhClient.get.multiGet(keyGroup.toSet)
            Future.collect(keyGroup.flatMap(results.get)).map { drSeq =>
              drSeq.map { drOpt =>
                if (drOpt.isEmpty) statsReceiver.scope("mhTweetRTA").counter("empty").incr()
                else statsReceiver.scope("mhTweetRTA").counter("non_empty").incr()
                Try(drOpt.map(postTransformer).getOrElse(EmptyDataRecord))
              }
            }
          }.toSeq
      }.map(_.flatten)
  }

  def getFetchFunc(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Future[Seq[Try[DataRecord]]] = {
    val fetchFromMhOnly = query.params.getBoolean(EnableTweetRTAMhOnlyParam)
    val fetchFromMhAsFallBack = query.params.getBoolean(EnableTweetRTAMhFallbackParam)
    val possiblyKeys = keysFromQueryAndCandidates(query, candidates)
    val stats = statsReceiver.scope("tweet_real_time_rta")
    if (fetchFromMhOnly) {
      fetchAndConstructDataRecordFromMh(possiblyKeys)
    } else if (fetchFromMhAsFallBack) {
      fetchAndConstructDataRecordsWithFallback(
        possiblyKeys,
        stats,
        fetchAndConstructDataRecords,
        fetchAndConstructDataRecordFromMh,
      )
    } else {
      fetchAndConstructDataRecords(possiblyKeys)
    }
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    getFetchFunc(query, candidates).map { dataRecords =>
      val featureMaps = dataRecords.map { dataRecord =>
        FeatureMapBuilder().add(outputFeature, dataRecord).build()
      }
      featureMaps
    }
  }
}
