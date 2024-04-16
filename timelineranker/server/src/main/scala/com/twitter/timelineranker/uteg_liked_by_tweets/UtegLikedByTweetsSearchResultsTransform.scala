package com.tw ter.t  l neranker.uteg_l ked_by_t ets

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.t  l neranker.common.RecapHydrat onSearchResultsTransformBase
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l neranker.model.RecapQuery.DependencyProv der
 mport com.tw ter.t  l nes.cl ents.relevance_search.SearchCl ent
 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.ut l.Future

class UtegL kedByT etsSearchResultsTransform(
  overr de protected val searchCl ent: SearchCl ent,
  overr de protected val statsRece ver: StatsRece ver,
  relevanceSearchProv der: DependencyProv der[Boolean])
    extends RecapHydrat onSearchResultsTransformBase {

  pr vate[t ] val numResultsFromSearchStat = statsRece ver.stat("numResultsFromSearch")

  overr de def t et dsToHydrate(envelope: Cand dateEnvelope): Seq[T et d] =
    envelope.utegResults.keys.toSeq

  overr de def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {
    searchCl ent
      .getT etsScoredForRecap(
        user d = envelope.query.user d,
        t et ds = t et dsToHydrate(envelope),
        earlyb rdOpt ons = envelope.query.earlyb rdOpt ons,
        logSearchDebug nfo = false,
        searchCl ent d = None,
        relevanceSearch = relevanceSearchProv der(envelope.query)
      ).map { results =>
        numResultsFromSearchStat.add(results.s ze)
        envelope.copy(searchResults = results)
      }
  }
}
