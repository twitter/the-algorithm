package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.timeline_module

import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleDisplayTypeBuilder
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.ModuleDisplayType
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

case class StaticModuleDisplayTypeBuilder(displayType: ModuleDisplayType)
    etwittertends BaseModuleDisplayTypeBuilder[PipelineQuery, UniversalNoun[Any]] {

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[UniversalNoun[Any]]]
  ): ModuleDisplayType = displayType
}
