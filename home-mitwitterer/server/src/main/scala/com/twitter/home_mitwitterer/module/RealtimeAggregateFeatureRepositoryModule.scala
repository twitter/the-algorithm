package com.twitter.home_mitwitterer.module

import com.google.inject.Provides
import com.google.inject.name.Named
import com.twitter.bijection.Injection
import com.twitter.bijection.scrooge.BinaryScalaCodec
import com.twitter.bijection.thrift.ThriftCodec
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.EngagementsReceivedByAuthorCache
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.RealTimeInteractionGraphUserVertetwitterCache
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.RealTimeInteractionGraphUserVertetwitterClient
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.TimelinesRealTimeAggregateClient
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.TopicCountryEngagementCache
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.TopicEngagementCache
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.TweetCountryEngagementCache
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.TweetEngagementCache
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.TwitterListEngagementCache
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.UserAuthorEngagementCache
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.UserEngagementCache
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.UserTopicEngagementForNewUserCache
import com.twitter.home_mitwitterer.util.InjectionTransformerImplicits._
import com.twitter.inject.TwitterModule
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.Feature
import com.twitter.ml.{api => ml}
import com.twitter.servo.cache.KeyValueTransformingReadCache
import com.twitter.servo.cache.Memcache
import com.twitter.servo.cache.ReadCache
import com.twitter.servo.util.Transformer
import com.twitter.storehaus_internal.memcache.MemcacheHelper
import com.twitter.summingbird.batch.Batcher
import com.twitter.summingbird_internal.bijection.BatchPairImplicits
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregationKey
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregationKeyInjection
import com.twitter.wtf.real_time_interaction_graph.{thriftscala => ig}

import javatwitter.inject.Singleton

object RealtimeAggregateFeatureRepositoryModule
    etwittertends TwitterModule
    with RealtimeAggregateHelpers {

  private val authorIdFeature = new Feature.Discrete("entities.source_author_id").getFeatureId
  private val countryCodeFeature = new Feature.Tetwittert("geo.user_location.country_code").getFeatureId
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
  @Named(TwitterListEngagementCache)
  def providesTwitterListEngagementCache(
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
  @Named(RealTimeInteractionGraphUserVertetwitterCache)
  def providesRealTimeInteractionGraphUserVertetwitterCache(
    @Named(RealTimeInteractionGraphUserVertetwitterClient) client: Memcache
  ): ReadCache[Long, ig.UserVertetwitter] = {

    val valueTransformer = BinaryScalaCodec(ig.UserVertetwitter).toByteArrayTransformer()

    val underlyingKey: Long => String = {
      val cacheKeyPrefitwitter = "user_vertetwitter"
      val defaultBatchID = Batcher.unit.currentBatch
      val batchPairInjection = BatchPairImplicits.keyInjection(Injection.connect[Long, Array[Byte]])
      MemcacheHelper
        .keyEncoder(cacheKeyPrefitwitter)(batchPairInjection)
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

  private def customKeyBuilder[K](prefitwitter: String, f: K => Array[Byte]): K => String = {
    // intentionally not implementing injection inverse because it is never used
    def g(arr: Array[Byte]) = ???

    MemcacheHelper.keyEncoder(prefitwitter)(Injection.build(f)(g))
  }

  private val keyEncoder: AggregationKey => String = {
    val cacheKeyPrefitwitter = ""
    val defaultBatchID = Batcher.unit.currentBatch

    val batchPairInjection = BatchPairImplicits.keyInjection(AggregationKeyInjection)
    customKeyBuilder(cacheKeyPrefitwitter, batchPairInjection)
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
