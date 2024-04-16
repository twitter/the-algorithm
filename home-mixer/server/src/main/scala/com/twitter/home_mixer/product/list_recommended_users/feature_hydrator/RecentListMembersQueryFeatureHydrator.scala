package com.tw ter.ho _m xer.product.l st_recom nded_users.feature_hydrator

 mport com.tw ter.ho _m xer.model.request.HasL st d
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.soc algraph.{thr ftscala => sg}
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.soc algraph.Soc alGraph

 mport javax. nject. nject
 mport javax. nject.S ngleton

case object RecentL st mbersFeature extends FeatureW hDefaultOnFa lure[P pel neQuery, Seq[Long]] {
  overr de val defaultValue: Seq[Long] = Seq.empty
}

@S ngleton
class RecentL st mbersQueryFeatureHydrator @ nject() (soc alGraph: Soc alGraph)
    extends QueryFeatureHydrator[P pel neQuery w h HasL st d] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("RecentL st mbers")

  overr de val features: Set[Feature[_, _]] = Set(RecentL st mbersFeature)

  pr vate val MaxRecent mbers = 10

  overr de def hydrate(query: P pel neQuery w h HasL st d): St ch[FeatureMap] = {
    val request = sg. dsRequest(
      relat onsh ps = Seq(sg
        .SrcRelat onsh p(query.l st d, sg.Relat onsh pType.L stHas mber, hasRelat onsh p = true)),
      pageRequest = So (sg.PageRequest(selectAll = So (true), count = So (MaxRecent mbers)))
    )
    soc alGraph. ds(request).map(_. ds).map { l st mbers =>
      FeatureMapBu lder().add(RecentL st mbersFeature, l st mbers).bu ld()
    }
  }
}
