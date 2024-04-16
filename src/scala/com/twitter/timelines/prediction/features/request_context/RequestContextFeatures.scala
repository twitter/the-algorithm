package com.tw ter.t  l nes.pred ct on.features.request_context

 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap .Feature._
 mport com.tw ter.dal.personal_data.thr ftjava.PersonalDataType._
 mport scala.collect on.JavaConverters._

object RequestContextFeatures {
  val COUNTRY_CODE =
    new Text("request_context.country_code", Set(Pr vateCountryOrReg on,  nferredCountry).asJava)
  val LANGUAGE_CODE = new Text(
    "request_context.language_code",
    Set(GeneralSett ngs, Prov dedLanguage,  nferredLanguage).asJava)
  val REQUEST_PROVENANCE = new Text("request_context.request_provenance", Set(AppUsage).asJava)
  val D SPLAY_W DTH = new Cont nuous("request_context.d splay_w dth", Set(Ot rDev ce nfo).asJava)
  val D SPLAY_HE GHT = new Cont nuous("request_context.d splay_  ght", Set(Ot rDev ce nfo).asJava)
  val D SPLAY_DP  = new Cont nuous("request_context.d splay_dp ", Set(Ot rDev ce nfo).asJava)

  // t  follow ng features are not Cont nuous Features because for e.g. cont nu y bet en
  // 23 and 0 h s cannot be handled that way.  nstead,   w ll treat each sl ce of h s/days
  //  ndependently, l ke a set of sparse b nary features.
  val T MESTAMP_GMT_HOUR =
    new D screte("request_context.t  stamp_gmt_h ", Set(Pr vateT  stamp).asJava)
  val T MESTAMP_GMT_DOW =
    new D screte("request_context.t  stamp_gmt_dow", Set(Pr vateT  stamp).asJava)

  val  S_GET_ N T AL = new B nary("request_context. s_get_ n  al")
  val  S_GET_M DDLE = new B nary("request_context. s_get_m ddle")
  val  S_GET_NEWER = new B nary("request_context. s_get_ne r")
  val  S_GET_OLDER = new B nary("request_context. s_get_older")

  // t  follow ng features are not B nary Features because t  s ce f eld  s Opt on[Boolean],
  // and   want to d st ngu sh So (false) from None. None w ll be converted to -1.
  val  S_POLL NG = new D screte("request_context. s_poll ng")
  val  S_SESS ON_START = new D screte("request_context. s_sess on_start")

  //  lps d st ngu sh requests from "ho " vs "ho _latest" (reverse chron ho  v ew).
  val T MEL NE_K ND = new Text("request_context.t  l ne_k nd")

  val featureContext = new FeatureContext(
    COUNTRY_CODE,
    LANGUAGE_CODE,
    REQUEST_PROVENANCE,
    D SPLAY_W DTH,
    D SPLAY_HE GHT,
    D SPLAY_DP ,
    T MESTAMP_GMT_HOUR,
    T MESTAMP_GMT_DOW,
     S_GET_ N T AL,
     S_GET_M DDLE,
     S_GET_NEWER,
     S_GET_OLDER,
     S_POLL NG,
     S_SESS ON_START,
    T MEL NE_K ND
  )
}
