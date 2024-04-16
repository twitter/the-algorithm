package com.tw ter.cr_m xer.scr be

/**
 * Categor es def ne scr be categor es used  n cr-m xer serv ce.
 */
object Scr beCategor es {
  lazy val AllCategor es =
    L st(AbDec der, TopLevelAp Ddg tr cs, T etsRecs)

  /**
   * AbDec der represents scr be logs for exper  nts
   */
  lazy val AbDec der: Scr beCategory = Scr beCategory(
    "abdec der_scr be",
    "cl ent_event"
  )

  /**
   * Top-Level Cl ent event scr be logs, to record changes  n system  tr cs (e.g. latency,
   * cand dates returned, empty rate ) per exper  nt bucket, and store t m  n DDG  tr c group
   */
  lazy val TopLevelAp Ddg tr cs: Scr beCategory = Scr beCategory(
    "top_level_ap _ddg_ tr cs_scr be",
    "cl ent_event"
  )

  lazy val T etsRecs: Scr beCategory = Scr beCategory(
    "get_t ets_recom ndat ons_scr be",
    "cr_m xer_get_t ets_recom ndat ons"
  )

  lazy val V TT etsRecs: Scr beCategory = Scr beCategory(
    "get_v _t ets_recom ndat ons_scr be",
    "cr_m xer_get_v _t ets_recom ndat ons"
  )

  lazy val RelatedT ets: Scr beCategory = Scr beCategory(
    "get_related_t ets_scr be",
    "cr_m xer_get_related_t ets"
  )

  lazy val UtegT ets: Scr beCategory = Scr beCategory(
    "get_uteg_t ets_scr be",
    "cr_m xer_get_uteg_t ets"
  )

  lazy val AdsRecom ndat ons: Scr beCategory = Scr beCategory(
    "get_ads_recom ndat ons_scr be",
    "cr_m xer_get_ads_recom ndat ons"
  )
}

/**
 * Category represents each scr be log data.
 *
 * @param loggerFactoryNode loggerFactory node na   n cr-m xer assoc ated w h t  scr be category
 * @param scr beCategory    scr be category na  (globally un que at Tw ter)
 */
case class Scr beCategory(
  loggerFactoryNode: Str ng,
  scr beCategory: Str ng) {
  def getProdLoggerFactoryNode: Str ng = loggerFactoryNode
  def getStag ngLoggerFactoryNode: Str ng = "stag ng_" + loggerFactoryNode
}
