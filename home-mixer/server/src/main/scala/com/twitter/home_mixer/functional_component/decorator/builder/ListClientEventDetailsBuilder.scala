package com.tw ter.ho _m xer.funct onal_component.decorator.bu lder

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEventDeta lsBu lder
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEventDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.T  l nesDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l neserv ce.suggests.{thr ftscala => st}

case class L stCl entEventDeta lsBu lder(suggestType: st.SuggestType)
    extends BaseCl entEventDeta lsBu lder[P pel neQuery, Un versalNoun[Any]] {

  overr de def apply(
    query: P pel neQuery,
    cand date: Un versalNoun[Any],
    cand dateFeatures: FeatureMap
  ): Opt on[Cl entEventDeta ls] = {
    val cl entEventDeta ls = Cl entEventDeta ls(
      conversat onDeta ls = None,
      t  l nesDeta ls = So (
        T  l nesDeta ls(
           nject onType = So (suggestType.na ),
          controllerData = None,
          s ceData = None)),
      art cleDeta ls = None,
      l veEventDeta ls = None,
      com rceDeta ls = None
    )

    So (cl entEventDeta ls)
  }
}
