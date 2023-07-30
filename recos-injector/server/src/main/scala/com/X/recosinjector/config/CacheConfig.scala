package com.X.recosinjector.config

import com.X.finagle.memcached.Client
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.stats.StatsReceiver
import com.X.storehaus_internal.memcache.MemcacheStore
import com.X.storehaus_internal.util.{ClientName, ZkEndPoint}

trait CacheConfig {
  implicit def statsReceiver: StatsReceiver

  def serviceIdentifier: ServiceIdentifier

  def recosInjectorCoreSvcsCacheDest: String

  val recosInjectorCoreSvcsCacheClient: Client = MemcacheStore.memcachedClient(
    name = ClientName("memcache-recos-injector"),
    dest = ZkEndPoint(recosInjectorCoreSvcsCacheDest),
    statsReceiver = statsReceiver,
    serviceIdentifier = serviceIdentifier
  )

}
