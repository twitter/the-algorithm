package com.ExTwitter.home_mixer.module

import com.google.inject.Provides
import com.google.inject.name.Named
import com.ExTwitter.bijection.Injection
import com.ExTwitter.bijection.scrooge.BinaryScalaCodec
import com.ExTwitter.bijection.thrift.ThriftCodec
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.EngagementsReceivedByAuthorCache
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.RealTimeInteractionGraphUserVertexCache
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.RealTimeInteractionGraphUserVertexClient
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.TimelinesRealTimeAggregateClient
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.TopicCountryEngagementCache
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.TopicEngagementCache
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.TweetCountryEngagementCache
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.TweetEngagementCache
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.ExTwitterListEngagementCache
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.UserAuthorEngagementCache
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.UserEngagementCache
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.UserTopicEngagementForNewUserCache
import com.ExTwitter.home_mixer.util.InjectionTransformerImplicits._
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.ml.api.DataRecord
import com.ExTwitter.ml.api.Feature
import com.ExTwitter.ml.{api => ml}
import com.ExTwitter.servo.cache.KeyValueTransformingReadCache
import com.ExTwitter.servo.cache.Memcache
import com.ExTwitter.servo.cache.ReadCache
import com.ExTwitter.servo.util.Transformer
import com.ExTwitter.storehaus_internal.memcache.MemcacheHelper
import com.ExTwitter.summingbird.batch.Batcher
import com.ExTwitter.summingbird_internal.bijection.BatchPairImplicits
import com.ExTwitter.timelines.data_processing.ml_util.aggregation_framework.AggregationKey
import com.ExTwitter.timelines.data_processing.ml_util.aggregation_framework.AggregationKeyInjection
import com.ExTwitter.wtf.real_time_interaction_graph.{thriftscala => ig}

import javax.inject.Singleton

