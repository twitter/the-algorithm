package com.twitter.home_mixer.product.list_recommended_users.model

import com.twitter.gizmoduck.{thriftscala => gt}
import com.twitter.product_mixer.component_library.model.candidate.UserCandidate
import com.twitter.product_mixer.core.feature.Feature

object ListFeatures {
  // Candidate features
  object GizmoduckUserFeature extends Feature[UserCandidate, Option[gt.User]]
  object IsListMemberFeature extends Feature[UserCandidate, Boolean]
  object ScoreFeature extends Feature[UserCandidate, Double]
}
