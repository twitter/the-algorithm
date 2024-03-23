package com.ExTwitter.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.cr_mixer.param.decider.CrMixerDecider
import com.ExTwitter.cr_mixer.source_signal.FrsStore
import com.ExTwitter.cr_mixer.source_signal.FrsStore.FrsQueryResult
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.follow_recommendations.thriftscala.FollowRecommendationsThriftService
import com.ExTwitter.hermit.store.common.ObservedReadableStore
import com.ExTwitter.storehaus.ReadableStore
import javax.inject.Named

object FrsStoreModule extends ExTwitterModule {

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
