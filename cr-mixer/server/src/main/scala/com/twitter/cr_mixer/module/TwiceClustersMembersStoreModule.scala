package com.twitter.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.twitter.app.Flag
import com.twitter.cr_mixer.model.ModuleNames
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.store.strato.StratoFetchableStore
import com.twitter.hermit.store.common.ObservedReadableStore
import com.twitter.inject.TwitterModule
import com.twitter.simclusters_v2.common.UserId
import com.twitter.storehaus.ReadableStore
import com.twitter.strato.client.{Client => StratoClient}
import com.twitter.simclusters_v2.thriftscala.OrderedClustersAndMembers
import javax.inject.Named

object TwiceClustersMembersStoreModule extends TwitterModule {

  private val twiceClustersMembersColumnPath: Flag[String] = flag[String](
    name = "crMixer.twiceClustersMembersColumnPath",
    default =
      "recommendations/simclusters_v2/embeddings/TwiceClustersMembersLargestDimApeSimilarity",
    help = "Strato column path for TweetRecentEngagedUsersStore"
  )

  @Provides
  @Singleton
  @Named(ModuleNames.TwiceClustersMembersStore)
  def providesTweetRecentEngagedUserStore(
    statsReceiver: StatsReceiver,
    stratoClient: StratoClient,
  ): ReadableStore[UserId, OrderedClustersAndMembers] = {
    val twiceClustersMembersStratoFetchableStore = StratoFetchableStore
      .withUnitView[UserId, OrderedClustersAndMembers](
        stratoClient,
        twiceClustersMembersColumnPath())

    ObservedReadableStore(
      twiceClustersMembersStratoFetchableStore
    )(statsReceiver.scope("twice_clusters_members_largestDimApe_similarity_store"))
  }
}
