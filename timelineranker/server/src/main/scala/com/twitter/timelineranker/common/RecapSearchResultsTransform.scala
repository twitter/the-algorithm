package com.tw ter.t  l neranker.common

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l neranker.model.RecapQuery.DependencyProv der
 mport com.tw ter.t  l neranker.model.T et dRange
 mport com.tw ter.t  l neranker.para ters.recap.RecapParams
 mport com.tw ter.t  l nes.cl ents.relevance_search.SearchCl ent
 mport com.tw ter.t  l nes.cl ents.relevance_search.SearchCl ent.T etTypes
 mport com.tw ter.ut l.Future

/**
 * Fetch recap/recycled search results us ng t  search cl ent
 * and populate t m  nto t  Cand dateEnvelope
 */
class RecapSearchResultsTransform(
  searchCl ent: SearchCl ent,
  maxCountProv der: DependencyProv der[ nt],
  returnAllResultsProv der: DependencyProv der[Boolean],
  relevanceOpt onsMaxH sToProcessProv der: DependencyProv der[ nt],
  enableExcludeS ceT et dsProv der: DependencyProv der[Boolean],
  enableSett ngT etTypesW hT etK ndOpt onProv der: DependencyProv der[Boolean],
  perRequestSearchCl ent dProv der: DependencyProv der[Opt on[Str ng]],
  relevanceSearchProv der: DependencyProv der[Boolean] =
    DependencyProv der.from(RecapParams.EnableRelevanceSearchParam),
  statsRece ver: StatsRece ver,
  logSearchDebug nfo: Boolean = true)
    extends FutureArrow[Cand dateEnvelope, Cand dateEnvelope] {
  pr vate[t ] val maxCountStat = statsRece ver.stat("maxCount")
  pr vate[t ] val numResultsFromSearchStat = statsRece ver.stat("numResultsFromSearch")
  pr vate[t ] val excludedT et dsStat = statsRece ver.stat("excludedT et ds")

  overr de def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {
    val maxCount = maxCountProv der(envelope.query)
    maxCountStat.add(maxCount)

    val excludedT et dsOpt = envelope.query.excludedT et ds
    excludedT et dsOpt.foreach { excludedT et ds =>
      excludedT et dsStat.add(excludedT et ds.s ze)
    }

    val t et dRange = envelope.query.range
      .map(T et dRange.fromT  l neRange)
      .getOrElse(T et dRange.default)

    val beforeT et dExclus ve = t et dRange.to d
    val afterT et dExclus ve = t et dRange.from d

    val returnAllResults = returnAllResultsProv der(envelope.query)
    val relevanceOpt onsMaxH sToProcess = relevanceOpt onsMaxH sToProcessProv der(envelope.query)

    Future
      .jo n(
        envelope.followGraphData.follo dUser dsFuture,
        envelope.followGraphData.ret etsMutedUser dsFuture
      ).flatMap {
        case (follo d ds, ret etsMuted ds) =>
          val follo d ds nclud ngSelf = follo d ds.toSet + envelope.query.user d

          searchCl ent
            .getUsersT etsForRecap(
              user d = envelope.query.user d,
              follo dUser ds = follo d ds nclud ngSelf,
              ret etsMutedUser ds = ret etsMuted ds,
              maxCount = maxCount,
              t etTypes = T etTypes.fromT etK ndOpt on(envelope.query.opt ons),
              searchOperator = envelope.query.searchOperator,
              beforeT et dExclus ve = beforeT et dExclus ve,
              afterT et dExclus ve = afterT et dExclus ve,
              enableSett ngT etTypesW hT etK ndOpt on =
                enableSett ngT etTypesW hT etK ndOpt onProv der(envelope.query),
              excludedT et ds = excludedT et dsOpt,
              earlyb rdOpt ons = envelope.query.earlyb rdOpt ons,
              getOnlyProtectedT ets = false,
              logSearchDebug nfo = logSearchDebug nfo,
              returnAllResults = returnAllResults,
              enableExcludeS ceT et dsQuery =
                enableExcludeS ceT et dsProv der(envelope.query),
              relevanceSearch = relevanceSearchProv der(envelope.query),
              searchCl ent d = perRequestSearchCl ent dProv der(envelope.query),
              relevanceOpt onsMaxH sToProcess = relevanceOpt onsMaxH sToProcess
            ).map { results =>
              numResultsFromSearchStat.add(results.s ze)
              envelope.copy(searchResults = results)
            }
      }
  }
}
