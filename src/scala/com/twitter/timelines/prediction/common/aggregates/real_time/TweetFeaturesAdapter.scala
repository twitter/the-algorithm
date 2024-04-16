package com.tw ter.t  l nes.pred ct on.common.aggregates.real_t  

 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.featurestore.catalog.ent  es.core.T et
 mport com.tw ter.ml.featurestore.catalog.features.trends.T etTrendsScores
 mport com.tw ter.ml.featurestore.l b.T et d
 mport com.tw ter.ml.featurestore.l b.data.Pred ct onRecord
 mport com.tw ter.ml.featurestore.l b.data.Pred ct onRecordAdapter
 mport com.tw ter.ml.featurestore.l b.feature.BoundFeature
 mport com.tw ter.ml.featurestore.l b.feature.BoundFeatureSet
 mport com.tw ter.t  l nes.pred ct on.common.adapters.T  l nesAdapterBase
 mport java.ut l
 mport scala.collect on.JavaConverters._

object T etFeaturesAdapter extends T  l nesAdapterBase[Pred ct onRecord] {

  pr vate val Cont nuousFeatureMap: Map[BoundFeature[T et d, Double], Feature.Cont nuous] = Map()

  val T etFeaturesSet: BoundFeatureSet = new BoundFeatureSet(Cont nuousFeatureMap.keys.toSet)

  val AllFeatures: Seq[Feature[_]] =
    Cont nuousFeatureMap.values.toSeq

  pr vate val adapter = Pred ct onRecordAdapter.oneToOne(T etFeaturesSet)

  overr de def getFeatureContext: FeatureContext = new FeatureContext(AllFeatures: _*)

  overr de def commonFeatures: Set[Feature[_]] = Set.empty

  overr de def adaptToDataRecords(record: Pred ct onRecord): ut l.L st[DataRecord] = {
    L st(adapter.adaptToDataRecord(record)).asJava
  }
}
