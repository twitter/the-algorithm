package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nReplyToUser dFeature
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.RealGraphV e rAuthorFeatureHydrator.getComb nedRealGraphFeatures
 mport com.tw ter.ho _m xer.ut l.M ss ngKeyExcept on
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
 mport com.tw ter.t  l nes.pred ct on.adapters.real_graph.RealGraphFeaturesAdapter
 mport com.tw ter.t  l nes.real_graph.v1.{thr ftscala => v1}
 mport com.tw ter.t  l nes.real_graph.{thr ftscala => rg}
 mport com.tw ter.ut l.Throw
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport scala.collect on.JavaConverters._

object RealGraphV e rAuthorDataRecordFeature
    extends DataRecord nAFeature[T etCand date]
    w h FeatureW hDefaultOnFa lure[T etCand date, DataRecord] {
  overr de def defaultValue: DataRecord = new DataRecord()
}

object RealGraphV e rAuthorsDataRecordFeature
    extends DataRecord nAFeature[T etCand date]
    w h FeatureW hDefaultOnFa lure[T etCand date, DataRecord] {
  overr de def defaultValue: DataRecord = new DataRecord()
}

@S ngleton
class RealGraphV e rAuthorFeatureHydrator @ nject() ()
    extends Cand dateFeatureHydrator[P pel neQuery, T etCand date] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("RealGraphV e rAuthor")

  overr de val features: Set[Feature[_, _]] =
    Set(RealGraphV e rAuthorDataRecordFeature, RealGraphV e rAuthorsDataRecordFeature)

  pr vate val realGraphEdgeFeaturesAdapter = new RealGraphFeaturesAdapter
  pr vate val realGraphEdgeFeaturesComb neAdapter =
    new RealGraphEdgeFeaturesComb neAdapter(pref x = "authors.realgraph")

  pr vate val M ss ngKeyFeatureMap = FeatureMapBu lder()
    .add(RealGraphV e rAuthorDataRecordFeature, Throw(M ss ngKeyExcept on))
    .add(RealGraphV e rAuthorsDataRecordFeature, Throw(M ss ngKeyExcept on))
    .bu ld()

  overr de def apply(
    query: P pel neQuery,
    cand date: T etCand date,
    ex st ngFeatures: FeatureMap
  ): St ch[FeatureMap] = OffloadFuturePools.offload {
    val v e r d = query.getRequ redUser d
    val realGraphFeatures = query.features
      .flatMap(_.getOrElse(RealGraphFeatures, None))
      .getOrElse(Map.empty[Long, v1.RealGraphEdgeFeatures])

    ex st ngFeatures.getOrElse(Author dFeature, None) match {
      case So (author d) =>
        val realGraphAuthorFeatures =
          getRealGraphV e rAuthorFeatures(v e r d, author d, realGraphFeatures)
        val realGraphAuthorDataRecord = realGraphEdgeFeaturesAdapter
          .adaptToDataRecords(realGraphAuthorFeatures).asScala. adOpt on.getOrElse(new DataRecord)

        val comb nedRealGraphFeaturesDataRecord = for {
           nReplyToAuthor d <- ex st ngFeatures.getOrElse( nReplyToUser dFeature, None)
        } y eld {
          val comb nedRealGraphFeatures =
            getComb nedRealGraphFeatures(Seq(author d,  nReplyToAuthor d), realGraphFeatures)
          realGraphEdgeFeaturesComb neAdapter
            .adaptToDataRecords(So (comb nedRealGraphFeatures)).asScala. adOpt on
            .getOrElse(new DataRecord)
        }

        FeatureMapBu lder()
          .add(RealGraphV e rAuthorDataRecordFeature, realGraphAuthorDataRecord)
          .add(
            RealGraphV e rAuthorsDataRecordFeature,
            comb nedRealGraphFeaturesDataRecord.getOrElse(new DataRecord))
          .bu ld()
      case _ => M ss ngKeyFeatureMap
    }
  }

  pr vate def getRealGraphV e rAuthorFeatures(
    v e r d: Long,
    author d: Long,
    realGraphEdgeFeaturesMap: Map[Long, v1.RealGraphEdgeFeatures]
  ): rg.UserRealGraphFeatures = {
    realGraphEdgeFeaturesMap.get(author d) match {
      case So (realGraphEdgeFeatures) =>
        rg.UserRealGraphFeatures(
          src d = v e r d,
          features = rg.RealGraphFeatures.V1(
            v1.RealGraphFeatures(edgeFeatures = Seq(realGraphEdgeFeatures))))
      case _ =>
        rg.UserRealGraphFeatures(
          src d = v e r d,
          features = rg.RealGraphFeatures.V1(v1.RealGraphFeatures(edgeFeatures = Seq.empty)))
    }
  }
}

object RealGraphV e rAuthorFeatureHydrator {
  def getComb nedRealGraphFeatures(
    user ds: Seq[Long],
    realGraphEdgeFeaturesMap: Map[Long, v1.RealGraphEdgeFeatures]
  ): rg.RealGraphFeatures = {
    val edgeFeatures = user ds.flatMap(realGraphEdgeFeaturesMap.get)
    rg.RealGraphFeatures.V1(v1.RealGraphFeatures(edgeFeatures = edgeFeatures))
  }
}
