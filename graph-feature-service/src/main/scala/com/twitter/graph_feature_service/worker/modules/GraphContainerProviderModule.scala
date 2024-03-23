package com.ExTwitter.graph_feature_service.worker.modules

import com.google.inject.Provides
import com.ExTwitter.concurrent.AsyncSemaphore
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.graph_feature_service.common.Configs._
import com.ExTwitter.graph_feature_service.worker.util
import com.ExTwitter.graph_feature_service.worker.util.AutoUpdatingGraph
import com.ExTwitter.graph_feature_service.worker.util.FollowedByPartialValueGraph
import com.ExTwitter.graph_feature_service.worker.util.FollowingPartialValueGraph
import com.ExTwitter.graph_feature_service.worker.util.GraphContainer
import com.ExTwitter.graph_feature_service.worker.util.GraphKey
import com.ExTwitter.graph_feature_service.worker.util.MutualFollowPartialValueGraph
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.inject.annotations.Flag
import com.ExTwitter.util.Timer
import javax.inject.Singleton

object GraphContainerProviderModule extends ExTwitterModule {

  @Provides
  @Singleton
  def provideAutoUpdatingGraphs(
    @Flag(WorkerFlagNames.HdfsCluster) hdfsCluster: String,
    @Flag(WorkerFlagNames.HdfsClusterUrl) hdfsClusterUrl: String,
    @Flag(WorkerFlagNames.ShardId) shardId: Int
  )(
    implicit statsReceiver: StatsReceiver,
    timer: Timer
  ): GraphContainer = {

    // NOTE that we do not load some the graphs for saving RAM at this moment.
    val enabledGraphPaths: Map[GraphKey, String] =
      Map(
        FollowingPartialValueGraph -> FollowOutValPath,
        FollowedByPartialValueGraph -> FollowInValPath
      )

    // Only allow one graph to update at the same time.
    val sharedSemaphore = new AsyncSemaphore(1)

    val graphs: Map[GraphKey, AutoUpdatingGraph] =
      enabledGraphPaths.map {
        case (graphKey, path) =>
          graphKey -> AutoUpdatingGraph(
            dataPath = getHdfsPath(path),
            hdfsCluster = hdfsCluster,
            hdfsClusterUrl = hdfsClusterUrl,
            shard = shardId,
            minimumSizeForCompleteGraph = 1e6.toLong,
            sharedSemaphore = Some(sharedSemaphore)
          )(
            statsReceiver
              .scope("graphs")
              .scope(graphKey.getClass.getSimpleName),
            timer
          )
      }

    util.GraphContainer(graphs)
  }
}
