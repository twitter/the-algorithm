package com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module

object ModuleMetadata {
  def isConversationModule(moduleMetadata: Option[ModuleMetadata]): Boolean =
    moduleMetadata.map(_.conversationMetadata).isDefined
}

case class ModuleMetadata(
  adsMetadata: Option[AdsMetadata],
  conversationMetadata: Option[ModuleConversationMetadata],
  gridCarouselMetadata: Option[GridCarouselMetadata])
