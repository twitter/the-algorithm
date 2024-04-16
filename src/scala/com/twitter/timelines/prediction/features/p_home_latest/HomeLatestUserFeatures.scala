package com.tw ter.t  l nes.pred ct on.features.p_ho _latest

 mport com.tw ter.ml.ap .Feature.{Cont nuous, D screte}
 mport com.tw ter.dal.personal_data.thr ftjava.PersonalDataType._
 mport scala.collect on.JavaConverters._

object Ho LatestUserFeatures {
  val LAST_LOG N_T MESTAMP_MS =
    new D screte("ho _latest.user_feature.last_log n_t  stamp_ms", Set(Pr vateT  stamp).asJava)
}

object Ho LatestUserAggregatesFeatures {

  /**
   * Used as `t  stampFeature`  n `Offl neAggregateS ce` requ red by feature aggregat ons, set to
   * t  `dateRange` end t  stamp by default
   */
  val AGGREGATE_T MESTAMP_MS =
    new D screte("ho _latest.user_feature.aggregate_t  stamp_ms", Set(Pr vateT  stamp).asJava)
  val HOME_TOP_ MPRESS ONS =
    new Cont nuous("ho _latest.user_feature.ho _top_ mpress ons", Set(CountOf mpress on).asJava)
  val HOME_LATEST_ MPRESS ONS =
    new Cont nuous(
      "ho _latest.user_feature.ho _latest_ mpress ons",
      Set(CountOf mpress on).asJava)
  val HOME_TOP_LAST_LOG N_T MESTAMP_MS =
    new D screte(
      "ho _latest.user_feature.ho _top_last_log n_t  stamp_ms",
      Set(Pr vateT  stamp).asJava)
  val HOME_LATEST_LAST_LOG N_T MESTAMP_MS =
    new D screte(
      "ho _latest.user_feature.ho _latest_last_log n_t  stamp_ms",
      Set(Pr vateT  stamp).asJava)
  val HOME_LATEST_MOST_RECENT_CL CK_T MESTAMP_MS =
    new D screte(
      "ho _latest.user_feature.ho _latest_most_recent_cl ck_t  stamp_ms",
      Set(Pr vateT  stamp).asJava)
}

case class Ho LatestUserFeatures(user d: Long, lastLog nT  stampMs: Long)

case class Ho LatestUserAggregatesFeatures(
  user d: Long,
  aggregateT  stampMs: Long,
  ho Top mpress ons: Opt on[Double],
  ho Latest mpress ons: Opt on[Double],
  ho TopLastLog nT  stampMs: Opt on[Long],
  ho LatestLastLog nT  stampMs: Opt on[Long],
  ho LatestMostRecentCl ckT  stampMs: Opt on[Long])
