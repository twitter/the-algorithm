package com.twitter.recosinjector.config

import com.twitter.finagle.memcached.Client
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.storehaus_internal.memcache.MemcacheStore
import com.twitter.storehaus_internal.util.{ClientName, ZkEndPoint}

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
