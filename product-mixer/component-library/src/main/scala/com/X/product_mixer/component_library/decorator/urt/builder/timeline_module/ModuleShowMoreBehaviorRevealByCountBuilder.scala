package com.X.product_mixer.component_library.decorator.urt.builder.timeline_module

import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleShowMoreBehavior
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleShowMoreBehaviorRevealByCount
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleShowMoreBehaviorBuilder
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.timelines.configapi.Param

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
