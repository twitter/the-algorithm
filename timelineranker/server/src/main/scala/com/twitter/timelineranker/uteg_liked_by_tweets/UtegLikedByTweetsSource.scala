package com.tw ter.t  l neranker.uteg_l ked_by_t ets

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.recos.recos_common.thr ftscala.Soc alProofType
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala.T etRecom ndat on
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.storehaus.Store
 mport com.tw ter.t  l neranker.common._
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l neranker.core.DependencyTransfor r
 mport com.tw ter.t  l neranker.core.HydratedCand datesAndFeaturesEnvelope
 mport com.tw ter.t  l neranker.model.Cand dateT etsResult
 mport com.tw ter.t  l neranker.model.RecapQuery
 mport com.tw ter.t  l neranker.model.RecapQuery.DependencyProv der
 mport com.tw ter.t  l neranker.mon or ng.UsersSearchResultMon or ngTransform
 mport com.tw ter.t  l neranker.para ters.recap.RecapParams
 mport com.tw ter.t  l neranker.para ters.uteg_l ked_by_t ets.UtegL kedByT etsParams
 mport com.tw ter.t  l neranker.para ters.mon or ng.Mon or ngParams
 mport com.tw ter.t  l neranker.recap.model.ContentFeatures
 mport com.tw ter.t  l neranker.ut l.CopyContentFeatures ntoHydratedT etsTransform
 mport com.tw ter.t  l neranker.ut l.CopyContentFeatures ntoThr ftT etFeaturesTransform
 mport com.tw ter.t  l neranker.v s b l y.FollowGraphDataProv der
 mport com.tw ter.t  l nes.cl ents.g zmoduck.G zmoduckCl ent
 mport com.tw ter.t  l nes.cl ents.manhattan.User tadataCl ent
 mport com.tw ter.t  l nes.cl ents.relevance_search.SearchCl ent
 mport com.tw ter.t  l nes.cl ents.t etyp e.T etyP eCl ent
 mport com.tw ter.t  l nes.cl ents.user_t et_ent y_graph.UserT etEnt yGraphCl ent
 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.t  l nes.uteg_ut ls.UTEGRecom ndat onsF lterBu lder
 mport com.tw ter.t  l nes.ut l.Fa lOpenHandler
 mport com.tw ter.t  l nes.ut l.stats.RequestStatsRece ver
 mport com.tw ter.ut l.Future

