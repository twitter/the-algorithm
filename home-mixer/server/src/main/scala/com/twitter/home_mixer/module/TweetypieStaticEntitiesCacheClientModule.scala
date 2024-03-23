package com.ExTwitter.home_mixer.module

import com.google.inject.name.Named
import com.google.inject.Provides
import com.ExTwitter.conversions.DurationOps.RichDuration
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.TweetypieStaticEntitiesCache
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.product_mixer.shared_library.memcached_client.MemcachedClientBuilder
import com.ExTwitter.servo.cache.FinagleMemcache
import com.ExTwitter.servo.cache.KeyTransformer
import com.ExTwitter.servo.cache.KeyValueTransformingTtlCache
import com.ExTwitter.servo.cache.ObservableTtlCache
import com.ExTwitter.servo.cache.Serializer
import com.ExTwitter.servo.cache.ThriftSerializer
import com.ExTwitter.servo.cache.TtlCache
import com.ExTwitter.tweetypie.{thriftscala => tp}
import javax.inject.Singleton
import org.apache.thrift.protocol.TCompactProtocol

object TweetypieStaticEntitiesCacheClientModule extends ExTwitterModule {

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
