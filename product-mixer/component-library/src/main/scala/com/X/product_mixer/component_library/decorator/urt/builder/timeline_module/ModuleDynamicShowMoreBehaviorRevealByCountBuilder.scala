package com.X.product_mixer.component_library.decorator.urt.builder.timeline_module

import com.X.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleShowMoreBehaviorBuilder
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleShowMoreBehavior
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleShowMoreBehaviorRevealByCount
import com.X.product_mixer.core.pipeline.PipelineQuery

case class ModuleDynamicShowMoreBehaviorRevealByCountBuilder(
  initialItemsCount: Int,
  showMoreItemsCount: Int)
    extends BaseModuleShowMoreBehaviorBuilder[PipelineQuery, UniversalNoun[Any]] {

  override def apply(
    query: PipelineQuery,
    candidate: Seq[CandidateWithFeatures[UniversalNoun[Any]]]
  ): ModuleShowMoreBehavior = ModuleShowMoreBehaviorRevealByCount(
    initialItemsCount = initialItemsCount,
    showMoreItemsCount = showMoreItemsCount
  )
}
