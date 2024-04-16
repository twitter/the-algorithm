package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.ho _m xer.model.Ho Features. nReplyToT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nReplyToUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sExtendedReplyFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sRet etFeature
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.query.soc al_graph.SGSFollo dUsersFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.ut l.OffloadFuturePools
 mport com.tw ter.st ch.St ch

object  sExtendedReplyFeatureHydrator
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date] {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er(" sExtendedReply")

  overr de def features: Set[Feature[_, _]] = Set( sExtendedReplyFeature)

  pr vate val TrueFeatureMap = FeatureMapBu lder().add( sExtendedReplyFeature, true).bu ld()
  pr vate val FalseFeatureMap = FeatureMapBu lder().add( sExtendedReplyFeature, false).bu ld()

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = OffloadFuturePools.offload {
    val follo dUsers =
      query.features.map(_.get(SGSFollo dUsersFeature)).getOrElse(Seq.empty).toSet

    cand dates.map { cand date =>
      val features = cand date.features
      val  sExtendedReply = features.getOrElse( nReplyToT et dFeature, None).nonEmpty &&
        !features.getOrElse( sRet etFeature, false) &&
        features.getOrElse( nReplyToUser dFeature, None).ex sts(!follo dUsers.conta ns(_))

       f ( sExtendedReply) TrueFeatureMap else FalseFeatureMap
    }
  }
}
