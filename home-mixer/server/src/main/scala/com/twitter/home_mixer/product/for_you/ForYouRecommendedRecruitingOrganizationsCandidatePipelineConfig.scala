package com.twitter.home_mixer.product.for_you

import com.twitter.home_mixer.functional_component.decorator.urt.builder.FeedbackStrings
import com.twitter.home_mixer.functional_component.gate.DismissFatigueGate
import com.twitter.home_mixer.functional_component.gate.RateLimitGate
import com.twitter.home_mixer.functional_component.gate.TimelinesPersistenceStoreLastInjectionGate
import com.twitter.home_mixer.model.HomeFeatures.DismissInfoFeature
import com.twitter.home_mixer.model.HomeFeatures.ViewerHasRecruitingOrganizationRecommendationsEnabled
import com.twitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.twitter.home_mixer.product.for_you.candidate_source.RecommendedRecruitingOrganizationsCandidateSource
import com.twitter.home_mixer.product.for_you.param.ForYouParam.EnableRecommendedRecruitingOrganizationsParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.MaxRecommendedRecruitingOrganizationCandidatesParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.RecommendedRecruitingOrganizationMinInjectionIntervalParam
import com.twitter.product_mixer.component_library.gate.DefinedUserIdGate
import com.twitter.product_mixer.component_library.gate.FeatureGate
import com.twitter.product_mixer.component_library.model.candidate.RecruitingOrganizationCandidate
import com.twitter.product_mixer.component_library.pipeline.candidate.recruiting_organization.RecommendedRecruitingOrganizationsCandidateDecorator
import com.twitter.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mixer.core.functional_component.candidate_source.strato.StratoKeyView
import com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.product_mixer.core.product.guice.scope.ProductScoped
import com.twitter.recruiting.organization.RecruitingOrganizationRecommendationsView
import com.twitter.stringcenter.client.StringCenter
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelineservice.model.rich.EntityIdType
import com.twitter.timelineservice.suggests.{thriftscala => st}
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ForYouRecommendedRecruitingOrganizationsCandidatePipelineConfig @Inject() (
  recommendedRecruitingOrganizationsProductCandidateSource: RecommendedRecruitingOrganizationsCandidateSource,
  @ProductScoped stringCenterProvider: Provider[StringCenter],
  externalStrings: HomeMixerExternalStrings,
  feedbackStrings: FeedbackStrings)
    extends CandidatePipelineConfig[
      PipelineQuery,
      StratoKeyView[Long, RecruitingOrganizationRecommendationsView],
      Long,
      RecruitingOrganizationCandidate
    ] {

  private val stringCenter = stringCenterProvider.get()

  override val supportedClientParam: Option[FSParam[Boolean]] =
    Some(EnableRecommendedRecruitingOrganizationsParam)

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ForYouRecommendedRecruitingOrganizations")

  override val gates = Seq(
    DefinedUserIdGate,
    FeatureGate.fromFeature(ViewerHasRecruitingOrganizationRecommendationsEnabled),
    RateLimitGate,
    TimelinesPersistenceStoreLastInjectionGate(
      RecommendedRecruitingOrganizationMinInjectionIntervalParam,
      EntityIdType.RecruitingOrganizationModule
    ),
    DismissFatigueGate(st.SuggestType.RecruitingOrganization, DismissInfoFeature)
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    PipelineQuery,
    StratoKeyView[Long, RecruitingOrganizationRecommendationsView]
  ] = { query =>
    StratoKeyView(
      query.getRequiredUserId,
      RecruitingOrganizationRecommendationsView(count =
        Some(query.params(MaxRecommendedRecruitingOrganizationCandidatesParam)))
    )
  }

  override val candidateSource: BaseCandidateSource[
    StratoKeyView[Long, RecruitingOrganizationRecommendationsView],
    Long
  ] =
    recommendedRecruitingOrganizationsProductCandidateSource

  override val resultTransformer: CandidatePipelineResultsTransformer[
    Long,
    RecruitingOrganizationCandidate
  ] = { orgResult => RecruitingOrganizationCandidate(id = orgResult) }

  override val decorator: Option[
    CandidateDecorator[PipelineQuery, RecruitingOrganizationCandidate]
  ] = {
    Some(
      RecommendedRecruitingOrganizationsCandidateDecorator(
        stringCenter = stringCenter,
        headerString = externalStrings.RecommendedRecruitingOrganizationHeaderString,
        footerString = externalStrings.RecommendedRecruitingOrganizationFooterString,
        seeLessOftenString = feedbackStrings.seeLessOftenFeedbackString,
        seeLessOftenConfirmationString = feedbackStrings.seeLessOftenConfirmationFeedbackString
      ))
  }
}
