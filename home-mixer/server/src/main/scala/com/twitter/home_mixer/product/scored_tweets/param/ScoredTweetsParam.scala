package com.tw ter.ho _m xer.product.scored_t ets.param

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.ho _m xer.param.dec der.Dec derKey
 mport com.tw ter.t  l nes.conf gap .Durat onConvers on
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .HasDurat onConvers on
 mport com.tw ter.t  l nes.conf gap .dec der.BooleanDec derParam
 mport com.tw ter.ut l.Durat on

object ScoredT etsParam {
  val SupportedCl entFSNa  = "scored_t ets_supported_cl ent"

  object Cand dateP pel ne {
    object Enable nNetworkParam
        extends BooleanDec derParam(Dec derKey.EnableScoredT ets nNetworkCand dateP pel ne)

    object EnableT etM xerParam
        extends BooleanDec derParam(Dec derKey.EnableScoredT etsT etM xerCand dateP pel ne)

    object EnableUtegParam
        extends BooleanDec derParam(Dec derKey.EnableScoredT etsUtegCand dateP pel ne)

    object EnableFrsParam
        extends BooleanDec derParam(Dec derKey.EnableScoredT etsFrsCand dateP pel ne)

    object EnableL stsParam
        extends BooleanDec derParam(Dec derKey.EnableScoredT etsL stsCand dateP pel ne)

    object EnablePopularV deosParam
        extends BooleanDec derParam(Dec derKey.EnableScoredT etsPopularV deosCand dateP pel ne)

    object EnableBackf llParam
        extends BooleanDec derParam(Dec derKey.EnableScoredT etsBackf llCand dateP pel ne)
  }

  object EnableBackf llCand dateP pel neParam
      extends FSParam[Boolean](
        na  = "scored_t ets_enable_backf ll_cand date_p pel ne",
        default = true
      )

  object Qual yFactor {
    object  nNetworkMaxT etsToScoreParam
        extends FSBoundedParam[ nt](
          na  = "scored_t ets_qual y_factor_earlyb rd_max_t ets_to_score",
          default = 500,
          m n = 0,
          max = 10000
        )

    object UtegMaxT etsToScoreParam
        extends FSBoundedParam[ nt](
          na  = "scored_t ets_qual y_factor_uteg_max_t ets_to_score",
          default = 500,
          m n = 0,
          max = 10000
        )

    object FrsMaxT etsToScoreParam
        extends FSBoundedParam[ nt](
          na  = "scored_t ets_qual y_factor_frs_max_t ets_to_score",
          default = 500,
          m n = 0,
          max = 10000
        )

    object T etM xerMaxT etsToScoreParam
        extends FSBoundedParam[ nt](
          na  = "scored_t ets_qual y_factor_t et_m xer_max_t ets_to_score",
          default = 500,
          m n = 0,
          max = 10000
        )

    object L stsMaxT etsToScoreParam
        extends FSBoundedParam[ nt](
          na  = "scored_t ets_qual y_factor_l sts_max_t ets_to_score",
          default = 500,
          m n = 0,
          max = 100
        )

    object PopularV deosMaxT etsToScoreParam
        extends FSBoundedParam[ nt](
          na  = "scored_t ets_qual y_factor_popular_v deos_max_t ets_to_score",
          default = 40,
          m n = 0,
          max = 10000
        )

    object Backf llMaxT etsToScoreParam
        extends FSBoundedParam[ nt](
          na  = "scored_t ets_qual y_factor_backf ll_max_t ets_to_score",
          default = 500,
          m n = 0,
          max = 10000
        )
  }

  object ServerMaxResultsParam
      extends FSBoundedParam[ nt](
        na  = "scored_t ets_server_max_results",
        default = 120,
        m n = 1,
        max = 500
      )

  object Max nNetworkResultsParam
      extends FSBoundedParam[ nt](
        na  = "scored_t ets_max_ n_network_results",
        default = 60,
        m n = 1,
        max = 500
      )

  object MaxOutOfNetworkResultsParam
      extends FSBoundedParam[ nt](
        na  = "scored_t ets_max_out_of_network_results",
        default = 60,
        m n = 1,
        max = 500
      )

  object Cac dScoredT ets {
    object TTLParam
        extends FSBoundedParam[Durat on](
          na  = "scored_t ets_cac d_scored_t ets_ttl_m nutes",
          default = 3.m nutes,
          m n = 0.m nute,
          max = 60.m nutes
        )
        w h HasDurat onConvers on {
      overr de val durat onConvers on: Durat onConvers on = Durat onConvers on.FromM nutes
    }

    object M nCac dT etsParam
        extends FSBoundedParam[ nt](
          na  = "scored_t ets_cac d_scored_t ets_m n_cac d_t ets",
          default = 30,
          m n = 0,
          max = 1000
        )
  }

  object Scor ng {
    object Ho ModelParam
        extends FSParam[Str ng](na  = "scored_t ets_ho _model", default = "Ho ")

    object Model  ghts {

      object FavParam
          extends FSBoundedParam[Double](
            na  = "scored_t ets_model_  ght_fav",
            default = 1.0,
            m n = 0.0,
            max = 100.0
          )

      object Ret etParam
          extends FSBoundedParam[Double](
            na  = "scored_t ets_model_  ght_ret et",
            default = 1.0,
            m n = 0.0,
            max = 100.0
          )

      object ReplyParam
          extends FSBoundedParam[Double](
            na  = "scored_t ets_model_  ght_reply",
            default = 1.0,
            m n = 0.0,
            max = 100.0
          )

