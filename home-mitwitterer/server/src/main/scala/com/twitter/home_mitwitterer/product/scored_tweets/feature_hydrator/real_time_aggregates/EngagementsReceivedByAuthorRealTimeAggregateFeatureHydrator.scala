package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator.real_time_aggregates

import com.google.inject.name.Named
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.EngagementsReceivedByAuthorCache
import com.twitter.home_mitwitterer.util.CandidatesUtil
import com.twitter.ml.api.DataRecord
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mitwitterer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.servo.cache.ReadCache
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregateGroup
import com.twitter.timelines.prediction.common.aggregates.real_time.TimelinesOnlineAggregationFeaturesOnlyConfig._
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

object EngagementsReceivedByAuthorRealTimeAggregateFeature
    etwittertends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class EngagementsReceivedByAuthorRealTimeAggregateFeatureHydrator @Inject() (
  @Named(EngagementsReceivedByAuthorCache) override val client: ReadCache[Long, DataRecord],
  override val statsReceiver: StatsReceiver)
    etwittertends BaseRealTimeAggregateBulkCandidateFeatureHydrator[Long] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("EngagementsReceivedByAuthorRealTimeAggregate")

  override val outputFeature: DataRecordInAFeature[TweetCandidate] =
    EngagementsReceivedByAuthorRealTimeAggregateFeature

  override val aggregateGroups: Seq[AggregateGroup] = Seq(
    authorEngagementRealTimeAggregatesProd,
    authorShareEngagementsRealTimeAggregates
  )

  override val aggregateGroupToPrefitwitter: Map[AggregateGroup, String] = Map(
    authorShareEngagementsRealTimeAggregates -> "original_author.timelines.author_share_engagements_real_time_aggregates."
  )

  override def keysFromQueryAndCandidates(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Seq[Option[Long]] =
    candidates.map(candidate => CandidatesUtil.getOriginalAuthorId(candidate.features))
}
