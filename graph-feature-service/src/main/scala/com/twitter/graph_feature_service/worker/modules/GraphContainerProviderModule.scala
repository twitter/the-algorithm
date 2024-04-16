package com.tw ter.graph_feature_serv ce.worker.modules

 mport com.google. nject.Prov des
 mport com.tw ter.concurrent.AsyncSemaphore
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.graph_feature_serv ce.common.Conf gs._
 mport com.tw ter.graph_feature_serv ce.worker.ut l
 mport com.tw ter.graph_feature_serv ce.worker.ut l.AutoUpdat ngGraph
 mport com.tw ter.graph_feature_serv ce.worker.ut l.Follo dByPart alValueGraph
 mport com.tw ter.graph_feature_serv ce.worker.ut l.Follow ngPart alValueGraph
 mport com.tw ter.graph_feature_serv ce.worker.ut l.GraphConta ner
 mport com.tw ter.graph_feature_serv ce.worker.ut l.GraphKey
 mport com.tw ter.graph_feature_serv ce.worker.ut l.MutualFollowPart alValueGraph
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.ut l.T  r
 mport javax. nject.S ngleton

object GraphConta nerProv derModule extends Tw terModule {

  @Prov des
  @S ngleton
  def prov deAutoUpdat ngGraphs(
    @Flag(WorkerFlagNa s.HdfsCluster) hdfsCluster: Str ng,
    @Flag(WorkerFlagNa s.HdfsClusterUrl) hdfsClusterUrl: Str ng,
    @Flag(WorkerFlagNa s.Shard d) shard d:  nt
  )(
     mpl c  statsRece ver: StatsRece ver,
    t  r: T  r
  ): GraphConta ner = {

    // NOTE that   do not load so  t  graphs for sav ng RAM at t  mo nt.
    val enabledGraphPaths: Map[GraphKey, Str ng] =
      Map(
        Follow ngPart alValueGraph -> FollowOutValPath,
        Follo dByPart alValueGraph -> Follow nValPath
      )

    // Only allow one graph to update at t  sa  t  .
    val sharedSemaphore = new AsyncSemaphore(1)

    val graphs: Map[GraphKey, AutoUpdat ngGraph] =
      enabledGraphPaths.map {
        case (graphKey, path) =>
          graphKey -> AutoUpdat ngGraph(
            dataPath = getHdfsPath(path),
            hdfsCluster = hdfsCluster,
            hdfsClusterUrl = hdfsClusterUrl,
            shard = shard d,
            m n mumS zeForCompleteGraph = 1e6.toLong,
            sharedSemaphore = So (sharedSemaphore)
          )(
            statsRece ver
              .scope("graphs")
              .scope(graphKey.getClass.getS mpleNa ),
            t  r
          )
      }

    ut l.GraphConta ner(graphs)
  }
}
