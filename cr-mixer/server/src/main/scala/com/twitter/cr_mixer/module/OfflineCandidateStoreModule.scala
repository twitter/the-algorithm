package com.twitter.cr_mixer.module

import com.google.inject.Provides
import com.twitter.bijection.Injection
import com.twitter.bijection.scrooge.CompactScalaCodec
import com.twitter.cr_mixer.model.ModuleNames
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.inject.TwitterModule
import com.twitter.simclusters_v2.thriftscala.CandidateTweetsList
import com.twitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.twitter.storehaus.ReadableStore
import com.twitter.storehaus_internal.manhattan.Apollo
import com.twitter.storehaus_internal.manhattan.ManhattanRO
import com.twitter.storehaus_internal.manhattan.ManhattanROConfig
import com.twitter.storehaus_internal.util.ApplicationID
import com.twitter.storehaus_internal.util.DatasetName
import com.twitter.storehaus_internal.util.HDFSPath
import javax.inject.Named
import javax.inject.Singleton

object OfflineCandidateStoreModule extends TwitterModule {
  type UserId = Long
  implicit val tweetCandidatesInjection: Injection[CandidateTweetsList, Array[Byte]] =
    CompactScalaCodec(CandidateTweetsList)

  @Provides
  @Singleton
  @Named(ModuleNames.OfflineTweet2020CandidateStore)
  def offlineTweet2020CandidateMhStore(
    serviceIdentifier: ServiceIdentifier
  ): ReadableStore[UserId, CandidateTweetsList] = {
    buildOfflineCandidateStore(
      serviceIdentifier,
      datasetName = "offline_tweet_recommendations_from_interestedin_2020"
    )
  }

  @Provides
  @Singleton
  @Named(ModuleNames.OfflineTweet2020Hl0El15CandidateStore)
  def offlineTweet2020Hl0El15CandidateMhStore(
    serviceIdentifier: ServiceIdentifier
  ): ReadableStore[UserId, CandidateTweetsList] = {
    buildOfflineCandidateStore(
      serviceIdentifier,
      datasetName = "offline_tweet_recommendations_from_interestedin_2020_hl_0_el_15"
    )
  }

  @Provides
  @Singleton
  @Named(ModuleNames.OfflineTweet2020Hl2El15CandidateStore)
  def offlineTweet2020Hl2El15CandidateMhStore(
    serviceIdentifier: ServiceIdentifier
  ): ReadableStore[UserId, CandidateTweetsList] = {
    buildOfflineCandidateStore(
      serviceIdentifier,
      datasetName = "offline_tweet_recommendations_from_interestedin_2020_hl_2_el_15"
    )
  }

  @Provides
  @Singleton
  @Named(ModuleNames.OfflineTweet2020Hl2El50CandidateStore)
  def offlineTweet2020Hl2El50CandidateMhStore(
    serviceIdentifier: ServiceIdentifier
  ): ReadableStore[UserId, CandidateTweetsList] = {
    buildOfflineCandidateStore(
      serviceIdentifier,
      datasetName = "offline_tweet_recommendations_from_interestedin_2020_hl_2_el_50"
    )
  }

  @Provides
  @Singleton
  @Named(ModuleNames.OfflineTweet2020Hl8El50CandidateStore)
  def offlineTweet2020Hl8El50CandidateMhStore(
    serviceIdentifier: ServiceIdentifier
  ): ReadableStore[UserId, CandidateTweetsList] = {
    buildOfflineCandidateStore(
      serviceIdentifier,
      datasetName = "offline_tweet_recommendations_from_interestedin_2020_hl_8_el_50"
    )
  }

  @Provides
  @Singleton
  @Named(ModuleNames.OfflineTweetMTSCandidateStore)
  def offlineTweetMTSCandidateMhStore(
    serviceIdentifier: ServiceIdentifier
  ): ReadableStore[UserId, CandidateTweetsList] = {
    buildOfflineCandidateStore(
      serviceIdentifier,
      datasetName = "offline_tweet_recommendations_from_mts_consumer_embeddings"
    )
  }

  @Provides
  @Singleton
  @Named(ModuleNames.OfflineFavDecayedSumCandidateStore)
  def offlineFavDecayedSumCandidateStore(
    serviceIdentifier: ServiceIdentifier
  ): ReadableStore[UserId, CandidateTweetsList] = {
    buildOfflineCandidateStore(
      serviceIdentifier,
      datasetName = "offline_tweet_recommendations_from_decayed_sum"
    )
  }

  @Provides
  @Singleton
  @Named(ModuleNames.OfflineFtrAt5Pop1000RankDecay11CandidateStore)
  def offlineFtrAt5Pop1000RankDecay11CandidateStore(
    serviceIdentifier: ServiceIdentifier
  ): ReadableStore[UserId, CandidateTweetsList] = {
    buildOfflineCandidateStore(
      serviceIdentifier,
      datasetName = "offline_tweet_recommendations_from_ftrat5_pop1000_rank_decay_1_1"
    )
  }

  @Provides
  @Singleton
  @Named(ModuleNames.OfflineFtrAt5Pop10000RankDecay11CandidateStore)
  def offlineFtrAt5Pop10000RankDecay11CandidateStore(
    serviceIdentifier: ServiceIdentifier
  ): ReadableStore[UserId, CandidateTweetsList] = {
    buildOfflineCandidateStore(
      serviceIdentifier,
      datasetName = "offline_tweet_recommendations_from_ftrat5_pop10000_rank_decay_1_1"
    )
  }

  private def buildOfflineCandidateStore(
    serviceIdentifier: ServiceIdentifier,
    datasetName: String
  ): ReadableStore[UserId, CandidateTweetsList] = {
    ManhattanRO
      .getReadableStoreWithMtls[Long, CandidateTweetsList](
        ManhattanROConfig(
          HDFSPath(""), // not needed
          ApplicationID("multi_type_simclusters"),
          DatasetName(datasetName),
          Apollo
        ),
        ManhattanKVClientMtlsParams(serviceIdentifier)
      )
  }

}
