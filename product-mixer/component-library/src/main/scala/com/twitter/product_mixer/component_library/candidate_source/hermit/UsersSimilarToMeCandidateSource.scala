package com.twitter.product_mixer.component_library.candidate_source.hermit

import com.twitter.hermit.thriftscala.RecommendationRequest
import com.twitter.hermit.thriftscala.RecommendationResponse
import com.twitter.hermit.thriftscala.RelatedUser
import com.twitter.product_mixer.core.functional_component.candidate_source.strato.StratoKeyViewFetcherSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.onboarding.HermitRecommendUsersClientColumn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersSimilarToMeCandidateSource @Inject() (
  column: HermitRecommendUsersClientColumn)
    extends StratoKeyViewFetcherSource[
      Long,
      RecommendationRequest,
      RecommendationResponse,
      RelatedUser
    ] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("UsersSimilarToMe")

  override val fetcher: Fetcher[Long, RecommendationRequest, RecommendationResponse] =
    column.fetcher

  override def stratoResultTransformer(
    stratoKey: Long,
    result: RecommendationResponse
  ): Seq[RelatedUser] = result.suggestions.getOrElse(Seq.empty).filter(_.id.isDefined)
}
