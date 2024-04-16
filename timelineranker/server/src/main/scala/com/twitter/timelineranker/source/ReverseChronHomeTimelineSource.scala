package com.tw ter.t  l neranker.s ce

 mport com.google.common.annotat ons.V s bleForTest ng
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.search.earlyb rd.thr ftscala.Thr ftSearchResult
 mport com.tw ter.t  l neranker.core.FollowGraphData
 mport com.tw ter.t  l neranker.model._
 mport com.tw ter.t  l neranker.para ters.revchron.ReverseChronT  l neQueryContext
 mport com.tw ter.t  l neranker.ut l.T etF ltersBasedOnSearch tadata
 mport com.tw ter.t  l neranker.ut l.T etsPostF lterBasedOnSearch tadata
 mport com.tw ter.t  l neranker.ut l.SearchResultW hV s b l yActors
 mport com.tw ter.t  l neranker.v s b l y.FollowGraphDataProv der
 mport com.tw ter.t  l nes.cl ents.relevance_search.SearchCl ent
 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.t  l nes.model.User d
 mport com.tw ter.t  l nes.ut l.stats.RequestStats
 mport com.tw ter.t  l nes.ut l.stats.RequestStatsRece ver
 mport com.tw ter.t  l nes.v s b l y.V s b l yEnforcer
 mport com.tw ter.t  l neserv ce.model.T  l ne d
 mport com.tw ter.t  l neserv ce.model.core.T  l neK nd
 mport com.tw ter.ut l.Future

object ReverseChronHo T  l neS ce {

  // Post search f lters appl ed to t ets us ng  tadata  ncluded  n search results.
  val F ltersBasedOnSearch tadata: T etF ltersBasedOnSearch tadata.ValueSet =
    T etF ltersBasedOnSearch tadata.ValueSet(
      T etF ltersBasedOnSearch tadata.Dupl cateRet ets,
      T etF ltersBasedOnSearch tadata.Dupl cateT ets
    )

  object GetT etsResult {
    val Empty: GetT etsResult = GetT etsResult(0, 0L, N l)
    val EmptyFuture: Future[GetT etsResult] = Future.value(Empty)
  }

  case class GetT etsResult(
    // numSearchResults  s t  result count before f lter ng so may not match t ets.s ze
    numSearchResults:  nt,
    m nT et dFromSearch: T et d,
    t ets: Seq[T et])
}

/**
 * T  l ne s ce that enables mater al z ng reverse chron t  l nes
 * us ng search  nfrastructure.
 */
