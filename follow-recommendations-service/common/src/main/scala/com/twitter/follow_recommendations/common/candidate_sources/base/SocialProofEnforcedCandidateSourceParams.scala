package com.tw ter.follow_recom ndat ons.common.cand date_s ces.base

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.t  l nes.conf gap .Durat onConvers on
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .HasDurat onConvers on
 mport com.tw ter.ut l.Durat on

object Soc alProofEnforcedCand dateS ceParams {
  case object MustCallSgs
      extends FSParam[Boolean]("soc al_proof_enforced_cand date_s ce_must_call_sgs", true)

  case object CallSgsCac dColumn
      extends FSParam[Boolean](
        "soc al_proof_enforced_cand date_s ce_call_sgs_cac d_column",
        false)

  case object Query ntersect on dsNum
      extends FSBoundedParam[ nt](
        na  = "soc al_proof_enforced_cand date_s ce_query_ ntersect on_ ds_num",
        default = 3,
        m n = 0,
        max =  nteger.MAX_VALUE)

  case object MaxNumCand datesToAnnotate
      extends FSBoundedParam[ nt](
        na  = "soc al_proof_enforced_cand date_s ce_max_num_cand dates_to_annotate",
        default = 50,
        m n = 0,
        max =  nteger.MAX_VALUE)

  case object Gfs ntersect on dsNum
      extends FSBoundedParam[ nt](
        na  = "soc al_proof_enforced_cand date_s ce_gfs_ ntersect on_ ds_num",
        default = 3,
        m n = 0,
        max =  nteger.MAX_VALUE)

  case object Sgs ntersect on dsNum
      extends FSBoundedParam[ nt](
        na  = "soc al_proof_enforced_cand date_s ce_sgs_ ntersect on_ ds_num",
        default = 10,
        m n = 0,
        max =  nteger.MAX_VALUE)

  case object GfsLagDurat on nDays
      extends FSBoundedParam[Durat on](
        na  = "soc al_proof_enforced_cand date_s ce_gfs_lag_durat on_ n_days",
        default = 14.days,
        m n = 1.days,
        max = 60.days)
      w h HasDurat onConvers on {
    overr de val durat onConvers on: Durat onConvers on = Durat onConvers on.FromDays
  }
}
