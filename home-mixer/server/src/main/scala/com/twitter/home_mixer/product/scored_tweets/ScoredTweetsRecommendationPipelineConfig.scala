package com.tw ter.ho _m xer.product.scored_t ets

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.ho _m xer.funct onal_component.feature_hydrator.Feedback toryQueryFeatureHydrator
 mport com.tw ter.ho _m xer.funct onal_component.feature_hydrator. mpress onBloomF lterQueryFeatureHydrator
 mport com.tw ter.ho _m xer.funct onal_component.feature_hydrator.RealGraph nNetworkScoresQueryFeatureHydrator
 mport com.tw ter.ho _m xer.funct onal_component.feature_hydrator.RequestQueryFeatureHydrator
 mport com.tw ter.ho _m xer.funct onal_component.feature_hydrator.T et mpress onsQueryFeatureHydrator
 mport com.tw ter.ho _m xer.funct onal_component.f lter.FeedbackFat gueF lter
 mport com.tw ter.ho _m xer.funct onal_component.f lter.Prev ouslySeenT etsF lter
 mport com.tw ter.ho _m xer.funct onal_component.f lter.Prev ouslyServedT etsF lter
 mport com.tw ter.ho _m xer.funct onal_component.f lter.RejectT etFromV e rF lter
 mport com.tw ter.ho _m xer.funct onal_component.f lter.Ret etDedupl cat onF lter
 mport com.tw ter.ho _m xer.funct onal_component.s de_effect.Publ shCl entSent mpress onsEventBusS deEffect
 mport com.tw ter.ho _m xer.funct onal_component.s de_effect.Publ shCl entSent mpress onsManhattanS deEffect
 mport com.tw ter.ho _m xer.funct onal_component.s de_effect.Publ sh mpress onBloomF lterS deEffect
 mport com.tw ter.ho _m xer.funct onal_component.s de_effect.UpdateLastNonPoll ngT  S deEffect
 mport com.tw ter.ho _m xer.model.Ho Features.Exclus veConversat onAuthor dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nNetworkFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nReplyToT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sSupportAccountReplyFeature
 mport com.tw ter.ho _m xer.model.Ho Features.ScoreFeature
 mport com.tw ter.ho _m xer.param.Ho GlobalParams.Enable mpress onBloomF lter
 mport com.tw ter.ho _m xer.param.Ho M xerFlagNa .TargetFetchLatency
 mport com.tw ter.ho _m xer.param.Ho M xerFlagNa .TargetScor ngLatency
 mport com.tw ter.ho _m xer.product.scored_t ets.cand date_p pel ne.Cac dScoredT etsCand dateP pel neConf g
 mport com.tw ter.ho _m xer.product.scored_t ets.cand date_p pel ne.ScoredT etsBackf llCand dateP pel neConf g
 mport com.tw ter.ho _m xer.product.scored_t ets.cand date_p pel ne.ScoredT etsFrsCand dateP pel neConf g
 mport com.tw ter.ho _m xer.product.scored_t ets.cand date_p pel ne.ScoredT ets nNetworkCand dateP pel neConf g
 mport com.tw ter.ho _m xer.product.scored_t ets.cand date_p pel ne.ScoredT etsL stsCand dateP pel neConf g
 mport com.tw ter.ho _m xer.product.scored_t ets.cand date_p pel ne.ScoredT etsPopularV deosCand dateP pel neConf g
 mport com.tw ter.ho _m xer.product.scored_t ets.cand date_p pel ne.ScoredT etsT etM xerCand dateP pel neConf g
 mport com.tw ter.ho _m xer.product.scored_t ets.cand date_p pel ne.ScoredT etsUtegCand dateP pel neConf g
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.Cac dScoredT etsQueryFeatureHydrator
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.L st dsQueryFeatureHydrator
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.RealGraphQueryFeatureHydrator
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.RealT   nteract onGraphUserVertexQueryFeatureHydrator
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.RequestT  QueryFeatureHydrator
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.Twh nUserEngage ntQueryFeatureHydrator
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.Twh nUserFollowQueryFeatureHydrator
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.UserLanguagesFeatureHydrator
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.UserStateQueryFeatureHydrator
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.offl ne_aggregates.PartAAggregateQueryFeatureHydrator
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.offl ne_aggregates.PartBAggregateQueryFeatureHydrator
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.real_t  _aggregates.UserEngage ntRealT  AggregatesFeatureHydrator
 mport com.tw ter.ho _m xer.product.scored_t ets.f lter.Dupl cateConversat onT etsF lter
 mport com.tw ter.ho _m xer.product.scored_t ets.f lter.OutOfNetworkCompet orF lter
 mport com.tw ter.ho _m xer.product.scored_t ets.f lter.OutOfNetworkCompet orURLF lter
 mport com.tw ter.ho _m xer.product.scored_t ets.f lter.ScoredT etsSoc alContextF lter
 mport com.tw ter.ho _m xer.product.scored_t ets.marshaller.ScoredT etsResponseDoma nMarshaller
 mport com.tw ter.ho _m xer.product.scored_t ets.marshaller.ScoredT etsResponseTransportMarshaller
 mport com.tw ter.ho _m xer.product.scored_t ets.model.ScoredT etsQuery
 mport com.tw ter.ho _m xer.product.scored_t ets.model.ScoredT etsResponse
 mport com.tw ter.ho _m xer.product.scored_t ets.param.ScoredT etsParam.Max nNetworkResultsParam
 mport com.tw ter.ho _m xer.product.scored_t ets.param.ScoredT etsParam.MaxOutOfNetworkResultsParam
 mport com.tw ter.ho _m xer.product.scored_t ets.scor ng_p pel ne.ScoredT ets ur st cScor ngP pel neConf g
 mport com.tw ter.ho _m xer.product.scored_t ets.scor ng_p pel ne.ScoredT etsModelScor ngP pel neConf g
 mport com.tw ter.ho _m xer.product.scored_t ets.selector.KeepBestOutOfNetworkCand datePerAuthorPerSuggestType
 mport com.tw ter.ho _m xer.product.scored_t ets.s de_effect.Cac dScoredT etsS deEffect
 mport com.tw ter.ho _m xer.product.scored_t ets.s de_effect.Scr beScoredCand datesS deEffect
 mport com.tw ter.ho _m xer.product.scored_t ets.s de_effect.Scr beServedCommonFeaturesAndCand dateFeaturesS deEffect
 mport com.tw ter.ho _m xer.{thr ftscala => t}
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.query.async.AsyncQueryFeatureHydrator
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.query. mpressed_t ets. mpressedT etsQueryFeatureHydrator
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.query.param_gated.ParamGatedQueryFeatureHydrator
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.query.soc al_graph.SGSFollo dUsersQueryFeatureHydrator
 mport com.tw ter.product_m xer.component_l brary.f lter.Pred cateFeatureF lter
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.component_l brary.selector.DropDupl cateCand dates
 mport com.tw ter.product_m xer.component_l brary.selector.DropF lteredMaxCand dates
 mport com.tw ter.product_m xer.component_l brary.selector.DropMaxCand dates
 mport com.tw ter.product_m xer.component_l brary.selector. dAndClassDupl cat onKey
 mport com.tw ter.product_m xer.component_l brary.selector. nsertAppendResults
 mport com.tw ter.product_m xer.component_l brary.selector.P ckF rstCand date rger
 mport com.tw ter.product_m xer.component_l brary.selector.UpdateSortCand dates
 mport com.tw ter.product_m xer.component_l brary.selector.sorter.FeatureValueSorter
 mport com.tw ter.product_m xer.core.funct onal_component.common.AllExceptP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.common.AllP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.conf gap .Stat cParam
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.TransportMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.Doma nMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Recom ndat onP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Scor ngP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.Fa lOpenPol cy
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neConf g
 mport com.tw ter.product_m xer.core.p pel ne.recom ndat on.Recom ndat onP pel neConf g
 mport com.tw ter.product_m xer.core.p pel ne.scor ng.Scor ngP pel neConf g
 mport com.tw ter.product_m xer.core.qual y_factor.BoundsW hDefault
 mport com.tw ter.product_m xer.core.qual y_factor.L nearLatencyQual yFactorConf g
 mport com.tw ter.product_m xer.core.qual y_factor.Qual yFactorConf g
 mport com.tw ter.ut l.Durat on

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ScoredT etsRecom ndat onP pel neConf g @ nject() (
  scoredT ets nNetworkCand dateP pel neConf g: ScoredT ets nNetworkCand dateP pel neConf g,
  scoredT etsUtegCand dateP pel neConf g: ScoredT etsUtegCand dateP pel neConf g,
  scoredT etsT etM xerCand dateP pel neConf g: ScoredT etsT etM xerCand dateP pel neConf g,
  scoredT etsFrsCand dateP pel neConf g: ScoredT etsFrsCand dateP pel neConf g,
  scoredT etsL stsCand dateP pel neConf g: ScoredT etsL stsCand dateP pel neConf g,
  scoredT etsPopularV deosCand dateP pel neConf g: ScoredT etsPopularV deosCand dateP pel neConf g,
  scoredT etsBackf llCand dateP pel neConf g: ScoredT etsBackf llCand dateP pel neConf g,
  cac dScoredT etsCand dateP pel neConf g: Cac dScoredT etsCand dateP pel neConf g,
  requestQueryFeatureHydrator: RequestQueryFeatureHydrator[ScoredT etsQuery],
  requestT  QueryFeatureHydrator: RequestT  QueryFeatureHydrator,
  realT   nteract onGraphUserVertexQueryFeatureHydrator: RealT   nteract onGraphUserVertexQueryFeatureHydrator,
  userStateQueryFeatureHydrator: UserStateQueryFeatureHydrator,
  userEngage ntRealT  AggregatesFeatureHydrator: UserEngage ntRealT  AggregatesFeatureHydrator,
  twh nUserEngage ntQueryFeatureHydrator: Twh nUserEngage ntQueryFeatureHydrator,
  twh nUserFollowQueryFeatureHydrator: Twh nUserFollowQueryFeatureHydrator,
  cac dScoredT etsQueryFeatureHydrator: Cac dScoredT etsQueryFeatureHydrator,
  sgsFollo dUsersQueryFeatureHydrator: SGSFollo dUsersQueryFeatureHydrator,
  scoredT etsModelScor ngP pel neConf g: ScoredT etsModelScor ngP pel neConf g,
   mpress onBloomF lterQueryFeatureHydrator:  mpress onBloomF lterQueryFeatureHydrator[
    ScoredT etsQuery
  ],
  manhattanT et mpress onsQueryFeatureHydrator: T et mpress onsQueryFeatureHydrator[
    ScoredT etsQuery
  ],
   mcac T et mpress onsQueryFeatureHydrator:  mpressedT etsQueryFeatureHydrator,
  l st dsQueryFeatureHydrator: L st dsQueryFeatureHydrator,
  feedback toryQueryFeatureHydrator: Feedback toryQueryFeatureHydrator,
  publ shCl entSent mpress onsEventBusS deEffect: Publ shCl entSent mpress onsEventBusS deEffect,
  publ shCl entSent mpress onsManhattanS deEffect: Publ shCl entSent mpress onsManhattanS deEffect,
  publ sh mpress onBloomF lterS deEffect: Publ sh mpress onBloomF lterS deEffect,
  realGraph nNetworkScoresQueryFeatureHydrator: RealGraph nNetworkScoresQueryFeatureHydrator,
  realGraphQueryFeatureHydrator: RealGraphQueryFeatureHydrator,
  userLanguagesFeatureHydrator: UserLanguagesFeatureHydrator,
  partAAggregateQueryFeatureHydrator: PartAAggregateQueryFeatureHydrator,
  partBAggregateQueryFeatureHydrator: PartBAggregateQueryFeatureHydrator,
  cac dScoredT etsS deEffect: Cac dScoredT etsS deEffect,
  scr beScoredCand datesS deEffect: Scr beScoredCand datesS deEffect,
  scr beServedCommonFeaturesAndCand dateFeaturesS deEffect: Scr beServedCommonFeaturesAndCand dateFeaturesS deEffect,
  updateLastNonPoll ngT  S deEffect: UpdateLastNonPoll ngT  S deEffect[
    ScoredT etsQuery,
    ScoredT etsResponse
  ],
  @Flag(TargetFetchLatency) targetFetchLatency: Durat on,
  @Flag(TargetScor ngLatency) targetScor ngLatency: Durat on)
    extends Recom ndat onP pel neConf g[
      ScoredT etsQuery,
      T etCand date,
      ScoredT etsResponse,
      t.ScoredT etsResponse
    ] {

  overr de val  dent f er: Recom ndat onP pel ne dent f er =
    Recom ndat onP pel ne dent f er("ScoredT ets")

  pr vate val Subscr pt onReplyF lter d = "Subscr pt onReply"
  pr vate val MaxBackf llT ets = 50

  pr vate val scor ngStep = Recom ndat onP pel neConf g.scor ngP pel nesStep

  overr de val fetchQueryFeatures: Seq[QueryFeatureHydrator[ScoredT etsQuery]] = Seq(
    requestQueryFeatureHydrator,
    realGraph nNetworkScoresQueryFeatureHydrator,
    cac dScoredT etsQueryFeatureHydrator,
    sgsFollo dUsersQueryFeatureHydrator,
    ParamGatedQueryFeatureHydrator(
      Enable mpress onBloomF lter,
       mpress onBloomF lterQueryFeatureHydrator
    ),
    manhattanT et mpress onsQueryFeatureHydrator,
     mcac T et mpress onsQueryFeatureHydrator,
    l st dsQueryFeatureHydrator,
    userStateQueryFeatureHydrator,
    AsyncQueryFeatureHydrator(scor ngStep, feedback toryQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(scor ngStep, realGraphQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(scor ngStep, requestT  QueryFeatureHydrator),
    AsyncQueryFeatureHydrator(scor ngStep, userLanguagesFeatureHydrator),
    AsyncQueryFeatureHydrator(scor ngStep, userEngage ntRealT  AggregatesFeatureHydrator),
    AsyncQueryFeatureHydrator(scor ngStep, realT   nteract onGraphUserVertexQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(scor ngStep, twh nUserFollowQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(scor ngStep, twh nUserEngage ntQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(scor ngStep, partAAggregateQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(scor ngStep, partBAggregateQueryFeatureHydrator),
  )

  overr de val cand dateP pel nes: Seq[
    Cand dateP pel neConf g[ScoredT etsQuery, _, _, T etCand date]
  ] = Seq(
    cac dScoredT etsCand dateP pel neConf g,
    scoredT ets nNetworkCand dateP pel neConf g,
    scoredT etsUtegCand dateP pel neConf g,
    scoredT etsT etM xerCand dateP pel neConf g,
    scoredT etsFrsCand dateP pel neConf g,
    scoredT etsL stsCand dateP pel neConf g,
    scoredT etsPopularV deosCand dateP pel neConf g,
    scoredT etsBackf llCand dateP pel neConf g
  )

  overr de val postCand dateP pel nesSelectors: Seq[Selector[ScoredT etsQuery]] = Seq(
    DropDupl cateCand dates(
      p pel neScope = AllP pel nes,
      dupl cat onKey =  dAndClassDupl cat onKey,
       rgeStrategy = P ckF rstCand date rger
    ),
     nsertAppendResults(AllP pel nes)
  )

  overr de val globalF lters: Seq[F lter[ScoredT etsQuery, T etCand date]] = Seq(
    // sort t se to have t  "c aper" f lters run f rst
    RejectT etFromV e rF lter,
    Ret etDedupl cat onF lter,
    Prev ouslySeenT etsF lter,
    Prev ouslyServedT etsF lter,
    Pred cateFeatureF lter.fromPred cate(
      F lter dent f er(Subscr pt onReplyF lter d),
      shouldKeepCand date = { features =>
        features.getOrElse( nReplyToT et dFeature, None). sEmpty ||
        features.getOrElse(Exclus veConversat onAuthor dFeature, None). sEmpty
      }
    ),
    FeedbackFat gueF lter
  )

  overr de val cand dateP pel neFa lOpenPol c es: Map[Cand dateP pel ne dent f er, Fa lOpenPol cy] =
    Map(
      cac dScoredT etsCand dateP pel neConf g. dent f er -> Fa lOpenPol cy.Always,
      scoredT ets nNetworkCand dateP pel neConf g. dent f er -> Fa lOpenPol cy.Always,
      scoredT etsUtegCand dateP pel neConf g. dent f er -> Fa lOpenPol cy.Always,
      scoredT etsT etM xerCand dateP pel neConf g. dent f er -> Fa lOpenPol cy.Always,
      scoredT etsFrsCand dateP pel neConf g. dent f er -> Fa lOpenPol cy.Always,
      scoredT etsL stsCand dateP pel neConf g. dent f er -> Fa lOpenPol cy.Always,
      scoredT etsPopularV deosCand dateP pel neConf g. dent f er -> Fa lOpenPol cy.Always,
      scoredT etsBackf llCand dateP pel neConf g. dent f er -> Fa lOpenPol cy.Always
    )

  overr de val scor ngP pel neFa lOpenPol c es: Map[Scor ngP pel ne dent f er, Fa lOpenPol cy] =
    Map(
      ScoredT ets ur st cScor ngP pel neConf g. dent f er -> Fa lOpenPol cy.Always
    )

  pr vate val cand dateP pel neQual yFactorConf g = L nearLatencyQual yFactorConf g(
    qual yFactorBounds = BoundsW hDefault(m n nclus ve = 0.1, max nclus ve = 1.0, default = 0.95),
     n  alDelay = 60.seconds,
    targetLatency = targetFetchLatency,
    targetLatencyPercent le = 95.0,
    delta = 0.00125
  )

  pr vate val scor ngP pel neQual yFactorConf g =
    cand dateP pel neQual yFactorConf g.copy(targetLatency = targetScor ngLatency)

  overr de val qual yFactorConf gs: Map[Component dent f er, Qual yFactorConf g] = Map(
    // cand date p pel nes
    scoredT ets nNetworkCand dateP pel neConf g. dent f er -> cand dateP pel neQual yFactorConf g,
    scoredT etsUtegCand dateP pel neConf g. dent f er -> cand dateP pel neQual yFactorConf g,
    scoredT etsT etM xerCand dateP pel neConf g. dent f er -> cand dateP pel neQual yFactorConf g,
    scoredT etsFrsCand dateP pel neConf g. dent f er -> cand dateP pel neQual yFactorConf g,
    scoredT etsL stsCand dateP pel neConf g. dent f er -> cand dateP pel neQual yFactorConf g,
    scoredT etsPopularV deosCand dateP pel neConf g. dent f er -> cand dateP pel neQual yFactorConf g,
    scoredT etsBackf llCand dateP pel neConf g. dent f er -> cand dateP pel neQual yFactorConf g,
    // scor ng p pel nes
    scoredT etsModelScor ngP pel neConf g. dent f er -> scor ngP pel neQual yFactorConf g,
  )

  overr de val scor ngP pel nes: Seq[Scor ngP pel neConf g[ScoredT etsQuery, T etCand date]] =
    Seq(
      // scor ng p pel ne - run on non-cac d cand dates only s nce cac d ones are already scored
      scoredT etsModelScor ngP pel neConf g,
      // re-scor ng p pel ne - run on all cand dates s nce t se are request spec f c
      ScoredT ets ur st cScor ngP pel neConf g
    )

  overr de val postScor ngF lters = Seq(
    ScoredT etsSoc alContextF lter,
    OutOfNetworkCompet orF lter,
    OutOfNetworkCompet orURLF lter,
    Dupl cateConversat onT etsF lter,
    Pred cateFeatureF lter.fromPred cate(
      F lter dent f er(" sSupportAccountReply"),
      shouldKeepCand date = { features =>
        !features.getOrElse( sSupportAccountReplyFeature, false)
      })
  )

  overr de val resultSelectors: Seq[Selector[ScoredT etsQuery]] = Seq(
    KeepBestOutOfNetworkCand datePerAuthorPerSuggestType(AllP pel nes),
    UpdateSortCand dates(AllP pel nes, FeatureValueSorter.descend ng(ScoreFeature)),
    DropF lteredMaxCand dates(
      p pel neScope =
        AllExceptP pel nes(Set(scoredT etsBackf llCand dateP pel neConf g. dent f er)),
      f lter = {
        case  emCand dateW hDeta ls(_, _, features) =>
          features.getOrElse( nNetworkFeature, false)
        case _ => false
      },
      maxSelect onsParam = Max nNetworkResultsParam
    ),
    DropF lteredMaxCand dates(
      p pel neScope = AllP pel nes,
      f lter = {
        case  emCand dateW hDeta ls(_, _, features) =>
          !features.getOrElse( nNetworkFeature, false)
        case _ => false
      },
      maxSelect onsParam = MaxOutOfNetworkResultsParam
    ),
    DropMaxCand dates(
      cand dateP pel ne = scoredT etsBackf llCand dateP pel neConf g. dent f er,
      maxSelect onsParam = Stat cParam(MaxBackf llT ets)
    ),
     nsertAppendResults(AllP pel nes)
  )

  overr de val resultS deEffects: Seq[
    P pel neResultS deEffect[ScoredT etsQuery, ScoredT etsResponse]
  ] = Seq(
    cac dScoredT etsS deEffect,
    publ shCl entSent mpress onsEventBusS deEffect,
    publ shCl entSent mpress onsManhattanS deEffect,
    publ sh mpress onBloomF lterS deEffect,
    scr beScoredCand datesS deEffect,
    scr beServedCommonFeaturesAndCand dateFeaturesS deEffect,
    updateLastNonPoll ngT  S deEffect
  )

  overr de val doma nMarshaller: Doma nMarshaller[
    ScoredT etsQuery,
    ScoredT etsResponse
  ] = ScoredT etsResponseDoma nMarshaller

  overr de val transportMarshaller: TransportMarshaller[
    ScoredT etsResponse,
    t.ScoredT etsResponse
  ] = ScoredT etsResponseTransportMarshaller
}
