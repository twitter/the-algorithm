package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.twhin_embeddings.TwhinVideoEmbeddingsAdapter
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TwhinVideoEmbeddingsStore
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.{thriftscala => ml}
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.CandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.simclusters_v2.thriftscala.TwhinTweetEmbedding
import com.twitter.stitch.Stitch
import com.twitter.storehaus.ReadableStore
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import scala.collection.JavaConverters._

object TwhinVideoFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class TwhinVideoFeatureHydrator @Inject() (
  @Named(TwhinVideoEmbeddingsStore) store: ReadableStore[Long, TwhinTweetEmbedding])
    extends CandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("TwhinVideo")

  override val features: Set[Feature[_, _]] = Set(TwhinVideoFeature)

  override def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    existingFeatures: FeatureMap
  ): Stitch[FeatureMap] = OffloadFuturePools.offloadFuture {
    val originalTweetId = CandidatesUtil.getOriginalTweetId(candidate, existingFeatures)

    store.get(originalTweetId).map { resultOpt =>
      val floatTensor = resultOpt.map(result => ml.FloatTensor(result.embedding))
      val dataRecord = TwhinVideoEmbeddingsAdapter.adaptToDataRecords(floatTensor).asScala.head
      FeatureMapBuilder().add(TwhinVideoFeature, dataRecord).build()
    }
  }
}
