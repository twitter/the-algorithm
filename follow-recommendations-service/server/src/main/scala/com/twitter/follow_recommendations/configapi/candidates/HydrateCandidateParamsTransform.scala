package com.ExTwitter.follow_recommendations.configapi.candidates

import com.google.inject.Inject
import com.google.inject.Singleton
import com.ExTwitter.follow_recommendations.common.models.CandidateUser
import com.ExTwitter.follow_recommendations.common.models.HasDisplayLocation
import com.ExTwitter.follow_recommendations.common.base.Transform
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelines.configapi.HasParams
import com.ExTwitter.util.logging.Logging

@Singleton
class HydrateCandidateParamsTransform[Target <: HasParams with HasDisplayLocation] @Inject() (
  candidateParamsFactory: CandidateUserParamsFactory[Target])
    extends Transform[Target, CandidateUser]
    with Logging {

  def transform(target: Target, candidates: Seq[CandidateUser]): Stitch[Seq[CandidateUser]] = {
    Stitch.value(candidates.map(candidateParamsFactory.apply(_, target)))
  }
}
