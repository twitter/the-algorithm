package com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasRecentFollo dUser ds
 mport com.tw ter.follow_recom ndat ons.common.models.STPGraph
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.ut l.logg ng.Logg ng
 mport com.tw ter.wtf.scald ng.jobs.strong_t e_pred ct on.STPFeatureGenerator
 mport com.tw ter.wtf.scald ng.jobs.strong_t e_pred ct on.STPRecord

abstract class BaseOnl neSTPS ce(
  stpGraphBu lder: STPGraphBu lder,
  baseStatsRece ver: StatsRece ver)
    extends Cand dateS ce[
      HasCl entContext w h HasParams w h HasRecentFollo dUser ds,
      Cand dateUser
    ]
    w h Logg ng {

  protected val statsRece ver: StatsRece ver = baseStatsRece ver.scope("onl ne_stp")

  overr de val  dent f er: Cand dateS ce dent f er = BaseOnl neSTPS ce. dent f er

  def getCand dates(
    records: Seq[STPRecord],
    request: HasCl entContext w h HasParams w h HasRecentFollo dUser ds
  ): St ch[Seq[Cand dateUser]]

  overr de def apply(
    request: HasCl entContext w h HasParams w h HasRecentFollo dUser ds
  ): St ch[Seq[Cand dateUser]] =
    request.getOpt onalUser d
      .map { user d =>
        stpGraphBu lder(request)
          .flatMap { graph: STPGraph =>
            logger.debug(graph)
            val records = STPFeatureGenerator.constructFeatures(
              user d,
              graph.f rstDegreeEdge nfoL st,
              graph.secondDegreeEdge nfoL st)
            getCand dates(records, request)
          }
      }.getOrElse(St ch.N l)
}

object BaseOnl neSTPS ce {
  val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er(
    Algor hm.Onl neStrongT ePred ct onRecNoCach ng.toStr ng)
}
