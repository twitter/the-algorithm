package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.real_t  _aggregates

 mport com.google. nject.na .Na d
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.model.Ho Features.Tw terL st dFeature
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.Tw terL stEngage ntCac 
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
 mport javax. nject.S ngleton

object Tw terL stEngage ntRealT  AggregateFeature
    extends DataRecord nAFeature[T etCand date]
    w h FeatureW hDefaultOnFa lure[T etCand date, DataRecord] {
  overr de def defaultValue: DataRecord = new DataRecord()
}

@S ngleton
class Tw terL stEngage ntRealT  AggregateFeatureHydrator @ nject() (
  @Na d(Tw terL stEngage ntCac ) overr de val cl ent: ReadCac [Long, DataRecord],
  overr de val statsRece ver: StatsRece ver)
    extends BaseRealT  AggregateBulkCand dateFeatureHydrator[Long] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("Tw terL stEngage ntRealT  Aggregate")

  overr de val outputFeature: DataRecord nAFeature[T etCand date] =
    Tw terL stEngage ntRealT  AggregateFeature

  overr de val aggregateGroups: Seq[AggregateGroup] = Seq(
    l stEngage ntRealT  AggregatesProd
  )

  overr de val aggregateGroupToPref x: Map[AggregateGroup, Str ng] = Map(
    l stEngage ntRealT  AggregatesProd -> "tw ter_l st.t  l nes.tw ter_l st_engage nt_real_t  _aggregates."
  )

  overr de def keysFromQueryAndCand dates(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): Seq[Opt on[Long]] = {
    cand dates.map { cand date =>
      cand date.features
        .getTry(Tw terL st dFeature)
        .toOpt on
        .flatten
    }
  }
}
