package com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module

sealed trait ModuleDisplayType

case object Carousel extends ModuleDisplayType
case object CompactCarousel extends ModuleDisplayType
case object ConversationTree extends ModuleDisplayType
case object GridCarousel extends ModuleDisplayType
case object Vertical extends ModuleDisplayType
case object VerticalConversation extends ModuleDisplayType
case object VerticalGrid extends ModuleDisplayType
case object VerticalWithContextLine extends ModuleDisplayType