      object GoodProf leCl ckParam
          extends FSBoundedParam[Double](
            na  = "scored_t ets_model_  ght_good_prof le_cl ck",
            default = 1.0,
            m n = 0.0,
            max = 1000000.0
          )

      object V deoPlayback50Param
          extends FSBoundedParam[Double](
            na  = "scored_t ets_model_  ght_v deo_playback50",
            default = 1.0,
            m n = 0.0,
            max = 100.0
          )

      object ReplyEngagedByAuthorParam
          extends FSBoundedParam[Double](
            na  = "scored_t ets_model_  ght_reply_engaged_by_author",
            default = 1.0,
            m n = 0.0,
            max = 200.0
          )

      object GoodCl ckParam
          extends FSBoundedParam[Double](
            na  = "scored_t ets_model_  ght_good_cl ck",
            default = 1.0,
            m n = 0.0,
            max = 1000000.0
          )

      object GoodCl ckV2Param
          extends FSBoundedParam[Double](
            na  = "scored_t ets_model_  ght_good_cl ck_v2",
            default = 1.0,
            m n = 0.0,
            max = 1000000.0
          )

      object T etDeta lD llParam
          extends FSBoundedParam[Double](
            na  = "scored_t ets_model_  ght_t et_deta l_d ll",
            default = 0.0,
            m n = 0.0,
            max = 100.0
          )

      object Prof leD lledParam
          extends FSBoundedParam[Double](
            na  = "scored_t ets_model_  ght_prof le_d lled",
            default = 0.0,
            m n = 0.0,
            max = 100.0
          )

      object BookmarkParam
          extends FSBoundedParam[Double](
            na  = "scored_t ets_model_  ght_bookmark",
            default = 0.0,
            m n = 0.0,
            max = 100.0
          )

      object ShareParam
          extends FSBoundedParam[Double](
            na  = "scored_t ets_model_  ght_share",
            default = 0.0,
            m n = 0.0,
            max = 100.0
          )

      object Share nuCl ckParam
          extends FSBoundedParam[Double](
            na  = "scored_t ets_model_  ght_share_ nu_cl ck",
            default = 0.0,
            m n = 0.0,
            max = 100.0
          )

      object Negat veFeedbackV2Param
          extends FSBoundedParam[Double](
            na  = "scored_t ets_model_  ght_negat ve_feedback_v2",
            default = 1.0,
            m n = -1000.0,
            max = 0.0
          )

      object ReportParam
          extends FSBoundedParam[Double](
            na  = "scored_t ets_model_  ght_report",
            default = 1.0,
            m n = -20000.0,
            max = 0.0
          )

      object  akNegat veFeedbackParam
          extends FSBoundedParam[Double](
            na  = "scored_t ets_model_  ght_ ak_negat ve_feedback",
            default = 0.0,
            m n = -1000.0,
            max = 0.0
          )

      object StrongNegat veFeedbackParam
          extends FSBoundedParam[Double](
            na  = "scored_t ets_model_  ght_strong_negat ve_feedback",
            default = 0.0,
            m n = -1000.0,
            max = 0.0
          )
    }
  }

  object EnableS mClustersS m lar yFeatureHydrat onDec derParam
      extends BooleanDec derParam(dec der = Dec derKey.EnableS mClustersS m lar yFeatureHydrat on)

  object Compet orSetParam
      extends FSParam[Set[Long]](na  = "scored_t ets_compet or_l st", default = Set.empty)

  object Compet orURLSeqParam
      extends FSParam[Seq[Str ng]](na  = "scored_t ets_compet or_url_l st", default = Seq.empty)

  object BlueVer f edAuthor nNetworkMult pl erParam
      extends FSBoundedParam[Double](
        na  = "scored_t ets_blue_ver f ed_author_ n_network_mult pl er",
        default = 4.0,
        m n = 0.0,
        max = 100.0
      )

  object BlueVer f edAuthorOutOfNetworkMult pl erParam
      extends FSBoundedParam[Double](
        na  = "scored_t ets_blue_ver f ed_author_out_of_network_mult pl er",
        default = 2.0,
        m n = 0.0,
        max = 100.0
      )

  object Creator nNetworkMult pl erParam
      extends FSBoundedParam[Double](
        na  = "scored_t ets_creator_ n_network_mult pl er",
        default = 1.1,
        m n = 0.0,
        max = 100.0
      )

  object CreatorOutOfNetworkMult pl erParam
      extends FSBoundedParam[Double](
        na  = "scored_t ets_creator_out_of_network_mult pl er",
        default = 1.3,
        m n = 0.0,
        max = 100.0
      )

  object OutOfNetworkScaleFactorParam
      extends FSBoundedParam[Double](
        na  = "scored_t ets_out_of_network_scale_factor",
        default = 1.0,
        m n = 0.0,
        max = 100.0
      )

  object EnableScr beScoredCand datesParam
      extends FSParam[Boolean](na  = "scored_t ets_enable_scr b ng", default = false)

  object Earlyb rdTensorflowModel {

    object  nNetworkParam
        extends FSParam[Str ng](
          na  = "scored_t ets_ n_network_earlyb rd_tensorflow_model",
          default = "t  l nes_recap_repl ca")

    object FrsParam
        extends FSParam[Str ng](
          na  = "scored_t ets_frs_earlyb rd_tensorflow_model",
          default = "t  l nes_rect et_repl ca")

    object UtegParam
        extends FSParam[Str ng](
          na  = "scored_t ets_uteg_earlyb rd_tensorflow_model",
          default = "t  l nes_rect et_repl ca")
  }

}
