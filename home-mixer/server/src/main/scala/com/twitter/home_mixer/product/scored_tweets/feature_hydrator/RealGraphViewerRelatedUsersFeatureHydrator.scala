package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.D rectedAtUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nt onUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceUser dFeature
 mport com.tw ter.ho _m xer.ut l.Cand datesUt l
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.datarecord.DataRecord nAFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.Cand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.ut l.OffloadFuturePools
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.pred ct on.adapters.real_graph.RealGraphEdgeFeaturesComb neAdapter
 mport com.tw ter.t  l nes.real_graph.v1.{thr ftscala => v1}
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport scala.collect on.JavaConverters._

object RealGraphV e rRelatedUsersDataRecordFeature
    extends DataRecord nAFeature[T etCand date]
    w h FeatureW hDefaultOnFa lure[T etCand date, DataRecord] {
  overr de def defaultValue: DataRecord = new DataRecord()
}

@S ngleton
class RealGraphV e rRelatedUsersFeatureHydrator @ nject() ()
    extends Cand dateFeatureHydrator[P pel neQuery, T etCand date] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("RealGraphV e rRelatedUsers")

  overr de val features: Set[Feature[_, _]] = Set(RealGraphV e rRelatedUsersDataRecordFeature)

  pr vate val RealGraphEdgeFeaturesComb neAdapter = new RealGraphEdgeFeaturesComb neAdapter

  overr de def apply(
    query: P pel neQuery,
    cand date: T etCand date,
    ex st ngFeatures: FeatureMap
  ): St ch[FeatureMap] = OffloadFuturePools.offload {
    val realGraphQueryFeatures = query.features
      .flatMap(_.getOrElse(RealGraphFeatures, None))
      .getOrElse(Map.empty[Long, v1.RealGraphEdgeFeatures])

    val allRelatedUser ds = getRelatedUser ds(ex st ngFeatures)
    val realGraphFeatures = RealGraphV e rAuthorFeatureHydrator.getComb nedRealGraphFeatures(
      allRelatedUser ds,
      realGraphQueryFeatures
    )
    val realGraphFeaturesDataRecord = RealGraphEdgeFeaturesComb neAdapter
      .adaptToDataRecords(So (realGraphFeatures)).asScala. adOpt on
      .getOrElse(new DataRecord)

    FeatureMapBu lder()
      .add(RealGraphV e rRelatedUsersDataRecordFeature, realGraphFeaturesDataRecord)
      .bu ld()
  }

  pr vate def getRelatedUser ds(features: FeatureMap): Seq[Long] = {
    (Cand datesUt l.getEngagerUser ds(features) ++
      features.getOrElse(Author dFeature, None) ++
      features.getOrElse( nt onUser dFeature, Seq.empty) ++
      features.getOrElse(S ceUser dFeature, None) ++
      features.getOrElse(D rectedAtUser dFeature, None)).d st nct
  }
}
