package com.tw ter.t  l neranker.recap_author

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.storehaus.Store
 mport com.tw ter.t  l neranker.common._
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l neranker.core.HydratedCand datesAndFeaturesEnvelope
 mport com.tw ter.t  l neranker.model.RecapQuery.DependencyProv der
 mport com.tw ter.t  l neranker.model._
 mport com.tw ter.t  l neranker.mon or ng.UsersSearchResultMon or ngTransform
 mport com.tw ter.t  l neranker.para ters.mon or ng.Mon or ngParams
 mport com.tw ter.t  l neranker.para ters.recap.RecapParams
 mport com.tw ter.t  l neranker.para ters.recap_author.RecapAuthorParams
 mport com.tw ter.t  l neranker.recap.model.ContentFeatures
 mport com.tw ter.t  l neranker.ut l.CopyContentFeatures ntoHydratedT etsTransform
 mport com.tw ter.t  l neranker.ut l.CopyContentFeatures ntoThr ftT etFeaturesTransform
 mport com.tw ter.t  l neranker.ut l.T etF lters
 mport com.tw ter.t  l neranker.v s b l y.FollowGraphDataProv der
 mport com.tw ter.t  l nes.cl ents.g zmoduck.G zmoduckCl ent
 mport com.tw ter.t  l nes.cl ents.manhattan.User tadataCl ent
 mport com.tw ter.t  l nes.cl ents.relevance_search.SearchCl ent
 mport com.tw ter.t  l nes.cl ents.t etyp e.T etyP eCl ent
 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.t  l nes.ut l.Fa lOpenHandler
 mport com.tw ter.t  l nes.ut l.stats.RequestStatsRece ver
 mport com.tw ter.t  l nes.v s b l y.V s b l yEnforcer
 mport com.tw ter.ut l.Future

/**
 * T  s ce controls what t ets are fetc d from earlyb rd g ven a
 * l st of authors to fetch t ets from. T  controls ava lable are:
 * 1. T  ''f lters'' val, wh ch  s also overr dden
 * by t  query opt ons  n T etK ndOpt ons (see Recap.scala, t 
 * parent class, for deta ls on how t  overr de works). For example, one
 * can choose to retr eve repl es, ret ets and/or extended repl es
 * by chang ng t  opt ons passed  n, wh ch get added to ''f lters''.
 * 2. T  v s bl yEnforcer passed  n, wh ch controls what v s b l y rules
 * are appl ed to t  t ets returned from earlyb rd (e.g. mutes, blocks).
 */
