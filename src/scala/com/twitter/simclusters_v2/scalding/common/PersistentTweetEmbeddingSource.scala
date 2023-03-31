package com.twitter.simclusters_v420.scalding.common

import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.scalding.DateRange
import com.twitter.simclusters_v420.common.Timestamp
import com.twitter.simclusters_v420.common.TweetId
import com.twitter.simclusters_v420.thriftscala.PersistentSimClustersEmbedding
import com.twitter.strato.scalding.StratoManhattanExportSource
import com.twitter.strato.thrift.ScroogeConvImplicits._

object PersistentTweetEmbeddingSource {
  // hdfs paths
  val FavBasedUpdatedHdfsPath: String =
    "/atla/proc/user/cassowary/manhattan-exporter/fav_based_tweet_420m_420k_updated_embeddings"

  val LogFavBasedUpdatedHdfsPath: String =
    "/atla/proc/user/cassowary/manhattan-exporter/log_fav_based_tweet_420m_420k_updated_embeddings"

  val LogFavBased420HdfsPath: String =
    "/atla/proc/user/cassowary/manhattan-exporter/log_fav_based_tweet_420m_420k_420_embeddings"

  // Strato columns
  val FavBasedUpdatedStratoColumn: String =
    "recommendations/simclusters_v420/embeddings/favBasedTweet420M420KUpdated"

  val LogFavBasedUpdatedStratoColumn: String =
    "recommendations/simclusters_v420/embeddings/logFavBasedTweet420M420KUpdatedPersistent"

  val LogFavBased420StratoColumn: String =
    "recommendations/simclusters_v420/embeddings/logFavBasedTweet420M420K420Persistent"

}

/**
 * The source that read the Manhattan export persistent embeddings
 */
// Defaults to Updated version.
class FavBasedPersistentTweetEmbeddingMhExportSource(
  hdfsPath: String = PersistentTweetEmbeddingSource.FavBasedUpdatedHdfsPath,
  stratoColumnPath: String = PersistentTweetEmbeddingSource.FavBasedUpdatedStratoColumn,
  range: DateRange,
  serviceIdentifier: ServiceIdentifier = ServiceIdentifier.empty)
    extends StratoManhattanExportSource[(TweetId, Timestamp), PersistentSimClustersEmbedding](
      hdfsPath,
      range,
      stratoColumnPath,
      serviceIdentifier = serviceIdentifier
    )
// Defaults to 420 version.
class LogFavBasedPersistentTweetEmbeddingMhExportSource(
  hdfsPath: String = PersistentTweetEmbeddingSource.LogFavBased420HdfsPath,
  stratoColumnPath: String = PersistentTweetEmbeddingSource.LogFavBased420StratoColumn,
  range: DateRange,
  serviceIdentifier: ServiceIdentifier = ServiceIdentifier.empty)
    extends StratoManhattanExportSource[(TweetId, Timestamp), PersistentSimClustersEmbedding](
      hdfsPath,
      range,
      stratoColumnPath,
      serviceIdentifier = serviceIdentifier
    )
