package com.X.home_mixer.product.for_you

import com.X.home_mixer.functional_component.decorator.urt.builder.HomeWhoToFollowFeedbackActionInfoBuilder
import com.X.home_mixer.functional_component.gate.DismissFatigueGate
import com.X.home_mixer.functional_component.gate.TimelinesPersistenceStoreLastInjectionGate
import com.X.home_mixer.model.HomeFeatures.DismissInfoFeature
import com.X.home_mixer.model.HomeFeatures.PersistenceEntriesFeature
import com.X.home_mixer.model.HomeFeatures.WhoToFollowExcludedUserIdsFeature
import com.X.home_mixer.product.for_you.model.ForYouQuery
import com.X.home_mixer.product.for_you.param.ForYouParam.EnableWhoToFollowCandidatePipelineParam
import com.X.home_mixer.product.for_you.param.ForYouParam.WhoToFollowDisplayLocationParam
import com.X.home_mixer.product.for_you.param.ForYouParam.WhoToFollowDisplayTypeIdParam
import com.X.home_mixer.product.for_you.param.ForYouParam.WhoToFollowMinInjectionIntervalParam
import com.X.home_mixer.service.HomeMixerAlertConfig
import com.X.product_mixer.component_library.decorator.urt.builder.timeline_module.ParamWhoToFollowModuleDisplayTypeBuilder
import com.X.product_mixer.component_library.pipeline.candidate.who_to_follow_module.WhoToFollowArmCandidatePipelineConfig
import com.X.product_mixer.component_library.pipeline.candidate.who_to_follow_module.WhoToFollowArmCandidatePipelineConfigBuilder
import com.X.product_mixer.core.functional_component.gate.Gate
import com.X.timelineservice.model.rich.EntityIdType
import com.X.timelineservice.suggests.thriftscala.SuggestType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForYouWhoToFollowCandidatePipelineConfigBuilder @Inject() (
  whoToFollowArmCandidatePipelineConfigBuilder: WhoToFollowArmCandidatePipelineConfigBuilder,
  homeWhoToFollowFeedbackActionInfoBuilder: HomeWhoToFollowFeedbackActionInfoBuilder) {

  def build(): WhoToFollowArmCandidatePipelineConfig[ForYouQuery] = {
    val gates: Seq[Gate[ForYouQuery]] = Seq(
      TimelinesPersistenceStoreLastInjectionGate(
        WhoToFollowMinInjectionIntervalParam,
        PersistenceEntriesFeature,
        EntityIdType.WhoToFollow
      ),
      DismissFatigueGate(SuggestType.WhoToFollow, DismissInfoFeature)
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
      displayLocationParam = WhoToFollowDisplayLocationParam
    )
  }

  private val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(70),
    HomeMixerAlertConfig.BusinessHours.defaultEmptyResponseRateAlert()
  )
}
