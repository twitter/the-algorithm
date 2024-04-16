package com.tw ter.t  l neranker.recap_hydrat on

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.t  l neranker.common.RecapHydrat onSearchResultsTransformBase
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l nes.cl ents.relevance_search.SearchCl ent
 mport com.tw ter.t  l nes.model.T et d

class RecapHydrat onSearchResultsTransform(
  overr de protected val searchCl ent: SearchCl ent,
  overr de protected val statsRece ver: StatsRece ver)
    extends RecapHydrat onSearchResultsTransformBase {
  overr de def t et dsToHydrate(envelope: Cand dateEnvelope): Seq[T et d] =
    envelope.query.t et ds.get
}
