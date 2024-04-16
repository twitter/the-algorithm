package com.tw ter.ho _m xer.cand date_p pel ne

 mport com.tw ter.ho _m xer.funct onal_component.cand date_s ce.StaleT etsCac Cand dateS ce
 mport com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder.Ho FeedbackAct on nfoBu lder
 mport com.tw ter.ho _m xer.funct onal_component.feature_hydrator.Na sFeatureHydrator
 mport com.tw ter.ho _m xer.funct onal_component.query_transfor r.Ed edT etsCand dateP pel neQueryTransfor r
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAlertConf g
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.Urt emCand dateDecorator
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.contextual_ref.ContextualT etRefBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.t et.T etCand dateUrt emBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. tadata.EmptyCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.BaseCand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Cand dateDecorator
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neResultsTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.response.rtf.safety_level.T  l neFocalT etSafetyLevel
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.contextual_ref.T etHydrat onContext
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et.T et em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.cand date.DependentCand dateP pel neConf g
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Cand date P pel ne Conf g that fetc s ed ed t ets from t  Stale T ets Cac 
 */
@S ngleton
case class Ed edT etsCand dateP pel neConf g @ nject() (
  staleT etsCac Cand dateS ce: StaleT etsCac Cand dateS ce,
  na sFeatureHydrator: Na sFeatureHydrator,
  ho FeedbackAct on nfoBu lder: Ho FeedbackAct on nfoBu lder)
    extends DependentCand dateP pel neConf g[
      P pel neQuery,
      Seq[Long],
      Long,
      T etCand date
    ] {

  overr de val  dent f er: Cand dateP pel ne dent f er = Cand dateP pel ne dent f er("Ed edT ets")

  overr de val cand dateS ce: BaseCand dateS ce[Seq[Long], Long] =
    staleT etsCac Cand dateS ce

  overr de val queryTransfor r: Cand dateP pel neQueryTransfor r[
    P pel neQuery,
    Seq[Long]
  ] = Ed edT etsCand dateP pel neQueryTransfor r

  overr de val resultTransfor r: Cand dateP pel neResultsTransfor r[
    Long,
    T etCand date
  ] = { cand date => T etCand date( d = cand date) }

  overr de val postF lterFeatureHydrat on: Seq[
    BaseCand dateFeatureHydrator[P pel neQuery, T etCand date, _]
  ] = Seq(na sFeatureHydrator)

  overr de val decorator: Opt on[Cand dateDecorator[P pel neQuery, T etCand date]] = {
    val t et emBu lder = T etCand dateUrt emBu lder[P pel neQuery, T etCand date](
      cl entEvent nfoBu lder = EmptyCl entEvent nfoBu lder,
      entry dToReplaceBu lder = So ((_, cand date, _) =>
        So (s"${T et em.T etEntryNa space}-${cand date. d.toStr ng}")),
      contextualT etRefBu lder = So (
        ContextualT etRefBu lder(
          T etHydrat onContext(
            // Apply safety level that  ncludes canon cal VF treat nts that apply regardless of context.
            safetyLevelOverr de = So (T  l neFocalT etSafetyLevel),
            outerT etContext = None
          )
        )
      ),
      feedbackAct on nfoBu lder = So (ho FeedbackAct on nfoBu lder)
    )

    So (Urt emCand dateDecorator(t et emBu lder))
  }

  overr de val alerts = Seq(
    Ho M xerAlertConf g.Bus nessH s.defaultSuccessRateAlert(99.5, 50, 60, 60)
  )
}
