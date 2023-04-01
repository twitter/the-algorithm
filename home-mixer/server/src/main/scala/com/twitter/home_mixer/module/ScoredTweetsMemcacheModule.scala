package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.{thriftscala => t}
import com.twitter.inject.TwitterModule
import com.twitter.product_mixer.shared_library.memcached_client.MemcachedClientBuilder
import com.twitter.servo.cache.FinagleMemcache
import com.twitter.servo.cache.KeyTransformer
import com.twitter.servo.cache.KeyValueTransformingTtlCache
import com.twitter.servo.cache.ObservableTtlCache
import com.twitter.servo.cache.Serializer
import com.twitter.servo.cache.ThriftSerializer
import com.twitter.servo.cache.TtlCache
import com.twitter.timelines.model.UserId
import org.apache.thrift.protocol.TCompactProtocol

import javax.inject.Singleton

object ScoredTweetsMemcacheModule extends TwitterModule {

  private val ScopeName = "ScoredTweetsCache"
  private val ProdDestName = "/srv#/prod/local/cache/home_scored_tweets:twemcaches"
  private val StagingDestName = "/srv#/test/local/cache/twemcache_home_scored_tweets:twemcaches"
  private val cachedScoredTweetsSerializer: Serializer[t.CachedScoredTweets] =
    new ThriftSerializer[t.CachedScoredTweets](t.CachedScoredTweets, new TCompactProtocol.Factory())
  private val userIdKeyTransformer: KeyTransformer[UserId] = (userId: UserId) => userId.toString

  @Singleton
  @Provides
  def providesScoredTweetsCache(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): TtlCache[UserId, t.CachedScoredTweets] = {
    val destName = serviceIdentifier.environment.toLowerCase match {
      case "prod" => ProdDestName
      case _ => StagingDestName
    }
    val client = MemcachedClientBuilder.buildMemcachedClient(
      destName = destName,
      numTries = 2,
      requestTimeout = 200.milliseconds,
      globalTimeout = 400.milliseconds,
      connectTimeout = 100.milliseconds,
      acquisitionTimeout = 100.milliseconds,
      serviceIdentifier = serviceIdentifier,
      statsReceiver = statsReceiver.scope(ScopeName)
    )
    val underlyingCache = new FinagleMemcache(client)
    val baseCache: KeyValueTransformingTtlCache[UserId, String, t.CachedScoredTweets, Array[Byte]] =
      new KeyValueTransformingTtlCache(
        underlyingCache = underlyingCache,
        transformer = cachedScoredTweetsSerializer,
        underlyingKey = userIdKeyTransformer
      )
    ObservableTtlCache(
      underlyingCache = baseCache,
      statsReceiver = statsReceiver,
      windowSize = 1000L,
      name = ScopeName
    )
  }
}
