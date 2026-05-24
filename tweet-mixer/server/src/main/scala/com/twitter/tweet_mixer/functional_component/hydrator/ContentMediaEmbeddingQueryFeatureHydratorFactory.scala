package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.searchai.storage.PostAnnotationEmbeddingMHClientColumn
import javax.inject.Inject
import javax.inject.Singleton

object ContentMediaEmbeddingFeature
    extends FeatureWithDefaultOnFailure[PipelineQuery, Option[Map[Long, Seq[Double]]]] {
  override def defaultValue: Option[Map[Long, Seq[Double]]] = None
}

@Singleton
class ContentMediaEmbeddingQueryFeatureHydratorFactory @Inject() (
  embeddingClientColumn: PostAnnotationEmbeddingMHClientColumn) {

  def build(
    signalFn: PipelineQuery => Seq[Long]
  ): ContentMediaEmbeddingQueryFeatureHydrator = {
    new ContentMediaEmbeddingQueryFeatureHydrator(
      embeddingClientColumn,
      signalFn
    )
  }
}

class ContentMediaEmbeddingQueryFeatureHydrator(
  postAnnotationEmbeddingMHClientColumn: PostAnnotationEmbeddingMHClientColumn,
  signalFn: PipelineQuery => Seq[Long])
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("ContentMediaEmbedding")

  override val features: Set[Feature[_, _]] = Set(ContentMediaEmbeddingFeature)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val tweetIds = signalFn(query)
    getEmbedding(tweetIds)
      .map { embedding =>
        new FeatureMapBuilder()
          .add(ContentMediaEmbeddingFeature, embedding)
          .build()
      }
  }

  private def getEmbedding(
    tweetIds: Seq[Long]
  ): Stitch[Option[Map[Long, Seq[Double]]]] = {
    Stitch
      .traverse(tweetIds) { tweetId =>
        postAnnotationEmbeddingMHClientColumn.fetcher
          .fetch((tweetId, "")).map { result =>
            result.v match {
              case Some(tweetEmbedding) => Some(tweetId -> tweetEmbedding.embedding)
              case _ => None
            }
          }
      }.map { results =>
        val map = results.flatten.toMap
        if (map.nonEmpty) Some(map) else None
      }
  }
}
