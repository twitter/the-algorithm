package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.timeline_module

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.Carousel
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.CompactCarousel
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.ConversationTree
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.GridCarousel
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.ModuleDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.Vertical
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.VerticalConversation
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.VerticalWithContetwittertLine
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.VerticalGrid
import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import com.twitter.timelines.render.{thriftscala => urt}

@Singleton
class ModuleDisplayTypeMarshaller @Inject() () {

  def apply(displayType: ModuleDisplayType): urt.ModuleDisplayType = displayType match {
    case Vertical => urt.ModuleDisplayType.Vertical
    case Carousel => urt.ModuleDisplayType.Carousel
    case VerticalWithContetwittertLine => urt.ModuleDisplayType.VerticalWithContetwittertLine
    case VerticalConversation => urt.ModuleDisplayType.VerticalConversation
    case ConversationTree => urt.ModuleDisplayType.ConversationTree
    case GridCarousel => urt.ModuleDisplayType.GridCarousel
    case CompactCarousel => urt.ModuleDisplayType.CompactCarousel
    case VerticalGrid => urt.ModuleDisplayType.VerticalGrid
  }
}
