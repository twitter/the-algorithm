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
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.cr_mitwitterer.config.TimeoutConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.GatingConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.twitter.cr_mitwitterer.thriftscala.SimilarityEngineType

object ConsumerEmbeddingBasedTwHINSimilarityEngineModule etwittertends TwitterModule {
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
