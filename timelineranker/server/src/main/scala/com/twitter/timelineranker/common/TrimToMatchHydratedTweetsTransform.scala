package com.tw ter.t  l neranker.common

 mport com.tw ter.search.earlyb rd.thr ftscala.Thr ftSearchResult
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l nes.model.t et.HydratedT et
 mport com.tw ter.ut l.Future

/**
 * tr ms searchResults to match w h hydratedT ets
 * ( f   prev ously f ltered out hydrated t ets, t  transform f lters t  search result set
 * down to match t  hydrated t ets.)
 */
object Tr mToMatchHydratedT etsTransform
    extends FutureArrow[Cand dateEnvelope, Cand dateEnvelope] {
  overr de def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {
    val f lteredSearchResults =
      tr mSearchResults(envelope.searchResults, envelope.hydratedT ets.outerT ets)
    val f lteredS ceSearchResults =
      tr mSearchResults(envelope.s ceSearchResults, envelope.s ceHydratedT ets.outerT ets)

    Future.value(
      envelope.copy(
        searchResults = f lteredSearchResults,
        s ceSearchResults = f lteredS ceSearchResults
      )
    )
  }

  pr vate def tr mSearchResults(
    searchResults: Seq[Thr ftSearchResult],
    hydratedT ets: Seq[HydratedT et]
  ): Seq[Thr ftSearchResult] = {
    val f lteredT et ds = hydratedT ets.map(_.t et d).toSet
    searchResults.f lter(result => f lteredT et ds.conta ns(result. d))
  }
}
