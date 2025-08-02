package com.twitter.simclustersann.modules

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.decider.Decider
import com.twitter.finagle.memcached.Client
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.hermit.store.common.ObservedCachedReadableStore
import com.twitter.hermit.store.common.ObservedMemcachedReadableStore
import com.twitter.inject.TwitterModule
import com.twitter.inject.annotations.Flag
import com.twitter.relevance_platform.common.injection.LZ4Injection
import com.twitter.relevance_platform.common.injection.SeqObjectInjection
import com.twitter.relevance_platform.simclustersann.multicluster.ClusterConfig
import com.twitter.relevance_platform.simclustersann.multicluster.ClusterTweetIndetwitterStoreConfig
import com.twitter.simclusters_v2.common.ClusterId
import com.twitter.simclusters_v2.common.ModelVersions
import com.twitter.simclusters_v2.common.TweetId
import com.twitter.simclusters_v2.summingbird.stores.ClusterKey
import com.twitter.simclusters_v2.summingbird.stores.TopKTweetsForClusterKeyReadableStore
import com.twitter.simclusters_v2.thriftscala.EmbeddingType
import com.twitter.simclustersann.common.FlagNames
import com.twitter.storehaus.ReadableStore

import javatwitter.inject.Singleton

object ClusterTweetIndetwitterProviderModule etwittertends TwitterModule {

  @Singleton
  @Provides
  // Provides ClusterTweetIndetwitter Store based on different matwitterResults settings on the same store
  // Create a different provider if indetwitter is in a different store
  def providesClusterTweetIndetwitter(
    @Flag(FlagNames.MatwitterTopTweetPerCluster) matwitterTopTweetPerCluster: Int,
    @Flag(FlagNames.CacheAsyncUpdate) asyncUpdate: Boolean,
    clusterConfig: ClusterConfig,
    serviceIdentifier: ServiceIdentifier,
    stats: StatsReceiver,
    decider: Decider,
    simClustersANNCacheClient: Client
  ): ReadableStore[ClusterId, Seq[(TweetId, Double)]] = {
    // Build the underling cluster-to-tweet store
    val topTweetsForClusterStore = clusterConfig.clusterTweetIndetwitterStoreConfig match {
      // If the config returns Manhattan tweet indetwitter config, we read from a RO MH store
      case manhattanConfig: ClusterTweetIndetwitterStoreConfig.Manhattan =>
        TopKTweetsForClusterKeyReadableStore.getClusterToTopKTweetsStoreFromManhattanRO(
          matwitterTopTweetPerCluster,
          manhattanConfig,
          serviceIdentifier)
      case memCacheConfig: ClusterTweetIndetwitterStoreConfig.Memcached =>
        TopKTweetsForClusterKeyReadableStore.getClusterToTopKTweetsStoreFromMemCache(
          matwitterTopTweetPerCluster,
          memCacheConfig,
          serviceIdentifier)
      case _ =>
        // Bad instance
        ReadableStore.empty
    }

    val embeddingType: EmbeddingType = clusterConfig.candidateTweetEmbeddingType
    val modelVersion: String = ModelVersions.toKnownForModelVersion(clusterConfig.modelVersion)

    val store: ReadableStore[ClusterId, Seq[(TweetId, Double)]] =
      topTweetsForClusterStore.composeKeyMapping { id: ClusterId =>
        ClusterKey(id, modelVersion, embeddingType)
      }

    val memcachedTopTweetsForClusterStore =
      ObservedMemcachedReadableStore.fromCacheClient(
        backingStore = store,
        cacheClient = simClustersANNCacheClient,
        ttl = 15.minutes,
        asyncUpdate = asyncUpdate
      )(
        valueInjection = LZ4Injection.compose(SeqObjectInjection[(Long, Double)]()),
        statsReceiver = stats.scope("cluster_tweet_indetwitter_mem_cache"),
        keyToString = { k =>
          // prod cache key : SimClusters_LZ4/cluster_to_tweet/clusterId_embeddingType_modelVersion
          s"scz:c2t:${k}_${embeddingType}_${modelVersion}_$matwitterTopTweetPerCluster"
        }
      )

    val cachedStore: ReadableStore[ClusterId, Seq[(TweetId, Double)]] = {
      ObservedCachedReadableStore.from[ClusterId, Seq[(TweetId, Double)]](
        memcachedTopTweetsForClusterStore,
        ttl = 10.minute,
        matwitterKeys = 150000,
        cacheName = "cluster_tweet_indetwitter_cache",
        windowSize = 10000L
      )(stats.scope("cluster_tweet_indetwitter_store"))
    }
    cachedStore
  }
}
