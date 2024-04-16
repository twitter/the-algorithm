package com.tw ter.follow_recom ndat ons.common.cand date_s ces.crowd_search_accounts

 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSEnumSeqParam
 mport com.tw ter.t  l nes.conf gap .FSParam

object CrowdSearchAccountsParams {
  // w t r or not to fetch CrowdSearchAccounts cand date s ces
  case object Cand dateS ceEnabled
      extends FSParam[Boolean]("crowd_search_accounts_cand date_s ce_enabled", false)

  /**
   *   Conta ns t  log c key for account f lter ng and rank ng. Currently   have 3 ma n log c keys
   *    - new_da ly: f lter ng top searc d accounts w h max da ly searc s based on new users
   *    - new_ ekly: f lter ng top searc d accounts w h max  ekly searc s based on new users
   *    - da ly: f lter ng top searc d accounts w h max da ly searc s
   *    -  ekly: f lter ng top searc d accounts w h max  ekly searc s
   *    Mapp ng of t  Log c  d to Log c key  s done v a @enum AccountsF lter ngAndRank ngLog c
   */
  case object AccountsF lter ngAndRank ngLog cs
      extends FSEnumSeqParam[AccountsF lter ngAndRank ngLog c d.type](
        na  = "crowd_search_accounts_f lter ng_and_rank ng_log c_ ds",
        default = Seq(AccountsF lter ngAndRank ngLog c d.Searc s ekly),
        enum = AccountsF lter ngAndRank ngLog c d)

  case object Cand dateS ce  ght
      extends FSBoundedParam[Double](
        "crowd_search_accounts_cand date_s ce_  ght",
        default = 1200,
        m n = 0.001,
        max = 2000)
}
