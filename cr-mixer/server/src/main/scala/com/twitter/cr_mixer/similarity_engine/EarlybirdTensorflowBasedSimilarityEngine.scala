package com.tw ter.cr_m xer.s m lar y_eng ne

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.search.earlyb rd.thr ftscala.Earlyb rdRequest
 mport com.tw ter.search.earlyb rd.thr ftscala.Earlyb rdServ ce
 mport com.tw ter.search.earlyb rd.thr ftscala.Thr ftSearchQuery
 mport com.tw ter.ut l.T  
 mport com.tw ter.search.common.query.thr ftjava.thr ftscala.CollectorParams
 mport com.tw ter.search.common.rank ng.thr ftscala.Thr ftRank ngParams
 mport com.tw ter.search.common.rank ng.thr ftscala.Thr ftScor ngFunct onType
 mport com.tw ter.search.earlyb rd.thr ftscala.Thr ftSearchRelevanceOpt ons
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport Earlyb rdS m lar yEng neBase._
 mport com.tw ter.cr_m xer.conf g.T  outConf g
 mport com.tw ter.cr_m xer.s m lar y_eng ne.Earlyb rdTensorflowBasedS m lar yEng ne.Earlyb rdTensorflowBasedSearchQuery
 mport com.tw ter.cr_m xer.ut l.Earlyb rdSearchUt l.Earlyb rdCl ent d
 mport com.tw ter.cr_m xer.ut l.Earlyb rdSearchUt l.FacetsToFetch
 mport com.tw ter.cr_m xer.ut l.Earlyb rdSearchUt l.GetCollectorTerm nat onParams
 mport com.tw ter.cr_m xer.ut l.Earlyb rdSearchUt l.GetEarlyb rdQuery
 mport com.tw ter.cr_m xer.ut l.Earlyb rdSearchUt l. tadataOpt ons
 mport com.tw ter.cr_m xer.ut l.Earlyb rdSearchUt l.GetNa dD sjunct ons
 mport com.tw ter.search.earlyb rd.thr ftscala.Thr ftSearchRank ngMode
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.ut l.Durat on

@S ngleton
case class Earlyb rdTensorflowBasedS m lar yEng ne @ nject() (
  earlyb rdSearchCl ent: Earlyb rdServ ce. thodPerEndpo nt,
  t  outConf g: T  outConf g,
  stats: StatsRece ver)
    extends Earlyb rdS m lar yEng neBase[Earlyb rdTensorflowBasedSearchQuery] {
   mport Earlyb rdTensorflowBasedS m lar yEng ne._
  overr de val statsRece ver: StatsRece ver = stats.scope(t .getClass.getS mpleNa )
  overr de def getEarlyb rdRequest(
    query: Earlyb rdTensorflowBasedSearchQuery
  ): Opt on[Earlyb rdRequest] = {
     f (query.seedUser ds.nonEmpty)
      So (
        Earlyb rdRequest(
          searchQuery = getThr ftSearchQuery(query, t  outConf g.earlyb rdServerT  out),
          cl entHost = None,
          cl entRequest D = None,
          cl ent d = So (Earlyb rdCl ent d),
          cl entRequestT  Ms = So (T  .now. nM ll seconds),
          cach ngParams = None,
          t  outMs = t  outConf g.earlyb rdServerT  out. nM ll seconds. ntValue(),
          facetRequest = None,
          termStat st csRequest = None,
          debugMode = 0,
          debugOpt ons = None,
          searchSeg nt d = None,
          returnStatusType = None,
          successfulResponseThreshold = None,
          queryS ce = None,
          getOlderResults = So (false),
          follo dUser ds = So (query.seedUser ds),
          adjustedProtectedRequestParams = None,
          adjustedFullArch veRequestParams = None,
          getProtectedT etsOnly = So (false),
          retoken zeSer al zedQuery = None,
          sk pVeryRecentT ets = true,
          exper  ntClusterToUse = None
        ))
    else None
  }
}

object Earlyb rdTensorflowBasedS m lar yEng ne {
  case class Earlyb rdTensorflowBasedSearchQuery(
    searc rUser d: Opt on[User d],
    seedUser ds: Seq[User d],
    maxNumT ets:  nt,
    beforeT et dExclus ve: Opt on[T et d],
    afterT et dExclus ve: Opt on[T et d],
    f lterOutRet etsAndRepl es: Boolean,
    useTensorflowRank ng: Boolean,
    excludedT et ds: Set[T et d],
    maxNumH sPerShard:  nt)
      extends Earlyb rdSearchQuery

  pr vate def getThr ftSearchQuery(
    query: Earlyb rdTensorflowBasedSearchQuery,
    process ngT  out: Durat on
  ): Thr ftSearchQuery =
    Thr ftSearchQuery(
      ser al zedQuery = GetEarlyb rdQuery(
        query.beforeT et dExclus ve,
        query.afterT et dExclus ve,
        query.excludedT et ds,
        query.f lterOutRet etsAndRepl es).map(_.ser al ze),
      fromUser DF lter64 = So (query.seedUser ds),
      numResults = query.maxNumT ets,
      // W t r to collect conversat on  Ds. Remove   for now.
      // collectConversat on d = Gate.True(), // true for Ho 
      rank ngMode = Thr ftSearchRank ngMode.Relevance,
      relevanceOpt ons = So (getRelevanceOpt ons),
      collectorParams = So (
        CollectorParams(
          // numResultsToReturn def nes how many results each EB shard w ll return to search root
          numResultsToReturn = 1000,
          // term nat onParams.maxH sToProcess  s used for early term nat ng per shard results fetch ng.
          term nat onParams =
            GetCollectorTerm nat onParams(query.maxNumH sPerShard, process ngT  out)
        )),
      facetF eldNa s = So (FacetsToFetch),
      result tadataOpt ons = So ( tadataOpt ons),
      searc r d = query.searc rUser d,
      searchStatus ds = None,
      na dD sjunct onMap = GetNa dD sjunct ons(query.excludedT et ds)
    )

  // T  spec f c values of recap relevance/rerank ng opt ons correspond to
  // exper  nt: enable_recap_rerank ng_2988,t  l ne_ nternal_d sable_recap_f lter
  // bucket    : enable_rerank,d sable_f lter
  pr vate def getRelevanceOpt ons: Thr ftSearchRelevanceOpt ons = {
    Thr ftSearchRelevanceOpt ons(
      prox m yScor ng = true,
      maxConsecut veSa User = So (2),
      rank ngParams = So (getTensorflowBasedRank ngParams),
      maxH sToProcess = So (500),
      maxUserBlendCount = So (3),
      prox m yPhrase  ght = 9.0,
      returnAllResults = So (true)
    )
  }

  pr vate def getTensorflowBasedRank ngParams: Thr ftRank ngParams = {
    Thr ftRank ngParams(
      `type` = So (Thr ftScor ngFunct onType.TensorflowBased),
      selectedTensorflowModel = So ("t  l nes_rect et_repl ca"),
      m nScore = -1.0e100,
      applyBoosts = false,
      authorSpec f cScoreAdjust nts = None
    )
  }
}
