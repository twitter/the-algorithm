package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.content_understanding.PostMultimodalEmbeddingMhClientColumn
import javax.inject.Inject
import javax.inject.Singleton

object MultimodalEmbeddingFeature
    extends FeatureWithDefaultOnFailure[PipelineQuery, Option[Map[Long, Option[Seq[Double]]]]] {
  override def defaultValue: Option[Map[Long, Option[Seq[Double]]]] = None
}

@Singleton
class MultimodalEmbeddingQueryFeatureHydratorFactory @Inject() (
  embeddingClientColumn: PostMultimodalEmbeddingMhClientColumn) {

  def build(
    signalFn: PipelineQuery => Seq[Long]
  ): MultimodalEmbeddingQueryFeatureHydrator = {
    new MultimodalEmbeddingQueryFeatureHydrator(
      embeddingClientColumn,
      signalFn
    )
  }
}

class MultimodalEmbeddingQueryFeatureHydrator(
  postMultimodalEmbeddingMhClientColumn: PostMultimodalEmbeddingMhClientColumn,
  signalFn: PipelineQuery => Seq[Long])
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("MultimodalEmbedding")

  override val features: Set[Feature[_, _]] = Set(MultimodalEmbeddingFeature)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val tweetIds = signalFn(query)
    getEmbedding(tweetIds)
      .map { embedding =>
        new FeatureMapBuilder()
          .add(MultimodalEmbeddingFeature, embedding)
          .build()
      }
  }

  private def getEmbedding(
    tweetIds: Seq[Long]
  ): Stitch[Option[Map[Long, Option[Seq[Double]]]]] = {
    // Parallel fetch the embeddings for each tweetId
    Stitch
      .traverse(tweetIds) { tweetId =>
        postMultimodalEmbeddingMhClientColumn.fetcher
          .fetch(tweetId).map { result =>
            result.v match {
              case Some(tweetEmbedding) if tweetEmbedding.embedding1.isDefined =>
                tweetEmbedding.embedding1.map(embedding => tweetId -> Some(embedding))
              case other =>
                Some(tweetId -> None)
            }
          }
      }.map { results =>
        val map = results.flatten.toMap
        if (map.nonEmpty) Some(map) else None
      }
  }
}
