package com.twitter.product_mitwitterer.component_library.candidate_source.recommendations

import com.twitter.follow_recommendations.{thriftscala => fr}
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.strato.StratoKeyViewFetcherSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.onboarding.follow_recommendations_service.GetRecommendationsClientColumn
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * Returns a list of FollowRecommendations as [[fr.UserRecommendation]]s fetched from Strato
 */
@Singleton
class UserFollowRecommendationsCandidateSource @Inject() (
  getRecommendationsClientColumn: GetRecommendationsClientColumn)
    etwittertends StratoKeyViewFetcherSource[
      fr.RecommendationRequest,
      Unit,
      fr.RecommendationResponse,
      fr.UserRecommendation
    ] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    "FollowRecommendationsService")

  override val fetcher: Fetcher[fr.RecommendationRequest, Unit, fr.RecommendationResponse] =
    getRecommendationsClientColumn.fetcher

  override def stratoResultTransformer(
    stratoKey: fr.RecommendationRequest,
    stratoResult: fr.RecommendationResponse
  ): Seq[fr.UserRecommendation] = {
    stratoResult.recommendations.map {
      case fr.Recommendation.User(userRec: fr.UserRecommendation) =>
        userRec
      case _ =>
        throw new Etwitterception("Invalid recommendation type returned from FRS")
    }
  }
}
