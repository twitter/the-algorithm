package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.real_t  _aggregates

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.model.Ho Features.Top c dSoc alContextFeature
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.Top cEngage ntCac 
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.datarecord.DataRecord nAFeature
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.servo.cac .ReadCac 
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.AggregateGroup
 mport com.tw ter.t  l nes.pred ct on.common.aggregates.real_t  .T  l nesOnl neAggregat onFeaturesOnlyConf g._
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object Top cEngage ntRealT  AggregateFeature
    extends DataRecord nAFeature[T etCand date]
    w h FeatureW hDefaultOnFa lure[T etCand date, DataRecord] {
  overr de def defaultValue: DataRecord = new DataRecord()
}

@S ngleton
class Top cEngage ntRealT  AggregateFeatureHydrator @ nject() (
  @Na d(Top cEngage ntCac ) overr de val cl ent: ReadCac [Long, DataRecord],
  overr de val statsRece ver: StatsRece ver)
    extends BaseRealT  AggregateBulkCand dateFeatureHydrator[Long] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("Top cEngage ntRealT  Aggregate")

  overr de val outputFeature: DataRecord nAFeature[T etCand date] =
    Top cEngage ntRealT  AggregateFeature

  overr de val aggregateGroups: Seq[AggregateGroup] = Seq(
    top cEngage ntRealT  AggregatesProd,
    top cEngage nt24H RealT  AggregatesProd,
    top cShareEngage ntsRealT  Aggregates
  )

  overr de val aggregateGroupToPref x: Map[AggregateGroup, Str ng] = Map(
    top cEngage nt24H RealT  AggregatesProd -> "top c.t  l nes.top c_engage nt_24_h _real_t  _aggregates.",
    top cShareEngage ntsRealT  Aggregates -> "top c.t  l nes.top c_share_engage nts_real_t  _aggregates."
  )

  overr de def keysFromQueryAndCand dates(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): Seq[Opt on[Long]] = {
    cand dates.map { cand date =>
      cand date.features
        .getTry(Top c dSoc alContextFeature)
        .toOpt on
        .flatten
    }
  }
}
