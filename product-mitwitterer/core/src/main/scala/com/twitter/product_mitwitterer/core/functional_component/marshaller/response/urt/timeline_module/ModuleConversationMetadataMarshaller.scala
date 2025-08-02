package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.timeline_module

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.SocialContetwittertMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.ModuleConversationMetadata
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ModuleConversationMetadataMarshaller @Inject() (
  socialContetwittertMarshaller: SocialContetwittertMarshaller) {

  def apply(
    moduleConversationMetadata: ModuleConversationMetadata
  ): urt.ModuleConversationMetadata = urt.ModuleConversationMetadata(
    allTweetIds = moduleConversationMetadata.allTweetIds,
    socialContetwittert = moduleConversationMetadata.socialContetwittert.map(socialContetwittertMarshaller(_)),
    enableDeduplication = moduleConversationMetadata.enableDeduplication
  )
}
