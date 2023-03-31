package com.twitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module

import com.twitter.account_recommendations_mixer.{thriftscala => t}
import com.twitter.product_mixer.component_library.candidate_source.account_recommendations_mixer.AccountRecommendationsMixerCandidateSource
import com.twitter.product_mixer.component_library.model.candidate.UserCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mixer.core.functional_component.common.alert.Alert
import com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleDisplayTypeBuilder
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.gate.BaseGate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.DependentCandidatePipelineConfig
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.Param
import com.twitter.timelines.configapi.decider.DeciderParam

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
