package com.ExTwitter.home_mixer.product.following

import com.ExTwitter.home_mixer.functional_component.decorator.urt.builder.HomeWhoToFollowFeedbackActionInfoBuilder
import com.ExTwitter.home_mixer.functional_component.gate.DismissFatigueGate
import com.ExTwitter.home_mixer.functional_component.gate.TimelinesPersistenceStoreLastInjectionGate
import com.ExTwitter.home_mixer.model.HomeFeatures.DismissInfoFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.PersistenceEntriesFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.WhoToFollowExcludedUserIdsFeature
import com.ExTwitter.home_mixer.product.following.model.FollowingQuery
import com.ExTwitter.home_mixer.product.following.param.FollowingParam.EnableWhoToFollowCandidatePipelineParam
import com.ExTwitter.home_mixer.product.following.param.FollowingParam.WhoToFollowDisplayLocationParam
import com.ExTwitter.home_mixer.product.following.param.FollowingParam.WhoToFollowDisplayTypeIdParam
import com.ExTwitter.home_mixer.product.following.param.FollowingParam.WhoToFollowMinInjectionIntervalParam
import com.ExTwitter.home_mixer.service.HomeMixerAlertConfig
import com.ExTwitter.product_mixer.component_library.decorator.urt.builder.timeline_module.ParamWhoToFollowModuleDisplayTypeBuilder
import com.ExTwitter.product_mixer.component_library.gate.NonEmptyCandidatesGate
import com.ExTwitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module.WhoToFollowArmCandidatePipelineConfig
import com.ExTwitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module.WhoToFollowArmDependentCandidatePipelineConfig
import com.ExTwitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module.WhoToFollowArmDependentCandidatePipelineConfigBuilder
import com.ExTwitter.product_mixer.core.functional_component.common.CandidateScope
import com.ExTwitter.product_mixer.core.functional_component.configapi.StaticParam
import com.ExTwitter.product_mixer.core.functional_component.gate.BaseGate
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.timelineservice.model.rich.EntityIdType
import com.ExTwitter.timelineservice.suggests.thriftscala.SuggestType
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
        PersistenceEntriesFeature,
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
