package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.ho _m xer.model.Ho Features.Earlyb rdFeature
 mport com.tw ter.ho _m xer.model.Ho Features.NonPoll ngT  sFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceT et dFeature
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .ut l.FDsl._
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.datarecord.DataRecord nAFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.Cand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.pred ct on.features.t  _features.T  DataRecordFeatures._
 mport com.tw ter.ut l.Durat on
 mport scala.collect on.Search ng._

object T etT  DataRecordFeature
    extends DataRecord nAFeature[T etCand date]
    w h FeatureW hDefaultOnFa lure[T etCand date, DataRecord] {
  overr de def defaultValue: DataRecord = new DataRecord()
}

object T etT  FeatureHydrator extends Cand dateFeatureHydrator[P pel neQuery, T etCand date] {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er("T etT  ")

  overr de val features: Set[Feature[_, _]] = Set(T etT  DataRecordFeature)

  overr de def apply(
    query: P pel neQuery,
    cand date: T etCand date,
    ex st ngFeatures: FeatureMap
  ): St ch[FeatureMap] = {
    val t etFeatures = ex st ngFeatures.getOrElse(Earlyb rdFeature, None)
    val t  S nceT etCreat on = Snowflake d.t  From dOpt(cand date. d).map(query.queryT  .s nce)
    val t  S nceT etCreat onMs = t  S nceT etCreat on.map(_. nM ll s)

    val t  S nceS ceT etCreat onOpt = ex st ngFeatures
      .getOrElse(S ceT et dFeature, None)
      .flatMap { s ceT et d =>
        Snowflake d.t  From dOpt(s ceT et d).map(query.queryT  .s nce)
      }.orElse(t  S nceT etCreat on)

    val lastFavS nceCreat onHrs =
      t etFeatures.flatMap(_.lastFavS nceCreat onHrs).map(_.toDouble)
    val lastRet etS nceCreat onHrs =
      t etFeatures.flatMap(_.lastRet etS nceCreat onHrs).map(_.toDouble)
    val lastReplyS nceCreat onHrs =
      t etFeatures.flatMap(_.lastReplyS nceCreat onHrs).map(_.toDouble)
    val lastQuoteS nceCreat onHrs =
      t etFeatures.flatMap(_.lastQuoteS nceCreat onHrs).map(_.toDouble)
    val t  S nceLastFavor eHrs =
      getT  S nceLastEngage ntHrs(lastFavS nceCreat onHrs, t  S nceS ceT etCreat onOpt)
    val t  S nceLastRet etHrs =
      getT  S nceLastEngage ntHrs(lastRet etS nceCreat onHrs, t  S nceS ceT etCreat onOpt)
    val t  S nceLastReplyHrs =
      getT  S nceLastEngage ntHrs(lastReplyS nceCreat onHrs, t  S nceS ceT etCreat onOpt)
    val t  S nceLastQuoteHrs =
      getT  S nceLastEngage ntHrs(lastQuoteS nceCreat onHrs, t  S nceS ceT etCreat onOpt)

    val nonPoll ngT  stampsMs = query.features.get.getOrElse(NonPoll ngT  sFeature, Seq.empty)
    val t  S nceLastNonPoll ngRequest =
      nonPoll ngT  stampsMs. adOpt on.map(query.queryT  . nM ll s - _)

    val nonPoll ngRequestsS nceT etCreat on =
       f (nonPoll ngT  stampsMs.nonEmpty && t  S nceT etCreat onMs. sDef ned) {
        nonPoll ngT  stampsMs
          .search(t  S nceT etCreat onMs.get)(Order ng[Long].reverse)
          . nsert onPo nt
      } else 0.0

    val t etAgeRat o =
       f (t  S nceT etCreat onMs.ex sts(_ > 0.0) && t  S nceLastNonPoll ngRequest. sDef ned) {
        t  S nceLastNonPoll ngRequest.get / t  S nceT etCreat onMs.get.toDouble
      } else 0.0

    val dataRecord = new DataRecord()
      .setFeatureValue( S_TWEET_RECYCLED, false)
      .setFeatureValue(TWEET_AGE_RAT O, t etAgeRat o)
      .setFeatureValueFromOpt on(
        T ME_S NCE_TWEET_CREAT ON,
        t  S nceT etCreat onMs.map(_.toDouble)
      )
      .setFeatureValue(
        NON_POLL NG_REQUESTS_S NCE_TWEET_CREAT ON,
        nonPoll ngRequestsS nceT etCreat on
      )
      .setFeatureValueFromOpt on(LAST_FAVOR TE_S NCE_CREAT ON_HRS, lastFavS nceCreat onHrs)
      .setFeatureValueFromOpt on(LAST_RETWEET_S NCE_CREAT ON_HRS, lastRet etS nceCreat onHrs)
      .setFeatureValueFromOpt on(LAST_REPLY_S NCE_CREAT ON_HRS, lastReplyS nceCreat onHrs)
      .setFeatureValueFromOpt on(LAST_QUOTE_S NCE_CREAT ON_HRS, lastQuoteS nceCreat onHrs)
      .setFeatureValueFromOpt on(T ME_S NCE_LAST_FAVOR TE_HRS, t  S nceLastFavor eHrs)
      .setFeatureValueFromOpt on(T ME_S NCE_LAST_RETWEET_HRS, t  S nceLastRet etHrs)
      .setFeatureValueFromOpt on(T ME_S NCE_LAST_REPLY_HRS, t  S nceLastReplyHrs)
      .setFeatureValueFromOpt on(T ME_S NCE_LAST_QUOTE_HRS, t  S nceLastQuoteHrs)

    St ch.value(FeatureMapBu lder().add(T etT  DataRecordFeature, dataRecord).bu ld())
  }

  pr vate def getT  S nceLastEngage ntHrs(
    lastEngage ntT  S nceCreat onHrsOpt: Opt on[Double],
    t  S nceT etCreat on: Opt on[Durat on]
  ): Opt on[Double] = lastEngage ntT  S nceCreat onHrsOpt.flatMap { lastEngage ntT  Hrs =>
    t  S nceT etCreat on.map(_. nH s - lastEngage ntT  Hrs)
  }
}
