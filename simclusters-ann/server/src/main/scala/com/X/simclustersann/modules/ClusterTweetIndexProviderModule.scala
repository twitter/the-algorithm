package com.X.simclustersann.modules

import com.google.inject.Provides
import com.X.conversions.DurationOps._
import com.X.decider.Decider
import com.X.finagle.memcached.Client
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.stats.StatsReceiver
import com.X.hermit.store.common.ObservedCachedReadableStore
import com.X.hermit.store.common.ObservedMemcachedReadableStore
import com.X.inject.XModule
import com.X.inject.annotations.Flag
import com.X.relevance_platform.common.injection.LZ4Injection
import com.X.relevance_platform.common.injection.SeqObjectInjection
import com.X.relevance_platform.simclustersann.multicluster.ClusterConfig
import com.X.relevance_platform.simclustersann.multicluster.ClusterTweetIndexStoreConfig
import com.X.simclusters_v2.common.ClusterId
import com.X.simclusters_v2.common.ModelVersions
import com.X.simclusters_v2.common.TweetId
import com.X.simclusters_v2.summingbird.stores.ClusterKey
import com.X.simclusters_v2.summingbird.stores.TopKTweetsForClusterKeyReadableStore
import com.X.simclusters_v2.thriftscala.EmbeddingType
import com.X.simclustersann.common.FlagNames
import com.X.storehaus.ReadableStore

import javax.inject.Singleton

object ClusterTweetIndexProviderModule extends XModule {

  @Singleton
  @Provides
  // Provides ClusterTweetIndex Store based on different maxResults settings on the same store
  // Create a different provider if index is in a different store
  def providesClusterTweetIndex(
    @Flag(FlagNames.MaxTopTweetPerCluster) maxTopTweetPerCluster: Int,
    @Flag(FlagNames.CacheAsyncUpdate) asyncUpdate: Boolean,
    clusterConfig: ClusterConfig,
    serviceIdentifier: ServiceIdentifier,
    stats: StatsReceiver,
    decider: Decider,
    simClustersANNCacheClient: Client
  ): ReadableStore[ClusterId, Seq[(TweetId, Double)]] = {
    // Build the underling cluster-to-tweet store
    val topTweetsForClusterStore = clusterConfig.clusterTweetIndexStoreConfig match {
      // If the config returns Manhattan tweet index config, we read from a RO MH store
      case manhattanConfig: ClusterTweetIndexStoreConfig.Manhattan =>
        TopKTweetsForClusterKeyReadableStore.getClusterToTopKTweetsStoreFromManhattanRO(
          maxTopTweetPerCluster,
          manhattanConfig,
          serviceIdentifier)
      case memCacheConfig: ClusterTweetIndexStoreConfig.Memcached =>
        TopKTweetsForClusterKeyReadableStore.getClusterToTopKTweetsStoreFromMemCache(
          maxTopTweetPerCluster,
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
        statsReceiver = stats.scope("cluster_tweet_index_mem_cache"),
        keyToString = { k =>
          // prod cache key : SimClusters_LZ4/cluster_to_tweet/clusterId_embeddingType_modelVersion
          s"scz:c2t:${k}_${embeddingType}_${modelVersion}_$maxTopTweetPerCluster"
        }
      )

    val cachedStore: ReadableStore[ClusterId, Seq[(TweetId, Double)]] = {
      ObservedCachedReadableStore.from[ClusterId, Seq[(TweetId, Double)]](
        memcachedTopTweetsForClusterStore,
        ttl = 10.minute,
        maxKeys = 150000,
        cacheName = "cluster_tweet_index_cache",
        windowSize = 10000L
      )(stats.scope("cluster_tweet_index_store"))
    }
    cachedStore
  }
}
