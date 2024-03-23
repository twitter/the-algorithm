package com.ExTwitter.product_mixer.component_library.decorator.urt.builder.stringcenter

import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseModuleStr
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.stringcenter.BaseModuleStringCenterPlaceholderBuilder
import com.ExTwitter.product_mixer.core.model.common.CandidateWithFeatures
import com.ExTwitter.product_mixer.core.model.common.UniversalNoun
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stringcenter.client.StringCenter
import com.ExTwitter.stringcenter.client.core.ExternalString

/**
 * This class works the same as [[Str]] but passes in a list of candidates to the
 * [[BaseModuleStringCenterPlaceholderBuilder]] when building the placeholders.
 */
case class ModuleStr[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]](
  text: ExternalString,
  stringCenter: StringCenter,
  stringCenterPlaceholderBuilder: Option[
    BaseModuleStringCenterPlaceholderBuilder[Query, Candidate]
  ] = None)
    extends BaseModuleStr[Query, Candidate] {

  def apply(query: Query, candidates: Seq[CandidateWithFeatures[Candidate]]): String = {
    val placeholderMapOpt =
      stringCenterPlaceholderBuilder.map(_.apply(query, candidates))
    stringCenter.prepare(
      externalString = text,
      placeholders = placeholderMapOpt.getOrElse(Map.empty[String, Any])
    )
  }
}