class RecapAuthorS ce(
  g zmoduckCl ent: G zmoduckCl ent,
  searchCl ent: SearchCl ent,
  t etyP eCl ent: T etyP eCl ent,
  user tadataCl ent: User tadataCl ent,
  followGraphDataProv der: FollowGraphDataProv der,
  contentFeaturesCac : Store[T et d, ContentFeatures],
  v s b l yEnforcer: V s b l yEnforcer,
  statsRece ver: StatsRece ver) {

  pr vate[t ] val baseScope = statsRece ver.scope("recapAuthor")
  pr vate[t ] val requestStats = RequestStatsRece ver(baseScope)

  pr vate[t ] val fa lOpenScope = baseScope.scope("fa lOpen")
  pr vate[t ] val userProf leHandler = new Fa lOpenHandler(fa lOpenScope, "userProf le nfo")
  pr vate[t ] val userLanguagesHandler = new Fa lOpenHandler(fa lOpenScope, "userLanguages")

  /*
   * S m lar to RecapS ce,   f lter out t ets d rected at non-follo d users that
   * are not "repl es"  .e. those that beg n w h t  @-handle.
   * For t ets to non-follo d users that are repl es, t se are "extended repl es"
   * and are handled separately by t  dynam c f lters (see Recap.scala for deta ls).
   * Reply and ret et f lter ng  s also handled by dynam c f lters, overr den by
   * T etK ndOpt ons passed  n w h t  query (aga n, deta ls  n Recap.scala)
   *   ho ver do not f lter out t ets from non-follo d users, unl ke RecapS ce,
   * because one of t  ma n use cases of t  endpo nt  s to retr eve t ets from out
   * of network authors.
   */
  val f lters: T etF lters.ValueSet = T etF lters.ValueSet(
    T etF lters.Dupl cateT ets,
    T etF lters.Dupl cateRet ets,
    T etF lters.D rectedAtNotFollo dUsers,
    T etF lters.NonReplyD rectedAtNotFollo dUsers
  )

  pr vate[t ] val v s b l yEnforc ngTransform = new V s b l yEnforc ngTransform(
    v s b l yEnforcer
  )

  pr vate[t ] val hydratedT etsF lter = new HydratedT etsF lterTransform(
    outerF lters = f lters,
     nnerF lters = T etF lters.None,
    useFollowGraphData = true,
    useS ceT ets = false,
    statsRece ver = baseScope,
    numRet etsAllo d = HydratedT etsF lterTransform.NumDupl cateRet etsAllo d
  )

  pr vate[t ] val dynam cHydratedT etsF lter = new T etK ndOpt onHydratedT etsF lterTransform(
    useFollowGraphData = false,
    useS ceT ets = false,
    statsRece ver = baseScope
  )

  pr vate[t ] val maxFollo dUsersProv der =
    DependencyProv der.value(RecapParams.MaxFollo dUsers.default)
  pr vate[t ] val followGraphDataTransform =
    new FollowGraphDataTransform(followGraphDataProv der, maxFollo dUsersProv der)
  pr vate[t ] val maxSearchResultCountProv der = DependencyProv der { query =>
    query.maxCount.getOrElse(query.params(RecapParams.DefaultMaxT etCount))
  }
  pr vate[t ] val relevanceOpt onsMaxH sToProcessProv der =
    DependencyProv der.from(RecapParams.RelevanceOpt onsMaxH sToProcessParam)

  pr vate[t ] val retr eveSearchResultsTransform = new RecapAuthorSearchResultsTransform(
    searchCl ent = searchCl ent,
    maxCountProv der = maxSearchResultCountProv der,
    relevanceOpt onsMaxH sToProcessProv der = relevanceOpt onsMaxH sToProcessProv der,
    enableSett ngT etTypesW hT etK ndOpt onProv der =
      DependencyProv der.from(RecapParams.EnableSett ngT etTypesW hT etK ndOpt on),
    statsRece ver = baseScope,
    logSearchDebug nfo = false)

  pr vate[t ] val debugAuthorsMon or ngProv der =
    DependencyProv der.from(Mon or ngParams.DebugAuthorsAllowL stParam)
  pr vate[t ] val preTruncateSearchResultsTransform =
    new UsersSearchResultMon or ngTransform(
      na  = "RecapSearchResultsTruncat onTransform",
      new RecapSearchResultsTruncat onTransform(
        extraSortBeforeTruncat onGate = DependencyProv der.True,
        maxCountProv der = maxSearchResultCountProv der,
        statsRece ver = baseScope.scope("afterSearchResultsTransform")
      ),
      baseScope.scope("afterSearchResultsTransform"),
      debugAuthorsMon or ngProv der
    )

  pr vate[t ] val searchResultsTransform = retr eveSearchResultsTransform
    .andT n(preTruncateSearchResultsTransform)

  pr vate[t ] val userProf le nfoTransform =
    new UserProf le nfoTransform(userProf leHandler, g zmoduckCl ent)
  pr vate[t ] val languagesTransform =
    new UserLanguagesTransform(userLanguagesHandler, user tadataCl ent)

  pr vate[t ] val contentFeaturesHydrat onTransform =
    new ContentFeaturesHydrat onTransformBu lder(
      t etyP eCl ent = t etyP eCl ent,
      contentFeaturesCac  = contentFeaturesCac ,
      enableContentFeaturesGate =
        RecapQuery.paramGate(RecapAuthorParams.EnableContentFeaturesHydrat onParam),
      enableTokens nContentFeaturesGate =
        RecapQuery.paramGate(RecapAuthorParams.EnableTokens nContentFeaturesHydrat onParam),
      enableT etText nContentFeaturesGate =
        RecapQuery.paramGate(RecapAuthorParams.EnableT etText nContentFeaturesHydrat onParam),
      enableConversat onControlContentFeaturesGate = RecapQuery.paramGate(
        RecapAuthorParams.EnableConversat onControl nContentFeaturesHydrat onParam),
      enableT et d aHydrat onGate =
        RecapQuery.paramGate(RecapAuthorParams.EnableT et d aHydrat onParam),
      hydrate nReplyToT ets = false,
      statsRece ver = baseScope
    ).bu ld()

  pr vate[t ] def hydratesContentFeatures(
    hydratedEnvelope: HydratedCand datesAndFeaturesEnvelope
  ): Boolean =
    hydratedEnvelope.cand dateEnvelope.query.hydratesContentFeatures.getOrElse(true)

  pr vate[t ] val contentFeaturesTransfor r = FutureArrow.choose(
    pred cate = hydratesContentFeatures,
     fTrue = contentFeaturesHydrat onTransform
      .andT n(CopyContentFeatures ntoThr ftT etFeaturesTransform)
      .andT n(CopyContentFeatures ntoHydratedT etsTransform),
     fFalse = FutureArrow[
      HydratedCand datesAndFeaturesEnvelope,
      HydratedCand datesAndFeaturesEnvelope
    ](Future.value) // empty transfor r
  )

  pr vate[t ] val cand dateGenerat onTransform = new Cand dateGenerat onTransform(baseScope)

  val hydrat onAndF lter ngP pel ne: FutureArrow[RecapQuery, Cand dateEnvelope] =
    CreateCand dateEnvelopeTransform // Create empty Cand dateEnvelope
      .andT n(followGraphDataTransform) // Fetch follow graph data
      .andT n(searchResultsTransform) // Fetch search results
      .andT n(SearchResultDedupAndSort ngTransform)
      .andT n(Cand dateT etHydrat onTransform) // cand date hydrat on
      .andT n(v s b l yEnforc ngTransform) // f lter hydrated t ets to v s ble ones
      .andT n(hydratedT etsF lter) // f lter hydrated t ets based on predef ned f lter
      .andT n(dynam cHydratedT etsF lter) // f lter hydrated t ets based on query T etK ndOpt on
      .andT n(
        Tr mToMatchHydratedT etsTransform
      ) // tr m search result set to match f ltered hydrated t ets (t  needs to be accurate for feature hydrat on)

  // runs t  ma n p pel ne  n parallel w h fetch ng user prof le  nfo and user languages
  val featureHydrat onDataTransform: FeatureHydrat onDataTransform =
    new FeatureHydrat onDataTransform(
      hydrat onAndF lter ngP pel ne,
      languagesTransform,
      userProf le nfoTransform
    )

  // Copy transforms must go after t  search features transform, as t  search transform
  // overwr es t  Thr ftT etFeatures.
  val featureHydrat onP pel ne: FutureArrow[RecapQuery, Cand dateT etsResult] =
    featureHydrat onDataTransform
      .andT n(
         nNetworkT etsSearchFeaturesHydrat onTransform
      ) // RecapAuthorS ce uses  nNetworkT etsSearchFeaturesHydrat onTransform because PYLE uses t   n-network model and features.
      .andT n(contentFeaturesTransfor r)
      .andT n(cand dateGenerat onTransform)

  def get(query: RecapQuery): Future[Cand dateT etsResult] = {
    requestStats.addEventStats {
      featureHydrat onP pel ne(query)
    }
  }

  def get(quer es: Seq[RecapQuery]): Future[Seq[Cand dateT etsResult]] = {
    Future.collect(quer es.map(get))
  }
}
