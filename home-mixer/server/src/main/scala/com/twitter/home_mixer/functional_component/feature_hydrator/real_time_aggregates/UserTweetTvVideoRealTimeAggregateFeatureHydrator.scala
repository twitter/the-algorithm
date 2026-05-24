package com.twitter.home_mixer.functional_component.feature_hydrator.real_time_aggregates

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TvVideoByUserTweetCache
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.ml.api.DataRecord
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.real_time_aggregates.BaseRealTimeAggregateBulkCandidateFeatureHydrator
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.servo.cache.ReadCache
import com.twitter.stitch.Stitch
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregateGroup
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.metrics.AggregateFeature
import com.twitter.timelines.prediction.common.aggregates.real_time.tv.TvOnlineAggregationFeaturesOnlyConfig
import com.twitter.util.Return
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

object DummyFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = {
    new DataRecord()
  }
}

// The feature value is keyed by the RTA aggregation group prefix, then the AggregateFeature
// for ease of access.
object UserTweetTvVideoRealtimeAggregatesFeatures
    extends Feature[TweetCandidate, Option[Map[String, Map[AggregateFeature[_], Double]]]]

@Singleton
class UserTweetTvVideoRealTimeAggregateFeatureHydrator @Inject() (
  @Named(TvVideoByUserTweetCache) override val client: ReadCache[(Long, Long), DataRecord],
  override val statsReceiver: StatsReceiver)
    extends BaseRealTimeAggregateBulkCandidateFeatureHydrator[(Long, Long), TweetCandidate]
    with Conditionally[ScoredTweetsQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("UserTweetTvVideoRealTimeAggregate")

  override val outputFeature: DataRecordInAFeature[TweetCandidate] = DummyFeature

  override def onlyIf(
    query: ScoredTweetsQuery
  ): Boolean = query.videoType.contains(hmt.VideoType.LongForm)

  override def features: Set[Feature[_, _]] = Set(
    UserTweetTvVideoRealtimeAggregatesFeatures
  )

  override val aggregateGroups: Seq[AggregateGroup] = Seq(
    TvOnlineAggregationFeaturesOnlyConfig.userTweetViewRealTimeAggregates,
    TvOnlineAggregationFeaturesOnlyConfig.userTweetPlaybackRealTimeAggregates,
    TvOnlineAggregationFeaturesOnlyConfig.userTweetImpressionRealTimeAggregates
  )

  override val aggregateGroupToPrefix: Map[AggregateGroup, String] = Map.empty

  override def keysFromQueryAndCandidates(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Seq[Option[(Long, Long)]] = {
    candidates.map(candidate =>
      Some((query.getRequiredUserId, CandidatesUtil.getOriginalTweetId(candidate))))
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    fetchValues(keysFromQueryAndCandidates(query, candidates)).map { featureToAggValueMaps =>
      featureToAggValueMaps.map { featureToAggValueMap =>
        val value = featureToAggValueMap match {
          case Return(r) => Some(r)
          case _ => None
        }
        FeatureMapBuilder()
          .add(UserTweetTvVideoRealtimeAggregatesFeatures, value)
          .build()
      }
    }
  }
}
