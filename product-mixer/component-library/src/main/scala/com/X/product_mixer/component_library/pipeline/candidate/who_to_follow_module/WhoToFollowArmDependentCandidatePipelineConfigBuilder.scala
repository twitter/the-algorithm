package com.X.product_mixer.component_library.pipeline.candidate.who_to_follow_module

import com.X.product_mixer.component_library.candidate_source.account_recommendations_mixer.AccountRecommendationsMixerCandidateSource
import com.X.product_mixer.component_library.model.candidate.UserCandidate
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.functional_component.common.alert.Alert
import com.X.product_mixer.core.functional_component.configapi.StaticParam
import com.X.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.X.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleDisplayTypeBuilder
import com.X.product_mixer.core.functional_component.filter.Filter
import com.X.product_mixer.core.functional_component.gate.BaseGate
import com.X.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.timelines.configapi.FSParam
import com.X.timelines.configapi.Param
import com.X.timelines.configapi.decider.DeciderParam
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WhoToFollowArmDependentCandidatePipelineConfigBuilder @Inject() (
  accountRecommendationsMixerCandidateSource: AccountRecommendationsMixerCandidateSource) {

  /**
   * Build a WhoToFollowArmDependentCandidatePipelineConfig
   *
   *
   * To create a regular CandidatePipelineConfig instead see [[WhoToFollowArmCandidatePipelineConfigBuilder]].
   *
   * @note If injected classes are needed to populate parameters in this method, consider creating a
   *       ProductWhoToFollowCandidatePipelineConfigBuilder with a single `def build()` method. That
   *       product-specific builder class can then inject everything it needs (including this class),
   *       and delegate to this class's build() method within its own build() method.
   */
  def build[Query <: PipelineQuery](
    moduleDisplayTypeBuilder: BaseModuleDisplayTypeBuilder[Query, UserCandidate],
    identifier: CandidatePipelineIdentifier = WhoToFollowArmCandidatePipelineConfig.identifier,
    enabledDeciderParam: Option[DeciderParam[Boolean]] = None,
    supportedClientParam: Option[FSParam[Boolean]] = None,
    alerts: Seq[Alert] = Seq.empty,
    gates: Seq[BaseGate[Query]] = Seq.empty,
    filters: Seq[Filter[Query, UserCandidate]] = Seq.empty,
    feedbackActionInfoBuilder: Option[BaseFeedbackActionInfoBuilder[
      PipelineQuery,
      UserCandidate
    ]] = None,
    displayLocationParam: Param[String] =
      StaticParam(WhoToFollowArmCandidatePipelineQueryTransformer.HomeDisplayLocation),
    excludedUserIdsFeature: Option[Feature[PipelineQuery, Seq[Long]]],
    profileUserIdFeature: Option[Feature[PipelineQuery, Long]]
  ): WhoToFollowArmDependentCandidatePipelineConfig[Query] =
    new WhoToFollowArmDependentCandidatePipelineConfig(
      identifier = identifier,
      enabledDeciderParam = enabledDeciderParam,
      supportedClientParam = supportedClientParam,
      alerts = alerts,
      gates = gates,
      accountRecommendationsMixerCandidateSource = accountRecommendationsMixerCandidateSource,
      filters = filters,
      moduleDisplayTypeBuilder = moduleDisplayTypeBuilder,
      feedbackActionInfoBuilder = feedbackActionInfoBuilder,
      displayLocationParam = displayLocationParam,
      excludedUserIdsFeature = excludedUserIdsFeature,
      profileUserIdFeature = profileUserIdFeature
    )
}
