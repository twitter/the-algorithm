package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.twhin_embeddings.TwhinAuthorFollowEmbeddingsAdapter
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TwhinAuthorFollowFeatureRepository
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.home_mixer.util.ObservedKeyValueResultHandler
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.{thriftscala => ml}
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.servo.repository.KeyValueRepository
import com.twitter.servo.repository.KeyValueResult
import com.twitter.stitch.Stitch
import com.twitter.util.Future
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

  private val emptyDataRecord = new DataRecord()

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val possiblyAuthorIds = extractKeys(candidates)
    val authorIds = possiblyAuthorIds.flatten.distinct

    val response: Future[KeyValueResult[Long, DataRecord]] =
      if (authorIds.isEmpty) Future.value(KeyValueResult.empty)
      else client(authorIds).map(_.mapFound(postTransformer))

    response.map { result =>
      possiblyAuthorIds.map { possiblyAuthorId =>
        val value =
          observedGet(key = possiblyAuthorId, keyValueResult = result)
            .map(_.getOrElse(emptyDataRecord))

        FeatureMapBuilder().add(TwhinAuthorFollowFeature, value).build()
      }
    }
  }

  private def postTransformer(embedding: ml.FloatTensor): DataRecord = {
    TwhinAuthorFollowEmbeddingsAdapter.adaptToDataRecords(Some(embedding)).asScala.head
  }

  private def extractKeys(
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Seq[Option[Long]] = {
    candidates.map { candidate =>
      CandidatesUtil.getOriginalAuthorId(candidate.features)
    }
  }
}