class UtegL kedByT etsS ce(
  userT etEnt yGraphCl ent: UserT etEnt yGraphCl ent,
  g zmoduckCl ent: G zmoduckCl ent,
  searchCl ent: SearchCl ent,
  t etyP eCl ent: T etyP eCl ent,
  user tadataCl ent: User tadataCl ent,
  followGraphDataProv der: FollowGraphDataProv der,
  contentFeaturesCac : Store[T et d, ContentFeatures],
  statsRece ver: StatsRece ver) {

  pr vate[t ] val soc alProofTypes = Seq(Soc alProofType.Favor e)

  pr vate[t ] val baseScope = statsRece ver.scope("utegL kedByT etsS ce")
  pr vate[t ] val requestStats = RequestStatsRece ver(baseScope)

  pr vate[t ] val fa lOpenScope = baseScope.scope("fa lOpen")
  pr vate[t ] val userProf leHandler = new Fa lOpenHandler(fa lOpenScope, "userProf le nfo")
  pr vate[t ] val userLanguagesHandler = new Fa lOpenHandler(fa lOpenScope, "userLanguages")

  pr vate[t ] val debugAuthorsMon or ngProv der =
    DependencyProv der.from(Mon or ngParams.DebugAuthorsAllowL stParam)

  pr vate[t ] val maxFollo dUsersProv der =
    DependencyProv der.value(RecapParams.MaxFollo dUsers.default)
  pr vate[t ] val followGraphDataTransform =
    new FollowGraphDataTransform(followGraphDataProv der, maxFollo dUsersProv der)

  pr vate[t ] val searchResultsTransform =
    new UtegL kedByT etsSearchResultsTransform(
      searchCl ent = searchCl ent,
      statsRece ver = baseScope,
      relevanceSearchProv der =
        DependencyProv der.from(UtegL kedByT etsParams.EnableRelevanceSearchParam)
    )

  pr vate[t ] val userProf le nfoTransform =
    new UserProf le nfoTransform(userProf leHandler, g zmoduckCl ent)
  pr vate[t ] val languagesTransform =
    new UserLanguagesTransform(userLanguagesHandler, user tadataCl ent)

  pr vate[t ] val cand dateGenerat onTransform = new Cand dateGenerat onTransform(baseScope)

  pr vate[t ] val maxCand datesToFetchFromUtegProv der = DependencyProv der { query =>
    query.utegL kedByT etsOpt ons
      .map(_.utegCount).getOrElse(
        query.utegL kedByT etsOpt ons match {
          case So (opts) =>
             f (opts. s nNetwork) query.params(UtegL kedByT etsParams.DefaultUTEG nNetworkCount)
            else query.params(UtegL kedByT etsParams.DefaultUTEGOutOfNetworkCount)
          case None => 0
        }
      )
  }

  pr vate[t ] def  s nNetwork(envelope: Cand dateEnvelope): Boolean =
     s nNetwork(envelope.query)

  pr vate[t ] def  s nNetwork(query: RecapQuery): Boolean =
    query.utegL kedByT etsOpt ons.ex sts(_. s nNetwork)

  pr vate[t ] def  s nNetwork(hydratedEnvelope: HydratedCand datesAndFeaturesEnvelope): Boolean =
     s nNetwork(hydratedEnvelope.cand dateEnvelope)

  pr vate[t ] val recom ndat onsF lter =
    DependencyTransfor r.part  on[Seq[T etRecom ndat on], Seq[T etRecom ndat on]](
      gate = Gate[RecapQuery](f = (query: RecapQuery) =>  s nNetwork(query)),
       fTrue = DependencyTransfor r. dent y,
       fFalse = new UTEGRecom ndat onsF lterBu lder[RecapQuery](
        enabl ngGate =
          RecapQuery.paramGate(UtegL kedByT etsParams.UTEGRecom ndat onsF lter.EnableParam),
        excludeT etGate =
          RecapQuery.paramGate(UtegL kedByT etsParams.UTEGRecom ndat onsF lter.ExcludeT etParam),
        excludeRet etGate = RecapQuery.paramGate(
          UtegL kedByT etsParams.UTEGRecom ndat onsF lter.ExcludeRet etParam),
        excludeReplyGate =
          RecapQuery.paramGate(UtegL kedByT etsParams.UTEGRecom ndat onsF lter.ExcludeReplyParam),
        excludeQuoteGate = RecapQuery.paramGate(
          UtegL kedByT etsParams.UTEGRecom ndat onsF lter.ExcludeQuoteT etParam
        ),
        statsRece ver = baseScope
      ).bu ld
    )

  pr vate[t ] val utegResultsTransform = new UTEGResultsTransform(
    userT etEnt yGraphCl ent,
    maxCand datesToFetchFromUtegProv der,
    recom ndat onsF lter,
    soc alProofTypes
  )

  pr vate[t ] val earlyb rdScoreMult pl erProv der =
    DependencyProv der.from(UtegL kedByT etsParams.Earlyb rdScoreMult pl erParam)
  pr vate[t ] val maxCand datesToReturnToCallerProv der = DependencyProv der { query =>
    query.maxCount.getOrElse(query.params(UtegL kedByT etsParams.DefaultMaxT etCount))
  }

  pr vate[t ] val m nNumFavedByUser dsProv der = DependencyProv der { query =>
    query.params(UtegL kedByT etsParams.M nNumFavor edByUser dsParam)
  }

  pr vate[t ] val removeT etsAuthoredBySeedSetForOutOfNetworkP pel ne =
    FutureArrow.choose[Cand dateEnvelope, Cand dateEnvelope](
      pred cate =  s nNetwork,
       fTrue = FutureArrow. dent y,
       fFalse = new UsersSearchResultMon or ngTransform(
        na  = "RemoveCand datesAuthoredBy  ghtedFollow ngsTransform",
        RemoveCand datesAuthoredBy  ghtedFollow ngsTransform,
        baseScope,
        debugAuthorsMon or ngProv der
      )
    )

  pr vate[t ] val m nNumFavor edByUser dsF lterTransform =
    FutureArrow.choose[Cand dateEnvelope, Cand dateEnvelope](
      pred cate =  s nNetwork,
       fTrue = FutureArrow. dent y,
       fFalse = new UsersSearchResultMon or ngTransform(
        na  = "M nNumNonAuthorFavor edByUser dsF lterTransform",
        new M nNumNonAuthorFavor edByUser dsF lterTransform(
          m nNumFavor edByUser dsProv der = m nNumFavedByUser dsProv der
        ),
        baseScope,
        debugAuthorsMon or ngProv der
      )
    )

  pr vate[t ] val  ncludeRandomT etProv der =
    DependencyProv der.from(UtegL kedByT etsParams. ncludeRandomT etParam)
  pr vate[t ] val  ncludeS ngleRandomT etProv der =
    DependencyProv der.from(UtegL kedByT etsParams. ncludeS ngleRandomT etParam)
  pr vate[t ] val probab l yRandomT etProv der =
    DependencyProv der.from(UtegL kedByT etsParams.Probab l yRandomT etParam)

  pr vate[t ] val markRandomT etTransform = new MarkRandomT etTransform(
     ncludeRandomT etProv der =  ncludeRandomT etProv der,
     ncludeS ngleRandomT etProv der =  ncludeS ngleRandomT etProv der,
    probab l yRandomT etProv der = probab l yRandomT etProv der,
  )

  pr vate[t ] val comb nedScoreTruncateTransform =
    FutureArrow.choose[Cand dateEnvelope, Cand dateEnvelope](
      pred cate =  s nNetwork,
       fTrue = FutureArrow. dent y,
       fFalse = new Comb nedScoreAndTruncateTransform(
        maxT etCountProv der = maxCand datesToReturnToCallerProv der,
        earlyb rdScoreMult pl erProv der = earlyb rdScoreMult pl erProv der,
        numAdd  onalRepl esProv der =
          DependencyProv der.from(UtegL kedByT etsParams.NumAdd  onalRepl esParam),
        statsRece ver = baseScope
      )
    )

  pr vate[t ] val excludeRecom ndedRepl esToNonFollo dUsersGate: Gate[RecapQuery] =
    RecapQuery.paramGate(
      UtegL kedByT etsParams.UTEGRecom ndat onsF lter.ExcludeRecom ndedRepl esToNonFollo dUsersParam)

  pr vate[t ] def enableUseFollowGraphDataForRecom ndedRepl es(
    envelope: Cand dateEnvelope
  ): Boolean =
    excludeRecom ndedRepl esToNonFollo dUsersGate(envelope.query)

  val dynam cHydratedT etsF lter: FutureArrow[Cand dateEnvelope, Cand dateEnvelope] =
    FutureArrow.choose[Cand dateEnvelope, Cand dateEnvelope](
      pred cate = enableUseFollowGraphDataForRecom ndedRepl es,
       fTrue = new T etK ndOpt onHydratedT etsF lterTransform(
        useFollowGraphData = true,
        useS ceT ets = false,
        statsRece ver = baseScope
      ),
       fFalse = new T etK ndOpt onHydratedT etsF lterTransform(
        useFollowGraphData = false,
        useS ceT ets = false,
        statsRece ver = baseScope
      )
    )

  pr vate[t ] val tr mToMatchSearchResultsTransform =
    new UsersSearchResultMon or ngTransform(
      na  = "Tr mToMatchSearchResultsTransform",
      new Tr mToMatchSearchResultsTransform(
        hydrateReplyRootT etProv der = DependencyProv der.False,
        statsRece ver = baseScope
      ),
      baseScope,
      debugAuthorsMon or ngProv der
    )

  // comb ne score and truncate t et cand dates  m d ately after
  pr vate[t ] val hydrat onAndF lter ngP pel ne =
    CreateCand dateEnvelopeTransform
      .andT n(followGraphDataTransform)
      .andT n(utegResultsTransform)
      .andT n(searchResultsTransform)
      // For out of network t ets, remove t ets whose author  s conta ned  n t    ghted follow ng seed set passed  nto TLR
      .andT n(removeT etsAuthoredBySeedSetForOutOfNetworkP pel ne)
      .andT n(m nNumFavor edByUser dsF lterTransform)
      .andT n(Cand dateT etHydrat onTransform)
      .andT n(markRandomT etTransform)
      .andT n(dynam cHydratedT etsF lter)
      .andT n(Tr mToMatchHydratedT etsTransform)
      .andT n(comb nedScoreTruncateTransform)
      .andT n(tr mToMatchSearchResultsTransform)

  // runs t  ma n p pel ne  n parallel w h fetch ng user prof le  nfo and user languages
  pr vate[t ] val featureHydrat onDataTransform = new FeatureHydrat onDataTransform(
    hydrat onAndF lter ngP pel ne,
    languagesTransform,
    userProf le nfoTransform
  )

  pr vate[t ] val contentFeaturesHydrat onTransform =
    new ContentFeaturesHydrat onTransformBu lder(
      t etyP eCl ent,
      contentFeaturesCac ,
      enableContentFeaturesGate =
        RecapQuery.paramGate(UtegL kedByT etsParams.EnableContentFeaturesHydrat onParam),
      enableTokens nContentFeaturesGate =
        RecapQuery.paramGate(UtegL kedByT etsParams.EnableTokens nContentFeaturesHydrat onParam),
      enableT etText nContentFeaturesGate = RecapQuery.paramGate(
        UtegL kedByT etsParams.EnableT etText nContentFeaturesHydrat onParam),
      enableConversat onControlContentFeaturesGate = RecapQuery.paramGate(
        UtegL kedByT etsParams.EnableConversat onControl nContentFeaturesHydrat onParam),
      enableT et d aHydrat onGate = RecapQuery.paramGate(
        UtegL kedByT etsParams.EnableT et d aHydrat onParam
      ),
      hydrate nReplyToT ets = true,
      statsRece ver = baseScope
    ).bu ld()

  // use OutOfNetworkT etsSearchFeaturesHydrat onTransform for rect ets
  pr vate[t ] val t etsSearchFeaturesHydrat onTransform =
    FutureArrow
      .choose[HydratedCand datesAndFeaturesEnvelope, HydratedCand datesAndFeaturesEnvelope](
        pred cate =  s nNetwork,
         fTrue =  nNetworkT etsSearchFeaturesHydrat onTransform,
         fFalse = OutOfNetworkT etsSearchFeaturesHydrat onTransform
      )

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

  pr vate[t ] val featureHydrat onP pel ne =
    featureHydrat onDataTransform
      .andT n(t etsSearchFeaturesHydrat onTransform)
      .andT n(Soc alProofAndUTEGScoreHydrat onTransform)
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
