package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.util.Duration
import com.twitter.util.Return
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.model.marshalling.request.ClientContext
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.USSDeepRetrievalI2iEmbModelName
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.USSDeepRetrievalSimilarityTweetTweetANNScoreThreshold
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.USSDeepRetrievalStratoTimeout
import com.twitter.strato.generated.client.hydra.CachedEgsTweetEmbeddingClientColumn
import com.twitter.strato.columns.hydra.thriftscala.EmbeddingGenerationServiceParamsWithKey
import com.twitter.strato.columns.hydra.thriftscala.EmbeddingGenerationServiceParams
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finagle.stats.Counter
import com.twitter.finagle.stats.Stat
import javax.inject.Inject
import javax.inject.Singleton
import com.twitter.tweet_mixer.feature.SignalInfo
import breeze.linalg.DenseVector
import breeze.linalg.norm
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableUSSDeepRetrievalTweetEmbeddingFeatureHydrator
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.tweet_mixer.feature.USSFeatures._
import com.twitter.util.Future
import com.twitter.util.JavaTimer
import com.twitter.util.TimeoutException
import com.twitter.util.Timer

object USSDeepRetrievalTweetEmbeddingFeature
    extends FeatureWithDefaultOnFailure[PipelineQuery, Option[Map[Long, Seq[Int]]]] {
  override def defaultValue: Option[Map[Long, Seq[Int]]] = None
}

@Singleton
class USSDeepRetrievalTweetEmbeddingFeatureHydrator @Inject() (
  cachedEgsTweetEmbeddingClientColumn: CachedEgsTweetEmbeddingClientColumn,
  stats: StatsReceiver)
    extends QueryFeatureHydrator[PipelineQuery]
    with Conditionally[PipelineQuery] {

  private val statsScope: StatsReceiver = stats.scope("uss_deep_retrieval_tweet_embedding")
  private val overallFilterRateStat: Stat = statsScope.stat("overall_filter_rate")
  private val similarityGreaterThanEqualThresholdCounter: Counter =
    statsScope.counter("similarity_greater_than_equal_thresholdCounter")
  private val similarityLessThanThresholdCounter: Counter =
    statsScope.counter("similarity_less_than_thresholdCounter")
  private val requestCounter: Counter = statsScope.counter("requests")
  private val successCounter: Counter = statsScope.counter("success")
  private val failureCounter: Counter = statsScope.counter("failures")
  private val timeoutCounter: Counter = statsScope.counter("timeouts")
  private val emptyResultCounter: Counter = statsScope.counter("empty_results")
  private val latencyStat: Stat = statsScope.stat("latency_ms")

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("USSDeepRetrievalTweetEmbedding")

  override def onlyIf(query: PipelineQuery): Boolean =
    query.params(EnableUSSDeepRetrievalTweetEmbeddingFeatureHydrator)

  override val features: Set[Feature[_, _]] = TweetFeatures.asInstanceOf[Set[Feature[_, _]]]

  private lazy val emptyFeatureMap: FeatureMap = {
    val emptyFeatureMapBuilder = FeatureMapBuilder()
    TweetFeatures.foreach { feature =>
      emptyFeatureMapBuilder.add(feature, Map.empty[Long, Seq[SignalInfo]])
    }
    emptyFeatureMapBuilder.build()
  }

  def computeCosineSimilarity(embedding1: Seq[Int], embedding2: Seq[Int]): Double = {
    val v1 =
      if (embedding1.nonEmpty)
        DenseVector(embedding1.map(_.toDouble).toArray)
      else DenseVector.zeros[Double](256)
    val v2 =
      if (embedding2.nonEmpty)
        DenseVector(embedding2.map(_.toDouble).toArray)
      else DenseVector.zeros[Double](256)

    val dotProduct = v1 dot v2
    val denom = norm(v1) * norm(v2)
    if (denom == 0.0) 0.0 else dotProduct / denom
  }

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    // Extract tweet IDs from positive tweet features
    val tweetIdsFromPositiveSignals = TweetFeatures.flatMap { feature =>
      feature.getValue(query).keys
    }.toSeq

    val tweetIdsFromNegativeSignals = NegativeFeaturesTweetBased.flatMap { feature =>
      feature.getValue(query).keys
    }.toSeq

    val combinedTweetIds = tweetIdsFromPositiveSignals ++ tweetIdsFromNegativeSignals

    if (combinedTweetIds.isEmpty) {
      Stitch.value(emptyFeatureMap)
    } else {
      getEmbedding(
        combinedTweetIds,
        query.clientContext,
        query.params(USSDeepRetrievalI2iEmbModelName),
        query.params(USSDeepRetrievalStratoTimeout)
      ).map { embedding =>
        val similarityThreshold =
          query.params(USSDeepRetrievalSimilarityTweetTweetANNScoreThreshold)

        // Pair negative embeddings with their tweet IDs
        val negativeEmbeddingsWithIds = embedding match {
          case Some(map) =>
            map.collect {
              case (tweetId, emb) if tweetIdsFromNegativeSignals.contains(tweetId) =>
                (tweetId, emb)
            }.toSeq
          case None => Seq.empty
        }

        var totalSignalsBeforeCount = 0
        var totalSignalsAfterCount = 0

        val filteredTweetFeatures = TweetFeatures.map { feature =>
          val signals = feature.getValue(query)
          val signalsBeforeCount = signals.size
          totalSignalsBeforeCount += signalsBeforeCount

          val filteredSignals = signals.filter {
            case (tweetId, _) =>
              embedding.getOrElse(Map.empty).get(tweetId) match {
                case Some(emb) =>
                  val isDissimilar = if (negativeEmbeddingsWithIds.isEmpty) {
                    true
                  } else {
                    negativeEmbeddingsWithIds.exists {
                      case (_, negEmb) =>
                        val similarity = computeCosineSimilarity(emb, negEmb)
                        if (similarity > similarityThreshold)
                          similarityGreaterThanEqualThresholdCounter.incr()
                        else
                          similarityLessThanThresholdCounter.incr()
                        similarity < similarityThreshold
                    }
                  }
                  isDissimilar
                case None =>
                  true
              }
          }
          val signalsAfterCount = filteredSignals.size
          totalSignalsAfterCount += signalsAfterCount

          feature -> filteredSignals
        }.toMap

        // Record overall filter rate
        if (totalSignalsBeforeCount > 0) {
          val overallFilterRate =
            ((totalSignalsBeforeCount - totalSignalsAfterCount).toDouble / totalSignalsBeforeCount) * 100.0
          overallFilterRateStat.add(overallFilterRate.toFloat)
        } else {
          overallFilterRateStat.add(-1.0f)
        }

        val featureMapBuilder = FeatureMapBuilder()
        filteredTweetFeatures.foreach {
          case (feature, signals) =>
            featureMapBuilder.add(feature, signals)
        }
        featureMapBuilder.build()
      }
    }
  }

  private def getEmbedding(
    tweetIds: Seq[Long],
    clientContext: ClientContext,
    modelName: String,
    ussDeepRetrievalStratoTimeout: Int
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
            Duration.fromMilliseconds(ussDeepRetrievalStratoTimeout),
            new TimeoutException("Embedding fetch timed out after 100ms")
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
