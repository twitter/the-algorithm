package com.tw ter.follow_recom ndat ons.common.pred cates.sgs

 mport com.google.common.annotat ons.V s bleForTest ng
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.base.Pred cate
 mport com.tw ter.follow_recom ndat ons.common.base.Pred cateResult
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.F lterReason. nval dRelat onsh pTypes
 mport com.tw ter.soc algraph.thr ftscala.Ex stsRequest
 mport com.tw ter.soc algraph.thr ftscala.Ex stsResult
 mport com.tw ter.soc algraph.thr ftscala.LookupContext
 mport com.tw ter.soc algraph.thr ftscala.Relat onsh p
 mport com.tw ter.soc algraph.thr ftscala.Relat onsh pType
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.soc algraph.Soc alGraph
 mport com.tw ter.ut l.logg ng.Logg ng
 mport javax. nject. nject
 mport javax. nject.S ngleton

class SgsRelat onsh psByUser dPred cate(
  soc alGraph: Soc alGraph,
  relat onsh pMapp ngs: Seq[Relat onsh pMapp ng],
  statsRece ver: StatsRece ver)
    extends Pred cate[(Opt on[Long], Cand dateUser)]
    w h Logg ng {
  pr vate val  nval dFromPr maryCand dateS ceNa  = " nval d_from_pr mary_cand date_s ce"
  pr vate val  nval dFromCand dateS ceNa  = " nval d_from_cand date_s ce"
  pr vate val NoPr maryCand dateS ce = "no_pr mary_cand date_s ce"

  pr vate val stats: StatsRece ver = statsRece ver.scope(t .getClass.getNa )

  overr de def apply(
    pa r: (Opt on[Long], Cand dateUser)
  ): St ch[Pred cateResult] = {
    val ( dOpt, cand date) = pa r
    val relat onsh ps = relat onsh pMapp ngs.map { relat onsh pMapp ng: Relat onsh pMapp ng =>
      Relat onsh p(
        relat onsh pMapp ng.relat onsh pType,
        relat onsh pMapp ng. ncludeBasedOnRelat onsh p)
    }
     dOpt
      .map {  d: Long =>
        val ex stsRequest = Ex stsRequest(
           d,
          cand date. d,
          relat onsh ps = relat onsh ps,
          context = SgsRelat onsh psByUser dPred cate.Un onLookupContext
        )
        soc alGraph
          .ex sts(ex stsRequest).map { ex stsResult: Ex stsResult =>
             f (ex stsResult.ex sts) {
              cand date.getPr maryCand dateS ce match {
                case So (cand dateS ce) =>
                  stats
                    .scope( nval dFromPr maryCand dateS ceNa ).counter(
                      cand dateS ce.na ). ncr()
                case None =>
                  stats
                    .scope( nval dFromPr maryCand dateS ceNa ).counter(
                      NoPr maryCand dateS ce). ncr()
              }
              cand date.getCand dateS ces.foreach({
                case (cand dateS ce, _) =>
                  stats
                    .scope( nval dFromCand dateS ceNa ).counter(cand dateS ce.na ). ncr()
              })
              Pred cateResult. nval d(Set( nval dRelat onsh pTypes(relat onsh pMapp ngs
                .map { relat onsh pMapp ng: Relat onsh pMapp ng =>
                  relat onsh pMapp ng.relat onsh pType
                }.mkStr ng(", "))))
            } else {
              Pred cateResult.Val d
            }
          }
      }
      //  f no user  d  s present, return true by default
      .getOrElse(St ch.value(Pred cateResult.Val d))
  }
}

object SgsRelat onsh psByUser dPred cate {
  // OR Operat on
  @V s bleForTest ng
  pr vate[follow_recom ndat ons] val Un onLookupContext = So (
    LookupContext(performUn on = So (true)))
}

@S ngleton
class ExcludeNonFollo rsSgsPred cate @ nject() (
  soc alGraph: Soc alGraph,
  statsRece ver: StatsRece ver)
    extends SgsRelat onsh psByUser dPred cate(
      soc alGraph,
      Seq(Relat onsh pMapp ng(Relat onsh pType.Follo dBy,  ncludeBasedOnRelat onsh p = false)),
      statsRece ver)

@S ngleton
class ExcludeNonFollow ngSgsPred cate @ nject() (
  soc alGraph: Soc alGraph,
  statsRece ver: StatsRece ver)
    extends SgsRelat onsh psByUser dPred cate(
      soc alGraph,
      Seq(Relat onsh pMapp ng(Relat onsh pType.Follow ng,  ncludeBasedOnRelat onsh p = false)),
      statsRece ver)

@S ngleton
class ExcludeFollow ngSgsPred cate @ nject() (
  soc alGraph: Soc alGraph,
  statsRece ver: StatsRece ver)
    extends SgsRelat onsh psByUser dPred cate(
      soc alGraph,
      Seq(Relat onsh pMapp ng(Relat onsh pType.Follow ng,  ncludeBasedOnRelat onsh p = true)),
      statsRece ver)
