package com.tw ter.t  l nes.pred ct on.features.l st_features

 mport com.tw ter.ml.ap .Feature.{B nary, D screte}
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.dal.personal_data.thr ftjava.PersonalDataType._
 mport scala.collect on.JavaConverters._

object L stFeatures {

  // l st. d  s used for l st t et  nject ons  n ho . t  l nes. ta.l st_ d  s used for l st t ets  n l st t  l ne.
  val L ST_ D = new D screte("l st. d")

  val V EWER_ S_OWNER =
    new B nary("l st.v e r. s_owner", Set(L stsNonpubl cL st, L stsPubl cL st).asJava)
  val V EWER_ S_SUBSCR BER = new B nary("l st.v e r. s_subscr ber")
  val  S_P NNED_L ST = new B nary("l st. s_p nned")

  val featureContext = new FeatureContext(
    L ST_ D,
    V EWER_ S_OWNER,
    V EWER_ S_SUBSCR BER,
     S_P NNED_L ST
  )
}
