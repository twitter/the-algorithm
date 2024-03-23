package com.ExTwitter.cr_mixer.module.similarity_engine

import com.google.inject.Provides
import com.ExTwitter.ann.common.thriftscala.AnnQueryService
import com.ExTwitter.cr_mixer.model.ModelConfig
import com.ExTwitter.cr_mixer.module.EmbeddingStoreModule
import com.ExTwitter.cr_mixer.module.thrift_client.AnnQueryServiceClientModule
import com.ExTwitter.cr_mixer.similarity_engine.HnswANNSimilarityEngine
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.simclusters_v2.thriftscala.InternalId
import com.ExTwitter.storehaus.ReadableStore
import javax.inject.Named
import com.ExTwitter.ml.api.{thriftscala => api}
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.cr_mixer.config.TimeoutConfig
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.ExTwitter.cr_mixer.thriftscala.SimilarityEngineType

object ConsumerEmbeddingBasedTwHINSimilarityEngineModule extends ExTwitterModule {
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
