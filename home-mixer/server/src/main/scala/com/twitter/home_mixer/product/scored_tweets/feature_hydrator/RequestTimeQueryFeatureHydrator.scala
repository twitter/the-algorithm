package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.ho _m xer.model.Ho Features.Follow ngLastNonPoll ngT  Feature
 mport com.tw ter.ho _m xer.model.Ho Features.LastNonPoll ngT  Feature
 mport com.tw ter.ho _m xer.model.Ho Features.NonPoll ngT  sFeature
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .ut l.FDsl._
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.datarecord.DataRecord nAFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.pred ct on.features.t  _features.AccountAge nterval
 mport com.tw ter.t  l nes.pred ct on.features.t  _features.T  DataRecordFeatures.ACCOUNT_AGE_ NTERVAL
 mport com.tw ter.t  l nes.pred ct on.features.t  _features.T  DataRecordFeatures. S_12_MONTH_NEW_USER
 mport com.tw ter.t  l nes.pred ct on.features.t  _features.T  DataRecordFeatures. S_30_DAY_NEW_USER
 mport com.tw ter.t  l nes.pred ct on.features.t  _features.T  DataRecordFeatures.T ME_BETWEEN_NON_POLL NG_REQUESTS_AVG
 mport com.tw ter.t  l nes.pred ct on.features.t  _features.T  DataRecordFeatures.T ME_S NCE_LAST_NON_POLL NG_REQUEST
 mport com.tw ter.t  l nes.pred ct on.features.t  _features.T  DataRecordFeatures.T ME_S NCE_V EWER_ACCOUNT_CREAT ON_SECS
 mport com.tw ter.t  l nes.pred ct on.features.t  _features.T  DataRecordFeatures.USER_ D_ S_SNOWFLAKE_ D
 mport com.tw ter.user_sess on_store.ReadRequest
 mport com.tw ter.user_sess on_store.ReadWr eUserSess onStore
 mport com.tw ter.user_sess on_store.UserSess onDataset
 mport com.tw ter.user_sess on_store.UserSess onDataset.UserSess onDataset
 mport com.tw ter.ut l.T  
 mport javax. nject. nject
 mport javax. nject.S ngleton

object RequestT  DataRecordFeature
    extends DataRecord nAFeature[P pel neQuery]
    w h FeatureW hDefaultOnFa lure[P pel neQuery, DataRecord] {
  overr de def defaultValue: DataRecord = new DataRecord()
}

@S ngleton
case class RequestT  QueryFeatureHydrator @ nject() (
  userSess onStore: ReadWr eUserSess onStore)
    extends QueryFeatureHydrator[P pel neQuery] {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er("RequestT  ")

  overr de val features: Set[Feature[_, _]] = Set(
    Follow ngLastNonPoll ngT  Feature,
    LastNonPoll ngT  Feature,
    NonPoll ngT  sFeature,
    RequestT  DataRecordFeature
  )

  pr vate val datasets: Set[UserSess onDataset] = Set(UserSess onDataset.NonPoll ngT  s)

  overr de def hydrate(query: P pel neQuery): St ch[FeatureMap] = {
    userSess onStore
      .read(ReadRequest(query.getRequ redUser d, datasets))
      .map { userSess on =>
        val nonPoll ngT  stamps = userSess on.flatMap(_.nonPoll ngT  stamps)

        val lastNonPoll ngT   = nonPoll ngT  stamps
          .flatMap(_.nonPoll ngT  stampsMs. adOpt on)
          .map(T  .fromM ll seconds)

        val follow ngLastNonPoll ngT   = nonPoll ngT  stamps
          .flatMap(_.mostRecentHo LatestNonPoll ngT  stampMs)
          .map(T  .fromM ll seconds)

        val nonPoll ngT  s = nonPoll ngT  stamps
          .map(_.nonPoll ngT  stampsMs)
          .getOrElse(Seq.empty)

        val requestT  DataRecord = getRequestT  DataRecord(query, nonPoll ngT  s)

        FeatureMapBu lder()
          .add(Follow ngLastNonPoll ngT  Feature, follow ngLastNonPoll ngT  )
          .add(LastNonPoll ngT  Feature, lastNonPoll ngT  )
          .add(NonPoll ngT  sFeature, nonPoll ngT  s)
          .add(RequestT  DataRecordFeature, requestT  DataRecord)
          .bu ld()
      }
  }

  def getRequestT  DataRecord(query: P pel neQuery, nonPoll ngT  s: Seq[Long]): DataRecord = {
    val requestT  Ms = query.queryT  . nM ll s
    val accountAge = Snowflake d.t  From dOpt(query.getRequ redUser d)
    val t  S nceAccountCreat on = accountAge.map(query.queryT  .s nce)
    val t  S nceEarl estNonPoll ngRequest =
      nonPoll ngT  s.lastOpt on.map(requestT  Ms - _)
    val t  S nceLastNonPoll ngRequest =
      nonPoll ngT  s. adOpt on.map(requestT  Ms - _)

    new DataRecord()
      .setFeatureValue(USER_ D_ S_SNOWFLAKE_ D, accountAge. sDef ned)
      .setFeatureValue(
         S_30_DAY_NEW_USER,
        t  S nceAccountCreat on.map(_ < 30.days).getOrElse(false)
      )
      .setFeatureValue(
         S_12_MONTH_NEW_USER,
        t  S nceAccountCreat on.map(_ < 365.days).getOrElse(false)
      )
      .setFeatureValueFromOpt on(
        ACCOUNT_AGE_ NTERVAL,
        t  S nceAccountCreat on.flatMap(AccountAge nterval.fromDurat on).map(_. d.toLong)
      )
      .setFeatureValueFromOpt on(
        T ME_S NCE_V EWER_ACCOUNT_CREAT ON_SECS,
        t  S nceAccountCreat on.map(_. nSeconds.toDouble)
      )
      .setFeatureValueFromOpt on(
        T ME_BETWEEN_NON_POLL NG_REQUESTS_AVG,
        t  S nceEarl estNonPoll ngRequest.map(_.toDouble / math.max(1.0, nonPoll ngT  s.s ze))
      )
      .setFeatureValueFromOpt on(
        T ME_S NCE_LAST_NON_POLL NG_REQUEST,
        t  S nceLastNonPoll ngRequest.map(_.toDouble)
      )
  }
}
