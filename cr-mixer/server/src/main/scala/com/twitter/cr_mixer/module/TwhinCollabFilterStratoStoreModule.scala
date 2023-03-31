package com.twitter.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.twitter.inject.TwitterModule
import com.twitter.cr_mixer.model.ModuleNames
import com.twitter.frigate.common.store.strato.StratoFetchableStore
import com.twitter.cr_mixer.similarity_engine.TwhinCollabFilterSimilarityEngine.TwhinCollabFilterView
import com.twitter.strato.client.{Client => StratoClient}
import com.twitter.simclusters_v2.common.TweetId
import com.twitter.storehaus.ReadableStore
import javax.inject.Named

object TwhinCollabFilterStratoStoreModule extends TwitterModule {

  val stratoColumnPath: String = "cuad/twhin/getCollabFilterTweetCandidatesProd.User"

  @Provides
  @Singleton
  @Named(ModuleNames.TwhinCollabFilterStratoStoreForFollow)
  def providesTwhinCollabFilterStratoStoreForFollow(
    stratoClient: StratoClient
  ): ReadableStore[Long, Seq[TweetId]] = {
    StratoFetchableStore.withView[Long, TwhinCollabFilterView, Seq[TweetId]](
      stratoClient,
      column = stratoColumnPath,
      view = TwhinCollabFilterView("follow_2022_03_10_c_500K")
    )
  }

  @Provides
  @Singleton
  @Named(ModuleNames.TwhinCollabFilterStratoStoreForEngagement)
  def providesTwhinCollabFilterStratoStoreForEngagement(
    stratoClient: StratoClient
  ): ReadableStore[Long, Seq[TweetId]] = {
    StratoFetchableStore.withView[Long, TwhinCollabFilterView, Seq[TweetId]](
      stratoClient,
      column = stratoColumnPath,
      view = TwhinCollabFilterView("engagement_2022_04_10_c_500K"))
  }

  @Provides
  @Singleton
  @Named(ModuleNames.TwhinMultiClusterStratoStoreForFollow)
  def providesTwhinMultiClusterStratoStoreForFollow(
    stratoClient: StratoClient
  ): ReadableStore[Long, Seq[TweetId]] = {
    StratoFetchableStore.withView[Long, TwhinCollabFilterView, Seq[TweetId]](
      stratoClient,
      column = stratoColumnPath,
      view = TwhinCollabFilterView("multiclusterFollow20220921")
    )
  }

  @Provides
  @Singleton
  @Named(ModuleNames.TwhinMultiClusterStratoStoreForEngagement)
  def providesTwhinMultiClusterStratoStoreForEngagement(
    stratoClient: StratoClient
  ): ReadableStore[Long, Seq[TweetId]] = {
    StratoFetchableStore.withView[Long, TwhinCollabFilterView, Seq[TweetId]](
      stratoClient,
      column = stratoColumnPath,
      view = TwhinCollabFilterView("multiclusterEng20220921"))
  }
}
