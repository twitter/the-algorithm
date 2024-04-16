package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.datarecord.DataRecord nAFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.ut l.OffloadFuturePools
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.pred ct on.adapters.realt  _ nteract on_graph.RealT   nteract onGraphFeaturesAdapter
 mport com.tw ter.t  l nes.pred ct on.features.realt  _ nteract on_graph.RealT   nteract onGraphEdgeFeatures
 mport com.tw ter.ut l.T  
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport scala.collect on.JavaConverters._

object RealT   nteract onGraphEdgeFeature
    extends DataRecord nAFeature[T etCand date]
    w h FeatureW hDefaultOnFa lure[T etCand date, DataRecord] {
  overr de def defaultValue: DataRecord = new DataRecord()
}

@S ngleton
class RealT   nteract onGraphEdgeFeatureHydrator @ nject() ()
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("RealT   nteract onGraphEdge")

  overr de val features: Set[Feature[_, _]] = Set(RealT   nteract onGraphEdgeFeature)

  pr vate val realT   nteract onGraphFeaturesAdapter = new RealT   nteract onGraphFeaturesAdapter

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = OffloadFuturePools.offload {
    val userVertex =
      query.features.flatMap(_.getOrElse(RealT   nteract onGraphUserVertexQueryFeature, None))
    val realT   nteract onGraphFeaturesMap =
      userVertex.map(RealT   nteract onGraphEdgeFeatures(_, T  .now))

    cand dates.map { cand date =>
      val feature = cand date.features.getOrElse(Author dFeature, None).flatMap { author d =>
        realT   nteract onGraphFeaturesMap.flatMap(_.get(author d))
      }

      val dataRecordFeature =
        realT   nteract onGraphFeaturesAdapter.adaptToDataRecords(feature).asScala. ad

      FeatureMapBu lder().add(RealT   nteract onGraphEdgeFeature, dataRecordFeature).bu ld()
    }
  }
}
