package com.tw ter.t  l neranker.recap_author

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l neranker.model.RecapQuery.DependencyProv der
 mport com.tw ter.t  l neranker.model.T et dRange
 mport com.tw ter.t  l nes.cl ents.relevance_search.SearchCl ent
 mport com.tw ter.t  l nes.cl ents.relevance_search.SearchCl ent.T etTypes
 mport com.tw ter.t  l nes.model.User d
 mport com.tw ter.ut l.Future

/**
 * Fetch recap results based on an author  d set passed  nto t  query.
 * Calls  nto t  sa  search  thod as Recap, but uses t  author ds  nstead of t  SGS-prov ded follo d ds.
 */
class RecapAuthorSearchResultsTransform(
  searchCl ent: SearchCl ent,
  maxCountProv der: DependencyProv der[ nt],
  relevanceOpt onsMaxH sToProcessProv der: DependencyProv der[ nt],
  enableSett ngT etTypesW hT etK ndOpt onProv der: DependencyProv der[Boolean],
  statsRece ver: StatsRece ver,
  logSearchDebug nfo: Boolean = false)
    extends FutureArrow[Cand dateEnvelope, Cand dateEnvelope] {
  pr vate[t ] val maxCountStat = statsRece ver.stat("maxCount")
  pr vate[t ] val num nputAuthorsStat = statsRece ver.stat("num nputAuthors")
  pr vate[t ] val excludedT et dsStat = statsRece ver.stat("excludedT et ds")

  overr de def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {
    val maxCount = maxCountProv der(envelope.query)
    maxCountStat.add(maxCount)

    val author ds = envelope.query.author ds.getOrElse(Seq.empty[User d])
    num nputAuthorsStat.add(author ds.s ze)

    val excludedT et dsOpt = envelope.query.excludedT et ds
    excludedT et dsOpt.map { excludedT et ds => excludedT et dsStat.add(excludedT et ds.s ze) }

    val t et dRange = envelope.query.range
      .map(T et dRange.fromT  l neRange)
      .getOrElse(T et dRange.default)

    val beforeT et dExclus ve = t et dRange.to d
    val afterT et dExclus ve = t et dRange.from d

    val relevanceOpt onsMaxH sToProcess = relevanceOpt onsMaxH sToProcessProv der(envelope.query)

    searchCl ent
      .getUsersT etsForRecap(
        user d = envelope.query.user d,
        follo dUser ds = author ds.toSet, // user author ds as t  set of follo d users
        ret etsMutedUser ds = Set.empty,
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
        returnAllResults = true,
        enableExcludeS ceT et dsQuery = false,
        relevanceOpt onsMaxH sToProcess = relevanceOpt onsMaxH sToProcess
      ).map { results => envelope.copy(searchResults = results) }
  }
}
