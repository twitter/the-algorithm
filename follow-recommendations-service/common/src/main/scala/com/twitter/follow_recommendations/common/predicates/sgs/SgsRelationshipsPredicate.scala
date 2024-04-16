package com.tw ter.follow_recom ndat ons.common.pred cates.sgs

 mport com.google.common.annotat ons.V s bleForTest ng
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.base.Pred cate
 mport com.tw ter.follow_recom ndat ons.common.base.Pred cateResult
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasProf le d
 mport com.tw ter.follow_recom ndat ons.common.models.F lterReason.Fa lOpen
 mport com.tw ter.follow_recom ndat ons.common.models.F lterReason. nval dRelat onsh pTypes
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.soc algraph.thr ftscala.Ex stsRequest
 mport com.tw ter.soc algraph.thr ftscala.Ex stsResult
 mport com.tw ter.soc algraph.thr ftscala.LookupContext
 mport com.tw ter.soc algraph.thr ftscala.Relat onsh p
 mport com.tw ter.soc algraph.thr ftscala.Relat onsh pType
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.soc algraph.Soc alGraph
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.ut l.T  outExcept on
 mport com.tw ter.ut l.logg ng.Logg ng

 mport javax. nject. nject
 mport javax. nject.S ngleton

case class Relat onsh pMapp ng(
  relat onsh pType: Relat onsh pType,
   ncludeBasedOnRelat onsh p: Boolean)

class SgsRelat onsh psPred cate(
  soc alGraph: Soc alGraph,
  relat onsh pMapp ngs: Seq[Relat onsh pMapp ng],
  statsRece ver: StatsRece ver = NullStatsRece ver)
    extends Pred cate[(HasCl entContext w h HasParams, Cand dateUser)]
    w h Logg ng {

  pr vate val stats: StatsRece ver = statsRece ver.scope(t .getClass.getS mpleNa )

  overr de def apply(
    pa r: (HasCl entContext w h HasParams, Cand dateUser)
  ): St ch[Pred cateResult] = {
    val (target, cand date) = pa r
    val t  out = target.params(SgsPred cateParams.SgsRelat onsh psPred cateT  out)
    SgsRelat onsh psPred cate
      .extractUser d(target)
      .map {  d =>
        val relat onsh ps = relat onsh pMapp ngs.map { relat onsh pMapp ng: Relat onsh pMapp ng =>
          Relat onsh p(
            relat onsh pMapp ng.relat onsh pType,
            relat onsh pMapp ng. ncludeBasedOnRelat onsh p)
        }
        val ex stsRequest = Ex stsRequest(
           d,
          cand date. d,
          relat onsh ps = relat onsh ps,
          context = SgsRelat onsh psPred cate.Un onLookupContext
        )
        soc alGraph
          .ex sts(ex stsRequest).map { ex stsResult: Ex stsResult =>
             f (ex stsResult.ex sts) {
              Pred cateResult. nval d(Set( nval dRelat onsh pTypes(relat onsh pMapp ngs
                .map { relat onsh pMapp ng: Relat onsh pMapp ng =>
                  relat onsh pMapp ng.relat onsh pType
                }.mkStr ng(", "))))
            } else {
              Pred cateResult.Val d
            }
          }
          .w h n(t  out)(com.tw ter.f nagle.ut l.DefaultT  r)
      }
      //  f no user  d  s present, return true by default
      .getOrElse(St ch.value(Pred cateResult.Val d))
      .rescue {
        case e: T  outExcept on =>
          stats.counter("t  out"). ncr()
          St ch(Pred cateResult. nval d(Set(Fa lOpen)))
        case e: Except on =>
          stats.counter(e.getClass.getS mpleNa ). ncr()
          St ch(Pred cateResult. nval d(Set(Fa lOpen)))
      }

  }
}

object SgsRelat onsh psPred cate {
  // OR Operat on
  @V s bleForTest ng
  pr vate[follow_recom ndat ons] val Un onLookupContext = So (
    LookupContext(performUn on = So (true)))

  pr vate def extractUser d(target: HasCl entContext w h HasParams): Opt on[Long] = target match {
    case profRequest: HasProf le d => So (profRequest.prof le d)
    case userRequest: HasCl entContext w h HasParams => userRequest.getOpt onalUser d
    case _ => None
  }
}

@S ngleton
class  nval dTargetCand dateRelat onsh pTypesPred cate @ nject() (
  soc alGraph: Soc alGraph)
    extends SgsRelat onsh psPred cate(
      soc alGraph,
       nval dRelat onsh pTypesPred cate. nval dRelat onsh pTypes) {}

@S ngleton
class NoteworthyAccountsSgsPred cate @ nject() (
  soc alGraph: Soc alGraph)
    extends SgsRelat onsh psPred cate(
      soc alGraph,
       nval dRelat onsh pTypesPred cate.NoteworthyAccounts nval dRelat onsh pTypes)

object  nval dRelat onsh pTypesPred cate {

  val  nval dRelat onsh pTypesExcludeFollow ng: Seq[Relat onsh pMapp ng] = Seq(
    Relat onsh pMapp ng(Relat onsh pType.H deRecom ndat ons, true),
    Relat onsh pMapp ng(Relat onsh pType.Block ng, true),
    Relat onsh pMapp ng(Relat onsh pType.BlockedBy, true),
    Relat onsh pMapp ng(Relat onsh pType.Mut ng, true),
    Relat onsh pMapp ng(Relat onsh pType.MutedBy, true),
    Relat onsh pMapp ng(Relat onsh pType.ReportedAsSpam, true),
    Relat onsh pMapp ng(Relat onsh pType.ReportedAsSpamBy, true),
    Relat onsh pMapp ng(Relat onsh pType.ReportedAsAbuse, true),
    Relat onsh pMapp ng(Relat onsh pType.ReportedAsAbuseBy, true)
  )

  val  nval dRelat onsh pTypes: Seq[Relat onsh pMapp ng] = Seq(
    Relat onsh pMapp ng(Relat onsh pType.FollowRequestOutgo ng, true),
    Relat onsh pMapp ng(Relat onsh pType.Follow ng, true),
    Relat onsh pMapp ng(
      Relat onsh pType.UsedToFollow,
      true
    ) // t  data  s access ble for 90 days.
  ) ++  nval dRelat onsh pTypesExcludeFollow ng

  val NoteworthyAccounts nval dRelat onsh pTypes: Seq[Relat onsh pMapp ng] = Seq(
    Relat onsh pMapp ng(Relat onsh pType.Block ng, true),
    Relat onsh pMapp ng(Relat onsh pType.BlockedBy, true),
    Relat onsh pMapp ng(Relat onsh pType.Mut ng, true),
    Relat onsh pMapp ng(Relat onsh pType.MutedBy, true),
    Relat onsh pMapp ng(Relat onsh pType.ReportedAsSpam, true),
    Relat onsh pMapp ng(Relat onsh pType.ReportedAsSpamBy, true),
    Relat onsh pMapp ng(Relat onsh pType.ReportedAsAbuse, true),
    Relat onsh pMapp ng(Relat onsh pType.ReportedAsAbuseBy, true)
  )
}
