package com.X.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.X.app.Flag
import com.X.cr_mixer.model.ModuleNames
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.store.strato.StratoFetchableStore
import com.X.hermit.store.common.ObservedReadableStore
import com.X.inject.XModule
import com.X.simclusters_v2.common.UserId
import com.X.storehaus.ReadableStore
import com.X.strato.client.{Client => StratoClient}
import com.X.simclusters_v2.thriftscala.OrderedClustersAndMembers
import javax.inject.Named

object TwiceClustersMembersStoreModule extends XModule {

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
