package com.tw ter.t  l neranker.common

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l neranker.model.RecapQuery.DependencyProv der
 mport com.tw ter.t  l neranker.ut l.S ceT etsUt l
 mport com.tw ter.ut l.Future

/**
 * tr ms ele nts of t  envelope ot r than t  searchResults
 * ( .e. s ceSearchResults, hydratedT ets, s ceHydratedT ets) to match w h searchResults.
 */
class Tr mToMatchSearchResultsTransform(
  hydrateReplyRootT etProv der: DependencyProv der[Boolean],
  statsRece ver: StatsRece ver)
    extends FutureArrow[Cand dateEnvelope, Cand dateEnvelope] {

  pr vate val scopedStatsRece ver = statsRece ver.scope(getClass.getS mpleNa )

  overr de def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {
    val searchResults = envelope.searchResults
    val searchResults ds = searchResults.map(_. d).toSet

    // Tr m rest of t  seqs to match top search results.
    val hydratedT ets = envelope.hydratedT ets.outerT ets
    val topHydratedT ets = hydratedT ets.f lter(ht => searchResults ds.conta ns(ht.t et d))

    envelope.followGraphData.follo dUser dsFuture.map { follo dUser ds =>
      val s ceT et dsOfTopResults =
        S ceT etsUt l
          .getS ceT et ds(
            searchResults = searchResults,
            searchResultsT et ds = searchResults ds,
            follo dUser ds = follo dUser ds,
            should ncludeReplyRootT ets = hydrateReplyRootT etProv der(envelope.query),
            statsRece ver = scopedStatsRece ver
          ).toSet
      val s ceT etSearchResultsForTopN =
        envelope.s ceSearchResults.f lter(r => s ceT et dsOfTopResults.conta ns(r. d))
      val hydratedS ceT etsForTopN =
        envelope.s ceHydratedT ets.outerT ets.f lter(ht =>
          s ceT et dsOfTopResults.conta ns(ht.t et d))

      val hydratedT etsForEnvelope = envelope.hydratedT ets.copy(outerT ets = topHydratedT ets)
      val hydratedS ceT etsForEnvelope =
        envelope.s ceHydratedT ets.copy(outerT ets = hydratedS ceT etsForTopN)

      envelope.copy(
        hydratedT ets = hydratedT etsForEnvelope,
        searchResults = searchResults,
        s ceHydratedT ets = hydratedS ceT etsForEnvelope,
        s ceSearchResults = s ceT etSearchResultsForTopN
      )
    }
  }
}
