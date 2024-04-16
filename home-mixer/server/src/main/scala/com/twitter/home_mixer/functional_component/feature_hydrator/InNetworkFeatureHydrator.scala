package com.tw ter.ho _m xer.funct onal_component.feature_hydrator

 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nNetworkFeature
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.query.soc al_graph.SGSFollo dUsersFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

object  nNetworkFeatureHydrator
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date] {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er(" nNetwork")

  overr de val features: Set[Feature[_, _]] = Set( nNetworkFeature)

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = {
    val v e r d = query.getRequ redUser d
    val follo dUser ds = query.features.get.get(SGSFollo dUsersFeature).toSet

    val featureMaps = cand dates.map { cand date =>
      //   use author d and not s ceAuthor d  re so that ret ets are def ned as  n network
      val  s nNetworkOpt = cand date.features.getOrElse(Author dFeature, None).map { author d =>
        // Users cannot follow t mselves but t   s  n network by def n  on
        val  sSelfT et = author d == v e r d
         sSelfT et || follo dUser ds.conta ns(author d)
      }
      FeatureMapBu lder().add( nNetworkFeature,  s nNetworkOpt.getOrElse(true)).bu ld()
    }
    St ch.value(featureMaps)
  }
}
