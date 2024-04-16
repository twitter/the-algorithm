package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.real_t  _aggregates

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.datarecord.DataRecord nAFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.ut l.OffloadFuturePools
 mport com.tw ter.st ch.St ch

tra  BaseRealT  AggregateQueryFeatureHydrator[K]
    extends QueryFeatureHydrator[P pel neQuery]
    w h BaseRealt  AggregateHydrator[K] {

  val outputFeature: DataRecord nAFeature[P pel neQuery]

  overr de def features: Set[Feature[_, _]] = Set(outputFeature)

  overr de lazy val statScope: Str ng =  dent f er.toStr ng

  def keysFromQueryAndCand dates(
    query: P pel neQuery
  ): Opt on[K]

  overr de def hydrate(
    query: P pel neQuery
  ): St ch[FeatureMap] = OffloadFuturePools.offloadFuture {
    val poss blyKeys = keysFromQueryAndCand dates(query)
    fetchAndConstructDataRecords(Seq(poss blyKeys)).map { dataRecords =>
      FeatureMapBu lder()
        .add(outputFeature, dataRecords. ad)
        .bu ld()
    }
  }
}
