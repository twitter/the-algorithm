package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.conversions.DurationOps.RichDuration
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.gizmoduck.{thriftscala => gt}
import com.twitter.home_mixer.param.HomeMixerInjectionNames.GizmoduckTimelinesCache
import com.twitter.inject.TwitterModule
import com.twitter.product_mixer.shared_library.memcached_client.MemcachedClientBuilder
import com.twitter.servo.cache.FinagleMemcache
import com.twitter.servo.cache.KeyValueTransformingTtlCache
import com.twitter.servo.cache.ObservableTtlCache
import com.twitter.servo.cache.Serializer
import com.twitter.servo.cache.ThriftSerializer
import com.twitter.servo.cache.TtlCache
import javax.inject.Named
import javax.inject.Singleton
import org.apache.thrift.protocol.TCompactProtocol
import com.twitter.finagle.memcached.compressing.scheme.Lz4

object GizmoduckTimelinesCacheClientModule extends TwitterModule {

  private val ScopeName = "GizmoduckTimelinesCache"
  private val ProdDest = "/srv#/prod/local/cache/timelines_gizmoduck_secure:twemcaches"

  private val userSerializer: Serializer[gt.User] = {
    new ThriftSerializer[gt.User](gt.User, new TCompactProtocol.Factory())
  }

  @Provides
  @Singleton
  @Named(GizmoduckTimelinesCache)
  def providesGizmoduckTimelinesCache(
    statsReceiver: StatsReceiver,
    serviceIdentifier: ServiceIdentifier
  ): TtlCache[Long, gt.User] = {
    val memCacheClient = MemcachedClientBuilder.buildMemcachedClient(
      destName = ProdDest,
      numTries = 1,
      numConnections = 1,
      requestTimeout = 100.milliseconds,
      globalTimeout = 100.milliseconds,
      connectTimeout = 100.milliseconds,
      acquisitionTimeout = 100.milliseconds,
      serviceIdentifier = serviceIdentifier,
      statsReceiver = statsReceiver,
      compressionScheme = Lz4
    )
    mkCache(new FinagleMemcache(memCacheClient), statsReceiver)
  }

  private def mkCache(
    finagleMemcache: FinagleMemcache,
    statsReceiver: StatsReceiver
  ): TtlCache[Long, gt.User] = {
    val baseCache: KeyValueTransformingTtlCache[Long, String, gt.User, Array[Byte]] =
      new KeyValueTransformingTtlCache(
        underlyingCache = finagleMemcache,
        transformer = userSerializer,
        underlyingKey = { key: Long => key.toString }
      )
    ObservableTtlCache(
      underlyingCache = baseCache,
      statsReceiver = statsReceiver.scope(ScopeName),
      windowSize = 1000,
      name = ScopeName
    )
  }
}
