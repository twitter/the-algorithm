package com.ExTwitter.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.frigate.common.store.strato.StratoFetchableStore
import com.ExTwitter.cr_mixer.similarity_engine.TwhinCollabFilterSimilarityEngine.TwhinCollabFilterView
import com.ExTwitter.strato.client.{Client => StratoClient}
import com.ExTwitter.simclusters_v2.common.TweetId
import com.ExTwitter.storehaus.ReadableStore
import javax.inject.Named

object TwhinCollabFilterStratoStoreModule extends ExTwitterModule {

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
