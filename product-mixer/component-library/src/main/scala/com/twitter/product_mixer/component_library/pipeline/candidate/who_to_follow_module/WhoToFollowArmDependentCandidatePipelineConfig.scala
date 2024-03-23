package com.ExTwitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module

import com.ExTwitter.account_recommendations_mixer.{thriftscala => t}
import com.ExTwitter.product_mixer.component_library.candidate_source.account_recommendations_mixer.AccountRecommendationsMixerCandidateSource
import com.ExTwitter.product_mixer.component_library.model.candidate.UserCandidate
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.ExTwitter.product_mixer.core.functional_component.common.alert.Alert
import com.ExTwitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleDisplayTypeBuilder
import com.ExTwitter.product_mixer.core.functional_component.filter.Filter
import com.ExTwitter.product_mixer.core.functional_component.gate.BaseGate
import com.ExTwitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.ExTwitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.ExTwitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.product_mixer.core.pipeline.candidate.DependentCandidatePipelineConfig
import com.ExTwitter.timelines.configapi.FSParam
import com.ExTwitter.timelines.configapi.Param
import com.ExTwitter.timelines.configapi.decider.DeciderParam

class WhoToFollowArmDependentCandidatePipelineConfig[Query <: PipelineQuery](
  override val identifier: CandidatePipelineIdentifier,
  override val enabledDeciderParam: Option[DeciderParam[Boolean]],
  override val supportedClientParam: Option[FSParam[Boolean]],
  override val alerts: Seq[Alert],
  override val gates: Seq[BaseGate[Query]],
  accountRecommendationsMixerCandidateSource: AccountRecommendationsMixerCandidateSource,
  override val filters: Seq[Filter[Query, UserCandidate]],
  moduleDisplayTypeBuilder: BaseModuleDisplayTypeBuilder[Query, UserCandidate],
  feedbackActionInfoBuilder: Option[
    BaseFeedbackActionInfoBuilder[PipelineQuery, UserCandidate]
  ],
  displayLocationParam: Param[String],
  excludedUserIdsFeature: Option[Feature[PipelineQuery, Seq[Long]]],
  profileUserIdFeature: Option[Feature[PipelineQuery, Long]])
    extends DependentCandidatePipelineConfig[
      Query,
      t.AccountRecommendationsMixerRequest,
      t.RecommendedUser,
      UserCandidate
    ] {

  override val candidateSource: BaseCandidateSource[
    t.AccountRecommendationsMixerRequest,
    t.RecommendedUser
  ] =
    accountRecommendationsMixerCandidateSource

  override val queryTransformer: CandidatePipelineQueryTransformer[
    PipelineQuery,
    t.AccountRecommendationsMixerRequest
  ] = WhoToFollowArmCandidatePipelineQueryTransformer(
    displayLocationParam = displayLocationParam,
    excludedUserIdsFeature = excludedUserIdsFeature,
    profileUserIdFeature = profileUserIdFeature
  )

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[t.RecommendedUser]
  ] = Seq(WhoToFollowArmResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    t.RecommendedUser,
    UserCandidate
  ] = { user => UserCandidate(user.userId) }

  override val decorator: Option[CandidateDecorator[Query, UserCandidate]] =
    Some(
      WhoToFollowArmCandidateDecorator(
        moduleDisplayTypeBuilder,
        feedbackActionInfoBuilder
      ))
}
