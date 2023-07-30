package com.X.follow_recommendations.common.candidate_sources.stp

import com.X.follow_recommendations.common.models.CandidateUser
import com.X.follow_recommendations.common.models.HasRecentFollowedUserIds
import com.X.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.product_mixer.core.model.marshalling.request.HasClientContext
import com.X.stitch.Stitch
import com.X.timelines.configapi.HasParams

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnlineSTPSourceScorer @Inject() (
  onlineSTPSourceWithEPScorer: OnlineSTPSourceWithEPScorer)
    extends CandidateSource[
      HasClientContext with HasParams with HasRecentFollowedUserIds,
      CandidateUser
    ] {

  override def apply(
    request: HasClientContext with HasParams with HasRecentFollowedUserIds
  ): Stitch[Seq[CandidateUser]] = {
    onlineSTPSourceWithEPScorer(request)
  }

  override val identifier: CandidateSourceIdentifier = BaseOnlineSTPSource.Identifier
}
