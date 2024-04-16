package com.tw ter.follow_recom ndat ons.common.base

 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.st ch.St ch

/**
 * S deEffectsUt l appl es s de effects to t   nter d ate cand date results from a recom ndat on flow p pel ne.
 *
 * @tparam Target target to recom nd t  cand dates
 * @tparam Cand date cand date type to rank
 */
tra  S deEffectsUt l[Target, Cand date] {
  def applyS deEffects(
    target: Target,
    cand dateS ces: Seq[Cand dateS ce[Target, Cand date]],
    cand datesFromCand dateS ces: Seq[Cand date],
     rgedCand dates: Seq[Cand date],
    f lteredCand dates: Seq[Cand date],
    rankedCand dates: Seq[Cand date],
    transfor dCand dates: Seq[Cand date],
    truncatedCand dates: Seq[Cand date],
    results: Seq[Cand date]
  ): St ch[Un ] = St ch.Un 
}
