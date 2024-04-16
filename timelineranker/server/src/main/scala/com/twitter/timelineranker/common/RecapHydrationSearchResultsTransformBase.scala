package com.tw ter.t  l neranker.common

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l nes.cl ents.relevance_search.SearchCl ent
 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.ut l.Future

tra  RecapHydrat onSearchResultsTransformBase
    extends FutureArrow[Cand dateEnvelope, Cand dateEnvelope] {
  protected def statsRece ver: StatsRece ver
  protected def searchCl ent: SearchCl ent
  pr vate[t ] val numResultsFromSearchStat = statsRece ver.stat("numResultsFromSearch")

  def t et dsToHydrate(envelope: Cand dateEnvelope): Seq[T et d]

  overr de def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {
    searchCl ent
      .getT etsScoredForRecap(
        envelope.query.user d,
        t et dsToHydrate(envelope),
        envelope.query.earlyb rdOpt ons
      ).map { results =>
        numResultsFromSearchStat.add(results.s ze)
        envelope.copy(searchResults = results)
      }
  }
}
