package com.twitter.home_mitwitterer.product.for_you

import com.twitter.home_mitwitterer.functional_component.decorator.urt.builder.HomeWhoToFollowFeedbackActionInfoBuilder
import com.twitter.home_mitwitterer.functional_component.gate.DismissFatigueGate
import com.twitter.home_mitwitterer.functional_component.gate.TimelinesPersistenceStoreLastInjectionGate
import com.twitter.home_mitwitterer.model.HomeFeatures.DismissInfoFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.PersistenceEntriesFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.WhoToFollowEtwittercludedUserIdsFeature
import com.twitter.home_mitwitterer.product.for_you.model.ForYouQuery
import com.twitter.home_mitwitterer.product.for_you.param.ForYouParam.EnableWhoToFollowCandidatePipelineParam
import com.twitter.home_mitwitterer.product.for_you.param.ForYouParam.WhoToFollowDisplayLocationParam
import com.twitter.home_mitwitterer.product.for_you.param.ForYouParam.WhoToFollowDisplayTypeIdParam
import com.twitter.home_mitwitterer.product.for_you.param.ForYouParam.WhoToFollowMinInjectionIntervalParam
import com.twitter.home_mitwitterer.service.HomeMitwittererAlertConfig
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.timeline_module.ParamWhoToFollowModuleDisplayTypeBuilder
import com.twitter.product_mitwitterer.component_library.pipeline.candidate.who_to_follow_module.WhoToFollowArmCandidatePipelineConfig
import com.twitter.product_mitwitterer.component_library.pipeline.candidate.who_to_follow_module.WhoToFollowArmCandidatePipelineConfigBuilder
import com.twitter.product_mitwitterer.core.functional_component.gate.Gate
import com.twitter.timelineservice.model.rich.EntityIdType
import com.twitter.timelineservice.suggests.thriftscala.SuggestType
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

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
      etwittercludedUserIdsFeature = Some(WhoToFollowEtwittercludedUserIdsFeature),
      displayLocationParam = WhoToFollowDisplayLocationParam
    )
  }

  private val alerts = Seq(
    HomeMitwittererAlertConfig.BusinessHours.defaultSuccessRateAlert(70),
    HomeMitwittererAlertConfig.BusinessHours.defaultEmptyResponseRateAlert()
  )
}
