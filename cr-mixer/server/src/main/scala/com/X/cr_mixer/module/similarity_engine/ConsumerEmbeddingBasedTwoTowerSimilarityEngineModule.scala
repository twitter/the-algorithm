package com.X.cr_mixer.module
package similarity_engine

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

object ConsumerEmbeddingBasedTwoTowerSimilarityEngineModule extends XModule {
  @Provides
  @Named(ModuleNames.ConsumerEmbeddingBasedTwoTowerANNSimilarityEngine)
  def providesConsumerEmbeddingBasedTwoTowerANNSimilarityEngine(
    @Named(EmbeddingStoreModule.TwoTowerFavConsumerEmbeddingMhStoreName)
    twoTowerFavConsumerEmbeddingMhStore: ReadableStore[InternalId, api.Embedding],
    @Named(AnnQueryServiceClientModule.TwoTowerFavAnnServiceClientName)
    twoTowerFavAnnService: AnnQueryService.MethodPerEndpoint,
    timeoutConfig: TimeoutConfig,
    statsReceiver: StatsReceiver
  ): HnswANNSimilarityEngine = {
    new HnswANNSimilarityEngine(
      embeddingStoreLookUpMap = Map(
        ModelConfig.TwoTowerFavALL20220808 -> twoTowerFavConsumerEmbeddingMhStore,
      ),
      annServiceLookUpMap = Map(
        ModelConfig.TwoTowerFavALL20220808 -> twoTowerFavAnnService,
      ),
      globalStats = statsReceiver,
      identifier = SimilarityEngineType.ConsumerEmbeddingBasedTwoTowerANN,
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
