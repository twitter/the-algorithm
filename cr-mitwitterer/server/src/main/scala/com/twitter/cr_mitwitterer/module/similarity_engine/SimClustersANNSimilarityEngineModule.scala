package com.twitter.cr_mitwitterer.module.similarity_engine

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.cr_mitwitterer.model.TweetWithScore
import com.twitter.cr_mitwitterer.config.TimeoutConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimClustersANNSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.SimClustersANNSimilarityEngine.Query
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.GatingConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.twitter.cr_mitwitterer.similarity_engine.StandardSimilarityEngine
import com.twitter.cr_mitwitterer.thriftscala.SimilarityEngineType
import com.twitter.finagle.memcached.{Client => MemcachedClient}
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.hashing.KeyHasher
import com.twitter.hermit.store.common.ObservedMemcachedReadableStore
import com.twitter.hermit.store.common.ObservedReadableStore
import com.twitter.inject.TwitterModule
import com.twitter.relevance_platform.common.injection.LZ4Injection
import com.twitter.relevance_platform.common.injection.SeqObjectInjection
import com.twitter.simclusters_v2.candidate_source.SimClustersANNCandidateSource.CacheableShortTTLEmbeddingTypes
import com.twitter.simclustersann.thriftscala.SimClustersANNService
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future
import javatwitter.inject.Named
import javatwitter.inject.Singleton

object SimClustersANNSimilarityEngineModule etwittertends TwitterModule {

  private val keyHasher: KeyHasher = KeyHasher.FNV1A_64

  @Provides
  @Singleton
  @Named(ModuleNames.SimClustersANNSimilarityEngine)
  def providesProdSimClustersANNSimilarityEngine(
    @Named(ModuleNames.UnifiedCache) crMitwittererUnifiedCacheClient: MemcachedClient,
    simClustersANNServiceNameToClientMapper: Map[String, SimClustersANNService.MethodPerEndpoint],
    timeoutConfig: TimeoutConfig,
    statsReceiver: StatsReceiver
  ): StandardSimilarityEngine[Query, TweetWithScore] = {

    val underlyingStore =
      SimClustersANNSimilarityEngine(simClustersANNServiceNameToClientMapper, statsReceiver)

    val observedReadableStore =
      ObservedReadableStore(underlyingStore)(statsReceiver.scope("SimClustersANNServiceStore"))

    val memCachedStore: ReadableStore[Query, Seq[TweetWithScore]] =
      ObservedMemcachedReadableStore
        .fromCacheClient(
          backingStore = observedReadableStore,
          cacheClient = crMitwittererUnifiedCacheClient,
          ttl = 10.minutes
        )(
          valueInjection = LZ4Injection.compose(SeqObjectInjection[TweetWithScore]()),
          statsReceiver = statsReceiver.scope("simclusters_ann_store_memcache"),
          keyToString = { k =>
            //Etwitterample Query CRMitwitterer:SCANN:1:2:1234567890ABCDEF:1234567890ABCDEF
            f"CRMitwitterer:SCANN:${k.simClustersANNQuery.sourceEmbeddingId.embeddingType.getValue()}%twitter" +
              f":${k.simClustersANNQuery.sourceEmbeddingId.modelVersion.getValue()}%twitter" +
              f":${keyHasher.hashKey(k.simClustersANNQuery.sourceEmbeddingId.internalId.toString.getBytes)}%twitter" +
              f":${keyHasher.hashKey(k.simClustersANNQuery.config.toString.getBytes)}%twitter"
          }
        )

    // Only cache the candidates if it's not Consumer-source. For etwitterample, TweetSource,
    // ProducerSource, TopicSource
    val wrapperStats = statsReceiver.scope("SimClustersANNWrapperStore")

    val wrapperStore: ReadableStore[Query, Seq[TweetWithScore]] =
      buildWrapperStore(memCachedStore, observedReadableStore, wrapperStats)

    new StandardSimilarityEngine[
      Query,
      TweetWithScore
    ](
      implementingStore = wrapperStore,
      identifier = SimilarityEngineType.SimClustersANN,
      globalStats = statsReceiver,
      engineConfig = SimilarityEngineConfig(
        timeout = timeoutConfig.similarityEngineTimeout,
        gatingConfig = GatingConfig(
          deciderConfig = None,
          enableFeatureSwitch = None
        )
      )
    )
  }

  def buildWrapperStore(
    memCachedStore: ReadableStore[Query, Seq[TweetWithScore]],
    underlyingStore: ReadableStore[Query, Seq[TweetWithScore]],
    wrapperStats: StatsReceiver
  ): ReadableStore[Query, Seq[TweetWithScore]] = {

    // Only cache the candidates if it's not Consumer-source. For etwitterample, TweetSource,
    // ProducerSource, TopicSource
    val wrapperStore: ReadableStore[Query, Seq[TweetWithScore]] =
      new ReadableStore[Query, Seq[TweetWithScore]] {

        override def multiGet[K1 <: Query](
          queries: Set[K1]
        ): Map[K1, Future[Option[Seq[TweetWithScore]]]] = {
          val (cacheableQueries, nonCacheableQueries) =
            queries.partition { query =>
              CacheableShortTTLEmbeddingTypes.contains(
                query.simClustersANNQuery.sourceEmbeddingId.embeddingType)
            }
          memCachedStore.multiGet(cacheableQueries) ++
            underlyingStore.multiGet(nonCacheableQueries)
        }
      }
    wrapperStore
  }

}
