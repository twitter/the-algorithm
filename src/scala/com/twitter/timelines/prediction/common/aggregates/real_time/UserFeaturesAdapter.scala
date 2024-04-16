package com.tw ter.t  l nes.pred ct on.common.aggregates.real_t  

 mport com.tw ter.dal.personal_data.thr ftjava.PersonalDataType. nferredGender
 mport com.tw ter.dal.personal_data.thr ftjava.PersonalDataType.UserState
 mport com.tw ter.ml.ap .Feature.B nary
 mport com.tw ter.ml.ap .Feature.Text
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap .R chDataRecord
 mport com.tw ter.ml.featurestore.catalog.ent  es.core.User
 mport com.tw ter.ml.featurestore.catalog.features.core.UserAccount
 mport com.tw ter.ml.featurestore.catalog.features.geo.UserLocat on
 mport com.tw ter.ml.featurestore.catalog.features.mag crecs.UserAct v y
 mport com.tw ter.ml.featurestore.l b.Ent y d
 mport com.tw ter.ml.featurestore.l b.data.Pred ct onRecord
 mport com.tw ter.ml.featurestore.l b.feature.BoundFeature
 mport com.tw ter.ml.featurestore.l b.feature.BoundFeatureSet
 mport com.tw ter.ml.featurestore.l b.User d
 mport com.tw ter.ml.featurestore.l b.{D screte => FSD screte}
 mport com.tw ter.t  l nes.pred ct on.common.adapters.T  l nesAdapterBase
 mport com.tw ter.t  l nes.pred ct on.features.user_ alth.User althFeatures
 mport java.lang.{Boolean => JBoolean}
 mport java.lang.{Str ng => JStr ng}
 mport java.ut l
 mport scala.collect on.JavaConverters._

object UserFeaturesAdapter extends T  l nesAdapterBase[Pred ct onRecord] {
  val UserStateBoundFeature: BoundFeature[User d, FSD screte] = UserAct v y.UserState.b nd(User)

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
  val  S_USER_NEW = new B nary("t  l nes.user_state. s_user_new", Set(UserState).asJava)
  val  S_USER_L GHT = new B nary("t  l nes.user_state. s_user_l ght", Set(UserState).asJava)
  val  S_USER_MED UM_TWEETER =
    new B nary("t  l nes.user_state. s_user_ d um_t eter", Set(UserState).asJava)
  val  S_USER_MED UM_NON_TWEETER =
    new B nary("t  l nes.user_state. s_user_ d um_non_t eter", Set(UserState).asJava)
  val  S_USER_HEAVY_NON_TWEETER =
    new B nary("t  l nes.user_state. s_user_ avy_non_t eter", Set(UserState).asJava)
  val  S_USER_HEAVY_TWEETER =
    new B nary("t  l nes.user_state. s_user_ avy_t eter", Set(UserState).asJava)
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


  val USER_COUNTRY_ D = new Text("geo.user_locat on.country_code")
  val UserCountryCodeFeature: BoundFeature[User d, Str ng] =
    UserLocat on.CountryCodeAlpha2.b nd(User)
  val UserLocat onFeatures: Set[Feature[_]] = Set(USER_COUNTRY_ D)

  pr vate val UserVer f edFeaturesSet = Set(
    UserAccount. sUserVer f ed.b nd(User),
    UserAccount. sUserBlueVer f ed.b nd(User),
    UserAccount. sUserGoldVer f ed.b nd(User),
    UserAccount. sUserGrayVer f ed.b nd(User)
  )

  val UserFeaturesSet: BoundFeatureSet =
    BoundFeatureSet(UserStateBoundFeature, UserCountryCodeFeature) ++
      BoundFeatureSet(UserVer f edFeaturesSet.as nstanceOf[Set[BoundFeature[_ <: Ent y d, _]]])

  pr vate val allFeatures: Seq[Feature[_]] =
    UserStateBooleanFeatures.toSeq ++ GenderBooleanFeatures.toSeq ++
      UserLocat onFeatures.toSeq ++ Seq(User althFeatures. sUserVer f edUn on)

  overr de def getFeatureContext: FeatureContext = new FeatureContext(allFeatures: _*)
  overr de def commonFeatures: Set[Feature[_]] = Set.empty

  overr de def adaptToDataRecords(record: Pred ct onRecord): ut l.L st[DataRecord] = {
    val newRecord = new R chDataRecord(new DataRecord)
    record
      .getFeatureValue(UserStateBoundFeature)
      .flatMap { userState => userStateToFeatureMap.get(userState.value) }.foreach {
        booleanFeature => newRecord.setFeatureValue[JBoolean](booleanFeature, true)
      }
    record.getFeatureValue(UserCountryCodeFeature).foreach { countryCodeFeatureValue =>
      newRecord.setFeatureValue[JStr ng](USER_COUNTRY_ D, countryCodeFeatureValue)
    }

    val  sUserVer f edUn on =
      UserVer f edFeaturesSet.ex sts(feature => record.getFeatureValue(feature).getOrElse(false))
    newRecord.setFeatureValue[JBoolean](User althFeatures. sUserVer f edUn on,  sUserVer f edUn on)

    L st(newRecord.getRecord).asJava
  }
}
