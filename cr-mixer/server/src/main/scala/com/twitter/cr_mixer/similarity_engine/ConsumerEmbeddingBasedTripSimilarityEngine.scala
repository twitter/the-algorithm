package com.tw ter.cr_m xer.s m lar y_eng ne

 mport com.tw ter.cr_m xer.model.Tr pT etW hScore
 mport com.tw ter.cr_m xer.param.Consu rEmbedd ngBasedTr pParams
 mport com.tw ter.cr_m xer.ut l. nterleaveUt l
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter.s mclusters_v2.common.Cluster d
 mport com.tw ter.s mclusters_v2.common.S mClustersEmbedd ng
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes.conf gap 
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.trends.tr p_v1.tr p_t ets.thr ftscala.Cluster
 mport com.tw ter.trends.tr p_v1.tr p_t ets.thr ftscala.ClusterDoma n
 mport com.tw ter.trends.tr p_v1.tr p_t ets.thr ftscala.Tr pT et
 mport com.tw ter.trends.tr p_v1.tr p_t ets.thr ftscala.Tr pDoma n
 mport com.tw ter.ut l.Future

case class Tr pEng neQuery(
  model d: Str ng,
  s ce d:  nternal d,
  tr pS ce d: Str ng,
  maxResult:  nt,
  params: Params)

case class Consu rEmbedd ngBasedTr pS m lar yEng ne(
  embedd ngStoreLookUpMap: Map[Str ng, ReadableStore[User d, S mClustersEmbedd ng]],
  tr pCand dateS ce: ReadableStore[Tr pDoma n, Seq[Tr pT et]],
  statsRece ver: StatsRece ver,
) extends ReadableStore[Tr pEng neQuery, Seq[Tr pT etW hScore]] {
   mport Consu rEmbedd ngBasedTr pS m lar yEng ne._

  pr vate val scopedStats = statsRece ver.scope(na )
  pr vate def fetchTopClusters(query: Tr pEng neQuery): Future[Opt on[Seq[Cluster d]]] = {
    query.s ce d match {
      case  nternal d.User d(user d) =>
        val embedd ngStore = embedd ngStoreLookUpMap.getOrElse(
          query.model d,
          throw new  llegalArgu ntExcept on(
            s"${t .getClass.getS mpleNa }: " +
              s"Model d ${query.model d} does not ex st for embedd ngStore"
          )
        )
        embedd ngStore.get(user d).map(_.map(_.topCluster ds(MaxClusters)))
      case _ =>
        Future.None
    }
  }
  pr vate def fetchCand dates(
    topClusters: Seq[Cluster d],
    tr pS ce d: Str ng
  ): Future[Seq[Seq[Tr pT etW hScore]]] = {
    Future
      .collect {
        topClusters.map { cluster d =>
          tr pCand dateS ce
            .get(
              Tr pDoma n(
                s ce d = tr pS ce d,
                clusterDoma n = So (
                  ClusterDoma n(s mCluster = So (Cluster(cluster nt d = So (cluster d))))))).map {
              _.map {
                _.collect {
                  case Tr pT et(t et d, score) =>
                    Tr pT etW hScore(t et d, score)
                }
              }.getOrElse(Seq.empty).take(MaxNumResultsPerCluster)
            }
        }
      }
  }

  overr de def get(eng neQuery: Tr pEng neQuery): Future[Opt on[Seq[Tr pT etW hScore]]] = {
    val fetchTopClustersStat = scopedStats.scope(eng neQuery.model d).scope("fetchTopClusters")
    val fetchCand datesStat = scopedStats.scope(eng neQuery.model d).scope("fetchCand dates")

    for {
      topClustersOpt <- StatsUt l.trackOpt onStats(fetchTopClustersStat) {
        fetchTopClusters(eng neQuery)
      }
      cand dates <- StatsUt l.track emsStats(fetchCand datesStat) {
        topClustersOpt match {
          case So (topClusters) => fetchCand dates(topClusters, eng neQuery.tr pS ce d)
          case None => Future.N l
        }
      }
    } y eld {
      val  nterleavedT ets =  nterleaveUt l. nterleave(cand dates)
      val dedupCand dates =  nterleavedT ets
        .groupBy(_.t et d).flatMap {
          case (_, t etW hScoreSeq) => t etW hScoreSeq.sortBy(-_.score).take(1)
        }.toSeq.take(eng neQuery.maxResult)
      So (dedupCand dates)
    }
  }
}

object Consu rEmbedd ngBasedTr pS m lar yEng ne {
  pr vate val MaxClusters:  nt = 8
  pr vate val MaxNumResultsPerCluster:  nt = 25
  pr vate val na : Str ng = t .getClass.getS mpleNa 

  def fromParams(
    model d: Str ng,
    s ce d:  nternal d,
    params: conf gap .Params
  ): Tr pEng neQuery = {
    Tr pEng neQuery(
      model d = model d,
      s ce d = s ce d,
      tr pS ce d = params(Consu rEmbedd ngBasedTr pParams.S ce dParam),
      maxResult = params(Consu rEmbedd ngBasedTr pParams.MaxNumCand datesParam),
      params = params
    )
  }
}
