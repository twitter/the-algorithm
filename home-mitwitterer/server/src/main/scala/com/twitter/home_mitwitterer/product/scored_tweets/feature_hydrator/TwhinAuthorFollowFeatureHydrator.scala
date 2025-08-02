package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.TwhinAuthorFollowFeatureRepository
import com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator.adapters.twhin_embeddings.TwhinAuthorFollowEmbeddingsAdapter
import com.twitter.home_mitwitterer.util.CandidatesUtil
import com.twitter.home_mitwitterer.util.ObservedKeyValueResultHandler
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.{thriftscala => ml}
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mitwitterer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.util.OffloadFuturePools
import com.twitter.servo.repository.KeyValueRepository
import com.twitter.servo.repository.KeyValueResult
import com.twitter.stitch.Stitch
import com.twitter.util.Future
import com.twitter.util.Try
import javatwitter.inject.Inject
import javatwitter.inject.Named
import javatwitter.inject.Singleton
import scala.collection.JavaConverters._

object TwhinAuthorFollowFeature
    etwittertends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class TwhinAuthorFollowFeatureHydrator @Inject() (
  @Named(TwhinAuthorFollowFeatureRepository)
  client: KeyValueRepository[Seq[Long], Long, ml.FloatTensor],
  override val statsReceiver: StatsReceiver)
    etwittertends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with ObservedKeyValueResultHandler {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TwhinAuthorFollow")

  override val features: Set[Feature[_, _]] = Set(TwhinAuthorFollowFeature)

  override val statScope: String = identifier.toString

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val possiblyAuthorIds = etwittertractKeys(candidates)
    val authorIds = possiblyAuthorIds.flatten

    val response: Future[KeyValueResult[Long, ml.FloatTensor]] =
      if (authorIds.isEmpty) Future.value(KeyValueResult.empty) else client(authorIds)

    response.map { result =>
      possiblyAuthorIds.map { possiblyAuthorId =>
        val value = observedGet(key = possiblyAuthorId, keyValueResult = result)
        val transformedValue = postTransformer(value)

        FeatureMapBuilder().add(TwhinAuthorFollowFeature, transformedValue).build()
      }
    }
  }

  private def postTransformer(embedding: Try[Option[ml.FloatTensor]]): Try[DataRecord] = {
    embedding.map { floatTensor =>
      TwhinAuthorFollowEmbeddingsAdapter.adaptToDataRecords(floatTensor).asScala.head
    }
  }

  private def etwittertractKeys(
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Seq[Option[Long]] = {
    candidates.map { candidate =>
      CandidatesUtil.getOriginalAuthorId(candidate.features)
    }
  }
}
