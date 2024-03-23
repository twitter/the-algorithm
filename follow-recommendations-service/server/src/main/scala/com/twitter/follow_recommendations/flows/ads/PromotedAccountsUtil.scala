package com.ExTwitter.follow_recommendations.flows.ads
import com.ExTwitter.follow_recommendations.common.candidate_sources.promoted_accounts.PromotedCandidateUser
import com.ExTwitter.follow_recommendations.common.models.AccountProof
import com.ExTwitter.follow_recommendations.common.models.AdMetadata
import com.ExTwitter.follow_recommendations.common.models.CandidateUser
import com.ExTwitter.follow_recommendations.common.models.Reason
import com.ExTwitter.follow_recommendations.common.models.UserCandidateSourceDetails

object PromotedAccountsUtil {
  def toCandidateUser(promotedCandidateUser: PromotedCandidateUser): CandidateUser = {
    CandidateUser(
      id = promotedCandidateUser.id,
      score = None,
      adMetadata =
        Some(AdMetadata(promotedCandidateUser.position, promotedCandidateUser.adImpression)),
      reason = Some(
        Reason(
          accountProof = Some(AccountProof(followProof = Some(promotedCandidateUser.followProof))))
      ),
      userCandidateSourceDetails = Some(
        UserCandidateSourceDetails(
          promotedCandidateUser.primaryCandidateSource,
          Map.empty,
          Map.empty,
          None))
    )
  }
}
