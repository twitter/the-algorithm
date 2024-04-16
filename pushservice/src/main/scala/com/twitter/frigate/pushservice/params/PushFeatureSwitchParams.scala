package com.tw ter.fr gate.pushserv ce.params

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.fr gate.pushserv ce.params. nl neAct onsEnum._
 mport com.tw ter.fr gate.pushserv ce.params.H ghQual yCand dateGroupEnum._
 mport com.tw ter.t  l nes.conf gap .Durat onConvers on
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSEnumParam
 mport com.tw ter.t  l nes.conf gap .FSEnumSeqParam
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .HasDurat onConvers on
 mport com.tw ter.ut l.Durat on

object PushFeatureSw chParams {

  /**
   * L st of CRTs to uprank. Last CRT  n sequence ends up on top of l st
   */
  object L stOfCrtsToUpRank
      extends FSParam[Seq[Str ng]]("rerank_cand dates_crt_to_top", default = Seq.empty[Str ng])

  object L stOfCrtsForOpenApp
      extends FSParam[Seq[Str ng]](
        "open_app_allo d_crts",
        default = Seq(
          "f1f rstdegreet et",
          "f1f rstdegreephoto",
          "f1f rstdegreev deo",
          "geopopt et",
          "frst et",
          "trendt et",
          " rm user",
          "tr angularloopuser"
        ))

  /**
   * L st of CRTs to downrank. Last CRT  n sequence ends up on bottom of l st
   */
  object L stOfCrtsToDownRank
      extends FSParam[Seq[Str ng]](
        na  = "rerank_cand dates_crt_to_downrank",
        default = Seq.empty[Str ng])

  /**
   * Param to enable VF f lter ng  n T etyp e (vs us ng V s b l yL brary)
   */
  object EnableVF nT etyp e
      extends FSParam[Boolean](
        na  = "v s b l y_f lter ng_enable_vf_ n_t etyp e",
        default = true
      )

  /**
   * Number of max earlyb rd cand dates
   */
  object NumberOfMaxEarlyb rd nNetworkCand datesParam
      extends FSBoundedParam(
        na  = "fr gate_push_max_earlyb rd_ n_network_cand dates",
        default = 100,
        m n = 0,
        max = 800
      )

  /**
   * Number of max UserT etEnt yGraph cand dates to query
   */
  object NumberOfMaxUTEGCand datesQuer edParam
      extends FSBoundedParam(
        na  = "fr gate_push_max_uteg_cand dates_quer ed",
        default = 30,
        m n = 0,
        max = 300
      )

  /**
   * Param to control t  max t et age for users
   */
  object MaxT etAgeParam
      extends FSBoundedParam[Durat on](
        na  = "t et_age_max_h s",
        default = 24.h s,
        m n = 1.h s,
        max = 72.h s
      )
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   * Param to control t  max t et age for model ng-based cand dates
   */
  object Model ngBasedCand dateMaxT etAgeParam
      extends FSBoundedParam[Durat on](
        na  = "t et_age_cand date_generat on_model_max_h s",
        default = 24.h s,
        m n = 1.h s,
        max = 72.h s
      )
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   * Param to control t  max t et age for s mcluster-based cand dates
   */
  object GeoPopT etMaxAge nH s
      extends FSBoundedParam[Durat on](
        na  = "t et_age_geo_pop_max_h s",
        default = 24.h s,
        m n = 1.h s,
        max = 120.h s
      )
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   * Param to control t  max t et age for s mcluster-based cand dates
   */
  object S mclusterBasedCand dateMaxT etAgeParam
      extends FSBoundedParam[Durat on](
        na  = "t et_age_s mcluster_max_h s",
        default = 24.h s,
        m n = 24.h s,
        max = 48.h s
      )
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   * Param to control t  max t et age for Detop c-based cand dates
   */
  object Detop cBasedCand dateMaxT etAgeParam
      extends FSBoundedParam[Durat on](
        na  = "t et_age_detop c_max_h s",
        default = 24.h s,
        m n = 24.h s,
        max = 48.h s
      )
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   * Param to control t  max t et age for F1 cand dates
   */
  object F1Cand dateMaxT etAgeParam
      extends FSBoundedParam[Durat on](
        na  = "t et_age_f1_max_h s",
        default = 24.h s,
        m n = 1.h s,
        max = 96.h s
      )
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   * Param to control t  max t et age for Explore V deo T et
   */
  object ExploreV deoT etAgeParam
      extends FSBoundedParam[Durat on](
        na  = "explore_v deo_t ets_age_max_h s",
        default = 48.h s,
        m n = 1.h s,
        max = 336.h s // Two  eks
      )
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   * Param to no send for new user playbook push  f user log n for past h s
   */
  object NewUserPlaybookAllo dLastLog nH s
      extends FSBoundedParam[Durat on](
        na  = "new_user_playbook_allo d_last_log n_h s",
        default = 0.h s,
        m n = 0.h s,
        max = 72.h s
      )
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   * T  batch s ze of RefreshForPushHandler's Take step
   */
  object NumberOfMaxCand datesToBatch nRFPHTakeStep
      extends FSBoundedParam(
        na  = "fr gate_push_rfph_batch_take_max_s ze",
        default = 1,
        m n = 1,
        max = 10
      )

  /**
   * T  max mum number of cand dates to batch for  mportance Sampl ng
   */
  object NumberOfMaxCand datesToBatchFor mportanceSampl ng
      extends FSBoundedParam(
        na  = "fr gate_push_rfph_max_cand dates_to_batch_for_ mportance_sampl ng",
        default = 65,
        m n = 1,
        max = 500
      )

  /**
   * Max mum number of regular MR push  n 24.h s/dayt  /n ghtt  
   */
  object MaxMrPushSends24H sParam
      extends FSBoundedParam(
        na  = "pushcap_max_sends_24h s",
        default = 5,
        m n = 0,
        max = 12
      )

  /**
   * Max mum number of regular MR ntab only channel  n 24.h s/dayt  /n ghtt  
   */
  object MaxMrNtabOnlySends24H sParamV3
      extends FSBoundedParam(
        na  = "pushcap_max_sends_24h s_ntabonly_v3",
        default = 5,
        m n = 0,
        max = 12
      )

  /**
   * Max mum number of regular MR ntab only  n 24.h s/dayt  /n ghtt  
   */
  object MaxMrPushSends24H sNtabOnlyUsersParam
      extends FSBoundedParam(
        na  = "pushcap_max_sends_24h s_ntab_only",
        default = 5,
        m n = 0,
        max = 10
      )

  /**
   * Custom zed PushCap offset (e.g., to t  pred cted value)
   */
  object Custom zedPushCapOffset
      extends FSBoundedParam[ nt](
        na  = "pushcap_custom zed_offset",
        default = 0,
        m n = -2,
        max = 4
      )

  /**
   * Param to enable restr ct ng m n mum pushcap ass gned w h ML models
   * */
  object EnableRestr ctedM nModelPushcap
      extends FSParam[Boolean](
        na  = "pushcap_restr cted_model_m n_enable",
        default = false
      )

  /**
   * Param to spec fy t  m n mum pushcap allo d to be ass gned w h ML models
   * */
  object Restr ctedM nModelPushcap
      extends FSBoundedParam[ nt](
        na  = "pushcap_restr cted_model_m n_value",
        default = 1,
        m n = 0,
        max = 9
      )

  object EnablePushcapRefactor
      extends FSParam[Boolean](
        na  = "pushcap_enable_refactor",
        default = false
      )

  /**
   * Enables t  restr ct step  n pushserv ce for a g ven user
   *
   * Sett ng t  to false may cause a large number of cand dates to be passed on to f lter ng/take
   * step  n RefreshForPushHandler,  ncreas ng t  serv ce latency s gn f cantly
   */
  object EnableRestr ctStep extends FSParam[Boolean]("fr gate_push_rfph_restr ct_step_enable", true)

  /**
   * T  number of cand dates that are able to pass through t  restr ct step.
   */
  object Restr ctStepS ze
      extends FSBoundedParam(
        na  = "fr gate_push_rfph_restr ct_step_s ze",
        default = 65,
        m n = 65,
        max = 200
      )

  /**
   * Number of max crM xer cand dates to send.
   */
  object NumberOfMaxCrM xerCand datesParam
      extends FSBoundedParam(
        na  = "cr_m xer_m grat on_max_num_of_cand dates_to_return",
        default = 400,
        m n = 0,
        max = 2000
      )

  /**
   * Durat on bet en two MR pus s
   */
  object M nDurat onS ncePushParam
      extends FSBoundedParam[Durat on](
        na  = "pushcap_m n_durat on_s nce_push_h s",
        default = 4.h s,
        m n = 0.h s,
        max = 72.h s
      )
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   * Each Phase durat on to gradually ramp up Mag cRecs for new users
   */
  object GraduallyRampUpPhaseDurat onDays
      extends FSBoundedParam[Durat on](
        na  = "pushcap_gradually_ramp_up_phase_durat on_days",
        default = 3.days,
        m n = 2.days,
        max = 7.days
      )
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  /**
   * Param to spec fy  nterval for target pushcap fat gue
   */
  object TargetPushCapFat gue ntervalH s
      extends FSBoundedParam[Durat on](
        na  = "pushcap_fat gue_ nterval_h s",
        default = 24.h s,
        m n = 1.h ,
        max = 240.h s
      )
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   * Param to spec fy  nterval for target ntabOnly fat gue
   */
  object TargetNtabOnlyCapFat gue ntervalH s
      extends FSBoundedParam[Durat on](
        na  = "pushcap_ntabonly_fat gue_ nterval_h s",
        default = 24.h s,
        m n = 1.h ,
        max = 240.h s
      )
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   * Param to use completely expl c  push cap  nstead of LTV/model ng-based
   */
  object EnableExpl c PushCap
      extends FSParam[Boolean](
        na  = "pushcap_expl c _enable",
        default = false
      )

  /**
   * Param to control expl c  push cap (non-LTV)
   */
  object Expl c PushCap
      extends FSBoundedParam[ nt](
        na  = "pushcap_expl c _value",
        default = 1,
        m n = 0,
        max = 20
      )

  /**
   * Para ters for percent le thresholds of OpenOrNtabCl ck model  n MR f lter ng model refresh ng DDG
   */
  object Percent leThresholdCohort1
      extends FSBoundedParam[Double](
        na  = "fr gate_push_model ng_percent le_threshold_cohort1",
        default = 0.65,
        m n = 0.0,
        max = 1.0
      )

