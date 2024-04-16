package com.tw ter.cr_m xer.s m lar y_eng ne

 mport com.tw ter.ann.common.thr ftscala.AnnQueryServ ce
 mport com.tw ter.ann.common.thr ftscala.D stance
 mport com.tw ter.ann.common.thr ftscala.NearestNe ghborQuery
 mport com.tw ter.ann.common.thr ftscala.NearestNe ghborResult
 mport com.tw ter.ann.hnsw.HnswCommon
 mport com.tw ter.ann.hnsw.HnswParams
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.cortex.ml.embedd ngs.common.T etK nd
 mport com.tw ter.cr_m xer.model.S m lar yEng ne nfo
 mport com.tw ter.cr_m xer.model.T etW hScore
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter. d aserv ces.commons.codec.ArrayByteBufferCodec
 mport com.tw ter.ml.ap .thr ftscala.{Embedd ng => Thr ftEmbedd ng}
 mport com.tw ter.ml.featurestore.l b
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport javax. nject.S ngleton

/**
 * T  store looks for t ets whose s m lar y  s close to a S ce Dense Embedd ng.
 * Only support Long based embedd ng lookup. User d or T et d
 */
@S ngleton
class ModelBasedANNStore(
  embedd ngStoreLookUpMap: Map[Str ng, ReadableStore[ nternal d, Thr ftEmbedd ng]],
  annServ ceLookUpMap: Map[Str ng, AnnQueryServ ce. thodPerEndpo nt],
  globalStats: StatsRece ver)
    extends ReadableStore[
      ModelBasedANNStore.Query,
      Seq[T etW hScore]
    ] {

   mport ModelBasedANNStore._

  pr vate val stats = globalStats.scope(t .getClass.getS mpleNa )
  pr vate val fetchEmbedd ngStat = stats.scope("fetchEmbedd ng")
  pr vate val fetchCand datesStat = stats.scope("fetchCand dates")

  overr de def get(query: Query): Future[Opt on[Seq[T etW hScore]]] = {
    for {
      maybeEmbedd ng <- StatsUt l.trackOpt onStats(fetchEmbedd ngStat.scope(query.model d)) {
        fetchEmbedd ng(query)
      }
      maybeCand dates <- StatsUt l.trackOpt onStats(fetchCand datesStat.scope(query.model d)) {
        maybeEmbedd ng match {
          case So (embedd ng) =>
            fetchCand dates(query, embedd ng)
          case None =>
            Future.None
        }
      }
    } y eld {
      maybeCand dates.map(
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
  }

  pr vate def fetchEmbedd ng(query: Query): Future[Opt on[Thr ftEmbedd ng]] = {
    embedd ngStoreLookUpMap.get(query.model d) match {
      case So (embedd ngStore) =>
        embedd ngStore.get(query.s ce d)
      case _ =>
        Future.None
    }
  }

  pr vate def fetchCand dates(
    query: Query,
    embedd ng: Thr ftEmbedd ng
  ): Future[Opt on[NearestNe ghborResult]] = {
    val hnswParams = HnswCommon.Runt  Params nject on.apply(HnswParams(query.ef))

    annServ ceLookUpMap.get(query.model d) match {
      case So (annServ ce) =>
        val annQuery =
          NearestNe ghborQuery(embedd ng, w hD stance = true, hnswParams, MaxNumResults)
        annServ ce.query(annQuery).map(v => So (v))
      case _ =>
        Future.None
    }
  }
}

object ModelBasedANNStore {

  val MaxNumResults:  nt = 200
  val MaxT etCand dateAge: Durat on = 1.day

  val T et dByte nject on:  nject on[l b.T et d, Array[Byte]] = T etK nd.byte nject on

  // For more  nformat on about HNSW algor hm: https://docb rd.tw ter.b z/ann/hnsw.html
  case class Query(
    s ce d:  nternal d,
    model d: Str ng,
    s m lar yEng neType: S m lar yEng neType,
    ef:  nt = 800)

  def toScore(d stance: D stance): Double = {
    d stance match {
      case D stance.L2D stance(l2D stance) =>
        // (- nf n e, 0.0]
        0.0 - l2D stance.d stance
      case D stance.Cos neD stance(cos neD stance) =>
        // [0.0 - 1.0]
        1.0 - cos neD stance.d stance
      case D stance. nnerProductD stance( nnerProductD stance) =>
        // (- nf n e,  nf n e)
        1.0 -  nnerProductD stance.d stance
      case _ =>
        0.0
    }
  }
  def toS m lar yEng ne nfo(query: Query, score: Double): S m lar yEng ne nfo = {
    S m lar yEng ne nfo(
      s m lar yEng neType = query.s m lar yEng neType,
      model d = So (query.model d),
      score = So (score))
  }
}
