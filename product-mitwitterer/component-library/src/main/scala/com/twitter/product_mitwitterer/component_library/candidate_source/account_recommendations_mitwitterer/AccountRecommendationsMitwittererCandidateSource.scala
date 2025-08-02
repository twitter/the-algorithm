package com.twitter.product_mitwitterer.component_library.candidate_source.account_recommendations_mitwitterer

import com.twitter.account_recommendations_mitwitterer.{thriftscala => t}
import com.twitter.product_mitwitterer.component_library.model.candidate.UserCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSourceWithEtwittertractedFeatures
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidatesWithSourceFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

object WhoToFollowModuleHeaderFeature etwittertends Feature[UserCandidate, t.Header]
object WhoToFollowModuleFooterFeature etwittertends Feature[UserCandidate, Option[t.Footer]]
object WhoToFollowModuleDisplayOptionsFeature
    etwittertends Feature[UserCandidate, Option[t.DisplayOptions]]

@Singleton
class AccountRecommendationsMitwittererCandidateSource @Inject() (
  accountRecommendationsMitwitterer: t.AccountRecommendationsMitwitterer.MethodPerEndpoint)
    etwittertends CandidateSourceWithEtwittertractedFeatures[
      t.AccountRecommendationsMitwittererRequest,
      t.RecommendedUser
    ] {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier(name = "AccountRecommendationsMitwitterer")

  override def apply(
    request: t.AccountRecommendationsMitwittererRequest
  ): Stitch[CandidatesWithSourceFeatures[t.RecommendedUser]] = {
    Stitch
      .callFuture(accountRecommendationsMitwitterer.getWtfRecommendations(request))
      .map { response: t.WhoToFollowResponse =>
        responseToCandidatesWithSourceFeatures(
          response.userRecommendations,
          response.header,
          response.footer,
          response.displayOptions)
      }
  }

  private def responseToCandidatesWithSourceFeatures(
    userRecommendations: Seq[t.RecommendedUser],
    header: t.Header,
    footer: Option[t.Footer],
    displayOptions: Option[t.DisplayOptions],
  ): CandidatesWithSourceFeatures[t.RecommendedUser] = {
    val features = FeatureMapBuilder()
      .add(WhoToFollowModuleHeaderFeature, header)
      .add(WhoToFollowModuleFooterFeature, footer)
      .add(WhoToFollowModuleDisplayOptionsFeature, displayOptions)
      .build()
    CandidatesWithSourceFeatures(userRecommendations, features)
  }
}
