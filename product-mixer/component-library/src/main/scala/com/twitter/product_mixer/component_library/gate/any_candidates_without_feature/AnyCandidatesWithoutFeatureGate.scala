package com.tw ter.product_m xer.component_l brary.gate.any_cand dates_w hout_feature

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.gate.QueryAndCand dateGate
 mport com.tw ter.product_m xer.core.model.common. dent f er.Gate dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/**
 * A gate that enables a component only  f any cand dates are m ss ng a spec f c feature.
 *   can restr ct wh ch cand dates to c ck w h t  scope para ter.
 * T   s most commonly used to do backf ll scor ng, w re   can have one Scor ng P pel ne that
 * m ght return a score feature "FeatureA" and anot r sequent al p pel ne that   only want to run
 *  f t  prev ous scor ng p pel ne fa ls to hydrate for all cand dates.
 * @param  dent f er Un que  dent f er for t  gate. Typ cally, AnyCand datesW hout{Y Feature}.
 * @param scope A [[Cand dateScope]] to spec fy wh ch cand dates to c ck.
 * @param m ss ngFeature T  feature that should be m ss ng for any of t  cand dates for t  gate to cont nue
 */
case class AnyCand datesW houtFeatureGate(
  overr de val  dent f er: Gate dent f er,
  scope: Cand dateScope,
  m ss ngFeature: Feature[_, _])
    extends QueryAndCand dateGate[P pel neQuery] {

  overr de def shouldCont nue(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hDeta ls]
  ): St ch[Boolean] =
    St ch.value(scope.part  on(cand dates).cand dates nScope.ex sts { cand dateW hDeta ls =>
      !cand dateW hDeta ls.features.getSuccessfulFeatures.conta ns(m ss ngFeature)
    })
}
