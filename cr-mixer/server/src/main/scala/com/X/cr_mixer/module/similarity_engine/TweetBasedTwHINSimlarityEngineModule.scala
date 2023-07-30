package com.X.cr_mixer.module.similarity_engine
import com.google.inject.Provides
import com.X.ann.common.thriftscala.AnnQueryService
import com.X.cr_mixer.model.ModelConfig
import com.X.cr_mixer.module.EmbeddingStoreModule
import com.X.cr_mixer.module.thrift_client.AnnQueryServiceClientModule
import com.X.cr_mixer.similarity_engine.HnswANNSimilarityEngine
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.simclusters_v2.thriftscala.InternalId
import com.X.storehaus.ReadableStore
import javax.inject.Named
import com.X.ml.api.{thriftscala => api}
import com.X.conversions.DurationOps._
import com.X.cr_mixer.model.ModuleNames
import com.X.cr_mixer.config.TimeoutConfig
import com.X.cr_mixer.similarity_engine.HnswANNEngineQuery
import com.X.cr_mixer.similarity_engine.SimilarityEngine
import com.X.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.X.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.X.cr_mixer.thriftscala.SimilarityEngineType
import com.X.finagle.memcached.{Client => MemcachedClient}

object TweetBasedTwHINSimlarityEngineModule extends XModule {
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
    @Named(ModuleNames.UnifiedCache) crMixerUnifiedCacheClient: MemcachedClient,
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
          cacheClient = crMixerUnifiedCacheClient,
          ttl = 30.minutes,
          keyToString = (query: HnswANNEngineQuery) =>
            SimilarityEngine.keyHasher.hashKey(query.cacheKey.getBytes).toString
        ))
    )
  }
}
