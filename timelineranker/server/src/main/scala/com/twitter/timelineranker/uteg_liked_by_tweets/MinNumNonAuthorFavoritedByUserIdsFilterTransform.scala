package com.tw ter.t  l neranker.uteg_l ked_by_t ets

 mport com.tw ter.recos.recos_common.thr ftscala.Soc alProofType
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala.T etRecom ndat on
 mport com.tw ter.search.earlyb rd.thr ftscala.Thr ftSearchResult
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l neranker.model.RecapQuery.DependencyProv der
 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.ut l.Future

class M nNumNonAuthorFavor edByUser dsF lterTransform(
  m nNumFavor edByUser dsProv der: DependencyProv der[ nt])
    extends FutureArrow[Cand dateEnvelope, Cand dateEnvelope] {

  overr de def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {
    val f lteredSearchResults = envelope.searchResults.f lter { searchResult =>
      numNonAuthorFavs(
        searchResult = searchResult,
        utegResultsMap = envelope.utegResults
      ).ex sts(_ >= m nNumFavor edByUser dsProv der(envelope.query))
    }
    Future.value(envelope.copy(searchResults = f lteredSearchResults))
  }

  // return number of non-author users that faved t  t et  n a searchResult
  // return None  f author  s None or  f t  t et  s not found  n utegResultsMap
  protected def numNonAuthorFavs(
    searchResult: Thr ftSearchResult,
    utegResultsMap: Map[T et d, T etRecom ndat on]
  ): Opt on[ nt] = {
    for {
       tadata <- searchResult. tadata
      author d =  tadata.fromUser d
      t etRecom ndat on <- utegResultsMap.get(searchResult. d)
      favedByUser ds <- t etRecom ndat on.soc alProofByType.get(Soc alProofType.Favor e)
    } y eld favedByUser ds.f lterNot(_ == author d).s ze
  }
}
