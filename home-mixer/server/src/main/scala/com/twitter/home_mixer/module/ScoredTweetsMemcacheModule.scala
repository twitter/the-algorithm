package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.param.HomeMixerInjectionNames.ScoredTweetsCache
import com.twitter.home_mixer.{thriftscala => t}
import com.twitter.inject.TwitterModule
import com.twitter.product_mixer.shared_library.memcached_client.MemcachedClientBuilder
import com.twitter.servo.cache.FinagleMemcache
import com.twitter.servo.cache.KeyTransformer
import com.twitter.servo.cache.KeyValueTransformingTtlCache
import com.twitter.servo.cache.Serializer
import com.twitter.servo.cache.ThriftSerializer
import com.twitter.servo.cache.TtlCache
import com.twitter.timelines.model.UserId
import org.apache.thrift.protocol.TCompactProtocol
import com.twitter.finagle.memcached.compressing.scheme.Lz4

import javax.inject.Named
import javax.inject.Singleton

object ScoredTweetsMemcacheModule extends TwitterModule {

  private val ScopeName = "ScoredTweetsCache"
  private val ProdDestName = "/srv#/prod/local/cache/home_scored_tweets:twemcaches"
  private val StagingDestName = "/srv#/test/local/cache/twemcache_home_scored_tweets:twemcaches"
  private val scoredTweetsSerializer: Serializer[t.ScoredTweetsResponse] =
    new ThriftSerializer[t.ScoredTweetsResponse](
      t.ScoredTweetsResponse,
      new TCompactProtocol.Factory())
  private val userIdKeyTransformer: KeyTransformer[UserId] = (userId: UserId) => userId.toString

  @Singleton
  @Named(ScoredTweetsCache)
  @Provides
  def providesScoredTweetsCache(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): TtlCache[UserId, t.ScoredTweetsResponse] = {
    val destName = serviceIdentifier.environment.toLowerCase match {
      case "prod" => ProdDestName
      case _ => StagingDestName
    }
    val client = MemcachedClientBuilder.buildMemcachedClient(
      destName = destName,
      numTries = 2,
      numConnections = 1,
      requestTimeout = 200.milliseconds,
      globalTimeout = 400.milliseconds,
      connectTimeout = 100.milliseconds,
      acquisitionTimeout = 100.milliseconds,
      serviceIdentifier = serviceIdentifier,
      statsReceiver = statsReceiver.scope(ScopeName),
      compressionScheme = Lz4
    )
    val underlyingCache = new FinagleMemcache(client)

    new KeyValueTransformingTtlCache(
      underlyingCache = underlyingCache,
      transformer = scoredTweetsSerializer,
      underlyingKey = userIdKeyTransformer
    )
  }
}
