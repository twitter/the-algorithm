package com.twitter.product_mixer.component_library.candidate_source.account_recommendations_mixer

import com.twitter.account_recommendations_mixer.{thriftscala => t}
import com.twitter.product_mixer.component_library.model.candidate.UserCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSourceWithExtractedFeatures
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidatesWithSourceFeatures
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

object WhoToFollowModuleHeaderFeature extends Feature[UserCandidate, t.Header]
object WhoToFollowModuleFooterFeature extends Feature[UserCandidate, Option[t.Footer]]
object WhoToFollowModuleDisplayOptionsFeature
    extends Feature[UserCandidate, Option[t.DisplayOptions]]

@Singleton
class AccountRecommendationsMixerCandidateSource @Inject() (
  accountRecommendationsMixer: t.AccountRecommendationsMixer.MethodPerEndpoint)
    extends CandidateSourceWithExtractedFeatures[
      t.AccountRecommendationsMixerRequest,
      t.RecommendedUser
    ] {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier(name = "AccountRecommendationsMixer")

  override def apply(
    request: t.AccountRecommendationsMixerRequest
  ): Stitch[CandidatesWithSourceFeatures[t.RecommendedUser]] = {
    Stitch
      .callFuture(accountRecommendationsMixer.getWtfRecommendations(request))
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
