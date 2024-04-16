package com.tw ter.follow_recom ndat ons.common.cand date_s ces.top_organ c_follows_accounts

 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSEnumSeqParam
 mport com.tw ter.t  l nes.conf gap .FSParam

object TopOrgan cFollowsAccountsParams {
  // w t r or not to fetch TopOrgan cFollowsAccounts cand date s ces
  case object Cand dateS ceEnabled
      extends FSParam[Boolean]("top_organ c_follows_accounts_cand date_s ce_enabled", false)

  /**
   *   Conta ns t  log c key for account f lter ng and rank ng. Currently   have 3 ma n log c keys
   *    - new_organ c_follows: f lter ng top organ cally follo d accounts follo d by new users
   *    - non_new_organ c_follows: f lter ng top organ cally follo d accounts follo d by non new users
   *    - organ c_follows: f lter ng top organ cally follo d accounts follo d by all users
   *    Mapp ng of t  Log c  d to Log c key  s done v a @enum AccountsF lter ngAndRank ngLog c
   */
  case object AccountsF lter ngAndRank ngLog cs
      extends FSEnumSeqParam[AccountsF lter ngAndRank ngLog c d.type](
        na  = "top_organ c_follows_accounts_f lter ng_and_rank ng_log c_ ds",
        default = Seq(AccountsF lter ngAndRank ngLog c d.Organ cFollows),
        enum = AccountsF lter ngAndRank ngLog c d)

  case object Cand dateS ce  ght
      extends FSBoundedParam[Double](
        "top_organ c_follows_accounts_cand date_s ce_  ght",
        default = 1200,
        m n = 0.001,
        max = 2000)
}
