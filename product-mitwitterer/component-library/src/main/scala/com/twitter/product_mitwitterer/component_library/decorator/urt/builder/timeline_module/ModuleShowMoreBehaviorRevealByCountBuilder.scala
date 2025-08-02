package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.timeline_module

import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.ModuleShowMoreBehavior
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.ModuleShowMoreBehaviorRevealByCount
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleShowMoreBehaviorBuilder
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.timelines.configapi.Param

case class ModuleShowMoreBehaviorRevealByCountBuilder(
  initialItemsCountParam: Param[Int],
  showMoreItemsCountParam: Param[Int])
    etwittertends BaseModuleShowMoreBehaviorBuilder[PipelineQuery, UniversalNoun[Any]] {

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
