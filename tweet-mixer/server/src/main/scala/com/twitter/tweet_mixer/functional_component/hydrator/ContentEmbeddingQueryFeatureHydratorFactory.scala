package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.searchai.storage.SearchTweetEmbeddingXaiApiV2MHClientColumn
import javax.inject.Inject
import javax.inject.Singleton

object ContentExplorationEmbeddingFeature
    extends FeatureWithDefaultOnFailure[PipelineQuery, Option[Map[Long, Seq[Double]]]] {
  override def defaultValue: Option[Map[Long, Seq[Double]]] = None
}

@Singleton
class ContentExplorationEmbeddingQueryFeatureHydratorFactory @Inject() (
  embeddingClientColumn: SearchTweetEmbeddingXaiApiV2MHClientColumn) {

  def build(
    signalFn: PipelineQuery => Seq[Long]
  ): ContentExplorationEmbeddingQueryFeatureHydrator = {
    new ContentExplorationEmbeddingQueryFeatureHydrator(
      embeddingClientColumn,
      signalFn
    )
  }
}

class ContentExplorationEmbeddingQueryFeatureHydrator(
  searchTweetEmbeddingXaiApiV2MHClientColumn: SearchTweetEmbeddingXaiApiV2MHClientColumn,
  signalFn: PipelineQuery => Seq[Long])
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("ContentExplorationEmbedding")

  override val features: Set[Feature[_, _]] = Set(ContentExplorationEmbeddingFeature)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val tweetIds = signalFn(query)
    getEmbedding(tweetIds)
      .map { embedding =>
        new FeatureMapBuilder()
          .add(ContentExplorationEmbeddingFeature, embedding)
          .build()
      }
  }

  private def getEmbedding(
    tweetIds: Seq[Long]
  ): Stitch[Option[Map[Long, Seq[Double]]]] = {
    // Parallel fetch the embeddings for each tweetId
    Stitch
      .traverse(tweetIds) { tweetId =>
        searchTweetEmbeddingXaiApiV2MHClientColumn.fetcher
          .fetch((tweetId, "")).map { result =>
            result.v match {
              case Some(tweetEmbedding) if tweetEmbedding.embedding1.isDefined =>
                tweetEmbedding.embedding1.map(embedding => tweetId -> embedding)
              case other =>
                None
            }
          }
      }.map { results =>
        val map = results.flatten.toMap
        if (map.nonEmpty) Some(map) else None
      }
  }
}
