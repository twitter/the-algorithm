package com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleDisplayTypeBuilder
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleDisplayType
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.VerticalConversation
import com.twitter.product_mixer.core.pipeline.PipelineQuery

case class FeatureModuleDisplayTypeBuilder(
  displayTypeFeature: Feature[_, Option[ModuleDisplayType]],
  defaultDisplayType: ModuleDisplayType = VerticalConversation)
    extends BaseModuleDisplayTypeBuilder[PipelineQuery, UniversalNoun[Any]] {

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[UniversalNoun[Any]]]
  ): ModuleDisplayType = candidates.headOption
    .flatMap(_.features.getOrElse(displayTypeFeature, None))
    .getOrElse(defaultDisplayType)
}
