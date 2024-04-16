package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.soc algraph.{thr ftscala => sg}
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.soc algraph.Soc alGraph
 mport javax. nject. nject
 mport javax. nject.S ngleton

case object L st dsFeature extends FeatureW hDefaultOnFa lure[P pel neQuery, Seq[Long]] {
  overr de val defaultValue: Seq[Long] = Seq.empty
}

@S ngleton
class L st dsQueryFeatureHydrator @ nject() (soc alGraph: Soc alGraph)
    extends QueryFeatureHydrator[P pel neQuery] {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er("L st ds")

  overr de val features: Set[Feature[_, _]] = Set(L st dsFeature)

  pr vate val MaxL stsToFetch = 20

  overr de def hydrate(query: P pel neQuery): St ch[FeatureMap] = {
    val user d = query.getRequ redUser d

    val ownedSubscr bedRequest = sg. dsRequest(
      relat onsh ps = Seq(
        sg.SrcRelat onsh p(user d, sg.Relat onsh pType.L st sSubscr ber, hasRelat onsh p = true),
        sg.SrcRelat onsh p(user d, sg.Relat onsh pType.L stOwn ng, hasRelat onsh p = true)
      ),
      pageRequest = So (sg.PageRequest(selectAll = So (false), count = So (MaxL stsToFetch))),
      context = So (
        sg.LookupContext(
           nclude nact ve = false,
          performUn on = So (true),
           ncludeAll = So (false)
        )
      )
    )

    soc alGraph. ds(ownedSubscr bedRequest).map { response =>
      FeatureMapBu lder().add(L st dsFeature, response. ds).bu ld()
    }
  }
}