  object Percent leThresholdCohort2
      extends FSBoundedParam[Double](
        na  = "fr gate_push_model ng_percent le_threshold_cohort2",
        default = 0.03,
        m n = 0.0,
        max = 1.0
      )
  object Percent leThresholdCohort3
      extends FSBoundedParam[Double](
        na  = "fr gate_push_model ng_percent le_threshold_cohort3",
        default = 0.03,
        m n = 0.0,
        max = 1.0
      )
  object Percent leThresholdCohort4
      extends FSBoundedParam[Double](
        na  = "fr gate_push_model ng_percent le_threshold_cohort4",
        default = 0.06,
        m n = 0.0,
        max = 1.0
      )
  object Percent leThresholdCohort5
      extends FSBoundedParam[Double](
        na  = "fr gate_push_model ng_percent le_threshold_cohort5",
        default = 0.06,
        m n = 0.0,
        max = 1.0
      )
  object Percent leThresholdCohort6
      extends FSBoundedParam[Double](
        na  = "fr gate_push_model ng_percent le_threshold_cohort6",
        default = 0.8,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Para ters for percent le threshold l st of OpenOrNtabCL ck model  n MR percent le gr d search exper  nts
   */
  object MrPercent leGr dSearchThresholdsCohort1
      extends FSParam[Seq[Double]](
        na  = "fr gate_push_model ng_percent le_gr d_search_thresholds_cohort1",
        default = Seq(0.8, 0.75, 0.65, 0.55, 0.45, 0.35, 0.25)
      )
  object MrPercent leGr dSearchThresholdsCohort2
      extends FSParam[Seq[Double]](
        na  = "fr gate_push_model ng_percent le_gr d_search_thresholds_cohort2",
        default = Seq(0.15, 0.12, 0.1, 0.08, 0.06, 0.045, 0.03)
      )
  object MrPercent leGr dSearchThresholdsCohort3
      extends FSParam[Seq[Double]](
        na  = "fr gate_push_model ng_percent le_gr d_search_thresholds_cohort3",
        default = Seq(0.15, 0.12, 0.1, 0.08, 0.06, 0.045, 0.03)
      )
  object MrPercent leGr dSearchThresholdsCohort4
      extends FSParam[Seq[Double]](
        na  = "fr gate_push_model ng_percent le_gr d_search_thresholds_cohort4",
        default = Seq(0.15, 0.12, 0.1, 0.08, 0.06, 0.045, 0.03)
      )
  object MrPercent leGr dSearchThresholdsCohort5
      extends FSParam[Seq[Double]](
        na  = "fr gate_push_model ng_percent le_gr d_search_thresholds_cohort5",
        default = Seq(0.3, 0.2, 0.15, 0.1, 0.08, 0.06, 0.05)
      )
  object MrPercent leGr dSearchThresholdsCohort6
      extends FSParam[Seq[Double]](
        na  = "fr gate_push_model ng_percent le_gr d_search_thresholds_cohort6",
        default = Seq(0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2)
      )

  /**
   * Para ters for threshold l st of OpenOrNtabCl ck model  n MF gr d search exper  nts
   */
  object MfGr dSearchThresholdsCohort1
      extends FSParam[Seq[Double]](
        na  = "fr gate_push_model ng_mf_gr d_search_thresholds_cohort1",
        default = Seq(0.030, 0.040, 0.050, 0.062, 0.070, 0.080, 0.090) // default: 0.062
      )
  object MfGr dSearchThresholdsCohort2
      extends FSParam[Seq[Double]](
        na  = "fr gate_push_model ng_mf_gr d_search_thresholds_cohort2",
        default = Seq(0.005, 0.010, 0.015, 0.020, 0.030, 0.040, 0.050) // default: 0.020
      )
  object MfGr dSearchThresholdsCohort3
      extends FSParam[Seq[Double]](
        na  = "fr gate_push_model ng_mf_gr d_search_thresholds_cohort3",
        default = Seq(0.010, 0.015, 0.020, 0.025, 0.035, 0.045, 0.055) // default: 0.025
      )
  object MfGr dSearchThresholdsCohort4
      extends FSParam[Seq[Double]](
        na  = "fr gate_push_model ng_mf_gr d_search_thresholds_cohort4",
        default = Seq(0.015, 0.020, 0.025, 0.030, 0.040, 0.050, 0.060) // default: 0.030
      )
  object MfGr dSearchThresholdsCohort5
      extends FSParam[Seq[Double]](
        na  = "fr gate_push_model ng_mf_gr d_search_thresholds_cohort5",
        default = Seq(0.035, 0.040, 0.045, 0.050, 0.060, 0.070, 0.080) // default: 0.050
      )
  object MfGr dSearchThresholdsCohort6
      extends FSParam[Seq[Double]](
        na  = "fr gate_push_model ng_mf_gr d_search_thresholds_cohort6",
        default = Seq(0.040, 0.045, 0.050, 0.055, 0.065, 0.075, 0.085) // default: 0.055
      )

  /**
   * Param to spec fy wh ch global optout models to use to f rst pred ct t  global scores for users
   */
  object GlobalOptoutModelParam
      extends FSParam[Seq[OptoutModel.ModelNa Type]](
        na  = "optout_model_global_model_ ds",
        default = Seq.empty[OptoutModel.ModelNa Type]
      )

  /**
   * Param to spec fy wh ch optout model to use accord ng to t  exper  nt bucket
   */
  object BucketOptoutModelParam
      extends FSParam[OptoutModel.ModelNa Type](
        na  = "optout_model_bucket_model_ d",
        default = OptoutModel.D0_has_realt  _features
      )

  /*
   * Param to enable cand date generat on model
   * */
  object EnableCand dateGenerat onModelParam
      extends FSParam[Boolean](
        na  = "cand date_generat on_model_enable",
        default = false
      )

  object EnableOverr deForSportsCand dates
      extends FSParam[Boolean](na  = "mag cfanout_sports_event_enable_overr de", default = true)

  object EnableEvent dBasedOverr deForSportsCand dates
      extends FSParam[Boolean](
        na  = "mag cfanout_sports_event_enable_event_ d_based_overr de",
        default = true)

  /**
   * Param to spec fy t  threshold to determ ne  f a user’s optout score  s h gh enough to enter t  exper  nt.
   */
  object GlobalOptoutThresholdParam
      extends FSParam[Seq[Double]](
        na  = "optout_model_global_thresholds",
        default = Seq(1.0, 1.0)
      )

  /**
   * Param to spec fy t  threshold to determ ne  f a user’s optout score  s h gh enough to be ass gned
   * w h a reduced pushcap based on t  bucket  mbersh p.
   */
  object BucketOptoutThresholdParam
      extends FSBoundedParam[Double](
        na  = "optout_model_bucket_threshold",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Param to spec fy t  reduced pushcap value  f t  optout probab l y pred cted by t  bucket
   * optout model  s h g r than t  spec f ed bucket optout threshold.
   */
  object OptoutExptPushCapParam
      extends FSBoundedParam[ nt](
        na  = "optout_model_expt_push_cap",
        default = 10,
        m n = 0,
        max = 10
      )

  /**
   * Param to spec fy t  thresholds to determ ne wh ch push cap slot t  user should be ass gned to
   * accord ng to t  optout score. For example,t  slot thresholds are [0.1, 0.2, ..., 1.0], t  user
   *  s ass gned to t  second slot  f t  optout score  s  n (0.1, 0.2].
   */
  object BucketOptoutSlotThresholdParam
      extends FSParam[Seq[Double]](
        na  = "optout_model_bucket_slot_thresholds",
        default = Seq.empty[Double]
      )

  /**
   * Param to spec fy t  adjusted push cap of each slot. For example,  f t  slot push caps are [1, 2, ..., 10]
   * and t  user  s ass gned to t  2nd slot accord ng to t  optout score, t  push cap of t  user
   * w ll be adjusted to 2.
   */
  object BucketOptoutSlotPushcapParam
      extends FSParam[Seq[ nt]](
        na  = "optout_model_bucket_slot_pushcaps",
        default = Seq.empty[ nt]
      )

  /**
   * Param to spec fy  f t  optout score based push cap adjust nt  s enabled
   */
  object EnableOptoutAdjustedPushcap
      extends FSParam[Boolean](
        "optout_model_enable_optout_adjusted_pushcap",
        false
      )

  /**
   * Param to spec fy wh ch   ghted open or ntab cl ck model to use
   */
  object   ghtedOpenOrNtabCl ckRank ngModelParam
      extends FSParam[  ghtedOpenOrNtabCl ckModel.ModelNa Type](
        na  = "fr gate_push_model ng_oonc_rank ng_model_ d",
        default =   ghtedOpenOrNtabCl ckModel.Per od cally_Refres d_Prod_Model
      )

  /**
   * Param to d sable  avy ranker
   */
  object D sable avyRank ngModelFSParam
      extends FSParam[Boolean](
        na  = "fr gate_push_model ng_d sable_ avy_rank ng",
        default = false
      )

  /**
   * Param to spec fy wh ch   ghted open or ntab cl ck model to use for Andro d modell ng exper  nt
   */
  object   ghtedOpenOrNtabCl ckRank ngModelForAndro dParam
      extends FSParam[  ghtedOpenOrNtabCl ckModel.ModelNa Type](
        na  = "fr gate_push_model ng_oonc_rank ng_model_for_andro d_ d",
        default =   ghtedOpenOrNtabCl ckModel.Per od cally_Refres d_Prod_Model
      )

  /**
   * Param to spec fy wh ch   ghted open or ntab cl ck model to use for F LTER NG
   */
  object   ghtedOpenOrNtabCl ckF lter ngModelParam
      extends FSParam[  ghtedOpenOrNtabCl ckModel.ModelNa Type](
        na  = "fr gate_push_model ng_oonc_f lter ng_model_ d",
        default =   ghtedOpenOrNtabCl ckModel.Per od cally_Refres d_Prod_Model
      )

  /**
   * Param to spec fy wh ch qual y pred cate to use for ML f lter ng
   */
  object Qual yPred cate dParam
      extends FSEnumParam[Qual yPred cateEnum.type](
        na  = "fr gate_push_model ng_qual y_pred cate_ d",
        default = Qual yPred cateEnum.  ghtedOpenOrNtabCl ck,
        enum = Qual yPred cateEnum
      )

  /**
   * Param to control threshold for any qual y pred cates us ng expl c  thresholds
   */
  object Qual yPred cateExpl c ThresholdParam
      extends FSBoundedParam[Double](
        na  = "fr gate_push_model ng_qual y_pred cate_expl c _threshold",
        default = 0.1,
        m n = 0,
        max = 1)

  /**
   * Mag cFanout relaxed event D fat gue  nterval (w n   want to enable mult ple updates for t  sa  event)
   */
  object Mag cFanoutRelaxedEvent dFat gue nterval nH s
      extends FSBoundedParam[ nt](
        na  = "fr gate_push_mag cfanout_relaxed_event_ d_fat gue_ nterval_ n_h s",
        default = 24,
        m n = 0,
        max = 720
      )

  /**
   * Mag cFanout DenyL sted Countr es
   */
  object Mag cFanoutDenyL stedCountr es
      extends FSParam[Seq[Str ng]](
        "fr gate_push_mag cfanout_denyl sted_countr es",
        Seq.empty[Str ng])

  object Mag cFanoutSportsEventDenyL stedCountr es
      extends FSParam[Seq[Str ng]](
        "mag cfanout_sports_event_denyl sted_countr es",
        Seq.empty[Str ng])

  /**
   * Mag cFanout max mum erg rank for a g ven push event for non  avy users
   */
  object Mag cFanoutRankErgThresholdNon avy
      extends FSBoundedParam[ nt](
        na  = "fr gate_push_mag cfanout_erg_rank_threshold_non_ avy",
        default = 25,
        m n = 1,
        max = 50
      )

  /**
   * Mag cFanout max mum erg rank for a g ven push event for  avy users
   */
  object Mag cFanoutRankErgThreshold avy
      extends FSBoundedParam[ nt](
        na  = "fr gate_push_mag cfanout_erg_rank_threshold_ avy",
        default = 20,
        m n = 1,
        max = 50
      )

  object EnablePushM xerReplac ngAllS ces
      extends FSParam[Boolean](
        na  = "push_m xer_enable_replac ng_all_s ces",
        default = false
      )

  object EnablePushM xerReplac ngAllS cesW hControl
      extends FSParam[Boolean](
        na  = "push_m xer_enable_replac ng_all_s ces_w h_control",
        default = false
      )

  object EnablePushM xerReplac ngAllS cesW hExtra
      extends FSParam[Boolean](
        na  = "push_m xer_enable_replac ng_all_s ces_w h_extra",
        default = false
      )

  object EnablePushM xerS ce
      extends FSParam[Boolean](
        na  = "push_m xer_enable_s ce",
        default = false
      )

  object PushM xerMaxResults
      extends FSBoundedParam[ nt](
        na  = "push_m xer_max_results",
        default = 10,
        m n = 1,
        max = 5000
      )

  /**
   * Enable t ets from trends that have been annotated by curators
   */
  object EnableCuratedTrendT ets
      extends FSParam[Boolean](na  = "trend_t et_curated_trends_enable", default = false)

  /**
   * Enable t ets from trends that haven't been annotated by curators
   */
  object EnableNonCuratedTrendT ets
      extends FSParam[Boolean](na  = "trend_t et_non_curated_trends_enable", default = false)

  /**
   * Max mum trend t et not f cat ons  n f xed durat on
   */
  object MaxTrendT etNot f cat ons nDurat on
      extends FSBoundedParam[ nt](
        na  = "trend_t et_max_not f cat ons_ n_durat on",
        m n = 0,
        default = 0,
        max = 20)

  /**
   * Durat on  n days over wh ch trend t et not f cat ons fat gue  s appl ed
   */
  object TrendT etNot f cat onsFat gueDurat on
      extends FSBoundedParam[Durat on](
        na  = "trend_t et_not f cat ons_fat gue_ n_days",
        default = 1.day,
        m n = Durat on.Bottom,
        max = Durat on.Top
      )
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  /**
   * Max mum number of trends cand dates to query from event-recos endpo nt
   */
  object MaxRecom ndedTrendsToQuery
      extends FSBoundedParam[ nt](
        na  = "trend_t et_max_trends_to_query",
        m n = 0,
        default = 0,
        max = 100)

  /**
   * F x m ss ng event-assoc ated  nterests  n Mag cFanoutNoOptout nterestsPred cate
   */
  object Mag cFanoutF xNoOptout nterestsBugParam
      extends FSParam[Boolean]("fr gate_push_mag cfanout_f x_no_optout_ nterests", default = true)

  object EnableS mclusterOffl neAggFeatureForExpt
      extends FSParam[Boolean]("fr gate_enable_s mcluster_offl ne_agg_feature", false)

  /**
   * Param to enable removal of UTT doma n for
   */
  object ApplyMag cFanoutBroadEnt y nterestRankThresholdPred cate
      extends FSParam[Boolean](
        "fr gate_push_mag cfanout_broad_ent y_ nterest_rank_threshold_pred cate",
        false
      )

  object HydrateEventReasonsFeatures
      extends FSParam[Boolean](
        na  = "fr gate_push_mag cfanout_hydrate_event_reasons_features",
        false
      )

  /**
   * Param to enable onl ne MR  tory features
   */
  object EnableHydrat ngOnl neMR toryFeatures
      extends FSParam[Boolean](
        na  = "feature_hydrat on_onl ne_mr_ tory",
        default = false
      )

  /**
   * Param to enable bold t le on favor e and ret et push copy for Andro d  n DDG 10220
   */
  object MRBoldT leFavor eAndRet etParam
      extends FSEnumParam[MRBoldT leFavor eAndRet etExper  ntEnum.type](
        na  = "fr gate_push_bold_t le_favor e_and_ret et_ d",
        default = MRBoldT leFavor eAndRet etExper  ntEnum.ShortT le,
        enum = MRBoldT leFavor eAndRet etExper  ntEnum
      )

  /**
   * Param to enable h gh pr or y push
   */
  object EnableH ghPr or yPush
      extends FSParam[Boolean]("fr gate_push_mag cfanout_enable_h gh_pr or y_push", false)

  /**
   * Param to red rect sports crt event to a custom url
   */
  object EnableSearchURLRed rectForSportsFanout
      extends FSParam[Boolean]("mag cfanout_sports_event_enable_search_url_red rect", false)

  /**
   * Param to enable score fanout not f cat on for sports
   */
  object EnableScoreFanoutNot f cat on
      extends FSParam[Boolean]("mag cfanout_sports_event_enable_score_fanout", false)

  /**
   * Param to add custom search url for sports crt event
   */
  object SearchURLRed rectForSportsFanout
      extends FSParam[Str ng](
        na  = "mag cfanout_sports_event_search_url_red rect",
        default = "https://tw ter.com/explore/tabs/ pl",
      )

  /**
   * Param to enable h gh pr or y sports push
   */
  object EnableH ghPr or ySportsPush
      extends FSParam[Boolean]("mag cfanout_sports_event_enable_h gh_pr or y_push", false)

  /**
   * Param to control rank threshold for mag cfanout user follow
   */
  object Mag cFanoutRealgraphRankThreshold
      extends FSBoundedParam[ nt](
        na  = "mag cfanout_realgraph_threshold",
        default = 500,
        max = 500,
        m n = 100
      )

  /**
   * Top c score threshold for top c proof t et cand dates top c annotat ons
   * */
  object Top cProofT etCand datesTop cScoreThreshold
      extends FSBoundedParam[Double](
        na  = "top cs_as_soc al_proof_top c_score_threshold",
        default = 0.0,
        m n = 0.0,
        max = 100.0
      )

  /**
   * Enable Top c Proof T et Recs
   */
  object EnableTop cProofT etRecs
      extends FSParam[Boolean](na  = "top cs_as_soc al_proof_enable", default = true)

  /**
   * Enable  alth f lters for top c t et not f cat ons
   */
  object Enable althF ltersForTop cProofT et
      extends FSParam[Boolean](
        na  = "top cs_as_soc al_proof_enable_ alth_f lters",
        default = false)

  /**
   * D sable  alth f lters for CrM xer cand dates
   */
  object D sable althF ltersForCrM xerCand dates
      extends FSParam[Boolean](
        na  = " alth_and_qual y_f lter_d sable_for_crm xer_cand dates",
        default = false)

  object EnableMag cFanoutNewsFor NtabCopy
      extends FSParam[Boolean](na  = "send_handler_enable_nfy_ntab_copy", default = false)

  /**
   * Param to enable sem -personal zed h gh qual y cand dates  n pushserv ce
   * */
  object H ghQual yCand datesEnableCand dateS ce
      extends FSParam[Boolean](
        na  = "h gh_qual y_cand dates_enable_cand date_s ce",
        default = false
      )

  /**
   * Param to dec de sem -personal zed h gh qual y cand dates
   * */
  object H ghQual yCand datesEnableGroups
      extends FSEnumSeqParam[H ghQual yCand dateGroupEnum.type](
        na  = "h gh_qual y_cand dates_enable_groups_ ds",
        default = Seq(AgeBucket, Language),
        enum = H ghQual yCand dateGroupEnum
      )

  /**
   * Param to dec de sem -personal zed h gh qual y cand dates
   * */
  object H ghQual yCand datesNumberOfCand dates
      extends FSBoundedParam[ nt](
        na  = "h gh_qual y_cand dates_number_of_cand dates",
        default = 0,
        m n = 0,
        max =  nt.MaxValue
      )

  /**
   * Param to enable small doma n fall ng back to b gger doma ns for h gh qual y cand dates  n pushserv ce
   * */
  object H ghQual yCand datesEnableFallback
      extends FSParam[Boolean](
        na  = "h gh_qual y_cand dates_enable_fallback",
        default = false
      )

  /**
   * Param to dec de w t r to fallback to b gger doma n for h gh qual y cand dates
   * */
  object H ghQual yCand datesM nNumOfCand datesToFallback
      extends FSBoundedParam[ nt](
        na  = "h gh_qual y_cand dates_m n_num_of_cand dates_to_fallback",
        default = 50,
        m n = 0,
        max =  nt.MaxValue
      )

  /**
   * Param to spec f c s ce  ds for h gh qual y cand dates
   * */
  object H ghQual yCand datesFallbackS ce ds
      extends FSParam[Seq[Str ng]](
        na  = "h gh_qual y_cand dates_fallback_s ce_ ds",
        default = Seq("HQ_C_COUNT_PASS_QUAL TY_SCORES"))

  /**
   * Param to dec de groups for sem -personal zed h gh qual y cand dates
   * */
  object H ghQual yCand datesFallbackEnabledGroups
      extends FSEnumSeqParam[H ghQual yCand dateGroupEnum.type](
        na  = "h gh_qual y_cand dates_fallback_enabled_groups_ ds",
        default = Seq(Country),
        enum = H ghQual yCand dateGroupEnum
      )

  /**
   * Param to control what  avy ranker model to use for scr b ng scores
   */
  object H ghQual yCand dates avyRank ngModel
      extends FSParam[Str ng](
        na  = "h gh_qual y_cand dates_ avy_rank ng_model",
        default = "Per od cally_Refres d_Prod_Model_V11"
      )

  /**
   * Param to control what non personal zed qual y "Cnn" model to use for scr b ng scores
   */
  object H ghQual yCand datesNonPersonal zedQual yCnnModel
      extends FSParam[Str ng](
        na  = "h gh_qual y_cand dates_non_personal zed_qual y_cnn_model",
        default = "Q1_2023_Mr_Tf_Qual y_Model_cnn"
      )

  /**
   * Param to control what nsfw  alth model to use for scr b ng scores
   */
  object H ghQual yCand datesBqmlNsfwModel
      extends FSParam[Str ng](
        na  = "h gh_qual y_cand dates_bqml_nsfw_model",
        default = "Q2_2022_Mr_Bqml_ alth_Model_NsfwV0"
      )

  /**
   * Param to control what reportodel to use for scr b ng scores
   */
  object H ghQual yCand datesBqmlReportModel
      extends FSParam[Str ng](
        na  = "h gh_qual y_cand dates_bqml_report_model",
        default = "Q3_2022_15266_Mr_Bqml_Non_Personal zed_Report_Model_w h_ d a_Embedd ngs"
      )

  /**
   * Param to spec fy t  threshold to determ ne  f a t et conta ns nud y  d a
   */
  object T et d aSens  veCategoryThresholdParam
      extends FSBoundedParam[Double](
        na  = "t et_ d a_sens  ve_category_threshold",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Param to boost cand dates from subscr pt on creators
   */
  object BoostCand datesFromSubscr pt onCreators
      extends FSParam[Boolean](
        na  = "subscr pt on_enable_boost_cand dates_from_act ve_creators",
        default = false
      )

  /**
   * Param to soft rank cand dates from subscr pt on creators
   */
  object SoftRankCand datesFromSubscr pt onCreators
      extends FSParam[Boolean](
        na  = "subscr pt on_enable_soft_rank_cand dates_from_act ve_creators",
        default = false
      )

  /**
   * Param as factor to control how much   want to boost creator t ets
   */
  object SoftRankFactorForSubscr pt onCreators
      extends FSBoundedParam[Double](
        na  = "subscr pt on_soft_rank_factor_for_boost",
        default = 1.0,
        m n = 0.0,
        max = Double.MaxValue
      )

  /**
   * Param to enable new OON copy for Push Not f cat ons
   */
  object EnableNewMROONCopyForPush
      extends FSParam[Boolean](
        na  = "mr_copy_enable_new_mr_oon_copy_push",
        default = true
      )

  /**
   * Param to enable generated  nl ne act ons on OON Not f cat ons
   */
  object EnableOONGenerated nl neAct ons
      extends FSParam[Boolean](
        na  = "mr_ nl ne_enable_oon_generated_act ons",
        default = false
      )

  /**
   * Param to control dynam c  nl ne act ons for Out-of-Network cop es
   */
  object OONT etDynam c nl neAct onsL st
      extends FSEnumSeqParam[ nl neAct onsEnum.type](
        na  = "mr_ nl ne_oon_t et_dynam c_act on_ ds",
        default = Seq(Follow, Ret et, Favor e),
        enum =  nl neAct onsEnum
      )

  object H ghOONCT etFormat
      extends FSEnumParam[ b sTemplateFormatEnum.type](
        na  = "mr_copy_h gh_oonc_format_ d",
        default =  b sTemplateFormatEnum.template1,
        enum =  b sTemplateFormatEnum
      )

  object LowOONCT etFormat
      extends FSEnumParam[ b sTemplateFormatEnum.type](
        na  = "mr_copy_low_oonc_format_ d",
        default =  b sTemplateFormatEnum.template1,
        enum =  b sTemplateFormatEnum
      )

  /**
   * Param to enable dynam c  nl ne act ons based on FSParams for T et cop es (not OON)
   */
  object EnableT etDynam c nl neAct ons
      extends FSParam[Boolean](
        na  = "mr_ nl ne_enable_t et_dynam c_act ons",
        default = false
      )

  /**
   * Param to control dynam c  nl ne act ons for T et cop es (not OON)
   */
  object T etDynam c nl neAct onsL st
      extends FSEnumSeqParam[ nl neAct onsEnum.type](
        na  = "mr_ nl ne_t et_dynam c_act on_ ds",
        default = Seq(Reply, Ret et, Favor e),
        enum =  nl neAct onsEnum
      )

  object Use nl neAct onsV1
      extends FSParam[Boolean](
        na  = "mr_ nl ne_use_ nl ne_act on_v1",
        default = true
      )

  object Use nl neAct onsV2
      extends FSParam[Boolean](
        na  = "mr_ nl ne_use_ nl ne_act on_v2",
        default = false
      )

  object Enable nl neFeedbackOnPush
      extends FSParam[Boolean](
        na  = "mr_ nl ne_enable_ nl ne_feedback_on_push",
        default = false
      )

  object  nl neFeedbackSubst utePos  on
      extends FSBoundedParam[ nt](
        na  = "mr_ nl ne_feedback_subst ute_pos  on",
        m n = 0,
        max = 2,
        default = 2, // default to subst ute or append last  nl ne act on
      )

  /**
   * Param to control dynam c  nl ne act ons for  b not f cat ons
   */
  object EnableDynam c nl neAct onsForDesktop b
      extends FSParam[Boolean](
        na  = "mr_ nl ne_enable_dynam c_act ons_for_desktop_ b",
        default = false
      )

  object EnableDynam c nl neAct onsForMob le b
      extends FSParam[Boolean](
        na  = "mr_ nl ne_enable_dynam c_act ons_for_mob le_ b",
        default = false
      )

  /**
   * Param to def ne dynam c  nl ne act on types for  b not f cat ons (both desktop  b + mob le  b)
   */
  object T etDynam c nl neAct onsL stFor b
      extends FSEnumSeqParam[ nl neAct onsEnum.type](
        na  = "mr_ nl ne_t et_dynam c_act on_for_ b_ ds",
        default = Seq(Ret et, Favor e),
        enum =  nl neAct onsEnum
      )

  /**
   * Param to enable MR Overr de Not f cat ons for Andro d
   */
  object EnableOverr deNot f cat onsForAndro d
      extends FSParam[Boolean](
        na  = "mr_overr de_enable_overr de_not f cat ons_for_andro d",
        default = false
      )

  /**
   * Param to enable MR Overr de Not f cat ons for  OS
   */
  object EnableOverr deNot f cat onsFor os
      extends FSParam[Boolean](
        na  = "mr_overr de_enable_overr de_not f cat ons_for_ os",
        default = false
      )

  /**
   * Param to enable gradually ramp up not f cat on
   */
  object EnableGraduallyRampUpNot f cat on
      extends FSParam[Boolean](
        na  = "pushcap_gradually_ramp_up_enable",
        default = false
      )

  /**
   * Param to control t  m n nrerval for fat gue bet en consecut ve MFNFY pus s
   */
  object MFM n ntervalFat gue
      extends FSBoundedParam[Durat on](
        na  = "fr gate_push_mag cfanout_fat gue_m n_ nterval_consecut ve_pus s_m nutes",
        default = 240.m nutes,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromM nutes
  }

  /**
   * Param to control t   nterval for MFNFY pus s
   */
  object MFPush nterval nH s
      extends FSBoundedParam[Durat on](
        na  = "fr gate_push_mag cfanout_fat gue_push_ nterval_ n_h s",
        default = 24.h s,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   * Param to control t  max mum number of Sports MF pus s  n a per od of t  
   */
  object SportsMaxNumberOfPus s n nterval
      extends FSBoundedParam[ nt](
        na  = "mag cfanout_sports_event_fat gue_max_pus s_ n_ nterval",
        default = 2,
        m n = 0,
        max = 6)

  /**
   * Param to control t  m n nterval for fat gue bet en consecut ve sports pus s
   */
  object SportsM n ntervalFat gue
      extends FSBoundedParam[Durat on](
        na  = "mag cfanout_sports_event_fat gue_m n_ nterval_consecut ve_pus s_m nutes",
        default = 240.m nutes,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromM nutes
  }

  /**
   * Param to control t   nterval for sports pus s
   */
  object SportsPush nterval nH s
      extends FSBoundedParam[Durat on](
        na  = "mag cfanout_sports_event_fat gue_push_ nterval_ n_h s",
        default = 24.h s,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   * Param to control t  max mum number of sa  event sports MF pus s  n a per od of t  
   */
  object SportsMaxNumberOfPus s n ntervalPerEvent
      extends FSBoundedParam[ nt](
        na  = "mag cfanout_sports_event_fat gue_max_pus s_ n_per_event_ nterval",
        default = 2,
        m n = 0,
        max = 6)

  /**
   * Param to control t  m n nterval for fat gue bet en consecut ve sa  event sports pus s
   */
  object SportsM n ntervalFat guePerEvent
      extends FSBoundedParam[Durat on](
        na  = "mag cfanout_sports_event_fat gue_m n_ nterval_consecut ve_pus s_per_event_m nutes",
        default = 240.m nutes,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromM nutes
  }

  /**
   * Param to control t   nterval for sa  event sports pus s
   */
  object SportsPush nterval nH sPerEvent
      extends FSBoundedParam[Durat on](
        na  = "mag cfanout_sports_event_fat gue_push_ nterval_per_event_ n_h s",
        default = 24.h s,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   * Param to control t  max mum number of MF pus s  n a per od of t  
   */
  object MFMaxNumberOfPus s n nterval
      extends FSBoundedParam[ nt](
        na  = "fr gate_push_mag cfanout_fat gue_max_pus s_ n_ nterval",
        default = 2,
        m n = 0,
        max = 6)

  /**
   * Param to enable custom durat on for fat gu ng
   */
  object GPEnableCustomMag cFanoutCr cketFat gue
      extends FSParam[Boolean](
        na  = "global_part c pat on_cr cket_mag cfanout_enable_custom_fat gue",
        default = false
      )

  /**
   * Param to enable e2e scr b ng for target f lter ng step
   */
  object EnableMrRequestScr b ngForTargetF lter ng
      extends FSParam[Boolean](
        na  = "mr_request_scr b ng_enable_for_target_f lter ng",
        default = false
      )

  /**
   * Param to enable e2e scr b ng for cand date f lter ng step
   */
  object EnableMrRequestScr b ngForCand dateF lter ng
      extends FSParam[Boolean](
        na  = "mr_request_scr b ng_enable_for_cand date_f lter ng",
        default = false
      )

  /**
   * Param to enable e2e scr b ng w h feature hydrat ng
   */
  object EnableMrRequestScr b ngW hFeatureHydrat ng
      extends FSParam[Boolean](
        na  = "mr_request_scr b ng_enable_w h_feature_hydrat ng",
        default = false
      )

  /*
   * TargetLevel Feature l st for Mr request scr b ng
   */
  object TargetLevelFeatureL stForMrRequestScr b ng
      extends FSParam[Seq[Str ng]](
        na  = "mr_request_scr b ng_target_level_feature_l st",
        default = Seq.empty
      )

  /**
   * Param to enable \eps-greedy explorat on for B gF lter ng/LTV-based f lter ng
   */
  object EnableMrRequestScr b ngForEpsGreedyExplorat on
      extends FSParam[Boolean](
        na  = "mr_request_scr b ng_eps_greedy_explorat on_enable",
        default = false
      )

  /**
   * Param to control eps lon  n \eps-greedy explorat on for B gF lter ng/LTV-based f lter ng
   */
  object MrRequestScr b ngEpsGreedyExplorat onRat o
      extends FSBoundedParam[Double](
        na  = "mr_request_scr b ng_eps_greedy_explorat on_rat o",
        default = 0.0,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Param to enable scr b ng d sm ss model score
   */
  object EnableMrRequestScr b ngD sm ssScore
      extends FSParam[Boolean](
        na  = "mr_request_scr b ng_d sm ss_score_enable",
        default = false
      )

  /**
   * Param to enable scr b ng B gF lter ng superv sed model(s) score(s)
   */
  object EnableMrRequestScr b ngB gF lter ngSuperv sedScores
      extends FSParam[Boolean](
        na  = "mr_request_scr b ng_b gf lter ng_superv sed_scores_enable",
        default = false
      )

  /**
   * Param to enable scr b ng B gF lter ng RL model(s) score(s)
   */
  object EnableMrRequestScr b ngB gF lter ngRLScores
      extends FSParam[Boolean](
        na  = "mr_request_scr b ng_b gf lter ng_rl_scores_enable",
        default = false
      )

  /**
   * Param to flatten mr request scr be
   */
  object EnableFlattenMrRequestScr b ng
      extends FSParam[Boolean](
        na  = "mr_request_scr b ng_enable_flatten",
        default = false
      )

  /**
   * Param to enable NSFW token based f lter ng
   */
  object EnableNsfwTokenBasedF lter ng
      extends FSParam[Boolean](
        na  = " alth_and_qual y_f lter_enable_nsfw_token_based_f lter ng",
        default = false
      )

  object NsfwTokensParam
      extends FSParam[Seq[Str ng]](
        na  = " alth_and_qual y_f lter_nsfw_tokens",
        default = Seq("nsfw", "18+", "\uD83D\uDD1E"))

  object M n mumAllo dAuthorAccountAge nH s
      extends FSBoundedParam[ nt](
        na  = " alth_and_qual y_f lter_m n mum_allo d_author_account_age_ n_h s",
        default = 0,
        m n = 0,
        max = 168
      )

  /**
   * Param to enable t  profan y f lter
   */
  object EnableProfan yF lterParam
      extends FSParam[Boolean](
        na  = " alth_and_qual y_f lter_enable_profan y_f lter",
        default = false
      )

  /**
   * Param to enable query t  author  d a representat on store
   */
  object EnableQueryAuthor d aRepresentat onStore
      extends FSParam[Boolean](
        na  = " alth_and_qual y_f lter_enable_query_author_ d a_representat on_store",
        default = false
      )

  /**
   * Threshold to f lter a t et based on t  author sens  ve  d a score
   */
  object AuthorSens  ve d aF lter ngThreshold
      extends FSBoundedParam[Double](
        na  = " alth_and_qual y_f lter_author_sens  ve_ d a_f lter ng_threshold",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Threshold to f lter a t et based on t  author sens  ve  d a score
   */
  object AuthorSens  ve d aF lter ngThresholdForMrTw stly
      extends FSBoundedParam[Double](
        na  = " alth_and_qual y_f lter_author_sens  ve_ d a_f lter ng_threshold_for_mrtw stly",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Param to enable f lter ng t  S mCluster t et  f   has AbuseStr ke_Top2Percent ent  y
   */
  object EnableAbuseStr keTop2PercentF lterS mCluster
      extends FSParam[Boolean](
        na  = " alth_s gnal_store_enable_abuse_str ke_top_2_percent_f lter_s m_cluster",
        default = false
      )

  /**
   * Param to enable f lter ng t  S mCluster t et  f   has AbuseStr ke_Top1Percent ent  y
   */
  object EnableAbuseStr keTop1PercentF lterS mCluster
      extends FSParam[Boolean](
        na  = " alth_s gnal_store_enable_abuse_str ke_top_1_percent_f lter_s m_cluster",
        default = false
      )

  /**
   * Param to enable f lter ng t  S mCluster t et  f   has AbuseStr ke_Top0.5Percent ent  y
   */
  object EnableAbuseStr keTop05PercentF lterS mCluster
      extends FSParam[Boolean](
        na  = " alth_s gnal_store_enable_abuse_str ke_top_05_percent_f lter_s m_cluster",
        default = false
      )

  object EnableAgathaUser althModelPred cate
      extends FSParam[Boolean](
        na  = " alth_s gnal_store_enable_agatha_user_ alth_model_pred cate",
        default = false
      )

  /**
   * Threshold to f lter a t et based on t  agatha_cal brated_nsfw score of  s author for MrTw stly
   */
  object AgathaCal bratedNSFWThresholdForMrTw stly
      extends FSBoundedParam[Double](
        na  = " alth_s gnal_store_agatha_cal brated_nsfw_threshold_for_mrtw stly",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Threshold to f lter a t et based on t  agatha_cal brated_nsfw score of  s author
   */
  object AgathaCal bratedNSFWThreshold
      extends FSBoundedParam[Double](
        na  = " alth_s gnal_store_agatha_cal brated_nsfw_threshold",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Threshold to f lter a t et based on t  agatha_nsfw_text_user score of  s author for MrTw stly
   */
  object AgathaTextNSFWThresholdForMrTw stly
      extends FSBoundedParam[Double](
        na  = " alth_s gnal_store_agatha_text_nsfw_threshold_for_mrtw stly",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Threshold to f lter a t et based on t  agatha_nsfw_text_user score of  s author
   */
  object AgathaTextNSFWThreshold
      extends FSBoundedParam[Double](
        na  = " alth_s gnal_store_agatha_text_nsfw_threshold",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Threshold to bucket a user based on t  agatha_cal brated_nsfw score of t  t et author
   */
  object AgathaCal bratedNSFWBucketThreshold
      extends FSBoundedParam[Double](
        na  = " alth_s gnal_store_agatha_cal brated_nsfw_bucket_threshold",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Threshold to bucket a user based on t  agatha_nsfw_text_user score of t  t et author
   */
  object AgathaTextNSFWBucketThreshold
      extends FSBoundedParam[Double](
        na  = " alth_s gnal_store_agatha_text_nsfw_bucket_threshold",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Param to enable f lter ng us ng pnsfw_text_t et model.
   */
  object Enable althS gnalStorePnsfwT etTextPred cate
      extends FSParam[Boolean](
        na  = " alth_s gnal_store_enable_pnsfw_t et_text_pred cate",
        default = false
      )

  /**
   * Threshold score for f lter ng based on pnsfw_text_t et Model.
   */
  object PnsfwT etTextThreshold
      extends FSBoundedParam[Double](
        na  = " alth_s gnal_store_pnsfw_t et_text_threshold",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Threshold score for bucket ng based on pnsfw_text_t et Model.
   */
  object PnsfwT etTextBucket ngThreshold
      extends FSBoundedParam[Double](
        na  = " alth_s gnal_store_pnsfw_t et_text_bucket ng_threshold",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Enable f lter ng t ets w h  d a based on pnsfw_ d a_t et Model for OON t ets only.
   */
  object PnsfwT et d aF lterOonOnly
      extends FSParam[Boolean](
        na  = " alth_s gnal_store_pnsfw_t et_ d a_f lter_oon_only",
        default = true
      )

  /**
   * Threshold score for f lter ng t ets w h  d a based on pnsfw_ d a_t et Model.
   */
  object PnsfwT et d aThreshold
      extends FSBoundedParam[Double](
        na  = " alth_s gnal_store_pnsfw_t et_ d a_threshold",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Threshold score for f lter ng t ets w h  mages based on pnsfw_ d a_t et Model.
   */
  object PnsfwT et mageThreshold
      extends FSBoundedParam[Double](
        na  = " alth_s gnal_store_pnsfw_t et_ mage_threshold",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Threshold score for f lter ng quote/reply t ets based on s ce t et's  d a
   */
  object PnsfwQuoteT etThreshold
      extends FSBoundedParam[Double](
        na  = " alth_s gnal_store_pnsfw_quote_t et_threshold",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Threshold score for bucket ng based on pnsfw_ d a_t et Model.
   */
  object PnsfwT et d aBucket ngThreshold
      extends FSBoundedParam[Double](
        na  = " alth_s gnal_store_pnsfw_t et_ d a_bucket ng_threshold",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Param to enable f lter ng us ng mult l ngual psnfw pred cate
   */
  object Enable althS gnalStoreMult l ngualPnsfwT etTextPred cate
      extends FSParam[Boolean](
        na  = " alth_s gnal_store_enable_mult l ngual_pnsfw_t et_text_pred cate",
        default = false
      )

  /**
   * Language sequence   w ll query pnsfw scores for
   */
  object Mult l ngualPnsfwT etTextSupportedLanguages
      extends FSParam[Seq[Str ng]](
        na  = " alth_s gnal_store_mult l ngual_pnsfw_t et_supported_languages",
        default = Seq.empty[Str ng],
      )

  /**
   * Threshold score per language for bucket ng based on pnsfw scores.
   */
  object Mult l ngualPnsfwT etTextBucket ngThreshold
      extends FSParam[Seq[Double]](
        na  = " alth_s gnal_store_mult l ngual_pnsfw_t et_text_bucket ng_thresholds",
        default = Seq.empty[Double],
      )

  /**
   * Threshold score per language for f lter ng based on pnsfw scores.
   */
  object Mult l ngualPnsfwT etTextF lter ngThreshold
      extends FSParam[Seq[Double]](
        na  = " alth_s gnal_store_mult l ngual_pnsfw_t et_text_f lter ng_thresholds",
        default = Seq.empty[Double],
      )

  /**
   * L st of models to threshold scores for bucket ng purposes
   */
  object Mult l ngualPnsfwT etTextBucket ngModelL st
      extends FSEnumSeqParam[NsfwTextDetect onModel.type](
        na  = " alth_s gnal_store_mult l ngual_pnsfw_t et_text_bucket ng_models_ ds",
        default = Seq(NsfwTextDetect onModel.ProdModel),
        enum = NsfwTextDetect onModel
      )

  object Mult l ngualPnsfwT etTextModel
      extends FSEnumParam[NsfwTextDetect onModel.type](
        na  = " alth_s gnal_store_mult l ngual_pnsfw_t et_text_model",
        default = NsfwTextDetect onModel.ProdModel,
        enum = NsfwTextDetect onModel
      )

  /**
   * Param to determ ne  d a should be enabled for andro d
   */
  object EnableEventSquare d aAndro d
      extends FSParam[Boolean](
        na  = "mr_enable_event_ d a_square_andro d",
        default = false
      )

  /**
   * Param to determ ne expanded  d a should be enabled for andro d
   */
  object EnableEventPr mary d aAndro d
      extends FSParam[Boolean](
        na  = "mr_enable_event_ d a_pr mary_andro d",
        default = false
      )

  /**
   * Param to determ ne  d a should be enabled for  os for Mag cFanout
   */
  object EnableEventSquare d a osMag cFanoutNewsEvent
      extends FSParam[Boolean](
        na  = "mr_enable_event_ d a_square_ os_mf",
        default = false
      )

  /**
   * Param to conf gure HTL V s  fat gue
   */
  object HTLV s Fat gueT  
      extends FSBoundedParam[ nt](
        na  = "fr gate_push_htl_v s _fat gue_t  ",
        default = 20,
        m n = 0,
        max = 72) {

    // Fat gue durat on for HTL v s 
    f nal val DefaultH sToFat gueAfterHtlV s  = 20
    f nal val OldH sToFat gueAfterHtlV s  = 8
  }

  object Mag cFanoutNewsUserGeneratedEventsEnable
      extends FSParam[Boolean](
        na  = "mag cfanout_news_user_generated_events_enable",
        default = false)

  object Mag cFanoutSk pAccountCountryPred cate
      extends FSParam[Boolean]("mag cfanout_news_sk p_account_country_pred cate", false)

  object Mag cFanoutNewsEnableDescr pt onCopy
      extends FSParam[Boolean](na  = "mag cfanout_news_enable_descr pt on_copy", default = false)

  /**
   *  Enables Custom Target ng for Mag cFnaout News events  n Pushserv ce
   */
  object Mag cFanoutEnableCustomTarget ngNewsEvent
      extends FSParam[Boolean]("mag cfanout_news_event_custom_target ng_enable", false)

  /**
   * Enable Top c Copy  n MF
   */
  object EnableTop cCopyForMF
      extends FSParam[Boolean](
        na  = "mag cfanout_enable_top c_copy",
        default = false
      )

  /**
   * Enable Top c Copy  n MF for  mpl c  top cs
   */
  object EnableTop cCopyFor mpl c Top cs
      extends FSParam[Boolean](
        na  = "mag cfanout_enable_top c_copy_erg_ nterests",
        default = false
      )

  /**
   * Enable NewCreator push
   */
  object EnableNewCreatorPush
      extends FSParam[Boolean](
        na  = "new_creator_enable_push",
        default = false
      )

  /**
   * Enable CreatorSubscr pt on push
   */
  object EnableCreatorSubscr pt onPush
      extends FSParam[Boolean](
        na  = "creator_subscr pt on_enable_push",
        default = false
      )

  /**
   * Featuresw ch param to enable/d sable push recom ndat ons
   */
  object EnablePushRecom ndat onsParam
      extends FSParam[Boolean](na  = "push_recom ndat ons_enabled", default = false)

  object D sableMl nF lter ngFeatureSw chParam
      extends FSParam[Boolean](
        na  = "fr gate_push_model ng_d sable_ml_ n_f lter ng",
        default = false
      )

  object EnableM nDurat onMod f er
      extends FSParam[Boolean](
        na  = "m n_durat on_mod f er_enable_h _mod f er",
        default = false
      )

  object EnableM nDurat onMod f erV2
      extends FSParam[Boolean](
        na  = "m n_durat on_mod f er_enable_h _mod f er_v2",
        default = false
      )

  object M nDurat onMod f erStartH L st
      extends FSParam[Seq[ nt]](
        na  = "m n_durat on_mod f er_start_t  _l st",
        default = Seq(),
      )

  object M nDurat onMod f erEndH L st
      extends FSParam[Seq[ nt]](
        na  = "m n_durat on_mod f er_start_end_l st",
        default = Seq(),
      )

  object M nDurat onT  Mod f erConst
      extends FSParam[Seq[ nt]](
        na  = "m n_durat on_mod f er_const_l st",
        default = Seq(),
      )

  object EnableQueryUserOpened tory
      extends FSParam[Boolean](
        na  = "m n_durat on_mod f er_enable_query_user_opened_ tory",
        default = false
      )

  object EnableM nDurat onMod f erByUser tory
      extends FSParam[Boolean](
        na  = "m n_durat on_mod f er_enable_h _mod f er_by_user_ tory",
        default = false
      )

  object EnableRandomH ForQu ckSend
      extends FSParam[Boolean](
        na  = "m n_durat on_mod f er_enable_random_h _for_qu ck_send",
        default = false
      )

  object SendT  ByUser toryMaxOpenedThreshold
      extends FSBoundedParam[ nt](
        na  = "m n_durat on_mod f er_max_opened_threshold",
        default = 4,
        m n = 0,
        max = 100)

  object SendT  ByUser toryNoSendsH s
      extends FSBoundedParam[ nt](
        na  = "m n_durat on_mod f er_no_sends_h s",
        default = 1,
        m n = 0,
        max = 24)

  object SendT  ByUser toryQu ckSendBeforeH s
      extends FSBoundedParam[ nt](
        na  = "m n_durat on_mod f er_qu ck_send_before_h s",
        default = 0,
        m n = 0,
        max = 24)

  object SendT  ByUser toryQu ckSendAfterH s
      extends FSBoundedParam[ nt](
        na  = "m n_durat on_mod f er_qu ck_send_after_h s",
        default = 0,
        m n = 0,
        max = 24)

  object SendT  ByUser toryQu ckSendM nDurat on nM nute
      extends FSBoundedParam[ nt](
        na  = "m n_durat on_mod f er_qu ck_send_m n_durat on",
        default = 0,
        m n = 0,
        max = 1440)

  object SendT  ByUser toryNoSendM nDurat on
      extends FSBoundedParam[ nt](
        na  = "m n_durat on_mod f er_no_send_m n_durat on",
        default = 24,
        m n = 0,
        max = 24)

  object EnableMfGeoTarget ng
      extends FSParam[Boolean](
        na  = "fr gate_push_mag cfanout_geo_target ng_enable",
        default = false)

  /**
   * Enable RUX T et land ng page for push open. W n t  param  s enabled, user w ll go to RUX
   * land ng page  nstead of T et deta ls page w n open ng Mag cRecs push.
   */
  object EnableRuxLand ngPage
      extends FSParam[Boolean](na  = "fr gate_push_enable_rux_land ng_page", default = false)

  /**
   * Enable RUX T et land ng page for Ntab Cl ck. W n t  param  s enabled, user w ll go to RUX
   * land ng page  nstead of T et deta ls page w n cl ck Mag cRecs entry on Ntab.
   */
  object EnableNTabRuxLand ngPage
      extends FSParam[Boolean](na  = "fr gate_push_enable_ntab_rux_land ng_page", default = false)

  /**
   * Param to enable Onboard ng Pus s
   */
  object EnableOnboard ngPus s
      extends FSParam[Boolean](
        na  = "onboard ng_push_enable",
        default = false
      )

  /**
   * Param to enable Address Book Pus s
   */
  object EnableAddressBookPush
      extends FSParam[Boolean](
        na  = "onboard ng_push_enable_address_book_push",
        default = false
      )

  /**
   * Param to enable Complete Onboard ng Pus s
   */
  object EnableCompleteOnboard ngPush
      extends FSParam[Boolean](
        na  = "onboard ng_push_enable_complete_onboard ng_push",
        default = false
      )

  /**
   * Param to enable Smart Push Conf g for MR Overr de Not fs on Andro d
   */
  object EnableOverr deNot f cat onsSmartPushConf gForAndro d
      extends FSParam[Boolean](
        na  = "mr_overr de_enable_smart_push_conf g_for_andro d",
        default = false)

  /**
   * Param to control t  m n durat on s nce last MR push for Onboard ng Pus s
   */
  object MrM nDurat onS ncePushForOnboard ngPus s
      extends FSBoundedParam[Durat on](
        na  = "onboard ng_push_m n_durat on_s nce_push_days",
        default = 4.days,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  /**
   * Param to control t  push fat gue for Onboard ng Pus s
   */
  object Fat gueForOnboard ngPus s
      extends FSBoundedParam[Durat on](
        na  = "onboard ng_push_fat gue_days",
        default = 30.days,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  /**
   * Param to spec fy t  max mum number of Onboard ng Push Not fs  n a spec f ed per od of t  
   */
  object MaxOnboard ngPush n nterval
      extends FSBoundedParam[ nt](
        na  = "onboard ng_push_max_ n_ nterval",
        default = 1,
        m n = 0,
        max = 10
      )

  /**
   * Param to d sable t  Onboard ng Push Not f Fat gue
   */
  object D sableOnboard ngPushFat gue
      extends FSParam[Boolean](
        na  = "onboard ng_push_d sable_push_fat gue",
        default = false
      )

  /**
   * Param to control t   nverter for fat gue bet en consecut ve TopT etsByGeoPush
   */
  object TopT etsByGeoPush nterval
      extends FSBoundedParam[Durat on](
        na  = "top_t ets_by_geo_ nterval_days",
        default = 0.days,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  /**
   * Param to control t   nverter for fat gue bet en consecut ve Tr pT ets
   */
  object H ghQual yT etsPush nterval
      extends FSBoundedParam[Durat on](
        na  = "h gh_qual y_cand dates_push_ nterval_days",
        default = 1.days,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  /**
   * Exp ry TTL durat on for T et Not f cat on types wr ten to  tory store
   */
  object Fr gate toryT etNot f cat onWr eTtl
      extends FSBoundedParam[Durat on](
        na  = "fr gate_not f cat on_ tory_t et_wr e_ttl_days",
        default = 60.days,
        m n = Durat on.Bottom,
        max = Durat on.Top
      )
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  /**
   * Exp ry TTL durat on for Not f cat on wr ten to  tory store
   */
  object Fr gate toryOt rNot f cat onWr eTtl
      extends FSBoundedParam[Durat on](
        na  = "fr gate_not f cat on_ tory_ot r_wr e_ttl_days",
        default = 90.days,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  /**
   * Param to control max mum number of TopT etsByGeoPush pus s to rece ve  n an  nterval
   */
  object MaxTopT etsByGeoPushG ven nterval
      extends FSBoundedParam[ nt](
        na  = "top_t ets_by_geo_push_g ven_ nterval",
        default = 1,
        m n = 0,
        max = 10
      )

  /**
   * Param to control max mum number of H ghQual yT et pus s to rece ve  n an  nterval
   */
  object MaxH ghQual yT etsPushG ven nterval
      extends FSBoundedParam[ nt](
        na  = "h gh_qual y_cand dates_max_push_g ven_ nterval",
        default = 3,
        m n = 0,
        max = 10
      )

  /**
   * Param to downrank/backf ll top t ets by geo cand dates
   */
  object Backf llRankTopT etsByGeoCand dates
      extends FSParam[Boolean](
        na  = "top_t ets_by_geo_backf ll_rank",
        default = false
      )

  /**
   * Determ ne w t r to use aggress ve thresholds for  alth f lter ng on SearchT et
   */
  object PopGeoT etEnableAggress veThresholds
      extends FSParam[Boolean](
        na  = "top_t ets_by_geo_enable_aggress ve_ alth_thresholds",
        default = false
      )

  /**
   * Param to apply d fferent scor ng funct ons to select top t ets by geo cand dates
   */
  object Scor ngFuncForTopT etsByGeo
      extends FSParam[Str ng](
        na  = "top_t ets_by_geo_scor ng_funct on",
        default = "Pop8H",
      )

  /**
   * Param to query d fferent stores  n pop geo serv ce.
   */
  object TopT etsByGeoComb nat onParam
      extends FSEnumParam[TopT etsForGeoComb nat on.type](
        na  = "top_t ets_by_geo_comb nat on_ d",
        default = TopT etsForGeoComb nat on.Default,
        enum = TopT etsForGeoComb nat on
      )

  /**
   * Param for popgeo t et vers on
   */
  object PopGeoT etVers onParam
      extends FSEnumParam[PopGeoT etVers on.type](
        na  = "top_t ets_by_geo_vers on_ d",
        default = PopGeoT etVers on.Prod,
        enum = PopGeoT etVers on
      )

  /**
   * Param to query what length of hash for geoh store
   */
  object GeoHashLengthL st
      extends FSParam[Seq[ nt]](
        na  = "top_t ets_by_geo_hash_length_l st",
        default = Seq(4),
      )

  /**
   * Param to  nclude country code results as back off .
   */
  object EnableCountryCodeBackoffTopT etsByGeo
      extends FSParam[Boolean](
        na  = "top_t ets_by_geo_enable_country_code_backoff",
        default = false,
      )

  /**
   * Param to dec de rank ng funct on for fetc d top t ets by geo
   */
  object Rank ngFunct onForTopT etsByGeo
      extends FSEnumParam[TopT etsForGeoRank ngFunct on.type](
        na  = "top_t ets_by_geo_rank ng_funct on_ d",
        default = TopT etsForGeoRank ngFunct on.Score,
        enum = TopT etsForGeoRank ngFunct on
      )

  /**
   * Param to enable top t ets by geo cand dates
   */
  object EnableTopT etsByGeoCand dates
      extends FSParam[Boolean](
        na  = "top_t ets_by_geo_enable_cand date_s ce",
        default = false
      )

  /**
   * Param to enable top t ets by geo cand dates for dormant users
   */
  object EnableTopT etsByGeoCand datesForDormantUsers
      extends FSParam[Boolean](
        na  = "top_t ets_by_geo_enable_cand date_s ce_dormant_users",
        default = false
      )

  /**
   * Param to spec fy t  max mum number of Top T ets by Geo cand dates to take
   */
  object MaxTopT etsByGeoCand datesToTake
      extends FSBoundedParam[ nt](
        na  = "top_t ets_by_geo_cand dates_to_take",
        default = 10,
        m n = 0,
        max = 100
      )

  /**
   * Param to m n durat on s nce last MR push for top t ets by geo pus s
   */
  object MrM nDurat onS ncePushForTopT etsByGeoPus s
      extends FSBoundedParam[Durat on](
        na  = "top_t ets_by_geo_m n_durat on_s nce_last_mr_days",
        default = 3.days,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  /**
   * Param to enable FRS cand date t ets
   */
  object EnableFrsCand dates
      extends FSParam[Boolean](
        na  = "frs_t et_cand date_enable_adaptor",
        default = false
      )

  /**
   * Param to enable FRST et cand dates for top c sett ng users
   * */
  object EnableFrsT etCand datesTop cSett ng
      extends FSParam[Boolean](
        na  = "frs_t et_cand date_enable_adaptor_for_top c_sett ng",
        default = false
      )

  /**
   * Param to enable top c annotat ons for FRST et cand dates t ets
   * */
  object EnableFrsT etCand datesTop cAnnotat on
      extends FSParam[Boolean](
        na  = "frs_t et_cand date_enable_top c_annotat on",
        default = false
      )

  /**
   * Param to enable top c copy for FRST et cand dates t ets
   * */
  object EnableFrsT etCand datesTop cCopy
      extends FSParam[Boolean](
        na  = "frs_t et_cand date_enable_top c_copy",
        default = false
      )

  /**
   * Top c score threshold for FRST et cand dates top c annotat ons
   * */
  object FrsT etCand datesTop cScoreThreshold
      extends FSBoundedParam[Double](
        na  = "frs_t et_cand date_top c_score_threshold",
        default = 0.0,
        m n = 0.0,
        max = 100.0
      )

  /**
   * Param to enable mr model ng-based cand dates t ets
   * */
  object EnableMrModel ngBasedCand dates
      extends FSParam[Boolean](
        na  = "cand date_generat on_model_enable_adaptor",
        default = false
      )

  /**
   Param to enable mr model ng-based cand dates t ets for top c sett ng users
   * */
  object EnableMrModel ngBasedCand datesTop cSett ng
      extends FSParam[Boolean](
        na  = "cand date_generat on_model_enable_adaptor_for_top c_sett ng",
        default = false
      )

  /**
   * Param to enable top c annotat ons for mr model ng-based cand dates t ets
   * */
  object EnableMrModel ngBasedCand datesTop cAnnotat on
      extends FSParam[Boolean](
        na  = "cand date_generat on_model_enable_adaptor_top c_annotat on",
        default = false
      )

  /**
   * Top c score threshold for mr model ng based cand dates top c annotat ons
   * */
  object MrModel ngBasedCand datesTop cScoreThreshold
      extends FSBoundedParam[Double](
        na  = "cand date_generat on_model_top c_score_threshold",
        default = 0.0,
        m n = 0.0,
        max = 100.0
      )

  /**
   * Param to enable top c copy for mr model ng-based cand dates t ets
   * */
  object EnableMrModel ngBasedCand datesTop cCopy
      extends FSParam[Boolean](
        na  = "cand date_generat on_model_enable_top c_copy",
        default = false
      )

  /**
   * Number of max mr model ng based cand dates
   * */
  object NumberOfMaxMrModel ngBasedCand dates
      extends FSBoundedParam[ nt](
        na  = "cand date_generat on_model_max_mr_model ng_based_cand dates",
        default = 200,
        m n = 0,
        max = 1000
      )

  /**
   * Enable t  traff c to use fav threshold
   * */
  object EnableThresholdOfFavMrModel ngBasedCand dates
      extends FSParam[Boolean](
        na  = "cand date_generat on_model_enable_fav_threshold",
        default = false
      )

  /**
   * Threshold of fav for mr model ng based cand dates
   * */
  object ThresholdOfFavMrModel ngBasedCand dates
      extends FSBoundedParam[ nt](
        na  = "cand date_generat on_model_fav_threshold",
        default = 0,
        m n = 0,
        max = 500
      )

  /**
   * F ltered threshold for mr model ng based cand dates
   * */
  object Cand dateGenerat onModelCos neThreshold
      extends FSBoundedParam[Double](
        na  = "cand date_generat on_model_cos ne_threshold",
        default = 0.9,
        m n = 0.0,
        max = 1.0
      )

  /*
   * ANN hypara ters
   * */
  object ANNEfQuery
      extends FSBoundedParam[ nt](
        na  = "cand date_generat on_model_ann_ef_query",
        default = 300,
        m n = 50,
        max = 1500
      )

  /**
   * Param to do real A/B  mpress on for FRS cand dates to avo d d lut on
   */
  object EnableResultFromFrsCand dates
      extends FSParam[Boolean](
        na  = "frs_t et_cand date_enable_returned_result",
        default = false
      )

  /**
   * Param to enable hashspace cand date t ets
   */
  object EnableHashspaceCand dates
      extends FSParam[Boolean](
        na  = "hashspace_cand date_enable_adaptor",
        default = false
      )

  /**
   * Param to enable hashspace cand dates t ets for top c sett ng users
   * */
  object EnableHashspaceCand datesTop cSett ng
      extends FSParam[Boolean](
        na  = "hashspace_cand date_enable_adaptor_for_top c_sett ng",
        default = false
      )

  /**
   * Param to enable top c annotat ons for hashspace cand dates t ets
   * */
  object EnableHashspaceCand datesTop cAnnotat on
      extends FSParam[Boolean](
        na  = "hashspace_cand date_enable_top c_annotat on",
        default = false
      )

  /**
   * Param to enable top c copy for hashspace cand dates t ets
   * */
  object EnableHashspaceCand datesTop cCopy
      extends FSParam[Boolean](
        na  = "hashspace_cand date_enable_top c_copy",
        default = false
      )

  /**
   * Top c score threshold for hashspace cand dates top c annotat ons
   * */
  object HashspaceCand datesTop cScoreThreshold
      extends FSBoundedParam[Double](
        na  = "hashspace_cand date_top c_score_threshold",
        default = 0.0,
        m n = 0.0,
        max = 100.0
      )

  /**
   * Param to do real A/B  mpress on for hashspace cand dates to avo d d lut on
   */
  object EnableResultFromHashspaceCand dates
      extends FSParam[Boolean](
        na  = "hashspace_cand date_enable_returned_result",
        default = false
      )

  /**
   * Param to enable detop c t et cand dates  n adaptor
   */
  object EnableDeTop cT etCand dates
      extends FSParam[Boolean](
        na  = "detop c_t et_cand date_enable_adaptor",
        default = false
      )

  /**
   * Param to enable detop c t et cand dates results (to avo d d lut on)
   */
  object EnableDeTop cT etCand dateResults
      extends FSParam[Boolean](
        na  = "detop c_t et_cand date_enable_results",
        default = false
      )

  /**
   * Param to spec fy w t r to prov de a custom l st of top cs  n request
   */
  object EnableDeTop cT etCand datesCustomTop cs
      extends FSParam[Boolean](
        na  = "detop c_t et_cand date_enable_custom_top cs",
        default = false
      )

  /**
   * Param to spec fy w t r to prov de a custom language  n request
   */
  object EnableDeTop cT etCand datesCustomLanguages
      extends FSParam[Boolean](
        na  = "detop c_t et_cand date_enable_custom_languages",
        default = false
      )

  /**
   * Number of detop c t et cand dates  n t  request
   * */
  object NumberOfDeTop cT etCand dates
      extends FSBoundedParam[ nt](
        na  = "detop c_t et_cand date_num_cand dates_ n_request",
        default = 600,
        m n = 0,
        max = 3000
      )

  /**
   * Max Number of detop c t et cand dates returned  n adaptor
   * */
  object NumberOfMaxDeTop cT etCand datesReturned
      extends FSBoundedParam[ nt](
        na  = "detop c_t et_cand date_max_num_cand dates_returned",
        default = 200,
        m n = 0,
        max = 3000
      )

  /**
   * Param to enable F1 from protected Authors
   */
  object EnableF1FromProtectedT etAuthors
      extends FSParam[Boolean](
        "f1_enable_protected_t ets",
        false
      )

  /**
   * Param to enable safe user t et t etyp e store
   */
  object EnableSafeUserT etT etyp eStore
      extends FSParam[Boolean](
        "mr_ nfra_enable_use_safe_user_t et_t etyp e",
        false
      )

  /**
   * Param to m n durat on s nce last MR push for top t ets by geo pus s
   */
  object EnableMrM nDurat onS nceMrPushFat gue
      extends FSParam[Boolean](
        na  = "top_t ets_by_geo_enable_m n_durat on_s nce_mr_fat gue",
        default = false
      )

  /**
   * Param to c ck t   s nce last t   user logged  n for geo top t ets by geo push
   */
  object T  S nceLastLog nForGeoPopT etPush
      extends FSBoundedParam[Durat on](
        na  = "top_t ets_by_geo_t  _s nce_last_log n_ n_days",
        default = 14.days,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  /**
   * Param to c ck t   s nce last t   user logged  n for geo top t ets by geo push
   */
  object M n mumT  S nceLastLog nForGeoPopT etPush
      extends FSBoundedParam[Durat on](
        na  = "top_t ets_by_geo_m n mum_t  _s nce_last_log n_ n_days",
        default = 14.days,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  /** How long   wa  after a user v s ed t  app before send ng t m a space fanout rec */
  object SpaceRecsAppFat gueDurat on
      extends FSBoundedParam[Durat on](
        na  = "space_recs_app_fat gue_durat on_h s",
        default = 4.h s,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /** T  fat gue t  -w ndow for OON space fanout recs, e.g. 1 push every 3 days */
  object OONSpaceRecsFat gueDurat on
      extends FSBoundedParam[Durat on](
        na  = "space_recs_oon_fat gue_durat on_days",
        default = 1.days,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  /** T  global fat gue t  -w ndow for space fanout recs, e.g. 1 push every 3 days */
  object SpaceRecsGlobalFat gueDurat on
      extends FSBoundedParam[Durat on](
        na  = "space_recs_global_fat gue_durat on_days",
        default = 1.day,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  /** T  m n- nterval bet en space fanout recs.
   * After rece v ng a space fanout rec, t y must wa  a m n mum of t 
   *  nterval before el g b le for anot r */
  object SpaceRecsFat gueM n ntervalDurat on
      extends FSBoundedParam[Durat on](
        na  = "space_recs_fat gue_m n nterval_durat on_m nutes",
        default = 30.m nutes,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromM nutes
  }

  /** Space fanout user-follow rank threshold.
   * Users targeted by a follow that  s above t  threshold w ll be f ltered */
  object SpaceRecsRealgraphThreshold
      extends FSBoundedParam[ nt](
        na  = "space_recs_realgraph_threshold",
        default = 50,
        max = 500,
        m n = 0
      )

  object EnableHydrat ngRealGraphTargetUserFeatures
      extends FSParam[Boolean](
        na  = "fr gate_push_model ng_enable_hydrat ng_real_graph_target_user_feature",
        default = true
      )

  /** Param to reduce d llut on w n c ck ng  f a space  s featured or not */
  object C ckFeaturedSpaceOON
      extends FSParam[Boolean](na  = "space_recs_c ck_ f_ s_featured_space", default = false)

  /** Enable Featured Spaces Rules for OON spaces */
  object EnableFeaturedSpacesOON
      extends FSParam[Boolean](na  = "space_recs_enable_featured_spaces_oon", default = false)

  /** Enable Geo Target ng */
  object EnableGeoTarget ngForSpaces
      extends FSParam[Boolean](na  = "space_recs_enable_geo_target ng", default = false)

  /** Number of max pus s w h n t  fat gue durat on for OON Space Recs */
  object OONSpaceRecsPushL m 
      extends FSBoundedParam[ nt](
        na  = "space_recs_oon_push_l m ",
        default = 1,
        max = 3,
        m n = 0
      )

  /** Space fanout recs, number of max pus s w h n t  fat gue durat on */
  object SpaceRecsGlobalPushL m 
      extends FSBoundedParam[ nt](
        na  = "space_recs_global_push_l m ",
        default = 3,
        max = 50,
        m n = 0
      )

  /**
   * Param to enable score based overr de.
   */
  object EnableOverr deNot f cat onsScoreBasedOverr de
      extends FSParam[Boolean](
        na  = "mr_overr de_enable_score_rank ng",
        default = false
      )

  /**
   * Param to determ ne t  lookback durat on w n search ng for overr de  nfo.
   */
  object Overr deNot f cat onsLookbackDurat onForOverr de nfo
      extends FSBoundedParam[Durat on](
        na  = "mr_overr de_lookback_durat on_overr de_ nfo_ n_days",
        default = 30.days,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  /**
   * Param to determ ne t  lookback durat on w n search ng for  mpress on  ds.
   */
  object Overr deNot f cat onsLookbackDurat onFor mpress on d
      extends FSBoundedParam[Durat on](
        na  = "mr_overr de_lookback_durat on_ mpress on_ d_ n_days",
        default = 30.days,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  /**
   * Param to enable send ng mult ple target  ds  n t  payload.
   */
  object EnableOverr deNot f cat onsMult pleTarget ds
      extends FSParam[Boolean](
        na  = "mr_overr de_enable_mult ple_target_ ds",
        default = false
      )

  /**
   * Param for MR  b Not f cat ons holdback
   */
  object MR bHoldbackParam
      extends FSParam[Boolean](
        na  = "mr_ b_not f cat ons_holdback",
        default = false
      )

  object CommonRecom ndat onTypeDenyL stPushHoldbacks
      extends FSParam[Seq[Str ng]](
        na  = "crt_to_exclude_from_holdbacks_push_holdbacks",
        default = Seq.empty[Str ng]
      )

  /**
   * Param to enable send ng number of slots to ma nta n  n t  payload.
   */
  object EnableOverr deNot f cat onsNSlots
      extends FSParam[Boolean](
        na  = "mr_overr de_enable_n_slots",
        default = false
      )

  /**
   * Enable down rank ng of NUPS and pop geo top c follow cand dates for new user playbook.
   */
  object EnableDownRankOfNewUserPlaybookTop cFollowPush
      extends FSParam[Boolean](
        na  = "top c_follow_new_user_playbook_enable_down_rank",
        default = false
      )

  /**
   * Enable down rank ng of NUPS and pop geo top c t et cand dates for new user playbook.
   */
  object EnableDownRankOfNewUserPlaybookTop cT etPush
      extends FSParam[Boolean](
        na  = "top c_t et_new_user_playbook_enable_down_rank",
        default = false
      )

  /**
   * Param to enable/d sable employee only spaces for fanout of not f cat ons
   */
  object EnableEmployeeOnlySpaceNot f cat ons
      extends FSParam[Boolean](na  = "space_recs_employee_only_enable", default = false)

  /**
   * NTab spaces ttl exper  nts
   */
  object EnableSpacesTtlForNtab
      extends FSParam[Boolean](
        na  = "ntab_spaces_ttl_enable",
        default = false
      )

  /**
   * Param to determ ne t  ttl durat on for space not f cat ons on NTab.
   */
  object SpaceNot f cat onsTTLDurat onForNTab
      extends FSBoundedParam[Durat on](
        na  = "ntab_spaces_ttl_h s",
        default = 1.h ,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /*
   * NTab overr de exper  nts
   * see go/ntab-overr de exper  nt br ef for more deta ls
   */

  /**
   * Overr de not f cat ons for Spaces on lockscreen.
   */
  object EnableOverr deForSpaces
      extends FSParam[Boolean](
        na  = "mr_overr de_spaces",
        default = false
      )

  /**
   * Param to enable stor ng t  Gener c Not f cat on Key.
   */
  object EnableStor ngNtabGener cNot fKey
      extends FSParam[Boolean](
        na  = "ntab_enable_stor ng_gener c_not f_key",
        default = false
      )

  /**
   * Param to enable delet ng t  Target's t  l ne.
   */
  object EnableDelet ngNtabT  l ne
      extends FSParam[Boolean](
        na  = "ntab_enable_delete_t  l ne",
        default = false
      )

  /**
   * Param to enable send ng t  overr de d
   * to NTab wh ch enables overr de support  n NTab-ap 
   */
  object EnableOverr de dNTabRequest
      extends FSParam[Boolean](
        na  = "ntab_enable_overr de_ d_ n_request",
        default = false
      )

  /**
   * [Overr de Workstream] Param to enable NTab overr de n-slot feature.
   */
  object EnableNslotsForOverr deOnNtab
      extends FSParam[Boolean](
        na  = "ntab_enable_overr de_max_count",
        default = false
      )

  /**
   * Param to determ ne t  lookback durat on for overr de cand dates on NTab.
   */
  object Overr deNot f cat onsLookbackDurat onForNTab
      extends FSBoundedParam[Durat on](
        na  = "ntab_overr de_lookback_durat on_days",
        default = 30.days,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  /**
   * Param to determ ne t  max count for cand dates on NTab.
   */
  object Overr deNot f cat onsMaxCountForNTab
      extends FSBoundedParam[ nt](
        na  = "ntab_overr de_l m ",
        m n = 0,
        max =  nt.MaxValue,
        default = 4)

  //// end overr de exper  nts ////
  /**
   * Param to enable top t et  mpress ons not f cat on
   */
  object EnableTopT et mpress onsNot f cat on
      extends FSParam[Boolean](
        na  = "top_t et_ mpress ons_not f cat on_enable",
        default = false
      )

  /**
   * Param to control t   nverter for fat gue bet en consecut ve T et mpress ons
   */
  object TopT et mpress onsNot f cat on nterval
      extends FSBoundedParam[Durat on](
        na  = "top_t et_ mpress ons_not f cat on_ nterval_days",
        default = 7.days,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  /**
   * T  m n- nterval bet en T et mpress ons not f cat ons.
   * After rece v ng a T et mpress ons not f, t y must wa  a m n mum of t 
   *  nterval before be ng el g ble for anot r
   */
  object TopT et mpress onsFat gueM n ntervalDurat on
      extends FSBoundedParam[Durat on](
        na  = "top_t et_ mpress ons_fat gue_m n nterval_durat on_days",
        default = 1.days,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  /**
   * Max mum number of top t et  mpress ons not f cat ons to rece ve  n an  nterval
   */
  object MaxTopT et mpress onsNot f cat ons
      extends FSBoundedParam(
        na  = "top_t et_ mpress ons_fat gue_max_ n_ nterval",
        default = 0,
        m n = 0,
        max = 10
      )

  /**
   * Param for m n number of  mpress ons counts to be el g ble for lonely_b rds_t et_ mpress ons model
   */
  object TopT et mpress onsM nRequ red
      extends FSBoundedParam[ nt](
        na  = "top_t et_ mpress ons_m n_requ red",
        default = 25,
        m n = 0,
        max =  nt.MaxValue
      )

  /**
   * Param for threshold of  mpress ons counts to not fy for lonely_b rds_t et_ mpress ons model
   */
  object TopT et mpress onsThreshold
      extends FSBoundedParam[ nt](
        na  = "top_t et_ mpress ons_threshold",
        default = 25,
        m n = 0,
        max =  nt.MaxValue
      )

  /**
   * Param for t  number of days to search up to for a user's or g nal t ets
   */
  object TopT et mpress onsOr g nalT etsNumDaysSearch
      extends FSBoundedParam[ nt](
        na  = "top_t et_ mpress ons_or g nal_t ets_num_days_search",
        default = 3,
        m n = 0,
        max = 21
      )

  /**
   * Param for t  m n mum number of or g nal t ets a user needs to be cons dered an or g nal author
   */
  object TopT et mpress onsM nNumOr g nalT ets
      extends FSBoundedParam[ nt](
        na  = "top_t et_ mpress ons_num_or g nal_t ets",
        default = 3,
        m n = 0,
        max =  nt.MaxValue
      )

  /**
   * Param for t  max number of favor es any or g nal T et can have
   */
  object TopT et mpress onsMaxFavor esPerT et
      extends FSBoundedParam[ nt](
        na  = "top_t et_ mpress ons_max_favor es_per_t et",
        default = 3,
        m n = 0,
        max =  nt.MaxValue
      )

  /**
   * Param for t  max number of total  nbound favor es for a user's t ets
   */
  object TopT et mpress onsTotal nboundFavor esL m 
      extends FSBoundedParam[ nt](
        na  = "top_t et_ mpress ons_total_ nbound_favor es_l m ",
        default = 60,
        m n = 0,
        max =  nt.MaxValue
      )

  /**
   * Param for t  number of days to search for t ets to count t  total  nbound favor es
   */
  object TopT et mpress onsTotalFavor esL m NumDaysSearch
      extends FSBoundedParam[ nt](
        na  = "top_t et_ mpress ons_total_favor es_l m _num_days_search",
        default = 7,
        m n = 0,
        max = 21
      )

  /**
   * Param for t  max number of recent t ets Tflock should return
   */
  object TopT et mpress onsRecentT etsByAuthorStoreMaxResults
      extends FSBoundedParam[ nt](
        na  = "top_t et_ mpress ons_recent_t ets_by_author_store_max_results",
        default = 50,
        m n = 0,
        max = 1000
      )

  /*
   * Param to represent t  max number of slots to ma nta n for Overr de Not f cat ons
   */
  object Overr deNot f cat onsMaxNumOfSlots
      extends FSBoundedParam[ nt](
        na  = "mr_overr de_max_num_slots",
        default = 1,
        max = 10,
        m n = 1
      )

  object EnableOverr deMaxSlotFn
      extends FSParam[Boolean](
        na  = "mr_overr de_enable_max_num_slots_fn",
        default = false
      )

  object Overr deMaxSlotFnPushCapKnobs
      extends FSParam[Seq[Double]]("mr_overr de_fn_pushcap_knobs", default = Seq.empty[Double])

  object Overr deMaxSlotFnNSlotKnobs
      extends FSParam[Seq[Double]]("mr_overr de_fn_nslot_knobs", default = Seq.empty[Double])

  object Overr deMaxSlotFnPo rKnobs
      extends FSParam[Seq[Double]]("mr_overr de_fn_po r_knobs", default = Seq.empty[Double])

  object Overr deMaxSlotFn  ght
      extends FSBoundedParam[Double](
        "mr_overr de_fn_  ght",
        default = 1.0,
        m n = 0.0,
        max = Double.MaxValue)

  /**
   * Use to enable send ng target  ds  n t  Smart Push Payload
   */
  object EnableTarget ds nSmartPushPayload
      extends FSParam[Boolean](na  = "mr_overr de_enable_target_ ds", default = true)

  /**
   * Param to enable overr de by target  d for Mag cFanoutSportsEvent cand dates
   */
  object EnableTarget d nSmartPushPayloadForMag cFanoutSportsEvent
      extends FSParam[Boolean](
        na  = "mr_overr de_enable_target_ d_for_mag c_fanout_sports_event",
        default = true)

  /**
   * Param to enable secondary account pred cate on MF NFY
   */
  object EnableSecondaryAccountPred cateMF
      extends FSParam[Boolean](
        na  = "fr gate_push_mag cfanout_secondary_account_pred cate",
        default = false
      )

  /**
   * Enables show ng   custo rs v deos on t  r not f cat ons
   */
  object Enable nl neV deo
      extends FSParam[Boolean](na  = "mr_ nl ne_enable_ nl ne_v deo", default = false)

  /**
   * Enables autoplay for  nl ne v deos
   */
  object EnableAutoplayFor nl neV deo
      extends FSParam[Boolean](na  = "mr_ nl ne_enable_autoplay_for_ nl ne_v deo", default = false)

  /**
   * Enable OON f lter ng based on  nt onF lter.
   */
  object EnableOONF lter ngBasedOnUserSett ngs
      extends FSParam[Boolean](na  = "oon_f lter ng_enable_based_on_user_sett ngs", false)

  /**
   * Enables Custom Thread  ds wh ch  s used to ungroup not f cat ons for N-slots on  OS
   */
  object EnableCustomThread dForOverr de
      extends FSParam[Boolean](na  = "mr_overr de_enable_custom_thread_ d", default = false)

  /**
   * Enables show ng ver f ed symbol  n t  push presentat on
   */
  object EnablePushPresentat onVer f edSymbol
      extends FSParam[Boolean](na  = "push_presentat on_enable_ver f ed_symbol", default = false)

  /**
   * Dec de subtext  n Andro d push  ader
   */
  object Subtext nAndro dPush aderParam
      extends FSEnumParam[SubtextForAndro dPush ader.type](
        na  = "push_presentat on_subtext_ n_andro d_push_ ader_ d",
        default = SubtextForAndro dPush ader.None,
        enum = SubtextForAndro dPush ader)

  /**
   * Enable S mClusters Target ng For Spaces.  f false   just drop all cand dates w h such target ng reason
   */
  object EnableS mClusterTarget ngSpaces
      extends FSParam[Boolean](na  = "space_recs_send_s mcluster_recom ndat ons", default = false)

  /**
   * Param to control threshold for dot product of s mcluster based target ng on Spaces
   */
  object SpacesTarget ngS mClusterDotProductThreshold
      extends FSBoundedParam[Double](
        "space_recs_s mclusters_dot_product_threshold",
        default = 0.0,
        m n = 0.0,
        max = 10.0)

  /**
   * Param to control top-k clusters s mcluster based target ng on Spaces
   */
  object SpacesTopKS mClusterCount
      extends FSBoundedParam[ nt](
        "space_recs_s mclusters_top_k_count",
        default = 1,
        m n = 1,
        max = 50)

  /** S mCluster users host/speaker must  et t  follo r count m n mum threshold to be cons dered for sends */
  object SpaceRecsS mClusterUserM n mumFollo rCount
      extends FSBoundedParam[ nt](
        na  = "space_recs_s mcluster_user_m n_follo r_count",
        default = 5000,
        max =  nt.MaxValue,
        m n = 0
      )

  /**
   * Target has been bucketed  nto t   nl ne Act on App V s  Fat gue Exper  nt
   */
  object Target n nl neAct onAppV s Fat gue
      extends FSParam[Boolean](na  = " nl ne_act on_target_ n_app_v s _fat gue", default = false)

  /**
   * Enables  nl ne Act on App V s  Fat gue
   */
  object Enable nl neAct onAppV s Fat gue
      extends FSParam[Boolean](na  = " nl ne_act on_enable_app_v s _fat gue", default = false)

  /**
   * Determ nes t  fat gue that   should apply w n t  target user has perfor d an  nl ne act on
   */
  object  nl neAct onAppV s Fat gue
      extends FSBoundedParam[Durat on](
        na  = " nl ne_act on_app_v s _fat gue_h s",
        default = 8.h s,
        m n = 1.h ,
        max = 48.h s)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   *   ght for rerank ng(oonc -   ght * nud yRate)
   */
  object AuthorSens  veScore  ght nRerank ng
      extends FSBoundedParam[Double](
        na  = "rerank_cand dates_author_sens  ve_score_  ght_ n_rerank ng",
        default = 0.0,
        m n = -100.0,
        max = 100.0
      )

  /**
   * Param to control t  last act ve space l stener threshold to f lter out based on that
   */
  object SpacePart c pant toryLastAct veThreshold
      extends FSBoundedParam[Durat on](
        na  = "space_recs_last_act ve_space_l stener_threshold_ n_h s",
        default = 0.h s,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /*
   * Param to enable mr user s mcluster feature set (v2020) hydrat on for model ng-based cand date generat on
   * */
  object HydrateMrUserS mclusterV2020 nModel ngBasedCG
      extends FSParam[Boolean](
        na  = "cand date_generat on_model_hydrate_mr_user_s mcluster_v2020",
        default = false)

  /*
   * Param to enable mr semant c core feature set hydrat on for model ng-based cand date generat on
   * */
  object HydrateMrUserSemant cCore nModel ngBasedCG
      extends FSParam[Boolean](
        na  = "cand date_generat on_model_hydrate_mr_user_semant c_core",
        default = false)

  /*
   * Param to enable mr semant c core feature set hydrat on for model ng-based cand date generat on
   * */
  object HydrateOnboard ng nModel ngBasedCG
      extends FSParam[Boolean](
        na  = "cand date_generat on_model_hydrate_onboard ng",
        default = false)

  /*
   * Param to enable mr top c follow feature set hydrat on for model ng-based cand date generat on
   * */
  object HydrateTop cFollow nModel ngBasedCG
      extends FSParam[Boolean](
        na  = "cand date_generat on_model_hydrate_top c_follow",
        default = false)

  /*
   * Param to enable mr user top c feature set hydrat on for model ng-based cand date generat on
   * */
  object HydrateMrUserTop c nModel ngBasedCG
      extends FSParam[Boolean](
        na  = "cand date_generat on_model_hydrate_mr_user_top c",
        default = false)

  /*
   * Param to enable mr user top c feature set hydrat on for model ng-based cand date generat on
   * */
  object HydrateMrUserAuthor nModel ngBasedCG
      extends FSParam[Boolean](
        na  = "cand date_generat on_model_hydrate_mr_user_author",
        default = false)

  /*
   * Param to enable user pengu n language feature set hydrat on for model ng-based cand date generat on
   * */
  object HydrateUserPengu nLanguage nModel ngBasedCG
      extends FSParam[Boolean](
        na  = "cand date_generat on_model_hydrate_user_pengu n_language",
        default = false)
  /*
   * Param to enable user geo feature set hydrat on for model ng-based cand date generat on
   * */
  object HydrateUseGeo nModel ngBasedCG
      extends FSParam[Boolean](
        na  = "cand date_generat on_model_hydrate_user_geo",
        default = false)

  /*
   * Param to enable mr user hashspace embedd ng feature set hydrat on for model ng-based cand date generat on
   * */
  object HydrateMrUserHashspaceEmbedd ng nModel ngBasedCG
      extends FSParam[Boolean](
        na  = "cand date_generat on_model_hydrate_mr_user_hashspace_embedd ng",
        default = false)
  /*
   * Param to enable user t et text feature hydrat on
   * */
  object EnableMrUserEngagedT etTokensFeature
      extends FSParam[Boolean](
        na  = "feature_hydrat on_mr_user_engaged_t et_tokens",
        default = false)

  /**
   * Params for CRT based see less often fat gue rules
   */
  object EnableF1Tr ggerSeeLessOftenFat gue
      extends FSParam[Boolean](
        na  = "seelessoften_enable_f1_tr gger_fat gue",
        default = false
      )

  object EnableNonF1Tr ggerSeeLessOftenFat gue
      extends FSParam[Boolean](
        na  = "seelessoften_enable_nonf1_tr gger_fat gue",
        default = false
      )

  /**
   * Adjust t  NtabCaretCl ckFat gue for cand dates  f    s tr ggered by
   * Tr pHqT et cand dates
   */
  object AdjustTr pHqT etTr ggeredNtabCaretCl ckFat gue
      extends FSParam[Boolean](
        na  = "seelessoften_adjust_tr p_hq_t et_tr ggered_fat gue",
        default = false
      )

  object NumberOfDaysToF lterForSeeLessOftenForF1Tr ggerF1
      extends FSBoundedParam[Durat on](
        na  = "seelessoften_for_f1_tr gger_f1_tof ltermr_days",
        default = 7.days,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  object NumberOfDaysToReducePushCapForSeeLessOftenForF1Tr ggerF1
      extends FSBoundedParam[Durat on](
        na  = "seelessoften_for_f1_tr gger_f1_toreduce_pushcap_days",
        default = 30.days,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  object NumberOfDaysToF lterForSeeLessOftenForF1Tr ggerNonF1
      extends FSBoundedParam[Durat on](
        na  = "seelessoften_for_f1_tr gger_nonf1_tof ltermr_days",
        default = 7.days,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  object NumberOfDaysToReducePushCapForSeeLessOftenForF1Tr ggerNonF1
      extends FSBoundedParam[Durat on](
        na  = "seelessoften_for_f1_tr gger_non_f1_toreduce_pushcap_days",
        default = 30.days,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  object NumberOfDaysToF lterForSeeLessOftenForNonF1Tr ggerF1
      extends FSBoundedParam[Durat on](
        na  = "seelessoften_for_nonf1_tr gger_f1_tof ltermr_days",
        default = 7.days,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  object NumberOfDaysToReducePushCapForSeeLessOftenForNonF1Tr ggerF1
      extends FSBoundedParam[Durat on](
        na  = "seelessoften_for_nonf1_tr gger_f1_toreduce_pushcap_days",
        default = 30.days,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  object NumberOfDaysToF lterForSeeLessOftenForNonF1Tr ggerNonF1
      extends FSBoundedParam[Durat on](
        na  = "seelessoften_for_nonf1_tr gger_nonf1_tof ltermr_days",
        default = 7.days,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  object NumberOfDaysToReducePushCapForSeeLessOftenForNonF1Tr ggerNonF1
      extends FSBoundedParam[Durat on](
        na  = "seelessoften_for_nonf1_tr gger_nonf1_toreduce_pushcap_days",
        default = 30.days,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  object EnableContFnF1Tr ggerSeeLessOftenFat gue
      extends FSParam[Boolean](
        na  = "seelessoften_fn_enable_f1_tr gger_fat gue",
        default = false
      )

  object EnableContFnNonF1Tr ggerSeeLessOftenFat gue
      extends FSParam[Boolean](
        na  = "seelessoften_fn_enable_nonf1_tr gger_fat gue",
        default = false
      )

  object SeeLessOftenL stOfDayKnobs
      extends FSParam[Seq[Double]]("seelessoften_fn_day_knobs", default = Seq.empty[Double])

  object SeeLessOftenL stOfPushCap  ghtKnobs
      extends FSParam[Seq[Double]]("seelessoften_fn_pushcap_knobs", default = Seq.empty[Double])

  object SeeLessOftenL stOfPo rKnobs
      extends FSParam[Seq[Double]]("seelessoften_fn_po r_knobs", default = Seq.empty[Double])

  object SeeLessOftenF1Tr ggerF1PushCap  ght
      extends FSBoundedParam[Double](
        "seelessoften_fn_f1_tr gger_f1_  ght",
        default = 1.0,
        m n = 0.0,
        max = 10000000.0)

  object SeeLessOftenF1Tr ggerNonF1PushCap  ght
      extends FSBoundedParam[Double](
        "seelessoften_fn_f1_tr gger_nonf1_  ght",
        default = 1.0,
        m n = 0.0,
        max = 10000000.0)

  object SeeLessOftenNonF1Tr ggerF1PushCap  ght
      extends FSBoundedParam[Double](
        "seelessoften_fn_nonf1_tr gger_f1_  ght",
        default = 1.0,
        m n = 0.0,
        max = 10000000.0)

  object SeeLessOftenNonF1Tr ggerNonF1PushCap  ght
      extends FSBoundedParam[Double](
        "seelessoften_fn_nonf1_tr gger_nonf1_  ght",
        default = 1.0,
        m n = 0.0,
        max = 10000000.0)

  object SeeLessOftenTr pHqT etTr ggerF1PushCap  ght
      extends FSBoundedParam[Double](
        "seelessoften_fn_tr p_hq_t et_tr gger_f1_  ght",
        default = 1.0,
        m n = 0.0,
        max = 10000000.0)

  object SeeLessOftenTr pHqT etTr ggerNonF1PushCap  ght
      extends FSBoundedParam[Double](
        "seelessoften_fn_tr p_hq_t et_tr gger_nonf1_  ght",
        default = 1.0,
        m n = 0.0,
        max = 10000000.0)

  object SeeLessOftenTr pHqT etTr ggerTr pHqT etPushCap  ght
      extends FSBoundedParam[Double](
        "seelessoften_fn_tr p_hq_t et_tr gger_tr p_hq_t et_  ght",
        default = 1.0,
        m n = 0.0,
        max = 10000000.0)

  object SeeLessOftenTop cTr ggerTop cPushCap  ght
      extends FSBoundedParam[Double](
        "seelessoften_fn_top c_tr gger_top c_  ght",
        default = 1.0,
        m n = 0.0,
        max = Double.MaxValue)

  object SeeLessOftenTop cTr ggerF1PushCap  ght
      extends FSBoundedParam[Double](
        "seelessoften_fn_top c_tr gger_f1_  ght",
        default = 100000.0,
        m n = 0.0,
        max = Double.MaxValue)

  object SeeLessOftenTop cTr ggerOONPushCap  ght
      extends FSBoundedParam[Double](
        "seelessoften_fn_top c_tr gger_oon_  ght",
        default = 100000.0,
        m n = 0.0,
        max = Double.MaxValue)

  object SeeLessOftenF1Tr ggerTop cPushCap  ght
      extends FSBoundedParam[Double](
        "seelessoften_fn_f1_tr gger_top c_  ght",
        default = 100000.0,
        m n = 0.0,
        max = Double.MaxValue)

  object SeeLessOftenOONTr ggerTop cPushCap  ght
      extends FSBoundedParam[Double](
        "seelessoften_fn_oon_tr gger_top c_  ght",
        default = 1.0,
        m n = 0.0,
        max = Double.MaxValue)

  object SeeLessOftenDefaultPushCap  ght
      extends FSBoundedParam[Double](
        "seelessoften_fn_default_  ght",
        default = 100000.0,
        m n = 0.0,
        max = Double.MaxValue)

  object SeeLessOftenNtabOnlyNot fUserPushCap  ght
      extends FSBoundedParam[Double](
        "seelessoften_fn_ntab_only_user_  ght",
        default = 1.0,
        m n = 0.0,
        max = Double.MaxValue)

  // Params for  nl ne feedback fat gue
  object EnableContFnF1Tr gger nl neFeedbackFat gue
      extends FSParam[Boolean](
        na  = "feedback_ nl ne_fn_enable_f1_tr gger_fat gue",
        default = false
      )

  object EnableContFnNonF1Tr gger nl neFeedbackFat gue
      extends FSParam[Boolean](
        na  = "feedback_ nl ne_fn_enable_nonf1_tr gger_fat gue",
        default = false
      )

  object Use nl neD sl keForFat gue
      extends FSParam[Boolean](
        na  = "feedback_ nl ne_fn_use_d sl ke",
        default = true
      )
  object Use nl neD sm ssForFat gue
      extends FSParam[Boolean](
        na  = "feedback_ nl ne_fn_use_d sm ss",
        default = false
      )
  object Use nl neSeeLessForFat gue
      extends FSParam[Boolean](
        na  = "feedback_ nl ne_fn_use_see_less",
        default = false
      )
  object Use nl neNotRelevantForFat gue
      extends FSParam[Boolean](
        na  = "feedback_ nl ne_fn_use_not_relevant",
        default = false
      )
  object  nl neFeedbackL stOfDayKnobs
      extends FSParam[Seq[Double]]("feedback_ nl ne_fn_day_knobs", default = Seq.empty[Double])

  object  nl neFeedbackL stOfPushCap  ghtKnobs
      extends FSParam[Seq[Double]]("feedback_ nl ne_fn_pushcap_knobs", default = Seq.empty[Double])

  object  nl neFeedbackL stOfPo rKnobs
      extends FSParam[Seq[Double]]("feedback_ nl ne_fn_po r_knobs", default = Seq.empty[Double])

  object  nl neFeedbackF1Tr ggerF1PushCap  ght
      extends FSBoundedParam[Double](
        "feedback_ nl ne_fn_f1_tr gger_f1_  ght",
        default = 1.0,
        m n = 0.0,
        max = 10000000.0)

  object  nl neFeedbackF1Tr ggerNonF1PushCap  ght
      extends FSBoundedParam[Double](
        "feedback_ nl ne_fn_f1_tr gger_nonf1_  ght",
        default = 1.0,
        m n = 0.0,
        max = 10000000.0)

  object  nl neFeedbackNonF1Tr ggerF1PushCap  ght
      extends FSBoundedParam[Double](
        "feedback_ nl ne_fn_nonf1_tr gger_f1_  ght",
        default = 1.0,
        m n = 0.0,
        max = 10000000.0)

  object  nl neFeedbackNonF1Tr ggerNonF1PushCap  ght
      extends FSBoundedParam[Double](
        "feedback_ nl ne_fn_nonf1_tr gger_nonf1_  ght",
        default = 1.0,
        m n = 0.0,
        max = 10000000.0)

  // Params for prompt feedback
  object EnableContFnF1Tr ggerPromptFeedbackFat gue
      extends FSParam[Boolean](
        na  = "feedback_prompt_fn_enable_f1_tr gger_fat gue",
        default = false
      )

  object EnableContFnNonF1Tr ggerPromptFeedbackFat gue
      extends FSParam[Boolean](
        na  = "feedback_prompt_fn_enable_nonf1_tr gger_fat gue",
        default = false
      )
  object PromptFeedbackL stOfDayKnobs
      extends FSParam[Seq[Double]]("feedback_prompt_fn_day_knobs", default = Seq.empty[Double])

  object PromptFeedbackL stOfPushCap  ghtKnobs
      extends FSParam[Seq[Double]]("feedback_prompt_fn_pushcap_knobs", default = Seq.empty[Double])

  object PromptFeedbackL stOfPo rKnobs
      extends FSParam[Seq[Double]]("feedback_prompt_fn_po r_knobs", default = Seq.empty[Double])

  object PromptFeedbackF1Tr ggerF1PushCap  ght
      extends FSBoundedParam[Double](
        "feedback_prompt_fn_f1_tr gger_f1_  ght",
        default = 1.0,
        m n = 0.0,
        max = 10000000.0)

  object PromptFeedbackF1Tr ggerNonF1PushCap  ght
      extends FSBoundedParam[Double](
        "feedback_prompt_fn_f1_tr gger_nonf1_  ght",
        default = 1.0,
        m n = 0.0,
        max = 10000000.0)

  object PromptFeedbackNonF1Tr ggerF1PushCap  ght
      extends FSBoundedParam[Double](
        "feedback_prompt_fn_nonf1_tr gger_f1_  ght",
        default = 1.0,
        m n = 0.0,
        max = 10000000.0)

  object PromptFeedbackNonF1Tr ggerNonF1PushCap  ght
      extends FSBoundedParam[Double](
        "feedback_prompt_fn_nonf1_tr gger_nonf1_  ght",
        default = 1.0,
        m n = 0.0,
        max = 10000000.0)

  /*
   * Param to enable cohost jo n event not f
   */
  object EnableSpaceCohostJo nEvent
      extends FSParam[Boolean](na  = "space_recs_cohost_jo n_enable", default = true)

  /*
   * Param to bypass global push cap w n target  s dev ce follow ng host/speaker.
   */
  object BypassGlobalSpacePushCapForSoftDev ceFollow
      extends FSParam[Boolean](na  = "space_recs_bypass_global_pushcap_for_soft_follow", false)

  /*
   * Param to bypass act ve l stener pred cate w n target  s dev ce follow ng host/speaker.
   */
  object C ckAct veL stenerPred cateForSoftDev ceFollow
      extends FSParam[Boolean](na  = "space_recs_c ck_act ve_l stener_for_soft_follow", false)

  object SpreadControlRat oParam
      extends FSBoundedParam[Double](
        na  = "oon_spread_control_rat o",
        default = 1000.0,
        m n = 0.0,
        max = 100000.0
      )

  object FavOverSendThresholdParam
      extends FSBoundedParam[Double](
        na  = "oon_spread_control_fav_over_send_threshold",
        default = 0.14,
        m n = 0.0,
        max = 1000.0
      )

  object AuthorReportRateThresholdParam
      extends FSBoundedParam[Double](
        na  = "oon_spread_control_author_report_rate_threshold",
        default = 7.4e-6,
        m n = 0.0,
        max = 1000.0
      )

  object AuthorD sl keRateThresholdParam
      extends FSBoundedParam[Double](
        na  = "oon_spread_control_author_d sl ke_rate_threshold",
        default = 1.0,
        m n = 0.0,
        max = 1000.0
      )

  object M nT etSendsThresholdParam
      extends FSBoundedParam[Double](
        na  = "oon_spread_control_m n_t et_sends_threshold",
        default = 10000000000.0,
        m n = 0.0,
        max = 10000000000.0
      )

  object M nAuthorSendsThresholdParam
      extends FSBoundedParam[Double](
        na  = "oon_spread_control_m n_author_sends_threshold",
        default = 10000000000.0,
        m n = 0.0,
        max = 10000000000.0
      )

  /*
   * T et Ntab-d sl ke pred cate related params
   */
  object T etNtabD sl keCountThresholdParam
      extends FSBoundedParam[Double](
        na  = "oon_t et_ntab_d sl ke_count_threshold",
        default = 10000.0,
        m n = 0.0,
        max = 10000.0
      )
  object T etNtabD sl keRateThresholdParam
      extends FSBoundedParam[Double](
        na  = "oon_t et_ntab_d sl ke_rate_threshold",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Param for t et language feature na 
   */
  object T etLanguageFeatureNa Param
      extends FSParam[Str ng](
        na  = "language_t et_language_feature_na ",
        default = "t et.language.t et. dent f ed")

  /**
   * Threshold for user  nferred language f lter ng
   */
  object User nferredLanguageThresholdParam
      extends FSBoundedParam[Double](
        na  = "language_user_ nferred_language_threshold",
        default = 0.0,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Threshold for user dev ce language f lter ng
   */
  object UserDev ceLanguageThresholdParam
      extends FSBoundedParam[Double](
        na  = "language_user_dev ce_language_threshold",
        default = 0.0,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Param to enable/d sable t et language f lter
   */
  object EnableT etLanguageF lter
      extends FSParam[Boolean](
        na  = "language_enable_t et_language_f lter",
        default = false
      )

  /**
   * Param to sk p language f lter for  d a t ets
   */
  object Sk pLanguageF lterFor d aT ets
      extends FSParam[Boolean](
        na  = "language_sk p_language_f lter_for_ d a_t ets",
        default = false
      )

  /*
   * T et Ntab-d sl ke pred cate related params for MrTw stly
   */
  object T etNtabD sl keCountThresholdForMrTw stlyParam
      extends FSBoundedParam[Double](
        na  = "oon_t et_ntab_d sl ke_count_threshold_for_mrtw stly",
        default = 10000.0,
        m n = 0.0,
        max = 10000.0
      )
  object T etNtabD sl keRateThresholdForMrTw stlyParam
      extends FSBoundedParam[Double](
        na  = "oon_t et_ntab_d sl ke_rate_threshold_for_mrtw stly",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )

  object T etNtabD sl keCountBucketThresholdParam
      extends FSBoundedParam[Double](
        na  = "oon_t et_ntab_d sl ke_count_bucket_threshold",
        default = 10.0,
        m n = 0.0,
        max = 10000.0
      )

  /*
   * T et engage nt rat o pred cate related params
   */
  object T etQTtoNtabCl ckRat oThresholdParam
      extends FSBoundedParam[Double](
        na  = "oon_t et_engage nt_f lter_qt_to_ntabcl ck_rat o_threshold",
        default = 0.0,
        m n = 0.0,
        max = 100000.0
      )

  /**
   * Lo r bound threshold to f lter a t et based on  s reply to l ke rat o
   */
  object T etReplytoL keRat oThresholdLo rBound
      extends FSBoundedParam[Double](
        na  = "oon_t et_engage nt_f lter_reply_to_l ke_rat o_threshold_lo r_bound",
        default = Double.MaxValue,
        m n = 0.0,
        max = Double.MaxValue
      )

  /**
   * Upper bound threshold to f lter a t et based on  s reply to l ke rat o
   */
  object T etReplytoL keRat oThresholdUpperBound
      extends FSBoundedParam[Double](
        na  = "oon_t et_engage nt_f lter_reply_to_l ke_rat o_threshold_upper_bound",
        default = 0.0,
        m n = 0.0,
        max = Double.MaxValue
      )

  /**
   * Upper bound threshold to f lter a t et based on  s reply to l ke rat o
   */
  object T etReplytoL keRat oReplyCountThreshold
      extends FSBoundedParam[ nt](
        na  = "oon_t et_engage nt_f lter_reply_count_threshold",
        default =  nt.MaxValue,
        m n = 0,
        max =  nt.MaxValue
      )

  /*
   * oonT etLengthBasedPrerank ngPred cate related params
   */
  object OonT etLengthPred cateUpdated d aLog c
      extends FSParam[Boolean](
        na  = "oon_qual y_f lter_t et_length_updated_ d a_log c",
        default = false
      )

  object OonT etLengthPred cateUpdatedQuoteT etLog c
      extends FSParam[Boolean](
        na  = "oon_qual y_f lter_t et_length_updated_quote_t et_log c",
        default = false
      )

  object OonT etLengthPred cateMoreStr ctForUndef nedLanguages
      extends FSParam[Boolean](
        na  = "oon_qual y_f lter_t et_length_more_str ct_for_undef ned_languages",
        default = false
      )

  object EnablePrerank ngT etLengthPred cate
      extends FSParam[Boolean](
        na  = "oon_qual y_f lter_enable_prerank ng_f lter",
        default = false
      )

  /*
   * LengthLanguageBasedOONT etCand datesQual yPred cate related params
   */
  object SautOonW h d aT etLengthThresholdParam
      extends FSBoundedParam[Double](
        na  = "oon_qual y_f lter_t et_length_threshold_for_saut_oon_w h_ d a",
        default = 0.0,
        m n = 0.0,
        max = 70.0
      )
  object NonSautOonW h d aT etLengthThresholdParam
      extends FSBoundedParam[Double](
        na  = "oon_qual y_f lter_t et_length_threshold_for_non_saut_oon_w h_ d a",
        default = 0.0,
        m n = 0.0,
        max = 70.0
      )
  object SautOonW hout d aT etLengthThresholdParam
      extends FSBoundedParam[Double](
        na  = "oon_qual y_f lter_t et_length_threshold_for_saut_oon_w hout_ d a",
        default = 0.0,
        m n = 0.0,
        max = 70.0
      )
  object NonSautOonW hout d aT etLengthThresholdParam
      extends FSBoundedParam[Double](
        na  = "oon_qual y_f lter_t et_length_threshold_for_non_saut_oon_w hout_ d a",
        default = 0.0,
        m n = 0.0,
        max = 70.0
      )

  object ArgfOonW h d aT etWordLengthThresholdParam
      extends FSBoundedParam[Double](
        na  = "oon_qual y_f lter_t et_word_length_threshold_for_argf_oon_w h_ d a",
        default = 0.0,
        m n = 0.0,
        max = 18.0
      )
  object EsfthOonW h d aT etWordLengthThresholdParam
      extends FSBoundedParam[Double](
        na  = "oon_qual y_f lter_t et_word_length_threshold_for_esfth_oon_w h_ d a",
        default = 0.0,
        m n = 0.0,
        max = 10.0
      )

  /**
   * Param to enable/d sable sent  nt feature hydrat on
   */
  object EnableMrT etSent  ntFeatureHydrat onFS
      extends FSParam[Boolean](
        na  = "feature_hydrat on_enable_mr_t et_sent  nt_feature",
        default = false
      )

  /**
   * Param to enable/d sable feature map scr b ng for stag ng test log
   */
  object EnableMrScr b ngMLFeaturesAsFeatureMapForStag ng
      extends FSParam[Boolean](
        na  = "fr gate_pushserv ce_enable_scr b ng_ml_features_as_featuremap_for_stag ng",
        default = false
      )

  /**
   * Param to enable t  l ne  alth s gnal hydrat on
   * */
  object EnableT  l ne althS gnalHydrat on
      extends FSParam[Boolean](
        na  = "t  l ne_ alth_s gnal_hydrat on",
        default = false
      )

  /**
   * Param to enable t  l ne  alth s gnal hydrat on for model tra n ng
   * */
  object EnableT  l ne althS gnalHydrat onForModelTra n ng
      extends FSParam[Boolean](
        na  = "t  l ne_ alth_s gnal_hydrat on_for_model_tra n ng",
        default = false
      )

  /**
   * Param to enable/d sable mr user soc al context agg feature hydrat on
   */
  object EnableMrUserSoc alContextAggregateFeatureHydrat on
      extends FSParam[Boolean](
        na  = "fr gate_push_model ng_hydrate_mr_user_soc al_context_agg_feature",
        default = true
      )

  /**
   * Param to enable/d sable mr user semant c core agg feature hydrat on
   */
  object EnableMrUserSemant cCoreAggregateFeatureHydrat on
      extends FSParam[Boolean](
        na  = "fr gate_push_model ng_hydrate_mr_user_semant c_core_agg_feature",
        default = true
      )

  /**
   * Param to enable/d sable mr user cand date sparse agg feature hydrat on
   */
  object EnableMrUserCand dateSparseOffl neAggregateFeatureHydrat on
      extends FSParam[Boolean](
        na  = "fr gate_push_model ng_hydrate_mr_user_cand date_sparse_agg_feature",
        default = true
      )

  /**
   * Param to enable/d sable mr user cand date agg feature hydrat on
   */
  object EnableMrUserCand dateOffl neAggregateFeatureHydrat on
      extends FSParam[Boolean](
        na  = "fr gate_push_model ng_hydrate_mr_user_cand date_agg_feature",
        default = true
      )

  /**
   * Param to enable/d sable mr user cand date compact agg feature hydrat on
   */
  object EnableMrUserCand dateOffl neCompactAggregateFeatureHydrat on
      extends FSParam[Boolean](
        na  = "fr gate_push_model ng_hydrate_mr_user_cand date_compact_agg_feature",
        default = false
      )

  /**
   * Param to enable/d sable mr real graph user-author/soc al-context feature hydrat on
   */
  object EnableRealGraphUserAuthorAndSoc alContxtFeatureHydrat on
      extends FSParam[Boolean](
        na  = "fr gate_push_model ng_hydrate_real_graph_user_soc al_feature",
        default = true
      )

  /**
   * Param to enable/d sable mr user author agg feature hydrat on
   */
  object EnableMrUserAuthorOffl neAggregateFeatureHydrat on
      extends FSParam[Boolean](
        na  = "fr gate_push_model ng_hydrate_mr_user_author_agg_feature",
        default = true
      )

  /**
   * Param to enable/d sable mr user author compact agg feature hydrat on
   */
  object EnableMrUserAuthorOffl neCompactAggregateFeatureHydrat on
      extends FSParam[Boolean](
        na  = "fr gate_push_model ng_hydrate_mr_user_author_compact_agg_feature",
        default = false
      )

  /**
   * Param to enable/d sable mr user compact agg feature hydrat on
   */
  object EnableMrUserOffl neCompactAggregateFeatureHydrat on
      extends FSParam[Boolean](
        na  = "fr gate_push_model ng_hydrate_mr_user_compact_agg_feature",
        default = false
      )

  /**
   * Param to enable/d sable mr user s mcluster agg feature hydrat on
   */
  object EnableMrUserS mcluster2020AggregateFeatureHydrat on
      extends FSParam[Boolean](
        na  = "fr gate_push_model ng_hydrate_mr_user_s mcluster_agg_feature",
        default = true
      )

  /**
   * Param to enable/d sable mr user agg feature hydrat on
   */
  object EnableMrUserOffl neAggregateFeatureHydrat on
      extends FSParam[Boolean](
        na  = "fr gate_push_model ng_hydrate_mr_user_agg_feature",
        default = true
      )

  /**
   * Param to enable/d sable top c engage nt RTA  n t  rank ng model
   */
  object EnableTop cEngage ntRealT  AggregatesFS
      extends FSParam[Boolean](
        "feature_hydrat on_enable_htl_top c_engage nt_real_t  _agg_feature",
        false)

  /*
   * Param to enable mr user semant c core feature hydrat on for  avy ranker
   * */
  object EnableMrUserSemant cCoreFeatureForExpt
      extends FSParam[Boolean](
        na  = "fr gate_push_model ng_hydrate_mr_user_semant c_core",
        default = false)

  /**
   * Param to enable hydrat ng user durat on s nce last v s  features
   */
  object EnableHydrat ngUserDurat onS nceLastV s Features
      extends FSParam[Boolean](
        na  = "feature_hydrat on_user_durat on_s nce_last_v s ",
        default = false)

  /**
    Param to enable/d sable user-top c aggregates  n t  rank ng model
   */
  object EnableUserTop cAggregatesFS
      extends FSParam[Boolean]("feature_hydrat on_enable_htl_top c_user_agg_feature", false)

  /*
   * PNegMult modalPred cate related params
   */
  object EnablePNegMult modalPred cateParam
      extends FSParam[Boolean](
        na  = "pneg_mult modal_f lter_enable_param",
        default = false
      )
  object PNegMult modalPred cateModelThresholdParam
      extends FSBoundedParam[Double](
        na  = "pneg_mult modal_f lter_model_threshold_param",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )
  object PNegMult modalPred cateBucketThresholdParam
      extends FSBoundedParam[Double](
        na  = "pneg_mult modal_f lter_bucket_threshold_param",
        default = 0.4,
        m n = 0.0,
        max = 1.0
      )

  /*
   * Negat veKeywordsPred cate related params
   */
  object EnableNegat veKeywordsPred cateParam
      extends FSParam[Boolean](
        na  = "negat ve_keywords_f lter_enable_param",
        default = false
      )
  object Negat veKeywordsPred cateDenyl st
      extends FSParam[Seq[Str ng]](
        na  = "negat ve_keywords_f lter_denyl st",
        default = Seq.empty[Str ng]
      )
  /*
   * L ghtRank ng related params
   */
  object EnableL ghtRank ngParam
      extends FSParam[Boolean](
        na  = "l ght_rank ng_enable_param",
        default = false
      )
  object L ghtRank ngNumberOfCand datesParam
      extends FSBoundedParam[ nt](
        na  = "l ght_rank ng_number_of_cand dates_param",
        default = 100,
        m n = 0,
        max = 1000
      )
  object L ghtRank ngModelTypeParam
      extends FSParam[Str ng](
        na  = "l ght_rank ng_model_type_param",
        default = "  ghtedOpenOrNtabCl ckProbab l y_Q4_2021_13172_Mr_L ght_Ranker_Dbv2_Top3")
  object EnableRandomBasel neL ghtRank ngParam
      extends FSParam[Boolean](
        na  = "l ght_rank ng_random_basel ne_enable_param",
        default = false
      )

  object L ghtRank ngScr beCand datesDownSampl ngParam
      extends FSBoundedParam[Double](
        na  = "l ght_rank ng_scr be_cand dates_down_sampl ng_param",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )

  /*
   * Qual y Uprank ng related params
   */
  object EnableProducersQual yBoost ngFor avyRank ngParam
      extends FSParam[Boolean](
        na  = "qual y_uprank ng_enable_producers_qual y_boost ng_for_ avy_rank ng_param",
        default = false
      )

  object Qual yUprank ngBoostForH ghQual yProducersParam
      extends FSBoundedParam[Double](
        na  = "qual y_uprank ng_boost_for_h gh_qual y_producers_param",
        default = 1.0,
        m n = 0.0,
        max = 10000.0
      )

  object Qual yUprank ngDownboostForLowQual yProducersParam
      extends FSBoundedParam[Double](
        na  = "qual y_uprank ng_downboost_for_low_qual y_producers_param",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )

  object EnableQual yUprank ngFor avyRank ngParam
      extends FSParam[Boolean](
        na  = "qual y_uprank ng_enable_for_ avy_rank ng_param",
        default = false
      )
  object Qual yUprank ngModelTypeParam
      extends FSParam[  ghtedOpenOrNtabCl ckModel.ModelNa Type](
        na  = "qual y_uprank ng_model_ d",
        default = "Q4_2022_Mr_Bqml_Qual y_Model_wALL"
      )
  object Qual yUprank ngTransformTypeParam
      extends FSEnumParam[MrQual yUprank ngTransformTypeEnum.type](
        na  = "qual y_uprank ng_transform_ d",
        default = MrQual yUprank ngTransformTypeEnum.S gmo d,
        enum = MrQual yUprank ngTransformTypeEnum
      )

  object Qual yUprank ngBoostFor avyRank ngParam
      extends FSBoundedParam[Double](
        na  = "qual y_uprank ng_boost_for_ avy_rank ng_param",
        default = 1.0,
        m n = -10.0,
        max = 10.0
      )
  object Qual yUprank ngS gmo dB asFor avyRank ngParam
      extends FSBoundedParam[Double](
        na  = "qual y_uprank ng_s gmo d_b as_for_ avy_rank ng_param",
        default = 0.0,
        m n = -10.0,
        max = 10.0
      )
  object Qual yUprank ngS gmo d  ghtFor avyRank ngParam
      extends FSBoundedParam[Double](
        na  = "qual y_uprank ng_s gmo d_  ght_for_ avy_rank ng_param",
        default = 1.0,
        m n = -10.0,
        max = 10.0
      )
  object Qual yUprank ngL nearBarFor avyRank ngParam
      extends FSBoundedParam[Double](
        na  = "qual y_uprank ng_l near_bar_for_ avy_rank ng_param",
        default = 1.0,
        m n = 0.0,
        max = 10.0
      )
  object EnableQual yUprank ngCrtScoreStatsFor avyRank ngParam
      extends FSParam[Boolean](
        na  = "qual y_uprank ng_enable_crt_score_stats_for_ avy_rank ng_param",
        default = false
      )
  /*
   * BQML  alth Model related params
   */
  object EnableBqml althModelPred cateParam
      extends FSParam[Boolean](
        na  = "bqml_ alth_model_f lter_enable_param",
        default = false
      )

  object EnableBqml althModelPred ct onFor nNetworkCand datesParam
      extends FSParam[Boolean](
        na  = "bqml_ alth_model_enable_pred ct on_for_ n_network_cand dates_param",
        default = false
      )

  object Bqml althModelTypeParam
      extends FSParam[ althNsfwModel.ModelNa Type](
        na  = "bqml_ alth_model_ d",
        default =  althNsfwModel.Q2_2022_Mr_Bqml_ alth_Model_NsfwV0
      )
  object Bqml althModelPred cateF lterThresholdParam
      extends FSBoundedParam[Double](
        na  = "bqml_ alth_model_f lter_threshold_param",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )
  object Bqml althModelPred cateBucketThresholdParam
      extends FSBoundedParam[Double](
        na  = "bqml_ alth_model_bucket_threshold_param",
        default = 0.005,
        m n = 0.0,
        max = 1.0
      )

  object EnableBqml althModelScore togramParam
      extends FSParam[Boolean](
        na  = "bqml_ alth_model_score_ togram_enable_param",
        default = false
      )

  /*
   * BQML Qual y Model related params
   */
  object EnableBqmlQual yModelPred cateParam
      extends FSParam[Boolean](
        na  = "bqml_qual y_model_f lter_enable_param",
        default = false
      )
  object EnableBqmlQual yModelScore togramParam
      extends FSParam[Boolean](
        na  = "bqml_qual y_model_score_ togram_enable_param",
        default = false
      )
  object BqmlQual yModelTypeParam
      extends FSParam[  ghtedOpenOrNtabCl ckModel.ModelNa Type](
        na  = "bqml_qual y_model_ d",
        default = "Q1_2022_13562_Mr_Bqml_Qual y_Model_V2"
      )

  /**
   * Param to spec fy wh ch qual y models to use to get t  scores for determ n ng
   * w t r to bucket a user for t  DDG
   */
  object BqmlQual yModelBucketModel dL stParam
      extends FSParam[Seq[  ghtedOpenOrNtabCl ckModel.ModelNa Type]](
        na  = "bqml_qual y_model_bucket_model_ d_l st",
        default = Seq(
          "Q1_2022_13562_Mr_Bqml_Qual y_Model_V2",
          "Q2_2022_DDG14146_Mr_Personal sed_BQML_Qual y_Model",
          "Q2_2022_DDG14146_Mr_NonPersonal sed_BQML_Qual y_Model"
        )
      )

  object BqmlQual yModelPred cateThresholdParam
      extends FSBoundedParam[Double](
        na  = "bqml_qual y_model_f lter_threshold_param",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Param to spec fy t  threshold to determ ne  f a user’s qual y score  s h gh enough to enter t  exper  nt.
   */
  object BqmlQual yModelBucketThresholdL stParam
      extends FSParam[Seq[Double]](
        na  = "bqml_qual y_model_bucket_threshold_l st",
        default = Seq(0.7, 0.7, 0.7)
      )

  /*
   * T etAuthorAggregates related params
   */
  object EnableT etAuthorAggregatesFeatureHydrat onParam
      extends FSParam[Boolean](
        na  = "t et_author_aggregates_feature_hydrat on_enable_param",
        default = false
      )

  /**
   * Param to determ ne  f   should  nclude t  relevancy score of cand dates  n t   b s payload
   */
  object  ncludeRelevanceScore n b s2Payload
      extends FSParam[Boolean](
        na  = "relevance_score_ nclude_ n_ b s2_payload",
        default = false
      )

  /**
   *  Param to spec fy superv sed model to pred ct score by send ng t  not f cat on
   */
  object B gF lter ngSuperv sedSend ngModelParam
      extends FSParam[B gF lter ngSuperv sedModel.ModelNa Type](
        na  = "ltv_f lter ng_b gf lter ng_superv sed_send ng_model_param",
        default = B gF lter ngSuperv sedModel.V0_0_B gF lter ng_Superv sed_Send ng_Model
      )

  /**
   *  Param to spec fy superv sed model to pred ct score by not send ng t  not f cat on
   */
  object B gF lter ngSuperv sedW houtSend ngModelParam
      extends FSParam[B gF lter ngSuperv sedModel.ModelNa Type](
        na  = "ltv_f lter ng_b gf lter ng_superv sed_w hout_send ng_model_param",
        default = B gF lter ngSuperv sedModel.V0_0_B gF lter ng_Superv sed_W hout_Send ng_Model
      )

  /**
   *  Param to spec fy RL model to pred ct score by send ng t  not f cat on
   */
  object B gF lter ngRLSend ngModelParam
      extends FSParam[B gF lter ngSuperv sedModel.ModelNa Type](
        na  = "ltv_f lter ng_b gf lter ng_rl_send ng_model_param",
        default = B gF lter ngRLModel.V0_0_B gF lter ng_Rl_Send ng_Model
      )

  /**
   *  Param to spec fy RL model to pred ct score by not send ng t  not f cat on
   */
  object B gF lter ngRLW houtSend ngModelParam
      extends FSParam[B gF lter ngSuperv sedModel.ModelNa Type](
        na  = "ltv_f lter ng_b gf lter ng_rl_w hout_send ng_model_param",
        default = B gF lter ngRLModel.V0_0_B gF lter ng_Rl_W hout_Send ng_Model
      )

  /**
   *  Param to spec fy t  threshold (send not f cat on  f score >= threshold)
   */
  object B gF lter ngThresholdParam
      extends FSBoundedParam[Double](
        na  = "ltv_f lter ng_b gf lter ng_threshold_param",
        default = 0.0,
        m n = Double.M nValue,
        max = Double.MaxValue
      )

  /**
   *  Param to spec fy normal zat on used for B gF lter ng
   */
  object B gF lter ngNormal zat onType dParam
      extends FSEnumParam[B gF lter ngNormal zat onEnum.type](
        na  = "ltv_f lter ng_b gf lter ng_normal zat on_type_ d",
        default = B gF lter ngNormal zat onEnum.Normal zat onD sabled,
        enum = B gF lter ngNormal zat onEnum
      )

  /**
   *  Param to spec fy  tograms of model scores  n B gF lter ng
   */
  object B gF lter ngEnable togramsParam
      extends FSParam[Boolean](
        na  = "ltv_f lter ng_b gf lter ng_enable_ tograms_param",
        default = false
      )

  /*
   * Param to enable send ng requests to  ns Sender
   */
  object Enable nsSender extends FSParam[Boolean](na  = " ns_enable_dark_traff c", default = false)

  /**
   * Param to spec fy t  range of relevance scores for Mag cFanout types.
   */
  object Mag cFanoutRelevanceScoreRange
      extends FSParam[Seq[Double]](
        na  = "relevance_score_mf_range",
        default = Seq(0.75, 1.0)
      )

  /**
   * Param to spec fy t  range of relevance scores for MR types.
   */
  object Mag cRecsRelevanceScoreRange
      extends FSParam[Seq[Double]](
        na  = "relevance_score_mr_range",
        default = Seq(0.25, 0.5)
      )

  /**
   * Param to enable backf ll ng OON cand dates  f number of F1 cand dates  s greater than a threshold K.
   */
  object EnableOONBackf llBasedOnF1Cand dates
      extends FSParam[Boolean](na  = "oon_enable_backf ll_based_on_f1", default = false)

  /**
   * Threshold for t  m n mum number of F1 cand dates requ red to enable backf ll of OON cand dates.
   */
  object NumberOfF1Cand datesThresholdForOONBackf ll
      extends FSBoundedParam[ nt](
        na  = "oon_enable_backf ll_f1_threshold",
        m n = 0,
        default = 5000,
        max = 5000)

  /**
   * Event  D allowl st to sk p account country pred cate
   */
  object Mag cFanoutEventAllowl stToSk pAccountCountryPred cate
      extends FSParam[Seq[Long]](
        na  = "mag cfanout_event_allowl st_sk p_account_country_pred cate",
        default = Seq.empty[Long]
      )

  /**
   * Mag cFanout Event Semant c Core Doma n  ds
   */
  object L stOfEventSemant cCoreDoma n ds
      extends FSParam[Seq[Long]](
        na  = "mag cfanout_automated_events_semant c_core_doma n_ ds",
        default = Seq())

  /**
   * Adhoc  d for deta led rank flow stats
   */
  object L stOfAdhoc dsForStatsTrack ng
      extends FSParam[Set[Long]](
        na  = "stats_enable_deta led_stats_track ng_ ds",
        default = Set.empty[Long]
      )

  object EnableGener cCRTBasedFat guePred cate
      extends FSParam[Boolean](
        na  = "seelessoften_enable_gener c_crt_based_fat gue_pred cate",
        default = false)

  /**
   * Param to enable copy features such as Emoj s and Target Na 
   */
  object EnableCopyFeaturesForF1
      extends FSParam[Boolean](na  = "mr_copy_enable_features_f1", default = false)

  /**
   * Param to enable copy features such as Emoj s and Target Na 
   */
  object EnableCopyFeaturesForOon
      extends FSParam[Boolean](na  = "mr_copy_enable_features_oon", default = false)

  /**
   * Param to enable Emoj   n F1 Copy
   */
  object EnableEmoj  nF1Copy
      extends FSParam[Boolean](na  = "mr_copy_enable_f1_emoj ", default = false)

  /**
   * Param to enable Target  n F1 Copy
   */
  object EnableTarget nF1Copy
      extends FSParam[Boolean](na  = "mr_copy_enable_f1_target", default = false)

  /**
   * Param to enable Emoj   n OON Copy
   */
  object EnableEmoj  nOonCopy
      extends FSParam[Boolean](na  = "mr_copy_enable_oon_emoj ", default = false)

  /**
   * Param to enable Target  n OON Copy
   */
  object EnableTarget nOonCopy
      extends FSParam[Boolean](na  = "mr_copy_enable_oon_target", default = false)

  /**
   * Param to enable spl  fat gue for Target and Emoj  copy for OON and F1
   */
  object EnableTargetAndEmoj Spl Fat gue
      extends FSParam[Boolean](na  = "mr_copy_enable_target_emoj _spl _fat gue", default = false)

  /**
   * Param to enable exper  nt ng str ng on t  body
   */
  object EnableF1CopyBody extends FSParam[Boolean](na  = "mr_copy_f1_enable_body", default = false)

  object EnableOONCopyBody
      extends FSParam[Boolean](na  = "mr_copy_oon_enable_body", default = false)

  object Enable osCopyBodyTruncate
      extends FSParam[Boolean](na  = "mr_copy_enable_body_truncate", default = false)

  object EnableNsfwCopy extends FSParam[Boolean](na  = "mr_copy_enable_nsfw", default = false)

  /**
   * Param to determ ne F1 cand date nsfw score threshold
   */
  object NsfwScoreThresholdForF1Copy
      extends FSBoundedParam[Double](
        na  = "mr_copy_nsfw_threshold_f1",
        default = 0.3,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Param to determ ne OON cand date nsfw score threshold
   */
  object NsfwScoreThresholdForOONCopy
      extends FSBoundedParam[Double](
        na  = "mr_copy_nsfw_threshold_oon",
        default = 0.2,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Param to determ ne t  lookback durat on w n search ng for prev copy features.
   */
  object CopyFeatures toryLookbackDurat on
      extends FSBoundedParam[Durat on](
        na  = "mr_copy_ tory_lookback_durat on_ n_days",
        default = 30.days,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  /**
   * Param to determ ne t  F1 emoj  copy fat gue  n # of h s.
   */
  object F1Emoj CopyFat gueDurat on
      extends FSBoundedParam[Durat on](
        na  = "mr_copy_f1_emoj _copy_fat gue_ n_h s",
        default = 24.h s,
        m n = 0.h s,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   * Param to determ ne t  F1 target copy fat gue  n # of h s.
   */
  object F1TargetCopyFat gueDurat on
      extends FSBoundedParam[Durat on](
        na  = "mr_copy_f1_target_copy_fat gue_ n_h s",
        default = 24.h s,
        m n = 0.h s,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   * Param to determ ne t  OON emoj  copy fat gue  n # of h s.
   */
  object OonEmoj CopyFat gueDurat on
      extends FSBoundedParam[Durat on](
        na  = "mr_copy_oon_emoj _copy_fat gue_ n_h s",
        default = 24.h s,
        m n = 0.h s,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   * Param to determ ne t  OON target copy fat gue  n # of h s.
   */
  object OonTargetCopyFat gueDurat on
      extends FSBoundedParam[Durat on](
        na  = "mr_copy_oon_target_copy_fat gue_ n_h s",
        default = 24.h s,
        m n = 0.h s,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   * Param to turn on/off ho  t  l ne based fat gue rule, w re once last ho  t  l ne v s 
   *  s larger than t  spec f ed w ll evalute to not fat gue
   */
  object EnableHTLBasedFat gueBas cRule
      extends FSParam[Boolean](
        na  = "mr_copy_enable_htl_based_fat gue_bas c_rule",
        default = false)

  /**
   * Param to determ ne f1 emoj  copy fat gue  n # of pus s
   */
  object F1Emoj CopyNumOfPus sFat gue
      extends FSBoundedParam[ nt](
        na  = "mr_copy_f1_emoj _copy_number_of_pus s_fat gue",
        default = 0,
        m n = 0,
        max = 200
      )

  /**
   * Param to determ ne oon emoj  copy fat gue  n # of pus s
   */
  object OonEmoj CopyNumOfPus sFat gue
      extends FSBoundedParam[ nt](
        na  = "mr_copy_oon_emoj _copy_number_of_pus s_fat gue",
        default = 0,
        m n = 0,
        max = 200
      )

  /**
   *  f user haven't v s ed ho  t  l ne for certa n durat on,   w ll
   * exempt user from feature copy fat gue. T  param  s used to control
   * how long    s before   enter exempt on.
   */
  object M nFat gueDurat onS nceLastHTLV s 
      extends FSBoundedParam[Durat on](
        na  = "mr_copy_m n_durat on_s nce_last_htl_v s _h s",
        default = Durat on.Top,
        m n = 0.h ,
        max = Durat on.Top,
      )
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   *  f a user haven't v s  ho  t  l ne very long, t  user w ll return
   * to fat gue state under t  ho  t  l ne based fat gue rule. T re w ll
   * only be a w ndow, w re t  user  s out of fat gue state under t  rule.
   * T  param control t  length of t  non fat gue per od.
   */
  object LastHTLV s BasedNonFat gueW ndow
      extends FSBoundedParam[Durat on](
        na  = "mr_copy_last_htl_v s _based_non_fat gue_w ndow_h s",
        default = 48.h s,
        m n = 0.h ,
        max = Durat on.Top,
      )
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  object EnableOONCBasedCopy
      extends FSParam[Boolean](
        na  = "mr_copy_enable_oonc_based_copy",
        default = false
      )

  object H ghOONCThresholdForCopy
      extends FSBoundedParam[Double](
        na  = "mr_copy_h gh_oonc_threshold_for_copy",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )

  object LowOONCThresholdForCopy
      extends FSBoundedParam[Double](
        na  = "mr_copy_low_oonc_threshold_for_copy",
        default = 0.0,
        m n = 0.0,
        max = 1.0
      )

  object EnableT etTranslat on
      extends FSParam[Boolean](na  = "t et_translat on_enable", default = false)

  object Tr pT etCand dateReturnEnable
      extends FSParam[Boolean](na  = "tr p_t et_cand date_enable", default = false)

  object Tr pT etCand dateS ce ds
      extends FSParam[Seq[Str ng]](
        na  = "tr p_t et_cand date_s ce_ ds",
        default = Seq("TOP_GEO_V3"))

  object Tr pT etMaxTotalCand dates
      extends FSBoundedParam[ nt](
        na  = "tr p_t et_max_total_cand dates",
        default = 500,
        m n = 10,
        max = 1000)

  object EnableEmptyBody
      extends FSParam[Boolean](na  = "push_presentat on_enable_empty_body", default = false)

  object EnableSoc alContextForRet et
      extends FSParam[Boolean](na  = "push_presentat on_soc al_context_ret et", default = false)

  /**
   * Param to enable/d sable s mcluster feature hydrat on
   */
  object EnableMrT etS mClusterFeatureHydrat onFS
      extends FSParam[Boolean](
        na  = "feature_hydrat on_enable_mr_t et_s mcluster_feature",
        default = false
      )

  /**
   * Param to d sable OON cand dates based on t etAuthor
   */
  object D sableOutNetworkT etCand datesFS
      extends FSParam[Boolean](na  = "oon_f lter ng_d sable_oon_cand dates", default = false)

  /**
   * Param to enable Local V ral T ets
   */
  object EnableLocalV ralT ets
      extends FSParam[Boolean](na  = "local_v ral_t ets_enable", default = true)

  /**
   * Param to enable Explore V deo T ets
   */
  object EnableExploreV deoT ets
      extends FSParam[Boolean](na  = "explore_v deo_t ets_enable", default = false)

  /**
   * Param to enable L st Recom ndat ons
   */
  object EnableL stRecom ndat ons
      extends FSParam[Boolean](na  = "l st_recom ndat ons_enable", default = false)

  /**
   * Param to enable  DS L st Recom ndat ons
   */
  object Enable DSL stRecom ndat ons
      extends FSParam[Boolean](na  = "l st_recom ndat ons_ ds_enable", default = false)

  /**
   * Param to enable PopGeo L st Recom ndat ons
   */
  object EnablePopGeoL stRecom ndat ons
      extends FSParam[Boolean](na  = "l st_recom ndat ons_pop_geo_enable", default = false)

  /**
   * Param to control t   nverter for fat gue bet en consecut ve L stRecom ndat ons
   */
  object L stRecom ndat onsPush nterval
      extends FSBoundedParam[Durat on](
        na  = "l st_recom ndat ons_ nterval_days",
        default = 24.h s,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromDays
  }

  /**
   * Param to control t  granular y of GeoHash for L stRecom ndat ons
   */
  object L stRecom ndat onsGeoHashLength
      extends FSBoundedParam[ nt](
        na  = "l st_recom ndat ons_geo_hash_length",
        default = 5,
        m n = 3,
        max = 5)

  /**
   * Param to control max mum number of L stRecom ndat on pus s to rece ve  n an  nterval
   */
  object MaxL stRecom ndat onsPushG ven nterval
      extends FSBoundedParam[ nt](
        na  = "l st_recom ndat ons_push_g ven_ nterval",
        default = 1,
        m n = 0,
        max = 10
      )

  /**
   * Param to control t  subscr ber count for l st recom ndat on
   */
  object L stRecom ndat onsSubscr berCount
      extends FSBoundedParam[ nt](
        na  = "l st_recom ndat ons_subscr ber_count",
        default = 0,
        m n = 0,
        max =  nteger.MAX_VALUE)

  /**
   * Param to def ne dynam c  nl ne act on types for  b not f cat ons (both desktop  b + mob le  b)
   */
  object LocalV ralT etsBucket
      extends FSParam[Str ng](
        na  = "local_v ral_t ets_bucket",
        default = "h gh",
      )

  /**
   * L st of CrTags to d sable
   */
  object OONCand datesD sabledCrTagParam
      extends FSParam[Seq[Str ng]](
        na  = "oon_enable_oon_cand dates_d sabled_crtag",
        default = Seq.empty[Str ng]
      )

  /**
   * L st of Crt groups to d sable
   */
  object OONCand datesD sabledCrtGroupParam
      extends FSEnumSeqParam[CrtGroupEnum.type](
        na  = "oon_enable_oon_cand dates_d sabled_crt_group_ ds",
        default = Seq.empty[CrtGroupEnum.Value],
        enum = CrtGroupEnum
      )

  /**
   * Param to enable launch ng v deo t ets  n t   m rs ve Explore t  l ne
   */
  object EnableLaunchV deos n m rs veExplore
      extends FSParam[Boolean](na  = "launch_v deos_ n_ m rs ve_explore", default = false)

  /**
   * Param to enable Ntab Entr es for Sports Event Not f cat ons
   */
  object EnableNTabEntr esForSportsEventNot f cat ons
      extends FSParam[Boolean](
        na  = "mag cfanout_sports_event_enable_ntab_entr es",
        default = false)

  /**
   * Param to enable Ntab Facep les for teams  n Sport Not fs
   */
  object EnableNTabFaceP leForSportsEventNot f cat ons
      extends FSParam[Boolean](
        na  = "mag cfanout_sports_event_enable_ntab_facep les",
        default = false)

  /**
   * Param to enable Ntab Overr de for Sports Event Not f cat ons
   */
  object EnableNTabOverr deForSportsEventNot f cat ons
      extends FSParam[Boolean](
        na  = "mag cfanout_sports_event_enable_ntab_overr de",
        default = false)

  /**
   * Param to control t   nterval for MF Product Launch Not fs
   */
  object ProductLaunchPush nterval nH s
      extends FSBoundedParam[Durat on](
        na  = "product_launch_fat gue_push_ nterval_ n_h s",
        default = 24.h s,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   * Param to control t  max mum number of MF Product Launch Not fs  n a per od of t  
   */
  object ProductLaunchMaxNumberOfPus s n nterval
      extends FSBoundedParam[ nt](
        na  = "product_launch_fat gue_max_pus s_ n_ nterval",
        default = 1,
        m n = 0,
        max = 10)

  /**
   * Param to control t  m n nterval for fat gue bet en consecut ve MF Product Launch Not fs
   */
  object ProductLaunchM n ntervalFat gue
      extends FSBoundedParam[Durat on](
        na  = "product_launch_fat gue_m n_ nterval_consecut ve_pus s_ n_h s",
        default = 24.h s,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   * Param to control t   nterval for MF New Creator Not fs
   */
  object NewCreatorPush nterval nH s
      extends FSBoundedParam[Durat on](
        na  = "new_creator_fat gue_push_ nterval_ n_h s",
        default = 24.h s,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   * Param to control t  max mum number of MF New Creator Not fs  n a per od of t  
   */
  object NewCreatorPushMaxNumberOfPus s n nterval
      extends FSBoundedParam[ nt](
        na  = "new_creator_fat gue_max_pus s_ n_ nterval",
        default = 1,
        m n = 0,
        max = 10)

  /**
   * Param to control t  m n nterval for fat gue bet en consecut ve MF New Creator Not fs
   */
  object NewCreatorPushM n ntervalFat gue
      extends FSBoundedParam[Durat on](
        na  = "new_creator_fat gue_m n_ nterval_consecut ve_pus s_ n_h s",
        default = 24.h s,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   * Param to control t   nterval for MF New Creator Not fs
   */
  object CreatorSubscr pt onPush nterval nH s
      extends FSBoundedParam[Durat on](
        na  = "creator_subscr pt on_fat gue_push_ nterval_ n_h s",
        default = 24.h s,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   * Param to control t  max mum number of MF New Creator Not fs  n a per od of t  
   */
  object CreatorSubscr pt onPushMaxNumberOfPus s n nterval
      extends FSBoundedParam[ nt](
        na  = "creator_subscr pt on_fat gue_max_pus s_ n_ nterval",
        default = 1,
        m n = 0,
        max = 10)

  /**
   * Param to control t  m n nterval for fat gue bet en consecut ve MF New Creator Not fs
   */
  object CreatorSubscr pt onPushhM n ntervalFat gue
      extends FSBoundedParam[Durat on](
        na  = "creator_subscr pt on_fat gue_m n_ nterval_consecut ve_pus s_ n_h s",
        default = 24.h s,
        m n = Durat on.Bottom,
        max = Durat on.Top)
      w h HasDurat onConvers on {
    overr de val durat onConvers on = Durat onConvers on.FromH s
  }

  /**
   * Param to def ne t  land ng page deepl nk of product launch not f cat ons
   */
  object ProductLaunchLand ngPageDeepL nk
      extends FSParam[Str ng](
        na  = "product_launch_land ng_page_deepl nk",
        default = ""
      )

  /**
   * Param to def ne t  tap through of product launch not f cat ons
   */
  object ProductLaunchTapThrough
      extends FSParam[Str ng](
        na  = "product_launch_tap_through",
        default = ""
      )

  /**
   * Param to sk p c ck ng  sTargetBlueVer f ed
   */
  object D sable sTargetBlueVer f edPred cate
      extends FSParam[Boolean](
        na  = "product_launch_d sable_ s_target_blue_ver f ed_pred cate",
        default = false
      )

  /**
   * Param to enable Ntab Entr es for Sports Event Not f cat ons
   */
  object EnableNTabEntr esForProductLaunchNot f cat ons
      extends FSParam[Boolean](na  = "product_launch_enable_ntab_entry", default = true)

  /**
   * Param to sk p c ck ng  sTargetLegacyVer f ed
   */
  object D sable sTargetLegacyVer f edPred cate
      extends FSParam[Boolean](
        na  = "product_launch_d sable_ s_target_legacy_ver f ed_pred cate",
        default = false
      )

  /**
   * Param to enable c ck ng  sTargetSuperFollowCreator
   */
  object Enable sTargetSuperFollowCreatorPred cate
      extends FSParam[Boolean](
        na  = "product_launch_ s_target_super_follow_creator_pred cate_enabled",
        default = false
      )

  /**
   * Param to enable Spam  T et f lter
   */
  object EnableSpam T etF lter
      extends FSParam[Boolean](
        na  = " alth_s gnal_store_enable_spam _t et_f lter",
        default = false)

  /**
   * Param to enable Push to Ho  Andro d
   */
  object EnableT etPushToHo Andro d
      extends FSParam[Boolean](na  = "push_to_ho _t et_recs_andro d", default = false)

  /**
   * Param to enable Push to Ho   OS
   */
  object EnableT etPushToHo  OS
      extends FSParam[Boolean](na  = "push_to_ho _t et_recs_ OS", default = false)

  /**
   * Param to set Spam  T et score threshold for OON cand dates
   */
  object Spam T etOonThreshold
      extends FSBoundedParam[Double](
        na  = " alth_s gnal_store_spam _t et_oon_threshold",
        default = 1.1,
        m n = 0.0,
        max = 1.1
      )

  object NumFollo rThresholdFor althAndQual yF lters
      extends FSBoundedParam[Double](
        na  = " alth_s gnal_store_num_follo r_threshold_for_ alth_and_qual y_f lters",
        default = 10000000000.0,
        m n = 0.0,
        max = 10000000000.0
      )

  object NumFollo rThresholdFor althAndQual yF ltersPrerank ng
      extends FSBoundedParam[Double](
        na  =
          " alth_s gnal_store_num_follo r_threshold_for_ alth_and_qual y_f lters_prerank ng",
        default = 10000000.0,
        m n = 0.0,
        max = 10000000000.0
      )

  /**
   * Param to set Spam  T et score threshold for  N cand dates
   */
  object Spam T et nThreshold
      extends FSBoundedParam[Double](
        na  = " alth_s gnal_store_spam _t et_ n_threshold",
        default = 1.1,
        m n = 0.0,
        max = 1.1
      )

  /**
   * Param to control bucket ng for t  Spam  T et score
   */
  object Spam T etBucket ngThreshold
      extends FSBoundedParam[Double](
        na  = " alth_s gnal_store_spam _t et_bucket ng_threshold",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )

  /**
   * Param to spec fy t  max mum number of Explore V deo T ets to request
   */
  object MaxExploreV deoT ets
      extends FSBoundedParam[ nt](
        na  = "explore_v deo_t ets_max_cand dates",
        default = 100,
        m n = 0,
        max = 500
      )

  /**
   * Param to enable soc al context feature set
   */
  object EnableBoundedFeatureSetForSoc alContext
      extends FSParam[Boolean](
        na  = "feature_hydrat on_user_soc al_context_bounded_feature_set_enable",
        default = true)

  /**
   * Param to enable stp user soc al context feature set
   */
  object EnableStpBoundedFeatureSetForUserSoc alContext
      extends FSParam[Boolean](
        na  = "feature_hydrat on_stp_soc al_context_bounded_feature_set_enable",
        default = true)

  /**
   * Param to enable core user  tory soc al context feature set
   */
  object EnableCoreUser toryBoundedFeatureSetForSoc alContext
      extends FSParam[Boolean](
        na  = "feature_hydrat on_core_user_ tory_soc al_context_bounded_feature_set_enable",
        default = true)

  /**
   * Param to enable sk pp ng post-rank ng f lters
   */
  object Sk pPostRank ngF lters
      extends FSParam[Boolean](
        na  = "fr gate_push_model ng_sk p_post_rank ng_f lters",
        default = false)

  object Mag cFanoutS mClusterDotProductNon avyUserThreshold
      extends FSBoundedParam[Double](
        na  = "fr gate_push_mag cfanout_s mcluster_non_ avy_user_dot_product_threshold",
        default = 0.0,
        m n = 0.0,
        max = 100.0
      )

  object Mag cFanoutS mClusterDotProduct avyUserThreshold
      extends FSBoundedParam[Double](
        na  = "fr gate_push_mag cfanout_s mcluster_ avy_user_dot_product_threshold",
        default = 10.0,
        m n = 0.0,
        max = 100.0
      )

  object EnableReducedFat gueRulesForSeeLessOften
      extends FSParam[Boolean](
        na  = "seelessoften_enable_reduced_fat gue",
        default = false
      )
}
