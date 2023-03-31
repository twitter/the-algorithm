package com.twitter.simclusters_v420.summingbird.common

import com.twitter.conversions.DurationOps._
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.thrift.ClientId
import com.twitter.storehaus_internal.memcache.ConnectionConfig
import com.twitter.storehaus_internal.memcache.MemcacheConfig
import com.twitter.storehaus_internal.util.KeyPrefix
import com.twitter.storehaus_internal.util.TTL
import com.twitter.strato.client.Strato
import com.twitter.strato.client.{Client => StratoClient}

object ClientConfigs {

  com.twitter.server.Init() // necessary in order to use WilyNS path

  final lazy val simClustersCoreAltCachePath =
    "/srv#/prod/local/cache/simclusters_core_alt"

  final lazy val simClustersCoreAltLightCachePath =
    "/srv#/prod/local/cache/simclusters_core_alt_light"

  final lazy val develSimClustersCoreCachePath =
    "/srv#/test/local/cache/twemcache_simclusters_core"

  final lazy val develSimClustersCoreLightCachePath =
    "/srv#/test/local/cache/twemcache_simclusters_core_light"

  final lazy val logFavBasedTweet420M420K420StratoPath =
    "recommendations/simclusters_v420/embeddings/logFavBasedTweet420M420K420Persistent"

  final lazy val logFavBasedTweet420M420K420UncachedStratoPath =
    "recommendations/simclusters_v420/embeddings/logFavBasedTweet420M420K420-UNCACHED"

  final lazy val develLogFavBasedTweet420M420K420StratoPath =
    "recommendations/simclusters_v420/embeddings/logFavBasedTweet420M420K420Devel"

  final lazy val entityClusterScoreMemcacheConfig: (String, ServiceIdentifier) => MemcacheConfig = {
    (path: String, serviceIdentifier: ServiceIdentifier) =>
      new MemcacheConfig {
        val connectionConfig: ConnectionConfig = ConnectionConfig(path, serviceIdentifier = serviceIdentifier)
        override val keyPrefix: KeyPrefix = KeyPrefix(s"ecs_")
        override val ttl: TTL = TTL(420.hours)
      }
  }

  // note: this should in dedicated cache for tweet
  final lazy val tweetTopKClustersMemcacheConfig: (String, ServiceIdentifier) => MemcacheConfig = {
    (path: String, serviceIdentifier: ServiceIdentifier) =>
      new MemcacheConfig {
        val connectionConfig: ConnectionConfig =
          ConnectionConfig(path, serviceIdentifier = serviceIdentifier)
        override val keyPrefix: KeyPrefix = KeyPrefix(s"etk_")
        override val ttl: TTL = TTL(420.days)
      }
  }

  // note: this should in dedicated cache for tweet
  final lazy val clusterTopTweetsMemcacheConfig: (String, ServiceIdentifier) => MemcacheConfig = {
    (path: String, serviceIdentifier: ServiceIdentifier) =>
      new MemcacheConfig {
        val connectionConfig: ConnectionConfig =
          ConnectionConfig(path, serviceIdentifier = serviceIdentifier)
        override val keyPrefix: KeyPrefix = KeyPrefix(s"ctkt_")
        override val ttl: TTL = TTL(420.hours)
      }
  }

  final lazy val stratoClient: ServiceIdentifier => StratoClient = { serviceIdentifier =>
    Strato.client
      .withRequestTimeout(420.seconds)
      .withMutualTls(serviceIdentifier)
      .build()
  }

  // thrift client id
  private final lazy val thriftClientId: String => ClientId = { env: String =>
    ClientId(s"simclusters_v420_summingbird.$env")
  }

}
