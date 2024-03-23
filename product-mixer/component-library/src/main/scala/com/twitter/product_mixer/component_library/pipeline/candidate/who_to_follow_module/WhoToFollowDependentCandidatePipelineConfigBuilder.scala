package com.ExTwitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module

import com.ExTwitter.product_mixer.component_library.candidate_source.people_discovery.PeopleDiscoveryCandidateSource
import com.ExTwitter.product_mixer.component_library.model.candidate.UserCandidate
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.functional_component.common.alert.Alert
import com.ExTwitter.product_mixer.core.functional_component.configapi.StaticParam
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleDisplayTypeBuilder
import com.ExTwitter.product_mixer.core.functional_component.filter.Filter
import com.ExTwitter.product_mixer.core.functional_component.gate.BaseGate
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.timelines.configapi.FSParam
import com.ExTwitter.timelines.configapi.Param
import com.ExTwitter.timelines.configapi.decider.DeciderParam
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WhoToFollowDependentCandidatePipelineConfigBuilder @Inject() (
  whoToFollowCandidateSource: PeopleDiscoveryCandidateSource) {

  /**
   * Build a WhoToFollowDependentCandidatePipelineConfig
   *
   *
   * To create a regular CandidatePipelineConfig instead see [[WhoToFollowCandidatePipelineConfigBuilder]].
   *
   * @note If injected classes are needed to populate parameters in this method, consider creating a
   *       ProductWhoToFollowCandidatePipelineConfigBuilder with a single `def build()` method. That
   *       product-specific builder class can then inject everything it needs (including this class),
   *       and delegate to this class's build() method within its own build() method.
   */
  def build[Query <: PipelineQuery](
    moduleDisplayTypeBuilder: BaseModuleDisplayTypeBuilder[Query, UserCandidate],
    identifier: CandidatePipelineIdentifier = WhoToFollowCandidatePipelineConfig.identifier,
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
      StaticParam(WhoToFollowCandidatePipelineQueryTransformer.DisplayLocation),
    supportedLayoutsParam: Param[Seq[String]] =
      StaticParam(WhoToFollowCandidatePipelineQueryTransformer.SupportedLayouts),
    layoutVersionParam: Param[Int] =
      StaticParam(WhoToFollowCandidatePipelineQueryTransformer.LayoutVersion),
    excludedUserIdsFeature: Option[Feature[PipelineQuery, Seq[Long]]] = None,
  ): WhoToFollowDependentCandidatePipelineConfig[Query] =
    new WhoToFollowDependentCandidatePipelineConfig(
      identifier = identifier,
      enabledDeciderParam = enabledDeciderParam,
      supportedClientParam = supportedClientParam,
      alerts = alerts,
      gates = gates,
      whoToFollowCandidateSource = whoToFollowCandidateSource,
      filters = filters,
      moduleDisplayTypeBuilder = moduleDisplayTypeBuilder,
      feedbackActionInfoBuilder = feedbackActionInfoBuilder,
      displayLocationParam = displayLocationParam,
      supportedLayoutsParam = supportedLayoutsParam,
      layoutVersionParam = layoutVersionParam,
      excludedUserIdsFeature = excludedUserIdsFeature
    )
}
