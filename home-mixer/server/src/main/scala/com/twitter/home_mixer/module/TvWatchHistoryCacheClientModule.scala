package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.hashing.KeyHasher
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TvWatchedVideoIdsKeyCacheStore
import com.twitter.inject.TwitterModule
import com.twitter.product_mixer.shared_library.memcached_client.MemcachedClientBuilder
import com.twitter.servo.cache.FinagleMemcache
import com.twitter.servo.cache.KeyValueTransformingTtlCache
import com.twitter.servo.cache.ObservableTtlCache
import com.twitter.servo.cache.TtlCache
import com.twitter.servo.util.Transformer
import com.twitter.util.Try
import java.nio.ByteBuffer
import javax.inject.Named
import javax.inject.Singleton
import scala.collection.mutable.ArrayBuffer

object TvWatchHistoryCacheClientModule extends TwitterModule {
  @Provides
  @Singleton
  @Named(TvWatchedVideoIdsKeyCacheStore)
  def providesEntityRealGraphClient(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): TtlCache[Long, Seq[Long]] = {
    val scopeName = "TvWatchedVideoIds"
    val memCacheClient = MemcachedClientBuilder.buildMemcachedClient(
      destName = "/srv#/prod/local/cache/related_videos:twemcaches",
      numTries = 1,
      numConnections = 1,
      requestTimeout = 50.milliseconds,
      globalTimeout = 100.milliseconds,
      connectTimeout = 100.milliseconds,
      acquisitionTimeout = 100.milliseconds,
      serviceIdentifier = serviceIdentifier,
      statsReceiver = statsReceiver,
      keyHasher = Some(KeyHasher.FNV1A_64)
    )

    val longSeqTransformer: Transformer[Seq[Long], Array[Byte]] =
      new Transformer[Seq[Long], Array[Byte]] {
        def deserialize(input: Array[Byte]): ArrayBuffer[Long] = {
          val buffer = ByteBuffer.wrap(input)
          val resultBuffer = ArrayBuffer[Long]()
          while (buffer.hasRemaining) {
            val longValue = buffer.getLong()
            resultBuffer += longValue
          }
          resultBuffer
        }

        override def from(input: Array[Byte]): Try[Seq[Long]] = Try(deserialize(input))

        override def to(b: Seq[Long]): Try[Array[Byte]] = ???
      }
    val keyValueCache = new KeyValueTransformingTtlCache[Long, String, Seq[Long], Array[Byte]](
      underlyingCache = new FinagleMemcache(memCacheClient),
      transformer = longSeqTransformer,
      underlyingKey = { key: Long => key.toString }
    )
    ObservableTtlCache(
      underlyingCache = keyValueCache,
      statsReceiver = statsReceiver.scope(scopeName),
      windowSize = 1000,
      name = scopeName
    )
  }
}
