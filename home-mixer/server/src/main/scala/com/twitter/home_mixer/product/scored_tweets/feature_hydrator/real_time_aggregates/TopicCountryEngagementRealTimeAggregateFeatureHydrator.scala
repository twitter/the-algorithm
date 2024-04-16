package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.real_t  _aggregates

 mport com.google. nject.na .Na d
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.model.Ho Features.Top c dSoc alContextFeature
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.Top cCountryEngage ntCac 
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

object Top cCountryEngage ntRealT  AggregateFeature
    extends DataRecord nAFeature[T etCand date]
    w h FeatureW hDefaultOnFa lure[T etCand date, DataRecord] {
  overr de def defaultValue: DataRecord = new DataRecord()
}

@S ngleton
class Top cCountryEngage ntRealT  AggregateFeatureHydrator @ nject() (
  @Na d(Top cCountryEngage ntCac ) overr de val cl ent: ReadCac [(Long, Str ng), DataRecord],
  overr de val statsRece ver: StatsRece ver)
    extends BaseRealT  AggregateBulkCand dateFeatureHydrator[(Long, Str ng)] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("Top cCountryEngage ntRealT  Aggregate")

  overr de val outputFeature: DataRecord nAFeature[T etCand date] =
    Top cCountryEngage ntRealT  AggregateFeature

  overr de val aggregateGroups: Seq[AggregateGroup] = Seq(
    top cCountryRealT  Aggregates
  )

  overr de val aggregateGroupToPref x: Map[AggregateGroup, Str ng] = Map(
    top cCountryRealT  Aggregates -> "top c-country_code.t  l nes.top c_country_engage nt_real_t  _aggregates."
  )

  overr de def keysFromQueryAndCand dates(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): Seq[Opt on[(Long, Str ng)]] = {
    cand dates.map { cand date =>
      val maybeTop c d = cand date.features
        .getTry(Top c dSoc alContextFeature)
        .toOpt on
        .flatten

      val maybeCountryCode = query.cl entContext.countryCode

      for {
        top c d <- maybeTop c d
        countryCode <- maybeCountryCode
      } y eld (top c d, countryCode)
    }
  }
}
