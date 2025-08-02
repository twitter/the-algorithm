package com.twitter.follow_recommendations.common.candidate_sources.geo

import com.google.inject.Singleton
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.hermit.model.Algorithm
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.request.HasClientContetwittert
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.HasParams
import javatwitter.inject.Inject

@Singleton
class PopCountryBackFillSource @Inject() (popGeoSource: PopGeoSource)
    etwittertends CandidateSource[HasClientContetwittert with HasParams, CandidateUser] {

  override val identifier: CandidateSourceIdentifier = PopCountryBackFillSource.Identifier

  override def apply(target: HasClientContetwittert with HasParams): Stitch[Seq[CandidateUser]] = {
    target.getOptionalUserId
      .map(_ =>
        popGeoSource(PopCountryBackFillSource.DefaultKey)
          .map(_.take(PopCountryBackFillSource.MatwitterResults).map(_.withCandidateSource(identifier))))
      .getOrElse(Stitch.Nil)
  }
}

object PopCountryBackFillSource {
  val Identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier(Algorithm.PopCountryBackFill.toString)
  val MatwitterResults = 40
  val DefaultKey = "country_US"
}