object RealtimeAggregateFeatureRepositoryModule
    extends ExTwitterModule
    with RealtimeAggregateHelpers {

  private val authorIdFeature = new Feature.Discrete("entities.source_author_id").getFeatureId
  private val countryCodeFeature = new Feature.Text("geo.user_location.country_code").getFeatureId
  private val listIdFeature = new Feature.Discrete("list.id").getFeatureId
  private val userIdFeature = new Feature.Discrete("meta.user_id").getFeatureId
  private val topicIdFeature = new Feature.Discrete("entities.topic_id").getFeatureId
  private val tweetIdFeature = new Feature.Discrete("entities.source_tweet_id").getFeatureId

  @Provides
  @Singleton
  @Named(UserTopicEngagementForNewUserCache)
  def providesUserTopicEngagementForNewUserCache(
    @Named(TimelinesRealTimeAggregateClient) client: Memcache
  ): ReadCache[(Long, Long), ml.DataRecord] = {
    new KeyValueTransformingReadCache(
      client,
      dataRecordValueTransformer,
      keyTransformD2(userIdFeature, topicIdFeature)
    )
  }

  @Provides
  @Singleton
  @Named(ExTwitterListEngagementCache)
  def providesExTwitterListEngagementCache(
    @Named(TimelinesRealTimeAggregateClient) client: Memcache
  ): ReadCache[Long, ml.DataRecord] = {
    new KeyValueTransformingReadCache(
      client,
      dataRecordValueTransformer,
      keyTransformD1(listIdFeature)
    )
  }

  @Provides
  @Singleton
  @Named(TopicEngagementCache)
  def providesTopicEngagementCache(
    @Named(TimelinesRealTimeAggregateClient) client: Memcache
  ): ReadCache[Long, ml.DataRecord] = {
    new KeyValueTransformingReadCache(
      client,
      dataRecordValueTransformer,
      keyTransformD1(topicIdFeature)
    )
  }

  @Provides
  @Singleton
  @Named(UserAuthorEngagementCache)
  def providesUserAuthorEngagementCache(
    @Named(TimelinesRealTimeAggregateClient) client: Memcache
  ): ReadCache[(Long, Long), ml.DataRecord] = {
    new KeyValueTransformingReadCache(
      client,
      dataRecordValueTransformer,
      keyTransformD2(userIdFeature, authorIdFeature)
    )
  }

  @Provides
  @Singleton
  @Named(UserEngagementCache)
  def providesUserEngagementCache(
    @Named(TimelinesRealTimeAggregateClient) client: Memcache
  ): ReadCache[Long, ml.DataRecord] = {
    new KeyValueTransformingReadCache(
      client,
      dataRecordValueTransformer,
      keyTransformD1(userIdFeature)
    )
  }

  @Provides
  @Singleton
  @Named(TweetCountryEngagementCache)
  def providesTweetCountryEngagementCache(
    @Named(TimelinesRealTimeAggregateClient) client: Memcache
  ): ReadCache[(Long, String), ml.DataRecord] = {

    new KeyValueTransformingReadCache(
      client,
      dataRecordValueTransformer,
      keyTransformD1T1(tweetIdFeature, countryCodeFeature)
    )
  }

  @Provides
  @Singleton
  @Named(TweetEngagementCache)
  def providesTweetEngagementCache(
    @Named(TimelinesRealTimeAggregateClient) client: Memcache
  ): ReadCache[Long, ml.DataRecord] = {
    new KeyValueTransformingReadCache(
      client,
      dataRecordValueTransformer,
      keyTransformD1(tweetIdFeature)
    )
  }

  @Provides
  @Singleton
  @Named(EngagementsReceivedByAuthorCache)
  def providesEngagementsReceivedByAuthorCache(
    @Named(TimelinesRealTimeAggregateClient) client: Memcache
  ): ReadCache[Long, ml.DataRecord] = {
    new KeyValueTransformingReadCache(
      client,
      dataRecordValueTransformer,
      keyTransformD1(authorIdFeature)
    )
  }

  @Provides
  @Singleton
  @Named(TopicCountryEngagementCache)
  def providesTopicCountryEngagementCache(
    @Named(TimelinesRealTimeAggregateClient) client: Memcache
  ): ReadCache[(Long, String), ml.DataRecord] = {
    new KeyValueTransformingReadCache(
      client,
      dataRecordValueTransformer,
      keyTransformD1T1(topicIdFeature, countryCodeFeature)
    )
  }

  @Provides
  @Singleton
  @Named(RealTimeInteractionGraphUserVertexCache)
  def providesRealTimeInteractionGraphUserVertexCache(
    @Named(RealTimeInteractionGraphUserVertexClient) client: Memcache
  ): ReadCache[Long, ig.UserVertex] = {

    val valueTransformer = BinaryScalaCodec(ig.UserVertex).toByteArrayTransformer()

    val underlyingKey: Long => String = {
      val cacheKeyPrefix = "user_vertex"
      val defaultBatchID = Batcher.unit.currentBatch
      val batchPairInjection = BatchPairImplicits.keyInjection(Injection.connect[Long, Array[Byte]])
      MemcacheHelper
        .keyEncoder(cacheKeyPrefix)(batchPairInjection)
        .compose((k: Long) => (k, defaultBatchID))
    }

    new KeyValueTransformingReadCache(
      client,
      valueTransformer,
      underlyingKey
    )
  }
}

trait RealtimeAggregateHelpers {

  private def customKeyBuilder[K](prefix: String, f: K => Array[Byte]): K => String = {
    // intentionally not implementing injection inverse because it is never used
    def g(arr: Array[Byte]) = ???

    MemcacheHelper.keyEncoder(prefix)(Injection.build(f)(g))
  }

  private val keyEncoder: AggregationKey => String = {
    val cacheKeyPrefix = ""
    val defaultBatchID = Batcher.unit.currentBatch

    val batchPairInjection = BatchPairImplicits.keyInjection(AggregationKeyInjection)
    customKeyBuilder(cacheKeyPrefix, batchPairInjection)
      .compose((k: AggregationKey) => (k, defaultBatchID))
  }

  protected def keyTransformD1(f1: Long)(key: Long): String = {
    val aggregationKey = AggregationKey(Map(f1 -> key), Map.empty)
    keyEncoder(aggregationKey)
  }

  protected def keyTransformD2(f1: Long, f2: Long)(keys: (Long, Long)): String = {
    val (k1, k2) = keys
    val aggregationKey = AggregationKey(Map(f1 -> k1, f2 -> k2), Map.empty)
    keyEncoder(aggregationKey)
  }

  protected def keyTransformD1T1(f1: Long, f2: Long)(keys: (Long, String)): String = {
    val (k1, k2) = keys
    val aggregationKey = AggregationKey(Map(f1 -> k1), Map(f2 -> k2))
    keyEncoder(aggregationKey)
  }

  protected val dataRecordValueTransformer: Transformer[DataRecord, Array[Byte]] = ThriftCodec
    .toCompact[ml.DataRecord]
    .toByteArrayTransformer()
}
