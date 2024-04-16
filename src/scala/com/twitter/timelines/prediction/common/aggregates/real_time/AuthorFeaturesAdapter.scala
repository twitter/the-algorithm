package com.tw ter.t  l nes.pred ct on.common.aggregates.real_t  

 mport com.tw ter.dal.personal_data.thr ftjava.PersonalDataType.UserState
 mport com.tw ter.ml.ap .Feature.B nary
 mport com.tw ter.ml.ap .{DataRecord, Feature, FeatureContext, R chDataRecord}
 mport com.tw ter.ml.featurestore.catalog.ent  es.core.Author
 mport com.tw ter.ml.featurestore.catalog.features.mag crecs.UserAct v y
 mport com.tw ter.ml.featurestore.l b.data.Pred ct onRecord
 mport com.tw ter.ml.featurestore.l b.feature.{BoundFeature, BoundFeatureSet}
 mport com.tw ter.ml.featurestore.l b.{User d, D screte => FSD screte}
 mport com.tw ter.t  l nes.pred ct on.common.adapters.T  l nesAdapterBase
 mport java.lang.{Boolean => JBoolean}
 mport java.ut l
 mport scala.collect on.JavaConverters._

object AuthorFeaturesAdapter extends T  l nesAdapterBase[Pred ct onRecord] {
  val UserStateBoundFeature: BoundFeature[User d, FSD screte] = UserAct v y.UserState.b nd(Author)
  val UserFeaturesSet: BoundFeatureSet = BoundFeatureSet(UserStateBoundFeature)

  /**
   * Boolean features about v e r's user state. 
   * enum UserState {
   *   NEW = 0,
   *   NEAR_ZERO = 1,
   *   VERY_L GHT = 2,
   *   L GHT = 3,
   *   MED UM_TWEETER = 4,
   *   MED UM_NON_TWEETER = 5,
   *   HEAVY_NON_TWEETER = 6,
   *   HEAVY_TWEETER = 7
   * }(pers sted='true')
   */
  val  S_USER_NEW = new B nary("t  l nes.author.user_state. s_user_new", Set(UserState).asJava)
  val  S_USER_L GHT = new B nary("t  l nes.author.user_state. s_user_l ght", Set(UserState).asJava)
  val  S_USER_MED UM_TWEETER =
    new B nary("t  l nes.author.user_state. s_user_ d um_t eter", Set(UserState).asJava)
  val  S_USER_MED UM_NON_TWEETER =
    new B nary("t  l nes.author.user_state. s_user_ d um_non_t eter", Set(UserState).asJava)
  val  S_USER_HEAVY_NON_TWEETER =
    new B nary("t  l nes.author.user_state. s_user_ avy_non_t eter", Set(UserState).asJava)
  val  S_USER_HEAVY_TWEETER =
    new B nary("t  l nes.author.user_state. s_user_ avy_t eter", Set(UserState).asJava)
  val userStateToFeatureMap: Map[Long, B nary] = Map(
    0L ->  S_USER_NEW,
    1L ->  S_USER_L GHT,
    2L ->  S_USER_L GHT,
    3L ->  S_USER_L GHT,
    4L ->  S_USER_MED UM_TWEETER,
    5L ->  S_USER_MED UM_NON_TWEETER,
    6L ->  S_USER_HEAVY_NON_TWEETER,
    7L ->  S_USER_HEAVY_TWEETER
  )

  val UserStateBooleanFeatures: Set[Feature[_]] = userStateToFeatureMap.values.toSet

  pr vate val allFeatures: Seq[Feature[_]] = UserStateBooleanFeatures.toSeq
  overr de def getFeatureContext: FeatureContext = new FeatureContext(allFeatures: _*)
  overr de def commonFeatures: Set[Feature[_]] = Set.empty

  overr de def adaptToDataRecords(record: Pred ct onRecord): ut l.L st[DataRecord] = {
    val newRecord = new R chDataRecord(new DataRecord)
    record
      .getFeatureValue(UserStateBoundFeature)
      .flatMap { userState => userStateToFeatureMap.get(userState.value) }.foreach {
        booleanFeature => newRecord.setFeatureValue[JBoolean](booleanFeature, true)
      }

    L st(newRecord.getRecord).asJava
  }
}
