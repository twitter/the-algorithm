package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.twhin_embeddings.TwhinRebuildUserPositiveEmbeddingsAdapter
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TwhinRebuildUserPositiveEmbeddingsStore
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
import com.twitter.simclusters_v2.thriftscala.TwhinEmbeddingDataset
import com.twitter.stitch.Stitch
import com.twitter.storehaus.ReadableStore
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import scala.collection.JavaConverters._

object TwhinRebuildUserPositiveFeature
    extends DataRecordInAFeature[PipelineQuery]
    with FeatureWithDefaultOnFailure[PipelineQuery, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class TwhinRebuildUserPositiveQueryFeatureHydrator @Inject() (
  @Named(TwhinRebuildUserPositiveEmbeddingsStore) store: ReadableStore[
    (Long, Long),
    TwhinTweetEmbedding
  ],
  statsReceiver: StatsReceiver)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TwhinRebuildUserPositive")

  override val features: Set[Feature[_, _]] = Set(TwhinRebuildUserPositiveFeature)

  private val versionId = TwhinEmbeddingDataset.RefreshedTwhinTweet.value

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val keyFoundCounter = scopedStatsReceiver.counter("key/found")
  private val keyNotFoundCounter = scopedStatsReceiver.counter("key/notFound")
  private val keyTotalCounter = scopedStatsReceiver.counter("key/total")

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    Stitch
      .callFuture(store.get((query.getRequiredUserId, versionId))).map { resultOpt =>
        keyTotalCounter.incr()
        resultOpt match {
          case Some(_) =>
            keyFoundCounter.incr()
          case None => keyNotFoundCounter.incr()
        }
        val floatTensor = resultOpt.map(result => ml.FloatTensor(result.embedding))
        val dataRecord = TwhinRebuildUserPositiveEmbeddingsAdapter
          .adaptToDataRecords(floatTensor).asScala.head
        FeatureMapBuilder().add(TwhinRebuildUserPositiveFeature, dataRecord).build()
      }
  }
}
