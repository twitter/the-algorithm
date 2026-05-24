package com.twitter.home_mixer.functional_component.decorator

import com.twitter.home_mixer.functional_component.decorator.EntryPointPivotModuleCandidateDecorator.EntryNamespaceString
import com.twitter.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.twitter.product_mixer.component_library.decorator.urt.UrtItemInModuleDecorator
import com.twitter.product_mixer.component_library.decorator.urt.builder.item.pivot.PivotCandidateUrtItemBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.metadata.ClientEventInfoBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.metadata.SuggestTypeClientEventDetailsBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.ModuleHeaderBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.StaticModuleDisplayTypeBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.TimelineModuleBuilder
import com.twitter.product_mixer.component_library.model.candidate.pivot.PivotCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseStr
import com.twitter.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.Vertical
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelineservice.suggests.{thriftscala => st}

case class StrEntryPointPivotCategoryText(
  defaultText: String)
    extends BaseStr[PipelineQuery, PivotCandidate] {
  def apply(
    query: PipelineQuery,
    candidate: PivotCandidate,
    candidateFeatures: FeatureMap
  ): String =
    candidate.categoryText.getOrElse(defaultText)
}
object EntryPointPivotModuleCandidateDecorator {
  val EntryNamespaceString = "entry-point-pivot"
}
case class EntryPointPivotModuleCandidateDecorator(
  component: String,
  headerText: BaseStr[PipelineQuery, PivotCandidate]) {

  private val clientEventsBuilder =
    ClientEventInfoBuilder[PipelineQuery, PivotCandidate](component)
  private val clientEventDetailsBuilder =
    SuggestTypeClientEventDetailsBuilder(st.SuggestType.EntryPointPivot)

  private val itemBuilder =
    PivotCandidateUrtItemBuilder(clientEventInfoBuilder = Some(clientEventDetailsBuilder))
  private val itemDecorator = UrtItemCandidateDecorator(itemBuilder)

  private val moduleHeaderBuilder = ModuleHeaderBuilder(
    textBuilder = headerText,
    isSticky = Some(false),
    urlBuilder = None
  )

  private val moduleBuilder = TimelineModuleBuilder(
    entryNamespace = EntryNamespace(EntryNamespaceString),
    displayTypeBuilder = StaticModuleDisplayTypeBuilder(Vertical),
    clientEventInfoBuilder = clientEventsBuilder,
    headerBuilder = Some(moduleHeaderBuilder),
    footerBuilder = None
  )

  val moduleDecorator: UrtItemInModuleDecorator[PipelineQuery, PivotCandidate, Nothing] =
    UrtItemInModuleDecorator(itemDecorator, moduleBuilder)
}
