package com.twitter.home_mitwitterer.product.for_you

import com.twitter.home_mitwitterer.functional_component.decorator.urt.builder.HomeWhoToSubscribeFeedbackActionInfoBuilder
import com.twitter.home_mitwitterer.functional_component.gate.DismissFatigueGate
import com.twitter.home_mitwitterer.functional_component.gate.TimelinesPersistenceStoreLastInjectionGate
import com.twitter.home_mitwitterer.model.HomeFeatures.DismissInfoFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.PersistenceEntriesFeature
import com.twitter.home_mitwitterer.product.for_you.model.ForYouQuery
import com.twitter.home_mitwitterer.product.for_you.param.ForYouParam.EnableWhoToSubscribeCandidatePipelineParam
import com.twitter.home_mitwitterer.product.for_you.param.ForYouParam.WhoToSubscribeDisplayTypeIdParam
import com.twitter.home_mitwitterer.product.for_you.param.ForYouParam.WhoToSubscribeMinInjectionIntervalParam
import com.twitter.home_mitwitterer.service.HomeMitwittererAlertConfig
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.timeline_module.ParamWhoToFollowModuleDisplayTypeBuilder
import com.twitter.product_mitwitterer.component_library.pipeline.candidate.who_to_subscribe_module.WhoToSubscribeCandidatePipelineConfig
import com.twitter.product_mitwitterer.component_library.pipeline.candidate.who_to_subscribe_module.WhoToSubscribeCandidatePipelineConfigBuilder
import com.twitter.product_mitwitterer.core.functional_component.gate.Gate
import com.twitter.timelineservice.model.rich.EntityIdType
import com.twitter.timelineservice.suggests.thriftscala.SuggestType
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

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
    HomeMitwittererAlertConfig.BusinessHours.defaultSuccessRateAlert(70),
    HomeMitwittererAlertConfig.BusinessHours.defaultEmptyResponseRateAlert()
  )
}
