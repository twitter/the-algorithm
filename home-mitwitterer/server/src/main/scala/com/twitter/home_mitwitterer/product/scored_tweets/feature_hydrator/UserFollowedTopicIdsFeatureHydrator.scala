package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mitwitterer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.UserFollowedTopicIdsRepository
import com.twitter.home_mitwitterer.util.ObservedKeyValueResultHandler
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
import com.twitter.util.Try
import javatwitter.inject.Inject
import javatwitter.inject.Named
import javatwitter.inject.Singleton

object UserFollowedTopicIdsFeature etwittertends Feature[TweetCandidate, Seq[Long]]

@Singleton
class UserFollowedTopicIdsFeatureHydrator @Inject() (
  @Named(UserFollowedTopicIdsRepository)
  client: KeyValueRepository[Seq[Long], Long, Seq[Long]],
  override val statsReceiver: StatsReceiver)
    etwittertends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with ObservedKeyValueResultHandler {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("UserFollowedTopicIds")

  override val features: Set[Feature[_, _]] = Set(UserFollowedTopicIdsFeature)

  override val statScope: String = identifier.toString

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val possiblyAuthorIds = etwittertractKeys(candidates)
    val authorIds = possiblyAuthorIds.flatten

    val response: Future[KeyValueResult[Long, Seq[Long]]] =
      if (authorIds.isEmpty) Future.value(KeyValueResult.empty) else client(authorIds)

    response.map { result =>
      possiblyAuthorIds.map { possiblyAuthorId =>
        val value = observedGet(key = possiblyAuthorId, keyValueResult = result)
        val transformedValue = postTransformer(value)

        FeatureMapBuilder().add(UserFollowedTopicIdsFeature, transformedValue).build()
      }
    }
  }

  private def postTransformer(input: Try[Option[Seq[Long]]]): Try[Seq[Long]] = {
    input.map(_.getOrElse(Seq.empty[Long]))
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
