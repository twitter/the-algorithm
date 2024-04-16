package com.tw ter.t  l neranker.uteg_l ked_by_t ets

 mport com.tw ter.search.earlyb rd.thr ftscala.Thr ftSearchResult
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l nes.model.User d
 mport com.tw ter.ut l.Future

object RemoveCand datesAuthoredBy  ghtedFollow ngsTransform
    extends FutureArrow[Cand dateEnvelope, Cand dateEnvelope] {
  overr de def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {
    val f lteredSearchResults = envelope.query.utegL kedByT etsOpt ons match {
      case So (opts) =>
        envelope.searchResults.f lterNot( sAuthor n  ghtedFollow ngs(_, opts.  ghtedFollow ngs))
      case None => envelope.searchResults
    }
    Future.value(envelope.copy(searchResults = f lteredSearchResults))
  }

  pr vate def  sAuthor n  ghtedFollow ngs(
    searchResult: Thr ftSearchResult,
      ghtedFollow ngs: Map[User d, Double]
  ): Boolean = {
    searchResult. tadata match {
      case So ( tadata) =>   ghtedFollow ngs.conta ns( tadata.fromUser d)
      case None => false
    }
  }
}
