package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.timeline_module

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.ModuleMetadata
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ModuleMetadataMarshaller @Inject() (
  adsMetadataMarshaller: AdsMetadataMarshaller,
  moduleConversationMetadataMarshaller: ModuleConversationMetadataMarshaller,
  gridCarouselMetadataMarshaller: GridCarouselMetadataMarshaller) {

  def apply(moduleMetadata: ModuleMetadata): urt.ModuleMetadata = urt.ModuleMetadata(
    adsMetadata = moduleMetadata.adsMetadata.map(adsMetadataMarshaller(_)),
    conversationMetadata =
      moduleMetadata.conversationMetadata.map(moduleConversationMetadataMarshaller(_)),
    gridCarouselMetadata =
      moduleMetadata.gridCarouselMetadata.map(gridCarouselMetadataMarshaller(_))
  )
}
