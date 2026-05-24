package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.timeline_module

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.SocialContextMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleConversationMetadata
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModuleConversationMetadataMarshaller @Inject() (
  socialContextMarshaller: SocialContextMarshaller) {

  def apply(
    moduleConversationMetadata: ModuleConversationMetadata
  ): urt.ModuleConversationMetadata = urt.ModuleConversationMetadata(
    allTweetIds = moduleConversationMetadata.allTweetIds,
    socialContext = moduleConversationMetadata.socialContext.map(socialContextMarshaller(_)),
    enableDeduplication = moduleConversationMetadata.enableDeduplication
  )
}
