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
import com.X.cr_mixer.model.ModuleNames
import com.X.cr_mixer.config.TimeoutConfig
import com.X.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.X.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.X.cr_mixer.thriftscala.SimilarityEngineType

object ConsumerEmbeddingBasedTwHINSimilarityEngineModule extends XModule {
  @Provides
  @Named(ModuleNames.ConsumerEmbeddingBasedTwHINANNSimilarityEngine)
  def providesConsumerEmbeddingBasedTwHINANNSimilarityEngine(
    // MH stores
    @Named(EmbeddingStoreModule.ConsumerBasedTwHINEmbeddingRegularUpdateMhStoreName)
    consumerBasedTwHINEmbeddingRegularUpdateMhStore: ReadableStore[InternalId, api.Embedding],
    @Named(EmbeddingStoreModule.DebuggerDemoUserEmbeddingMhStoreName)
    debuggerDemoUserEmbeddingMhStore: ReadableStore[InternalId, api.Embedding],
    @Named(AnnQueryServiceClientModule.TwHINRegularUpdateAnnServiceClientName)
    twHINRegularUpdateAnnService: AnnQueryService.MethodPerEndpoint,
    @Named(AnnQueryServiceClientModule.DebuggerDemoAnnServiceClientName)
    debuggerDemoAnnService: AnnQueryService.MethodPerEndpoint,
    // Other configs
    timeoutConfig: TimeoutConfig,
    statsReceiver: StatsReceiver
  ): HnswANNSimilarityEngine = {
    new HnswANNSimilarityEngine(
      embeddingStoreLookUpMap = Map(
        ModelConfig.ConsumerBasedTwHINRegularUpdateAll20221024 -> consumerBasedTwHINEmbeddingRegularUpdateMhStore,
        ModelConfig.DebuggerDemo -> debuggerDemoUserEmbeddingMhStore,
      ),
      annServiceLookUpMap = Map(
        ModelConfig.ConsumerBasedTwHINRegularUpdateAll20221024 -> twHINRegularUpdateAnnService,
        ModelConfig.DebuggerDemo -> debuggerDemoAnnService,
      ),
      globalStats = statsReceiver,
      identifier = SimilarityEngineType.ConsumerEmbeddingBasedTwHINANN,
      engineConfig = SimilarityEngineConfig(
        timeout = timeoutConfig.similarityEngineTimeout,
        gatingConfig = GatingConfig(
          deciderConfig = None,
          enableFeatureSwitch = None
        )
      )
    )
  }
}
