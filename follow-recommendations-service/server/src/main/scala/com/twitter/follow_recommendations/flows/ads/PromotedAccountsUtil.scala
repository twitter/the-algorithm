package com.tw ter.follow_recom ndat ons.flows.ads
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.promoted_accounts.PromotedCand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.AccountProof
 mport com.tw ter.follow_recom ndat ons.common.models.Ad tadata
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.Reason
 mport com.tw ter.follow_recom ndat ons.common.models.UserCand dateS ceDeta ls

object PromotedAccountsUt l {
  def toCand dateUser(promotedCand dateUser: PromotedCand dateUser): Cand dateUser = {
    Cand dateUser(
       d = promotedCand dateUser. d,
      score = None,
      ad tadata =
        So (Ad tadata(promotedCand dateUser.pos  on, promotedCand dateUser.ad mpress on)),
      reason = So (
        Reason(
          accountProof = So (AccountProof(followProof = So (promotedCand dateUser.followProof))))
      ),
      userCand dateS ceDeta ls = So (
        UserCand dateS ceDeta ls(
          promotedCand dateUser.pr maryCand dateS ce,
          Map.empty,
          Map.empty,
          None))
    )
  }
}
