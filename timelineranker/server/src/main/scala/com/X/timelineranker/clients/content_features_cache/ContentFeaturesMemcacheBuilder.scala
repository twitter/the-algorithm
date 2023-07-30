package com.X.timelineranker.clients.content_features_cache

import com.X.bijection.Injection
import com.X.bijection.scrooge.CompactScalaCodec
import com.X.finagle.stats.StatsReceiver
import com.X.storehaus.Store
import com.X.timelineranker.recap.model.ContentFeatures
import com.X.timelines.clients.memcache_common._
import com.X.timelines.content_features.{thriftscala => thrift}
import com.X.timelines.model.TweetId
import com.X.util.Duration

/**
 * Content features will be stored by tweetId
 */
class ContentFeaturesMemcacheBuilder(
  config: StorehausMemcacheConfig,
  ttl: Duration,
  statsReceiver: StatsReceiver) {
  private[this] val scalaToThriftInjection: Injection[ContentFeatures, thrift.ContentFeatures] =
    Injection.build[ContentFeatures, thrift.ContentFeatures](_.toThrift)(
      ContentFeatures.tryFromThrift)

  private[this] val thriftToBytesInjection: Injection[thrift.ContentFeatures, Array[Byte]] =
    CompactScalaCodec(thrift.ContentFeatures)

  private[this] implicit val valueInjection: Injection[ContentFeatures, Array[Byte]] =
    scalaToThriftInjection.andThen(thriftToBytesInjection)

  private[this] val underlyingBuilder =
    new MemcacheStoreBuilder[TweetId, ContentFeatures](
      config = config,
      scopeName = "contentFeaturesCache",
      statsReceiver = statsReceiver,
      ttl = ttl
    )

  def build(): Store[TweetId, ContentFeatures] = underlyingBuilder.build()
}
