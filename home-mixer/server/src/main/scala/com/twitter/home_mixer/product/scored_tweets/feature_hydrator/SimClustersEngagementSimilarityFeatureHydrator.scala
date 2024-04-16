package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.ho _m xer.product.scored_t ets.param.ScoredT etsParam
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.datarecord.DataRecord nAFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Cond  onally
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.ut l.OffloadFuturePools
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.cl ents.strato.tw stly.S mClustersRecentEngage ntS m lar yCl ent
 mport com.tw ter.t  l nes.conf gap .dec der.BooleanDec derParam
 mport com.tw ter.t  l nes.pred ct on.adapters.tw stly.S mClustersRecentEngage ntS m lar yFeaturesAdapter
 mport javax. nject. nject
 mport javax. nject.S ngleton

object S mClustersEngage ntS m lar yFeature
    extends DataRecord nAFeature[P pel neQuery]
    w h FeatureW hDefaultOnFa lure[P pel neQuery, DataRecord] {
  overr de def defaultValue: DataRecord = new DataRecord()
}

@S ngleton
class S mClustersEngage ntS m lar yFeatureHydrator @ nject() (
  s mClustersEngage ntS m lar yCl ent: S mClustersRecentEngage ntS m lar yCl ent)
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date]
    w h Cond  onally[P pel neQuery] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("S mClustersEngage ntS m lar y")

  overr de val features: Set[Feature[_, _]] = Set(S mClustersEngage ntS m lar yFeature)

  pr vate val s mClustersRecentEngage ntS m lar yFeaturesAdapter =
    new S mClustersRecentEngage ntS m lar yFeaturesAdapter

  overr de def only f(query: P pel neQuery): Boolean = {
    val param: BooleanDec derParam =
      ScoredT etsParam.EnableS mClustersS m lar yFeatureHydrat onDec derParam
    query.params.apply(param)
  }

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val t etToCand dates = cand dates.map(cand date => cand date.cand date. d -> cand date).toMap
    val t et ds = t etToCand dates.keySet.toSeq
    val user d = query.getRequ redUser d
    val userT etEdges = t et ds.map(t et d => (user d, t et d))
    s mClustersEngage ntS m lar yCl ent
      .getS mClustersRecentEngage ntS m lar yScores(userT etEdges).map {
        s mClustersRecentEngage ntS m lar yScoresMap =>
          cand dates.map { cand date =>
            val s m lar yFeatureOpt = s mClustersRecentEngage ntS m lar yScoresMap
              .get(user d -> cand date.cand date. d).flatten
            val dataRecordOpt = s m lar yFeatureOpt.map { s m lar yFeature =>
              s mClustersRecentEngage ntS m lar yFeaturesAdapter
                .adaptToDataRecords(s m lar yFeature)
                .get(0)
            }
            FeatureMapBu lder()
              .add(S mClustersEngage ntS m lar yFeature, dataRecordOpt.getOrElse(new DataRecord))
              .bu ld()
          }
      }
  }
}
