package com.twitter.follow_recommendations.common.candidate_sources.stp

import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.models.HasRecentFollowedUserIds
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mixer.core.model.marshalling.request.HasClientContext
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.HasParams

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
