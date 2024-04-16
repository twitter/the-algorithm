package com.tw ter.t  l neranker.ent y_t ets

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.storehaus.Store
 mport com.tw ter.t  l neranker.common._
 mport com.tw ter.t  l neranker.core.HydratedCand datesAndFeaturesEnvelope
 mport com.tw ter.t  l neranker.model.RecapQuery.DependencyProv der
 mport com.tw ter.t  l neranker.model._
 mport com.tw ter.t  l neranker.para ters.ent y_t ets.Ent yT etsParams._
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

class Ent yT etsS ce(
  g zmoduckCl ent: G zmoduckCl ent,
  searchCl ent: SearchCl ent,
  t etyP eCl ent: T etyP eCl ent,
  user tadataCl ent: User tadataCl ent,
  followGraphDataProv der: FollowGraphDataProv der,
  v s b l yEnforcer: V s b l yEnforcer,
  contentFeaturesCac : Store[T et d, ContentFeatures],
  statsRece ver: StatsRece ver) {

  pr vate[t ] val baseScope = statsRece ver.scope("ent yT etsS ce")
  pr vate[t ] val requestStats = RequestStatsRece ver(baseScope)

  pr vate[t ] val fa lOpenScope = baseScope.scope("fa lOpen")
  pr vate[t ] val userProf leHandler = new Fa lOpenHandler(fa lOpenScope, "userProf le nfo")
  pr vate[t ] val userLanguagesHandler = new Fa lOpenHandler(fa lOpenScope, "userLanguages")

  pr vate[t ] val followGraphDataTransform = new FollowGraphDataTransform(
    followGraphDataProv der = followGraphDataProv der,
    maxFollo dUsersProv der = DependencyProv der.from(MaxFollo dUsersParam)
  )
  pr vate[t ] val fetchSearchResultsTransform = new Ent yT etsSearchResultsTransform(
    searchCl ent = searchCl ent,
    statsRece ver = baseScope
  )
  pr vate[t ] val userProf le nfoTransform =
    new UserProf le nfoTransform(userProf leHandler, g zmoduckCl ent)
  pr vate[t ] val languagesTransform =
    new UserLanguagesTransform(userLanguagesHandler, user tadataCl ent)

  pr vate[t ] val v s b l yEnforc ngTransform = new V s b l yEnforc ngTransform(
    v s b l yEnforcer
  )

  pr vate[t ] val f lters = T etF lters.ValueSet(
    T etF lters.Dupl cateT ets,
    T etF lters.Dupl cateRet ets
  )

  pr vate[t ] val hydratedT etsF lter = new HydratedT etsF lterTransform(
    outerF lters = f lters,
     nnerF lters = T etF lters.None,
    useFollowGraphData = false,
    useS ceT ets = false,
    statsRece ver = baseScope,
    numRet etsAllo d = HydratedT etsF lterTransform.NumDupl cateRet etsAllo d
  )

  pr vate[t ] val contentFeaturesHydrat onTransform =
    new ContentFeaturesHydrat onTransformBu lder(
      t etyP eCl ent = t etyP eCl ent,
      contentFeaturesCac  = contentFeaturesCac ,
      enableContentFeaturesGate = RecapQuery.paramGate(EnableContentFeaturesHydrat onParam),
      enableTokens nContentFeaturesGate =
        RecapQuery.paramGate(EnableTokens nContentFeaturesHydrat onParam),
      enableT etText nContentFeaturesGate =
        RecapQuery.paramGate(EnableT etText nContentFeaturesHydrat onParam),
      enableConversat onControlContentFeaturesGate =
        RecapQuery.paramGate(EnableConversat onControl nContentFeaturesHydrat onParam),
      enableT et d aHydrat onGate = RecapQuery.paramGate(EnableT et d aHydrat onParam),
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

  pr vate[t ] val hydrat onAndF lter ngP pel ne =
    CreateCand dateEnvelopeTransform
      .andT n(followGraphDataTransform) // Fetch follow graph data
      .andT n(fetchSearchResultsTransform) // fetch search results
      .andT n(SearchResultDedupAndSort ngTransform) // dedup and order search results
      .andT n(Cand dateT etHydrat onTransform) // hydrate search results
      .andT n(v s b l yEnforc ngTransform) // f lter hydrated t ets to v s ble ones
      .andT n(hydratedT etsF lter) // f lter hydrated t ets based on predef ned f lter
      .andT n(
        Tr mToMatchHydratedT etsTransform
      ) // tr m search result set to match f ltered hydrated t ets (t  needs to be accurate for feature hydrat on)

  // runs t  ma n p pel ne  n parallel w h fetch ng user prof le  nfo and user languages
  pr vate[t ] val featureHydrat onDataTransform =
    new FeatureHydrat onDataTransform(
      hydrat onAndF lter ngP pel ne,
      languagesTransform,
      userProf le nfoTransform
    )

  pr vate[t ] val t etFeaturesHydrat onTransform =
    OutOfNetworkT etsSearchFeaturesHydrat onTransform
      .andT n(contentFeaturesTransfor r)

  pr vate[t ] val featureHydrat onP pel ne =
    featureHydrat onDataTransform
      .andT n(t etFeaturesHydrat onTransform)
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
