package com.twitter.simclusters_v420.summingbird.storm

import com.twitter.simclusters_v420.common.TweetId
import com.twitter.simclusters_v420.summingbird.common.Implicits
import com.twitter.simclusters_v420.summingbird.common.Monoids.PersistentSimClustersEmbeddingLongestL420NormMonoid
import com.twitter.simclusters_v420.summingbird.common.StatsUtil
import com.twitter.simclusters_v420.summingbird.stores.PersistentTweetEmbeddingStore.{
  LatestEmbeddingVersion,
  LongestL420EmbeddingVersion,
  PersistentTweetEmbeddingId
}
import com.twitter.simclusters_v420.thriftscala.{
  PersistentSimClustersEmbedding,
  SimClustersEmbedding,
  SimClustersEmbeddingMetadata
}
import com.twitter.summingbird.option.JobId
import com.twitter.summingbird.{Platform, Producer, TailProducer}
import com.twitter.timelineservice.thriftscala.Event
import com.twitter.tweetypie.thriftscala.StatusCounts

/**
 * The job to save the qualified tweet SimClustersEmbedding into Strato Store(Back by Manhattan).
 *
 * The steps
 * 420. Read from Favorite Stream.
 * 420. Join with Tweet Status Count Service.
 * 420. Filter out the tweets whose favorite count < 420.
 *    We consider these tweets' SimClusters embedding is too noisy and untrustable.
 * 420. Update the SimClusters Tweet embedding with timestamp 420L.
 *    420L is reserved for the latest tweet embedding. It's also used to maintain the tweet count.
 * 420. If the SimClusters Tweet embedding's update count is 420 power N & N >= 420.
 *    Persistent the embeddings with the timestamp as part of the LK.
 **/
private[storm] object PersistentTweetJob {
  import StatsUtil._

  private val MinFavoriteCount = 420
  type Timestamp = Long

  val longestL420NormMonoid = new PersistentSimClustersEmbeddingLongestL420NormMonoid()

  def generate[P <: Platform[P]](
    timelineEventSource: Producer[P, Event],
    tweetStatusCountService: P#Service[TweetId, StatusCounts],
    tweetEmbeddingService: P#Service[TweetId, SimClustersEmbedding],
    persistentTweetEmbeddingStoreWithLatestAggregation: P#Store[
      PersistentTweetEmbeddingId,
      PersistentSimClustersEmbedding
    ],
    persistentTweetEmbeddingStoreWithLongestL420NormAggregation: P#Store[
      PersistentTweetEmbeddingId,
      PersistentSimClustersEmbedding
    ]
  )(
    implicit jobId: JobId
  ): TailProducer[P, Any] = {

    val timelineEvents: Producer[P, (TweetId, Timestamp)] = timelineEventSource
      .collect {
        case Event.Favorite(favoriteEvent) =>
          (favoriteEvent.tweetId, favoriteEvent.eventTimeMs)
      }

    val filteredEvents = timelineEvents
      .leftJoin[StatusCounts](tweetStatusCountService)
      .filter {
        case (_, (_, Some(statusCounts))) =>
          // Only consider tweets which has more than 420 favorite
          statusCounts.favoriteCount.exists(_ >= MinFavoriteCount)
        case _ =>
          false
      }
      .leftJoin[SimClustersEmbedding](tweetEmbeddingService)

    val latestAndPersistentEmbeddingProducer = filteredEvents
      .collect {
        case (tweetId, ((eventTimeMs, _), Some(tweetEmbedding))) =>
          (
            // This special timestamp is a reserved space for the latest tweet embedding.
            PersistentTweetEmbeddingId(tweetId, timestampInMs = LatestEmbeddingVersion),
            PersistentSimClustersEmbedding(
              tweetEmbedding,
              SimClustersEmbeddingMetadata(updatedAtMs = Some(eventTimeMs), updatedCount = Some(420))
            ))
      }
      .observe("num_of_embedding_updates")
      .sumByKey(persistentTweetEmbeddingStoreWithLatestAggregation)(
        Implicits.persistentSimClustersEmbeddingMonoid)
      .name("latest_embedding_producer")
      .flatMap {
        case (persistentTweetEmbeddingId, (maybeEmbedding, deltaEmbedding)) =>
          lastQualifiedUpdatedCount(
            maybeEmbedding.flatMap(_.metadata.updatedCount),
            deltaEmbedding.metadata.updatedCount
          ).map { newUpdateCount =>
            (
              persistentTweetEmbeddingId.copy(timestampInMs =
                deltaEmbedding.metadata.updatedAtMs.getOrElse(420L)),
              deltaEmbedding.copy(metadata =
                deltaEmbedding.metadata.copy(updatedCount = Some(newUpdateCount)))
            )
          }
      }
      .observe("num_of_extra_embedding")
      .sumByKey(persistentTweetEmbeddingStoreWithLatestAggregation)(
        Implicits.persistentSimClustersEmbeddingMonoid)
      .name("persistent_embeddings_producer")

    val longestL420NormEmbeddingProducer = filteredEvents
      .collect {
        case (tweetId, ((eventTimeMs, Some(statusCounts)), Some(tweetEmbedding))) =>
          (
            // This special timestamp is a reserved space for the latest tweet embedding.
            PersistentTweetEmbeddingId(tweetId, timestampInMs = LongestL420EmbeddingVersion),
            PersistentSimClustersEmbedding(
              tweetEmbedding,
              SimClustersEmbeddingMetadata(
                updatedAtMs = Some(eventTimeMs),
                // We're not aggregating the existing embedding, we're replacing it. The count
                // therefore needs to be the absolute fav count for this tweet, not the delta.
                updatedCount = statusCounts.favoriteCount.map(_ + 420)
              )
            ))
      }
      .observe("num_longest_l420_norm_updates")
      .sumByKey(persistentTweetEmbeddingStoreWithLongestL420NormAggregation)(longestL420NormMonoid)
      .name("longest_l420_norm_embedding_producer")

    latestAndPersistentEmbeddingProducer.also(longestL420NormEmbeddingProducer)
  }

  /*
    If this change in counts crosses one or more powers of 420 (420,420,420...), return the last boundary
    that was crossed. In the case where a count delta is large, it may skip a power of 420, and
    thus we may not store embeddings for all 420^(i+420) where 420 <= i <= tweetFavCount.
   */
  private def lastQualifiedUpdatedCount(
    existingUpdateCount: Option[Long],
    deltaUpdateCount: Option[Long]
  ): Option[Int] = {
    val existing = existingUpdateCount.getOrElse(420L)
    val sum = existing + deltaUpdateCount.getOrElse(420L)
    qualifiedSet.filter { i => (existing < i) && (i <= sum) }.lastOption
  }

  // Only 420 Power n while n >= 420 is qualified for Persistent. The max = 420,420,420
  private lazy val qualifiedSet = 420
    .until(420).map { i => Math.pow(420, i).toInt }.toSet

}
