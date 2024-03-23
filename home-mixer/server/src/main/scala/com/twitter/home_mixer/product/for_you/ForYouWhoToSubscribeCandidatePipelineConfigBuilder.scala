package com.ExTwitter.home_mixer.product.for_you

import com.ExTwitter.home_mixer.functional_component.decorator.urt.builder.HomeWhoToSubscribeFeedbackActionInfoBuilder
import com.ExTwitter.home_mixer.functional_component.gate.DismissFatigueGate
import com.ExTwitter.home_mixer.functional_component.gate.TimelinesPersistenceStoreLastInjectionGate
import com.ExTwitter.home_mixer.model.HomeFeatures.DismissInfoFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.PersistenceEntriesFeature
import com.ExTwitter.home_mixer.product.for_you.model.ForYouQuery
import com.ExTwitter.home_mixer.product.for_you.param.ForYouParam.EnableWhoToSubscribeCandidatePipelineParam
import com.ExTwitter.home_mixer.product.for_you.param.ForYouParam.WhoToSubscribeDisplayTypeIdParam
import com.ExTwitter.home_mixer.product.for_you.param.ForYouParam.WhoToSubscribeMinInjectionIntervalParam
import com.ExTwitter.home_mixer.service.HomeMixerAlertConfig
import com.ExTwitter.product_mixer.component_library.decorator.urt.builder.timeline_module.ParamWhoToFollowModuleDisplayTypeBuilder
import com.ExTwitter.product_mixer.component_library.pipeline.candidate.who_to_subscribe_module.WhoToSubscribeCandidatePipelineConfig
import com.ExTwitter.product_mixer.component_library.pipeline.candidate.who_to_subscribe_module.WhoToSubscribeCandidatePipelineConfigBuilder
import com.ExTwitter.product_mixer.core.functional_component.gate.Gate
import com.ExTwitter.timelineservice.model.rich.EntityIdType
import com.ExTwitter.timelineservice.suggests.thriftscala.SuggestType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForYouWhoToSubscribeCandidatePipelineConfigBuilder @Inject() (
  whoToSubscribeCandidatePipelineConfigBuilder: WhoToSubscribeCandidatePipelineConfigBuilder,
  homeWhoToSubscribeFeedbackActionInfoBuilder: HomeWhoToSubscribeFeedbackActionInfoBuilder) {

  def build(): WhoToSubscribeCandidatePipelineConfig[ForYouQuery] = {
    val gates: Seq[Gate[ForYouQuery]] = Seq(
      TimelinesPersistenceStoreLastInjectionGate(
        WhoToSubscribeMinInjectionIntervalParam,
        PersistenceEntriesFeature,
        EntityIdType.WhoToSubscribe
      ),
      DismissFatigueGate(SuggestType.WhoToSubscribe, DismissInfoFeature)
    )

    whoToSubscribeCandidatePipelineConfigBuilder.build[ForYouQuery](
      identifier = WhoToSubscribeCandidatePipelineConfig.identifier,
      supportedClientParam = Some(EnableWhoToSubscribeCandidatePipelineParam),
      alerts = alerts,
      gates = gates,
      moduleDisplayTypeBuilder =
        ParamWhoToFollowModuleDisplayTypeBuilder(WhoToSubscribeDisplayTypeIdParam),
      feedbackActionInfoBuilder = Some(homeWhoToSubscribeFeedbackActionInfoBuilder)
    )
  }

  private val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(70),
    HomeMixerAlertConfig.BusinessHours.defaultEmptyResponseRateAlert()
  )
}
