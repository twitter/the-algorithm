package com.twitter.representation_manager.store

import com.twitter.servo.decider.DeciderKeyEnum

object DeciderConstants {
  // Deciders inherited from CR and RSX and only used in LegacyRMS
  // Their value are manipulated by CR and RSX's yml file and their decider dashboard
  // We will remove them after migration completed
  val enableLogFavBasedApeEntity20M145KUpdatedEmbeddingCachedStore =
    "enableLogFavBasedApeEntity20M145KUpdatedEmbeddingCachedStore"

  val enableLogFavBasedApeEntity20M145K2020EmbeddingCachedStore =
    "enableLogFavBasedApeEntity20M145K2020EmbeddingCachedStore"

  val enablelogFavBased20M145K2020TweetEmbeddingStoreTimeouts =
    "enable_log_fav_based_tweet_embedding_20m145k2020_timeouts"
  val logFavBased20M145K2020TweetEmbeddingStoreTimeoutValueMillis =
    "log_fav_based_tweet_embedding_20m145k2020_timeout_value_millis"

  val enablelogFavBased20M145KUpdatedTweetEmbeddingStoreTimeouts =
    "enable_log_fav_based_tweet_embedding_20m145kUpdated_timeouts"
  val logFavBased20M145KUpdatedTweetEmbeddingStoreTimeoutValueMillis =
    "log_fav_based_tweet_embedding_20m145kUpdated_timeout_value_millis"

  val enableSimClustersEmbeddingStoreTimeouts = "enable_sim_clusters_embedding_store_timeouts"
  val simClustersEmbeddingStoreTimeoutValueMillis =
    "sim_clusters_embedding_store_timeout_value_millis"
}

// Necessary for using servo Gates
object DeciderKey extends DeciderKeyEnum {
  val enableLogFavBasedApeEntity20M145KUpdatedEmbeddingCachedStore: Value = Value(
    DeciderConstants.enableLogFavBasedApeEntity20M145KUpdatedEmbeddingCachedStore
  )

  val enableLogFavBasedApeEntity20M145K2020EmbeddingCachedStore: Value = Value(
    DeciderConstants.enableLogFavBasedApeEntity20M145K2020EmbeddingCachedStore
  )
}
