package com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.real_time_aggregates

import com.google.inject.name.Named
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.home_mixer.model.HomeFeatures.TopicIdSocialContextFeature
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.TopicCountryEngagementCache
import com.ExTwitter.ml.api.DataRecord
import com.ExTwitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.ExTwitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.ExTwitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.ExTwitter.product_mixer.core.model.common.CandidateWithFeatures
import com.ExTwitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.servo.cache.ReadCache
import com.ExTwitter.timelines.data_processing.ml_util.aggregation_framework.AggregateGroup
import com.ExTwitter.timelines.prediction.common.aggregates.real_time.TimelinesOnlineAggregationFeaturesOnlyConfig._
import javax.inject.Inject
import javax.inject.Singleton

object TopicCountryEngagementRealTimeAggregateFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class TopicCountryEngagementRealTimeAggregateFeatureHydrator @Inject() (
  @Named(TopicCountryEngagementCache) override val client: ReadCache[(Long, String), DataRecord],
  override val statsReceiver: StatsReceiver)
    extends BaseRealTimeAggregateBulkCandidateFeatureHydrator[(Long, String)] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TopicCountryEngagementRealTimeAggregate")

  override val outputFeature: DataRecordInAFeature[TweetCandidate] =
    TopicCountryEngagementRealTimeAggregateFeature

  override val aggregateGroups: Seq[AggregateGroup] = Seq(
    topicCountryRealTimeAggregates
  )

  override val aggregateGroupToPrefix: Map[AggregateGroup, String] = Map(
    topicCountryRealTimeAggregates -> "topic-country_code.timelines.topic_country_engagement_real_time_aggregates."
  )

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
