package com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator

import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.UserFollowedTopicIdsRepository
import com.ExTwitter.home_mixer.util.ObservedKeyValueResultHandler
import com.ExTwitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.ExTwitter.product_mixer.core.model.common.CandidateWithFeatures
import com.ExTwitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.product_mixer.core.util.OffloadFuturePools
import com.ExTwitter.servo.keyvalue.KeyValueResult
import com.ExTwitter.servo.repository.KeyValueRepository
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.util.Future
import com.ExTwitter.util.Try
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

object UserFollowedTopicIdsFeature extends Feature[TweetCandidate, Seq[Long]]

@Singleton
class UserFollowedTopicIdsFeatureHydrator @Inject() (
  @Named(UserFollowedTopicIdsRepository)
  client: KeyValueRepository[Seq[Long], Long, Seq[Long]],
  override val statsReceiver: StatsReceiver)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with ObservedKeyValueResultHandler {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("UserFollowedTopicIds")

  override val features: Set[Feature[_, _]] = Set(UserFollowedTopicIdsFeature)

  override val statScope: String = identifier.toString

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val possiblyAuthorIds = extractKeys(candidates)
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

  private def extractKeys(
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