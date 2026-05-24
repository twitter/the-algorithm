package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.model.marshalling.request.ClientContext
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.feature.USSFeatures
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalI2iEmbModelName
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalTweetTweetEmbeddingRandomSize
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalTweetTweetEmbeddingTimeDecay
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalTweetTweetEmbeddingSeedMaxAgeInDays
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalTweetTweetEmbeddingEngagementBoostWeight
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalTweetTweetEmbeddingTimeOfDayBoostWeight
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalTweetTweetEmbeddingDayOfWeekBoostWeight
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalTweetTweetEmbeddingMinPriority
import com.twitter.strato.generated.client.hydra.CachedEgsTweetEmbeddingClientColumn
import com.twitter.strato.columns.hydra.thriftscala.EmbeddingGenerationServiceParamsWithKey
import com.twitter.strato.columns.hydra.thriftscala.EmbeddingGenerationServiceParams
import com.twitter.util.Time
import com.twitter.util.Duration
import javax.inject.Inject
import javax.inject.Singleton
import scala.util.Random
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

object DeepRetrievalTweetEmbeddingFeature
    extends FeatureWithDefaultOnFailure[PipelineQuery, Option[Map[Long, Seq[Int]]]] {
  override def defaultValue: Option[Map[Long, Seq[Int]]] = None
}

@Singleton
class DeepRetrievalTweetEmbeddingQueryFeatureHydratorFactory @Inject() (
  cachedEgsTweetEmbeddingClientColumn: CachedEgsTweetEmbeddingClientColumn) {

  def build(
    signalFn: PipelineQuery => Seq[Long]
  ): DeepRetrievalTweetEmbeddingQueryFeatureHydrator = {
    new DeepRetrievalTweetEmbeddingQueryFeatureHydrator(
      cachedEgsTweetEmbeddingClientColumn,
      signalFn
    )
  }
}

class DeepRetrievalTweetEmbeddingQueryFeatureHydrator(
  cachedEgsTweetEmbeddingClientColumn: CachedEgsTweetEmbeddingClientColumn,
  signalFn: PipelineQuery => Seq[Long])
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("DeepRetrievalTweetEmbedding")

  override val features: Set[Feature[_, _]] = Set(DeepRetrievalTweetEmbeddingFeature)

  def sampleTweetIds(
    ids: Seq[Long],
    query: PipelineQuery
  ): Seq[Long] = {
    val n = query.params(DeepRetrievalTweetTweetEmbeddingRandomSize)
    val timeDecay = query.params(DeepRetrievalTweetTweetEmbeddingTimeDecay)
    val engagementBoostWeight = query.params(DeepRetrievalTweetTweetEmbeddingEngagementBoostWeight)
    val timeOfDayBoostWeight = query.params(DeepRetrievalTweetTweetEmbeddingTimeOfDayBoostWeight)
    val dayOfWeekBoostWeight = query.params(DeepRetrievalTweetTweetEmbeddingDayOfWeekBoostWeight)
    val maxAge = query.params(DeepRetrievalTweetTweetEmbeddingSeedMaxAgeInDays)
    val minPriority = query.params(DeepRetrievalTweetTweetEmbeddingMinPriority)

    // If n is non-positive, return all ids.
    if (n <= 0) return ids

    // If timeDecay is not set or is out of range, use random sampling.
    if (timeDecay <= 0.0 || timeDecay >= 1.0) {
      return Random.shuffle(ids).take(n)
    }

    // Filter out ids based on timestamp.
    val idTosignals = USSFeatures.getSignalsWithInfo(query, USSFeatures.TweetFeatures).map {
      case (id, signalInfos) =>
        val priority = USSFeatures.getPriority(signalInfos)
        val mostRecentTimeStamp =
          signalInfos
            .flatMap(_.sourceEventTime)
            .foldLeft(Time.Zero)(_ max _)
        (id, (priority, mostRecentTimeStamp))
    }
    val filteredIds = ids.filter { id =>
      idTosignals.get(id) match {
        case Some((priority, timestamp)) =>
          val passesAgeFilter = maxAge <= Duration.fromDays(0) || (Time.now - timestamp <= maxAge)
          val passesPriorityFilter = minPriority < 0 || priority >= minPriority
          passesAgeFilter && passesPriorityFilter
        case None => false
      }
    }

    // Select ids based on priority and time.
    val weightedIds = filteredIds.map { id =>
      idTosignals.get(id) match {
        case Some((priority, timestamp)) =>
          val matchTimeOfDay = isMatchTimeOfDay(timestamp)
          val matchDayOfWeek = isMatchDayOfWeek(timestamp)
          val weight = math.pow(timeDecay, (Time.now - timestamp).inDays) *
            math.min(priority * engagementBoostWeight, 1.0) *
            (if (matchTimeOfDay) timeOfDayBoostWeight else 1.0) *
            (if (matchDayOfWeek) dayOfWeekBoostWeight else 1.0)
          (id, weight)
        case None =>
          (id, 0.0)
      }
    }
    weightedIds.sortBy(_._2).take(n).map(_._1)
  }

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val tweetIds = signalFn(query)
    val sampledTweetIds = sampleTweetIds(tweetIds, query)
    getEmbedding(sampledTweetIds, query.clientContext, query.params(DeepRetrievalI2iEmbModelName))
      .map { embedding =>
        new FeatureMapBuilder()
          .add(DeepRetrievalTweetEmbeddingFeature, embedding)
          .build()
      }
  }

  private def getEmbedding(
    tweetIds: Seq[Long],
    clientContext: ClientContext,
    modelName: String
  ): Stitch[Option[Map[Long, Seq[Int]]]] = {
    val egsParams = EmbeddingGenerationServiceParams(
      deepRetrievalModels = Some(true),
      modelNames = Some(Seq(modelName))
    )

    // Parallel fetch the embeddings for each tweetId
    Stitch
      .traverse(tweetIds) { tweetId =>
        val egsKey = EmbeddingGenerationServiceParamsWithKey(
          tweetId = tweetId,
          params = egsParams,
        )
        cachedEgsTweetEmbeddingClientColumn.fetcher.fetch(egsKey).flatMap { result =>
          result.v match {
            case Some(embeddingsByModel: Map[String, Seq[Int]]) =>
              Stitch.value(embeddingsByModel.get(modelName).map(embedding => tweetId -> embedding))
            case other =>
              Stitch.value(None)
          }
        }
      }.map { results =>
        val map = results.flatten.toMap
        if (map.nonEmpty) Some(map) else None
      }
  }

  def isMatchTimeOfDay(time: Time, windowHours: Int = 2): Boolean = {
    val millisInDay = 24 * 60 * 60 * 1000L
    val now = Time.now
    val nowMillisOfDay = now.inMilliseconds % millisInDay
    val timeMillisOfDay = time.inMilliseconds % millisInDay
    val diff = math.abs(nowMillisOfDay - timeMillisOfDay)
    val minDiff = math.min(diff, millisInDay - diff) // handle wrap-around at midnight
    minDiff <= windowHours * 60 * 60 * 1000L
  }

  def isMatchDayOfWeek(time: Time): Boolean = {
    val zdt =
      ZonedDateTime.ofInstant(Instant.ofEpochMilli(time.inMilliseconds), ZoneId.systemDefault())
    val nowZdt =
      ZonedDateTime.ofInstant(Instant.ofEpochMilli(Time.now.inMilliseconds), ZoneId.systemDefault())
    zdt.getDayOfWeek == nowZdt.getDayOfWeek
  }
}
