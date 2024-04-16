package com.tw ter.s mclusters_v2.sc o
package mult _type_graph.mult _type_graph_s ms

 mport com.spot fy.sc o.Sc oContext
 mport com.spot fy.sc o.coders.Coder
 mport com.spot fy.sc o.values.SCollect on
 mport com.tw ter.beam. o.dal.DAL
 mport com.tw ter.beam. o.fs.mult format.D skFormat
 mport com.tw ter.beam. o.fs.mult format.PathLa t
 mport com.tw ter.beam.job.DateRangeOpt ons
 mport com.tw ter.dal.cl ent.dataset.SnapshotDALDataset
 mport com.tw ter.sc o_ nternal.coders.Thr ftStructLazyB naryScroogeCoder
 mport com.tw ter.sc o_ nternal.job.Sc oBeamJob
 mport com.tw ter.scrooge.Thr ftStruct
 mport com.tw ter.s mclusters_v2.sc o.mult _type_graph.common.Mult TypeGraphUt l
 mport com.tw ter.s mclusters_v2.thr ftscala.R ghtNode
 mport com.tw ter.s mclusters_v2.thr ftscala.R ghtNodeS mHashSketch
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.wtf.dataflow.cos ne_s m lar y.S mHashJob
 mport java.t  . nstant

tra  R ghtNodeS mHashSc oBaseApp extends Sc oBeamJob[DateRangeOpt ons] w h S mHashJob[R ghtNode] {
  overr de  mpl c  def scroogeCoder[T <: Thr ftStruct: Man fest]: Coder[T] =
    Thr ftStructLazyB naryScroogeCoder.scroogeCoder
  overr de val order ng: Order ng[R ghtNode] = Mult TypeGraphUt l.r ghtNodeOrder ng

  val  sAdhoc: Boolean
  val r ghtNodeS mHashSnapshotDataset: SnapshotDALDataset[R ghtNodeS mHashSketch]
  val s msHashJobOutputD rectory: Str ng = Conf g.s msHashJobOutputD rectory

  overr de def graph(
     mpl c  sc: Sc oContext,
  ): SCollect on[(Long, R ghtNode, Double)] =
    Mult TypeGraphUt l.getTruncatedMult TypeGraph(noOlderThan = Durat on.fromDays(14))

  overr de def conf gureP pel ne(sc: Sc oContext, opts: DateRangeOpt ons): Un  = {
     mpl c  def sc oContext: Sc oContext = sc

    // DAL.Env ron nt var able for Wr eExecs
    val dalEnv =  f ( sAdhoc) DAL.Env ron nt.Dev else DAL.Env ron nt.Prod

    val sketc s = computeS mHashSketc sFor  ghtedGraph(graph)
      .map {
        case (r ghtNode, sketch, norm) => R ghtNodeS mHashSketch(r ghtNode, sketch, norm)
      }

    // Wr e S mHashSketc s to DAL
    sketc s
      .saveAsCustomOutput(
        na  = "Wr eS mHashSketc s",
        DAL.wr eSnapshot(
          r ghtNodeS mHashSnapshotDataset,
          PathLa t.F xedPath(
            (( f (! sAdhoc)
                Mult TypeGraphUt l.RootThr ftPath
              else
                Mult TypeGraphUt l.AdhocRootPath)
              + s msHashJobOutputD rectory)),
           nstant.ofEpochM ll (opts. nterval.getEndM ll s - 1L),
          D skFormat.Thr ft(),
          env ron ntOverr de = dalEnv
        )
      )
  }
}
