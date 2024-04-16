package com.tw ter.s mclusters_v2.sc o
package mult _type_graph.mult _type_graph_s ms

 mport com.spot fy.sc o.Sc oContext
 mport com.spot fy.sc o.coders.Coder
 mport com.spot fy.sc o.values.SCollect on
 mport com.tw ter.beam. o.dal.DAL
 mport com.tw ter.beam. o.fs.mult format.PathLa t
 mport com.tw ter.beam.job.DateRangeOpt ons
 mport com.tw ter.common.ut l.Clock
 mport com.tw ter.dal.cl ent.dataset.KeyValDALDataset
 mport com.tw ter.dal.cl ent.dataset.SnapshotDALDataset
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.sc o_ nternal.coders.Thr ftStructLazyB naryScroogeCoder
 mport com.tw ter.sc o_ nternal.job.Sc oBeamJob
 mport com.tw ter.scrooge.Thr ftStruct
 mport com.tw ter.s mclusters_v2.hdfs_s ces.R ghtNodeS mHashSc oScalaDataset
 mport com.tw ter.s mclusters_v2.sc o.mult _type_graph.common.Mult TypeGraphUt l
 mport com.tw ter.s mclusters_v2.thr ftscala._
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.wtf.dataflow.cos ne_s m lar y.Approx mateMatr xSelfTransposeMult pl cat onJob
 mport java.t  . nstant

tra  R ghtNodeCos neS m lar ySc oBaseApp
    extends Sc oBeamJob[DateRangeOpt ons]
    w h Approx mateMatr xSelfTransposeMult pl cat onJob[R ghtNode] {
  overr de  mpl c  def scroogeCoder[T <: Thr ftStruct: Man fest]: Coder[T] =
    Thr ftStructLazyB naryScroogeCoder.scroogeCoder
  overr de val order ng: Order ng[R ghtNode] = Mult TypeGraphUt l.r ghtNodeOrder ng

  val  sAdhoc: Boolean
  val cos neS mKeyValSnapshotDataset: KeyValDALDataset[KeyVal[R ghtNode, S m larR ghtNodes]]
  val r ghtNodeS mHashSnapshotDataset: SnapshotDALDataset[R ghtNodeS mHashSketch] =
    R ghtNodeS mHashSc oScalaDataset
  val cos neS mJobOutputD rectory: Str ng = Conf g.cos neS mJobOutputD rectory

  overr de def graph(
     mpl c  sc: Sc oContext,
    coder: Coder[R ghtNode]
  ): SCollect on[(Long, R ghtNode, Double)] =
    Mult TypeGraphUt l.getTruncatedMult TypeGraph(noOlderThan = Durat on.fromDays(14))

  overr de def s mHashSketc s(
     mpl c  sc: Sc oContext,
    coder: Coder[R ghtNode]
  ): SCollect on[(R ghtNode, Array[Byte])] = {
    sc.custom nput(
        "ReadS mHashSketc s",
        DAL
          .readMostRecentSnapshotNoOlderThan(
            r ghtNodeS mHashSnapshotDataset,
            Durat on.fromDays(14),
            Clock.SYSTEM_CLOCK,
            DAL.Env ron nt.Prod
          )
      ).map { sketch =>
        sketch.r ghtNode -> sketch.s mHashOfEngagers.toArray
      }
  }

  overr de def conf gureP pel ne(
    sc: Sc oContext,
    opts: DateRangeOpt ons
  ): Un  = {
     mpl c  def sc oContext: Sc oContext = sc
    // DAL.Env ron nt var able for Wr eExecs
    val dalEnv =  f ( sAdhoc) DAL.Env ron nt.Dev else DAL.Env ron nt.Prod

    val topKR ghtNodes: SCollect on[(R ghtNode, S m larR ghtNodes)] = topK
      .collect {
        case (r ghtNode, s mR ghtNodes) =>
          val s ms = s mR ghtNodes.collect {
            case (s mR ghtNode, score) => S m larR ghtNode(s mR ghtNode, score)
          }
          (r ghtNode, S m larR ghtNodes(s ms))
      }

    topKR ghtNodes
      .map {
        case (r ghtNode, s ms) => KeyVal(r ghtNode, s ms)
      }.saveAsCustomOutput(
        na  = "Wr eR ghtNodeCos neS m lar yDataset",
        DAL.wr eVers onedKeyVal(
          cos neS mKeyValSnapshotDataset,
          PathLa t.Vers onedPath(pref x =
            (( f (! sAdhoc)
                Mult TypeGraphUt l.RootMHPath
              else
                Mult TypeGraphUt l.AdhocRootPath)
              + Conf g.cos neS mJobOutputD rectory)),
           nstant =  nstant.ofEpochM ll (opts. nterval.getEndM ll s - 1L),
          env ron ntOverr de = dalEnv,
        )
      )
  }
}
