package com.tw ter.t  l neranker.ent y_t ets

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l neranker.model.T et dRange
 mport com.tw ter.t  l nes.cl ents.relevance_search.SearchCl ent
 mport com.tw ter.t  l nes.cl ents.relevance_search.SearchCl ent.T etTypes
 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.ut l.Future

object Ent yT etsSearchResultsTransform {
  //  f Ent yT etsQuery.maxCount  s not spec f ed, t  follow ng count  s used.
  val DefaultEnt yT etsMaxT etCount = 200
}

/**
 * Fetch ent y t ets search results us ng t  search cl ent
 * and populate t m  nto t  Cand dateEnvelope
 */
class Ent yT etsSearchResultsTransform(
  searchCl ent: SearchCl ent,
  statsRece ver: StatsRece ver,
  logSearchDebug nfo: Boolean = false)
    extends FutureArrow[Cand dateEnvelope, Cand dateEnvelope] {
   mport Ent yT etsSearchResultsTransform._

  pr vate[t ] val maxCountStat = statsRece ver.stat("maxCount")
  pr vate[t ] val numResultsFromSearchStat = statsRece ver.stat("numResultsFromSearch")

  overr de def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {
    val maxCount = envelope.query.maxCount.getOrElse(DefaultEnt yT etsMaxT etCount)
    maxCountStat.add(maxCount)

    val t et dRange = envelope.query.range
      .map(T et dRange.fromT  l neRange)
      .getOrElse(T et dRange.default)

    val beforeT et dExclus ve = t et dRange.to d
    val afterT et dExclus ve = t et dRange.from d

    val excludedT et ds = envelope.query.excludedT et ds.getOrElse(Seq.empty[T et d]).toSet
    val languages = envelope.query.languages.map(_.map(_.language))

    envelope.followGraphData. nNetworkUser dsFuture.flatMap {  nNetworkUser ds =>
      searchCl ent
        .getEnt yT ets(
          user d = So (envelope.query.user d),
          follo dUser ds =  nNetworkUser ds.toSet,
          maxCount = maxCount,
          beforeT et dExclus ve = beforeT et dExclus ve,
          afterT et dExclus ve = afterT et dExclus ve,
          earlyb rdOpt ons = envelope.query.earlyb rdOpt ons,
          semant cCore ds = envelope.query.semant cCore ds,
          hashtags = envelope.query.hashtags,
          languages = languages,
          t etTypes = T etTypes.fromT etK ndOpt on(envelope.query.opt ons),
          searchOperator = envelope.query.searchOperator,
          excludedT et ds = excludedT et ds,
          logSearchDebug nfo = logSearchDebug nfo,
           ncludeNullcastT ets = envelope.query. ncludeNullcastT ets.getOrElse(false),
           ncludeT etsFromArch ve ndex =
            envelope.query. ncludeT etsFromArch ve ndex.getOrElse(false),
          author ds = envelope.query.author ds.map(_.toSet)
        ).map { results =>
          numResultsFromSearchStat.add(results.s ze)
          envelope.copy(searchResults = results)
        }
    }
  }
}
