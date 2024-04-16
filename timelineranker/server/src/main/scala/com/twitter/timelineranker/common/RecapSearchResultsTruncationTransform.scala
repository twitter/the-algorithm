package com.tw ter.t  l neranker.common

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l neranker.model.RecapQuery.DependencyProv der
 mport com.tw ter.t  l neranker.ut l.SearchResultUt l
 mport com.tw ter.ut l.Future

/**
 * Truncate t  search results by score. Assu s that t  search results are sorted  n
 * score-descend ng order unless extraSortBeforeTruncat on  s set to true.
 *
 * T  transform has two ma n use cases:
 *
 * - w n returnAllResults  s set to true, earlyb rd returns (numResultsPerShard * number of shards)
 *   results. t  transform  s t n used to furt r truncate t  result, so that t  s ze w ll be t 
 *   sa  as w n returnAllResults  s set to false.
 *
 * -   retr eve extra number of results from earlyb rd, as spec f ed  n MaxCountMult pl erParam,
 *   so that   are left w h suff c ent number of cand dates after hydrat on and f lter ng.
 *   t  transform w ll be used to get r d of extra results   ended up not us ng.
 */
class RecapSearchResultsTruncat onTransform(
  extraSortBeforeTruncat onGate: DependencyProv der[Boolean],
  maxCountProv der: DependencyProv der[ nt],
  statsRece ver: StatsRece ver)
    extends FutureArrow[Cand dateEnvelope, Cand dateEnvelope] {
  pr vate[t ] val postTruncat onS zeStat = statsRece ver.stat("postTruncat onS ze")
  pr vate[t ] val earlyb rdScoreX100Stat = statsRece ver.stat("earlyb rdScoreX100")

  overr de def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {
    val sortBeforeTruncat on = extraSortBeforeTruncat onGate(envelope.query)
    val maxCount = maxCountProv der(envelope.query)
    val searchResults = envelope.searchResults

    // set as de results that are marked by  sRandomT et f eld
    val (randomT etSeq, searchResultsExclud ngRandom) = searchResults.part  on { result =>
      result.t etFeatures.flatMap(_. sRandomT et).getOrElse(false)
    }

    // sort and truncate searchResults ot r than t  random t et
    val maxCountExclud ngRandom = Math.max(0, maxCount - randomT etSeq.s ze)

    val truncatedResultsExclud ngRandom =
       f (sortBeforeTruncat on || searchResultsExclud ngRandom.s ze > maxCountExclud ngRandom) {
        val sorted =  f (sortBeforeTruncat on) {
          searchResultsExclud ngRandom.sortW h(
            SearchResultUt l.getScore(_) > SearchResultUt l.getScore(_))
        } else searchResultsExclud ngRandom
        sorted.take(maxCountExclud ngRandom)
      } else searchResultsExclud ngRandom

    // put back t  random t et set as de prev ously
    val allTruncatedResults = truncatedResultsExclud ngRandom ++ randomT etSeq

    // stats
    postTruncat onS zeStat.add(allTruncatedResults.s ze)
    allTruncatedResults.foreach { result =>
      val earlyb rdScoreX100 =
        result. tadata.flatMap(_.score).getOrElse(0.0).toFloat * 100
      earlyb rdScoreX100Stat.add(earlyb rdScoreX100)
    }

    Future.value(envelope.copy(searchResults = allTruncatedResults))
  }
}
