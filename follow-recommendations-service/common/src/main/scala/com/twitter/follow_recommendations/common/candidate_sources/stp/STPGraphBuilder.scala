package com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp

 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.models.HasRecentFollo dUser ds
 mport com.tw ter.follow_recom ndat ons.common.models.STPGraph
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class STPGraphBu lder @ nject() (
  stpF rstDegreeFetc r: STPF rstDegreeFetc r,
  stpSecondDegreeFetc r: STPSecondDegreeFetc r,
  statsRece ver: StatsRece ver) {
  pr vate val stats: StatsRece ver = statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val f rstDegreeStat: Stat = stats.stat("f rst_degree_edges")
  pr vate val secondDegreeStat: Stat = stats.stat("second_degree_edges")
  def apply(
    target: HasCl entContext w h HasParams w h HasRecentFollo dUser ds
  ): St ch[STPGraph] = stpF rstDegreeFetc r
    .getF rstDegreeEdges(target).flatMap { f rstDegreeEdges =>
      f rstDegreeStat.add(f rstDegreeEdges.s ze)
      stpSecondDegreeFetc r
        .getSecondDegreeEdges(target, f rstDegreeEdges).map { secondDegreeEdges =>
          secondDegreeStat.add(f rstDegreeEdges.s ze)
          STPGraph(f rstDegreeEdges.toL st, secondDegreeEdges.toL st)
        }
    }
}
