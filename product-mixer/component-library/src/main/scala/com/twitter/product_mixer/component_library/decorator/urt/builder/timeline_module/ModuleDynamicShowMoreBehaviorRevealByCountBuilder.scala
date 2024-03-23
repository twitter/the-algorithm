package com.ExTwitter.product_mixer.component_library.decorator.urt.builder.timeline_module

import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleShowMoreBehaviorBuilder
import com.ExTwitter.product_mixer.core.model.common.CandidateWithFeatures
import com.ExTwitter.product_mixer.core.model.common.UniversalNoun
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleShowMoreBehavior
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleShowMoreBehaviorRevealByCount
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery

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
