package com.X.product_mixer.component_library.decorator.urt.builder.timeline_module

import com.X.product_mixer.core.functional_component.decorator.urt.builder.icon.BaseHorizonIconBuilder
import com.X.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseStr
import com.X.product_mixer.core.functional_component.decorator.urt.builder.social_context.BaseModuleSocialContextBuilder
import com.X.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleHeaderBuilder
import com.X.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleHeaderDisplayTypeBuilder
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.Classic
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.ImageVariant
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleHeader
import com.X.product_mixer.core.pipeline.PipelineQuery

case class ModuleHeaderBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]](
  textBuilder: BaseStr[Query, Candidate],
  isSticky: Option[Boolean] = None,
  moduleHeaderIconBuilder: Option[BaseHorizonIconBuilder[Query, Candidate]] = None,
  customIcon: Option[ImageVariant] = None,
  moduleSocialContextBuilder: Option[BaseModuleSocialContextBuilder[Query, Candidate]] = None,
  moduleHeaderDisplayTypeBuilder: BaseModuleHeaderDisplayTypeBuilder[Query, Candidate] =
    ModuleHeaderDisplayTypeBuilder(Classic))
    extends BaseModuleHeaderBuilder[Query, Candidate] {

  override def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Option[ModuleHeader] = {
    val firstCandidate = candidates.head
    Some(
      ModuleHeader(
        text = textBuilder(query, firstCandidate.candidate, firstCandidate.features),
        sticky = isSticky,
        customIcon = customIcon,
        socialContext = moduleSocialContextBuilder.flatMap(_.apply(query, candidates)),
        icon = moduleHeaderIconBuilder.flatMap(_.apply(query, candidates)),
        moduleHeaderDisplayType = moduleHeaderDisplayTypeBuilder(query, candidates),
      )
    )
  }
}
