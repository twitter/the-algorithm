package com.tw ter.cr_m xer.ranker

 mport com.tw ter.cr_m xer.model.BlendedCand date
 mport com.tw ter.cr_m xer.model.RankedCand date
 mport com.tw ter.ut l.Future
 mport javax. nject.S ngleton

/**
 * Keep t  sa  order as t   nput.
 */
@S ngleton
class DefaultRanker() {
  def rank(
    cand dates: Seq[BlendedCand date],
  ): Future[Seq[RankedCand date]] = {
    val cand dateS ze = cand dates.s ze
    val rankedCand dates = cand dates.z pW h ndex.map {
      case (cand date,  ndex) =>
        cand date.toRankedCand date((cand dateS ze -  ndex).toDouble)
    }
    Future.value(rankedCand dates)
  }
}
