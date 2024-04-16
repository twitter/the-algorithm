package com.tw ter.t  l neranker.ut l

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.search.earlyb rd.thr ftscala.Thr ftSearchResult
 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.t  l nes.model.User d

object S ceT etsUt l {
  def getS ceT et ds(
    searchResults: Seq[Thr ftSearchResult],
    searchResultsT et ds: Set[T et d],
    follo dUser ds: Seq[T et d],
    should ncludeReplyRootT ets: Boolean,
    statsRece ver: StatsRece ver
  ): Seq[T et d] = {
    val replyRootT etCounter = statsRece ver.counter("replyRootT et")

    val ret etS ceT et ds = getRet etS ceT et ds(searchResults, searchResultsT et ds)

    val  nNetworkReply nReplyToT et ds = get nNetwork nReplyToT et ds(
      searchResults,
      searchResultsT et ds,
      follo dUser ds
    )

    val extendedRepl esS ceT et ds = getExtendedReplyS ceT et ds(
      searchResults,
      searchResultsT et ds,
      follo dUser ds
    )

    val replyRootT et ds =  f (should ncludeReplyRootT ets) {
      val rootT et ds = getReplyRootT et ds(
        searchResults,
        searchResultsT et ds
      )
      replyRootT etCounter. ncr(rootT et ds.s ze)

      rootT et ds
    } else {
      Seq.empty
    }

    (ret etS ceT et ds ++ extendedRepl esS ceT et ds ++
       nNetworkReply nReplyToT et ds ++ replyRootT et ds).d st nct
  }

  def get nNetwork nReplyToT et ds(
    searchResults: Seq[Thr ftSearchResult],
    searchResultsT et ds: Set[T et d],
    follo dUser ds: Seq[User d]
  ): Seq[T et d] = {
    searchResults
      .f lter(SearchResultUt l. s nNetworkReply(follo dUser ds))
      .flatMap(SearchResultUt l.getS ceT et d)
      .f lterNot(searchResultsT et ds.conta ns)
  }

  def getReplyRootT et ds(
    searchResults: Seq[Thr ftSearchResult],
    searchResultsT et ds: Set[T et d]
  ): Seq[T et d] = {
    searchResults
      .flatMap(SearchResultUt l.getReplyRootT et d)
      .f lterNot(searchResultsT et ds.conta ns)
  }

  def getRet etS ceT et ds(
    searchResults: Seq[Thr ftSearchResult],
    searchResultsT et ds: Set[T et d]
  ): Seq[T et d] = {
    searchResults
      .f lter(SearchResultUt l. sRet et)
      .flatMap(SearchResultUt l.getS ceT et d)
      .f lterNot(searchResultsT et ds.conta ns)
  }

  def getExtendedReplyS ceT et ds(
    searchResults: Seq[Thr ftSearchResult],
    searchResultsT et ds: Set[T et d],
    follo dUser ds: Seq[User d]
  ): Seq[T et d] = {
    searchResults
      .f lter(SearchResultUt l. sExtendedReply(follo dUser ds))
      .flatMap(SearchResultUt l.getS ceT et d)
      .f lterNot(searchResultsT et ds.conta ns)
  }
}
