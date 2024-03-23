package com.ExTwitter.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.ExTwitter.app.Flag
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.frigate.common.store.strato.StratoFetchableStore
import com.ExTwitter.hermit.store.common.ObservedReadableStore
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.simclusters_v2.common.UserId
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.strato.client.{Client => StratoClient}
import com.ExTwitter.simclusters_v2.thriftscala.OrderedClustersAndMembers
import javax.inject.Named

object TwiceClustersMembersStoreModule extends ExTwitterModule {

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
