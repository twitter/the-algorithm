package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.real_t  _aggregates

 mport com.google. nject.na .Na d
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.T etCountryEngage ntCac 
 mport com.tw ter.ho _m xer.ut l.Cand datesUt l
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

object T etCountryEngage ntRealT  AggregateFeature
    extends DataRecord nAFeature[T etCand date]
    w h FeatureW hDefaultOnFa lure[T etCand date, DataRecord] {
  overr de def defaultValue: DataRecord = new DataRecord()
}

@S ngleton
class T etCountryEngage ntRealT  AggregateFeatureHydrator @ nject() (
  @Na d(T etCountryEngage ntCac ) overr de val cl ent: ReadCac [(Long, Str ng), DataRecord],
  overr de val statsRece ver: StatsRece ver)
    extends BaseRealT  AggregateBulkCand dateFeatureHydrator[(Long, Str ng)] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("T etCountryEngage ntRealT  Aggregate")

  overr de val outputFeature: DataRecord nAFeature[T etCand date] =
    T etCountryEngage ntRealT  AggregateFeature

  overr de val aggregateGroups: Seq[AggregateGroup] = Seq(
    t etCountryRealT  Aggregates,
    t etCountryPr vateEngage ntsRealT  Aggregates
  )

  overr de val aggregateGroupToPref x: Map[AggregateGroup, Str ng] = Map(
    t etCountryRealT  Aggregates -> "t et-country_code.t  l nes.t et_country_engage nt_real_t  _aggregates.",
    t etCountryPr vateEngage ntsRealT  Aggregates -> "t et-country_code.t  l nes.t et_country_pr vate_engage nt_real_t  _aggregates."
  )

  overr de def keysFromQueryAndCand dates(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): Seq[Opt on[(Long, Str ng)]] = {
    val countryCode = query.cl entContext.countryCode
    cand dates.map { cand date =>
      val or g nalT et d = Cand datesUt l.getOr g nalT et d(cand date)
      countryCode.map((or g nalT et d, _))
    }
  }
}