class ReverseChronHo T  l neS ce(
  searchCl ent: SearchCl ent,
  followGraphDataProv der: FollowGraphDataProv der,
  v s b l yEnforcer: V s b l yEnforcer,
  statsRece ver: StatsRece ver)
    extends RequestStats {

   mport ReverseChronHo T  l neS ce._

  pr vate[t ] val logger = Logger.get("ReverseChronHo T  l neS ce")
  pr vate[t ] val scope = statsRece ver.scope("reverseChronS ce")
  pr vate[t ] val stats = RequestStatsRece ver(scope)
  pr vate[t ] val emptyT  l neReturnedCounter =
    scope.counter("emptyT  l neReturnedDueToMaxFollows")
  pr vate[t ] val maxCountStat = scope.stat("maxCount")
  pr vate[t ] val numT etsStat = scope.stat("numT ets")
  pr vate[t ] val requestedAdd  onalT etsAfterF lter =
    scope.counter("requestedAdd  onalT etsAfterF lter")
  pr vate[t ] val emptyT  l nes = scope.counter("emptyT  l nes")
  pr vate[t ] val emptyT  l nesW hS gn f cantFollow ng =
    scope.counter("emptyT  l nesW hS gn f cantFollow ng")

  // Threshold to use to determ ne  f a user has a s gn f cant follow ngs l st s ze
  pr vate[t ] val S gn f cantFollow ngThreshold = 20

  def get(contexts: Seq[ReverseChronT  l neQueryContext]): Seq[Future[T  l ne]] = {
    contexts.map(get)
  }

  def get(context: ReverseChronT  l neQueryContext): Future[T  l ne] = {
    stats.addEventStats {
      val query: ReverseChronT  l neQuery = context.query

      //   only support T et  D range at present.
      val t et dRange =
        query.range.map(T et dRange.fromT  l neRange).getOrElse(T et dRange.default)

      val user d = query.user d
      val t  l ne d = T  l ne d(user d, T  l neK nd.ho )
      val maxFollow ngCount = context.maxFollo dUsers()

      followGraphDataProv der
        .get(
          user d,
          maxFollow ngCount
        )
        .flatMap { followGraphData =>
          //   return an empty t  l ne  f a g ven user follows more than t  l m 
          // on t  number of users. T   s because, such a user's t  l ne w ll qu ckly
          // f ll up d splac ng mater al zed t ets wast ng t  mater alat on work.
          // T  behav or can be d sabled v a featuresw c s to support non-mater al zat on
          // use cases w n   should always return a t  l ne.
           f (followGraphData.f lteredFollo dUser ds. sEmpty ||
            (followGraphData.follo dUser ds.s ze >= maxFollow ngCount && context
              .returnEmptyW nOverMaxFollows())) {
             f (followGraphData.follo dUser ds.s ze >= maxFollow ngCount) {
              emptyT  l neReturnedCounter. ncr()
            }
            Future.value(T  l ne.empty(t  l ne d))
          } else {
            val maxCount = getMaxCount(context)
            val numEntr esToRequest = (maxCount * context.maxCountMult pl er()).to nt
            maxCountStat.add(numEntr esToRequest)

            val allUser ds = followGraphData.follo dUser ds :+ user d
            getT ets(
              user d,
              allUser ds,
              followGraphData,
              numEntr esToRequest,
              t et dRange,
              context
            ).map { t ets =>
               f (t ets. sEmpty) {
                emptyT  l nes. ncr()
                 f (followGraphData.follo dUser ds.s ze >= S gn f cantFollow ngThreshold) {
                  emptyT  l nesW hS gn f cantFollow ng. ncr()
                  logger.debug(
                    "Search returned empty ho  t  l ne for user %s (follow count %s), query: %s",
                    user d,
                    followGraphData.follo dUser ds.s ze,
                    query)
                }
              }
              //  f   had requested more entr es than maxCount (due to mult pl er be ng > 1.0)
              // t n   need to tr m   back to maxCount.
              val truncatedT ets = t ets.take(maxCount)
              numT etsStat.add(truncatedT ets.s ze)
              T  l ne(
                t  l ne d,
                truncatedT ets.map(t et => T  l neEntryEnvelope(t et))
              )
            }
          }
        }
    }
  }

  /**
   * Gets t ets from search and performs post-f lter ng.
   *
   *  f   do not end up w h suff c ent t ets after post-f lter ng,
   *    ssue a second call to search to get more t ets  f:
   * -- such behav or  s enabled by sett ng backf llF lteredEntr es to true.
   * -- t  or g nal call to search returned requested number of t ets.
   * -- after post-f lter ng, t  percentage of f ltered out t ets
   *    exceeds t  value of t etsF lter ngLossageThresholdPercent.
   */
  pr vate def getT ets(
    user d: User d,
    allUser ds: Seq[User d],
    followGraphData: FollowGraphData,
    numEntr esToRequest:  nt,
    t et dRange: T et dRange,
    context: ReverseChronT  l neQueryContext
  ): Future[Seq[T et]] = {
    getT ets lper(
      user d,
      allUser ds,
      followGraphData,
      numEntr esToRequest,
      t et dRange,
      context.d rectedAtNarrowcast ngV aSearch(),
      context.postF lter ngBasedOnSearch tadataEnabled(),
      context.getT etsFromArch ve ndex()
    ).flatMap { result =>
      val numAdd  onalT etsToRequest = getNumAdd  onalT etsToRequest(
        numEntr esToRequest,
        result.numSearchResults,
        result.numSearchResults - result.t ets.s ze,
        context
      )

       f (numAdd  onalT etsToRequest > 0) {
        requestedAdd  onalT etsAfterF lter. ncr()
        val updatedRange = t et dRange.copy(to d = So (result.m nT et dFromSearch))
        getT ets lper(
          user d,
          allUser ds,
          followGraphData,
          numAdd  onalT etsToRequest,
          updatedRange,
          context.d rectedAtNarrowcast ngV aSearch(),
          context.postF lter ngBasedOnSearch tadataEnabled(),
          context.getT etsFromArch ve ndex()
        ).map { result2 => result.t ets ++ result2.t ets }
      } else {
        Future.value(result.t ets)
      }
    }
  }

  pr vate[s ce] def getNumAdd  onalT etsToRequest(
    numT etsRequested:  nt,
    numT etsFoundBySearch:  nt,
    numT etsF lteredOut:  nt,
    context: ReverseChronT  l neQueryContext
  ):  nt = {
    requ re(numT etsFoundBySearch <= numT etsRequested)

     f (!context.backf llF lteredEntr es() || (numT etsFoundBySearch < numT etsRequested)) {
      //  f mult ple calls are not enabled or  f search d d not f nd enough t ets,
      // t re  s no po nt  n mak ng anot r call to get more.
      0
    } else {
      val numT etsF lteredOutPercent = numT etsF lteredOut * 100.0 / numT etsFoundBySearch
       f (numT etsF lteredOutPercent > context.t etsF lter ngLossageThresholdPercent()) {

        //   assu  that t  next call w ll also have lossage percentage s m lar to t  f rst call.
        // T refore,   proact vely request proport onately more t ets so that   do not
        // end up need ng a th rd call.
        //  n any case, regardless of what   get  n t  second call,   do not make any subsequent calls.
        val adjustedF lteredOutPercent =
          math.m n(numT etsF lteredOutPercent, context.t etsF lter ngLossageL m Percent())
        val numT etsToRequestMult pl er = 100 / (100 - adjustedF lteredOutPercent)
        val numT etsToRequest = (numT etsF lteredOut * numT etsToRequestMult pl er).to nt

        numT etsToRequest
      } else {
        // D d not have suff c ent lossage to warrant an extra call.
        0
      }
    }
  }

  pr vate def getCl ent d(subCl ent d: Str ng): Str ng = {
    // Hacky: Extract t  env ron nt from t  ex st ng cl ent d set by T  l neRepos oryBu lder
    val env = searchCl ent.cl ent d.spl ('.').last

    s"t  l neranker.$subCl ent d.$env"
  }

  pr vate def getT ets lper(
    user d: User d,
    allUser ds: Seq[User d],
    followGraphData: FollowGraphData,
    maxCount:  nt,
    t et dRange: T et dRange,
    w hD rectedAtNarrowcast ng: Boolean,
    postF lter ngBasedOnSearch tadataEnabled: Boolean,
    getT etsFromArch ve ndex: Boolean
  ): Future[GetT etsResult] = {
    val beforeT et dExclus ve = t et dRange.to d
    val afterT et dExclus ve = t et dRange.from d
    val searchCl ent d: Opt on[Str ng] =  f (!getT etsFromArch ve ndex) {
      // Set a custom cl ent d wh ch has d fferent QPS quota and access.
      // Used for not fy   are fetch ng from realt   only.
      // see: SEARCH-42651
      So (getCl ent d("ho _mater al zat on_realt  _only"))
    } else {
      // Let t  searchCl ent der ve  s cl ent d for t  regular case of fetch ng from arch ve
      None
    }

    searchCl ent
      .getUsersT etsReverseChron(
        user d = user d,
        follo dUser ds = allUser ds.toSet,
        ret etsMutedUser ds = followGraphData.ret etsMutedUser ds,
        maxCount = maxCount,
        beforeT et dExclus ve = beforeT et dExclus ve,
        afterT et dExclus ve = afterT et dExclus ve,
        w hD rectedAtNarrowcast ng = w hD rectedAtNarrowcast ng,
        postF lter ngBasedOnSearch tadataEnabled = postF lter ngBasedOnSearch tadataEnabled,
        getT etsFromArch ve ndex = getT etsFromArch ve ndex,
        searchCl ent d = searchCl ent d
      )
      .flatMap { searchResults =>
         f (searchResults.nonEmpty) {
          val m nT et d = searchResults.last. d
          val f lteredT etsFuture = f lterT ets(
            user d,
            followGraphData. nNetworkUser ds,
            searchResults,
            F ltersBasedOnSearch tadata,
            postF lter ngBasedOnSearch tadataEnabled = postF lter ngBasedOnSearch tadataEnabled,
            v s b l yEnforcer
          )
          f lteredT etsFuture.map(t ets =>
            GetT etsResult(searchResults.s ze, m nT et d, t ets))
        } else {
          GetT etsResult.EmptyFuture
        }
      }
  }

  def f lterT ets(
    user d: User d,
     nNetworkUser ds: Seq[User d],
    searchResults: Seq[Thr ftSearchResult],
    f ltersBasedOnSearch tadata: T etF ltersBasedOnSearch tadata.ValueSet,
    postF lter ngBasedOnSearch tadataEnabled: Boolean = true,
    v s b l yEnforcer: V s b l yEnforcer
  ): Future[Seq[T et]] = {
    val f lteredT ets =  f (postF lter ngBasedOnSearch tadataEnabled) {
      val t etsPostF lterBasedOnSearch tadata =
        new T etsPostF lterBasedOnSearch tadata(f ltersBasedOnSearch tadata, logger, scope)
      t etsPostF lterBasedOnSearch tadata.apply(user d,  nNetworkUser ds, searchResults)
    } else {
      searchResults
    }
    v s b l yEnforcer
      .apply(So (user d), f lteredT ets.map(SearchResultW hV s b l yActors(_, scope)))
      .map(_.map { searchResult =>
        new T et(
           d = searchResult.t et d,
          user d = So (searchResult.user d),
          s ceT et d = searchResult.s ceT et d,
          s ceUser d = searchResult.s ceUser d)
      })
  }

  @V s bleForTest ng
  pr vate[s ce] def getMaxCount(context: ReverseChronT  l neQueryContext):  nt = {
    val maxCountFromQuery = ReverseChronT  l neQueryContext.MaxCount(context.query.maxCount)
    val maxCountFromContext = context.maxCount()
    math.m n(maxCountFromQuery, maxCountFromContext)
  }
}
