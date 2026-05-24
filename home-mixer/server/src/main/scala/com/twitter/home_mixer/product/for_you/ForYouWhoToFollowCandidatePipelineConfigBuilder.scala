package com.twitter.home_mixer.product.for_you

import com.twitter.home_mixer.functional_component.decorator.urt.builder.HomeWhoToFollowFeedbackActionInfoBuilder
import com.twitter.home_mixer.functional_component.gate.DismissFatigueGate
import com.twitter.home_mixer.functional_component.gate.RateLimitGate
import com.twitter.home_mixer.functional_component.gate.TestUserProbabilisticGate
import com.twitter.home_mixer.functional_component.gate.TimelinesPersistenceStoreLastInjectionGate
import com.twitter.home_mixer.model.HomeFeatures.DismissInfoFeature
import com.twitter.home_mixer.model.HomeFeatures.WhoToFollowExcludedUserIdsFeature
import com.twitter.home_mixer.product.for_you.model.ForYouQuery
import com.twitter.home_mixer.product.for_you.param.ForYouParam.EnableWhoToFollowCandidatePipelineParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.WhoToFollowDisplayLocationParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.WhoToFollowDisplayTypeIdParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.WhoToFollowMinInjectionIntervalParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.WhoToFollowUserDisplayTypeIdParam
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.ParamWhoToFollowModuleDisplayTypeBuilder
import com.twitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module.ParamWhoToFollowUserDisplayTypeBuilder
import com.twitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module.WhoToFollowArmCandidatePipelineConfig
import com.twitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module.WhoToFollowArmCandidatePipelineConfigBuilder
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.timelineservice.model.rich.EntityIdType
import com.twitter.timelineservice.suggests.thriftscala.SuggestType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForYouWhoToFollowCandidatePipelineConfigBuilder @Inject() (
  whoToFollowArmCandidatePipelineConfigBuilder: WhoToFollowArmCandidatePipelineConfigBuilder,
  homeWhoToFollowFeedbackActionInfoBuilder: HomeWhoToFollowFeedbackActionInfoBuilder,
  testUserProbabilisticGate: TestUserProbabilisticGate) {

  def build(): WhoToFollowArmCandidatePipelineConfig[ForYouQuery] = {
    val gates: Seq[Gate[ForYouQuery]] = Seq(
      RateLimitGate,
      TimelinesPersistenceStoreLastInjectionGate(
        WhoToFollowMinInjectionIntervalParam,
        EntityIdType.WhoToFollow
      ),
      DismissFatigueGate(SuggestType.WhoToFollow, DismissInfoFeature),
      testUserProbabilisticGate
    )

    whoToFollowArmCandidatePipelineConfigBuilder.build[ForYouQuery](
      identifier = WhoToFollowArmCandidatePipelineConfig.identifier,
      supportedClientParam = Some(EnableWhoToFollowCandidatePipelineParam),
      alerts = alerts,
      gates = gates,
      moduleDisplayTypeBuilder =
        ParamWhoToFollowModuleDisplayTypeBuilder(WhoToFollowDisplayTypeIdParam),
      feedbackActionInfoBuilder = Some(homeWhoToFollowFeedbackActionInfoBuilder),
      excludedUserIdsFeature = Some(WhoToFollowExcludedUserIdsFeature),
      userDisplayTypeBuilder =
        ParamWhoToFollowUserDisplayTypeBuilder(WhoToFollowUserDisplayTypeIdParam),
      displayLocationParam = WhoToFollowDisplayLocationParam
    )
  }

  private val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(70),
    HomeMixerAlertConfig.BusinessHours.defaultEmptyResponseRateAlert()
  )
}
