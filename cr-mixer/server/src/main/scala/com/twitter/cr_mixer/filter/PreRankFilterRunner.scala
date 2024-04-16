package com.tw ter.cr_m xer.f lter

 mport com.tw ter.cr_m xer.model.Cand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model. n  alCand date
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ut l.Future
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class PreRankF lterRunner @ nject() (
   mpressedT etL stF lter:  mpressedT etl stF lter,
  t etAgeF lter: T etAgeF lter,
  v deoT etF lter: V deoT etF lter,
  t etReplyF lter: ReplyF lter,
  globalStats: StatsRece ver) {

  pr vate val scopedStats = globalStats.scope(t .getClass.getCanon calNa )

  /***
   * T  order of t  f lters does not matter as long as   do not apply .take(N) truncat on
   * across all f lters.  n ot r words,    s f ne that   f rst do t etAgeF lter, and t n
   *   do  mpressedT etL stF lter, or t  ot r way around.
   * Sa   dea appl es to t  s gnal based f lter -    s ok that   apply s gnal based f lters
   * before  mpressedT etL stF lter.
   *
   *   move all s gnal based f lters before t etAgeF lter and  mpressedT etL stF lter
   * as a set of early f lters.
   */
  val orderedF lters = Seq(
    t etAgeF lter,
     mpressedT etL stF lter,
    v deoT etF lter,
    t etReplyF lter
  )

  def runSequent alF lters[CGQueryType <: Cand dateGeneratorQuery](
    request: CGQueryType,
    cand dates: Seq[Seq[ n  alCand date]],
  ): Future[Seq[Seq[ n  alCand date]]] = {
    PreRankF lterRunner.runSequent alF lters(
      request,
      cand dates,
      orderedF lters,
      scopedStats
    )
  }

}

object PreRankF lterRunner {
  pr vate def recordCand dateStatsBeforeF lter(
    cand dates: Seq[Seq[ n  alCand date]],
    statsRece ver: StatsRece ver
  ): Un  = {
    statsRece ver
      .counter("empty_s ces", "before"). ncr(
        cand dates.count { _. sEmpty }
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
        cand dates.count { _. sEmpty }
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
