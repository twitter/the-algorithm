package com.ExTwitter.home_mixer.module

import com.google.inject.Provides
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.home_mixer.{thriftscala => t}
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.product_mixer.shared_library.memcached_client.MemcachedClientBuilder
import com.ExTwitter.servo.cache.FinagleMemcache
import com.ExTwitter.servo.cache.KeyTransformer
import com.ExTwitter.servo.cache.KeyValueTransformingTtlCache
import com.ExTwitter.servo.cache.Serializer
import com.ExTwitter.servo.cache.ThriftSerializer
import com.ExTwitter.servo.cache.TtlCache
import com.ExTwitter.timelines.model.UserId
import org.apache.thrift.protocol.TCompactProtocol

import javax.inject.Singleton

object ScoredTweetsMemcacheModule extends ExTwitterModule {

  private val ScopeName = "ScoredTweetsCache"
  private val ProdDestName = "/srv#/prod/local/cache/home_scored_tweets:twemcaches"
  private val StagingDestName = "/srv#/test/local/cache/twemcache_home_scored_tweets:twemcaches"
  private val scoredTweetsSerializer: Serializer[t.ScoredTweetsResponse] =
    new ThriftSerializer[t.ScoredTweetsResponse](
      t.ScoredTweetsResponse,
      new TCompactProtocol.Factory())
  private val userIdKeyTransformer: KeyTransformer[UserId] = (userId: UserId) => userId.toString

  @Singleton
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
      statsReceiver = statsReceiver.scope(ScopeName)
    )
    val underlyingCache = new FinagleMemcache(client)

    new KeyValueTransformingTtlCache(
      underlyingCache = underlyingCache,
      transformer = scoredTweetsSerializer,
      underlyingKey = userIdKeyTransformer
    )
  }
}
