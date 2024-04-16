package com.tw ter.ho _m xer.product.for_ 

 mport com.tw ter.ho _m xer.cand date_p pel ne.Conversat onServ ceResponseFeatureTransfor r
 mport com.tw ter.ho _m xer.funct onal_component.decorator.Ho Conversat onServ ceCand dateDecorator
 mport com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder.Ho FeedbackAct on nfoBu lder
 mport com.tw ter.ho _m xer.funct onal_component.feature_hydrator. nNetworkFeatureHydrator
 mport com.tw ter.ho _m xer.funct onal_component.feature_hydrator.Na sFeatureHydrator
 mport com.tw ter.ho _m xer.funct onal_component.feature_hydrator.T etyp eFeatureHydrator
 mport com.tw ter.ho _m xer.funct onal_component.f lter. nval dConversat onModuleF lter
 mport com.tw ter.ho _m xer.funct onal_component.f lter. nval dSubscr pt onT etF lter
 mport com.tw ter.ho _m xer.funct onal_component.f lter.Prev ouslyServedT etsF lter
 mport com.tw ter.ho _m xer.funct onal_component.f lter.Ret etDedupl cat onF lter
 mport com.tw ter.ho _m xer.model.Ho Features. sHydratedFeature
 mport com.tw ter.ho _m xer.model.Ho Features.QuotedT etDroppedFeature
 mport com.tw ter.ho _m xer.model.Ho Features.T  l neServ ceT etsFeature
 mport com.tw ter.ho _m xer.product.for_ .model.For Query
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAlertConf g
 mport com.tw ter.product_m xer.component_l brary.cand date_s ce.t etconvosvc.Conversat onServ ceCand dateS ce
 mport com.tw ter.product_m xer.component_l brary.cand date_s ce.t etconvosvc.Conversat onServ ceCand dateS ceRequest
 mport com.tw ter.product_m xer.component_l brary.cand date_s ce.t etconvosvc.T etW hConversat on tadata
 mport com.tw ter.product_m xer.component_l brary.f lter.FeatureF lter
 mport com.tw ter.product_m xer.component_l brary.f lter.Pred cateFeatureF lter
 mport com.tw ter.product_m xer.component_l brary.gate.NoCand datesGate
 mport com.tw ter.product_m xer.component_l brary.gate.NonEmptySeqFeatureGate
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.BaseCand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Cand dateDecorator
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.gate.BaseGate
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neResultsTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.DependentCand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.cand date.DependentCand dateP pel neConf g
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Cand date P pel ne Conf g that fetc s T et ancestors from Conversat on Serv ce Cand date S ce
 */
@S ngleton
class For Conversat onServ ceCand dateP pel neConf g @ nject() (
  for ScoredT etsCand dateP pel neConf g: For ScoredT etsCand dateP pel neConf g,
  for T  l neScorerCand dateP pel neConf g: For T  l neScorerCand dateP pel neConf g,
  conversat onServ ceCand dateS ce: Conversat onServ ceCand dateS ce,
  t etyp eFeatureHydrator: T etyp eFeatureHydrator,
  na sFeatureHydrator: Na sFeatureHydrator,
   nval dSubscr pt onT etF lter:  nval dSubscr pt onT etF lter,
  ho FeedbackAct on nfoBu lder: Ho FeedbackAct on nfoBu lder)
    extends DependentCand dateP pel neConf g[
      For Query,
      Conversat onServ ceCand dateS ceRequest,
      T etW hConversat on tadata,
      T etCand date
    ] {

  overr de val  dent f er: Cand dateP pel ne dent f er =
    Cand dateP pel ne dent f er("For Conversat onServ ce")

  overr de val gates: Seq[BaseGate[For Query]] = Seq(
    NoCand datesGate(
      Spec f cP pel nes(
        for T  l neScorerCand dateP pel neConf g. dent f er,
        for ScoredT etsCand dateP pel neConf g. dent f er
      )
    ),
    NonEmptySeqFeatureGate(T  l neServ ceT etsFeature)
  )

  overr de val cand dateS ce: BaseCand dateS ce[
    Conversat onServ ceCand dateS ceRequest,
    T etW hConversat on tadata
  ] = conversat onServ ceCand dateS ce

  overr de val queryTransfor r: DependentCand dateP pel neQueryTransfor r[
    For Query,
    Conversat onServ ceCand dateS ceRequest
  ] = { (query, cand dates) =>
    val t  l neServ ceT ets = query.features
      .map(_.getOrElse(T  l neServ ceT etsFeature, Seq.empty)).getOrElse(Seq.empty)

    val t etsW hConversat on tadata = t  l neServ ceT ets.map {  d =>
      T etW hConversat on tadata(
        t et d =  d,
        user d = None,
        s ceT et d = None,
        s ceUser d = None,
         nReplyToT et d = None,
        conversat on d = None,
        ancestors = Seq.empty
      )
    }
    Conversat onServ ceCand dateS ceRequest(t etsW hConversat on tadata)
  }

  overr de val featuresFromCand dateS ceTransfor rs: Seq[
    Cand dateFeatureTransfor r[T etW hConversat on tadata]
  ] = Seq(Conversat onServ ceResponseFeatureTransfor r)

  overr de val resultTransfor r: Cand dateP pel neResultsTransfor r[
    T etW hConversat on tadata,
    T etCand date
  ] = { s ceResult => T etCand date( d = s ceResult.t et d) }

  overr de val preF lterFeatureHydrat onPhase1: Seq[
    BaseCand dateFeatureHydrator[For Query, T etCand date, _]
  ] = Seq(
     nNetworkFeatureHydrator,
    t etyp eFeatureHydrator
  )

  overr de def f lters: Seq[F lter[For Query, T etCand date]] = Seq(
    Prev ouslyServedT etsF lter,
    Ret etDedupl cat onF lter,
    FeatureF lter.fromFeature(F lter dent f er("T etyp eHydrated"),  sHydratedFeature),
    Pred cateFeatureF lter.fromPred cate(
      F lter dent f er("QuotedT etDropped"),
      shouldKeepCand date = { features => !features.getOrElse(QuotedT etDroppedFeature, false) }
    ),
     nval dSubscr pt onT etF lter,
     nval dConversat onModuleF lter
  )

  overr de val postF lterFeatureHydrat on: Seq[
    BaseCand dateFeatureHydrator[For Query, T etCand date, _]
  ] = Seq(na sFeatureHydrator)

  overr de val decorator: Opt on[Cand dateDecorator[For Query, T etCand date]] =
    Ho Conversat onServ ceCand dateDecorator(ho FeedbackAct on nfoBu lder)

  overr de val alerts = Seq(
    Ho M xerAlertConf g.Bus nessH s.defaultSuccessRateAlert(),
    Ho M xerAlertConf g.Bus nessH s.defaultEmptyResponseRateAlert()
  )
}
