package com.X.product_mixer.component_library.decorator.urt.builder.timeline_module

import com.X.product_mixer.core.functional_component.configapi.StaticParam
import com.X.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleDisplayTypeBuilder
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.Carousel
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.CompactCarousel
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.ConversationTree
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.GridCarousel
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleDisplayType
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.Vertical
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.VerticalConversation
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.VerticalGrid
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.VerticalWithContextLine
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.timelines.configapi.Param

object WhoToFollowModuleDisplayType extends Enumeration {
  type ModuleDisplayType = Value

  val Carousel = Value
  val CompactCarousel = Value
  val ConversationTree = Value
  val GridCarousel = Value
  val Vertical = Value
  val VerticalConversation = Value
  val VerticalGrid = Value
  val VerticalWithContextLine = Value
}

case class ParamWhoToFollowModuleDisplayTypeBuilder(
  displayTypeParam: Param[WhoToFollowModuleDisplayType.Value] =
    StaticParam(WhoToFollowModuleDisplayType.Vertical))
    extends BaseModuleDisplayTypeBuilder[PipelineQuery, UniversalNoun[Any]] {

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[UniversalNoun[Any]]]
  ): ModuleDisplayType = {
    val displayType = query.params(displayTypeParam)
    displayType match {
      case WhoToFollowModuleDisplayType.Carousel => Carousel
      case WhoToFollowModuleDisplayType.CompactCarousel => CompactCarousel
      case WhoToFollowModuleDisplayType.ConversationTree => ConversationTree
      case WhoToFollowModuleDisplayType.GridCarousel => GridCarousel
      case WhoToFollowModuleDisplayType.Vertical => Vertical
      case WhoToFollowModuleDisplayType.VerticalConversation => VerticalConversation
      case WhoToFollowModuleDisplayType.VerticalGrid => VerticalGrid
      case WhoToFollowModuleDisplayType.VerticalWithContextLine => VerticalWithContextLine
    }
  }
}
