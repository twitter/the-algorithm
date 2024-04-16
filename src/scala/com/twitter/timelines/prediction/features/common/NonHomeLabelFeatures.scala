package com.tw ter.t  l nes.pred ct on.features.common

 mport com.tw ter.dal.personal_data.thr ftjava.PersonalDataType._
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .Feature.B nary
 mport java.lang.{Boolean => JBoolean}
 mport scala.collect on.JavaConverters._

object Prof leLabelFeatures {
  pr vate val pref x = "prof le"

  val  S_CL CKED =
    new B nary(s"${pref x}.engage nt. s_cl cked", Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_DWELLED =
    new B nary(s"${pref x}.engage nt. s_d lled", Set(T etsV e d, Engage ntsPr vate).asJava)
  val  S_FAVOR TED = new B nary(
    s"${pref x}.engage nt. s_favor ed",
    Set(Publ cL kes, Pr vateL kes, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_REPL ED = new B nary(
    s"${pref x}.engage nt. s_repl ed",
    Set(Publ cRepl es, Pr vateRepl es, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_RETWEETED = new B nary(
    s"${pref x}.engage nt. s_ret eted",
    Set(Publ cRet ets, Pr vateRet ets, Engage ntsPr vate, Engage ntsPubl c).asJava)

  // Negat ve engage nts
  val  S_DONT_L KE =
    new B nary(s"${pref x}.engage nt. s_dont_l ke", Set(Engage ntsPr vate).asJava)
  val  S_BLOCK_CL CKED = new B nary(
    s"${pref x}.engage nt. s_block_cl cked",
    Set(Blocks, T etsCl cked, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_MUTE_CL CKED = new B nary(
    s"${pref x}.engage nt. s_mute_cl cked",
    Set(Mutes, T etsCl cked, Engage ntsPr vate).asJava)
  val  S_REPORT_TWEET_CL CKED = new B nary(
    s"${pref x}.engage nt. s_report_t et_cl cked",
    Set(T etsCl cked, Engage ntsPr vate).asJava)

  val  S_NEGAT VE_FEEDBACK_UN ON = new B nary(
    s"${pref x}.engage nt. s_negat ve_feedback_un on",
    Set(Engage ntsPr vate, Blocks, Mutes, T etsCl cked, Engage ntsPubl c).asJava)

  val CoreEngage nts: Set[Feature[JBoolean]] = Set(
     S_CL CKED,
     S_DWELLED,
     S_FAVOR TED,
     S_REPL ED,
     S_RETWEETED
  )

  val Negat veEngage nts: Set[Feature[JBoolean]] = Set(
     S_DONT_L KE,
     S_BLOCK_CL CKED,
     S_MUTE_CL CKED,
     S_REPORT_TWEET_CL CKED
  )

}

object SearchLabelFeatures {
  pr vate val pref x = "search"

  val  S_CL CKED =
    new B nary(s"${pref x}.engage nt. s_cl cked", Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_DWELLED =
    new B nary(s"${pref x}.engage nt. s_d lled", Set(T etsV e d, Engage ntsPr vate).asJava)
  val  S_FAVOR TED = new B nary(
    s"${pref x}.engage nt. s_favor ed",
    Set(Publ cL kes, Pr vateL kes, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_REPL ED = new B nary(
    s"${pref x}.engage nt. s_repl ed",
    Set(Publ cRepl es, Pr vateRepl es, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_RETWEETED = new B nary(
    s"${pref x}.engage nt. s_ret eted",
    Set(Publ cRet ets, Pr vateRet ets, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_PROF LE_CL CKED_SEARCH_RESULT_USER = new B nary(
    s"${pref x}.engage nt. s_prof le_cl cked_search_result_user",
    Set(Prof lesCl cked, Prof lesV e d, Engage ntsPr vate).asJava)
  val  S_PROF LE_CL CKED_SEARCH_RESULT_TWEET = new B nary(
    s"${pref x}.engage nt. s_prof le_cl cked_search_result_t et",
    Set(Prof lesCl cked, Prof lesV e d, Engage ntsPr vate).asJava)
  val  S_PROF LE_CL CKED_TYPEAHEAD_USER = new B nary(
    s"${pref x}.engage nt. s_prof le_cl cked_typea ad_user",
    Set(Prof lesCl cked, Prof lesV e d, Engage ntsPr vate).asJava)

  val CoreEngage nts: Set[Feature[JBoolean]] = Set(
     S_CL CKED,
     S_DWELLED,
     S_FAVOR TED,
     S_REPL ED,
     S_RETWEETED,
     S_PROF LE_CL CKED_SEARCH_RESULT_USER,
     S_PROF LE_CL CKED_SEARCH_RESULT_TWEET,
     S_PROF LE_CL CKED_TYPEAHEAD_USER
  )
}
// Add T et Deta l labels later
