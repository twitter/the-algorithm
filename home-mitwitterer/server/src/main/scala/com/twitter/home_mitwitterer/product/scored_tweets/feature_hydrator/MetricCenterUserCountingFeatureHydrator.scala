package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mitwitterer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.MetricCenterUserCountingFeatureRepository
import com.twitter.home_mitwitterer.util.ObservedKeyValueResultHandler
import com.twitter.onboarding.relevance.features.{thriftjava => rf}
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.util.OffloadFuturePools
import com.twitter.servo.keyvalue.KeyValueResult
import com.twitter.servo.repository.KeyValueRepository
import com.twitter.stitch.Stitch
import com.twitter.util.Future
import javatwitter.inject.Inject
import javatwitter.inject.Named
import javatwitter.inject.Singleton

object MetricCenterUserCountingFeature
    etwittertends Feature[TweetCandidate, Option[rf.MCUserCountingFeatures]]

@Singleton
class MetricCenterUserCountingFeatureHydrator @Inject() (
  @Named(MetricCenterUserCountingFeatureRepository) client: KeyValueRepository[Seq[
    Long
  ], Long, rf.MCUserCountingFeatures],
  override val statsReceiver: StatsReceiver)
    etwittertends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with ObservedKeyValueResultHandler {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("MetricCenterUserCounting")

  override val features: Set[Feature[_, _]] = Set(MetricCenterUserCountingFeature)

  override val statScope: String = identifier.toString

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val possiblyAuthorIds = etwittertractKeys(candidates)
    val userIds = possiblyAuthorIds.flatten

    val response: Future[KeyValueResult[Long, rf.MCUserCountingFeatures]] =
      if (userIds.isEmpty) Future.value(KeyValueResult.empty) else client(userIds)

    response.map { result =>
      possiblyAuthorIds.map { possiblyAuthorId =>
        val value = observedGet(key = possiblyAuthorId, keyValueResult = result)
        FeatureMapBuilder().add(MetricCenterUserCountingFeature, value).build()
      }
    }
  }

  private def etwittertractKeys(
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Seq[Option[Long]] = {
    candidates.map { candidate =>
      candidate.features
        .getTry(AuthorIdFeature)
        .toOption
        .flatten
    }
  }
}
