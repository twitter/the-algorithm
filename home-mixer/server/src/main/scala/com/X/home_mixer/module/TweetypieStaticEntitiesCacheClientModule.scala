package com.X.home_mixer.module

import com.google.inject.name.Named
import com.google.inject.Provides
import com.X.conversions.DurationOps.RichDuration
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.stats.StatsReceiver
import com.X.home_mixer.param.HomeMixerInjectionNames.TweetypieStaticEntitiesCache
import com.X.inject.XModule
import com.X.product_mixer.shared_library.memcached_client.MemcachedClientBuilder
import com.X.servo.cache.FinagleMemcache
import com.X.servo.cache.KeyTransformer
import com.X.servo.cache.KeyValueTransformingTtlCache
import com.X.servo.cache.ObservableTtlCache
import com.X.servo.cache.Serializer
import com.X.servo.cache.ThriftSerializer
import com.X.servo.cache.TtlCache
import com.X.tweetypie.{thriftscala => tp}
import javax.inject.Singleton
import org.apache.thrift.protocol.TCompactProtocol

object TweetypieStaticEntitiesCacheClientModule extends XModule {

  private val ScopeName = "TweetypieStaticEntitiesMemcache"
  private val ProdDest = "/srv#/prod/local/cache/timelinescorer_tweet_core_data:twemcaches"

  private val tweetsSerializer: Serializer[tp.Tweet] = {
    new ThriftSerializer[tp.Tweet](tp.Tweet, new TCompactProtocol.Factory())
  }
  private val keyTransformer: KeyTransformer[Long] = { tweetId => tweetId.toString }

  @Provides
  @Singleton
  @Named(TweetypieStaticEntitiesCache)
  def providesTweetypieStaticEntitiesCache(
    statsReceiver: StatsReceiver,
    serviceIdentifier: ServiceIdentifier
  ): TtlCache[Long, tp.Tweet] = {
    val memCacheClient = MemcachedClientBuilder.buildMemcachedClient(
      destName = ProdDest,
      numTries = 1,
      numConnections = 1,
      requestTimeout = 50.milliseconds,
      globalTimeout = 100.milliseconds,
      connectTimeout = 100.milliseconds,
      acquisitionTimeout = 100.milliseconds,
      serviceIdentifier = serviceIdentifier,
      statsReceiver = statsReceiver
    )
    mkCache(new FinagleMemcache(memCacheClient), statsReceiver)
  }

  private def mkCache(
    finagleMemcache: FinagleMemcache,
    statsReceiver: StatsReceiver
  ): TtlCache[Long, tp.Tweet] = {
    val baseCache: KeyValueTransformingTtlCache[Long, String, tp.Tweet, Array[Byte]] =
      new KeyValueTransformingTtlCache(
        underlyingCache = finagleMemcache,
        transformer = tweetsSerializer,
        underlyingKey = keyTransformer
      )
    ObservableTtlCache(
      underlyingCache = baseCache,
      statsReceiver = statsReceiver.scope(ScopeName),
      windowSize = 1000,
      name = ScopeName
    )
  }
}
