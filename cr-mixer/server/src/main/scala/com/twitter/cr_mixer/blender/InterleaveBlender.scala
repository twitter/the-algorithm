package com.tw ter.cr_m xer.blender

 mport com.tw ter.cr_m xer.model.BlendedCand date
 mport com.tw ter.cr_m xer.model. n  alCand date
 mport com.tw ter.cr_m xer.ut l. nterleaveUt l
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ut l.Future
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class  nterleaveBlender @ nject() (globalStats: StatsRece ver) {

  pr vate val na : Str ng = t .getClass.getCanon calNa 
  pr vate val stats: StatsRece ver = globalStats.scope(na )

  /**
   *  nterleaves cand dates, by tak ng 1 cand date from each Seq[Seq[ n  alCand date]]  n sequence,
   * unt l   run out of cand dates.
   */
  def blend(
     nputCand dates: Seq[Seq[ n  alCand date]],
  ): Future[Seq[BlendedCand date]] = {

    val  nterleavedCand dates =  nterleaveUt l. nterleave( nputCand dates)

    stats.stat("cand dates").add( nterleavedCand dates.s ze)

    val blendedCand dates = BlendedCand datesBu lder.bu ld( nputCand dates,  nterleavedCand dates)
    Future.value(blendedCand dates)
  }

}
