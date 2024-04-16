package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.real_t  _aggregates

 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.datarecord.DataRecord nAFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.ut l.OffloadFuturePools
 mport com.tw ter.st ch.St ch

tra  BaseRealT  AggregateBulkCand dateFeatureHydrator[K]
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date]
    w h BaseRealt  AggregateHydrator[K] {

  val outputFeature: DataRecord nAFeature[T etCand date]

  overr de def features: Set[Feature[_, _]] = Set(outputFeature)

  overr de lazy val statScope: Str ng =  dent f er.toStr ng

  def keysFromQueryAndCand dates(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): Seq[Opt on[K]]

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val poss blyKeys = keysFromQueryAndCand dates(query, cand dates)
    fetchAndConstructDataRecords(poss blyKeys).map { dataRecords =>
      dataRecords.map { dataRecord =>
        FeatureMapBu lder().add(outputFeature, dataRecord).bu ld()
      }
    }
  }
}
