package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.google.inject.name.Named
import com.twitter.conversions.DurationOps.RichDuration
import com.twitter.cproxy.{thriftscala => cp}
import com.twitter.deferredrpc.client.DeferredThriftService
import com.twitter.deferredrpc.thrift.Datacenter
import com.twitter.deferredrpc.thrift.DeferredRPC
import com.twitter.deferredrpc.thrift.Target
import com.twitter.finagle.ThriftMux
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.mtls.client.MtlsStackClient.MtlsThriftMuxClientSyntax
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TweetypieStaticEntitiesCache
import com.twitter.inject.TwitterModule
import com.twitter.product_mixer.shared_library.memcached_client.MemcachedClientBuilder
import com.twitter.servo.cache.ExpiringLruInProcessCache
import com.twitter.servo.cache.FinagleMemcache
import com.twitter.servo.cache.HotKeyMemcacheClient
import com.twitter.servo.cache.KeyTransformer
import com.twitter.servo.cache.KeyValueTransformingTtlCache
import com.twitter.servo.cache.ObservableTtlCache
import com.twitter.servo.cache.Serializer
import com.twitter.servo.cache.ThriftSerializer
import com.twitter.servo.cache.TtlCache
import com.twitter.servo.util.Gate
import com.twitter.timelinemixer.clients.rankedtweetcaching.CproxyTtlCacheWrapper
import com.twitter.tweetypie.{thriftscala => tp}
import javax.inject.Singleton
import org.apache.thrift.protocol.TCompactProtocol

object TweetypieStaticEntitiesCacheClientModule extends TwitterModule {

  private val ScopeName = "TweetypieStaticEntitiesMemcache"
  private val ProdDest = "/s/cache/timelinescorer_tweet_core_data:twemcaches"
  private val ProdKrpcDest = "/s/kafka-shared/krpc-server-cache"
  private val DevelKrpcDest = "/srv#/staging/local/kafka-shared/krpc-server-custdevel"

  private val tweetsSerializer: Serializer[tp.Tweet] =
    new ThriftSerializer[tp.Tweet](tp.Tweet, new TCompactProtocol.Factory())

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
      requestTimeout = 200.milliseconds,
      globalTimeout = 200.milliseconds,
      connectTimeout = 200.milliseconds,
      acquisitionTimeout = 200.milliseconds,
      serviceIdentifier = serviceIdentifier,
      statsReceiver = statsReceiver
    )

    val hotkeyCacheClient = new HotKeyMemcacheClient(
      proxyClient = memCacheClient,
      inProcessCache = new ExpiringLruInProcessCache(ttl = 15.minute, maximumSize = 75000),
      statsReceiver = statsReceiver.scope(ScopeName).scope("inProcess")
    )

    val krpcDest = serviceIdentifier.environment.toLowerCase match {
      case "prod" => ProdKrpcDest
      case _ => DevelKrpcDest
    }

    val deferredRpcClient = new DeferredRPC.FinagledClient(
      ClientBuilder()
        .name("deferredRpc")
        .dest(krpcDest)
        .requestTimeout(200.milliseconds)
        .hostConnectionLimit(3)
        .stack(ThriftMux.client.withMutualTls(serviceIdentifier))
        .build()
    )

    val cproxyClient = new cp.Cproxy.FinagledClient(
      service = new DeferredThriftService(
        deferredrpcService = deferredRpcClient,
        target = Target(Datacenter.AllOthers, "cproxy"),
        statsReceiver = statsReceiver.scope("deferredThriftService")
      ),
      stats = statsReceiver.scope("cproxy")
    )

    val baseCache: KeyValueTransformingTtlCache[Long, String, tp.Tweet, Array[Byte]] =
      new KeyValueTransformingTtlCache(
        underlyingCache = new FinagleMemcache(hotkeyCacheClient),
        transformer = tweetsSerializer,
        underlyingKey = keyTransformer
      )

    val observableTtlCache = ObservableTtlCache(
      underlyingCache = baseCache,
      statsReceiver = statsReceiver.scope(ScopeName),
      windowSize = 1000,
      name = ScopeName
    )

    new CproxyTtlCacheWrapper[Long, tp.Tweet](
      underlyingCache = observableTtlCache,
      cProxyCache = cproxyClient,
      cType = cp.CachePoolType.HomeTweetCoreData,
      valueSerializer = tweetsSerializer,
      keyTransformer = keyTransformer,
      replicationAvailable = Gate.True
    )
  }
}
