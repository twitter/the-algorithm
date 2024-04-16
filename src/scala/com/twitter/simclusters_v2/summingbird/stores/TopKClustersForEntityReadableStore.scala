package com.tw ter.s mclusters_v2.summ ngb rd.stores

 mport com.tw ter.s mclusters_v2.summ ngb rd.common.Ent yUt l
 mport com.tw ter.s mclusters_v2.thr ftscala._
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  

case class TopKClustersForEnt yReadableStore(
  underly ngStore: ReadableStore[Ent yW hVers on, TopKClustersW hScores])
    extends ReadableStore[Ent yW hVers on, TopKClustersW hScores] {

  overr de def mult Get[K1 <: Ent yW hVers on](
    ks: Set[K1]
  ): Map[K1, Future[Opt on[TopKClustersW hScores]]] = {
    val now nMs = T  .now. nM ll seconds
    underly ngStore
      .mult Get(ks)
      .mapValues { resFuture =>
        resFuture.map { resOpt =>
          resOpt.map { clustersW hScores =>
            clustersW hScores.copy(
              topClustersByFavClusterNormal zedScore = Ent yUt l.updateScoreW hLatestT  stamp(
                clustersW hScores.topClustersByFavClusterNormal zedScore,
                now nMs
              ),
              topClustersByFollowClusterNormal zedScore = Ent yUt l.updateScoreW hLatestT  stamp(
                clustersW hScores.topClustersByFollowClusterNormal zedScore,
                now nMs
              )
            )
          }
        }
      }
  }
}
