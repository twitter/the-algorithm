package com.twitter.cr_mitwitterer.module.similarity_engine

import com.google.inject.Provides
import com.twitter.cr_mitwitterer.config.TimeoutConfig
import com.twitter.cr_mitwitterer.param.decider.CrMitwittererDecider
import com.twitter.cr_mitwitterer.param.decider.DeciderConstants
import com.twitter.cr_mitwitterer.similarity_engine.EarlybirdModelBasedSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.EarlybirdRecencyBasedSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.EarlybirdSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.EarlybirdTensorflowBasedSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.DeciderConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.GatingConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.twitter.cr_mitwitterer.thriftscala.SimilarityEngineType
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import javatwitter.inject.Singleton

object EarlybirdSimilarityEngineModule etwittertends TwitterModule {

  @Provides
  @Singleton
  def providesRecencyBasedEarlybirdSimilarityEngine(
    earlybirdRecencyBasedSimilarityEngine: EarlybirdRecencyBasedSimilarityEngine,
    timeoutConfig: TimeoutConfig,
    decider: CrMitwittererDecider,
    statsReceiver: StatsReceiver
  ): EarlybirdSimilarityEngine[
    EarlybirdRecencyBasedSimilarityEngine.EarlybirdRecencyBasedSearchQuery,
    EarlybirdRecencyBasedSimilarityEngine
  ] = {
    new EarlybirdSimilarityEngine[
      EarlybirdRecencyBasedSimilarityEngine.EarlybirdRecencyBasedSearchQuery,
      EarlybirdRecencyBasedSimilarityEngine
    ](
      implementingStore = earlybirdRecencyBasedSimilarityEngine,
      identifier = SimilarityEngineType.EarlybirdRecencyBasedSimilarityEngine,
      globalStats =
        statsReceiver.scope(SimilarityEngineType.EarlybirdRecencyBasedSimilarityEngine.name),
      engineConfig = SimilarityEngineConfig(
        timeout = timeoutConfig.earlybirdSimilarityEngineTimeout,
        gatingConfig = GatingConfig(
          deciderConfig = Some(
            DeciderConfig(
              decider = decider,
              deciderString = DeciderConstants.enableEarlybirdTrafficDeciderKey
            )),
          enableFeatureSwitch = None
        )
      )
    )
  }

  @Provides
  @Singleton
  def providesModelBasedEarlybirdSimilarityEngine(
    earlybirdModelBasedSimilarityEngine: EarlybirdModelBasedSimilarityEngine,
    timeoutConfig: TimeoutConfig,
    decider: CrMitwittererDecider,
    statsReceiver: StatsReceiver
  ): EarlybirdSimilarityEngine[
    EarlybirdModelBasedSimilarityEngine.EarlybirdModelBasedSearchQuery,
    EarlybirdModelBasedSimilarityEngine
  ] = {
    new EarlybirdSimilarityEngine[
      EarlybirdModelBasedSimilarityEngine.EarlybirdModelBasedSearchQuery,
      EarlybirdModelBasedSimilarityEngine
    ](
      implementingStore = earlybirdModelBasedSimilarityEngine,
      identifier = SimilarityEngineType.EarlybirdModelBasedSimilarityEngine,
      globalStats =
        statsReceiver.scope(SimilarityEngineType.EarlybirdModelBasedSimilarityEngine.name),
      engineConfig = SimilarityEngineConfig(
        timeout = timeoutConfig.earlybirdSimilarityEngineTimeout,
        gatingConfig = GatingConfig(
          deciderConfig = Some(
            DeciderConfig(
              decider = decider,
              deciderString = DeciderConstants.enableEarlybirdTrafficDeciderKey
            )),
          enableFeatureSwitch = None
        )
      )
    )
  }

  @Provides
  @Singleton
  def providesTensorflowBasedEarlybirdSimilarityEngine(
    earlybirdTensorflowBasedSimilarityEngine: EarlybirdTensorflowBasedSimilarityEngine,
    timeoutConfig: TimeoutConfig,
    decider: CrMitwittererDecider,
    statsReceiver: StatsReceiver
  ): EarlybirdSimilarityEngine[
    EarlybirdTensorflowBasedSimilarityEngine.EarlybirdTensorflowBasedSearchQuery,
    EarlybirdTensorflowBasedSimilarityEngine
  ] = {
    new EarlybirdSimilarityEngine[
      EarlybirdTensorflowBasedSimilarityEngine.EarlybirdTensorflowBasedSearchQuery,
      EarlybirdTensorflowBasedSimilarityEngine
    ](
      implementingStore = earlybirdTensorflowBasedSimilarityEngine,
      identifier = SimilarityEngineType.EarlybirdTensorflowBasedSimilarityEngine,
      globalStats =
        statsReceiver.scope(SimilarityEngineType.EarlybirdTensorflowBasedSimilarityEngine.name),
      engineConfig = SimilarityEngineConfig(
        timeout = timeoutConfig.earlybirdSimilarityEngineTimeout,
        gatingConfig = GatingConfig(
          deciderConfig = Some(
            DeciderConfig(
              decider = decider,
              deciderString = DeciderConstants.enableEarlybirdTrafficDeciderKey
            )),
          enableFeatureSwitch = None
        )
      )
    )
  }

}
