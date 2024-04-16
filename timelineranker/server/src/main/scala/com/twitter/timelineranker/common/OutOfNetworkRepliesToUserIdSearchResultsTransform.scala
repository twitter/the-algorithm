package com.tw ter.t  l neranker.common

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l nes.cl ents.relevance_search.SearchCl ent
 mport com.tw ter.ut l.Future

object OutOfNetworkRepl esToUser dSearchResultsTransform {
  val DefaultMaxT etCount = 100
}

// Requests search results for out-of-network repl es to a user  d
class OutOfNetworkRepl esToUser dSearchResultsTransform(
  searchCl ent: SearchCl ent,
  statsRece ver: StatsRece ver,
  logSearchDebug nfo: Boolean = true)
    extends FutureArrow[Cand dateEnvelope, Cand dateEnvelope] {
  pr vate[t ] val maxCountStat = statsRece ver.stat("maxCount")
  pr vate[t ] val numResultsFromSearchStat = statsRece ver.stat("numResultsFromSearch")
  pr vate[t ] val earlyb rdScoreX100Stat = statsRece ver.stat("earlyb rdScoreX100")

  overr de def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {
    val maxCount = envelope.query.maxCount
      .getOrElse(OutOfNetworkRepl esToUser dSearchResultsTransform.DefaultMaxT etCount)
    maxCountStat.add(maxCount)

    envelope.followGraphData.follo dUser dsFuture
      .flatMap {
        case follo d ds =>
          searchCl ent
            .getOutOfNetworkRepl esToUser d(
              user d = envelope.query.user d,
              follo dUser ds = follo d ds.toSet,
              maxCount = maxCount,
              earlyb rdOpt ons = envelope.query.earlyb rdOpt ons,
              logSearchDebug nfo
            ).map { results =>
              numResultsFromSearchStat.add(results.s ze)
              results.foreach { result =>
                val earlyb rdScoreX100 =
                  result. tadata.flatMap(_.score).getOrElse(0.0).toFloat * 100
                earlyb rdScoreX100Stat.add(earlyb rdScoreX100)
              }
              envelope.copy(searchResults = results)
            }
      }
  }
}
