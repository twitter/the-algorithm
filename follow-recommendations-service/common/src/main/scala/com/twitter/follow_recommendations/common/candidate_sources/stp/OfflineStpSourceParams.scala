package com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp

 mport com.tw ter.t  l nes.conf gap .FSParam

object Offl neStpS ceParams {
  //  f enabled,   use t  new, denser vers on of PM  matr x to generate Offl neSTP cand dates.
  case object UseDenserPm Matr x
      extends FSParam[Boolean]("offl ne_stp_s ce_use_denser_pm _matr x", default = false)
}
