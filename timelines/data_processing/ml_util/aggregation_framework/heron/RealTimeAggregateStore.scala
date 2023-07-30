package com.X.timelines.data_processing.ml_util.aggregation_framework.heron

import com.X.conversions.DurationOps._
import com.X.finagle.mtls.authentication.EmptyServiceIdentifier
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.storehaus_internal.memcache.ConnectionConfig
import com.X.storehaus_internal.memcache.MemcacheConfig
import com.X.storehaus_internal.util.KeyPrefix
import com.X.storehaus_internal.util.TTL
import com.X.storehaus_internal.util.ZkEndPoint
import com.X.summingbird_internal.runner.store_config.OnlineStoreOnlyConfig
import com.X.timelines.data_processing.ml_util.aggregation_framework.AggregateStore
import com.X.util.Duration

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
