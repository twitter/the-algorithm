package com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator

import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.TwhinAuthorFollowFeatureRepository
import com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.adapters.twhin_embeddings.TwhinAuthorFollowEmbeddingsAdapter
import com.ExTwitter.home_mixer.util.CandidatesUtil
import com.ExTwitter.home_mixer.util.ObservedKeyValueResultHandler
import com.ExTwitter.ml.api.DataRecord
import com.ExTwitter.ml.api.{thriftscala => ml}
import com.ExTwitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.ExTwitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.ExTwitter.product_mixer.core.model.common.CandidateWithFeatures
import com.ExTwitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.product_mixer.core.util.OffloadFuturePools
import com.ExTwitter.servo.repository.KeyValueRepository
import com.ExTwitter.servo.repository.KeyValueResult
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.util.Future
import com.ExTwitter.util.Try
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import scala.collection.JavaConverters._

object TwhinAuthorFollowFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class TwhinAuthorFollowFeatureHydrator @Inject() (
  @Named(TwhinAuthorFollowFeatureRepository)
  client: KeyValueRepository[Seq[Long], Long, ml.FloatTensor],
  override val statsReceiver: StatsReceiver)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with ObservedKeyValueResultHandler {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TwhinAuthorFollow")

  override val features: Set[Feature[_, _]] = Set(TwhinAuthorFollowFeature)

  override val statScope: String = identifier.toString

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val possiblyAuthorIds = extractKeys(candidates)
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

  private def extractKeys(
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Seq[Option[Long]] = {
    candidates.map { candidate =>
      CandidatesUtil.getOriginalAuthorId(candidate.features)
    }
  }
}
