package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.offl ne_aggregates

 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap . RecordOneToOneAdapter
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.datarecord.DataRecord nAFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.ut l.OffloadFuturePools
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.AggregateGroup
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.AggregateType.AggregateType
 mport com.tw ter.t  l nes.suggests.common.dense_data_record.thr ftjava.DenseCompactDataRecord
 mport java.lang.{Long => JLong}
 mport java.ut l.{Map => JMap}

abstract case class BaseEdgeAggregateFeature(
  aggregateGroups: Set[AggregateGroup],
  aggregateType: AggregateType,
  extractMapFn: AggregateFeaturesToDecodeW h tadata => JMap[JLong, DenseCompactDataRecord],
  adapter:  RecordOneToOneAdapter[Seq[DataRecord]],
  getSecondaryKeysFn: Cand dateW hFeatures[T etCand date] => Seq[Long])
    extends DataRecord nAFeature[P pel neQuery]
    w h FeatureW hDefaultOnFa lure[P pel neQuery, DataRecord] {
  overr de def defaultValue: DataRecord = new DataRecord

  pr vate val rootFeature nfo = new AggregateFeature nfo(aggregateGroups, aggregateType)
  val featureContext: FeatureContext = rootFeature nfo.featureContext
  val rootFeature: BaseAggregateRootFeature = rootFeature nfo.feature
}

tra  BaseEdgeAggregateFeatureHydrator
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date] {

  def aggregateFeatures: Set[BaseEdgeAggregateFeature]

  overr de def features = aggregateFeatures.as nstanceOf[Set[Feature[_, _]]]

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = OffloadFuturePools.offload {
    val featureMapBu lders: Seq[FeatureMapBu lder] =
      for (_ <- cand dates) y eld FeatureMapBu lder()

    aggregateFeatures.foreach { feature =>
      val featureValues = hydrateAggregateFeature(query, cand dates, feature)
      (featureMapBu lders z p featureValues).foreach {
        case (featureMapBu lder, featureValue) => featureMapBu lder.add(feature, featureValue)
      }
    }

    featureMapBu lders.map(_.bu ld())
  }

  pr vate def hydrateAggregateFeature(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]],
    feature: BaseEdgeAggregateFeature
  ): Seq[DataRecord] = {
    val rootFeature = feature.rootFeature
    val extractMapFn = feature.extractMapFn
    val featureContext = feature.featureContext
    val secondary ds: Seq[Seq[Long]] = cand dates.map(feature.getSecondaryKeysFn)

    val featuresToDecodeW h tadata = query.features
      .flatMap(_.getOrElse(rootFeature, None))
      .getOrElse(AggregateFeaturesToDecodeW h tadata.empty)

    // Decode t  DenseCompactDataRecords  nto DataRecords for each requ red secondary  d.
    val decoded: Map[Long, DataRecord] = Ut ls.selectAndTransform(
      secondary ds.flatten.d st nct,
      featuresToDecodeW h tadata.toDataRecord,
      extractMapFn(featuresToDecodeW h tadata)
    )

    // Remove unnecessary features  n-place. T   s safe because t  underly ng DataRecords
    // are un que and have just been generated  n t  prev ous step.
    decoded.values.foreach(Ut ls.f lterDataRecord(_, featureContext))

    // Put features  nto t  FeatureMapBu lders
    secondary ds.map {  ds =>
      val dataRecords =  ds.flatMap(decoded.get)
      feature.adapter.adaptToDataRecord(dataRecords)
    }
  }
}
