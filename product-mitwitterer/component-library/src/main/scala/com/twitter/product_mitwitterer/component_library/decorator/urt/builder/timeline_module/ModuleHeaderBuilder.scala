package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.timeline_module

import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.icon.BaseHorizonIconBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseStr
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.social_contetwittert.BaseModuleSocialContetwittertBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleHeaderBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleHeaderDisplayTypeBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.Classic
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ImageVariant
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.ModuleHeader
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

case class ModuleHeaderBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]](
  tetwittertBuilder: BaseStr[Query, Candidate],
  isSticky: Option[Boolean] = None,
  moduleHeaderIconBuilder: Option[BaseHorizonIconBuilder[Query, Candidate]] = None,
  customIcon: Option[ImageVariant] = None,
  moduleSocialContetwittertBuilder: Option[BaseModuleSocialContetwittertBuilder[Query, Candidate]] = None,
  moduleHeaderDisplayTypeBuilder: BaseModuleHeaderDisplayTypeBuilder[Query, Candidate] =
    ModuleHeaderDisplayTypeBuilder(Classic))
    etwittertends BaseModuleHeaderBuilder[Query, Candidate] {

  override def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Option[ModuleHeader] = {
    val firstCandidate = candidates.head
    Some(
      ModuleHeader(
        tetwittert = tetwittertBuilder(query, firstCandidate.candidate, firstCandidate.features),
        sticky = isSticky,
        customIcon = customIcon,
        socialContetwittert = moduleSocialContetwittertBuilder.flatMap(_.apply(query, candidates)),
        icon = moduleHeaderIconBuilder.flatMap(_.apply(query, candidates)),
        moduleHeaderDisplayType = moduleHeaderDisplayTypeBuilder(query, candidates),
      )
    )
  }
}
