package com.tw ter.ho _m xer.funct onal_component.decorator.bu lder

 mport com.tw ter.f nagle.trac ng.Trace
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEventDeta lsBu lder
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEventDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.T  l nesDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.suggests.controller_data.ho _t ets.v1.{thr ftscala => v1ht}
 mport com.tw ter.suggests.controller_data.ho _t ets.{thr ftscala => ht}
 mport com.tw ter.suggests.controller_data.thr ftscala.ControllerData
 mport com.tw ter.suggests.controller_data.v2.thr ftscala.{ControllerData => ControllerDataV2}

case class Ho AdsCl entEventDeta lsBu lder( nject onType: Opt on[Str ng])
    extends BaseCl entEventDeta lsBu lder[P pel neQuery, Un versalNoun[Any]] {

  overr de def apply(
    query: P pel neQuery,
    cand date: Un versalNoun[Any],
    cand dateFeatures: FeatureMap
  ): Opt on[Cl entEventDeta ls] = {
    val ho T etsControllerDataV1 = v1ht.Ho T etsControllerData(
      t etTypesB map = 0L,
      trace d = So (Trace. d.trace d.toLong),
      requestJo n d = None)

    val ser al zedControllerData = Ho Cl entEventDeta lsBu lder.ControllerDataSer al zer(
      ControllerData.V2(
        ControllerDataV2.Ho T ets(ht.Ho T etsControllerData.V1(ho T etsControllerDataV1))))

    val cl entEventDeta ls = Cl entEventDeta ls(
      conversat onDeta ls = None,
      t  l nesDeta ls = So (
        T  l nesDeta ls(
           nject onType =  nject onType,
          controllerData = So (ser al zedControllerData),
          s ceData = None)),
      art cleDeta ls = None,
      l veEventDeta ls = None,
      com rceDeta ls = None
    )

    So (cl entEventDeta ls)
  }
}
