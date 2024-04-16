package com.tw ter.cr_m xer.module

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.cr_m xer.conf g.T  outConf g
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.ut l.Earlyb rdSearchUt l.Earlyb rdCl ent d
 mport com.tw ter.cr_m xer.ut l.Earlyb rdSearchUt l.FacetsToFetch
 mport com.tw ter.cr_m xer.ut l.Earlyb rdSearchUt l.GetCollectorTerm nat onParams
 mport com.tw ter.cr_m xer.ut l.Earlyb rdSearchUt l.GetEarlyb rdQuery
 mport com.tw ter.cr_m xer.ut l.Earlyb rdSearchUt l. tadataOpt ons
 mport com.tw ter.f nagle. mcac d.{Cl ent =>  mcac dCl ent}
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.SeqLong nject on
 mport com.tw ter.hash ng.KeyHas r
 mport com.tw ter. rm .store.common.Observed mcac dReadableStore
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.search.common.query.thr ftjava.thr ftscala.CollectorParams
 mport com.tw ter.search.earlyb rd.thr ftscala.Earlyb rdRequest
 mport com.tw ter.search.earlyb rd.thr ftscala.Earlyb rdResponseCode
 mport com.tw ter.search.earlyb rd.thr ftscala.Earlyb rdServ ce
 mport com.tw ter.search.earlyb rd.thr ftscala.Thr ftSearchQuery
 mport com.tw ter.search.earlyb rd.thr ftscala.Thr ftSearchRank ngMode
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport javax. nject.Na d

