package com.ExTwitter.cr_mixer.module
package similarity_engine

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

object ConsumerEmbeddingBasedTwoTowerSimilarityEngineModule extends ExTwitterModule {
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
