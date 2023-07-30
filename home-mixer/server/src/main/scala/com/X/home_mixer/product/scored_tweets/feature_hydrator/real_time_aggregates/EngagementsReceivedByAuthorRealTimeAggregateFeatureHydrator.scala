package com.X.home_mixer.product.scored_tweets.feature_hydrator.real_time_aggregates

import com.google.inject.name.Named
import com.X.finagle.stats.StatsReceiver
import com.X.home_mixer.param.HomeMixerInjectionNames.EngagementsReceivedByAuthorCache
import com.X.home_mixer.util.CandidatesUtil
import com.X.ml.api.DataRecord
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.X.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.servo.cache.ReadCache
import com.X.timelines.data_processing.ml_util.aggregation_framework.AggregateGroup
import com.X.timelines.prediction.common.aggregates.real_time.TimelinesOnlineAggregationFeaturesOnlyConfig._
import javax.inject.Inject
import javax.inject.Singleton

object EngagementsReceivedByAuthorRealTimeAggregateFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class EngagementsReceivedByAuthorRealTimeAggregateFeatureHydrator @Inject() (
  @Named(EngagementsReceivedByAuthorCache) override val client: ReadCache[Long, DataRecord],
  override val statsReceiver: StatsReceiver)
    extends BaseRealTimeAggregateBulkCandidateFeatureHydrator[Long] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("EngagementsReceivedByAuthorRealTimeAggregate")

  override val outputFeature: DataRecordInAFeature[TweetCandidate] =
    EngagementsReceivedByAuthorRealTimeAggregateFeature

  override val aggregateGroups: Seq[AggregateGroup] = Seq(
    authorEngagementRealTimeAggregatesProd,
    authorShareEngagementsRealTimeAggregates
  )

  override val aggregateGroupToPrefix: Map[AggregateGroup, String] = Map(
    authorShareEngagementsRealTimeAggregates -> "original_author.timelines.author_share_engagements_real_time_aggregates."
  )

  override def keysFromQueryAndCandidates(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Seq[Option[Long]] =
    candidates.map(candidate => CandidatesUtil.getOriginalAuthorId(candidate.features))
}
