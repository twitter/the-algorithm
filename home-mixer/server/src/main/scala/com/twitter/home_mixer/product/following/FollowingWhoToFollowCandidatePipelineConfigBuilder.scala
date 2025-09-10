package com.twitter.home_mixer.product.following

import com.twitter.home_mixer.functional_component.decorator.urt.builder.HomeWhoToFollowFeedbackActionInfoBuilder
import com.twitter.home_mixer.functional_component.gate.DismissFatigueGate
import com.twitter.home_mixer.functional_component.gate.TimelinesPersistenceStoreLastInjectionGate
import com.twitter.home_mixer.model.HomeFeatures.DismissInfoFeature
import com.twitter.home_mixer.model.HomeFeatures.WhoToFollowExcludedUserIdsFeature
import com.twitter.home_mixer.product.following.model.FollowingQuery
import com.twitter.home_mixer.product.following.param.FollowingParam.EnableWhoToFollowCandidatePipelineParam
import com.twitter.home_mixer.product.following.param.FollowingParam.WhoToFollowDisplayLocationParam
import com.twitter.home_mixer.product.following.param.FollowingParam.WhoToFollowDisplayTypeIdParam
import com.twitter.home_mixer.product.following.param.FollowingParam.WhoToFollowMinInjectionIntervalParam
import com.twitter.home_mixer.product.following.param.FollowingParam.WhoToFollowUserDisplayTypeIdParam
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.ParamWhoToFollowModuleDisplayTypeBuilder
import com.twitter.product_mixer.component_library.gate.NonEmptyCandidatesGate
import com.twitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module.ParamWhoToFollowUserDisplayTypeBuilder
import com.twitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module.WhoToFollowArmCandidatePipelineConfig
import com.twitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module.WhoToFollowArmDependentCandidatePipelineConfig
import com.twitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module.WhoToFollowArmDependentCandidatePipelineConfigBuilder
import com.twitter.product_mixer.core.functional_component.common.CandidateScope
import com.twitter.product_mixer.core.functional_component.configapi.StaticParam
import com.twitter.product_mixer.core.functional_component.gate.BaseGate
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelineservice.model.rich.EntityIdType
import com.twitter.timelineservice.suggests.thriftscala.SuggestType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FollowingWhoToFollowCandidatePipelineConfigBuilder @Inject() (
  whoToFollowArmDependentCandidatePipelineConfigBuilder: WhoToFollowArmDependentCandidatePipelineConfigBuilder,
  homeWhoToFollowFeedbackActionInfoBuilder: HomeWhoToFollowFeedbackActionInfoBuilder) {

  def build(
    requiredNonEmptyPipelines: CandidateScope
  ): WhoToFollowArmDependentCandidatePipelineConfig[FollowingQuery] = {
    val gates: Seq[BaseGate[PipelineQuery]] = Seq(
      TimelinesPersistenceStoreLastInjectionGate(
        WhoToFollowMinInjectionIntervalParam,
        EntityIdType.WhoToFollow
      ),
      DismissFatigueGate(SuggestType.WhoToFollow, DismissInfoFeature),
      NonEmptyCandidatesGate(requiredNonEmptyPipelines)
    )

    whoToFollowArmDependentCandidatePipelineConfigBuilder.build[FollowingQuery](
      identifier = WhoToFollowArmCandidatePipelineConfig.identifier,
      supportedClientParam = Some(EnableWhoToFollowCandidatePipelineParam),
      alerts = alerts,
      gates = gates,
      moduleDisplayTypeBuilder =
        ParamWhoToFollowModuleDisplayTypeBuilder(WhoToFollowDisplayTypeIdParam),
      feedbackActionInfoBuilder = Some(homeWhoToFollowFeedbackActionInfoBuilder),
      userDisplayTypeBuilder =
        ParamWhoToFollowUserDisplayTypeBuilder(WhoToFollowUserDisplayTypeIdParam),
      displayLocationParam = StaticParam(WhoToFollowDisplayLocationParam.default),
      excludedUserIdsFeature = Some(WhoToFollowExcludedUserIdsFeature),
      profileUserIdFeature = None
    )
  }

  private val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(70),
    HomeMixerAlertConfig.BusinessHours.defaultEmptyResponseRateAlert()
  )
}
