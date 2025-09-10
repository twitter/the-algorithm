package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.strato.generated.client.hydra.CachedEgsTweetEmbeddingClientColumn
import com.twitter.strato.columns.hydra.thriftscala.EmbeddingGenerationServiceParamsWithKey
import com.twitter.strato.columns.hydra.thriftscala.EmbeddingGenerationServiceParams
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.FSParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.MediaDeepRetrievalModelName

import javax.inject.Inject
import javax.inject.Singleton

object MediaDeepRetrievalSignalTweetEmbeddingFeature
    extends FeatureWithDefaultOnFailure[PipelineQuery, Map[Long, Seq[Int]]] {
  override def defaultValue: Map[Long, Seq[Int]] = Map.empty
}

@Singleton
class MediaDeepRetrievalTweetEmbeddingQueryFeatureHydratorFactory @Inject() (
  cachedEgsTweetEmbeddingClientColumn: CachedEgsTweetEmbeddingClientColumn) {
  def build(
    signalFn: PipelineQuery => Seq[Long]
  ): MediaDeepRetrievalTweetEmbeddingQueryFeatureHydrator = {
    new MediaDeepRetrievalTweetEmbeddingQueryFeatureHydrator(
      cachedEgsTweetEmbeddingClientColumn,
      signalFn)
  }
}

class MediaDeepRetrievalTweetEmbeddingQueryFeatureHydrator(
  cachedEgsTweetEmbeddingClientColumn: CachedEgsTweetEmbeddingClientColumn,
  signalFn: PipelineQuery => Seq[Long],
) extends QueryFeatureHydrator[PipelineQuery] {

  val modelName: FSParam[String] = MediaDeepRetrievalModelName

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("MediaDeepRetrievalTweetEmbedding")

  override val features: Set[Feature[_, _]] = Set(MediaDeepRetrievalSignalTweetEmbeddingFeature)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val egsParams = EmbeddingGenerationServiceParams(
      deepRetrievalModels = Some(true),
      modelNames = Some(Seq(query.params(modelName)))
    )
    Stitch
      .traverse(signalFn(query)) { tweetId =>
        val egsKey = EmbeddingGenerationServiceParamsWithKey(
          tweetId = tweetId,
          params = egsParams,
        )
        cachedEgsTweetEmbeddingClientColumn.fetcher.fetch(egsKey).flatMap { result =>
          result.v match {
            case Some(embeddingsByModel: Map[String, Seq[Int]]) =>
              Stitch.value(embeddingsByModel
                .get(query.params(modelName)).map(embedding => tweetId -> embedding))
            case other =>
              Stitch.value(None)
          }
        }
      }.map { results =>
        val map = results.flatten.toMap
        new FeatureMapBuilder()
          .add(MediaDeepRetrievalSignalTweetEmbeddingFeature, map)
          .build()
      }
  }
}
