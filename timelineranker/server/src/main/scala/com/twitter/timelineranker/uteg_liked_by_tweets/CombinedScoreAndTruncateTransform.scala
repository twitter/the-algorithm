package com.tw ter.t  l neranker.uteg_l ked_by_t ets

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala.T etRecom ndat on
 mport com.tw ter.search.earlyb rd.thr ftscala.Thr ftSearchResult
 mport com.tw ter.search.earlyb rd.thr ftscala.Thr ftSearchResult tadata
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l neranker.model.RecapQuery.DependencyProv der
 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.ut l.Future

object Comb nedScoreAndTruncateTransform {
  val DefaultRealGraph  ght = 1.0
  val DefaultEmptyScore = 0.0
}

/**
 * Rank and truncate search results accord ng to
 * DefaultRealGraph  ght * real_graph_score + earlyb rd_score_mult pl er * earlyb rd_score
 * Note: scor ng and truncat on only appl es to out of network cand dates
 */
class Comb nedScoreAndTruncateTransform(
  maxT etCountProv der: DependencyProv der[ nt],
  earlyb rdScoreMult pl erProv der: DependencyProv der[Double],
  numAdd  onalRepl esProv der: DependencyProv der[ nt],
  statsRece ver: StatsRece ver)
    extends FutureArrow[Cand dateEnvelope, Cand dateEnvelope] {
   mport Comb nedScoreAndTruncateTransform._

  pr vate[t ] val scopedStatsRece ver = statsRece ver.scope("Comb nedScoreAndTruncateTransform")
  pr vate[t ] val earlyb rdScoreX100Stat = scopedStatsRece ver.stat("earlyb rdScoreX100")
  pr vate[t ] val realGraphScoreX100Stat = scopedStatsRece ver.stat("realGraphScoreX100")
  pr vate[t ] val add  onalReplyCounter = scopedStatsRece ver.counter("add  onalRepl es")
  pr vate[t ] val resultCounter = scopedStatsRece ver.counter("results")

  pr vate[t ] def getRealGraphScore(
    searchResult: Thr ftSearchResult,
    utegResults: Map[T et d, T etRecom ndat on]
  ): Double = {
    utegResults.get(searchResult. d).map(_.score).getOrElse(DefaultEmptyScore)
  }

  pr vate[t ] def getEarlyb rdScore( tadataOpt: Opt on[Thr ftSearchResult tadata]): Double = {
     tadataOpt
      .flatMap( tadata =>  tadata.score)
      .getOrElse(DefaultEmptyScore)
  }

  overr de def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {
    val maxCount = maxT etCountProv der(envelope.query)
    val earlyb rdScoreMult pl er = earlyb rdScoreMult pl erProv der(envelope.query)
    val realGraphScoreMult pl er = DefaultRealGraph  ght

    val searchResultsAndScore = envelope.searchResults.map { searchResult =>
      val realGraphScore = getRealGraphScore(searchResult, envelope.utegResults)
      val earlyb rdScore = getEarlyb rdScore(searchResult. tadata)
      earlyb rdScoreX100Stat.add(earlyb rdScore.toFloat * 100)
      realGraphScoreX100Stat.add(realGraphScore.toFloat * 100)
      val comb nedScore =
        realGraphScoreMult pl er * realGraphScore + earlyb rdScoreMult pl er * earlyb rdScore
      (searchResult, comb nedScore)
    }

    // set as de results that are marked by  sRandomT et f eld
    val (randomSearchResults, ot rSearchResults) = searchResultsAndScore.part  on {
      resultAndScore =>
        resultAndScore._1.t etFeatures.flatMap(_. sRandomT et).getOrElse(false)
    }

    val (topResults, rema n ngResults) = ot rSearchResults
      .sortBy(_._2)(Order ng[Double].reverse).map(_._1).spl At(
        maxCount - randomSearchResults.length)

    val numAdd  onalRepl es = numAdd  onalRepl esProv der(envelope.query)
    val add  onalRepl es = {
       f (numAdd  onalRepl es > 0) {
        val replyT et dSet =
          envelope.hydratedT ets.outerT ets.f lter(_.hasReply).map(_.t et d).toSet
        rema n ngResults.f lter(result => replyT et dSet(result. d)).take(numAdd  onalRepl es)
      } else {
        Seq.empty
      }
    }

    val transfor dSearchResults =
      topResults ++ add  onalRepl es ++ randomSearchResults
        .map(_._1)

    resultCounter. ncr(transfor dSearchResults.s ze)
    add  onalReplyCounter. ncr(add  onalRepl es.s ze)

    Future.value(envelope.copy(searchResults = transfor dSearchResults))
  }
}
