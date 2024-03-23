package com.ExTwitter.cr_mixer.module.similarity_engine

import com.google.inject.Provides
import com.ExTwitter.cr_mixer.config.TimeoutConfig
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.cr_mixer.model.TweetWithScore
import com.ExTwitter.cr_mixer.similarity_engine.ConsumerBasedWalsSimilarityEngine
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.ExTwitter.cr_mixer.similarity_engine.StandardSimilarityEngine
import com.ExTwitter.cr_mixer.thriftscala.SimilarityEngineType
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import io.grpc.ManagedChannel
import javax.inject.Named

object ConsumerBasedWalsSimilarityEngineModule extends ExTwitterModule {
  @Provides
  @Named(ModuleNames.ConsumerBasedWalsSimilarityEngine)
  def providesConsumerBasedWalsSimilarityEngine(
    timeoutConfig: TimeoutConfig,
    statsReceiver: StatsReceiver,
    @Named(ModuleNames.HomeNaviGRPCClient) homeNaviGRPCClient: ManagedChannel,
    @Named(ModuleNames.AdsFavedNaviGRPCClient) adsFavedNaviGRPCClient: ManagedChannel,
    @Named(ModuleNames.AdsMonetizableNaviGRPCClient) adsMonetizableNaviGRPCClient: ManagedChannel,
  ): StandardSimilarityEngine[
    ConsumerBasedWalsSimilarityEngine.Query,
    TweetWithScore
  ] = {

    val underlyingStore = new ConsumerBasedWalsSimilarityEngine(
      homeNaviGRPCClient,
      adsFavedNaviGRPCClient,
      adsMonetizableNaviGRPCClient,
      statsReceiver
    )

    new StandardSimilarityEngine[
      ConsumerBasedWalsSimilarityEngine.Query,
      TweetWithScore
    ](
      implementingStore = underlyingStore,
      identifier = SimilarityEngineType.ConsumerBasedWalsANN,
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
}
