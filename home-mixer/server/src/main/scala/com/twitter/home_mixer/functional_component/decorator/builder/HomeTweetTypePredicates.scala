package com.tw ter.ho _m xer.funct onal_component.decorator.bu lder

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.ho _m xer.model.Ho Features._
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Bas cTop cContextFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Recom ndat onTop cContextFunct onal yType
 mport com.tw ter.t  l nem xer. nject on.model.cand date.Semant cCoreFeatures
 mport com.tw ter.t etyp e.{thr ftscala => tpt}

object Ho T etTypePred cates {

  /**
   *  MPORTANT: Please avo d logg ng t et types that are t ed to sens  ve
   *  nternal author  nformat on / labels (e.g. bl nk labels, abuse labels, or geo-locat on).
   */
  pr vate[t ] val Cand datePred cates: Seq[(Str ng, FeatureMap => Boolean)] = Seq(
    ("w h_cand date", _ => true),
    ("ret et", _.getOrElse( sRet etFeature, false)),
    ("reply", _.getOrElse( nReplyToT et dFeature, None).nonEmpty),
    (" mage", _.getOrElse(Earlyb rdFeature, None).ex sts(_.has mage)),
    ("v deo", _.getOrElse(Earlyb rdFeature, None).ex sts(_.hasV deo)),
    ("l nk", _.getOrElse(Earlyb rdFeature, None).ex sts(_.hasV s bleL nk)),
    ("quote", _.getOrElse(Earlyb rdFeature, None).ex sts(_.hasQuote.conta ns(true))),
    ("l ke_soc al_context", _.getOrElse(NonSelfFavor edByUser dsFeature, Seq.empty).nonEmpty),
    ("protected", _.getOrElse(Earlyb rdFeature, None).ex sts(_. sProtected)),
    (
      "has_exclus ve_conversat on_author_ d",
      _.getOrElse(Exclus veConversat onAuthor dFeature, None).nonEmpty),
    (" s_el g ble_for_connect_boost", _ => false),
    ("hashtag", _.getOrElse(Earlyb rdFeature, None).ex sts(_.numHashtags > 0)),
    ("has_sc duled_space", _.getOrElse(Aud oSpace taDataFeature, None).ex sts(_. sSc duled)),
    ("has_recorded_space", _.getOrElse(Aud oSpace taDataFeature, None).ex sts(_. sRecorded)),
    (" s_read_from_cac ", _.getOrElse( sReadFromCac Feature, false)),
    ("get_ n  al", _.getOrElse(Get n  alFeature, false)),
    ("get_ne r", _.getOrElse(GetNe rFeature, false)),
    ("get_m ddle", _.getOrElse(GetM ddleFeature, false)),
    ("get_older", _.getOrElse(GetOlderFeature, false)),
    ("pull_to_refresh", _.getOrElse(PullToRefreshFeature, false)),
    ("poll ng", _.getOrElse(Poll ngFeature, false)),
    ("near_empty", _.getOrElse(ServedS zeFeature, None).ex sts(_ < 3)),
    (" s_request_context_launch", _.getOrElse( sLaunchRequestFeature, false)),
    ("mutual_follow", _.getOrElse(Earlyb rdFeature, None).ex sts(_.fromMutualFollow)),
    (
      "less_than_10_m ns_s nce_lnpt",
      _.getOrElse(LastNonPoll ngT  Feature, None).ex sts(_.unt lNow < 10.m nutes)),
    ("served_ n_conversat on_module", _.getOrElse(Served nConversat onModuleFeature, false)),
    ("has_t cketed_space", _.getOrElse(Aud oSpace taDataFeature, None).ex sts(_.hasT ckets)),
    (" n_ut s_top5", _.getOrElse(Pos  onFeature, None).ex sts(_ < 5)),
    (
      "conversat on_module_has_2_d splayed_t ets",
      _.getOrElse(Conversat onModule2D splayedT etsFeature, false)),
    ("empty_request", _.getOrElse(ServedS zeFeature, None).ex sts(_ == 0)),
    ("served_s ze_less_than_50", _.getOrElse(ServedS zeFeature, None).ex sts(_ < 50)),
    (
      "served_s ze_bet en_50_and_100",
      _.getOrElse(ServedS zeFeature, None).ex sts(s ze => s ze >= 50 && s ze < 100)),
    ("authored_by_contextual_user", _.getOrElse(AuthoredByContextualUserFeature, false)),
    (
      " s_self_thread_t et",
      _.getOrElse(Conversat onFeature, None).ex sts(_. sSelfThreadT et.conta ns(true))),
    ("has_ancestors", _.getOrElse(AncestorsFeature, Seq.empty).nonEmpty),
    ("full_scor ng_succeeded", _.getOrElse(FullScor ngSucceededFeature, false)),
    ("served_s ze_less_than_20", _.getOrElse(ServedS zeFeature, None).ex sts(_ < 20)),
    ("served_s ze_less_than_10", _.getOrElse(ServedS zeFeature, None).ex sts(_ < 10)),
    ("served_s ze_less_than_5", _.getOrElse(ServedS zeFeature, None).ex sts(_ < 5)),
    (
      "account_age_less_than_30_m nutes",
      _.getOrElse(AccountAgeFeature, None).ex sts(_.unt lNow < 30.m nutes)),
    ("conversat on_module_has_gap", _.getOrElse(Conversat onModuleHasGapFeature, false)),
    (
      "d rected_at_user_ s_ n_f rst_degree",
      _.getOrElse(Earlyb rdFeature, None).ex sts(_.d rectedAtUser d s nF rstDegree.conta ns(true))),
    (
      "has_semant c_core_annotat on",
      _.getOrElse(Earlyb rdFeature, None).ex sts(_.semant cCoreAnnotat ons.nonEmpty)),
    (" s_request_context_foreground", _.getOrElse( sForegroundRequestFeature, false)),
    (
      "account_age_less_than_1_day",
      _.getOrElse(AccountAgeFeature, None).ex sts(_.unt lNow < 1.day)),
    (
      "account_age_less_than_7_days",
      _.getOrElse(AccountAgeFeature, None).ex sts(_.unt lNow < 7.days)),
    (
      "part_of_utt",
      _.getOrElse(Earlyb rdFeature, None)
        .ex sts(_.semant cCoreAnnotat ons.ex sts(_.ex sts(annotat on =>
          annotat on.doma n d == Semant cCoreFeatures.Un f edTw terTaxono )))),
    (
      "has_ho _latest_request_past_ ek",
      _.getOrElse(Follow ngLastNonPoll ngT  Feature, None).ex sts(_.unt lNow < 7.days)),
    (" s_ut s_pos0", _.getOrElse(Pos  onFeature, None).ex sts(_ == 0)),
    (" s_ut s_pos1", _.getOrElse(Pos  onFeature, None).ex sts(_ == 1)),
    (" s_ut s_pos2", _.getOrElse(Pos  onFeature, None).ex sts(_ == 2)),
    (" s_ut s_pos3", _.getOrElse(Pos  onFeature, None).ex sts(_ == 3)),
    (" s_ut s_pos4", _.getOrElse(Pos  onFeature, None).ex sts(_ == 4)),
    (" s_random_t et", _.getOrElse( sRandomT etFeature, false)),
    ("has_random_t et_ n_response", _.getOrElse(HasRandomT etFeature, false)),
    (" s_random_t et_above_ n_ut s", _.getOrElse( sRandomT etAboveFeature, false)),
    (
      "has_ancestor_authored_by_v e r",
      cand date =>
        cand date
          .getOrElse(AncestorsFeature, Seq.empty).ex sts(ancestor =>
            cand date.getOrElse(V e r dFeature, 0L) == ancestor.user d)),
    ("ancestor", _.getOrElse( sAncestorCand dateFeature, false)),
    (
      "deep_reply",
      cand date =>
        cand date.getOrElse( nReplyToT et dFeature, None).nonEmpty && cand date
          .getOrElse(AncestorsFeature, Seq.empty).s ze > 2),
    (
      "has_s mcluster_embedd ngs",
      _.getOrElse(
        S mclustersT etTopKClustersW hScoresFeature,
        Map.empty[Str ng, Double]).nonEmpty),
    (
      "t et_age_less_than_15_seconds",
      _.getOrElse(Or g nalT etCreat onT  FromSnowflakeFeature, None)
        .ex sts(_.unt lNow <= 15.seconds)),
    (
      "less_than_1_h _s nce_lnpt",
      _.getOrElse(LastNonPoll ngT  Feature, None).ex sts(_.unt lNow < 1.h )),
    ("has_gte_10_favs", _.getOrElse(Earlyb rdFeature, None).ex sts(_.favCountV2.ex sts(_ >= 10))),
    (
      "dev ce_language_matc s_t et_language",
      cand date =>
        cand date.getOrElse(T etLanguageFeature, None) ==
          cand date.getOrElse(Dev ceLanguageFeature, None)),
    (
      "root_ancestor",
      cand date =>
        cand date.getOrElse( sAncestorCand dateFeature, false) && cand date
          .getOrElse( nReplyToT et dFeature, None). sEmpty),
    ("quest on", _.getOrElse(Earlyb rdFeature, None).ex sts(_.hasQuest on.conta ns(true))),
    (" n_network", _.getOrElse( nNetworkFeature, true)),
    (
      "has_pol  cal_annotat on",
      _.getOrElse(Earlyb rdFeature, None).ex sts(
        _.semant cCoreAnnotat ons.ex sts(
          _.ex sts(annotat on =>
            Semant cCoreFeatures.Pol  calDoma ns.conta ns(annotat on.doma n d) ||
              (annotat on.doma n d == Semant cCoreFeatures.Un f edTw terTaxono  &&
                annotat on.ent y d == Semant cCoreFeatures.UttPol  csEnt y d))))),
    (
      " s_dont_at_ _by_ nv at on",
      _.getOrElse(Earlyb rdFeature, None).ex sts(
        _.conversat onControl.ex sts(_. s nstanceOf[tpt.Conversat onControl.By nv at on]))),
    (
      " s_dont_at_ _commun y",
      _.getOrElse(Earlyb rdFeature, None)
        .ex sts(_.conversat onControl.ex sts(_. s nstanceOf[tpt.Conversat onControl.Commun y]))),
    ("has_zero_score", _.getOrElse(ScoreFeature, None).ex sts(_ == 0.0)),
    (
      " s_follo d_top c_t et",
      _.getOrElse(Top cContextFunct onal yTypeFeature, None)
        .ex sts(_ == Bas cTop cContextFunct onal yType)),
    (
      " s_recom nded_top c_t et",
      _.getOrElse(Top cContextFunct onal yTypeFeature, None)
        .ex sts(_ == Recom ndat onTop cContextFunct onal yType)),
    ("has_gte_100_favs", _.getOrElse(Earlyb rdFeature, None).ex sts(_.favCountV2.ex sts(_ >= 100))),
    ("has_gte_1k_favs", _.getOrElse(Earlyb rdFeature, None).ex sts(_.favCountV2.ex sts(_ >= 1000))),
    (
      "has_gte_10k_favs",
      _.getOrElse(Earlyb rdFeature, None).ex sts(_.favCountV2.ex sts(_ >= 10000))),
    (
      "has_gte_100k_favs",
      _.getOrElse(Earlyb rdFeature, None).ex sts(_.favCountV2.ex sts(_ >= 100000))),
    ("has_aud o_space", _.getOrElse(Aud oSpace taDataFeature, None).ex sts(_.hasSpace)),
    ("has_l ve_aud o_space", _.getOrElse(Aud oSpace taDataFeature, None).ex sts(_. sL ve)),
    (
      "has_gte_10_ret ets",
      _.getOrElse(Earlyb rdFeature, None).ex sts(_.ret etCountV2.ex sts(_ >= 10))),
    (
      "has_gte_100_ret ets",
      _.getOrElse(Earlyb rdFeature, None).ex sts(_.ret etCountV2.ex sts(_ >= 100))),
    (
      "has_gte_1k_ret ets",
      _.getOrElse(Earlyb rdFeature, None).ex sts(_.ret etCountV2.ex sts(_ >= 1000))),
    (
      "has_us_pol  cal_annotat on",
      _.getOrElse(Earlyb rdFeature, None)
        .ex sts(_.semant cCoreAnnotat ons.ex sts(_.ex sts(annotat on =>
          annotat on.doma n d == Semant cCoreFeatures.Un f edTw terTaxono  &&
            annotat on.ent y d == Semant cCoreFeatures.usPol  calT etEnt y d &&
            annotat on.group d == Semant cCoreFeatures.UsPol  calT etAnnotat onGroup ds.BalancedV0)))),
    (
      "has_tox c y_score_above_threshold",
      _.getOrElse(Earlyb rdFeature, None).ex sts(_.tox c yScore.ex sts(_ > 0.91))),
    (" s_top c_t et", _.getOrElse(Top c dSoc alContextFeature, None). sDef ned),
    (
      "text_only",
      cand date =>
        cand date.getOrElse(HasD splayedTextFeature, false) &&
          !(cand date.getOrElse(Earlyb rdFeature, None).ex sts(_.has mage) ||
            cand date.getOrElse(Earlyb rdFeature, None).ex sts(_.hasV deo) ||
            cand date.getOrElse(Earlyb rdFeature, None).ex sts(_.hasCard))),
    (
      " mage_only",
      cand date =>
        cand date.getOrElse(Earlyb rdFeature, None).ex sts(_.has mage) &&
          !cand date.getOrElse(HasD splayedTextFeature, false)),
    ("has_1_ mage", _.getOrElse(Num magesFeature, None).ex sts(_ == 1)),
    ("has_2_ mages", _.getOrElse(Num magesFeature, None).ex sts(_ == 2)),
    ("has_3_ mages", _.getOrElse(Num magesFeature, None).ex sts(_ == 3)),
    ("has_4_ mages", _.getOrElse(Num magesFeature, None).ex sts(_ == 4)),
    ("has_card", _.getOrElse(Earlyb rdFeature, None).ex sts(_.hasCard)),
    ("user_follow_count_gte_50", _.getOrElse(UserFollow ngCountFeature, None).ex sts(_ > 50)),
    (
      "has_l ked_by_soc al_context",
      cand dateFeatures =>
        cand dateFeatures
          .getOrElse(SGSVal dL kedByUser dsFeature, Seq.empty)
          .ex sts(cand dateFeatures
            .getOrElse(Perspect veF lteredL kedByUser dsFeature, Seq.empty).toSet.conta ns)),
    (
      "has_follo d_by_soc al_context",
      _.getOrElse(SGSVal dFollo dByUser dsFeature, Seq.empty).nonEmpty),
    (
      "has_top c_soc al_context",
      cand dateFeatures =>
        cand dateFeatures
          .getOrElse(Top c dSoc alContextFeature, None)
          . sDef ned &&
          cand dateFeatures.getOrElse(Top cContextFunct onal yTypeFeature, None). sDef ned),
    ("v deo_lte_10_sec", _.getOrElse(V deoDurat onMsFeature, None).ex sts(_ <= 10000)),
    (
      "v deo_bt_10_60_sec",
      _.getOrElse(V deoDurat onMsFeature, None).ex sts(durat on =>
        durat on > 10000 && durat on <= 60000)),
    ("v deo_gt_60_sec", _.getOrElse(V deoDurat onMsFeature, None).ex sts(_ > 60000)),
    (
      "t et_age_lte_30_m nutes",
      _.getOrElse(Or g nalT etCreat onT  FromSnowflakeFeature, None)
        .ex sts(_.unt lNow <= 30.m nutes)),
    (
      "t et_age_lte_1_h ",
      _.getOrElse(Or g nalT etCreat onT  FromSnowflakeFeature, None)
        .ex sts(_.unt lNow <= 1.h )),
    (
      "t et_age_lte_6_h s",
      _.getOrElse(Or g nalT etCreat onT  FromSnowflakeFeature, None)
        .ex sts(_.unt lNow <= 6.h s)),
    (
      "t et_age_lte_12_h s",
      _.getOrElse(Or g nalT etCreat onT  FromSnowflakeFeature, None)
        .ex sts(_.unt lNow <= 12.h s)),
    (
      "t et_age_gte_24_h s",
      _.getOrElse(Or g nalT etCreat onT  FromSnowflakeFeature, None)
        .ex sts(_.unt lNow >= 24.h s)),
  )

  val Pred cateMap = Cand datePred cates.toMap
}
