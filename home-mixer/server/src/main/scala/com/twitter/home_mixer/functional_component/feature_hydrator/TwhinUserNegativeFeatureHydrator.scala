package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.twhin_embeddings.TwhinUserNegativeEmbeddingsAdapter
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TwhinUserNegativeEmbeddingsStore
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.{thriftscala => ml}
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.simclusters_v2.thriftscala.TwhinTweetEmbedding
import com.twitter.stitch.Stitch
import com.twitter.storehaus.ReadableStore
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import scala.collection.JavaConverters._

object TwhinUserNegativeFeature
    extends DataRecordInAFeature[PipelineQuery]
    with FeatureWithDefaultOnFailure[PipelineQuery, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class TwhinUserNegativeQueryFeatureHydrator @Inject() (
  @Named(TwhinUserNegativeEmbeddingsStore) store: ReadableStore[Long, TwhinTweetEmbedding])
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TwhinUserNegative")

  override val features: Set[Feature[_, _]] = Set(TwhinUserNegativeFeature)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    Stitch.callFuture(store.get(query.getRequiredUserId)).map { resultOpt =>
      val floatTensor = resultOpt.map(result => ml.FloatTensor(result.embedding))
      val dataRecord = TwhinUserNegativeEmbeddingsAdapter
        .adaptToDataRecords(floatTensor).asScala.head
      FeatureMapBuilder().add(TwhinUserNegativeFeature, dataRecord).build()
    }
  }
}
