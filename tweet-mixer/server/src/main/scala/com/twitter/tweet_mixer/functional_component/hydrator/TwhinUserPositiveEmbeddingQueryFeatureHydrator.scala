package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.simclusters_v2.thriftscala.TwhinTweetEmbedding
import com.twitter.stitch.Stitch
import com.twitter.storehaus.ReadableStore
import com.twitter.tweet_mixer.model.ModuleNames.TwhinRebuildUserPositiveEmbeddingsStore
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

object TwhinUserPositiveEmbeddingFeature
    extends FeatureWithDefaultOnFailure[PipelineQuery, Option[Seq[Double]]] {
  override def defaultValue: Option[Seq[Double]] = None
}

@Singleton
class TwhinUserPositiveEmbeddingQueryFeatureHydrator @Inject() (
  @Named(TwhinRebuildUserPositiveEmbeddingsStore) store: ReadableStore[
    (Long, Long),
    TwhinTweetEmbedding
  ]) extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TwhinUserPositiveEmbedding")

  override val features: Set[Feature[_, _]] = Set(TwhinUserPositiveEmbeddingFeature)

  private def l2Normalize(v: Seq[Double]): Seq[Double] = {
    val sumSquares = v.foldLeft(0.0)((acc, x) => acc + x * x)
    if (sumSquares <= 0.0) v
    else {
      val n = math.sqrt(sumSquares)
      v.map(_ / n)
    }
  }

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    Stitch.callFuture(store.get((query.getRequiredUserId, 1L))).map { resultOpt =>
      val twhinUserPositiveEmbedding: Option[Seq[Double]] = resultOpt.map(_.embedding)
      FeatureMapBuilder()
        .add(TwhinUserPositiveEmbeddingFeature, twhinUserPositiveEmbedding.map(l2Normalize)).build()
    }
  }
}
