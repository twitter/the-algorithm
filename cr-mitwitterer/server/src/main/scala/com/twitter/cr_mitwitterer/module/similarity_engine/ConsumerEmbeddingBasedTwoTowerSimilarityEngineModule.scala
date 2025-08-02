package com.twitter.cr_mitwitterer.module
package similarity_engine

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

object ConsumerEmbeddingBasedTwoTowerSimilarityEngineModule etwittertends TwitterModule {
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
