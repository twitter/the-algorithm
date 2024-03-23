package com.ExTwitter.follow_recommendations.common.candidate_sources.geo

import com.google.inject.Singleton
import com.ExTwitter.follow_recommendations.common.models.CandidateUser
import com.ExTwitter.hermit.model.Algorithm
import com.ExTwitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.ExTwitter.product_mixer.core.model.marshalling.request.HasClientContext
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelines.configapi.HasParams
import javax.inject.Inject

@Singleton
class PopCountryBackFillSource @Inject() (popGeoSource: PopGeoSource)
    extends CandidateSource[HasClientContext with HasParams, CandidateUser] {

  override val identifier: CandidateSourceIdentifier = PopCountryBackFillSource.Identifier

  override def apply(target: HasClientContext with HasParams): Stitch[Seq[CandidateUser]] = {
    target.getOptionalUserId
      .map(_ =>
        popGeoSource(PopCountryBackFillSource.DefaultKey)
          .map(_.take(PopCountryBackFillSource.MaxResults).map(_.withCandidateSource(identifier))))
      .getOrElse(Stitch.Nil)
  }
}

object PopCountryBackFillSource {
  val Identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier(Algorithm.PopCountryBackFill.toString)
  val MaxResults = 40
  val DefaultKey = "country_US"
}
