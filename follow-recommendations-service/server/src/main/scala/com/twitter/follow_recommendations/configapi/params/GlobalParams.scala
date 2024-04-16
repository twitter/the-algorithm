package com.tw ter.follow_recom ndat ons.conf gap .params

 mport com.tw ter.follow_recom ndat ons.models.Cand dateS ceType
 mport com.tw ter.t  l nes.conf gap .FSEnumParam
 mport com.tw ter.t  l nes.conf gap .FSParam

/**
 * W n add ng Producer s de exper  nts, make sure to reg ster t  FS Key  n [[ProducerFeatureF lter]]
 *  n [[FeatureSw c sModule]], ot rw se, t  FS w ll not work.
 */
object GlobalParams {

  object EnableCand dateParamHydrat ons
      extends FSParam[Boolean]("frs_rece ver_enable_cand date_params", false)

  object KeepUserCand date
      extends FSParam[Boolean]("frs_rece ver_holdback_keep_user_cand date", true)

  object KeepSoc alUserCand date
      extends FSParam[Boolean]("frs_rece ver_holdback_keep_soc al_user_cand date", true)

  case object EnableGFSSoc alProofTransform
      extends FSParam("soc al_proof_transform_use_graph_feature_serv ce", true)

  case object EnableWhoToFollowProducts extends FSParam("who_to_follow_product_enabled", true)

  case object Cand dateS cesToF lter
      extends FSEnumParam[Cand dateS ceType.type](
        "cand date_s ces_type_f lter_ d",
        Cand dateS ceType.None,
        Cand dateS ceType)

  object EnableRecom ndat onFlowLogs
      extends FSParam[Boolean]("frs_recom ndat on_flow_logs_enabled", false)
}
