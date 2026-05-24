package com.twitter.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.twitter.cr_mixer.model.ModuleNames
import com.twitter.cr_mixer.param.decider.CrMixerDecider
import com.twitter.cr_mixer.source_signal.FrsStore
import com.twitter.cr_mixer.source_signal.FrsStore.FrsQueryResult
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.follow_recommendations.thriftscala.FollowRecommendationsThriftService
import com.twitter.hermit.store.common.ObservedReadableStore
import com.twitter.storehaus.ReadableStore
import javax.inject.Named

object FrsStoreModule extends TwitterModule {

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
