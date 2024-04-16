package com.tw ter.s mclusters_v2.summ ngb rd.stores

 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.fr gate.common.store.strato.StratoStore
 mport com.tw ter.s mclusters_v2.summ ngb rd.common. mpl c s.clustersW hScoreMono d
 mport com.tw ter.s mclusters_v2.summ ngb rd.common. mpl c s.clustersW hScoresCodec
 mport com.tw ter.storehaus.algebra. rgeableStore
 mport com.tw ter.s mclusters_v2.summ ngb rd.common.Cl entConf gs
 mport com.tw ter.s mclusters_v2.summ ngb rd.common. mpl c s
 mport com.tw ter.s mclusters_v2.thr ftscala.ClustersW hScores
 mport com.tw ter.s mclusters_v2.thr ftscala.FullCluster dBucket
 mport com.tw ter.s mclusters_v2.thr ftscala.Mult ModelClustersW hScores
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClusterEnt y
 mport com.tw ter.storehaus.Store
 mport com.tw ter.storehaus_ nternal. mcac . mcac 
 mport com.tw ter.strato.cl ent.Cl ent
 mport com.tw ter.summ ngb rd.batch.Batch D
 mport com.tw ter.summ ngb rd_ nternal.b ject on.BatchPa r mpl c s
 mport com.tw ter.ut l.Future
 mport com.tw ter.strato.thr ft.ScroogeConv mpl c s._

object Ent yClusterScoreReadableStore {

  pr vate[s mclusters_v2] f nal lazy val onl ne rgeableStore: (
    Str ng,
    Serv ce dent f er
  ) =>  rgeableStore[
    ((S mClusterEnt y, FullCluster dBucket), Batch D),
    ClustersW hScores
  ] = { (path: Str ng, serv ce dent f er: Serv ce dent f er) =>
     mcac 
      .get mcac Store[((S mClusterEnt y, FullCluster dBucket), Batch D), ClustersW hScores](
        Cl entConf gs.ent yClusterScore mcac Conf g(path, serv ce dent f er)
      )(
        BatchPa r mpl c s.key nject on[(S mClusterEnt y, FullCluster dBucket)](
           mpl c s.ent yW hCluster nject on
        ),
        clustersW hScoresCodec,
        clustersW hScoreMono d
      )
  }

}

object Mult ModelEnt yClusterScoreReadableStore {

  pr vate[s mclusters_v2] def Mult ModelEnt yClusterScoreReadableStore(
    stratoCl ent: Cl ent,
    column: Str ng
  ): Store[Ent yCluster d, Mult ModelClustersW hScores] = {
    StratoStore
      .w hUn V ew[(S mClusterEnt y,  nt), Mult ModelClustersW hScores](stratoCl ent, column)
      .composeKeyMapp ng(_.toTuple)
  }

  case class Ent yCluster d(
    s mClusterEnt y: S mClusterEnt y,
    cluster dBucket:  nt) {
    lazy val toTuple: (S mClusterEnt y,  nt) =
      (s mClusterEnt y, cluster dBucket)
  }
}
