package com.twitter.cr_mitwitterer.module.similarity_engine

import com.google.inject.Provides
import com.twitter.cr_mitwitterer.config.TimeoutConfig
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.cr_mitwitterer.model.TweetWithScore
import com.twitter.cr_mitwitterer.similarity_engine.ConsumerBasedWalsSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.GatingConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.twitter.cr_mitwitterer.similarity_engine.StandardSimilarityEngine
import com.twitter.cr_mitwitterer.thriftscala.SimilarityEngineType
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import io.grpc.ManagedChannel
import javatwitter.inject.Named

object ConsumerBasedWalsSimilarityEngineModule etwittertends TwitterModule {
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
