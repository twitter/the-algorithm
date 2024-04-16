package com.tw ter.t  l nes.pred ct on.features.cl ent_log_event

 mport com.tw ter.dal.personal_data.thr ftjava.PersonalDataType._
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .Feature.B nary
 mport com.tw ter.ml.ap .Feature.Cont nuous
 mport com.tw ter.ml.ap .Feature.D screte
 mport scala.collect on.JavaConverters._
 mport com.tw ter.t  l neserv ce.suggests.logg ng.cand date_t et_s ce_ d.thr ftscala.Cand dateT etS ce d

object Cl entLogEventDataRecordFeatures {
  val HasConsu rV deo = new B nary(
    "cl ent_log_event.t et.has_consu r_v deo",
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val PhotoCount = new Cont nuous(
    "cl ent_log_event.t et.photo_count",
    Set(CountOfPr vateT etEnt  esAnd tadata, CountOfPubl cT etEnt  esAnd tadata).asJava)
  val Has mage = new B nary(
    "cl ent_log_event.t et.has_ mage",
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val  sReply =
    new B nary("cl ent_log_event.t et. s_reply", Set(Publ cRepl es, Pr vateRepl es).asJava)
  val  sRet et =
    new B nary("cl ent_log_event.t et. s_ret et", Set(Publ cRet ets, Pr vateRet ets).asJava)
  val  sPromoted =
    new B nary(
      "cl ent_log_event.t et. s_promoted",
      Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HasV s bleL nk = new B nary(
    "cl ent_log_event.t et.has_v s ble_l nk",
    Set(UrlFoundFlag, Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HasHashtag = new B nary(
    "cl ent_log_event.t et.has_hashtag",
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val FromMutualFollow = new B nary("cl ent_log_event.t et.from_mutual_follow")
  val  s nNetwork = new B nary("cl ent_log_event.t et. s_ n_network")
  val  sNot nNetwork = new B nary("cl ent_log_event.t et. s_not_ n_network")
  val FromRecap = new B nary("cl ent_log_event.t et.from_recap")
  val FromRecycled = new B nary("cl ent_log_event.t et.from_recycled")
  val FromAct v y = new B nary("cl ent_log_event.t et.from_act v y")
  val FromS mcluster = new B nary("cl ent_log_event.t et.from_s mcluster")
  val FromErg = new B nary("cl ent_log_event.t et.from_erg")
  val FromCroon = new B nary("cl ent_log_event.t et.from_croon")
  val FromL st = new B nary("cl ent_log_event.t et.from_l st")
  val FromRecTop c = new B nary("cl ent_log_event.t et.from_rec_top c")
  val  njectedPos  on = new D screte("cl ent_log_event.t et. njectedPos  on")
  val TextOnly = new B nary("cl ent_log_event.t et.text_only")
  val HasL kedBySoc alContext = new B nary("cl ent_log_event.t et.has_l ked_by_soc al_context")
  val HasFollo dBySoc alContext = new B nary(
    "cl ent_log_event.t et.has_follo d_by_soc al_context")
  val HasTop cSoc alContext = new B nary("cl ent_log_event.t et.has_top c_soc al_context")
  val  sFollo dTop cT et = new B nary("cl ent_log_event.t et. s_follo d_top c_t et")
  val  sRecom ndedTop cT et = new B nary("cl ent_log_event.t et. s_recom nded_top c_t et")
  val  sT etAgeLessThan15Seconds = new B nary(
    "cl ent_log_event.t et.t et_age_less_than_15_seconds")
  val  sT etAgeLessThanOrEqualTo30M nutes = new B nary(
    "cl ent_log_event.t et.t et_age_lte_30_m nutes")
  val  sT etAgeLessThanOrEqualTo1H  = new B nary("cl ent_log_event.t et.t et_age_lte_1_h ")
  val  sT etAgeLessThanOrEqualTo6H s = new B nary("cl ent_log_event.t et.t et_age_lte_6_h s")
  val  sT etAgeLessThanOrEqualTo12H s = new B nary(
    "cl ent_log_event.t et.t et_age_lte_12_h s")
  val  sT etAgeGreaterThanOrEqualTo24H s = new B nary(
    "cl ent_log_event.t et.t et_age_gte_24_h s")
  val HasGreaterThanOrEqualTo100Favs = new B nary("cl ent_log_event.t et.has_gte_100_favs")
  val HasGreaterThanOrEqualTo1KFavs = new B nary("cl ent_log_event.t et.has_gte_1k_favs")
  val HasGreaterThanOrEqualTo10KFavs = new B nary("cl ent_log_event.t et.has_gte_10k_favs")
  val HasGreaterThanOrEqualTo100KFavs = new B nary("cl ent_log_event.t et.has_gte_100k_favs")
  val HasGreaterThanOrEqualTo10Ret ets = new B nary("cl ent_log_event.t et.has_gte_10_ret ets")
  val HasGreaterThanOrEqualTo100Ret ets = new B nary("cl ent_log_event.t et.has_gte_100_ret ets")
  val HasGreaterThanOrEqualTo1KRet ets = new B nary("cl ent_log_event.t et.has_gte_1k_ret ets")

  val T etTypeToFeatureMap: Map[Str ng, B nary] = Map(
    "l nk" -> HasV s bleL nk,
    "hashtag" -> HasHashtag,
    "mutual_follow" -> FromMutualFollow,
    " n_network" ->  s nNetwork,
    "text_only" -> TextOnly,
    "has_l ked_by_soc al_context" -> HasL kedBySoc alContext,
    "has_follo d_by_soc al_context" -> HasFollo dBySoc alContext,
    "has_top c_soc al_context" -> HasTop cSoc alContext,
    " s_follo d_top c_t et" ->  sFollo dTop cT et,
    " s_recom nded_top c_t et" ->  sRecom ndedTop cT et,
    "t et_age_less_than_15_seconds" ->  sT etAgeLessThan15Seconds,
    "t et_age_lte_30_m nutes" ->  sT etAgeLessThanOrEqualTo30M nutes,
    "t et_age_lte_1_h " ->  sT etAgeLessThanOrEqualTo1H ,
    "t et_age_lte_6_h s" ->  sT etAgeLessThanOrEqualTo6H s,
    "t et_age_lte_12_h s" ->  sT etAgeLessThanOrEqualTo12H s,
    "t et_age_gte_24_h s" ->  sT etAgeGreaterThanOrEqualTo24H s,
    "has_gte_100_favs" -> HasGreaterThanOrEqualTo100Favs,
    "has_gte_1k_favs" -> HasGreaterThanOrEqualTo1KFavs,
    "has_gte_10k_favs" -> HasGreaterThanOrEqualTo10KFavs,
    "has_gte_100k_favs" -> HasGreaterThanOrEqualTo100KFavs,
    "has_gte_10_ret ets" -> HasGreaterThanOrEqualTo10Ret ets,
    "has_gte_100_ret ets" -> HasGreaterThanOrEqualTo100Ret ets,
    "has_gte_1k_ret ets" -> HasGreaterThanOrEqualTo1KRet ets
  )

  val Cand dateT etS ce dFeatureMap: Map[ nt, B nary] = Map(
    Cand dateT etS ce d.RecapT et.value -> FromRecap,
    Cand dateT etS ce d.RecycledT et.value -> FromRecycled,
    Cand dateT etS ce d.Recom ndedT et.value -> FromAct v y,
    Cand dateT etS ce d.S mcluster.value -> FromS mcluster,
    Cand dateT etS ce d.ErgT et.value -> FromErg,
    Cand dateT etS ce d.CroonTop cT et.value -> FromCroon,
    Cand dateT etS ce d.CroonT et.value -> FromCroon,
    Cand dateT etS ce d.L stT et.value -> FromL st,
    Cand dateT etS ce d.Recom ndedTop cT et.value -> FromRecTop c
  )

  val T etFeaturesV2: Set[Feature[_]] = Set(
    Has mage,
     sReply,
     sRet et,
    HasV s bleL nk,
    HasHashtag,
    FromMutualFollow,
     s nNetwork
  )

  val ContentT etTypeFeatures: Set[Feature[_]] = Set(
    Has mage,
    HasV s bleL nk,
    HasHashtag,
    TextOnly,
    HasV s bleL nk
  )

  val FreshnessT etTypeFeatures: Set[Feature[_]] = Set(
     sT etAgeLessThan15Seconds,
     sT etAgeLessThanOrEqualTo30M nutes,
     sT etAgeLessThanOrEqualTo1H ,
     sT etAgeLessThanOrEqualTo6H s,
     sT etAgeLessThanOrEqualTo12H s,
     sT etAgeGreaterThanOrEqualTo24H s
  )

  val Soc alProofT etTypeFeatures: Set[Feature[_]] = Set(
    HasL kedBySoc alContext,
    HasFollo dBySoc alContext,
    HasTop cSoc alContext
  )

  val Top cT etPreferenceT etTypeFeatures: Set[Feature[_]] = Set(
     sFollo dTop cT et,
     sRecom ndedTop cT et
  )

  val T etPopular yT etTypeFeatures: Set[Feature[_]] = Set(
    HasGreaterThanOrEqualTo100Favs,
    HasGreaterThanOrEqualTo1KFavs,
    HasGreaterThanOrEqualTo10KFavs,
    HasGreaterThanOrEqualTo100KFavs,
    HasGreaterThanOrEqualTo10Ret ets,
    HasGreaterThanOrEqualTo100Ret ets,
    HasGreaterThanOrEqualTo1KRet ets
  )

  val UserGraph nteract onT etTypeFeatures: Set[Feature[_]] = Set(
     s nNetwork,
    FromMutualFollow,
     sNot nNetwork,
     sPromoted
  )

  val UserContentPreferenceT etTypeFeatures: Set[Feature[_]] =
    ContentT etTypeFeatures ++ FreshnessT etTypeFeatures ++ Soc alProofT etTypeFeatures ++ Top cT etPreferenceT etTypeFeatures ++ T etPopular yT etTypeFeatures ++ UserGraph nteract onT etTypeFeatures
  val AuthorContentPreferenceT etTypeFeatures: Set[Feature[_]] =
    Set( s nNetwork, FromMutualFollow,  sNot nNetwork) ++ ContentT etTypeFeatures
}
