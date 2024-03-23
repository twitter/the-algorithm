package com.ExTwitter.follow_recommendations.common.candidate_sources.stp

import com.ExTwitter.follow_recommendations.common.models.CandidateUser
import com.ExTwitter.follow_recommendations.common.models.HasRecentFollowedUserIds
import com.ExTwitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.ExTwitter.product_mixer.core.model.marshalling.request.HasClientContext
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelines.configapi.HasParams

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
