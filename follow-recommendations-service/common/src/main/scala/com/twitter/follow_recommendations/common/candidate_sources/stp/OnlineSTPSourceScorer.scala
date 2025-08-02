package com.twitter.follow_recommendations.common.candidate_sources.stp

import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.models.HasRecentFollowedUserIds
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.request.HasClientContetwittert
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.HasParams

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class OnlineSTPSourceScorer @Inject() (
  onlineSTPSourceWithEPScorer: OnlineSTPSourceWithEPScorer)
    etwittertends CandidateSource[
      HasClientContetwittert with HasParams with HasRecentFollowedUserIds,
      CandidateUser
    ] {

  override def apply(
    request: HasClientContetwittert with HasParams with HasRecentFollowedUserIds
  ): Stitch[Seq[CandidateUser]] = {
    onlineSTPSourceWithEPScorer(request)
  }

  override val identifier: CandidateSourceIdentifier = BaseOnlineSTPSource.Identifier
}
