package com.twitter.home_mixer.functional_component.feature_hydrator.real_time_aggregates

import com.google.inject.name.Named
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.TopicIdSocialContextFeature
import com.twitter.home_mixer.module.RealtimeAggregateFeatureRepositoryModule.countryCodeFeature
import com.twitter.home_mixer.module.RealtimeAggregateFeatureRepositoryModule.keyTransformD1T1
import com.twitter.home_mixer.module.RealtimeAggregateFeatureRepositoryModule.topicIdFeature
import com.twitter.home_mixer.param.HomeGlobalParams.EnableTopicCountryBasedRealTimeAggregateFeatureHydratorParam
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TopicCountryEngagementCache
import com.twitter.home_mixer_features.{thriftjava => t}
import com.twitter.ml.api.DataRecord
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.servo.cache.ReadCache
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregateGroup
import com.twitter.timelines.prediction.common.aggregates.real_time.TimelinesOnlineAggregationFeaturesOnlyConfig._
import javax.inject.Inject
import javax.inject.Singleton

object TopicCountryEngagementRealTimeAggregateFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class TopicCountryEngagementRealTimeAggregateFeatureHydrator @Inject() (
  override val homeMixerFeatureService: t.HomeMixerFeatures.ServiceToClient,
  @Named(TopicCountryEngagementCache) override val client: ReadCache[(Long, String), DataRecord],
  override val statsReceiver: StatsReceiver)
    extends FlagBasedRealTimeAggregateBulkCandidateFeatureHydrator[(Long, String)]
    with Conditionally[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TopicCountryEngagementRealTimeAggregate")

  override def onlyIf(query: PipelineQuery): Boolean =
    query.params(EnableTopicCountryBasedRealTimeAggregateFeatureHydratorParam)

  override val outputFeature: DataRecordInAFeature[TweetCandidate] =
    TopicCountryEngagementRealTimeAggregateFeature

  override val aggregateGroups: Seq[AggregateGroup] = Seq(
    topicCountryRealTimeAggregates
  )

  override val aggregateGroupToPrefix: Map[AggregateGroup, String] = Map(
    topicCountryRealTimeAggregates -> "topic-country_code.timelines.topic_country_engagement_real_time_aggregates."
  )

  def serializeKey(key: (Long, String)): String = {
    keyTransformD1T1(topicIdFeature, countryCodeFeature)(key)
  }

  override def keysFromQueryAndCandidates(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Seq[Option[(Long, String)]] = {
    candidates.map { candidate =>
      val maybeTopicId = candidate.features
        .getTry(TopicIdSocialContextFeature)
        .toOption
        .flatten

      val maybeCountryCode = query.clientContext.countryCode

      for {
        topicId <- maybeTopicId
        countryCode <- maybeCountryCode
      } yield (topicId, countryCode)
    }
  }
}