object Earlyb rdRecencyBasedCand dateStoreModule extends Tw terModule {

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.Earlyb rdRecencyBasedW houtRet etsRepl esT etsCac )
  def prov desEarlyb rdRecencyBasedW houtRet etsRepl esCand dateStore(
    statsRece ver: StatsRece ver,
    earlyb rdSearchCl ent: Earlyb rdServ ce. thodPerEndpo nt,
    @Na d(ModuleNa s.Earlyb rdT etsCac ) earlyb rdRecencyBasedT etsCac :  mcac dCl ent,
    t  outConf g: T  outConf g
  ): ReadableStore[User d, Seq[T et d]] = {
    val stats = statsRece ver.scope("Earlyb rdRecencyBasedW houtRet etsRepl esCand dateStore")
    val underly ngStore = new ReadableStore[User d, Seq[T et d]] {
      overr de def get(user d: User d): Future[Opt on[Seq[T et d]]] = {
        // Ho  based EB f lters out ret ets and repl es
        val earlyb rdRequest =
          bu ldEarlyb rdRequest(
            user d,
            F lterOutRet etsAndRepl es,
            DefaultMaxNumT etPerUser,
            t  outConf g.earlyb rdServerT  out)
        getEarlyb rdSearchResult(earlyb rdSearchCl ent, earlyb rdRequest, stats)
      }
    }
    Observed mcac dReadableStore.fromCac Cl ent(
      back ngStore = underly ngStore,
      cac Cl ent = earlyb rdRecencyBasedT etsCac ,
      ttl =  mcac KeyT  ToL veDurat on,
      asyncUpdate = true
    )(
      value nject on = SeqLong nject on,
      statsRece ver = statsRece ver.scope("earlyb rd_recency_based_t ets_ho _ mcac "),
      keyToStr ng = { k =>
        f"uEBRBHM:${keyHas r.hashKey(k.toStr ng.getBytes)}%X" // pref x = EarlyB rdRecencyBasedHo 
      }
    )
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.Earlyb rdRecencyBasedW hRet etsRepl esT etsCac )
  def prov desEarlyb rdRecencyBasedW hRet etsRepl esCand dateStore(
    statsRece ver: StatsRece ver,
    earlyb rdSearchCl ent: Earlyb rdServ ce. thodPerEndpo nt,
    @Na d(ModuleNa s.Earlyb rdT etsCac ) earlyb rdRecencyBasedT etsCac :  mcac dCl ent,
    t  outConf g: T  outConf g
  ): ReadableStore[User d, Seq[T et d]] = {
    val stats = statsRece ver.scope("Earlyb rdRecencyBasedW hRet etsRepl esCand dateStore")
    val underly ngStore = new ReadableStore[User d, Seq[T et d]] {
      overr de def get(user d: User d): Future[Opt on[Seq[T et d]]] = {
        val earlyb rdRequest = bu ldEarlyb rdRequest(
          user d,
          // Not f cat ons based EB keeps ret ets and repl es
          NotF lterOutRet etsAndRepl es,
          DefaultMaxNumT etPerUser,
          process ngT  out = t  outConf g.earlyb rdServerT  out
        )
        getEarlyb rdSearchResult(earlyb rdSearchCl ent, earlyb rdRequest, stats)
      }
    }
    Observed mcac dReadableStore.fromCac Cl ent(
      back ngStore = underly ngStore,
      cac Cl ent = earlyb rdRecencyBasedT etsCac ,
      ttl =  mcac KeyT  ToL veDurat on,
      asyncUpdate = true
    )(
      value nject on = SeqLong nject on,
      statsRece ver = statsRece ver.scope("earlyb rd_recency_based_t ets_not f cat ons_ mcac "),
      keyToStr ng = { k =>
        f"uEBRBN:${keyHas r.hashKey(k.toStr ng.getBytes)}%X" // pref x = EarlyB rdRecencyBasedNot f cat ons
      }
    )
  }

  pr vate val keyHas r: KeyHas r = KeyHas r.FNV1A_64

  /**
   * Note t  DefaultMaxNumT etPerUser  s used to adjust t  result s ze per cac  entry.
   *  f t  value changes,   w ll  ncrease t  s ze of t   mcac .
   */
  pr vate val DefaultMaxNumT etPerUser:  nt = 100
  pr vate val F lterOutRet etsAndRepl es = true
  pr vate val NotF lterOutRet etsAndRepl es = false
  pr vate val  mcac KeyT  ToL veDurat on: Durat on = Durat on.fromM nutes(15)

  pr vate def bu ldEarlyb rdRequest(
    seedUser d: User d,
    f lterOutRet etsAndRepl es: Boolean,
    maxNumT etsPerSeedUser:  nt,
    process ngT  out: Durat on
  ): Earlyb rdRequest =
    Earlyb rdRequest(
      searchQuery = getThr ftSearchQuery(
        seedUser d = seedUser d,
        f lterOutRet etsAndRepl es = f lterOutRet etsAndRepl es,
        maxNumT etsPerSeedUser = maxNumT etsPerSeedUser,
        process ngT  out = process ngT  out
      ),
      cl ent d = So (Earlyb rdCl ent d),
      t  outMs = process ngT  out. nM ll seconds. ntValue(),
      getOlderResults = So (false),
      adjustedProtectedRequestParams = None,
      adjustedFullArch veRequestParams = None,
      getProtectedT etsOnly = So (false),
      sk pVeryRecentT ets = true,
    )

  pr vate def getThr ftSearchQuery(
    seedUser d: User d,
    f lterOutRet etsAndRepl es: Boolean,
    maxNumT etsPerSeedUser:  nt,
    process ngT  out: Durat on
  ): Thr ftSearchQuery = Thr ftSearchQuery(
    ser al zedQuery = GetEarlyb rdQuery(
      None,
      None,
      Set.empty,
      f lterOutRet etsAndRepl es
    ).map(_.ser al ze),
    fromUser DF lter64 = So (Seq(seedUser d)),
    numResults = maxNumT etsPerSeedUser,
    rank ngMode = Thr ftSearchRank ngMode.Recency,
    collectorParams = So (
      CollectorParams(
        // numResultsToReturn def nes how many results each EB shard w ll return to search root
        numResultsToReturn = maxNumT etsPerSeedUser,
        // term nat onParams.maxH sToProcess  s used for early term nat ng per shard results fetch ng.
        term nat onParams =
          GetCollectorTerm nat onParams(maxNumT etsPerSeedUser, process ngT  out)
      )),
    facetF eldNa s = So (FacetsToFetch),
    result tadataOpt ons = So ( tadataOpt ons),
    searchStatus ds = None
  )

  pr vate def getEarlyb rdSearchResult(
    earlyb rdSearchCl ent: Earlyb rdServ ce. thodPerEndpo nt,
    request: Earlyb rdRequest,
    statsRece ver: StatsRece ver
  ): Future[Opt on[Seq[T et d]]] = earlyb rdSearchCl ent
    .search(request)
    .map { response =>
      response.responseCode match {
        case Earlyb rdResponseCode.Success =>
          val earlyb rdSearchResult =
            response.searchResults
              .map {
                _.results
                  .map(searchResult => searchResult. d)
              }
          statsRece ver.scope("result").stat("s ze").add(earlyb rdSearchResult.s ze)
          earlyb rdSearchResult
        case e =>
          statsRece ver.scope("fa lures").counter(e.getClass.getS mpleNa ). ncr()
          So (Seq.empty)
      }
    }

}
