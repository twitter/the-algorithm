package com.tw ter.t  l neranker.para ters.uteg_l ked_by_t ets

 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.t  l nes.ut l.bounds.BoundsW hDefault

object UtegL kedByT etsParams {

  val Probab l yRandomT et: BoundsW hDefault[Double] = BoundsW hDefault[Double](0.0, 1.0, 0.0)

  object DefaultUTEG nNetworkCount extends Param(200)

  object DefaultUTEGOutOfNetworkCount extends Param(800)

  object DefaultMaxT etCount extends Param(200)

  /**
   * Enables semant c core, pengu n, and t etyp e content features  n uteg l ked by t ets s ce.
   */
  object EnableContentFeaturesHydrat onParam extends Param(false)

  /**
   * add  onally enables tokens w n hydrat ng content features.
   */
  object EnableTokens nContentFeaturesHydrat onParam
      extends FSParam(
        na  = "uteg_l ked_by_t ets_enable_tokens_ n_content_features_hydrat on",
        default = false
      )

  /**
   * add  onally enables t et text w n hydrat ng content features.
   * T  only works  f EnableContentFeaturesHydrat onParam  s set to true
   */
  object EnableT etText nContentFeaturesHydrat onParam
      extends FSParam(
        na  = "uteg_l ked_by_t ets_enable_t et_text_ n_content_features_hydrat on",
        default = false
      )

  /**
   * A mult pl er for earlyb rd score w n comb n ng earlyb rd score and real graph score for rank ng.
   * Note mult pl er for realgraph score := 1.0, and only change earlyb rd score mult pl er.
   */
  object Earlyb rdScoreMult pl erParam
      extends FSBoundedParam(
        "uteg_l ked_by_t ets_earlyb rd_score_mult pl er_param",
        1.0,
        0,
        20.0
      )

  object UTEGRecom ndat onsF lter {

    /**
     * enable f lter ng of UTEG recom ndat ons based on soc al proof type
     */
    object EnableParam
        extends FSParam(
          "uteg_l ked_by_t ets_uteg_recom ndat ons_f lter_enable",
          false
        )

    /**
     * f lters out UTEG recom ndat ons that have been t eted by so one t  user follows
     */
    object ExcludeT etParam
        extends FSParam(
          "uteg_l ked_by_t ets_uteg_recom ndat ons_f lter_exclude_t et",
          false
        )

    /**
     * f lters out UTEG recom ndat ons that have been ret eted by so one t  user follows
     */
    object ExcludeRet etParam
        extends FSParam(
          "uteg_l ked_by_t ets_uteg_recom ndat ons_f lter_exclude_ret et",
          false
        )

    /**
     * f lters out UTEG recom ndat ons that have been repl ed to by so one t  user follows
     * not f lter ng out t  repl es
     */
    object ExcludeReplyParam
        extends FSParam(
          "uteg_l ked_by_t ets_uteg_recom ndat ons_f lter_exclude_reply",
          false
        )

    /**
     * f lters out UTEG recom ndat ons that have been quoted by so one t  user follows
     */
    object ExcludeQuoteT etParam
        extends FSParam(
          "uteg_l ked_by_t ets_uteg_recom ndat ons_f lter_exclude_quote",
          false
        )

    /**
     * f lters out recom nded repl es that have been d rected at out of network users.
     */
    object ExcludeRecom ndedRepl esToNonFollo dUsersParam
        extends FSParam(
          na  =
            "uteg_l ked_by_t ets_uteg_recom ndat ons_f lter_exclude_recom nded_repl es_to_non_follo d_users",
          default = false
        )
  }

  /**
   * M n mum number of favor ed-by users requ red for recom nded t ets.
   */
  object M nNumFavor edByUser dsParam extends Param(1)

  /**
   *  ncludes one or mult ple random t ets  n t  response.
   */
  object  ncludeRandomT etParam
      extends FSParam(na  = "uteg_l ked_by_t ets_ nclude_random_t et", default = false)

  /**
   * One s ngle random t et (true) or tag t et as random w h g ven probab l y (false).
   */
  object  ncludeS ngleRandomT etParam
      extends FSParam(na  = "uteg_l ked_by_t ets_ nclude_random_t et_s ngle", default = false)

  /**
   * Probab l y to tag a t et as random (w ll not be ranked).
   */
  object Probab l yRandomT etParam
      extends FSBoundedParam(
        na  = "uteg_l ked_by_t ets_ nclude_random_t et_probab l y",
        default = Probab l yRandomT et.default,
        m n = Probab l yRandomT et.bounds.m n nclus ve,
        max = Probab l yRandomT et.bounds.max nclus ve)

  /**
   * add  onally enables conversat onControl w n hydrat ng content features.
   * T  only works  f EnableContentFeaturesHydrat onParam  s set to true
   */

  object EnableConversat onControl nContentFeaturesHydrat onParam
      extends FSParam(
        na  = "conversat on_control_ n_content_features_hydrat on_uteg_l ked_by_t ets_enable",
        default = false
      )

  object EnableT et d aHydrat onParam
      extends FSParam(
        na  = "t et_ d a_hydrat on_uteg_l ked_by_t ets_enable",
        default = false
      )

  object NumAdd  onalRepl esParam
      extends FSBoundedParam(
        na  = "uteg_l ked_by_t ets_num_add  onal_repl es",
        default = 0,
        m n = 0,
        max = 1000
      )

  /**
   * Enable relevance search, ot rw se recency search from earlyb rd.
   */
  object EnableRelevanceSearchParam
      extends FSParam(
        na  = "uteg_l ked_by_t ets_enable_relevance_search",
        default = true
      )

}
