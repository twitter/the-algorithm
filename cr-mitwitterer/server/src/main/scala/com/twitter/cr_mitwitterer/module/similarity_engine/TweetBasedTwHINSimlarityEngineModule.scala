package com.twitter.cr_mitwitterer.module.similarity_engine
import com.google.inject.Provides
import com.twitter.ann.common.thriftscala.AnnQueryService
import com.twitter.cr_mitwitterer.model.ModelConfig
import com.twitter.cr_mitwitterer.module.EmbeddingStoreModule
import com.twitter.cr_mitwitterer.module.thrift_client.AnnQueryServiceClientModule
import com.twitter.cr_mitwitterer.similarity_engine.HnswANNSimilarityEngine
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.simclusters_v2.thriftscala.InternalId
import com.twitter.storehaus.ReadableStore
import javatwitter.inject.Named
import com.twitter.ml.api.{thriftscala => api}
import com.twitter.conversions.DurationOps._
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.cr_mitwitterer.config.TimeoutConfig
import com.twitter.cr_mitwitterer.similarity_engine.HnswANNEngineQuery
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.GatingConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.twitter.cr_mitwitterer.thriftscala.SimilarityEngineType
import com.twitter.finagle.memcached.{Client => MemcachedClient}

object TweetBasedTwHINSimlarityEngineModule etwittertends TwitterModule {
  @Provides
  @Named(ModuleNames.TweetBasedTwHINANNSimilarityEngine)
  def providesTweetBasedTwHINANNSimilarityEngine(
    // MH stores
    @Named(EmbeddingStoreModule.TwHINEmbeddingRegularUpdateMhStoreName)
    twHINEmbeddingRegularUpdateMhStore: ReadableStore[InternalId, api.Embedding],
    @Named(EmbeddingStoreModule.DebuggerDemoTweetEmbeddingMhStoreName)
    debuggerDemoTweetEmbeddingMhStore: ReadableStore[InternalId, api.Embedding],
    // ANN clients
    @Named(AnnQueryServiceClientModule.TwHINRegularUpdateAnnServiceClientName)
    twHINRegularUpdateAnnService: AnnQueryService.MethodPerEndpoint,
    @Named(AnnQueryServiceClientModule.DebuggerDemoAnnServiceClientName)
    debuggerDemoAnnService: AnnQueryService.MethodPerEndpoint,
    // Other configs
    @Named(ModuleNames.UnifiedCache) crMitwittererUnifiedCacheClient: MemcachedClient,
    timeoutConfig: TimeoutConfig,
    statsReceiver: StatsReceiver
  ): HnswANNSimilarityEngine = {
    new HnswANNSimilarityEngine(
      embeddingStoreLookUpMap = Map(
        ModelConfig.TweetBasedTwHINRegularUpdateAll20221024 -> twHINEmbeddingRegularUpdateMhStore,
        ModelConfig.DebuggerDemo -> debuggerDemoTweetEmbeddingMhStore,
      ),
      annServiceLookUpMap = Map(
        ModelConfig.TweetBasedTwHINRegularUpdateAll20221024 -> twHINRegularUpdateAnnService,
        ModelConfig.DebuggerDemo -> debuggerDemoAnnService,
      ),
      globalStats = statsReceiver,
      identifier = SimilarityEngineType.TweetBasedTwHINANN,
      engineConfig = SimilarityEngineConfig(
        timeout = timeoutConfig.similarityEngineTimeout,
        gatingConfig = GatingConfig(
          deciderConfig = None,
          enableFeatureSwitch = None
        )
      ),
      memCacheConfigOpt = Some(
        SimilarityEngine.MemCacheConfig[HnswANNEngineQuery](
          cacheClient = crMitwittererUnifiedCacheClient,
          ttl = 30.minutes,
          keyToString = (query: HnswANNEngineQuery) =>
            SimilarityEngine.keyHasher.hashKey(query.cacheKey.getBytes).toString
        ))
    )
  }
}
