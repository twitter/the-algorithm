package com.X.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.X.cr_mixer.model.ModuleNames
import com.X.cr_mixer.param.decider.CrMixerDecider
import com.X.cr_mixer.source_signal.FrsStore
import com.X.cr_mixer.source_signal.FrsStore.FrsQueryResult
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.follow_recommendations.thriftscala.FollowRecommendationsThriftService
import com.X.hermit.store.common.ObservedReadableStore
import com.X.storehaus.ReadableStore
import javax.inject.Named

object FrsStoreModule extends XModule {

  @Provides
  @Singleton
  @Named(ModuleNames.FrsStore)
  def providesFrsStore(
    frsClient: FollowRecommendationsThriftService.MethodPerEndpoint,
    statsReceiver: StatsReceiver,
    decider: CrMixerDecider
  ): ReadableStore[FrsStore.Query, Seq[FrsQueryResult]] = {
    ObservedReadableStore(FrsStore(frsClient, statsReceiver, decider))(
      statsReceiver.scope("follow_recommendations_store"))
  }
}
