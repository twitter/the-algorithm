package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.MultiModalEmbeddingsFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.servo.cache.ExpiringLruInProcessCache
import com.twitter.servo.cache.InProcessCache
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.searchai.storage.PostMultimodalEmbeddingMhClientColumn
import com.twitter.util.Duration
import javax.inject.Inject
import javax.inject.Singleton
import scala.util.Random
import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.util.Try

object MultiModalEmbeddingsFeatureHydrator {
  private val BaseTTL = 15
  private val TTL: Duration = (BaseTTL + Random.nextInt(10)).minutes
  private val maximumCacheSize = 15000 // 15000 * 1536 * 8 bytes = <200Mb

  val cache: InProcessCache[Long, Option[Option[Seq[Double]]]] =
    new ExpiringLruInProcessCache(ttl = TTL, maximumSize = maximumCacheSize)

  private val embeddingFetcherModelVersion = "v1"
}

@Singleton
class MultiModalEmbeddingsFeatureHydrator @Inject() (
  postMultimodalEmbeddingMhClientColumn: PostMultimodalEmbeddingMhClientColumn,
  statsReceiver: StatsReceiver)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("MultiModalEmbeddings")

  override val features: Set[Feature[_, _]] = Set(MultiModalEmbeddingsFeature)

  private val embeddingFetcher = postMultimodalEmbeddingMhClientColumn.fetcher

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val keyFoundCounter = scopedStatsReceiver.counter("key/found")
  private val keyNotFoundCounter = scopedStatsReceiver.counter("key/notFound")
  private val fetchExceptionCounter = scopedStatsReceiver.counter("getFromCacheOrFetch/exception")

  private def getEmbedding(
    tweetId: Long
  ): Stitch[Option[Option[Seq[Double]]]] = {
    val embeddingStitchFromCache: Option[Stitch[Option[Option[Seq[Double]]]]] =
      MultiModalEmbeddingsFeatureHydrator.cache
        .get(tweetId)
        .map(Stitch.value)

    embeddingStitchFromCache match {
      case Some(stitch: Stitch[Option[Option[Seq[Double]]]]) => stitch
      case None =>
        val embeddingStitch = embeddingFetcher
          .fetch((tweetId, MultiModalEmbeddingsFeatureHydrator.embeddingFetcherModelVersion)).map {
            result =>
              result.v match {
                case Some(tweetEmbedding)
                    if tweetEmbedding != null && tweetEmbedding.embedding1 != null && tweetEmbedding.embedding1.isDefined =>
                  keyFoundCounter.incr()
                  val embedding = tweetEmbedding.embedding1
                  MultiModalEmbeddingsFeatureHydrator.cache.set(tweetId, Some(embedding))
                  Some(embedding)
                case _ =>
                  keyNotFoundCounter.incr()
                  MultiModalEmbeddingsFeatureHydrator.cache.set(tweetId, Some(None))
                  None
              }
          }.handle {
            case ex: Exception =>
              fetchExceptionCounter.incr()
              None
          }
        embeddingStitch
    }
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadStitch {
    val stitchesWithTry: Stitch[Seq[Try[Option[Option[Seq[Double]]]]]] = Stitch.collectToTry {
      candidates.map { candidate =>
        val tweetId: Long = candidate.candidate.id
        getEmbedding(tweetId)
      }
    }

    stitchesWithTry.map { tryVal =>
      tryVal.flatMap(_.toOption).map {
        case Some(embedding) => FeatureMap(MultiModalEmbeddingsFeature, embedding)
        case _ => FeatureMap(MultiModalEmbeddingsFeature, None)
      }
    }
  }

}
