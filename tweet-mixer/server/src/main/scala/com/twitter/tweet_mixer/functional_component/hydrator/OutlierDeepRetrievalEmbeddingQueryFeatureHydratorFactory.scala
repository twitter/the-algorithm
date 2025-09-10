package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.finagle.stats.Counter
import com.twitter.finagle.stats.Stat
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.strato.columns.hydra.thriftscala.EmbeddingGenerationServiceParamsWithKey
import com.twitter.strato.columns.hydra.thriftscala.EmbeddingGenerationServiceParams
import com.twitter.strato.generated.client.hydra.CachedEgsTweetEmbeddingClientColumn
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.OutlierDeepRetrievalStratoTimeout
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.OutlierTweetEmbeddingModelNameParam
import com.twitter.util.Duration
import com.twitter.util.Future
import com.twitter.util.JavaTimer
import com.twitter.util.Return
import com.twitter.util.TimeoutException
import com.twitter.util.Timer

import javax.inject.Inject

object OutlierDeepRetrievalTweetEmbeddingFeature
    extends FeatureWithDefaultOnFailure[PipelineQuery, Option[Map[Long, Seq[Int]]]] {
  override def defaultValue: Option[Map[Long, Seq[Int]]] = None
}

class OutlierDeepRetrievalEmbeddingQueryFeatureHydratorFactory @Inject() (
  stats: StatsReceiver,
  cachedEgsTweetEmbeddingClientColumn: CachedEgsTweetEmbeddingClientColumn) {

  def build(
    signalFn: PipelineQuery => Seq[Long]
  ): OutlierDeepRetrievalEmbeddingQueryFeatureHydrator = {
    new OutlierDeepRetrievalEmbeddingQueryFeatureHydrator(
      cachedEgsTweetEmbeddingClientColumn,
      signalFn,
      stats
    )
  }
}

class OutlierDeepRetrievalEmbeddingQueryFeatureHydrator(
  cachedEgsTweetEmbeddingClientColumn: CachedEgsTweetEmbeddingClientColumn,
  signalFn: PipelineQuery => Seq[Long],
  stats: StatsReceiver)
    extends QueryFeatureHydrator[PipelineQuery] {

  private val statsScope: StatsReceiver = stats.scope("outlier_deep_retrieval_tweet_embedding")
  private val requestCounter: Counter = statsScope.counter("requests")
  private val successCounter: Counter = statsScope.counter("success")
  private val failureCounter: Counter = statsScope.counter("failures")
  private val timeoutCounter: Counter = statsScope.counter("timeouts")
  private val emptyResultCounter: Counter = statsScope.counter("empty_results")
  private val latencyStat: Stat = statsScope.stat("latency_ms")

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "OutlierDeepRetrievalEmbedding")

  override def features: Set[Feature[_, _]] = Set(OutlierDeepRetrievalTweetEmbeddingFeature)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    getEmbedding(
      signalFn(query),
      query.params(OutlierTweetEmbeddingModelNameParam),
      query.params(OutlierDeepRetrievalStratoTimeout))
      .map { embeddingMapOpt =>
        FeatureMap(OutlierDeepRetrievalTweetEmbeddingFeature, embeddingMapOpt)
      }
  }

  private def getEmbedding(
    tweetIds: Seq[Long],
    modelName: String,
    outlierDeepRetrievalStratoTimeout: Int
  ): Stitch[Option[Map[Long, Seq[Int]]]] = {
    val egsParams = EmbeddingGenerationServiceParams(
      deepRetrievalModels = Some(true),
      modelNames = Some(Seq(modelName))
    )
    val timer: Timer = new JavaTimer(true)

    requestCounter.incr()

    def transformer(batch: Seq[Long]): Future[Seq[(Long, Seq[Int])]] = {
      Stitch.run {
        Stitch
          .collectToTry {
            batch.map { tweetId =>
              val egsKey = EmbeddingGenerationServiceParamsWithKey(
                tweetId = tweetId,
                params = egsParams
              )
              cachedEgsTweetEmbeddingClientColumn.fetcher.fetch(egsKey).flatMap { result =>
                result.v match {
                  case Some(embeddingsByModel: Map[String, Seq[Int]]) =>
                    val embedding = embeddingsByModel.get(modelName).map(emb => (tweetId, emb))
                    Stitch.value(embedding)
                  case _ =>
                    Stitch.value(None)
                }
              }
            }
          }
          .map { results =>
            results.flatMap {
              case Return(Some(embedding)) => Some(embedding)
              case _ => None
            }
          }
      }
    }

    val batchSize = 32 // Adjust based on performance testing
    val startTime = System.currentTimeMillis()
    val futureResult = OffloadFuturePools.offloadBatchSeqToFutureSeq(
      inputSeq = tweetIds,
      transformer = transformer,
      batchSize = batchSize,
      offload = true // Offload to thread pool
    )

    // Process the result with timeout and stats
    Stitch
      .callFuture {
        futureResult
          .within(
            timer,
            Duration.fromMilliseconds(outlierDeepRetrievalStratoTimeout)
          )
          .onSuccess { result =>
            val latencyMs = (System.currentTimeMillis() - startTime).toFloat
            latencyStat.add(latencyMs) // Record latency
            if (result.nonEmpty) {
              successCounter.incr()
            } else {
              emptyResultCounter.incr()
            }
          }
          .onFailure { e =>
            val latencyMs = (System.currentTimeMillis() - startTime).toFloat
            latencyStat.add(latencyMs) // Record latency even on failure
            failureCounter.incr()
            if (e.isInstanceOf[TimeoutException]) {
              timeoutCounter.incr()
            }
          }
      }
      .map { results =>
        val idToEmbeddingMap = results.toMap
        if (idToEmbeddingMap.nonEmpty) Some(idToEmbeddingMap) else None
      }
  }
}
