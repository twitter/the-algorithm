package com.ExTwitter.home_mixer.product.list_recommended_users.model

import com.ExTwitter.product_mixer.component_library.model.candidate.UserCandidate
import com.ExTwitter.product_mixer.core.feature.Feature

object ListRecommendedUsersFeatures {
  // Candidate features
  object IsGizmoduckValidUserFeature extends Feature[UserCandidate, Boolean]
  object IsListMemberFeature extends Feature[UserCandidate, Boolean]
  object IsSGSValidUserFeature extends Feature[UserCandidate, Boolean]
  object ScoreFeature extends Feature[UserCandidate, Double]
}