package com.tw ter.t  l neranker.common

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.search.earlyb rd.thr ftscala.Thr ftSearchResult
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l neranker.model.RecapQuery.DependencyProv der
 mport com.tw ter.t  l neranker.ut l.S ceT etsUt l
 mport com.tw ter.t  l nes.cl ents.relevance_search.SearchCl ent
 mport com.tw ter.t  l nes.ut l.Fa lOpenHandler
 mport com.tw ter.ut l.Future

object S ceT etsSearchResultsTransform {
  val EmptySearchResults: Seq[Thr ftSearchResult] = Seq.empty[Thr ftSearchResult]
  val EmptySearchResultsFuture: Future[Seq[Thr ftSearchResult]] = Future.value(EmptySearchResults)
}

/**
 * Fetch s ce t ets for a g ven set of search results
 * Collects  ds of s ce t ets,  nclud ng extended reply and reply s ce t ets  f needed,
 * fetc s those t ets from search and populates t m  nto t  envelope
 */
class S ceT etsSearchResultsTransform(
  searchCl ent: SearchCl ent,
  fa lOpenHandler: Fa lOpenHandler,
  hydrateReplyRootT etProv der: DependencyProv der[Boolean],
  perRequestS ceSearchCl ent dProv der: DependencyProv der[Opt on[Str ng]],
  statsRece ver: StatsRece ver)
    extends FutureArrow[Cand dateEnvelope, Cand dateEnvelope] {
   mport S ceT etsSearchResultsTransform._

  pr vate val scopedStatsRece ver = statsRece ver.scope(getClass.getS mpleNa )

  overr de def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {
    fa lOpenHandler {
      envelope.followGraphData.follo dUser dsFuture.flatMap { follo dUser ds =>
        // NOTE: t et ds are pre-computed as a performance opt m sat on.
        val searchResultsT et ds = envelope.searchResults.map(_. d).toSet
        val s ceT et ds = S ceT etsUt l.getS ceT et ds(
          searchResults = envelope.searchResults,
          searchResultsT et ds = searchResultsT et ds,
          follo dUser ds = follo dUser ds,
          should ncludeReplyRootT ets = hydrateReplyRootT etProv der(envelope.query),
          statsRece ver = scopedStatsRece ver
        )
         f (s ceT et ds. sEmpty) {
          EmptySearchResultsFuture
        } else {
          searchCl ent.getT etsScoredForRecap(
            user d = envelope.query.user d,
            t et ds = s ceT et ds,
            earlyb rdOpt ons = envelope.query.earlyb rdOpt ons,
            logSearchDebug nfo = false,
            searchCl ent d = perRequestS ceSearchCl ent dProv der(envelope.query)
          )
        }
      }
    } { _: Throwable => EmptySearchResultsFuture }.map { s ceSearchResults =>
      envelope.copy(s ceSearchResults = s ceSearchResults)
    }
  }
}
