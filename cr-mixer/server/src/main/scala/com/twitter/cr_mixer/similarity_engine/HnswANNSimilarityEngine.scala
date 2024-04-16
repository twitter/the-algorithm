package com.tw ter.cr_m xer.s m lar y_eng ne

 mport com.tw ter.ann.common.thr ftscala.AnnQueryServ ce
 mport com.tw ter.ann.common.thr ftscala.D stance
 mport com.tw ter.ann.common.thr ftscala.NearestNe ghborQuery
 mport com.tw ter.ann.hnsw.HnswCommon
 mport com.tw ter.ann.hnsw.HnswParams
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.cortex.ml.embedd ngs.common.T etK nd
 mport com.tw ter.cr_m xer.model.S m lar yEng ne nfo
 mport com.tw ter.cr_m xer.model.T etW hScore
 mport com.tw ter.cr_m xer.s m lar y_eng ne.S m lar yEng ne. mCac Conf g
 mport com.tw ter.cr_m xer.s m lar y_eng ne.S m lar yEng ne.S m lar yEng neConf g
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter. d aserv ces.commons.codec.ArrayByteBufferCodec
 mport com.tw ter.ml.ap .thr ftscala.{Embedd ng => Thr ftEmbedd ng}
 mport com.tw ter.ml.featurestore.l b
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.ut l.Future

case class HnswANNEng neQuery(
  model d: Str ng,
  s ce d:  nternal d,
  params: Params,
) {
  val cac Key: Str ng = s"${model d}_${s ce d.toStr ng}"
}

/**
 * T  Eng ne looks for t ets whose s m lar y  s close to a S ce Dense Embedd ng.
 * Only support Long based embedd ng lookup. User d or T et d.
 *
 *   prov des HNSW spec f c  mple ntat ons
 *
 * @param  mCac Conf gOpt    f spec f ed,   w ll wrap t  underly ng store w h a  mCac  layer
 *                              should only enable t  for cac able quer es, e.x. T et ds.
 *                            consu r based User ds are generally not poss ble to cac .
 */
class HnswANNS m lar yEng ne(
  embedd ngStoreLookUpMap: Map[Str ng, ReadableStore[ nternal d, Thr ftEmbedd ng]],
  annServ ceLookUpMap: Map[Str ng, AnnQueryServ ce. thodPerEndpo nt],
  globalStats: StatsRece ver,
  overr de val  dent f er: S m lar yEng neType,
  eng neConf g: S m lar yEng neConf g,
   mCac Conf gOpt: Opt on[ mCac Conf g[HnswANNEng neQuery]] = None)
    extends S m lar yEng ne[HnswANNEng neQuery, T etW hScore] {

  pr vate val MaxNumResults:  nt = 200
  pr vate val ef:  nt = 800
  pr vate val T et dByte nject on:  nject on[l b.T et d, Array[Byte]] = T etK nd.byte nject on

  pr vate val scopedStats = globalStats.scope("s m lar yEng ne",  dent f er.toStr ng)

  def getScopedStats: StatsRece ver = scopedStats

  pr vate def fetchEmbedd ng(
    query: HnswANNEng neQuery,
  ): Future[Opt on[Thr ftEmbedd ng]] = {
    val embedd ngStore = embedd ngStoreLookUpMap.getOrElse(
      query.model d,
      throw new  llegalArgu ntExcept on(
        s"${t .getClass.getS mpleNa } ${ dent f er.toStr ng}: " +
          s"Model d ${query.model d} does not ex st for embedd ngStore"
      )
    )

    embedd ngStore.get(query.s ce d)
  }

  pr vate def fetchCand dates(
    query: HnswANNEng neQuery,
    embedd ng: Thr ftEmbedd ng
  ): Future[Seq[T etW hScore]] = {
    val annServ ce = annServ ceLookUpMap.getOrElse(
      query.model d,
      throw new  llegalArgu ntExcept on(
        s"${t .getClass.getS mpleNa } ${ dent f er.toStr ng}: " +
          s"Model d ${query.model d} does not ex st for annStore"
      )
    )

    val hnswParams = HnswCommon.Runt  Params nject on.apply(HnswParams(ef))

    val annQuery =
      NearestNe ghborQuery(embedd ng, w hD stance = true, hnswParams, MaxNumResults)

    annServ ce
      .query(annQuery)
      .map(
        _.nearestNe ghbors
          .map { nearestNe ghbor =>
            val cand date d = T et dByte nject on
              . nvert(ArrayByteBufferCodec.decode(nearestNe ghbor. d))
              .toOpt on
              .map(_.t et d)
            (cand date d, nearestNe ghbor.d stance)
          }.collect {
            case (So (cand date d), So (d stance)) =>
              T etW hScore(cand date d, toScore(d stance))
          })
  }

  // Convert D stance to a score such that h g r scores  an more s m lar.
  def toScore(d stance: D stance): Double = {
    d stance match {
      case D stance.Ed D stance(ed D stance) =>
        // (- nf n e, 0.0]
        0.0 - ed D stance.d stance
      case D stance.L2D stance(l2D stance) =>
        // (- nf n e, 0.0]
        0.0 - l2D stance.d stance
      case D stance.Cos neD stance(cos neD stance) =>
        // [0.0 - 1.0]
        1.0 - cos neD stance.d stance
      case D stance. nnerProductD stance( nnerProductD stance) =>
        // (- nf n e,  nf n e)
        1.0 -  nnerProductD stance.d stance
      case D stance.UnknownUn onF eld(_) =>
        throw new  llegalStateExcept on(
          s"${t .getClass.getS mpleNa } does not recogn ze $d stance.toStr ng"
        )
    }
  }

  pr vate[s m lar y_eng ne] def getEmbedd ngAndCand dates(
    query: HnswANNEng neQuery
  ): Future[Opt on[Seq[T etW hScore]]] = {

    val fetchEmbedd ngStat = scopedStats.scope(query.model d).scope("fetchEmbedd ng")
    val fetchCand datesStat = scopedStats.scope(query.model d).scope("fetchCand dates")

    for {
      embedd ngOpt <- StatsUt l.trackOpt onStats(fetchEmbedd ngStat) { fetchEmbedd ng(query) }
      cand dates <- StatsUt l.track emsStats(fetchCand datesStat) {

        embedd ngOpt match {
          case So (embedd ng) => fetchCand dates(query, embedd ng)
          case None => Future.N l
        }
      }
    } y eld {
      So (cand dates)
    }
  }

  // Add  mcac  wrapper,  f spec f ed
  pr vate val store = {
    val uncac dStore = ReadableStore.fromFnFuture(getEmbedd ngAndCand dates)

     mCac Conf gOpt match {
      case So (conf g) =>
        S m lar yEng ne.add mCac (
          underly ngStore = uncac dStore,
           mCac Conf g = conf g,
          statsRece ver = scopedStats
        )
      case _ => uncac dStore
    }
  }

  def toS m lar yEng ne nfo(
    query: HnswANNEng neQuery,
    score: Double
  ): S m lar yEng ne nfo = {
    S m lar yEng ne nfo(
      s m lar yEng neType = t . dent f er,
      model d = So (query.model d),
      score = So (score))
  }

  overr de def getCand dates(
    eng neQuery: HnswANNEng neQuery
  ): Future[Opt on[Seq[T etW hScore]]] = {
    val vers onedStats = globalStats.scope(eng neQuery.model d)
    S m lar yEng ne.getFromFn(
      store.get,
      eng neQuery,
      eng neConf g,
      eng neQuery.params,
      vers onedStats
    )
  }
}
