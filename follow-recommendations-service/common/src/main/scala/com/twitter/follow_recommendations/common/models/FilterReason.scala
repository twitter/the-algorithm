package com.tw ter.follow_recom ndat ons.common.models

sealed tra  F lterReason {
  def reason: Str ng
}

object F lterReason {

  case object NoReason extends F lterReason {
    overr de val reason: Str ng = "no_reason"
  }

  case class ParamReason(paramNa : Str ng) extends F lterReason {
    overr de val reason: Str ng = s"param_$paramNa "
  }

  case object Excluded d extends F lterReason {
    overr de val reason: Str ng = "excluded_ d_from_request"
  }

  case object Prof leS debarBlackl st extends F lterReason {
    overr de val reason: Str ng = "prof le_s debar_blackl sted_ d"
  }

  case object CuratedAccountsCompet orL st extends F lterReason {
    overr de val reason: Str ng = "curated_blackl sted_ d"
  }

  case class  nval dRelat onsh pTypes(relat onsh pTypes: Str ng) extends F lterReason {
    overr de val reason: Str ng = s" nval d_relat onsh p_types $relat onsh pTypes"
  }

  case object Prof le d extends F lterReason {
    overr de val reason: Str ng = "cand date_has_sa _ d_as_prof le"
  }

  case object D sm ssed d extends F lterReason {
    overr de val reason: Str ng = s"d sm ssed_cand date"
  }

  case object OptedOut d extends F lterReason {
    overr de val reason: Str ng = s"cand date_opted_out_from_cr er a_ n_request"
  }

  // g zmoduck pred cates
  case object NoUser extends F lterReason {
    overr de val reason: Str ng = "no_user_result_from_g zmoduck"
  }

  case object AddressBookUnd scoverable extends F lterReason {
    overr de val reason: Str ng = "not_d scoverable_v a_address_book"
  }

  case object PhoneBookUnd scoverable extends F lterReason {
    overr de val reason: Str ng = "not_d scoverable_v a_phone_book"
  }

  case object Deact vated extends F lterReason {
    overr de val reason: Str ng = "deact vated"
  }

  case object Suspended extends F lterReason {
    overr de val reason: Str ng = "suspended"
  }

  case object Restr cted extends F lterReason {
    overr de val reason: Str ng = "restr cted"
  }

  case object NsfwUser extends F lterReason {
    overr de val reason: Str ng = "nsfwUser"
  }

  case object NsfwAdm n extends F lterReason {
    overr de val reason: Str ng = "nsfwAdm n"
  }

  case object HssS gnal extends F lterReason {
    overr de val reason: Str ng = "hssS gnal"
  }

  case object  sProtected extends F lterReason {
    overr de val reason: Str ng = " sProtected"
  }

  case class CountryTakedown(countryCode: Str ng) extends F lterReason {
    overr de val reason: Str ng = s"takedown_ n_$countryCode"
  }

  case object Bl nk extends F lterReason {
    overr de val reason: Str ng = "bl nk"
  }

  case object AlreadyFollo d extends F lterReason {
    overr de val reason: Str ng = "already_follo d"
  }

  case object  nval dRelat onsh p extends F lterReason {
    overr de val reason: Str ng = " nval d_relat onsh p"
  }

  case object NotFollow ngTargetUser extends F lterReason {
    overr de val reason: Str ng = "not_follow ng_target_user"
  }

  case object Cand dateS deHoldback extends F lterReason {
    overr de val reason: Str ng = "cand date_s de_holdback"
  }

  case object  nact ve extends F lterReason {
    overr de val reason: Str ng = " nact ve"
  }

  case object M ss ngRecom ndab l yData extends F lterReason {
    overr de val reason: Str ng = "m ss ng_recom ndab l y_data"
  }

  case object H ghT etVeloc y extends F lterReason {
    overr de val reason: Str ng = "h gh_t et_veloc y"
  }

  case object AlreadyRecom nded extends F lterReason {
    overr de val reason: Str ng = "already_recom nded"
  }

  case object M nStateNot t extends F lterReason {
    overr de val reason: Str ng = "m n_state_user_not_ t"
  }

  case object Fa lOpen extends F lterReason {
    overr de val reason: Str ng = "fa l_open"
  }
}
