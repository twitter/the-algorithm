package com.twitter.home_mixer.product.for_you.candidate_source

import com.twitter.home_mixer.param.HomeMixerInjectionNames.BatchedStratoClientWithModerateTimeout
import com.twitter.product_mixer.core.functional_component.candidate_source.strato.StratoKeyViewFetcherSource
import com.twitter.strato.generated.client.recruiting.api.user.RecruitingOrganizationRecommendationsOnUserClientColumn
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.recruiting.organization.RecruitingOrganizationRecommendationsView
import com.twitter.strato.client.Client

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class RecommendedRecruitingOrganizationsCandidateSource @Inject() (
  @Named(BatchedStratoClientWithModerateTimeout) stratoClient: Client)
    extends StratoKeyViewFetcherSource[
      Long,
      RecruitingOrganizationRecommendationsView,
      RecruitingOrganizationRecommendationsOnUserClientColumn.Value,
      Long
    ] {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("RecommendedRecruitingOrganizations")

  val fetcher = stratoClient.fetcher[
    Long,
    RecruitingOrganizationRecommendationsView,
    RecruitingOrganizationRecommendationsOnUserClientColumn.Value
  ](RecruitingOrganizationRecommendationsOnUserClientColumn.Path)

  override protected def stratoResultTransformer(
    stratoKey: Long,
    stratoResult: RecruitingOrganizationRecommendationsOnUserClientColumn.Value
  ): Seq[Long] = stratoResult.map(_.apiRecruitingOrganization)
}
