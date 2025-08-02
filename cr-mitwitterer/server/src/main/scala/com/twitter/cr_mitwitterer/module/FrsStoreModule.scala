package com.twitter.cr_mitwitterer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.cr_mitwitterer.param.decider.CrMitwittererDecider
import com.twitter.cr_mitwitterer.source_signal.FrsStore
import com.twitter.cr_mitwitterer.source_signal.FrsStore.FrsQueryResult
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.follow_recommendations.thriftscala.FollowRecommendationsThriftService
import com.twitter.hermit.store.common.ObservedReadableStore
import com.twitter.storehaus.ReadableStore
import javatwitter.inject.Named

object FrsStoreModule etwittertends TwitterModule {

  @Provides
  @Singleton
  @Named(ModuleNames.FrsStore)
  def providesFrsStore(
    frsClient: FollowRecommendationsThriftService.MethodPerEndpoint,
    statsReceiver: StatsReceiver,
    decider: CrMitwittererDecider
  ): ReadableStore[FrsStore.Query, Seq[FrsQueryResult]] = {
    ObservedReadableStore(FrsStore(frsClient, statsReceiver, decider))(
      statsReceiver.scope("follow_recommendations_store"))
  }
}
