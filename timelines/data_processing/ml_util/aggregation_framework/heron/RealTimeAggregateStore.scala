package com.twitter.timelines.data_processing.ml_util.aggregation_framework.heron

import com.twitter.conversions.DurationOps._
import com.twitter.finagle.mtls.authentication.EmptyServiceIdentifier
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.storehaus_internal.memcache.ConnectionConfig
import com.twitter.storehaus_internal.memcache.MemcacheConfig
import com.twitter.storehaus_internal.util.KeyPrefix
import com.twitter.storehaus_internal.util.TTL
import com.twitter.storehaus_internal.util.ZkEndPoint
import com.twitter.summingbird_internal.runner.store_config.OnlineStoreOnlyConfig
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregateStore
import com.twitter.util.Duration

object RealTimeAggregateStore {
  val twCacheWilyPrefix = "/srv#" // s2s is only supported for wily path

  def makeEndpoint(
    memcacheDataSet: String,
    isProd: Boolean,
    twCacheWilyPrefix: String = twCacheWilyPrefix
  ): String = {
    val env = if (isProd) "prod" else "test"
    s"$twCacheWilyPrefix/$env/local/cache/$memcacheDataSet"
  }
}

case class RealTimeAggregateStore(
  memcacheDataSet: String,
  isProd: Boolean = false,
  cacheTTL: Duration = 1.day)
    extends OnlineStoreOnlyConfig[MemcacheConfig]
    with AggregateStore {
  import RealTimeAggregateStore._

  override val name: String = ""
  val storeKeyPrefix: KeyPrefix = KeyPrefix(name)
  val memcacheZkEndPoint: String = makeEndpoint(memcacheDataSet, isProd)

  def online: MemcacheConfig = online(serviceIdentifier = EmptyServiceIdentifier)

  def online(serviceIdentifier: ServiceIdentifier = EmptyServiceIdentifier): MemcacheConfig =
    new MemcacheConfig {
      val endpoint = ZkEndPoint(memcacheZkEndPoint)
      override val connectionConfig =
        ConnectionConfig(endpoint, serviceIdentifier = serviceIdentifier)
      override val keyPrefix = storeKeyPrefix
      override val ttl = TTL(Duration.fromMilliseconds(cacheTTL.inMillis))
    }
}
