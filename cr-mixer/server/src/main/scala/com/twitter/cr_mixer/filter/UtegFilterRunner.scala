package com.tw ter.cr_m xer.f lter

 mport com.tw ter.cr_m xer.model.Cand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model. n  alCand date
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ut l.Future

 mport javax. nject. nject
 mport javax. nject.S ngleton

/***
 *
 * Run f lters sequent ally for UTEG cand date generator. T  structure  s cop ed from PreRankF lterRunner.
 */
@S ngleton
class UtegF lterRunner @ nject() (
   nNetworkF lter:  nNetworkF lter,
  uteg althF lter: Uteg althF lter,
  ret etF lter: Ret etF lter,
  globalStats: StatsRece ver) {

  pr vate val scopedStats = globalStats.scope(t .getClass.getCanon calNa )

  val orderedF lters: Seq[F lterBase] = Seq(
     nNetworkF lter,
    uteg althF lter,
    ret etF lter
  )

  def runSequent alF lters[CGQueryType <: Cand dateGeneratorQuery](
    request: CGQueryType,
    cand dates: Seq[Seq[ n  alCand date]],
  ): Future[Seq[Seq[ n  alCand date]]] = {
    UtegF lterRunner.runSequent alF lters(
      request,
      cand dates,
      orderedF lters,
      scopedStats
    )
  }

}

object UtegF lterRunner {
  pr vate def recordCand dateStatsBeforeF lter(
    cand dates: Seq[Seq[ n  alCand date]],
    statsRece ver: StatsRece ver
  ): Un  = {
    statsRece ver
      .counter("empty_s ces", "before"). ncr(
        cand dates.count {
          _. sEmpty
        }
      )
    cand dates.foreach { cand date =>
      statsRece ver.counter("cand dates", "before"). ncr(cand date.s ze)
    }
  }

  pr vate def recordCand dateStatsAfterF lter(
    cand dates: Seq[Seq[ n  alCand date]],
    statsRece ver: StatsRece ver
  ): Un  = {
    statsRece ver
      .counter("empty_s ces", "after"). ncr(
        cand dates.count {
          _. sEmpty
        }
      )
    cand dates.foreach { cand date =>
      statsRece ver.counter("cand dates", "after"). ncr(cand date.s ze)
    }
  }

  /*
   lper funct on for runn ng so  cand dates through a sequence of f lters
   */
  pr vate[f lter] def runSequent alF lters[CGQueryType <: Cand dateGeneratorQuery](
    request: CGQueryType,
    cand dates: Seq[Seq[ n  alCand date]],
    f lters: Seq[F lterBase],
    statsRece ver: StatsRece ver
  ): Future[Seq[Seq[ n  alCand date]]] =
    f lters.foldLeft(Future.value(cand dates)) {
      case (candsFut, f lter) =>
        candsFut.flatMap { cands =>
          recordCand dateStatsBeforeF lter(cands, statsRece ver.scope(f lter.na ))
          f lter
            .f lter(cands, f lter.requestToConf g(request))
            .map { f lteredCands =>
              recordCand dateStatsAfterF lter(f lteredCands, statsRece ver.scope(f lter.na ))
              f lteredCands
            }
        }
    }
}
