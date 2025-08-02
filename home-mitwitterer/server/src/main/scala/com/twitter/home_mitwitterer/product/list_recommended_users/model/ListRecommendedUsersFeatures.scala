package com.twitter.home_mitwitterer.product.list_recommended_users.model

import com.twitter.product_mitwitterer.component_library.model.candidate.UserCandidate
import com.twitter.product_mitwitterer.core.feature.Feature

object ListRecommendedUsersFeatures {
  // Candidate features
  object IsGizmoduckValidUserFeature etwittertends Feature[UserCandidate, Boolean]
  object IsListMemberFeature etwittertends Feature[UserCandidate, Boolean]
  object IsSGSValidUserFeature etwittertends Feature[UserCandidate, Boolean]
  object ScoreFeature etwittertends Feature[UserCandidate, Double]
}
