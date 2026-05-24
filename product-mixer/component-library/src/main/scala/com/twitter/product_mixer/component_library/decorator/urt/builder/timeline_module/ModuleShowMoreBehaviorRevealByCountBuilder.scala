package com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module

import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleShowMoreBehavior
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleShowMoreBehaviorRevealByCount
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleShowMoreBehaviorBuilder
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.timelines.configapi.Param

case class ModuleShowMoreBehaviorRevealByCountBuilder(
  initialItemsCountParam: Param[Int],
  showMoreItemsCountParam: Param[Int])
    extends BaseModuleShowMoreBehaviorBuilder[PipelineQuery, UniversalNoun[Any]] {

  override def apply(
    query: PipelineQuery,
    candidate: Seq[CandidateWithFeatures[UniversalNoun[Any]]]
  ): ModuleShowMoreBehavior = {
    ModuleShowMoreBehaviorRevealByCount(
      initialItemsCount = query.params(initialItemsCountParam),
      showMoreItemsCount = query.params(showMoreItemsCountParam)
    )
  }
}
